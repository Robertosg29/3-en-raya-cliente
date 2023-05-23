package data;

import Controlador.Controlador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rober
 */
public class Cliente {

    Socket conexionConServidor = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    boolean finCliente = false;
    Controlador c;
    String nombre;
    int id;
    char ficha = ' ';
    Tablero t;

    public Cliente() {
        t = new Tablero();
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setFinCliente(boolean finCliente) {
        this.finCliente = finCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void conectarServer() {

        try {
            conexionConServidor = new Socket(("localhost"), 50000);

            try {
                in = new DataInputStream(conexionConServidor.getInputStream());
                out = new DataOutputStream(conexionConServidor.getOutputStream());

                enviarNombre();//lo primero que hacemos es enviar y comprobar si nuestro nickName esta bien
                while (!finCliente) {
                    try {
                        conexionConServidor.setSoTimeout(3000);
                        String cad = in.readUTF();
                        System.out.println("Servidor dice : " + cad);
                        recibirServidor(cad);

                    } catch (IOException iOException) {
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error al crear el flujo de entrada o salida del Cliente o el servidor ha cortado la conexion");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "El servidor no está disponible");
        }

        try {
            if (conexionConServidor != null) {

                out.close();
                in.close();
                conexionConServidor.close();
                System.out.println("Se ha finalizado el chat.");
            }
        } catch (IOException ex) {
            System.out.println("Algún flujo no puede cerrarse.");
        }

    }

    private void recibirServidor(String cad) {

        String[] msjServidor = cad.split(Protocolo.SEPARADOR);
        switch (msjServidor[0]) {
            case "" + Protocolo.NOMBRE_REPETIDO_S:
                JOptionPane.showMessageDialog(null, "Nombre repetido, prueba otra vez");
                enviarNombre();
                break;
            case "" + Protocolo.NOMBRE_OK_S:
                this.c = new Controlador(this);//me lo creo aqui para que la ventana tome el valor del nombre del cliente, antes es null
                id = Integer.parseInt(msjServidor[1]);//Parseo y doy valor a mi atributo id
                c.hazVisibleLaVentana();
                break;
            case "" + Protocolo.PARTIDA_READY_S:
                //cambiare los botones para que solo podamos pulsar en iniciar y no en buscar, además paso el nombre del rival
                c.activarBotonIniciar(msjServidor[1]);
                break;
            case "" + Protocolo.ENVIAR_FICHA_RIVAL_S:
                //este es el caso de que no hayamos elegido ficha primero, entonces el servidor nos enviara la que quede libre
                asignarmeFichaDesdeServer(msjServidor[1]);
                break;
            case "" + Protocolo.CERRAR_VENTANAS_S:
                //esto viene despues de haber enviado el cliente iniciar partida, cerramos ventana y pintamos nuestro tab por primera vez
                cerrarVentana();
                mostrarTablero();
                break;
            case "" + Protocolo.COLOCA_FICHA_S:
                enviarPosAMover();
                break;
            case "" + Protocolo.ENVIAR_POSICION_S:
                actualizarTablero(msjServidor[1]);//actualizo posiciones y las pinto
                mostrarTablero();
                break;
            case "" + Protocolo.HAS_GANADO_S:
                //se finaliza normalmente la partida al ganar
                JOptionPane.showMessageDialog(null, "Enhorabuena " + nombre + " has ganado.");
                finalizarPartida();
                break;
            case "" + Protocolo.HAS_PERDIDO_S:
                //se finaliza normalmente la partida al perder
                JOptionPane.showMessageDialog(null, "Lo siento " + nombre + " has perdido.");
                finalizarPartida();
                break;

            case "" + Protocolo.GANAR_POR_ABANDONO_S:
                victoriaPorAbandono();
                break;
            case "" + Protocolo.FIN_SERVIDOR:
                //aqui corto al cliente una vez que el Servidor haya decidido interrumpir la conexion
                servidorDesconectado();

                break;
        }
    }

    private void enviarNombre() {
        try {
            nombre = JOptionPane.showInputDialog(null, "¿Cual es tu nickName?");
            out.writeUTF(Protocolo.ENVIAR_NOMBRE_C + Protocolo.SEPARADOR + nombre);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarPartida() {
        //Esto ocurre cuando el cliente hace click en buscarPartida, quiere entrar en una partida
        try {
            out.writeUTF(Protocolo.DAME_PARTIDA_C + "");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    

    public void iniciarPartida() {
        //AL HACER CLICK EN INICIAR PARTIDA
        try {
            //ESTA PARTE VIENE JUSTO DESPUES DE BUSCAR PARTIDA Y ENCONTRARLA CUANDO EL CLIENTE DESBLOQUEA EL BOTON
            //Vamos a elegir una ficha antes de iniciar partida en el servidor y se la pasamos

            if (elegirFicha()) {
                //ESTA CONDICION LA HAGO PORQUE SI EL CLIENTE PULSA EN SALIR Y EJECUTAMOS RENDIRNOS, NO QUEREMOS QUE 
                //ESTE CLIENTE INICIE LA PARTIDA, SI LO HACE SALTA EXCEPCION
                out.writeUTF(Protocolo.INICIAR_PARTIDA_C + Protocolo.SEPARADOR + ficha);
                out.flush();
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean elegirFicha() {
        boolean repetir = false;
        do {

            repetir = false;
            //voy a convertir el string a entero de la ficha que desee el cliente
            String respuesta = JOptionPane.showInputDialog(c.getV(), "Introduce 1 y elegirás 'X', introduce 2 y elegirás 'O',\n pulsa cancelar o la 'X' para salir y rendirte");
            if (respuesta != null) {
                try {
                    int num = Integer.parseInt(respuesta);

                    if (num == 1) {
                        ficha = 'X';
                        return true;
                    } else if (num == 2) {
                        ficha = 'O';
                        return true;

                    } else {

                        JOptionPane.showMessageDialog(c.getV(), "Solo puedes introducir 1 ó 2.");
                        repetir = true;

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(c.getV(), "Solo puedes introducir números como opción.");
                    repetir = true;
                }
            } else {
                rendirse();
                return false;
            }

        } while (repetir);

        return repetir;

    }

    private void asignarmeFichaDesdeServer(String cad) {
        //cojo el primer caracter de la cadena y se lo asigno a la ficha
        ficha = cad.charAt(0);
    }
    
    

    private void cerrarVentana() {
        c.cierraVentana();
//        JOptionPane.showMessageDialog(null, "Comienza el juego, suerte!");
    }

    private void mostrarTablero() {
        System.out.println("Tablero de " + nombre);
        t.pintarTablero();
    }
    
    
    

    private void enviarPosAMover() {
        boolean repetir = false;
        //COMO ES MI TURNO MUESTRO UN MENSAJE DE QUE ME TOCA JUGAR

        do {
            repetir = false;
            String posicion = JOptionPane.showInputDialog(null, nombre + " Dame un numero de fila y columna, desde 0 hasta 2, separado por comas. ¡ EJEMPLO 1,1 !");
            if (posicion != null) {

                try {

                    int fila = Integer.parseInt(posicion.split(",")[0]);
                    int columna = Integer.parseInt(posicion.split(",")[1]);
                    
                    if (!t.colocarFicha(fila, columna, ficha)) {
                        repetir = true;
                        JOptionPane.showMessageDialog(null, "Has introducido una posición ocupada o fuera del tablero.");
                    } else {
                        out.writeUTF(Protocolo.ENVIAR_POSICION_C + Protocolo.SEPARADOR + fila + Protocolo.SEPARADOR2 + columna + Protocolo.SEPARADOR2 + ficha);
                        out.flush();
                    }

                } catch (Exception e) {
                    repetir = true;
                    JOptionPane.showMessageDialog(null, "Recuerda, debes introducir dos numeros separados por coma, EJ 2,1");
                }

            } else {
                rendirse();
            }

        } while (repetir);
    }

    private void actualizarTablero(String posFicha) {

        String[] cadPosicion = posFicha.split(Protocolo.SEPARADOR2);//troceo la cadena
        int fila = Integer.parseInt(cadPosicion[0]);
        int columna = Integer.parseInt(cadPosicion[1]);
        char fich = cadPosicion[2].charAt(0);
        //actualizo el tablero de la partida
        t.colocarFicha(fila, columna, fich);

    }

    private void finalizarPartida() {
        //se finaliza normalmente la partida
        try {
            out.writeUTF(Protocolo.FIN_CLIENTE_C + Protocolo.SEPARADOR);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Se acabo la partida hasta la próxima.");
        finCliente = true;
    }

    private void rendirse() {
        JOptionPane.showMessageDialog(null, "Te has rendido "+nombre+" antes de tiempo");
        cortarConexionConServer();
        c.cierraVentana();
        finCliente = true;
    }

    public void cortarConexionConServer() {
        try {
            //ESTAMOS CERRANDO LA VENTANA DEL CLIENTE MANUALMENTE
            out.writeUTF(Protocolo.FIN_CLIENTE_ABANDONO_C + Protocolo.SEPARADOR + id);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void victoriaPorAbandono() {
        //habria que resetear al jugador en el futuro
        //ESTO SE HACE CUANDO RECIBES GANAR POR ABANDONO
        JOptionPane.showMessageDialog(null, "Tu rival ha abandonado, has ganado "+nombre+" enhorabuena.");
        c.reiniciarPanelInicial();
    }

    private void servidorDesconectado() {
        JOptionPane.showMessageDialog(null, "El servidor ha cortado la conexión, hasta luego");
        c.cierraVentana();
        finCliente = true;
    }

}

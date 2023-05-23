package Controlador;

import GUI.VPpal;
import data.Cliente;

/**
 *
 * @author Rober
 */
public class Controlador {

    Cliente c;
    VPpal v;

    public Controlador(Cliente c) {
        this.c = c;
        v = new VPpal(this);
    }

    public Cliente getC() {
        return c;
    }

    public VPpal getV() {
        return v;
    }

    public void hazVisibleLaVentana() {
        v.setVisible(true);
    }

    public void buscarPartida() {
        c.buscarPartida();
    }

    public void activarBotonIniciar(String nombreRival) {
        //esto lo haré despues de recibir el PARTIDA READY
        v.getPi().getbIniciarPartida().setEnabled(true);
        v.getPi().getbBuscarPartida().setEnabled(false);
        v.getPi().getEtiquetaP().setText("Rival " + nombreRival + " encontrado, pulsa Iniciar Partida para comenzar.");
        v.getPi().revalidate();

    }

    public void cierraVentana() {
        if (v.isVisible()) {
            //solo funciona si la ventana esta abierta o activa, esto lo hago por si cuando se produce el abandono la ventana no estuviera
            //abierta 
            v.dispose();
        }
    }

    public void ClienteCortaConexion() {
        c.cortarConexionConServer();
    }

    public void cambiarBoleando() {
        c.setFinCliente(true);
    }

    public void reiniciarPanelInicial() {
        if (v.isVisible()) {
            //solo funciona si la ventana esta abierta o activa, esto lo hago por si cuando se produce el abandono la ventana no estuviera
            //abierta
            v.reiniciarPanelInicio();
        } else {
            //abririamos una nueva ventana en el cliente que se queda vivo
            v = new VPpal(this);
            v.setVisible(true);
        }
    }

    public void iniciarPartida() {
        c.iniciarPartida();
    }

}

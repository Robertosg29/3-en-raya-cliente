package data;
/**
 *
 * @author Rober
 */
public class Tablero {

    final int NUM_FILAS = 3;
    char[][] tablero = new char[NUM_FILAS][NUM_FILAS];

    public Tablero() {
      inicializarTablero();
    }

    public boolean colocarFicha(int fila, int columna, char ficha) {

        if (fila <= 2 & columna <= 2 & fila >= 0 & columna >= 0) { 
            
            if (tablero[fila][columna] == ' ') {

                tablero[fila][columna] = ficha;
                return true;//comprobamos que los numeros esten dentro del array y comprobamos que no haya ficha pintada
            }
        }

        return false;
    }
    private void inicializarTablero(){
        for (int i = 0; i < NUM_FILAS; i++) {
            for (int j = 0; j < NUM_FILAS; j++) {
                tablero[i][j]=' ';
                
            }
            
        }
    }
     public void pintarTablero(){
        
        System.out.print("  ");
        for (int i = 0; i < NUM_FILAS; i++) {
            System.out.print(i+"  ");
        }
        System.out.println("");
        for (int i = 0; i < NUM_FILAS; i++) {
            System.out.print(i+"|");
            for (int j = 0; j < NUM_FILAS; j++) {
                System.out.print(tablero[i][j]+" |");
            }
            System.out.println("");
        }
    }

}

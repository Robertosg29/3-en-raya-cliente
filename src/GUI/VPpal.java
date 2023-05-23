
package GUI;
import Controlador.Controlador;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JPanel;

/**
 *
 * @author Rober
 */
public class VPpal extends javax.swing.JFrame implements WindowListener{

    Controlador c;
    PInicial pi;
    public VPpal(Controlador c) {
        this.c=c;
        initComponents();
        minitComponents();
        pi=new PInicial(this);
        ponPanel(pi);
        this.addWindowListener(this);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void ponPanel(JPanel p){
        setContentPane(p);
        revalidate();
    }

    private void minitComponents() {
        setTitle("Las 3 en raya de "+c.getC().getNombre());
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public Controlador getC() {
        return c;
    }

    public PInicial getPi() {
        return pi;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        c.ClienteCortaConexion();
        c.cambiarBoleando();//corto el bucle cliente
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void reiniciarPanelInicio() {
      // AQUI VENIMOS AL RECIBIR EL GANAR POR ABANDONO, RESETEAMOS LOS BOTONES Y EL PANEL COMO ESTABA AL PRINCIPIO
      
      this.getPi().getbIniciarPartida().setEnabled(false);
      this.getPi().getbBuscarPartida().setEnabled(true);
      this.getPi().getEtiquetaP().setText("Bienvenido a las 3 en raya.");
      this.getPi().revalidate();
      this.revalidate();
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

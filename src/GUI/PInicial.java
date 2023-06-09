/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Rober
 */
public class PInicial extends javax.swing.JPanel {

    VPpal v;
    public PInicial(VPpal v) {
        this.v=v;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaP = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bBuscarPartida = new javax.swing.JButton();
        bIniciarPartida = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        etiquetaP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaP.setText("Bienvenido a las 3 en raya");
        etiquetaP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(etiquetaP, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        bBuscarPartida.setText("Buscar Partida");
        bBuscarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarPartidaActionPerformed(evt);
            }
        });
        jPanel1.add(bBuscarPartida);

        bIniciarPartida.setText("Iniciar Partida");
        bIniciarPartida.setEnabled(false);
        bIniciarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIniciarPartidaActionPerformed(evt);
            }
        });
        jPanel1.add(bIniciarPartida);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void bBuscarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarPartidaActionPerformed
        etiquetaP.setText("Estamos buscando un rival....");
        bBuscarPartida.setEnabled(false);
        v.getC().buscarPartida();
        
    }//GEN-LAST:event_bBuscarPartidaActionPerformed

    private void bIniciarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIniciarPartidaActionPerformed
       v.getC().iniciarPartida();
    }//GEN-LAST:event_bIniciarPartidaActionPerformed

    public JButton getbIniciarPartida() {
        return bIniciarPartida;
    }

    public JButton getbBuscarPartida() {
        return bBuscarPartida;
    }

    public JLabel getEtiquetaP() {
        return etiquetaP;
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBuscarPartida;
    private javax.swing.JButton bIniciarPartida;
    private javax.swing.JLabel etiquetaP;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

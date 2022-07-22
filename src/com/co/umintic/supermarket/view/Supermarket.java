/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.co.umintic.supermarket.view;

import java.awt.BorderLayout;

/**
 *
 * @author crist
 */
public class Supermarket extends javax.swing.JFrame {

    /**
     * Creates new form Supermarket
     */
    public Supermarket() {
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

        jToggleButton1 = new javax.swing.JToggleButton();
        panelPrincipal = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuProveedores = new javax.swing.JMenu();
        menuCRUDProveedores = new javax.swing.JMenuItem();
        menuClientes = new javax.swing.JMenu();
        menuCRUDClientes = new javax.swing.JMenuItem();
        menuVentas = new javax.swing.JMenu();
        menuNuevaVenta = new javax.swing.JMenuItem();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 650));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 650));

        panelPrincipal.setBackground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 662, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        menuProveedores.setText("Proveedores");

        menuCRUDProveedores.setText("CRUD");
        menuCRUDProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCRUDProveedoresActionPerformed(evt);
            }
        });
        menuProveedores.add(menuCRUDProveedores);

        jMenuBar1.add(menuProveedores);

        menuClientes.setText("Clientes");

        menuCRUDClientes.setText("CRUD");
        menuCRUDClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCRUDClientesActionPerformed(evt);
            }
        });
        menuClientes.add(menuCRUDClientes);

        jMenuBar1.add(menuClientes);

        menuVentas.setText("Ventas");

        menuNuevaVenta.setText("Nueva venta");
        menuNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevaVentaActionPerformed(evt);
            }
        });
        menuVentas.add(menuNuevaVenta);

        jMenuBar1.add(menuVentas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCRUDProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCRUDProveedoresActionPerformed
        agregarAlPanelPrincipal(new PanelProveedores());
    }//GEN-LAST:event_menuCRUDProveedoresActionPerformed

    private void menuCRUDClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCRUDClientesActionPerformed
        agregarAlPanelPrincipal(new PanelClientes());
    }//GEN-LAST:event_menuCRUDClientesActionPerformed

    private void menuNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevaVentaActionPerformed
        agregarAlPanelPrincipal(new PanelVentas());
    }//GEN-LAST:event_menuNuevaVentaActionPerformed

    private void agregarAlPanelPrincipal(javax.swing.JPanel panel) {
        panelPrincipal.removeAll();
        panel.setAlignmentX(20);
        panel.setAlignmentY(20);
        panel.setSize(panelPrincipal.getSize());
        panelPrincipal.setLayout(null);
        panelPrincipal.add(panel);
        panelPrincipal.setVisible(true);
        panelPrincipal.repaint();
        panel.setVisible(true);
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Supermarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Supermarket view = new Supermarket();
                view.setVisible(true);
                view.setLayout(null);
            }

            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JMenuItem menuCRUDClientes;
    private javax.swing.JMenuItem menuCRUDProveedores;
    private javax.swing.JMenu menuClientes;
    private javax.swing.JMenuItem menuNuevaVenta;
    private javax.swing.JMenu menuProveedores;
    private javax.swing.JMenu menuVentas;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
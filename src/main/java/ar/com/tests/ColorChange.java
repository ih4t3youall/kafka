package ar.com.tests;


import java.awt.Color;
import java.awt.Graphics;
 
/**
 *
 * @author Javadb.com
 */
public class ColorChange extends javax.swing.JFrame {
 
    private Color color;
    private javax.swing.JButton buttonBlue;
    private javax.swing.JButton buttonGreen;
    private javax.swing.JButton buttonRed;
 
    /**
     * Creates new form ColorChange
     */
    public ColorChange() {
        initComponents();
        setTitle("Color change example");
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
 
        buttonGreen = new javax.swing.JButton();
        buttonBlue = new javax.swing.JButton();
        buttonRed = new javax.swing.JButton();
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
 
        buttonGreen.setText("Green");
        buttonGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGreenActionPerformed(evt);
            }
        });
 
        buttonBlue.setText("Blue");
        buttonBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBlueActionPerformed(evt);
            }
        });
 
        buttonRed.setText("Red");
        buttonRed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedActionPerformed(evt);
            }
        });
 
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(317, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonGreen, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(buttonBlue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(buttonGreen)
                .addGap(33, 33, 33)
                .addComponent(buttonBlue)
                .addGap(33, 33, 33)
                .addComponent(buttonRed)
                .addContainerGap(105, Short.MAX_VALUE))
        );
 
        pack();
    }// </editor-fold>                        
 
    private void buttonGreenActionPerformed(java.awt.event.ActionEvent evt) {                                            
        color = Color.GREEN;
        repaint();
    }                                           
 
    private void buttonBlueActionPerformed(java.awt.event.ActionEvent evt) {                                           
        color = Color.BLUE;
        repaint();
    }                                          
 
    private void buttonRedActionPerformed(java.awt.event.ActionEvent evt) {                                          
        color = Color.RED;
        repaint();
    }                                         
 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (color != null) {
            g.setColor(color);
            g.fillRect(50, 50, 100, 100);
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ColorChange.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ColorChange.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ColorChange.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ColorChange.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ColorChange().setVisible(true);
            }
        });
    }
}
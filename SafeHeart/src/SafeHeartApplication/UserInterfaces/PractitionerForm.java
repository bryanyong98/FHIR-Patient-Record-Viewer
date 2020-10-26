/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.UserInterfaces;
import SafeHeartApplication.EventUpdate.CholesterolObserver;
import SafeHeartApplication.EventUpdate.PatientVitalsData;
import SafeHeartApplication.EventUpdate.Subject;
import SafeHeartApplication.ServerDatas.ClinicianData;
//import SafeHeartApplication.ServerDatas.fhirServerData;
import SafeHeartApplication.ServerDatas.PatientData;
import SafeHeartApplication.Users.Patient;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author yogeshganesh
 */

public class PractitionerForm extends javax.swing.JFrame {
    
    // This is the clinician ID that has been passed. 
    private static String id;
    private static PractitionerLogin frm;
    private static PatientVitalsData subject ; 
    
    
    /**
     * Creates new form practitionerForm
     */
    public PractitionerForm(PatientVitalsData subject, String clincianid) 
    {
        initComponents();        
        setId(clincianid) ; 
        setSubject(subject);    
        addRowToTable() ; // adding the patient's data to the table       
    }
    
        public javax.swing.JTable getTable()
    {
        return this.patientTable;
    }

    
    public String getId()
    {
       return this.id;
    }
    
    public Subject getSubject()
    {
        return this.subject ; 
    }
    
    public void setId(String id)
    {
       this.id = id ;
    }
    
    public void setSubject(PatientVitalsData subject)
    {
        this.subject = subject ; 
    }    
    
    public HashMap<String, ArrayList<Patient>> getPatientList()
    {
        
        return PatientData.getPatients();
                
    }
    
    
    public void addRowToTable()
    {
        DefaultTableModel model = (DefaultTableModel) this.patientTable.getModel();
        ArrayList<Patient> list = getPatientList().get(id);

        Object rowData [] = new Object[7];

        for(int j=0; j<list.size();j++)
        {   
            rowData[0] = false;
            rowData[1] = list.get(j).getId();
            rowData[2] = list.get(j).getPrefix();
            rowData[3] = list.get(j).getFamilyName();
            rowData[4] = list.get(j).getGivenName();
            rowData[5] = list.get(j).getGender();

            model.addRow(rowData);           
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        patientTable = new javax.swing.JTable();
        monitorButton = new java.awt.Button();
        logoutButton = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        patientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Prefix", "Family Name", "Given Name", "Gender"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        patientTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(patientTable);
        patientTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        monitorButton.setActionCommand("Monitor");
        monitorButton.setLabel("Monitor");
        monitorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorButtonActionPerformed(evt);
            }
        });

        logoutButton.setLabel("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(monitorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(monitorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void monitorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorButtonActionPerformed
       
        DefaultTableModel model = (DefaultTableModel) this.patientTable.getModel();
        ArrayList <Integer> index = new ArrayList();

        for (int k =0 ; k<model.getRowCount();k++)
        {
            if (model.getValueAt(k,0).equals(true))
            {
                index.add(k);
                
            }
        }
        
            for(int a = 0 ; a<index.size();a++)
            {

                Patient monitored = null;
                Object patientIdObj = model.getValueAt(index.get(a),1);
                String patientId = patientIdObj.toString();
                ArrayList<Patient> list = getPatientList().get(id);
                for (int g=0; g < list.size();g++)
                {
                    if (list.get(g).getId().equals(patientId))
                    {
                        monitored = list.get(g);
                        
                    }
                }
            
            
                // creating a new monitor window. Also, pass in the clinician id. 
                Monitor frm = new Monitor(monitored,this,this.subject, this.id);
           
                frm.setVisible(true);
                this.setVisible(false);
                       
            }
    }//GEN-LAST:event_monitorButtonActionPerformed

    // Take note of this part. 
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        
        this.setVisible(false);
        this.frm.setVisible(true);
        
    }//GEN-LAST:event_logoutButtonActionPerformed

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
            java.util.logging.Logger.getLogger(PractitionerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PractitionerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PractitionerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PractitionerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PractitionerForm(subject, id).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Button logoutButton;
    private java.awt.Button monitorButton;
    private javax.swing.JTable patientTable;
    // End of variables declaration//GEN-END:variables
}
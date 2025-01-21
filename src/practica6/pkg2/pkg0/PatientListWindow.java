/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practica6.pkg2.pkg0;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *Interfaz prinncipal la cual implementa las funcionalidades de crear, eliminar pacientes asi como activar distintos tipos de listeners como la ventana de cuarto, el monitor global , el guardado de los valores en una db , una alarma para indicar la gravedad del paciente al cual se esta escuchando, un boton el cual muestra el monitor global...Los listeners se seleccionaran mediante combo box´s que iran puestos en una jTable de una fila por paciente y una columna por listener mas el nomre de paciente.
 * @author nacho
 */
public class PatientListWindow extends javax.swing.JFrame {

    private ConsolePressureListener consolePressureListener;
    private WindowPressureListener windowPressureListener;
    private GlobalWindowPressureListener globalWindowPressureListener;
    private DBPressureListener dBPressureListener;
    private AlarmPressureListener alarmPressureListener;
    private ArrayList<Patient> patients = new ArrayList();

    /**
     *Devuelve array de patients
     * @return patients
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Creates new form PatientListWindow
     */
    public PatientListWindow() throws UnsupportedAudioFileException, IOException {
        initComponents();
        globalWindowPressureListener = new GlobalWindowPressureListener(this);
        consolePressureListener = new ConsolePressureListener();
        dBPressureListener = new DBPressureListener();
        alarmPressureListener = new AlarmPressureListener();
        dBPressureListener.getConnection();

        patientsTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = patientsTable.getSelectedRow();
                    int column = patientsTable.getSelectedColumn();
                    updatePatientListeners(row, column);
                }
            }
        });

    }
//Metodo que activa desactiva listeners segun la columna seleccionada y paciente seleccionado
    private void updatePatientListeners(int row, int column) {
        PropertyChangeListener selectedListener = null;
        DefaultTableModel model = (DefaultTableModel) (TableModel) patientsTable.getModel();
        Boolean value = (Boolean) model.getValueAt(row, column);

        Patient p = patients.get(row);

        if (patientsTable.getSelectedColumn() == 1) {
            selectedListener = consolePressureListener;
            if (value.booleanValue()) {
                p.addPropertyChangeListener(selectedListener);
            } else {
                p.removePropertyChangeListener(selectedListener);
            }
        }
        if (patientsTable.getSelectedColumn() == 2) {
            if (value.booleanValue()) {
                p.addPropertyChangeListener(p.getWindowPressureListener());
                p.getWindowPressureListener().setVisible(true);
            } else {
                p.removePropertyChangeListener(p.getWindowPressureListener());
            }
        }

        if (patientsTable.getSelectedColumn() == 3) {
            selectedListener = globalWindowPressureListener;
            if (value.booleanValue()) {
                p.addPropertyChangeListener(selectedListener);
                globalWindowPressureListener.setVisible(true);
            } else {
                p.removePropertyChangeListener(selectedListener);
                globalWindowPressureListener.setVisible(true);
            }
        }
        if (patientsTable.getSelectedColumn() == 4) {
            selectedListener = dBPressureListener;
            if (value.booleanValue()) {
                p.addPropertyChangeListener(selectedListener);
            } else {
                p.removePropertyChangeListener(selectedListener);
            }
        }
        if (patientsTable.getSelectedColumn() == 5) {
            selectedListener = alarmPressureListener;
            if (value.booleanValue()) {
                p.addPropertyChangeListener(selectedListener);
            } else {
                p.removePropertyChangeListener(selectedListener);
                alarmPressureListener.getSickPatients().remove(p);
                if (alarmPressureListener.getSickPatients().isEmpty()) {
                    alarmPressureListener.getSonido().loop(0);
                }

            }
        }
    }

    /**
     *Metodo que elimina un checbox recorriendose la tabla de patients este metodo es llamado cuando se cierra una venta de un listener.
     * @param p
     */
    public void removeCheckBox(Patient p) {
        DefaultTableModel model = (DefaultTableModel) (TableModel) patientsTable.getModel();
        int rowIndex = -1;
        for (int i = 0; i < patientsTable.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(p.toString())) {
                rowIndex = i;
            }
        }
        model.setValueAt(false, rowIndex, 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        patientsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        novoPaciente = new javax.swing.JButton();
        eliminarPaciente = new javax.swing.JButton();
        showMonGlobal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Patients Blood Pressure Monitor");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        patientsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient", "Console Monitor", "Room Monitor", "Global Monitor", "Save Patient Data", "Alarm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        patientsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        patientsTable.setColumnSelectionAllowed(true);
        patientsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(patientsTable);
        patientsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (patientsTable.getColumnModel().getColumnCount() > 0) {
            patientsTable.getColumnModel().getColumn(0).setResizable(false);
            patientsTable.getColumnModel().getColumn(1).setResizable(false);
            patientsTable.getColumnModel().getColumn(2).setResizable(false);
            patientsTable.getColumnModel().getColumn(3).setResizable(false);
            patientsTable.getColumnModel().getColumn(4).setResizable(false);
            patientsTable.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        novoPaciente.setText("New Patient");
        novoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoPacienteActionPerformed(evt);
            }
        });
        jPanel3.add(novoPaciente);

        eliminarPaciente.setText("Delete Patient");
        eliminarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPacienteActionPerformed(evt);
            }
        });
        jPanel3.add(eliminarPaciente);

        showMonGlobal.setText("Show Global Monitor");
        showMonGlobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMonGlobalActionPerformed(evt);
            }
        });
        jPanel3.add(showMonGlobal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// metodo patra creear un nuevo paciente con joption panel´s con control de errores y verificacion de datos introducidos, cuando se crea un nuevo paciente se le añade al array de pacientes y tambien se le añade el listener de la global window asi como la actualizacion de la misma para que aparezca el nuevo paciente aunque todavia no se este escuchando.
    private void novoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoPacienteActionPerformed
        // TODO add your handling code here:
        String name, maxBloodPressure, minBloodPressure;
        double maxBlood = 0;
        double minBlood = 0;
        name = JOptionPane.showInputDialog("Enter patients name");
        if (name == null) {
            showMessageDialog(null, "Patient cannot be created", "Error", ERROR_MESSAGE);
            return;
        }
        if (name != null && name.isBlank()) {

            showMessageDialog(null, "Enter data again", "Error", ERROR_MESSAGE);
            name = JOptionPane.showInputDialog("Enter patients name");

        }

        maxBloodPressure = JOptionPane.showInputDialog("Enter max pressure");
        if (maxBloodPressure == null) {
            showMessageDialog(null, "Patient cannot be created", "Error", ERROR_MESSAGE);
            return;
        }
        if (maxBloodPressure != null && maxBloodPressure.isBlank()) {

            showMessageDialog(null, "Enter data again", "Error", ERROR_MESSAGE);

            maxBloodPressure = JOptionPane.showInputDialog("Enter max pressure");

        }
        if (!maxBloodPressure.isBlank()) {
            try {
                maxBlood = Double.parseDouble(maxBloodPressure.toString().trim());
            } catch (Exception e) {
                showMessageDialog(null, "Enter data again(decimal)", "Error", ERROR_MESSAGE);
                maxBloodPressure = JOptionPane.showInputDialog("Enter max pressure");
            }
        }

            minBloodPressure = JOptionPane.showInputDialog("Enter min pressure");
        if (minBloodPressure == null) {
            showMessageDialog(null, "Patient cannot be created", "Error", ERROR_MESSAGE);
            return;
        }
        if (minBloodPressure != null && minBloodPressure.isBlank()) {

            showMessageDialog(null, "Enter data again", "Error", ERROR_MESSAGE);
            minBloodPressure = JOptionPane.showInputDialog("Enter min pressure");
        }
        if (!minBloodPressure.isBlank()) {

            try {
                minBlood = Double.parseDouble(minBloodPressure.toString().trim());
            } catch (Exception e) {
                showMessageDialog(null, "Enter data again(decimal)", "Error", ERROR_MESSAGE);
                minBloodPressure = JOptionPane.showInputDialog("Enter min pressure");
            }
        }

        Patient p = new Patient(name, maxBlood, minBlood);
        DefaultTableModel model = (DefaultTableModel) patientsTable.getModel();
        model.addRow(new Object[]{p.toString()});
        patients.add(p);
        windowPressureListener = new WindowPressureListener(this);
        p.setWindowPressureListener(windowPressureListener);
        globalWindowPressureListener.detectsNewPatient(p);
        globalWindowPressureListener.updateGlobalPanel(p);
    }//GEN-LAST:event_novoPacienteActionPerformed

// metodo para eliminar un paciente mediante un boton. Segun la linea seleccionada se elimara el paciente y tambien sus correspondientes listeners.
    private void eliminarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPacienteActionPerformed
        // TODO add your handling code here:
        if (patientsTable.isRowSelected(patientsTable.getSelectedRow())) {
            int seguro = JOptionPane.showConfirmDialog(null, "Are you sure to delete the selected patient?", "Sure???", JOptionPane.YES_NO_CANCEL_OPTION);
            if (seguro == JOptionPane.YES_OPTION) {

                Patient p = patients.get(patientsTable.getSelectedRow());

                p.removePropertyChangeListener(consolePressureListener);

                p.removePropertyChangeListener(windowPressureListener);
                windowPressureListener.dispose();

                p.removePropertyChangeListener(globalWindowPressureListener);
                globalWindowPressureListener.deletePatientFromJPanel(p);

                p.removePropertyChangeListener(dBPressureListener);
                dBPressureListener.closeConnection();

                p.removePropertyChangeListener(alarmPressureListener);
                alarmPressureListener.getSickPatients().remove(p);
                if (alarmPressureListener.getSickPatients().isEmpty()) {
                    alarmPressureListener.getSonido().loop(0);
                }

                patients.remove(p);
                ((DefaultTableModel) patientsTable.getModel()).removeRow(patientsTable.getSelectedRow());
            }

        } else {
            showMessageDialog(null, "Debes seleccionar un paciente", "Selecciona un paciente!!!", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_eliminarPacienteActionPerformed
//boton que muestra la global window
    private void showMonGlobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMonGlobalActionPerformed
        // TODO add your handling code here: 
        globalWindowPressureListener.setVisible(true);
    }//GEN-LAST:event_showMonGlobalActionPerformed
// si se cierra la ventana principal se cierra la conexion con la base de datos sqlite
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dBPressureListener.closeConnection();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(PatientListWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientListWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientListWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientListWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PatientListWindow().setVisible(true);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(PatientListWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PatientListWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eliminarPaciente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton novoPaciente;
    private javax.swing.JTable patientsTable;
    private javax.swing.JButton showMonGlobal;
    // End of variables declaration//GEN-END:variables
}

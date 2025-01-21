/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica6.pkg2.pkg0;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

/**
 * Esta clase se conecta con una base de datos sqlite y escribe los valores
 * del/los pacientes seleccionados en la base de datos.
 *
 * @author nacho
 */
public class DBPressureListener implements PropertyChangeListener {

    GregorianCalendar calendar = new GregorianCalendar();
    private Patient p;
    private static Connection c = null;

    /**
     * Crea la conexion con la base de datos.
     */
    public static void getConnection() {
        if (c == null) {
            try {
                c = DriverManager.getConnection("jdbc:sqlite:F:\\1CSDAW\\PROGRAMACION\\Practica6.2.0\\src\\practica6\\pkg2\\pkg0\\pressure.db", "", "");

                String sql = "CREATE TABLE IF NOT EXISTS pressures (id INTEGER PRIMARY KEY AUTOINCREMENT,patient TEXT NOT NULL, instant TIMESTAMP NOT NULL, pressure_type TEXT NOT NULL,pressure_value DOUBLE NOT NULL);";

                try (Statement st = c.createStatement()) {
                    st.executeUpdate(sql);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Conexion fallida");
            }
        } else {
            System.out.println("A conexión co servidor de bases de datos non se puido establecer");
        }

    }

    /**
     * Cierra la conexion con la base de datos.
     */
    public static void closeConnection() {

        try {
            c.close();
            System.out.println("CONEXION CERRADA");
        } catch (SQLException ex) {
            System.out.println("NO SE PUDO CERRAR LA CONEXION");
        }
    }

    /**
     * Cuando escucha un nuevo cambio lo mete en la base de datos con una orden
     * INSERT mediante un preparedStatement.
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        p = (Patient) evt.getSource();
        String query = "INSERT INTO pressures(patient,instant,pressure_type,pressure_value)VALUES(?,CURRENT_TIMESTAMP,?,?)";
        try (PreparedStatement pstmt = c.prepareStatement(query)) {
            pstmt.setString(1, p.getName());
            pstmt.setString(2, evt.getPropertyName());
            if (evt.getPropertyName().equals("MaxPressure")) {
                pstmt.setDouble(3, p.getMaxPressure());
            } else {
                pstmt.setDouble(3, p.getMinPressure());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {

        }

    }
}

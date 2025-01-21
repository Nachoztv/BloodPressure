/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica6.pkg2.pkg0;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *Clase ConsolePressureListener cuando se le comunica un cambio imprime por pantalla los nuevos valores tanto min como max del paciente el cual comunico los cambios.
 * @author ignacio.campinofernandez
 */
public class ConsolePressureListener implements PropertyChangeListener{

    /**
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Patient p = (Patient) evt.getSource();
        if (evt.getPropertyName().equals("MaxPressure")) {
            System.out.println( "Maxima do paciente " + p.toString() + " : " + evt.getNewValue());
        } else {
            System.out.println( "Minima do paciente " + p.toString() +" : "+ evt.getNewValue());
        }
    }
    
}

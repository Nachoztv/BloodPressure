/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica6.pkg2.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *Clase Patient objeto el cúal implentará listeners que irán comunicando a los demás objetos de las clases los cambios realizados en las presiones sanguíneas del objeto.
 * @author ignacio.campinofernandez
 */
public class Patient implements ActionListener {
//Declaración de variables y constantes
    private String name;
    public final static int MAX_PERMIT_PRESSURE = 130;
    public final static int MIN_PERMIT_PRESSURE = 85;
    private double maxPressure;
    private double minPressure;

    // objetos de otras clases
    private WindowPressureListener windowPressureListener;
    private AlarmPressureListener alarmPressureListener;

    public AlarmPressureListener getAlarmPressureListener() {
        return alarmPressureListener;
    }

    public void setAlarmPressureListener(AlarmPressureListener alarmPressureListener) {
        this.alarmPressureListener = alarmPressureListener;
    }
    
    

    public WindowPressureListener getWindowPressureListener() {
        return windowPressureListener;
    }

    public void setWindowPressureListener(WindowPressureListener windowPressureListener) {
        this.windowPressureListener = windowPressureListener;
    }

    /**
     *Constante para el tiempo etablecido en el timer
     */
    public final static int DELAY = 1000;

    private Timer timer;
    //Array de listeners
    private ArrayList<PropertyChangeListener> myListeners = new ArrayList<>();

    /**
     *Devuelve nombre
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *Devuelve presión máxima
     * @return maxPressure
     */
    public double getMaxPressure() {
        return maxPressure;
    }

    /**
     *El metodo set avisa al listener de que hubo un cambio
     * @param maxPressure
     */
    public void setMaxPressure(double maxPressure) {
        for (PropertyChangeListener myListener : myListeners) {
            myListener.propertyChange(new PropertyChangeEvent(this, "MaxPressure", getMaxPressure(), maxPressure));

        }

        this.maxPressure = maxPressure;

    }

    /**
     *Devuelve presión minima
     * @return minPressure
     */
    public double getMinPressure() {
        return minPressure;
    }

    /**
     *El metodo set avisa al listener de que hubo un cambio
     * @param minPressure
     */
    public void setMinPressure(double minPressure) {
        for (PropertyChangeListener myListener : myListeners) {
            myListener.propertyChange(new PropertyChangeEvent(this, "MinPressure", getMinPressure(), minPressure));
        }

        this.minPressure = minPressure;

    }

    /**
     *Constructor de la clase doonde creamos un timer
     * @param name
     * @param maxPressure
     * @param minPressure
     */
    public Patient(String name, double maxPressure, double minPressure) {
        this.name = name;
        this.maxPressure = maxPressure;
        this.minPressure = minPressure;
        this.timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     *Metodo para parar el timer
     */
    public void disconnect() {
        timer.stop();
    }

    /**
     *Añade un listener al array de listeners
     * @param l
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        myListeners.add(l);
    }

    /**
     *Quita un listener del array de listeners
     * @param l
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        myListeners.remove(l);
    }

    /**
     *Cambia el valor de max y min con una funcion random
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        setMaxPressure((maxPressure) + (new java.util.Random().nextInt(3) - 1));
        setMinPressure((minPressure) + (new java.util.Random().nextInt(3) - 1));
    }

    /**
     *Devuelve el nombre del paciente
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica6.pkg2.pkg0;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ignacio.campinofernandez
 */
public class AlarmPressureListener implements PropertyChangeListener {

    private Clip sonido;
    private HashSet<Patient> sickPatients = new HashSet<Patient>();

    public HashSet<Patient> getSickPatients() {
        return sickPatients;
    }
    
    

    /**
     *
     * @return
     */
    public Clip getSonido() {
        return sonido;
    }

    /**
     *Esta clase coge un archivo de audio y lo utiliza cuando detecta que los valores del paciente/es escuchados salen de los valores normales.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public AlarmPressureListener() throws UnsupportedAudioFileException, IOException {

        try {
            sonido = AudioSystem.getClip();
            File a = new File("F:\\1CSDAW\\PROGRAMACION\\Practica6.2.0\\src\\practica6\\pkg2\\pkg0\\mixkit-alert-alarm-1005.wav");
            sonido.open(AudioSystem.getAudioInputStream(a));

        } catch (LineUnavailableException ex) {
            System.out.println("NO ES POSIBLE OBTENER EL ARCHIVO");
        }

    }

    /**
     *Cuando detecta una anomalia mete al paciente en un array de pacientes malos y activa la alarma, una vez el paciente se deja de escuchar o vuelve a unos valores normales se quita del array de pacientes malos y se desactiva la alarma.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Patient p = (Patient) evt.getSource();
        if (evt.getPropertyName().equals("MaxPressure")) {
            Double max = (Double) evt.getNewValue();
            if (max > Patient.MAX_PERMIT_PRESSURE) {
                sickPatients.add(p);
                if (!sickPatients.isEmpty()) {
                    sonido.loop(Clip.LOOP_CONTINUOUSLY);
                    sonido.start();
                }

            } else {
                sickPatients.remove(p);
                if (sickPatients.isEmpty()) {
                    sonido.loop(0);
                }
            }
        } else {
            Double min = (Double) evt.getNewValue();
            if (min < Patient.MIN_PERMIT_PRESSURE) {
                sickPatients.add(p);
                if (!sickPatients.isEmpty()) {
                    sonido.loop(Clip.LOOP_CONTINUOUSLY);
                    sonido.start();
                }
            } else {
                sickPatients.remove(p);
                if (sickPatients.isEmpty()) {
                    sonido.loop(0);
                }

            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datossensor;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class DatosSensor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int NUM_PROCESOS = 4;
        final int NUM_ITERACIONES = 4;

        Semaphore semSensor = new Semaphore(0);
        Semaphore[] semProcesos = new Semaphore[NUM_PROCESOS];
        Proceso[] procesos = new Proceso[NUM_PROCESOS];

        Datos datos = new Datos();
        
        Sensor sensor = new Sensor(datos, NUM_ITERACIONES, semSensor, semProcesos);
        sensor.start();

        for (int i = 0; i < NUM_PROCESOS; i++) {
            semProcesos[i] = new Semaphore(0);
        }
        
        for (int i = 0; i < NUM_PROCESOS; i++) {
            procesos[i] = new Proceso(i, datos, NUM_ITERACIONES, semSensor, semProcesos);
            procesos[i].start();
        }
        
        semSensor.release(4);
        
        for (int i = 0; i < NUM_PROCESOS; i++) {
            try {
                procesos[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(DatosSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(datos.getAnotacion());
    }
    
}

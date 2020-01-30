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
public class Sensor extends Thread {

    private final Datos dato;
    private final Semaphore sensor;
    private final Semaphore[] proceso;
    private final int iteraccion;

    public Sensor(Datos datos, int iteraccion, Semaphore sensor, Semaphore[] proceso) {
        this.dato = datos;
        this.sensor = sensor;
        this.proceso = proceso;
        this.iteraccion = iteraccion;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iteraccion; i++) {
                sensor.acquire(4);
                dato.almacenar(Math.random() * 100);
                
                for (Semaphore procesoFinal : proceso) {
                    procesoFinal.release();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

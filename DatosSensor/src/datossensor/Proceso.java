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
public class Proceso extends Thread {

    private final int idProceso;
    private final Datos dato;
    private final int iteraccion;
    private final Semaphore sensor;
    private final Semaphore[] proceso;

    public Proceso(int idProceso, Datos dato, int iteraccion, Semaphore sensor, Semaphore[] proceso) {
        this.idProceso = idProceso;
        this.dato = dato;
        this.iteraccion = iteraccion;
        this.sensor = sensor;
        this.proceso = proceso;
    }

    public int getIdProceso() {
        return idProceso;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iteraccion; i++) {
                proceso[idProceso].acquire();
                dato.añadir("\nProceso " + this.getIdProceso() + " tratando la información.");

                sensor.release();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

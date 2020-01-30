/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datossensor;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Datos {
    private String anotacion;
    private double valor;
    private final Semaphore semaforo = new Semaphore(1);
    private int contador;
    
    public String getAnotacion() {
        String texto = anotacion;
        anotacion = "";
        
        return texto;
    }
    
    public void almacenar(double valor) {
        this.valor = valor;
    }

    public void añadir(String texto) throws InterruptedException {
        semaforo.acquire();
        this.anotacion += texto;
        contador++;
        
        if(contador == 4){
            this.anotacion += "\n------------------";
            contador = 0;
        }
        
        semaforo.release();
    }
    
}

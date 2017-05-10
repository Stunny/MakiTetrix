package model;

/**
 * Created by jorti on 10/05/2017.
 */
public class Timer extends Thread {
    private int velocidad;
    private Partida game;
    private boolean running;
    private int tiempo;

    public Timer (Partida game){
        this.game = game;
        velocidad = 1000;
        running = false;
        tiempo = 0;
    }

    public void run() {
        running = true;
        while (running){
            tiempo++;
            //Bajar una posicion de la pantalla
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    public void subirVelocidad (){
        velocidad = velocidad - 50;
    }


}

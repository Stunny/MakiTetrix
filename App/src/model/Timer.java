package model;

import Vista.GameView;

/**
 * Created by jorti on 10/05/2017.
 */
public class Timer extends Thread {
    private int velocidad;
    private Partida game;
    private boolean running;
    private int tiempo;
    private GameView gv;

    public Timer (Partida game, GameView gv){
        this.game = game;
        velocidad = 1000;
        running = false;
        tiempo = 0;
        this.gv = gv;
    }

    public void run() {
        running = true;
        while (running){
            tiempo++;
            game.downABox();
            System.out.println(tiempo);
            gv.printarPantalla(game.getInterfaz());
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

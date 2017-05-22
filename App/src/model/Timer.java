package model;

import Vista.GameView;

/**
 * Created by jorti on 17/05/2017.
 */
public class Timer extends Thread {
    private int tiempo;
    private int espera;
    private GameView gv;
    private Partida game;

    public Timer (GameView gv, Partida game){
        tiempo = 0;
        espera = 1;
        this.gv = gv;
        this.game = game;
    }

    @Override
    public void run (){
        while (true){
            try{
                Thread.sleep(espera);
            } catch (InterruptedException e){
                System.out.println("Tiempo Parado");
                break;
            }
            tiempo++;
            gv.setTemps(tiempo/1000);
            if (game.isEnded()){
                this.interrupt();
            }
        }
    }

    public int getTiempo (){
        return tiempo;
    }

}

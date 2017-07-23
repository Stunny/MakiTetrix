package model;

import Vista.GameView;

/**
 * Cuenta los segundos que dura una partida.
 * Created by Javier Ortiz on 17/05/2017.
 */
public class Crono extends Thread {
    private int tiempo;
    private int espera;
    private GameView gv;
    private Partida game;


    public Crono(GameView gv, Partida game){
        tiempo = 0;
        espera = 1;
        this.gv = gv;
        this.game = game;
    }

    /**
     * Cada milisegundo, envia la información del tiempo a la vista de la partida.
     * Una vez acabada la partida deja de contar.
     * @see Partida#isEnded()
     * @see GameView#setTemps(int)
     */
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

    //Getters && Setter
    /**
     * Devuelve el tiempo
     *
     */
    public int getTiempo (){
        return tiempo;
    }

}

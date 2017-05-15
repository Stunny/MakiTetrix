package model;

import Vista.GameView;

/**
 * Created by jorti on 10/05/2017.
 */
public class Timer extends Thread {
    private int velocidad;
    private Partida game;
    private boolean running;
    private GameView gv;

    public Timer (Partida game, GameView gv){
        this.game = game;
        game.setMasterTimer(this);
        running = false;
        this.gv = gv;
        this.velocidad = 1200;
    }

    public void setVelocidad(int v){
        velocidad = v;
    }


    public void run() {
        running = true;
        while (running){
            game.goDown();
            gv.printarPantalla(game.getInterfaz());
            if (game.hadFloor() && game.getFloortime() == 0){
                game.checkLine();
                gv.printarPantalla(game.getInterfaz());
                game.checkEnd();
                if (!(game.isEnded())){
                    game.chargeNextPiece();
                    gv.printarNextPiece(game.getNextpiece());
                } else {
                    this.interrupt();
                }
            }
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException ie){
                System.out.println("Final del Juego");
                //Hacer lo oportuno para acabar la partida.
            }
        }
    }



}

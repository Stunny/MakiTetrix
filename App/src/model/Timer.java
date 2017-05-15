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
        velocidad = 3000;
        running = false;
        this.gv = gv;
    }

    public void run() {
        running = true;
        while (running){
            game.goDown();
            gv.printarPantalla(game.getInterfaz());
            if (game.hadFloor() && game.getFloortime() == 0){
                game.checkLine();
                gv.printarPantalla(game.getInterfaz());
                game.chargeNextPiece();
                gv.printarNextPiece(game.getNextpiece());
            }
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    public void subirVelocidad (){
        velocidad = velocidad - 75;
    }


}

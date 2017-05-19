package model;

import controller.GameController;

import java.util.Queue;

/**
 * Created by jorti on 10/05/2017.
 */
public class PlayGame extends Thread {
    private int velocidad;
    private Partida game;
    private boolean running;
    private GameController gc;

    public PlayGame(Partida game, GameController gc){
        this.game = game;
        game.setMasterTimer(this);
        running = false;
        this.gc = gc;
        this.velocidad = 1200;
    }

    @Override
    public void run() {
        running = true;
        while (running){
            game.goDown();
            gc.getGV().printarPantalla(game.getInterfaz());
            if (game.hadFloor() && game.getFloortime() == 0){
                game.checkLine();
                gc.getGV().printarPantalla(game.getInterfaz());
                gc.getGV().setNivel(game.getLevel());
                gc.getGV().setPuntuacion(game.getPoints());
                game.checkEnd();
                if (!(game.isEnded())){
                    game.chargeNextPiece();
                    gc.getGV().printarNextPiece(game.getNextpiece());
                    gc.getGV().printarPantalla(game.getInterfaz());
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

    public void run (Queue<Move> toplay){
        running = true;
        int time = 0;
        while ((!toplay.isEmpty())){
            if (time == velocidad) {
                game.goDown();
                gc.getGV().printarPantalla(game.getInterfaz());
                if (game.hadFloor() && game.getFloortime() == 0) {
                    game.checkLine();
                    gc.getGV().printarPantalla(game.getInterfaz());
                    gc.getGV().setNivel(game.getLevel());
                    gc.getGV().setPuntuacion(game.getPoints());
                    game.checkEnd();
                    if (!(game.isEnded())) {
                        game.chargeNextPiece(toplay.peek().getPiece());
                        gc.getGV().printarNextPiece(game.getNextpiece());
                        gc.getGV().printarPantalla(game.getInterfaz());
                    } else {
                        this.interrupt();
                    }
                }
                time = 0;
            } else if (gc.getTimer().getTiempo() == toplay.peek().getTime()) {
                //game.doMove();
            }
            try {
                Thread.sleep(1);
                time++;
            } catch (InterruptedException ie){
                System.out.println("Final del Juego");
                //Hacer lo oportuno para acabar la partida.
            }
        }
    }

    public void setVelocidad(int v){
        velocidad = v;
    }

}

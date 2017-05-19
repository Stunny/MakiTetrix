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
    private Queue<Move> toplay;

    public PlayGame(Partida game, GameController gc){
        this.game = game;
        running = false;
        this.gc = gc;
        this.velocidad = 1200;
    }

    public void setToPlay (Queue<Move> toplay){
        this.toplay = toplay;
    }

    @Override
    public void run (){
        if (toplay == null){
            game();
        } else {
            replay();
        }
    }

    public void game() {
        running = true;
        while (running){
            game.goDown(gc.getTimer().getTiempo());
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
                game.saveGame();
            }
        }
    }

    public void replay (){
        int time = 0;
        Pieza actual = toplay.peek().getPiece();
        toplay.poll();
        Pieza siguiente = toplay.peek().getPiece();
        toplay.poll();
        game.newGame(actual, siguiente);
        while ((!toplay.isEmpty())){
           switch (toplay.peek().getOption()){
               case Move.PIECE:
                   game.chargeNextPiece(toplay.peek().getPiece());
                   gc.getGV().printarNextPiece(game.getNextpiece());
                   gc.getGV().printarPantalla(game.getInterfaz());
                   break;
               case Move.MOVE:
                   if (time <= toplay.peek().getTime()){
                       System.out.println(toplay.peek().toString());
                       game.doMove(toplay.peek().getMove());
                       gc.getGV().printarPantalla(game.getInterfaz());
                   }
                   break;
           }
           toplay.poll();
            try {
                Thread.sleep(100);
                time++;
            } catch (InterruptedException ie){
                System.out.println("Final del Juego");
                game.saveGame();
            }
        }
    }

    public void setVelocidad(int v){
        velocidad = v;
    }

    public int getVelocidad (){ return velocidad;}

}

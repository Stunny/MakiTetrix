package model;

import controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

/**
 * Created by Javier Ortiz on 10/05/2017.
 * Thread que permite reproducir una partida por pantalla y atraves del tiempo.
 * Permite que se pueda jugar a través del usuario o ver un repetición.
 * @see Partida
 * @see GameController
 * @see Move
 */
public class PlayGame extends Thread {
    private int velocidad;
    private Partida game;
    private boolean running;
    private GameController gc;
    private Queue<Move> toplay;
    private Timer timer;
    private static boolean direct;
    private boolean newMove;
    private Move move;

    /**
     * Inicializa una nueva partida.
     * @param game      Información de la partida.
     * @param gc        Permite comunicarse que el resto del programa.
     */
    public PlayGame(Partida game, GameController gc){
        this.game = game;
        running = false;
        this.gc = gc;
        this.velocidad = 1200;
        newMove = false;
        move = null;
    }

    /**
     * Si la lista de Moves no esat vacia permite al jugador hechar una partida.
     * En caso contrario reproduce la repetición.
     */
    @Override
    public void run (){
        if (toplay == null) {
            game();
        } else {
            System.out.println("REPLAY");
            replay();
        }
    }

    /**
     * Permite al usuario jugar. Por cada iteración del bucle, baja una posición la pieza,
     * comprueba si esta esta tocado un "suelo" y también si la partida ha acbado.
     * Crea y carga las nuevas piezas en el momento que sea necesario.
     * Va actualizado lo que se muestra por pantalla durate la partida.
     */
    public void game() {
        running = true;
        while (running){
            game.goDown(gc.getTimer().getTiempo());
            gc.getGV().setNespectadors(game.getNespectadors());

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
                gc.endGame();
            }
            //game.sendTime(gc.getTimer().getTiempo());
        }
    }

    /**
     * A partir de los Moves que hay en la cola reproduce una partida jugada con anterioridad.
     * @see Move
     */
    public void replay (){
        Pieza actual = toplay.peek().getPiece();
        toplay.poll();
        Pieza siguiente = toplay.peek().getPiece();
        toplay.poll();
        game.newGame(actual, siguiente);
        gc.getGV().printarNextPiece(game.getNextpiece());
        gc.getGV().printarPantalla(game.getInterfaz());
        timer = new Timer(velocidad, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int time = 0;
                if (toplay.size() == 0 && direct){
                    timer.stop();
                    gc.startDirect();
                    return;
                }
                switch (toplay.peek().getOption()){
                    case Move.PIECE:
                        game.chargeNextPiece(toplay.peek().getPiece());
                        game.checkLine();
                        gc.getGV().setNivel(game.getLevel());
                        gc.getGV().setPuntuacion(game.getPoints());
                        gc.getGV().printarNextPiece(game.getNextpiece());
                        gc.getGV().printarPantalla(game.getInterfaz());
                        break;
                    case Move.MOVE:
                        game.doMove(toplay.peek().getMove());
                        gc.getGV().printarPantalla(game.getInterfaz());
                        gc.getGV().setTemps(toplay.peek().getTime()/1000);
                        break;
                }

                if (toplay.peek().getTime() != 0){
                    time = toplay.peek().getTime();
                }
                toplay.poll();
                try {
                    if (time != 0 && !direct) {
                        timer.setDelay(toplay.peek().getTime() - time);
                    } else {
                        timer.setDelay(0);
                    }
                } catch (IllegalArgumentException iae){
                    timer.setDelay(1);
                    System.out.println("Cargando pieza");
                } catch (NullPointerException npe){
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void setVelocidad(int v){
        velocidad = v;
    }

    public int getVelocidad (){ return velocidad;}

    public void setToPlay (Queue<Move> toplay){
        this.toplay = toplay;
    }

    public static void setDirect (boolean di) {direct = di;}

    public void setPiece (Move move) {
        this.move = move;
        this.newMove = true;
    }


    public void setRunning(Boolean running) {
        this.running = running;
    }
}

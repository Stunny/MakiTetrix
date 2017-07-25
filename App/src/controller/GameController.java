package controller;

import Vista.GameView;
import Vista.MainMenuView;
import model.Crono;
import model.Move;
import model.Partida;
import model.PlayGame;
import network.Conexio;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jorti on 09/05/2017.
 */
public class GameController implements KeyListener {
    private GameView gv;
    private Partida game;
    private PlayGame pg;
    private Crono t;
    private static int[] teclas  = new int[]{65,83,68,81,69,80};
    private boolean stopgame;
    private Conexio conexio;
    private MainMenuView mmv;
    private boolean endRepetetion;
    private Timer timer;

    public GameController(GameView gv, Partida game, Conexio conexio, MainMenuView mmv){
        this.gv = gv;
        this.game = game;
        gv.addKeyListener(this);
        t = new Crono(gv,game);
        this.conexio = conexio;
        this.mmv = mmv;
        gv.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                endGame();
                e.getWindow().dispose();
            }
        });
        endRepetetion = false;
    }

    /**
     * Inicializa Partida para empezar un partida.
     */
    public void startGame(){
        stopgame = false;
        game.newGame();
        gv.printarPantalla(game.getInterfaz());
        gv.printarNextPiece(game.getNextpiece());
        stopgame = false;
    }

    /**
     * Activa el Thread para poder jugar una partida
     */
    public void playGame () {
        pg = new PlayGame(game,this);
        pg.start();
        t.start();
    }

    /**
     * Prepara un repetición para ser jugada
     * @param replay        Array de Move (repeticion)
     * @see Move
     */
    public void readyReplay (ArrayList<String > replay) {
        game = new Partida(conexio);
        pg = new PlayGame(game, this);
        pg.setToPlay(toQueue(replay));
    }

    /**
     * Da visto bueno al thread para empezar un repetición
     */
    public void startReplay () {
        pg.run();
    }

    public void readyDirect (){
        game = new Partida(conexio);
        stopgame = true;
    }

    /**
     * Pasa un array de string a un array de Move
     * @param replay        Array de String
     * @return              Array de Move
     * @see Move
     */
    private Queue<Move> toQueue (ArrayList<String> replay){
        Queue<Move> toreplay = new LinkedList<>();
        for (int i = 0; i < replay.size(); i++){
            toreplay.add(new Move (replay.get(i)));
        }
        return toreplay;
    }

    /**
     * Procedimientos que se deben ejecutar cuando se acaba la partida.
     */
    public void endGame (){
        if (!stopgame) {
            pg.setRunning(false);
            if (gv.saveGame() == 0) {
                game.saveGame();
                conexio.saveGameData(game.getPoints(), t.getTiempo(),
                        game.getDate() + ".txt");
                conexio.sendReplay(game.getDate() + ".txt");
            } else {
                System.out.println("test");

            }
            conexio.sendEndGame();
        }
        gv.setVisible(false);
        mmv.setVisible(true);
    }

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == teclas[0]){
            if (!stopgame) {
                game.goLeft(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
            }
        } else if (e.getKeyCode() == teclas[1]){
            if (!stopgame) {
                game.goDown(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
            }
        } else if (e.getKeyCode() == teclas[2]){
            if (!stopgame) {
                game.goRight(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
            }
        } else if (e.getKeyCode() == teclas[3]){
            if (!stopgame) {
                game.rotateLeft(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
            }
        } else if (e.getKeyCode() == teclas[4]){
            if (!stopgame) {
                game.rotateRight(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
            }
        } else if (e.getKeyCode() == teclas[5]){
            if (!stopgame) {
                stopgame = true;
                pg.suspend();
                t.suspend();
                if (gv.pauseGame() == 0) {
                    stopgame = false;
                    pg.resume();
                    t.resume();
                }
            }
        } else {
            System.out.println("Tecla no válida.");
        }
    }

    public void setPGDirect (boolean bol){
        PlayGame.setDirect(bol);
    }

    @Override
    public void keyReleased(KeyEvent e){
    }

    @Override
    public void keyTyped (KeyEvent e){

    }

    //Getters && Setters

    public static void setTeclas (int right, int left, int down, int rotateright, int rotateleft, int pause){
        teclas[0] = left;
        teclas[1] = down;
        teclas[2] = right;
        teclas[3] = rotateleft;
        teclas[4] = rotateright;
        teclas[5] = pause;
    }

    public Crono getTimer () {
        return t;
    }

    public GameView getGV (){
        return gv;
    }

    public void setStopGame (boolean b) {stopgame = b;}

    public PlayGame getPG() {
        return pg;
    }

    public boolean endRepetetion() {
        return endRepetetion;
    }

    public void setEndRepetetion (boolean end){
        endRepetetion = end;
    }

    /**
     * Tras carga su historia empieza a reproduccir una partida en directo.
     */
    public void startDirect() {
        gv.setVisible(true);
        System.out.println("EMPIEZA EL DIRECTO");

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entro al Action Event");
                int time = 0;
                String aux = conexio.readMove();
                System.out.println("MOVEDIRECT:" + aux);
                if (aux.equals("end")){
                    PlayGame.setDirect(false);
                    conexio.disconnect();
                    timer.stop();
                    return;
                }
                Move m = new Move(aux);
                switch (m.getOption()){
                    case Move.PIECE:
                        game.chargeNextPiece(m.getPiece());
                        gv.printarNextPiece(game.getNextpiece());
                        gv.printarPantalla(game.getInterfaz());
                        break;
                    case Move.MOVE:
                        game.doMove(m.getMove());
                        gv.printarPantalla(game.getInterfaz());
                        break;
                }
                game.checkLine();
                gv.printarPantalla(game.getInterfaz());
                gv.setNivel(game.getLevel());
                gv.setPuntuacion(game.getPoints());
                if (time != 0) {
                    timer.setDelay(m.getTime() - time);
                } else {
                    timer.setDelay(0);
                }

            }
        });
        timer.start();
    }
}

package controller;

import Vista.GameView;
import Vista.MainMenuView;
import model.Crono;
import model.Move;
import model.Partida;
import model.PlayGame;
import network.Conexio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                e.getWindow().dispose();
            }
        });
    }

    public void startGame(){
        game.newGame();
        gv.printarPantalla(game.getInterfaz());
        gv.printarNextPiece(game.getNextpiece());
        stopgame = false;
    }

    public void playGame () {
        pg = new PlayGame(game,this);
        pg.start();
        t.start();
    }

    public void startReplay (ArrayList<String > replay) {
        pg = new PlayGame(game, this);
        pg.setToPlay(toQueue(replay));
        pg.run();

    }

    private Queue<Move> toQueue (ArrayList<String> replay){
        Queue<Move> toreplay = new LinkedList<>();
        for (int i = 0; i < replay.size(); i++){
            toreplay.add(new Move (replay.get(i)));
        }
        return toreplay;
    }

    public void endGame (){

        new WindowEvent (gv, WindowEvent.WINDOW_CLOSED);
        stopgame = true;
        if (gv.saveGame() == 0){
            game.saveGame();
            conexio.saveGameData(game.getPoints(), t.getTiempo(),0,
                    game.getDate() + ".txt");
            conexio.sendReplay(game.getDate() + ".txt");
        }else{
            System.out.println("test");
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

}

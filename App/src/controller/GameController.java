package controller;

import Vista.GameView;
import model.*;
import network.Conexio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jorti on 09/05/2017.
 */
public class GameController implements KeyListener {
    private GameView gv;
    private Partida game;
    private PlayGame pg;
    private Timer t;
    private static int[] teclas  = new int[]{65,83,68,81,69,80};
    private boolean stopgame;
    private Conexio conexio;

    public GameController(GameView gv, Partida game, Conexio conexio){
        this.gv = gv;
        this.game = game;
        gv.addKeyListener(this);
        t = new Timer(gv,game);
        this.conexio = conexio;
        gv.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                endGame();
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

    public void startReplay (String file) {
        pg = new PlayGame(game, this);
        pg.setToPlay(toQueue(file));
        pg.run();

    }

    private Queue<Move> toQueue (String file){
        Queue<Move> toreplay = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String aux;
            while((aux = br.readLine()) != null) {
                String value[] = aux.split(",");
                switch (Integer.parseInt(value[0])){
                    case Move.MOVE:
                        toreplay.add(new Move(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                        break;
                    case Move.PIECE:
                        toreplay.add(new Move (new Pieza(Integer.parseInt(value[1]))));
                        break;
                }
            }
            br.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return toreplay;
    }

    public void endGame (){
        new  WindowEvent (gv, WindowEvent.WINDOW_CLOSED);
        conexio.sendEndGame();
        stopgame = true;
        if (gv.saveGame() == 0){
           // conexio.saveGameData(userName, game.getPoints(), t.getTiempo(), game.getDate(),
                  //  max_espectators, game.getDate()+".txt");
            game.saveGame();
        }
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
            stopgame = true;
            pg.suspend();
            t.suspend();
            if (gv.pauseGame() == 0){
                stopgame = false;
                pg.resume();
                t.resume();
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

    public Timer getTimer () {
        return t;
    }

    public GameView getGV (){
        return gv;
    }

}

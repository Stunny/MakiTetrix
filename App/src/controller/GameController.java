package controller;

import Vista.GameView;
import model.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private int[] teclas;

    public GameController (GameView gv, Partida game){
        this.gv = gv;
        this.game = game;
        gv.addKeyListener(this);
        t = new Timer(gv,game);
        teclas = new int[]{65,83,68,81,69,80};
    }

    public void startGame (){
        game.newGame();
        gv.printarPantalla(game.getInterfaz());
        gv.printarNextPiece(game.getNextpiece());
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
        Queue<Move> toreplay = new LinkedList<Move>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String aux;
            while((aux = br.readLine())!=null) {
                String value[] = aux.split(",");
                switch (Integer.parseInt(value[0])){
                    case Move.MOVE:
                        toreplay.add(new Move(Integer.parseInt(value[1]),Integer.parseInt(value[2])));
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

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == teclas[0]){
            game.goLeft(t.getTiempo());
            gv.printarPantalla(game.getInterfaz());
        } else if (e.getKeyCode() == teclas[1]){
            game.goDown(t.getTiempo());
            gv.printarPantalla(game.getInterfaz());
        } else if (e.getKeyCode() == teclas[2]){
            game.goRight(t.getTiempo());
            gv.printarPantalla(game.getInterfaz());
        } else if (e.getKeyCode() == teclas[3]){
            game.rotateLeft(t.getTiempo());
            gv.printarPantalla(game.getInterfaz());
        } else if (e.getKeyCode() == teclas[4]){
            game.rotateRight(t.getTiempo());
            gv.printarPantalla(game.getInterfaz());
        } else if (e.getKeyCode() == teclas[5]){
            //TODO: PAUSA DEL JUEGO
        } else {
            System.out.println("Tecla no válida.");
        }
    }


    @Override()
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode()==83){
        }
    }
    @Override
    public void keyTyped (KeyEvent e){

    }

    //Getters && Setters

    private void setTeclas (int right, int left, int down, int rotateleft, int rotateright, int pause){
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

    public PlayGame getPG (){return pg;}
}

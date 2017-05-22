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

    public GameController (GameView gv, Partida game){
        this.gv = gv;
        this.game = game;
        gv.addKeyListener(this);
        t = new Timer(gv,game);
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

    private final int LEFT = 65;
    private final int RIGHT = 68;
    private final int DOWN = 83;
    private final int ROTATE_LEFT = 83;
    private final int ROTATE_RIGHT = 83;

    //TODO: IMPLEMENTAR LA TECLA DE PAUSAR EL JUEGO

    @Override
    public void keyPressed (KeyEvent e){
        switch (e.getKeyCode()){
            case 65:
                game.goLeft(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
                break;
            case 83:
                game.goDown(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
                break;
            case 68:
                game.goRight(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
                break;
            case 81:
                game.rotateLeft(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
                break;
            case 69:
                game.rotateRight(t.getTiempo());
                gv.printarPantalla(game.getInterfaz());
                break;
            case 80:
                //TODO: PAUSAR EL JUEGO
            default:
                System.out.println("Tecla no válida");
                break;
        }
    }

    private void keyActions(int right, int left, int down, int rotateLeft, int rotateRight){

    }


    @Override()
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode()==83){
        }
    }
    @Override
    public void keyTyped (KeyEvent e){

    }



    public Timer getTimer () {
        return t;
    }

    public GameView getGV (){
        return gv;
    }

    public PlayGame getPG (){return pg;}
}

package controller;

import Vista.GameView;
import model.Partida;
import model.PlayGame;
import model.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by jorti on 09/05/2017.
 */
public class GameController implements KeyListener {
    private GameView gv;
    private Partida game;
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
        PlayGame pg = new PlayGame(game,this);
        pg.start();
        t.start();
    }
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
            default:
                System.out.println("Tecla no válida");
                break;
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

    public Timer getTimer () {
        return t;
    }

    public GameView getGV (){
        return gv;
    }
}

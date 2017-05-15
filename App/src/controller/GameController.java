package controller;

import Vista.GameView;
import model.Partida;
import model.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by jorti on 09/05/2017.
 */
public class GameController implements KeyListener {
    private GameView gv;
    private Partida game;

    public GameController (GameView gv, Partida game){
        this.gv = gv;
        this.game = game;
        gv.addKeyListener(this);
    }

    public void startGame (){
        game.newGame();
        gv.printarPantalla(game.getInterfaz());
        gv.printarNextPiece(game.getNextpiece());
    }

    public void playGame () {
        Timer t = new Timer (game,gv);
        t.start();
    }
    @Override
    public void keyPressed (KeyEvent e){
        switch (e.getKeyCode()){
            case 65:
                game.goLeft();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 83:
                game.goDown();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 68:
                game.goRight();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 81:
                game.rotateLeft();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 69:
                game.rotateRight();
                gv.printarPantalla(game.getInterfaz());
                break;
            default:
                System.out.println("Tecla no válida");
                break;
        }
    }
    @Override()
    public void keyReleased(KeyEvent e){

    }
    @Override
    public void keyTyped (KeyEvent e){

    }
}

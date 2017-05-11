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
        game.startGame();
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
                System.out.println("Izquierda");
                game.leftBox();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 83:
                System.out.println("Abajo");
                game.downABox();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 68:
                System.out.println("Derecha");
                game.rigthBox();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 81:
                System.out.println("Rotar izquierda");
                game.rotateLeft();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 69:
                System.out.println("Rotar derecha");
                game.rotateRigth();
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

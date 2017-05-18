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
        PlayGame pg = new PlayGame(game,gv);
        Timer t = new Timer(gv, game);
        pg.start();
        t.start();
    }
    @Override
    public void keyPressed (KeyEvent e){
        switch (e.getKeyCode()){
            case 65:
                //System.out.println("leeeft");
                game.goLeft();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 83:
                //System.out.println("s");
                game.goDown();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 68:
                //System.out.println("d");
                game.goRight();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 81:
                //System.out.println("q");
                game.rotateLeft();
                gv.printarPantalla(game.getInterfaz());
                break;
            case 69:
                //System.out.println("e");
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
        if (e.getKeyCode()==83){
            game.reduceVelocidad();
        }
    }
    @Override
    public void keyTyped (KeyEvent e){

    }
}

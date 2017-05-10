package controller;

import Vista.GameView;
import model.Partida;

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
    @Override
    public void keyPressed (KeyEvent e){
        switch (e.getKeyCode()){
            case 65:
                System.out.println("Izquierda");
                break;
            case 83:
                System.out.println("Abajo");
                break;
            case 68:
                System.out.println("Derecha");
                break;
            case 81:
                System.out.println("Rotar derecha");
                break;
            case 69:
                System.out.println("Rotar Izq");
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

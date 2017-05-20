package controller;

import Vista.GameView;
import Vista.MainMenuView;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.Partida;
import org.omg.SendingContext.RunTime;
import sun.applet.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Created by avoge on 04/04/2017.
 */
public class MenuController implements ActionListener {
    private MainMenuView mmv;

    public MenuController(MainMenuView mmv){
        this.mmv = mmv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameView gameView = new GameView();
        Partida partida = new Partida();
        GameController gameController = new GameController(gameView, partida);
        switch (e.getActionCommand()){
            case "teclas":
                //Assignar Teclas
                System.out.println("asignar teclas");
                break;
            case "jugar":
                GameView game = new GameView();
                game.setVisible(true);
                GameController asdf = new GameController(game,partida);
                asdf.startGame();
                asdf.playGame();
                mmv.setVisible(false);
                //Permite Jugar
                break;
            case "ver":
                //Ver Partida en vivo
                break;
            case "anterior":
                //Reproducir anterior
                break;
            case "salir":
                System.exit(1);
                //Salir
                break;
        }
    }
}

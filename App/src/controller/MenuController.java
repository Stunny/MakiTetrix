package controller;

import Vista.*;
import model.Partida;
import model.User;
import network.Conexio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by avoge on 04/04/2017.
 */
public class MenuController extends WindowAdapter implements ActionListener {
    private MainMenuView mmv;
    private Conexio conexio;
    private User currentUser;

    public MenuController(MainMenuView mmv, Conexio conexio, User currentUser){
        this.mmv = mmv;
        this.conexio = conexio;
        this.currentUser = currentUser;
        mmv.getUsuario().setText(currentUser.getUserName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameView gameView = new GameView();
        Partida partida = new Partida(conexio);
        GameController gameController = new GameController(gameView, partida);
        switch (e.getActionCommand()){
            case "teclas":
                //modifica los valores de las teclasa gusto del usuario
                KeySelectMenu keyView = new KeySelectMenu();
                KeySelectMenuController keyContoller = new KeySelectMenuController(keyView, currentUser);
                keyView.registerKey(keyContoller);
                keyView.setVisible(true);
                break;
            case "jugar":
                //Permite Jugar
                gameView.setVisible(true);
                gameController.startGame();
                gameController.playGame();
                conexio.setGaming(currentUser.getUserName(), true);
                break;
            case "ver":
                //Ver Partida en vivo
                EspectatorView espectatorView = new EspectatorView();
                EspectatorController espectatorController = new EspectatorController(espectatorView, conexio, currentUser.getUserName());
                espectatorView.registerEspectator(espectatorController);
                espectatorView.setVisible(true);
                break;
            case "anterior":
                //Reproducir anterior
                ReplaySelectView replay = new ReplaySelectView();
                ReplaySelectController replayController = new ReplaySelectController(replay, conexio, currentUser.getUserName());
                replay.registerReplay(replayController);
                replay.setVisible(true);
                break;
            case "salir":
                conexio.disconnectUser(currentUser);
                System.exit(1);
                break;
        }
    }

    public void windowClosing(WindowEvent evt){
        conexio.disconnectUser(currentUser);
        super.windowClosing(evt);
    }
}

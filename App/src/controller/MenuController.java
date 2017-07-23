package controller;

import Vista.*;
import model.Partida;
import model.User;
import network.Conexio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by avoge on 04/04/2017.
 */
public class MenuController extends WindowAdapter implements ActionListener {
    private MainMenuView mmv;
    private Conexio conexio;
    private User currentUser;
    private ReplaySelectController replayController;
    private Timer timer;

    MenuController(MainMenuView mmv, Conexio conexio, User currentUser){
        this.mmv = mmv;
        this.conexio = conexio;
        this.currentUser = currentUser;
        mmv.getUsuario().setText(currentUser.getUserName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameView gameView = new GameView();
        Partida partida = new Partida(conexio);
        GameController gameController = new GameController(gameView, partida, conexio, mmv);
        conexio.setCurrentUser(currentUser);

        switch (e.getActionCommand()){
            case "teclas":
                //modifica los valores de las teclasa gusto del usuario
                ArrayList<Integer> tecles = conexio.getTeclesUser(currentUser.getUserName());

                KeySelectMenu keyView = new KeySelectMenu();
                if (tecles.size()>0){

                    keyView.setTextDerecha(tecles.get(0));
                    keyView.setTextIzquierda(tecles.get(1));
                    keyView.setTextAbajo(tecles.get(2));
                    keyView.setTextRotarDerecha(tecles.get(3));
                    keyView.setTextRotarIzquierda(tecles.get(4));
                    keyView.setTextPause(tecles.get(5));
                }

                KeySelectMenuController keyContoller = new KeySelectMenuController(conexio, keyView, currentUser);

                keyView.registerKey(keyContoller);
                keyView.setVisible(true);
                break;

            case "jugar":
                ArrayList<Integer> t = conexio.gameStartParameters(currentUser.getUserName(), true);
                if (t.size() > 0) {
                    GameController.setTeclas(t.get(0), t.get(1), t.get(2), t.get(3), t.get(4), t.get(5));
                }
                //Permite Jugar
                gameView.setVisible(true);
                gameController.startGame();
                gameController.playGame();
                mmv.setVisible(false);
                break;

            case "ver":
                //Ver Partida en vivo
                EspectatorView espectatorView = new EspectatorView();
                EspectatorController espectatorController = new EspectatorController(espectatorView,
                        conexio, currentUser.getUserName());
                espectatorView.registerEspectator(espectatorController);
                espectatorView.setVisible(true);
                break;

            case "anterior":
                //Reproducir anterior
                ReplaySelectView replay = new ReplaySelectView();
                replayController = new ReplaySelectController(replay, conexio,
                        currentUser.getUserName());
                replay.registerReplay(replayController);
                replay.setVisible(true);
                timer = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (replayController.getReplay().size() != 0){
                            timer.stop();
                        }
                    }
                });
                timer.start();
                gameController.setStopGame(true);
                while(replayController.getReplay() != null){
                    gameView.setVisible(true);
                    gameController.startReplay(replayController.getReplay());
                }
                break;

            case "salir":
                conexio.disconnectUser(currentUser);
                System.exit(1);
                break;
        }
    }

    public void fiPartida(){

    }

    /**
     * Notifies user has disconected (closed menu window)
     * @param evt Close event
     */
    public void windowClosing(WindowEvent evt){
        conexio.disconnectUser(currentUser);
        super.windowClosing(evt);
    }
}

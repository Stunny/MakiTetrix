package controller;

import Vista.*;
import model.Partida;
import model.User;
import network.Conexio;
import network.ThreadClientPasiu;
import network.ThreadClientPasiuDedicat;

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
        conexio.setCurrentUser(currentUser);

        //lanzamos el nuevo thread donde el servidor sera activo y el cliente el pasivo
        //ThreadClientPasiu threadClientPasiu = new ThreadClientPasiu(currentUser.getUserName(), conexio.getTime());

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

                ArrayList<Integer> t = conexio.getTeclesUser(currentUser.getUserName());
                if (t.size()>0) {
                    GameController.setTeclas(t.get(0), t.get(1), t.get(2), t.get(3), t.get(4), t.get(5));
                }
                //Permite Jugar
                //threadClientPasiu.start();
                gameView.setVisible(true);
                gameController.startGame();
                gameController.playGame();
                //threadClientPasiu.interrupt();
                System.out.println("set gaming");
                conexio.setGaming(currentUser.getUserName(), true);
                break;
            case "ver":
                //Ver Partida en vivo
                //threadClientPasiu.start();
                EspectatorView espectatorView = new EspectatorView();
                EspectatorController espectatorController = new EspectatorController(espectatorView, conexio, currentUser.getUserName());
                espectatorView.registerEspectator(espectatorController);
                espectatorView.setVisible(true);
                //threadClientPasiu.interrupt();
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

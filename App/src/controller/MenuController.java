package controller;

import Vista.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.Partida;
import network.ThreadSocketClient;
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
    private ThreadSocketClient tsc;

    public MenuController(MainMenuView mmv, ThreadSocketClient tsc){
        this.mmv = mmv;
        this.tsc = tsc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameView gameView = new GameView();
        Partida partida = new Partida();
        GameController gameController = new GameController(gameView, partida);
        switch (e.getActionCommand()){
            case "teclas":
                //modifica los valores de las teclasa gusto del usuario
                KeySelectMenu keyView = new KeySelectMenu();
                KeySelectMenuController keyContoller = new KeySelectMenuController(keyView);
                keyView.registerKey(keyContoller);
                keyView.setVisible(true);
                break;
            case "jugar":
                //Permite Jugar
                gameView.setVisible(true);
                GameController asdf = new GameController(gameView, partida);
                asdf.startGame();
                asdf.playGame();
                break;
            case "ver":
                //TODO: IMPLEMENTAR EN LA NUEVA VISTA LAS PARTIDAS EN DIRECTO ORDENADAS POR VIEWERS
                //Ver Partida en vivo
                EspectatorView espectatorView = new EspectatorView();
                EspectatorController espectatorController = new EspectatorController(espectatorView, tsc);
                espectatorView.registerEspectator(espectatorController);
                espectatorView.setVisible(true);
                break;
            case "anterior":
                //TODO: IMPLEMENTAR EN LA NUEVA VISTA UN HISTORIAL DE PARTIDAS GUARDADAS CON LA FECHA, LA PUNTUACION,
                //TODO: TIEMPO Y PICO MAXIMO DE ESPECTADORES
                //Reproducir anterior
                ReplaySelectView replay = new ReplaySelectView();
                ReplaySelectController replayController = new ReplaySelectController(replay);
                replay.registerReplay(replayController);
                replay.setVisible(true);
                break;
            case "salir":
                System.exit(1);
                break;
        }
    }
}

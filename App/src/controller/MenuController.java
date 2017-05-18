package controller;

import Vista.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        switch (e.getActionCommand()){
            case "teclas":
                //Assignar Teclas
                break;
            case "jugar":
                //Permite Jugar
                break;
            case "ver":
                //Ver Partida
                break;
            case "anterior":
                //Reproducir anterior
                break;
            case "salir":
                //Salir
                break;
        }
    }
}

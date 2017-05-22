package controller;

import Vista.GameView;
import Vista.KeySelectMenu;
import model.Partida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by natal on 20/5/2017.
 */
public class KeySelectMenuController implements ActionListener {
    private KeySelectMenu key;

    public KeySelectMenuController(KeySelectMenu key) {
        this.key = key;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if (e.getActionCommand().equals("save")){
            //TODO: GUARDAR LOS CAMBIOS EN LA CONFIGURACION DE LAS TECLAS DE USUARIO

            key.setVisible(false);
        }
    }
}

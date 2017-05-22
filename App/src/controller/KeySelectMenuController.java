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
    private KeySelectMenu view;

    public KeySelectMenuController(KeySelectMenu view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("save")){
            //TODO: GUARDAR LOS CAMBIOS EN LA CONFIGURACION DE LAS TECLAS DE USUARIO
            if (checkValues()){
                view.setVisible(false);
            }
        }
    }

    private boolean checkValues() {
        if (checkKey(view.getTextDerecha().getText()) && checkKey(view.getTextIzquierda().getText())
                && checkKey(view.getTextAbajo().getText()) && checkKey(view.getTextPause().getText())
                && checkKey(view.getTextRotarDerecha().getText()) && checkKey(view.getTextRotarIzquierda().getText())){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkKey(String key){
        if (key.length() != 1){
            view.showKeySelectError("EL valor " + key + " debe ser un solo caracter.");
            return false;
        }else{
            int aux = String.valueOf(key).codePointAt(0);
            if (aux < 65 || aux > 90){
                view.showKeySelectError("EL valor " + key + " debe estar comprendido entre la A y la Z.");
                return false;
            }else{
                return true;
            }
        }
    }
}

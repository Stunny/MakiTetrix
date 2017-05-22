package controller;

import Vista.EspectatorView;
import model.LiveUser;
import model.User;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by natal on 20/5/2017.
 */
public class EspectatorController implements MouseListener {
    private static EspectatorView espectatorView;

    public EspectatorController(EspectatorView espectatorView){
        this.espectatorView = espectatorView;
        ompleLlistaUsuaris();
    }


    public void ompleLlistaUsuaris(){
        //TODO: LLENAR UNA ARRAYLIST DE LiveUser Y LLENAR LA LISTA DE LA VISTA

        //codigo para testear que el usuario "angel" sale por pantalla. Es correcto
        LiveUser liveUser = new LiveUser("angel", 15);

        ArrayList<String> usuaris = new ArrayList<>();
        usuaris.add(liveUser.getUserName());
        usuaris.add(String.valueOf(liveUser.getEspectators()));

        for (int i = 0; i < usuaris.size(); i++){
            espectatorView.getModel().addElement(usuaris.get(i));
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList)e.getSource();
        //han fet doble click
        if (e.getClickCount() == 2) {
            System.out.println("Quiero ver esta partida: " + list.getSelectedValue().toString());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

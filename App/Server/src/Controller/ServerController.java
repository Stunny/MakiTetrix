package Controller;

import Model.GestioDades;
import Network.ThreadSocket;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Admin on 21/03/2017.
 */

public class ServerController implements ActionListener, MouseListener {
    private static View view;
    private GestioDades gestioDades;
    private ThreadSocket ts;
    private ArrayList<String> usuaris;

    public ServerController(View view, GestioDades gestioDades, ThreadSocket threadSocket) {
        this.view = view;
        this.gestioDades = gestioDades;
        this.ts = threadSocket;
        usuaris = gestioDades.plenaUsuaris();
        ompleUsuaris();
        startThread();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Estic fent aquesta accio: " + e.getActionCommand());
        if (e.getActionCommand().equals(view.ACTION_BORRAR)){
           // gestioDades.borraUsuari(usuaris[view.getLeftList().getSelectedIndex()]);
        }
    }

    public void ompleUsuaris(){
        ArrayList<String> usuaris = gestioDades.plenaUsuaris();
        for (int i = 0; i < usuaris.size(); i++){
            view.getModel().addElement(usuaris.get(i));
        }
    }

    public void startThread() {
        if (ts == null || !ts.isAlive()) {
            //aqui comence thread
            ts = new ThreadSocket();
            ts.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList)e.getSource();
        //han fet doble click
        if (e.getClickCount() == 2) {
            System.out.println("Estic selecionant l'usuari amb l'index: " + list.getSelectedIndex());
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
package Controller;

import Model.GestioDades;
import Network.ThreadSocket;
import View.View;

import javax.swing.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Created by Admin on 21/03/2017.
 */

public class ServerController implements ActionListener, MouseListener {
    private static View view;
    private GestioDades gestioDades;
    private ThreadSocket ts;
    private String[] usuaris;

    public ServerController(View view, GestioDades gestioDades, ThreadSocket threadSocket) {
        this.view = view;
        this.gestioDades = gestioDades;
        this.ts = threadSocket;
        usuaris = gestioDades.plenaUsuaris();
        ompleUsuaris();
        //usuariSeleccionat();
        startThread();
    }

    private void usuariSeleccionat() {
        boolean flag = true;
        int aux = view.getIndexSelectedUser();

        while (flag){
            if (aux != view.getIndexSelectedUser()){

                flag = false;
            }
        }
        gestioDades.mostraDades(usuaris[view.getLeftList().getSelectedIndex()]);
        flag = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Estic fent aquesta accio: " + e.getActionCommand());
        if (e.getActionCommand().equals(view.ACTION_BORRAR)){
            gestioDades.borraUsuari(usuaris[view.getLeftList().getSelectedIndex()]);
        }
    }

    public void ompleUsuaris(){
        String [] usuaris = gestioDades.plenaUsuaris();
        for (int i = 0; i < usuaris.length; i++){
            view.getModel().addElement(usuaris[i]);
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
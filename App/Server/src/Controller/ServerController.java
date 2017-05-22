package Controller;

import Model.GestioDades;
import Network.ThreadSocketServer;
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
    private ThreadSocketServer ts;

    public ServerController(View view, GestioDades gestioDades, ThreadSocketServer threadSocketServer) {
        this.view = view;
        this.gestioDades = gestioDades;
        this.ts = threadSocketServer;
        ompleUsuaris();
        startThread();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.ACTION_BORRAR)){
           // gestioDades.borraUsuari(usuaris[view.getLeftList().getSelectedIndex()]);
        }else if (e.getActionCommand().equals(view.ACTION_SEARCH)){
            ArrayList<String> usuaris =  gestioDades.busca(view.getBuscador().getText());
            view.getModel().clear();
            for (int i = 0; i < usuaris.size(); i++){
                view.getModel().addElement(usuaris.get(i));
            }        }
    }

    /**
     * Fills the view with the users' data
     */
    public void ompleUsuaris(){
        ArrayList<String> usuaris = gestioDades.plenaUsuaris();
        for (int i = 0; i < usuaris.size(); i++){
            view.getModel().addElement(usuaris.get(i));
        }
    }

    public void startThread() {
        if (ts == null || !ts.isAlive()) {
            //aqui comence thread
            ts = new ThreadSocketServer();
            ts.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList)e.getSource();
        //han fet doble click
        if (e.getClickCount() == 2) {
            gestioDades.mostraDades(list.getSelectedValue().toString());
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
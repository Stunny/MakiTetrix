package Controller;

import Model.GestioDades;
import Model.User;
import Network.ThreadSocketServer;
import View.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

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
            //DefaultTableModel model = (DefaultTableModel) View.getTable().getModel();

            /*
            view.getModel().clear();
            for (int i = 0; i < usuaris.size(); i++){
                view.getModel().addElement(usuaris.get(i));
            }
                    */
        }else if (e.getActionCommand().equals(view.UPDATE)){
            ompleUsuaris();
        }
    }

    /**
     * Fills the view with the users' data
     */
    public void ompleUsuaris(){
        ArrayList<String> usuaris = gestioDades.plenaUsuaris();
        DefaultTableModel model = (DefaultTableModel) view.getLeftTable().getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Name");
        for (int i = 0; i < usuaris.size(); i++){
            Vector<String> userName = new Vector<>(Arrays.asList(usuaris.get(i)));
            model.addRow(userName);
        }
    }

    /**
     * Fills de view with the selected user's data
     * @param u Selected user
     */
    private void ompleInformacioUsuari(User u){
        DefaultTableModel model = (DefaultTableModel) view.getRightJTable().getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Conectat");
        model.addColumn("Data de registre");
        model.addColumn("Ultim inici de sesio");
        model.addColumn("Nombre de partides");
        model.addColumn("Total de punts");

    }

    public void startThread() {
        if (ts == null || !ts.isAlive()) {
            //aqui comence thread
            ts = new ThreadSocketServer(gestioDades);
            ts.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1) {
            int row = view.getLeftTable().getSelectedRow();
            User u = gestioDades.mostraDades(table.getValueAt(row, 0).toString());
            ompleInformacioUsuari(u);
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
package Controller;

import Model.GestioDades;
import Model.User;
import Model.exceptions.BadAccessToDatabaseException;
import View.ServerAdminView;

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

    private ServerAdminView serverAdminView;
    private GestioDades gestioDades;

    private User selectedUser;

    /**
     * @param serverAdminView
     * @param gestioDades
     */
    public ServerController(ServerAdminView serverAdminView, GestioDades gestioDades) {
        this.serverAdminView = serverAdminView;
        this.gestioDades = gestioDades;

        try {
            ompleUsuaris(gestioDades.fetch("^"));
        } catch (BadAccessToDatabaseException e) {
            e.printMessage();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(serverAdminView.ACTION_BORRAR)){
            try{
                gestioDades.deleteUser(selectedUser.getUserName());
                ompleUsuaris(gestioDades.fetch("^"));
                selectedUser = null;
                DefaultTableModel model = (DefaultTableModel) serverAdminView.getRightJTable().getModel();
                model.setRowCount(0);
                model.setColumnCount(0);

            }catch (BadAccessToDatabaseException e1){
                serverAdminView.mostraError();
                e1.printMessage();
            }

        }else if (e.getActionCommand().equals(serverAdminView.ACTION_SEARCH)){
            if (serverAdminView.getBuscador().getText().equals("")){
                ArrayList<String> usuaris;
                try {
                    usuaris = gestioDades.fetch("^");
                    ompleUsuaris(usuaris);
                } catch (BadAccessToDatabaseException e1) {
                    e1.printMessage();
                }
            }else{
                ArrayList<String> usuaris;
                try {
                    usuaris = gestioDades.fetch(serverAdminView.getBuscador().getText());
                    ompleUsuaris(usuaris);
                } catch (BadAccessToDatabaseException e1) {
                    e1.printMessage();
                }
            }

        }else if (e.getActionCommand().equals(serverAdminView.UPDATE)){
            try {
                ompleUsuaris(gestioDades.fetch("^"));
            } catch (BadAccessToDatabaseException e1) {
                e1.printMessage();
            }
        }
    }

    /**
     * Fills the serverAdminView with a list of the user's names
     * @param usuaris
     */
    public void ompleUsuaris(ArrayList<String> usuaris){
        DefaultTableModel model = (DefaultTableModel) serverAdminView.getLeftTable().getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Name");
        for (int i = 0; i < usuaris.size(); i++){
            Vector<String> userName = new Vector<>(Arrays.asList(usuaris.get(i)));
            model.addRow(userName);
        }
    }

    /**
     * Fills de serverAdminView with the selected user's data
     * @param info Selected user
     */
    private void ompleInformacioUsuari(ArrayList<String> info){
        DefaultTableModel model = (DefaultTableModel) serverAdminView.getRightJTable().getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Conectat");
        model.addColumn("Data de registre");
        model.addColumn("Ultim inici de sesio");
        model.addColumn("Nombre de partides");
        model.addColumn("Total de punts");

        Vector<String> connected = new Vector<>(Arrays.asList(info.get(0)));
        Vector<String> registerDate = new Vector<>(Arrays.asList(info.get(1)));
        Vector<String> lastLogin = new Vector<>(Arrays.asList(info.get(2)));
        Vector<String> gameCount = new Vector<>(Arrays.asList(info.get(3)));
        Vector<String> totalPoints = new Vector<>(Arrays.asList(info.get(4)));

        String con;
        if (connected.get(0).equals("1")){
            con = "Online";
        }else{
            con = "Offline";
        }

        Vector<Object> row = new Vector<Object>();
        row.addElement(con);
        row.addElement(registerDate.get(0));
        row.addElement(lastLogin.get(0));
        row.addElement(gameCount.get(0));
        row.addElement(totalPoints.get(0));
        model.addRow(row);
    }

    /**
     * Actualiza tanto en base de datos como en la vista de administrador el estado de conexion
     * de un usuario (online o offline)
     * @param connected
     */
    public void updateUserConnectionStatus(boolean connected, String userName) {
        try {
            gestioDades.setConnectionStatus(userName, connected);
            serverAdminView.updateUserStatus(userName, connected);
        } catch (BadAccessToDatabaseException e) {
            e.printMessage();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 2) {
            int row = serverAdminView.getLeftTable().getSelectedRow();
            ArrayList<String> aux = null;
            try {
                aux = gestioDades.mostraDades(table.getValueAt(row, 0).toString());
                ompleInformacioUsuari(aux);
                selectedUser = gestioDades.getUser(table.getValueAt(row, 0).toString());

            } catch (BadAccessToDatabaseException e1) {
                e1.printMessage();
            }
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
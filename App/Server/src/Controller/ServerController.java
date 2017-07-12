package Controller;

import Model.GestioDades;
import Model.User;
import Model.exceptions.BadAccessToDatabaseException;
import View.PointsGraph;
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

        updateUserList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(serverAdminView.ACTION_BORRAR)){
            try{
                gestioDades.deleteUser(selectedUser.getUserName());
                updateUserList();
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

                updateUserList();

            }else{
                ArrayList<String> usuaris;
                try {
                    usuaris = gestioDades.fetch(serverAdminView.getBuscador().getText());
                    ompleUsuaris(usuaris);
                } catch (BadAccessToDatabaseException e1) {
                    e1.printMessage();
                }
            }

        }else if (e.getActionCommand().equals(serverAdminView.GRAPHS)){
            System.out.println("mostra grafiques");
            PointsGraph pointsGraph = new PointsGraph();
            pointsGraph.PointsGraph();
            pointsGraph.setVisible(true);
        }
    }

    /**
     * Fills the serverAdminView with a list of the user's names
     * @param usuaris lista de usuarios almacenados en la abse de datos
     */
    public void ompleUsuaris(ArrayList<String> usuaris){
        try {
            DefaultTableModel model = (DefaultTableModel) serverAdminView.getLeftTable().getModel();
            model.setRowCount(0);
            model.setColumnCount(0);

            model.addColumn("Name");
            for (int i = 0; i < usuaris.size(); i++) {
                String user = usuaris.get(i);
                if (gestioDades.userIsOnline(user)) {
                    user += " (Online)";
                }

                Vector<String> userName = new Vector<>();
                userName.add(user);
                model.addRow(userName);
            }

            serverAdminView.getLeftTable().repaint();
        }catch (BadAccessToDatabaseException bdb){
            bdb.printMessage();
        }
    }

    /**
     * Actualiza la lista de usuarios y su estado de conexion
     */
    public void updateUserList(){
        try {
            ompleUsuaris(gestioDades.fetch("^"));
        } catch (BadAccessToDatabaseException e) {
            e.printMessage();
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

        model.addColumn("Data de registre");
        model.addColumn("Ultim inici de sesio");
        model.addColumn("Nombre de partides");
        model.addColumn("Total de punts");

        Vector<String> registerDate = new Vector<>(Arrays.asList(info.get(1)));
        Vector<String> lastLogin = new Vector<>(Arrays.asList(info.get(2)));
        Vector<String> gameCount = new Vector<>(Arrays.asList(info.get(3)));
        Vector<String> totalPoints = new Vector<>(Arrays.asList(info.get(4)));

        Vector<Object> row = new Vector<Object>();
        row.addElement(registerDate.get(0));
        row.addElement(lastLogin.get(0));
        row.addElement(gameCount.get(0));
        row.addElement(totalPoints.get(0));
        model.addRow(row);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1 && table == serverAdminView.getLeftTable()) {
            int row = serverAdminView.getLeftTable().getSelectedRow();
            ArrayList<String> data = null;
            try {
                String user = table.getModel().getValueAt(row, 0).toString().replace("(Online)", "");
                data = gestioDades.mostraDades(user);
                ompleInformacioUsuari(data);
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

    public ArrayList<String> onlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (int i = 0; i < serverAdminView.getLeftTable().getRowCount(); i++){
            String user = (String) serverAdminView.getLeftTable().getValueAt(i,0);
            if (user.contains("(Online)")){
                String[] aux = user.split(" ");
                onlineUsers.add(aux[0]);
            }
        }
        return onlineUsers;
    }
}
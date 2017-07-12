package controller;

import Vista.EspectatorView;
import model.LiveUser;
import network.Conexio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by natal on 20/5/2017.
 */
public class EspectatorController implements MouseListener {
    private static EspectatorView espectatorView;
    private Conexio conexio;

    EspectatorController(EspectatorView espectatorView, Conexio conexio){
        this.espectatorView = espectatorView;
        this.conexio = conexio;
        ompleLlistaUsuaris();
    }


    private void ompleLlistaUsuaris(){
        ArrayList<String> usuaris = conexio.getOnlineUsers();
        DefaultTableModel model = (DefaultTableModel) espectatorView.getTable().getModel();
        model.addColumn("Nombre de usuario");
        model.addColumn("Tiempo de juego");
        model.addColumn("Espectadores");

        for (int i = 0; i < usuaris.size(); i++){
            Vector<String> userName = new Vector<String>(Arrays.asList(usuaris.get(i)));
            Vector<String> Time = new Vector<String>(Arrays.asList("Pendiente de implementacion"));
            Vector<String> espectators = new Vector<String>(Arrays.asList(String.valueOf(usuaris.get(i))));

            Vector<Object> row = new Vector<Object>();
            row.addElement(userName.get(0));
            row.addElement(Time.get(0));
            row.addElement(espectators.get(0));
            model.addRow(row);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1) {
            int row = espectatorView.getTable().getSelectedRow();
            //TODO: IMPLEMENTAR FUNCION QUE DEVUELVA LOS DATOS DE LA PARTIDA SELECCIONADA AL SERVER
            System.out.println("Quiero espectar la partida de " + table.getValueAt(row, 0).toString());

            conexio.sendUserToEspectate(table.getValueAt(row, 0).toString());
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

package controller;

import Vista.ReplaySelectView;
import model.UserReplay;
import network.ThreadSocketClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

/**
 * Created by angel on 25/05/2017.
 */
public class ReplaySelectController implements MouseListener{
    private ReplaySelectView view;
    private ThreadSocketClient conexio;

    ReplaySelectController(ReplaySelectView view, ThreadSocketClient conexio){
        this.view = view;
        this.conexio = conexio;
        ompleReplays();
    }


    private void ompleReplays(){
        //codigo para testear si printa por pantalla. borrar i hacer que los datos le lleguen de la BBDD
        Date date = new Date();

        UserReplay rep1 = new UserReplay(date, 12, 13,14);
        UserReplay rep2 = new UserReplay(date, 2,34,5);

        ArrayList<UserReplay> usuaris = new ArrayList<>();
        usuaris.add(rep1);
        usuaris.add(rep2);
//----------
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.addColumn("Fecha");
        model.addColumn("Puntuación");
        model.addColumn("Tiempo de duración");
        model.addColumn("Pico máximo de espectadores");

        for (int i = 0; i < usuaris.size(); i++){
            Vector<Date> fecha = new Vector<>(Arrays.asList(usuaris.get(i).getDate()));
            Vector<Integer> puntuacion = new Vector<>(Arrays.asList(usuaris.get(i).getPuntuacion()));
            Vector<Integer> tiempo = new Vector<>(Arrays.asList(usuaris.get(i).getTiempo()));
            Vector<Integer> espectadors = new Vector<>(Arrays.asList(usuaris.get(i).getTiempo()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(fecha.get(0));
            row.addElement(puntuacion.get(0));
            row.addElement(tiempo.get(0));
            row.addElement(espectadors.get(0));
            model.addRow(row);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1) {
            int row = view.getTable().getSelectedRow();
            //TODO: IMPLEMENTAR FUNCION QUE DEVUELVA LOS DATOS DE LA PARTIDA SELECCIONADA AL SERVER
            System.out.println("Quiero ver las replays de " + table.getValueAt(row, 0).toString());
            conexio.sendDesiredUserReplay(table.getValueAt(row, 0).toString());
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

package controller;

import Vista.GameView;
import Vista.ReplaySelectView;
import network.Conexio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by angel on 25/05/2017.
 */
public class ReplaySelectController implements MouseListener{
    private ReplaySelectView view;
    private Conexio conexio;
    private String userName;
    private ArrayList<String> replay;
    private GameController gc;
    private GameView gv;

    ReplaySelectController(ReplaySelectView view, Conexio conexio, String userName){
        this.view = view;
        this.conexio = conexio;
        this.userName = userName;
        try {
            ompleReplays();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        replay = new ArrayList<>();
    }


    /**
     * Llena la vista de la replay con la informacion requerida.
     * @throws ParseException
     */
    private void ompleReplays() throws ParseException {
        String[] data = conexio.getReplays(userName);

        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.addColumn("Identificador");
        model.addColumn("Puntuación");
        model.addColumn("Tiempo de duración");
        model.addColumn("Fecha");
        model.addColumn("Pico máximo de espectadores");

        for (int i = 0; i < data.length; i++){
            String [] aux = data[i].split("#");
            String score = aux[0];
            String date = aux[1];
            String time = aux[2];
            String max_espectators = aux[3];

            Vector id = new Vector<>(Arrays.asList(i + 1));
            Vector puntuacion = new Vector<>(Arrays.asList(score));
            Vector tiempo = new Vector<>(Arrays.asList(date));
            Vector fecha = new Vector<>(Arrays.asList(time));
            Vector espectadors = new Vector<>(Arrays.asList(max_espectators));

            Vector<Object> row = new Vector<Object>();
            row.addElement(id.get(0));
            row.addElement(puntuacion.get(0));
            row.addElement(tiempo.get(0));
            row.addElement(fecha.get(0));
            row.addElement(espectadors.get(0));
            model.addRow(row);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1) {
            int row = view.getTable().getSelectedRow();
            replay = conexio.getDesiredUserReplay(Integer.parseInt(table.getValueAt(row, 0).toString()));
            gc.readyReplay(replay);
            gv.setVisible(true);
            gc.startReplay();
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

    public ArrayList<String> getReplay (){return replay;}

    public void setGc (GameController gc){this.gc = gc;}
    public void setGv (GameView gv){this.gv = gv;}
}

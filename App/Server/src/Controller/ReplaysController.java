package Controller;

import Model.GestioDades;
import Model.User;
import Model.exceptions.BadAccessToDatabaseException;
import View.ReplaysView;

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
 * Created by angel on 23/07/2017.
 */
public class ReplaysController implements MouseListener, ActionListener {
    private String selectedUser;
    private GestioDades gestioDades;
    private ReplaysView replaysView;
    private String selectedReplay;
    private ArrayList<String> replays;


    public ReplaysController(String selectedUser, GestioDades gestioDades, ReplaysView replaysView){
        this.selectedUser = selectedUser;
        this.gestioDades = gestioDades;
        this.replaysView = replaysView;

        ompleLlistaReplays();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "DELETE":
                try {
                    System.out.println("entro a borrar la replay " + selectedReplay);
                    gestioDades.deleteReplay(selectedReplay);
                    actualitzaVista(selectedReplay);
                } catch (BadAccessToDatabaseException e1) {
                    e1.printStackTrace();
                }

                break;
        }
    }

    private void actualitzaVista(String selectedReplay) {
        replays.remove(selectedReplay);
        DefaultTableModel model = (DefaultTableModel) replaysView.getTable().getModel();
        model.setRowCount(0);

        for (int i = 0; i < replays.size(); i++){
            Vector<String> paths = new Vector<>(Arrays.asList(String.valueOf(replays.get(i))));
            Vector<Object> row = new Vector<Object>();
            row.addElement(paths.get(0));
            model.addRow(row);
        }
    }

    private void ompleLlistaReplays() {
        replays = gestioDades.getReplays(selectedUser);
        DefaultTableModel model = (DefaultTableModel) replaysView.getTable().getModel();
        model.addColumn("Archivos de replay");

        for (int i = 0; i < replays.size(); i++){
            Vector<String> paths = new Vector<>(Arrays.asList(String.valueOf(replays.get(i))));
            Vector<Object> row = new Vector<Object>();
            row.addElement(paths.get(0));
            model.addRow(row);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1 && table == replaysView.getTable()) {
            int row = replaysView.getTable().getSelectedRow();
            try {
                selectedReplay = table.getValueAt(row, 0).toString();

                System.out.println("selecciono la replay: " + selectedReplay);
                gestioDades.deleteReplay(selectedReplay);
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

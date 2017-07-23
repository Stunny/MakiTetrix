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
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by angel on 23/07/2017.
 */
public class ReplaysController implements MouseListener, ActionListener {
    private String selectedUser;
    private GestioDades gestioDades;
    private ReplaysView replaysView;


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
                JTable table = replaysView.getTable();
                int row = table.getSelectedRow();
                String replayPath = (String) table.getValueAt(row, 0);
                try {
                    gestioDades.deleteReplay(replayPath);
                } catch (BadAccessToDatabaseException e1) {
                    e1.printStackTrace();
                }

                break;

        }

    }

    private void ompleLlistaReplays() {

        String replays[] = gestioDades.getReplays(selectedUser);
        System.out.println("lenght: " + replays.length);
        for (int i = 0; i < replays.length; i++){
            System.out.println("rebo aquestes replays: " + replays[i]);
        }

        DefaultTableModel model = (DefaultTableModel) replaysView.getTable().getModel();
        model.addColumn("Archivos de replay");

        for (int i = 0; i < replays.length; i++){
            Vector<String> paths = new Vector<>(Arrays.asList(String.valueOf(replays[i])));
            Vector<Object> row = new Vector<Object>();
            row.addElement(paths.get(0));
            model.addRow(row);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

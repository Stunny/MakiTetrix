package controller;

import Vista.EspectatorView;
import network.Conexio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * Created by natal on 20/5/2017.
 */
public class EspectatorController implements MouseListener {
    private static EspectatorView espectatorView;
    private Conexio conexio;
    private String currentUser;
    private boolean esperant;


    EspectatorController(EspectatorView espectatorView, Conexio conexio, String currentUser){
        this.espectatorView = espectatorView;
        this.conexio = conexio;
        this.currentUser = currentUser;
        this.esperant = true;
        try {
            ompleLlistaUsuaris();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Llena la vista del modo espectador con la informacion necesaria
     * @throws ParseException
     */
    public void ompleLlistaUsuaris() throws ParseException {
        String data[] = conexio.getGamingUsers();

        DefaultTableModel model = (DefaultTableModel) espectatorView.getTable().getModel();
        model.addColumn("Nombre de usuario");
        model.addColumn("Tiempo de juego");
        model.addColumn("Espectadores");

        for (int i = 0; i < data.length; i+=2){
            Vector<String> userName = new Vector<String>(Arrays.asList(data[i]));
            String startingGameTime = data[i + 1];
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = format.parse(startingGameTime);
            Date date2 = format.parse(currentTime);
            long difference = date2.getTime() - date1.getTime();
            difference = (long) (difference* 0.8);
            String playTime = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(difference),
                    TimeUnit.MILLISECONDS.toMinutes(difference) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)),
                    TimeUnit.MILLISECONDS.toSeconds(difference) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));

            Vector<String> Time = new Vector<>(Arrays.asList(playTime));
            Vector<String> espectators = new Vector<>(Arrays.asList(String.valueOf(0)));

            Vector<Object> row = new Vector<Object>();
            row.addElement(userName.get(0));
            row.addElement(Time.get(0));
            row.addElement(espectators.get(0));
            model.addRow(row);
        }
    }
    public void setEsperant (boolean b){
        esperant = b;
    }
    public boolean getEsperant(){
        return esperant;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setEsperant(false);
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 1) {
            int row = espectatorView.getTable().getSelectedRow();
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

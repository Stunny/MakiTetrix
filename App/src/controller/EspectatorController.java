package controller;

import Vista.EspectatorView;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import model.LiveUser;
import model.User;
import network.ThreadSocketClient;
import org.omg.CORBA.TIMEOUT;

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
    private ThreadSocketClient conexio;

    EspectatorController(EspectatorView espectatorView, ThreadSocketClient conexio){
        this.espectatorView = espectatorView;
        this.conexio = conexio;
        ompleLlistaUsuaris();
    }


    private void ompleLlistaUsuaris(){
        //TODO: LLENAR UNA ARRAYLIST DE LiveUser Y LLENAR LA LISTA DE LA VISTA

        //codigo para testear que el usuario "angel" i "farre" sale por pantalla
        LiveUser liveUser = new LiveUser("angel", 15);
        LiveUser liveUser2 = new LiveUser("farre", 152);

        ArrayList<LiveUser> usuaris = new ArrayList<>();
        usuaris.add(liveUser);
        usuaris.add(liveUser2);
//----------
        //TODO: LLAMAR A FUNCION QUE DEVUELVA UNA INSTANCIA VALIDA DE LiveUser PARA POBLAR LA JTABLE
        DefaultTableModel model = (DefaultTableModel) espectatorView.getTable().getModel();
        model.addColumn("Nombre de usuario");
        model.addColumn("Tiempo de juego");
        model.addColumn("Espectadores");

        for (int i = 0; i < usuaris.size(); i++){
            Vector<String> userName = new Vector<String>(Arrays.asList(usuaris.get(i).getUserName()));
            Vector<String> Time = new Vector<String>(Arrays.asList("Pendiente de implementacion"));
            Vector<String> espectators = new Vector<String>(Arrays.asList(String.valueOf(usuaris.get(i).getEspectators())));

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

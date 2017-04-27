package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by avoge on 04/04/2017.
 */
public class MenuController implements ActionListener {

    private static MenuController mc;

    /**
     *
     * @return
     */
    public MenuController getInstance(){
        if(mc == null)
            mc = new MenuController();
        return mc;
    }

    /**
     *
     */
    private MenuController(){
        //TODO: definir funcionalidades del controlador principal del cliente
        //TODO: implementar contructor
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: implementar metodo de actionperformed
    }
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by avoge on 04/04/2017.
 */
public class MainController implements ActionListener {

    private static MainController mc;

    /**
     *
     * @return
     */
    public MainController getInstance(){
        if(mc == null)
            mc = new MainController();
        return mc;
    }

    /**
     *
     */
    private MainController(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

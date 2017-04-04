package controller;

import Vista.VInicioSesion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View controller for the Login functionality
 * Created by avoge on 04/04/2017.
 */
public class LoginController implements ActionListener {

    /**
     * Action command that the Login button of the Login Screen will have associated
     */
    public static final String LOGIN_ACTION_LOG = "LOGIN_LOG";

    /**
     * Action command that the Register button of the Register screen will have associated
     */
    public static final String LOGIN_ACTION_REG = "LOGIN_REG";

    /**
     * Login screen object to control
     */
    private VInicioSesion view;

    /**
     *
     * @param view
     */
    public LoginController(VInicioSesion view){

        this.view = view;

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()){
            case LOGIN_ACTION_LOG:
                OnLogin();
                break;
            case LOGIN_ACTION_REG:
                OnRegister();
                break;
        }
    }

    /**
     *
     */
    public void OnLogin(){

    }

    /**
     *
     */
    public void OnRegister(){

    }
}

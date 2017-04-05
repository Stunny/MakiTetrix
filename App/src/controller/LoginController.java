package controller;

import Vista.LoginView;

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
    private LoginView view;

    /**
     *
     */
    private static LoginController loginController;

    /**
     *
     * @return
     */
    public LoginController getInstance(LoginView view){
       if(loginController == null){
           loginController = new LoginController(view);
       }
       return loginController;
    }

    /**
     * Creates a new instance of a login controller
     * @param view an instance of a Login Screen
     */
    private LoginController(LoginView view){
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
        System.out.println(LOGIN_ACTION_LOG);
        System.out.println(view.getUserName());
        System.out.println(view.getPassword());
        System.out.println();
    }

    /**
     *
     */
    public void OnRegister(){
        System.out.println(LOGIN_ACTION_REG);
    }
}

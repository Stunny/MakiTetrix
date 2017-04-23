package controller;

import Vista.LoginView;
import Vista.RegisterView;
import model.User;
import network.UserAccessRepository;

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
    private UserAccessRepository uar;

    /**
     *
     */
    private static LoginController loginController;

    /**
     *
     * @return
     */
    public static LoginController getInstance(LoginView view, UserAccessRepository uar){
       if(loginController == null){
           loginController = new LoginController(view, uar);
       }
       return loginController;
    }

    /**
     * Creates a new instance of a login controller
     * @param view an instance of a Login Screen
     */
    private LoginController(LoginView view, UserAccessRepository uar){
        this.uar = uar;
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
                System.out.println("Registro");
                break;
        }
    }

    /**
     *
     */
    public void OnLogin(){
        User loginuser;
        if (view.getUserName().equals(view.LOG_EMPTY_UNAME)){
            OnLoginFailed();
            return;
        } else if (view.getUserName().indexOf("@") == -1){
            loginuser = new User ();
            loginuser.setUserName(view.getUserName());
            loginuser.setPassword(view.getPassword());
        } else {
            loginuser = new User();
            loginuser.setEmail(view.getUserName());
            loginuser.setPassword(view.getPassword());
        }
        if (uar.login(loginuser)){
            OnLoginSuccess();
        } else {
            OnLoginFailed();
        }
    }

    /**
     *
     */
    public void OnLoginSuccess(){
        //Cargar Menú del usuario
    }

    /**
     *
     */
    public void OnLoginFailed(){
        view.setLoginError();
    }

    /**
     *
     */
    public void OnRegister(){

        view.setVisible(false);

        RegisterView rv = new RegisterView();
        RegisterController rc = RegisterController.getInstance(rv, view);
        rv.registerController(rc);
        rv.setVisible(true);

    }
}

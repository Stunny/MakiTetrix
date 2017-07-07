package controller;

import Vista.LoginView;
import Vista.MainMenuView;
import Vista.RegisterView;
import model.User;
import network.Conexio;
import network.UserAccessRepository;

import javax.swing.*;
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
    private Conexio conexio;

    public static LoginController getInstance(LoginView view, Conexio conexio, UserAccessRepository uar){
       if(loginController == null){
           loginController = new LoginController(view, conexio, uar);
       }
       return loginController;
    }

    /**
     * Creates a new instance of a login controller
     * @param view an instance of a Login Screen
     */
    public LoginController(LoginView view, Conexio conexio, UserAccessRepository uar){
        this.uar = uar;
        this.view = view;
        this.conexio = conexio;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()){
            case LOGIN_ACTION_LOG:
                OnLoginPressed();
                break;

            case LOGIN_ACTION_REG:
                OnRegisterPressed();
                break;
        }
    }

    /**
     * Checks if fields are blank, if not waits for server response. If reponse is OK leads to Main menu view.
     * Otherwise displays error
     */
    private void OnLoginPressed(){

        if (view.getUserName().equals(LoginView.LOG_EMPTY_UNAME) || view.getPassword().equals(LoginView.LOG_EMPTY_PSSWD)){
            view.setLoginError("El nombre de usuario o contraseña no pueden estar vacios.");
        } else {
            User loginUser = new User(view.getUserName(), null, view.getPassword());
            conexio.startingLoginRegister(loginUser);

            try {
                conexio.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (conexio.isResponseSuccess()){
                onAccesOK(loginUser);
            }else{
                view.setLoginError(conexio.getResponse());
            }
        }
    }

    /**
     * Una vez la transaccion de login/registro ha tenido éxito, conduce al usuario al menu principal
     */
    public void onAccesOK(User loginUser){
        MainMenuView mmv = new MainMenuView();
        MenuController mc = new MenuController(mmv, conexio, loginUser);
        mmv.registerActions(mc);
        mmv.setVisible(true);
        view.setVisible(false);
    }

    /**
     * Oculta la vista vista del login y muestra la vista del registro
     */
    public void OnRegisterPressed(){
        JPanel formPanel;
        view.setVisible(false);

        LoginView lv = new LoginView();
        RegisterView rv = new RegisterView(lv);
        formPanel = rv.getFormPanel();
        rv.setContentPane(formPanel);
        rv.pack();
        RegisterController rc = new RegisterController(rv, null, conexio, this);
        rv.registerController(rc);
        rv.setVisible(true);

    }
}

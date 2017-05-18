package controller;

import Vista.LoginView;
import Vista.RegisterView;
import model.User;
import network.ThreadSocketClient;
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
    private ThreadSocketClient tsc;

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
    public LoginController(LoginView view, UserAccessRepository uar){
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
                break;
        }
    }

    public void startThread(User u){
        if (tsc == null || !tsc.isAlive()) {
            //aqui comence thread
            tsc = new ThreadSocketClient(u);
            tsc.start();
        }
    }

    /**
     *
     */
    public void OnLogin(){

        if (view.getUserName().equals(view.LOG_EMPTY_UNAME) || view.getPassword().equals(view.LOG_EMPTY_PSSWD)){
            OnLoginFailed();
            return;
            /*
            quien ha hecho este if y para que??
        } else if (view.getUserName().indexOf("@") == -1){
            loginUser = new User (null, null, null);
            loginUser.setUserName(view.getUserName());
            loginUser.setPassword(view.getPassword());
        */
        } else {
            User loginUser = new User(view.getUserName(), null, view.getPassword());
            startThread(loginUser);

            try {
                tsc.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (tsc.isAux()){
                OnLoginSuccess();
            }else{
                OnLoginFailed();
            }
        }
    }

    /**
     *
     */
    public void OnLoginSuccess(){
    }

    /**
     *
     */
    public void OnLoginFailed(){
        String[] aux = tsc.getResponse().split("-");
        view.setLoginError(aux[1]);
    }

    /**
     *
     */
    public void OnRegister(){
        JPanel formPanel;
        view.setVisible(false);

        LoginView lv = new LoginView();
        RegisterView rv = new RegisterView(lv);
        formPanel = rv.getFormPanel();
        rv.setContentPane(formPanel);
        rv.pack();
        RegisterController rc = new RegisterController(rv, null);
        rv.registerController(rc);
        rv.setVisible(true);

    }
}

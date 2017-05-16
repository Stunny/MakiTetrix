package controller;

import Vista.LoginView;
import Vista.MenuView;
import Vista.RegisterView;
import model.User;
import model.utils.UserDataChecker;
import network.ThreadSocketClient;
import network.UserAccessRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by avoge on 05/04/2017.
 *
 *
 */
public class RegisterController implements ActionListener {

    public static final String ACTION_REG = "REGISTER";
    private ThreadSocketClient tsc;
    //private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    /**
     *
     */
    private RegisterView view;

    /**
     *
     */
    private LoginView parent;

    private UserAccessRepository accessRepo;

    /**
     *
     */
    private static RegisterController rc;

    /**
     *
     */
    private UserAccessRepository uar;

    private String userName;
    private String userPass;
    private String confirmPass;
    private String userEmail;

    /**
     *
     * @param view
     * @param parent
     * @return
     */
    public static RegisterController getInstance(RegisterView view, LoginView parent/*, UserAccessRepository uar*/){
        if(rc == null)
            rc = new RegisterController(view, parent/*, uar*/);
        return rc;
    }

    /**
     *
     * @param view
     */
    public RegisterController(RegisterView view, LoginView parent/*, UserAccessRepository uar*/){
        this.view = view;
        this.parent = parent;
        //accessRepo = uar;
    }

    /**
     *
     */
    public void OnRegisterSuccess(){

        view.setVisible(false);
        parent.setVisible(true);

    }

    /**
     *
     */
    private void OnRegisterFailed() {
        //Utilizar JOptionPane
        JOptionPane.showMessageDialog(view,"User Register Error");
    }

    /**
     *
     */
    public void OnRegisterDone(){

        if(credentialsOK()){
            OnRegisterSuccess();
        }else{
            OnRegisterFailed();
        }
    }

    /**
     *
     * @return
     */
    private boolean credentialsOK() {
        userName = view.getUserName();
        userEmail = view.getUserEmail();
        userPass = view.getUserPassword();
        confirmPass = view.getConfirmPassword();

        UserDataChecker udc = new UserDataChecker();

        if (!udc.checkUserName(userName)){
            view.displayError("El nombre de usuario debe de tener 4 o mas caracteres!");
            view.getJtfUsername().setText("");
            return false;
        }else if(!udc.checkEMail(userEmail)){
            view.displayError("El email es incorrecto o esta vacio!");
            view.getJtfEmail().setText("");
            return false;
        }else if(!userPass.equals(confirmPass) || userPass.equals("") || confirmPass.equals("")){
            view.displayError("Las contraseñas deben coincidir");
            view.getJpfPassword().setText("");
            view.getJpfConfirmPassword().setText("");
            return false;
        }else if(!view.getJcbAcceptTerms().isSelected()){
            view.displayError("Se deben aceptar los Terminos y Condiciones");
            return false;
        }

        return true;
        /*
        accesRepo = null. no puede cumplir la condicion del if y peta
        else if(!accessRepo.checkEmail(userEmail)){
            return false;
        }
        return accessRepo.checkUserName(userName);
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ACTION_REG)){
            if(credentialsOK()){
                User registerUser = new User(userName, userEmail, userPass);
                startThread(registerUser);
                //despues del thread, la ejecucion en el thread principal siguie, y uar.registre(registerUser) es null
                // porque el thread aun no le ha asignado nada. hay que esperar a que tenga valor

                if (uar.register(registerUser)){
                    MenuView menuView = new MenuView();
                    view.setVisible(false);
                    menuView.setVisible(true);
                }else{
                    view.displayError(uar.response());
                }
            }
        }
    }

    public void startThread(User u){
        if (tsc == null || !tsc.isAlive()) {
            //aqui comence thread
            tsc = new ThreadSocketClient(u);
            tsc.start();
        }
    }
}
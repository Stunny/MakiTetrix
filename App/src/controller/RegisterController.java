package controller;

import Vista.LoginView;
import Vista.MainMenuView;
import Vista.RegisterView;
import model.User;
import model.utils.UserDataChecker;
import network.Conexio;

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
    private static Conexio conexio;

    /**
     *
     */
    private RegisterView view;

    /**
     *
     */
    private LoginView parent;

    private LoginController parentController;
    /**
     *
     */
    private static RegisterController rc;

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
    public static RegisterController getInstance(RegisterView view, LoginView parent, LoginController parentController){
        if(rc == null)
            rc = new RegisterController(view, parent, conexio, parentController);
        return rc;
    }


    public RegisterController(RegisterView view, LoginView parent, Conexio conexio, LoginController parentController){
        this.view = view;
        this.parent = parent;
        this.parentController = parentController;
        this.conexio = conexio;
    }

    /**
     * Checks introduced data on register form
     * @return True if introduced data is corrects, false otherwise
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ACTION_REG)){
            if(credentialsOK()){
                User registerUser = new User(userName, userEmail, userPass);
                conexio.startingLoginRegister(registerUser);

                try {
                    conexio.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                if (conexio.isResponseSuccess()){
                    onRegisterOK(registerUser);
                }else{
                    view.displayError(conexio.getResponse());
                }
            }
        }
    }

    private void onRegisterOK(User u){
        parentController.onAccesOK(u);
    }
}
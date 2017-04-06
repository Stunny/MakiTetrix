package controller;

import Vista.LoginView;
import Vista.RegisterView;

/**
 * Created by avoge on 05/04/2017.
 */
public class RegisterController {

    /**
     *
     */
    private RegisterView view;

    /**
     *
     */
    private LoginView parent;

    /**
     *
     */
    private static RegisterController rc;

    /**
     *
     * @param view
     * @param parent
     * @return
     */
    public static RegisterController getInstance(RegisterView view, LoginView parent){
        if(rc == null)
            rc = new RegisterController(view);
        return rc;
    }

    /**
     *
     * @param view
     */
    private RegisterController(RegisterView view){
        this.view = view;
    }

    /**
     *
     */
    public void OnRegisrerSuccess(){

        view.setVisible(false);
        parent.setVisible(true);

    }

    /**
     *
     */
    public void OnRegisterDone(){

    }
}

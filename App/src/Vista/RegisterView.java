package Vista;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de registro del cliente
 * Created by jorti on 31/03/2017.
 */
public class RegisterView extends JFrame{

    private JTextField jtfUsername;
    private JTextField jtfEmail;
    private JPasswordField jpfPassword;
    private JPasswordField jpfConfirmPassword;

    private JCheckBox jcbAcceptTerms;

    /**
     *
     */
    public RegisterView(){
        configFrame();


    }

    /**
     *
     * @param controller
     */
    public void registerController(RegisterController controller){

    }

    private void configFrame() {
        setTitle("MakiTetrix - Register");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420, 500);
        setLayout(new BorderLayout());
    }
}

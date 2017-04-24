package Vista;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de registro del cliente
 * Created by jorti on 31/03/2017.
 */
public class RegisterView extends JFrame {

    private JTextField jtfUsername;
    private JTextField jtfEmail;

    private JPasswordField jpfPassword;
    private JPasswordField jpfConfirmPassword;

    private JButton jbRegister;
    private JCheckBox jcbAcceptTerms;

    private JPanel inputPanel;
    private JPanel formPanel;


    /**
     * Builds a new RegisterView
     */
    public RegisterView() {
        configFrame();
    }

    /**
     * @param controller
     */
    public void registerController(RegisterController controller) {



    }

    private void configFrame() {
        setTitle("MakiTetrix - Register");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420, 500);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    public static void main(String[] args) {

        RegisterView rv = new RegisterView();
        rv.setContentPane(rv.formPanel);
        rv.pack();
        rv.setVisible(true);

    }

}

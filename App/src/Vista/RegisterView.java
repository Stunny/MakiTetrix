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

    private LoginView parentView;
    /**
     * Builds a new RegisterView
     */
    public RegisterView(LoginView parentView) {

        this.parentView = parentView;

        configFrame();
    }

    /**
     * @param controller
     */
    public void registerController(RegisterController controller) {

        jbRegister.setActionCommand(RegisterController.ACTION_REG);
        jbRegister.addActionListener(RegisterController.getInstance(this, parentView));

    }

    private void configFrame() {
        setTitle("MakiTetrix - Register");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(420, 500);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    public String getUserName(){
        return jtfUsername.getText();
    }

    public String getUserEmail(){
        return jtfEmail.getText();
    }

    public String getUserPassword(){
        return new String(jpfPassword.getPassword());
    }

    public String getConfirmPassword(){
        return new String(jpfConfirmPassword.getPassword());
    }

    public static void main(String[] args) {

        LoginView lv = new LoginView();
        RegisterView rv = new RegisterView(lv);
        rv.setContentPane(rv.formPanel);
        rv.pack();
        rv.setVisible(true);

    }

    public JPanel getFormPanel() {
        return formPanel;
    }
}

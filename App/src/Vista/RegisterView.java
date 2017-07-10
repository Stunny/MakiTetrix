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
        jbRegister.addActionListener(controller);

    }

    private void configFrame() {
        setSize(420, 500);
        setTitle("MakiTetrix - Register");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setResizable(false);
        setLayout(new BorderLayout());
    }

    public void displayError(String errorMessage){
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, errorMessage,
                "ERROR AL REGISTRARSE", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
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

    public JPanel getFormPanel() {
        return formPanel;
    }

    public JTextField getJtfEmail() {
        return jtfEmail;
    }

    public JPasswordField getJpfPassword() {
        return jpfPassword;
    }

    public JPasswordField getJpfConfirmPassword() {
        return jpfConfirmPassword;
    }

    public JCheckBox getJcbAcceptTerms() {
        return jcbAcceptTerms;
    }

    public JTextField getJtfUsername() {
        return jtfUsername;
    }
}

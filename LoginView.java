import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

    private String eMail, username, password;

    public LoginView() {
        super(new GridLayout(3,2, 5, 8));
    }

    public JPanel getPanel() {

        this.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));

        JLabel user = new JLabel("Username:");
        JTextField username = new JTextField(10);

        JLabel pw = new JLabel("Password:");
        JPasswordField password = new JPasswordField(10);

        JButton loginButton = new JButton("Log In");
        JButton registerButton = new JButton("Sign Up");

        loginButton.setPreferredSize(new Dimension(40, 35));
        registerButton.setPreferredSize(new Dimension(40, 35));

        add(user);
        add(username);

        add(pw);
        add(password);

        add(loginButton);
        add(registerButton);

        setVisible(true);
        setPreferredSize(new Dimension(500, 200));
        return this;
    }

}

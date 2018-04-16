package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginView implements View {
    private String title = "Login";
    private JPanel view = new JPanel();
    private JTextField password = new JPasswordField(10);
    private JTextField username = new JTextField(10);
    private JButton login = new JButton("Login");
    private JButton register = new JButton("Register");


    public LoginView(){
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());
        usernamePanel.add(new Label("Username"));
        usernamePanel.add(username);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.add(new Label("Password"));
        passwordPanel.add(password);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(login);
        buttonPanel.add(register);



        view.setLayout(new GridLayout(0,1));
        view.add(usernamePanel);
        view.add(passwordPanel);
        view.add(buttonPanel);

    }
    public String getTitle(){
        return title;
    }

    public JPanel getView() {
        return view;
    }

    public void registerListeners(ActionListener listener){
        login.addActionListener(listener);
        register.addActionListener(listener);
    }

    public ArrayList<Object> getListenerObjects(){
        ArrayList<Object> listeners = new ArrayList<>();
        listeners.add(login);
        listeners.add(password);

        return listeners;
    }

    public String[] getData(){
        return new String[]{username.getText(),password.getText()};
    }

    public void setData(Object[] models){

    }
}

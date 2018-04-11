import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public View() 
    {
        super("Library Companion App");

        JPanel jp = new JPanel();
        LoginView lv = new LoginView();
        MainView libMain = new MainView("librarian");
        MainView usrMain = new MainView("user");
        RegisterView rv = new RegisterView();
      
        //configure card layout
        CardLayout cards = new CardLayout(5, 5);      
        jp.setLayout(cards);
        jp.add(lv, "login");
        jp.add(libMain, "librarianMain");
        jp.add(usrMain, "userMain");
        jp.add(rv, "register");
      
        cards.show(jp, "login");
        setContentPane(jp);
        setLocation(500, 125);
        setSize(600, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setPanel(JPanel panel) {
        add(panel);
        setPreferredSize(panel.getPreferredSize());
        pack();
    }

    public void removePanel(JPanel panel) {
        remove(panel);
    }

}

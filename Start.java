import javax.swing.*;
import java.awt.*;

public class Start
{
   public static void main(String args[])
   {
      JFrame frame = new JFrame();
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
      frame.setContentPane(jp);
      frame.setLocation(500, 125);
      frame.setSize(600, 500);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
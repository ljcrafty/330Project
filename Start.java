import javax.swing.*;
import java.awt.*;

public class Start
{
   public static void main(String args[])
   {
      JFrame frame = new JFrame();
      //LoginView lv = new LoginView();
      MainView lv = new MainView("librarian");
      
      frame.setLayout(new BorderLayout());
      frame.add(lv, BorderLayout.CENTER);
      frame.setLocation(500, 125);
      frame.setSize(600, 500);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Listener implements ActionListener
{
   private ArrayList<String> command;
   private HashMap<String, JTextField> fields;
   private UserController uc;

   public Listener(ArrayList<String> command, HashMap<String, JTextField> fields)
   {
      this.command = command;
      this.fields = fields;
      this.uc = new UserController();
   }
   
   public void actionPerformed(ActionEvent e)
   {
      switch( command.get(0) )
      {
         case "login":
            //check credentials
            String username = fields.get("username").getText();
            String pass = fields.get("password").getText();
            String token = uc.login( username, pass );
            
            //credentials were good, log them in
            if( !token.equals("no user") && !token.equals("invalid password") )
            {
               //gets the frame so we can close it or change it or whatever
               Component component = (Component) e.getSource();
               JPanel jp = (JPanel) component.getParent().getParent();
               CardLayout cards = (CardLayout) jp.getLayout();
               //TODO: need to be able to check privilege level before changing
               cards.show(jp, "");
            }
            else //failed login, give error
            {
               //gets the frame so we can close it or change it or whatever
               JFrame frame = new JFrame();
               
               JOptionPane.showMessageDialog(frame,
                  "Invalid credentials",
                  "Login error",
                  JOptionPane.ERROR_MESSAGE);
            }
            
            break;
         
         case "register":
            //gets the frame so we can close it or change it or whatever
            Component component = (Component) e.getSource();
            JPanel jp = (JPanel) component.getParent().getParent();
            CardLayout cards = (CardLayout) jp.getLayout();
            
            cards.show(jp, "register");
            break;
         
         case " My Profile":
            break;
            
         case " Search By Author":
            break;
            
         case " Search By Title":
            break;
         
         case " Search By Genre":
            break;
         
         case " Borrowed Books":
            break;
         
         case " Reserved Books":
            break;
         
         case " Search for a User":
            break;
         
         case " Check Overdue Loans":
            break;
         
         case " Add a Book":
            break;
         
         case " Add a User":
            break;
         
         
         default:
            break;
      }
   }
}
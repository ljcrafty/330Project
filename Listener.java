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
            login(e);            
            break;
         
         case "register":
            show("register", e);
            break;
            
         case "register with data":
            register(e);            
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
         
         case "start":
            show("login", e);
            break;
         
         default:
            break;
      }//end switch
   }//end actionPerformed
   
   private void show(String name, ActionEvent e)
   {
      //gets the frame so we can close it or change it or whatever
      Component component = (Component) e.getSource();
      JPanel jp = (JPanel) component.getParent().getParent();
      CardLayout cards = (CardLayout) jp.getLayout();
      cards.show(jp, name);
   }
   
   private void login(ActionEvent e)
   {
      //check credentials
      String username = fields.get("username").getText();
      String pass = fields.get("password").getText();
      String token = uc.login( username, pass );
      
      //credentials were good, log them in
      if( !token.equals("no user") && !token.equals("invalid password") )
      {
         //TODO: need to be able to check privilege level before changing
         show("", e);
      }
      else //failed login, give error
      {
         JFrame frame = new JFrame();
         
         JOptionPane.showMessageDialog(frame,
            "Invalid credentials",
            "Login error",
            JOptionPane.ERROR_MESSAGE);
      }
   }

   private void register(ActionEvent e)
   {
      //get data
      String error = "";
      String user = fields.get("username").getText();
      String pass = fields.get("password").getText();
      String fname = fields.get("fname").getText();
      String lname = fields.get("lname").getText();
      String d = fields.get("day").getText(), 
         m = fields.get("month").getText(), 
         y = fields.get("year").getText();
      Calendar dob = Calendar.getInstance();
      
      //validate date
      //TODO: validate better
      if( !d.equals("") && !m.equals("") && !y.equals("") )
      {
         int day = Integer.parseInt(d);
         int mo = Integer.parseInt(m);
         int yr = Integer.parseInt(y);
         dob.set(yr, mo, day);
      }
      else
         error += "date of birth\n";

      //validate other
      error += (user.equals("")? "username\n" : "");
      error += (pass.equals("")? "password\n" : "");
      error += (fname.equals("")? "first name\n" : "");
      error += (lname.equals("")? "last name\n" : "");
      
      //show errors
      if( !error.equals("") )
      {
         JFrame frame = new JFrame();
         
         JOptionPane.showMessageDialog(frame,
            "Invalid input. Please fix the following fields:\n" + error,
            "Invalid input",
            JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         //make user
         User temp = new User(user, pass, fname, lname, 1, dob, 1);
      
         //try to add user
         if( uc.addUser(temp) )
         {
            show("login", e);
         }
         else
         {
            JFrame frame = new JFrame();
         
            JOptionPane.showMessageDialog(frame,
               "Cannot add data",
               "Creation error",
               JOptionPane.ERROR_MESSAGE);
         }
      }
   }
}//end class
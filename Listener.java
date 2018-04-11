import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Listener implements ActionListener
{
   private ArrayList<String> command;
   private HashMap<String, JTextField> fields;

   public Listener(ArrayList<String> command, HashMap<String, JTextField> fields)
   {
      this.command = command;
      this.fields = fields;
   }
   
   public void actionPerformed(ActionEvent e)
   {
      switch( command.get(0) )
      {
         case "login":
            //login mumbo jumbo
            break;
         
         case "register":
            //register mumbo jumbo
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
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
         
         default:
            break;
      }
   }
}
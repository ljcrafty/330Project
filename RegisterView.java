import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RegisterView extends JPanel
{
   public RegisterView() 
   {
       super(new GridLayout(6, 2, 5, 8));
        getPanel();
   }

   public JPanel getPanel() 
   {
       //TODO: add the date of birth and deal with the date stuff
       this.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));

       JLabel user = new JLabel("Username:");
       JTextField username = new JTextField(10);

       JLabel pw = new JLabel("Password:");
       JPasswordField password = new JPasswordField(10);

       JLabel fname = new JLabel("First Name:");
       JTextField f_name = new JTextField(10);

       JLabel lname = new JLabel("Last Name:");
       JPasswordField l_name = new JPasswordField(10);

       JButton cancelButton = new JButton("Cancel");
       JButton regButton = new JButton("Register");

       cancelButton.setPreferredSize(new Dimension(40, 35));
       ArrayList<String> command = new ArrayList<String>();
       command.add("start");
       HashMap<String, JTextField> fields = new HashMap<String, JTextField>();
        
       cancelButton.addActionListener( new Listener( command, fields ) );
        
       regButton.setPreferredSize(new Dimension(40, 35));
       command = new ArrayList<String>();
       command.add("register with data");
       fields = new HashMap<String, JTextField>();
       fields.put("username", username);
       fields.put("password", password);
       fields.put("fname", f_name);
       fields.put("lname", l_name);
        
       regButton.addActionListener( new Listener( command, fields ) );

       add(user);
       add(username);

       add(pw);
       add(password);
       
       add(fname);
       add(f_name);

       add(lname);
       add(l_name);

       add(cancelButton);
       add(regButton);

       setVisible(true);
       setPreferredSize(new Dimension(500, 200));
       return this;
   }
}
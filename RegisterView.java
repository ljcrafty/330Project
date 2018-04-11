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
       JTextField l_name = new JTextField(10);

       JLabel dob = new JLabel("Date of Birth:");
       JLabel date = new JLabel("Day:");
       JTextField day = new JTextField(10);

       JLabel mo = new JLabel("Month:");
       JTextField month = new JTextField(10);
       
       JLabel yr = new JLabel("Year:");
       JTextField year = new JTextField(10);

       JButton cancelButton = new JButton("Cancel");
       JButton regButton = new JButton("Register");

       //setup cancel button
       cancelButton.setPreferredSize(new Dimension(40, 35));
       ArrayList<String> command = new ArrayList<String>();
       command.add("start");
       HashMap<String, JTextField> fields = new HashMap<String, JTextField>();
        
       cancelButton.addActionListener( new Listener( command, fields ) );
        
       //setup register button
       regButton.setPreferredSize(new Dimension(40, 35));
       command = new ArrayList<String>();
       command.add("register with data");
       fields = new HashMap<String, JTextField>();
       fields.put("username", username);
       fields.put("password", password);
       fields.put("fname", f_name);
       fields.put("lname", l_name);
       fields.put("day", day);
       fields.put("month", month);
       fields.put("year", year);
        
       regButton.addActionListener( new Listener( command, fields ) );

       //setup DOB panel
       JPanel dobPanel = new JPanel( new GridLayout(4, 1) );
       dobPanel.add(dob);
       dobPanel.add(date);
       dobPanel.add(mo);
       dobPanel.add(yr);
       
       JPanel dobFieldPanel = new JPanel( new GridLayout(4, 1) );
       dobFieldPanel.add( new JLabel() );
       dobFieldPanel.add(day);
       dobFieldPanel.add(month);
       dobFieldPanel.add(year);

       //add the components
       add(user);
       add(username);

       add(pw);
       add(password);
       
       add(fname);
       add(f_name);

       add(lname);
       add(l_name);
       
       add(dobPanel);
       add(dobFieldPanel);

       add(cancelButton);
       add(regButton);

       setVisible(true);
       setPreferredSize(new Dimension(500, 200));
       return this;
   }
}
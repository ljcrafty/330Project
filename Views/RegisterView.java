package Views;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class RegisterView implements View
{
    private JPanel view = new JPanel();
    private JTextField username, f_name, l_name, day, month, year;
    private JPasswordField password;
    private JButton cancelButton, regButton;

    public RegisterView() 
    {
        this.view.setLayout(new GridLayout(6, 2, 5, 8));
        getPanel();
    }

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return "Register";
    }

    /**
     * Method used to get the JPanel for insertion into frame
     * @return
     */
    public JPanel getView()
    {
        return this.view;
    }

    /**
     * Method used to register listeners
     * @param listener
     */
    public void registerListeners(ActionListener listener)
    {
        cancelButton.addActionListener( listener );
        regButton.addActionListener( listener );
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> listeners = new ArrayList<Object>();
        listeners.add(cancelButton.getListeners(ActionListener.class)[0]);
        listeners.add(regButton.getListeners(ActionListener.class)[0]);

        return listeners;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        String[] fields = new String[7];

        fields[0] = username.getText();
        fields[1] = password.getText();
        fields[2] = f_name.getText();
        fields[3] = l_name.getText();
        fields[4] = day.getText();
        fields[5] = month.getText();
        fields[6] = year.getText();

        return fields;
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        
    }

   public void getPanel() 
   {
        this.view.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));

        JLabel user = new JLabel("Username:");
        username = new JTextField(10);

        JLabel pw = new JLabel("Password:");
        password = new JPasswordField(10);

        JLabel fname = new JLabel("First Name:");
        f_name = new JTextField(10);

        JLabel lname = new JLabel("Last Name:");
        l_name = new JTextField(10);

        JLabel dob = new JLabel("Date of Birth:");
        JLabel date = new JLabel("Day:");
        day = new JTextField(10);

        JLabel mo = new JLabel("Month:");
        month = new JTextField(10);
        
        JLabel yr = new JLabel("Year:");
        year = new JTextField(10);

        //setup buttons
        cancelButton = new JButton("Cancel");
        regButton = new JButton("Register");     
        regButton.setActionCommand("register with data");
        cancelButton.setActionCommand("start");
        cancelButton.setPreferredSize(new Dimension(40, 35));
        regButton.setPreferredSize(new Dimension(40, 35));

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
        this.view.add(user);
        this.view.add(username);

        this.view.add(pw);
        this.view.add(password);
        
        this.view.add(fname);
        this.view.add(f_name);

        this.view.add(lname);
        this.view.add(l_name);
        
        this.view.add(dobPanel);
        this.view.add(dobFieldPanel);

        this.view.add(cancelButton);
        this.view.add(regButton);

        this.view.setVisible(true);
        this.view.setPreferredSize(new Dimension(500, 200));
   }
}
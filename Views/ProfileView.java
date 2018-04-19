package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import Models.User;

public class ProfileView implements View
{
    private User user;
    private JTextField username, password, fname, lname, day, mo, yr;
    private JLabel lusername, lpassword, lfname, llname, lday, lmo, lyr, lrole = new JLabel("2");
    private JButton edit, save = new JButton(), cancel = new JButton(), cancelEdit;
    private ActionListener listener;
    private JPanel view = new JPanel();
    private boolean isEdit;

    public ProfileView()
    {
        this.isEdit = false;
        this.view.setLayout( new GridLayout(0, 2, 9, 8) );
    }

    public ProfileView( User user )
    {
        this.isEdit = false;
        this.view.setLayout( new GridLayout(0, 2, 9, 8) );
        this.setData( new Object[]{user} );
    }

    public void setEdit( boolean edit )
    {
        this.isEdit = edit;
        this.viewSetup();
    }

    private void viewSetup()
    {
        this.view.removeAll();

        if( this.isEdit )
        {
            editSetup();
        }
        else
        {
            nonEditSetup();
        }

        this.view.revalidate();
        this.view.repaint();
    }

    private void editSetup()
    {
        //username
        JLabel lbl = new JLabel("Username:");
        username = new JTextField();
        username.setText(this.user.getUsername());

        this.view.add(lbl);
        this.view.add(username);

        //password
        lbl = new JLabel("Password:");
        password = new JTextField();

        this.view.add(lbl);
        this.view.add(password);

        //first name
        lbl = new JLabel("First Name:");
        fname = new JTextField();
        fname.setText(this.user.getFName());

        this.view.add(lbl);
        this.view.add(fname);

        //last name
        lbl = new JLabel("Last Name:");
        lname = new JTextField();
        lname.setText(this.user.getLName());

        this.view.add(lbl);
        this.view.add(lname);

        //figure out the date
        String[] date = this.getDate();

        //day
        lbl = new JLabel("Day of Birth:");
        day = new JTextField();
        day.setText(date[0]);

        this.view.add(lbl);
        this.view.add(day);

        //month
        lbl = new JLabel("Month of Birth:");
        mo = new JTextField();
        mo.setText(date[1]);

        this.view.add(lbl);
        this.view.add(mo);

        //year
        lbl = new JLabel("Year of Birth:");
        yr = new JTextField();
        yr.setText(date[2]);

        this.view.add(lbl);
        this.view.add(yr);

        //buttons
        cancelEdit = new JButton(new AbstractAction("Cancel") {
            public void actionPerformed(ActionEvent e) {
                setEdit(false);
            }
        });
        save = new JButton("Done");
        save.setActionCommand("save user");
        save.addActionListener(this.listener);

        this.view.add(cancelEdit);
        this.view.add(save);
    }

    private void nonEditSetup()
    {
        //username
        JLabel lbl = new JLabel("Username:");
        lusername = new JLabel(this.user.getUsername());

        this.view.add(lbl);
        this.view.add(lusername);

        //password
        lbl = new JLabel("Password:");
        lpassword = new JLabel("***");

        this.view.add(lbl);
        this.view.add(lpassword);

        //first name
        lbl = new JLabel("First Name:");
        lfname = new JLabel(this.user.getFName());

        this.view.add(lbl);
        this.view.add(lfname);

        //last name
        lbl = new JLabel("Last Name:");
        llname = new JLabel(this.user.getLName());

        this.view.add(lbl);
        this.view.add(llname);

        //figure out the date
        String[] date = this.getDate();

        //day
        lbl = new JLabel("Day of Birth:");
        lday = new JLabel(date[0]);

        this.view.add(lbl);
        this.view.add(lday);

        //month
        lbl = new JLabel("Month of Birth:");
        lmo = new JLabel(date[1]);

        this.view.add(lbl);
        this.view.add(lmo);

        //year
        lbl = new JLabel("Year of Birth:");
        lyr = new JLabel(date[2]);

        this.view.add(lbl);
        this.view.add(lyr);

        //role
        lbl = new JLabel("Role:");
        lrole = new JLabel( Integer.toString(this.user.getRole()) );

        this.view.add(lbl);
        this.view.add(lrole);

        //buttons
        cancel = new JButton("Cancel");
        cancel.setActionCommand("home");
        cancel.addActionListener(this.listener);
        edit = new JButton( new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                setEdit(true);
            }
        });
        

        this.view.add(cancel);
        this.view.add(edit);
    }

    private String[] getDate()
    {
        Calendar dob = user.getDOB();
        String[] temp;

        if(dob != null)
        {
            temp = new String[]{ Integer.toString( dob.get(Calendar.DATE) ),
                Integer.toString( dob.get(Calendar.MONTH) ), Integer.toString( dob.get(Calendar.YEAR) )
            };
        }
        else
        {
            temp = new String[]{"", "", ""};
        }

        return temp;
    }

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return this.username + "'s Profile";
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
        this.listener = listener;
        this.cancel.addActionListener(listener);
        this.save.addActionListener(listener);
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(listener);
        temp.add(listener);
        return temp;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        return new String[]{
            Integer.toString(user.getId()),
            username.getText(),
            password.getText(),
            fname.getText(),
            lname.getText(),
            day.getText(),
            mo.getText(),
            yr.getText(),
            lrole.getText()
        };
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        this.user = (User) model[0];
        this.viewSetup();
    }
}
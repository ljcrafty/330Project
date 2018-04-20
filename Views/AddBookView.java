package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AddBookView implements View
{
    private JPanel view = new JPanel();
    private JButton cancel, done;
    private JTextField isbn, title, fname, lname, genre, numCopies, day, mo, yr;

    public AddBookView()
    {
        this.view.setLayout( new GridLayout(0, 2, 10, 8) );

        //isbn
        JLabel lbl = new JLabel("ISBN: ");
        this.isbn = new JTextField();

        this.view.add(lbl);
        this.view.add(this.isbn);

        //title
        lbl = new JLabel("Title: ");
        this.title = new JTextField();

        this.view.add(lbl);
        this.view.add(this.title);

        //author fname
        lbl = new JLabel("Author First Name: ");
        this.fname = new JTextField();

        this.view.add(lbl);
        this.view.add(this.fname);

        //author lname
        lbl = new JLabel("Author Last Name: ");
        this.lname = new JTextField();

        this.view.add(lbl);
        this.view.add(this.lname);

        //genre name
        lbl = new JLabel("Genre Name: ");
        this.genre = new JTextField();

        this.view.add(lbl);
        this.view.add(this.genre);

        //num copies
        lbl = new JLabel("Number of Copies: ");
        this.numCopies = new JTextField();

        this.view.add(lbl);
        this.view.add(this.numCopies);

        //release day
        lbl = new JLabel("Release Day: ");
        this.day = new JTextField();

        this.view.add(lbl);
        this.view.add(this.day);
        
        //release month
        lbl = new JLabel("Release Month: ");
        this.mo = new JTextField();

        this.view.add(lbl);
        this.view.add(this.mo);

        //release year
        lbl = new JLabel("Release Year: ");
        this.yr = new JTextField();

        this.view.add(lbl);
        this.view.add(this.yr);

        //buttons
        cancel = new JButton("Cancel");
        cancel.setActionCommand("home");
        this.view.add(cancel);

        done = new JButton("Save");
        done.setActionCommand("add book");
        this.view.add(done);
    }

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return "Add Book View";
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
        this.cancel.addActionListener(listener);
        this.done.addActionListener(listener);
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> listeners = new ArrayList<Object>();

        listeners.add( this.cancel.getListeners(ActionListener.class)[0] );
        listeners.add( this.done.getListeners(ActionListener.class)[0] );

        return listeners;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        return new String[]{
            isbn.getText(), 
            title.getText(), 
            fname.getText(), 
            lname.getText(), 
            genre.getText(), 
            numCopies.getText(), 
            day.getText(), 
            mo.getText(), 
            yr.getText()
        };
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        //do nothing
    }
}
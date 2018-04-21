package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import Models.*;
import java.text.SimpleDateFormat;

public class LoansView implements View
{
    private JPanel view = new JPanel();
    private JButton done;
    private String type;
    private Loan[] loans;
    private Reservation[] reservations;

    public LoansView( String type )
    {
        this.type = type;
    }

    private void viewSetup()
    {
        this.view.setLayout( new GridLayout(0, 1, 2, 8) );

        JPanel list = new JPanel( new FlowLayout() );
        int size = ( type == "loans" ? loans.length : reservations.length );

        for( int i = 0; i < size; i++ )
        {
            JPanel item = new JPanel( new GridLayout(0, 2, 2, 8) );
            Calendar date = (type == "loans" ? loans[i].getDueDate() : reservations[i].getDateReserved());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            if(type == "loans")
            {
                Book book = loans[i].getBook();

                JLabel lbl = new JLabel( "ISBN: " + book.getISBN() );
                item.add(lbl);

                lbl = new JLabel( "Title: " + book.getTitle() );
                item.add(lbl);

                lbl = new JLabel( "Author: " + book.getAuthorFName() + " " + book.getAuthorLName() );
                item.add(lbl);
            }
            else
            {
                BookDetails book = reservations[i].getBook();

                JLabel lbl = new JLabel( "ISBN: " + book.getIsbn() );
                item.add(lbl);

                lbl = new JLabel( "Title: " + book.getTitle() );
                item.add(lbl);

                lbl = new JLabel( "Author: " + book.getfName() + " " + book.getlName() );
                item.add(lbl);
            }

            JLabel lbl = new JLabel( (type == "loans" ? "Due Date: " : "Date Reserved: ") + 
                format.format(date.getTime()) );
            item.add(lbl);

            list.add(item);
        }

        //button
        done = new JButton("Done");
        done.setActionCommand("home");
        this.view.add(done);
    }

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return "My " + type;
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
        this.done.addActionListener(listener);
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> listeners = new ArrayList<Object>();

        listeners.add( this.done.getListeners(ActionListener.class)[0] );

        return listeners;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        return null;
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        //set model
        if(this.type == "loans")
        {
            this.loans = (Loan[])model;
        }
        else
        {
            this.reservations = (Reservation[])model;
        }

        viewSetup();
    }
}
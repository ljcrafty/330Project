package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import Models.Book;

public class BookView implements View
{
    private Book book;
    private JButton reserve, loan, cancel;
    private ActionListener listener;
    private JPanel view = new JPanel();
    private boolean canLoan;

    public BookView( Book book, int role )
    {
        this.canLoan = (role == 2);
        this.view.setLayout( new GridLayout(0, 2, 5, 8) );
        this.setData( new Object[]{book} );
    }

    private void viewSetup()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        //isbn
        JLabel lbl = new JLabel("ISBN: " + this.book.getISBN());
        this.view.add(lbl);

        //title 
        lbl = new JLabel("Title: " + this.book.getTitle());
        this.view.add(lbl);

        //author_id
        lbl = new JLabel("Author: " + this.book.getAuthorFName() + " " + this.book.getAuthorLName());
        this.view.add(lbl);

        //release_date
        lbl = new JLabel("Release Date: " + format.format(this.book.getReleaseDate().getTime()) );
        this.view.add(lbl);

        //genre_id
        lbl = new JLabel("Genre: " + this.book.getGenreName());
        this.view.add(lbl);

        //buttons
        cancel = new JButton("Cancel");
        cancel.setActionCommand("home");

        reserve = new JButton("Reserve");
        reserve.setActionCommand("reserve");

        this.view.add(cancel);
        this.view.add(reserve);
        

        if( this.canLoan )
        {
            loan = new JButton("Loan");
            loan.setActionCommand("loan");

            this.view.add(loan);
        }
    }

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return this.book.getTitle();
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
        this.reserve.addActionListener(listener);
        this.loan.addActionListener(listener);
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> temp = new ArrayList<>();
        
        temp.add( this.cancel.getListeners(ActionListener.class)[0] );
        temp.add( this.reserve.getListeners(ActionListener.class)[0] );
        temp.add( this.loan.getListeners(ActionListener.class)[0] );

        return temp;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        return new String[]{
            Integer.toString(book.getId())
        };
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        this.book = (Book) model[0];
        this.viewSetup();
    }
}
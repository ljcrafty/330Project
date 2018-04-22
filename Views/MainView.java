package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainView implements View 
{
    private String type;
    private int userId;
    private JPanel view = new JPanel();

    public MainView(String type){
        this.view.setLayout(new GridLayout(0, 2, 10, 8));
        this.type = type;
        
        switch( type )
        {
            case "librarian":
               String[] labelNames = {" My Profile", " Find Book"," Search for a User",
                  " Check Overdue Loans", " Search Reservations", " My Loaned Books", " My Reservations",
                  " Add a Book", " Add a User"};

               getPanel( labelNames );
               break;
               
            default:
               String[] labels = {" My Profile", " Find Book", " My Loaned Books",
                  " My Reservations"};
            
               getPanel( labels );
               break;
        }
    }
    
    /**
     * Method used to create a title
     * @return
     */
    public String getTitle()
    {
        return "Main " + type + " View";
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
        for( int i = 0; i < this.view.getComponentCount(); i++ )
        {
            Component cmp = this.view.getComponent(i);
            JButton btn = (JButton) cmp;
            btn.addActionListener( listener );
        }
    }

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects()
    {
        ArrayList<Object> listeners = new ArrayList<Object>();

        for( int i = 0; i < this.view.getComponentCount(); i++ )
        {
            Component cmp = this.view.getComponent(i);
            listeners.add(cmp.getListeners(ActionListener.class)[0]);
        }

        return listeners;
    }

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData()
    {
        return new String[]{Integer.toString(this.userId)};
    }

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model)
    {
        this.userId = Integer.parseInt( ((String[]) model)[0]);
    }

    public JPanel getPanel( String[] labelNames ) {

        this.view.setFont(new Font("Courier New", Font.PLAIN, 14));

        for(int i = 0; i < labelNames.length; i++) {
            createRecord(labelNames[i]);
        }

        this.view.setMaximumSize(new Dimension(350, 350));
        this.view.setPreferredSize(new Dimension(400, 280));
        this.view.setVisible(true);
        return this.view;
    }//end of getPanel()

    private void createRecord(String labelName) {

        JButton btn = new JButton(labelName);
        btn.setPreferredSize(new Dimension(45, 35));
        btn.setMaximumSize(new Dimension(45, 35));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        ArrayList<String> command = new ArrayList<String>();
        command.add(labelName);
        command.add(type);
        HashMap<String, JTextField> fields = new HashMap<String, JTextField>();

        this.view.add(btn);

    }//end of createRecord()

}//end of MainView class

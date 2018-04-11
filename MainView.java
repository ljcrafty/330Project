import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainView extends JPanel {

   private String type;

    public MainView(String type){
        super(new GridLayout(0, 2, 10, 8));
        this.type = type;
        
        switch( type )
        {
            case "librarian":
               String[] labelNames = {" My Profile", " Search By Author", 
                  " Search By Title", " Search By Genre", " Search for a User",
                  " Check Overdue Loans", " Borrowed Books", " Reserved Books", 
                  " Add a Book", " Add a User"};

               getPanel( labelNames );
               break;
               
            default:
               String[] labels = {" My Profile", " Search By Author", 
                  " Search By Title", " Search By Genre", " Borrowed Books", 
                  " Reserved Books"};
            
               getPanel( labels );
               break;
        }
    }

    public JPanel getPanel( String[] labelNames ) {

        setFont(new Font("Courier New", Font.PLAIN, 14));

        for(int i = 0; i < labelNames.length; i++) {
            createRecord(labelNames[i]);
        }

        setMaximumSize(new Dimension(350, 350));
        setPreferredSize(new Dimension(400, 280));
        setVisible(true);
        return this;
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
        btn.addActionListener( new Listener(command, fields) );

        add(btn);

    }//end of createRecord()

}//end of MainView class

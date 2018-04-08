import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {

    public MainView(){
        super(new GridLayout(0, 2, 10, 8));
    }

    public JPanel getPanel() {

        setFont(new Font("Courier New", Font.PLAIN, 14));

        String[] labelNames = {" Search", " Book Overview", " Author Overview", " Borrowed Books", " Add New Record", " Add New User"};

        for(int i = 0; i < labelNames.length; i++) {
            createRecord(labelNames[i]);
        }

        setMaximumSize(new Dimension(350, 350));
        setPreferredSize(new Dimension(400, 280));
        setVisible(true);
        return this;
    }//end of getPanel()

    private void createRecord(String labelName) {

        JLabel label = new JLabel(labelName);

        JButton btn = new JButton("Continue");
        btn.setPreferredSize(new Dimension(45, 35));
        btn.setMaximumSize(new Dimension(45, 35));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        add(label);
        add(btn);

    }//end of createRecord()

}//end of MainView class

import javax.swing.*;

public class View extends JFrame {

    public View() {
        super("Library Companion App");

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setPanel(JPanel panel) {
        add(panel);
        setPreferredSize(panel.getPreferredSize());
        pack();
    }

    public void removePanel(JPanel panel) {
        remove(panel);
    }

}

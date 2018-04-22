package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainContainer extends JFrame {

    private Container root;
    private View currentView = null;
    private ArrayList<View> previous;
    private boolean backClicked = false;

    public static void main(String[] args){
        MainContainer mainContainer = new MainContainer();
        mainContainer.addView(new LoginView());
    }



    public MainContainer(){
        super();
        root = this.getContentPane();
        previous = new ArrayList<>();
        JMenuItem back = new JMenuItem("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(back);

        this.setJMenuBar(menuBar);

    }

    public void addView(View view){

        if(!backClicked) {
            if (currentView != null) {
                previous.add(currentView);
            }
        }
        currentView = view;
        int numPanels = root.getComponents().length;

        if(numPanels > 0){
            root.remove(0);
        }


        this.add(view.getView());
        this.setTitle(view.getTitle());
        this.pack();
        setLocation(500, 125);
        setSize(600, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.print("");
        backClicked = false;
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

    public String[] getData(){
        return currentView.getData();
    }

    private void goBack(){
        if(previous.size() > 1) {
            backClicked = true;
            View prev = previous.remove(previous.size() - 1);
            addView(prev);
        }
    }
}

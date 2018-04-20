package Views;

import javax.swing.*;
import java.awt.*;


public class MainContainer extends JFrame {

    private Container root;
    private View currentView;

    public static void main(String[] args){
        MainContainer mainContainer = new MainContainer();
        mainContainer.addView(new LoginView());
    }



    public MainContainer(){
        super();
        root = this.getContentPane();
    }

    public void addView(View view){

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
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

    public String[] getData(){
        return currentView.getData();
    }
}

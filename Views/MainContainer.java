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
        this.setVisible(true);
        System.out.println("");
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message);
    }

    public String[] getData(){
        return currentView.getData();
    }
}

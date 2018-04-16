package Views;

import javax.swing.*;
import java.awt.*;


public class MainContainer {
    private JFrame frame;
    private Container root;
    private View currentView;

    public static void main(String[] args){
        MainContainer mainContainer = new MainContainer();
        mainContainer.addView(new LoginView());
    }



    public MainContainer(){
        frame = new JFrame();
        root = frame.getContentPane();
    }

    public void addView(View view){

        currentView = view;
        int numPanels = root.getComponents().length;

        if(numPanels > 0){
            root.remove(1);
        }


        frame.add(view.getView());
        frame.setTitle(view.getTitle());
        frame.pack();
        frame.setVisible(true);
        System.out.println("");
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(frame,message);
    }

    public String[] getData(){
        return currentView.getData();
    }
}

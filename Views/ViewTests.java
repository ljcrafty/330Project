package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTests implements ActionListener {

    private MainContainer mainContainer;

    public ViewTests(){
        mainContainer = new MainContainer();
        addView(createLoginView());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "Login" :{
                View test = new ProfileView(0);
                test.registerListeners(this);
                addView(test);

            }break;


            default:{
                JOptionPane.showMessageDialog(null,command);
            }
        }
    }

    private void addView(View view){
        mainContainer.addView(view);
    }

    private View createLoginView(){
        View v = new LoginView();
        v.registerListeners(this);
        return v;
    }

    public static void main(String args[]){
        new ViewTests();
    }
}

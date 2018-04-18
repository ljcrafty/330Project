package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileView implements View {

    private JPanel view;
    private String title;

    private ArrayList<JButton> buttons = new ArrayList<>();

    public ProfileView(int role){

        view = new JPanel();
        view.setLayout(new BorderLayout());

        JPanel standardOperations = new JPanel();
        standardOperations.setLayout(new GridLayout(2,0));

        configureControlls();
        addPrimaryControlls(standardOperations);

        view.add(standardOperations,BorderLayout.CENTER);


    }

    private void configureControlls(){
        JButton reserve = new JButton("Reserve Book");
        buttons.add(reserve);
        JButton loans = new JButton("My loans");
        buttons.add(loans);
        JButton reservations = new JButton("My reservations");
        buttons.add(reservations);
        JButton profile = new JButton("My Profile");
        buttons.add(profile);

        for(JButton button: buttons){
            button.setPreferredSize(new Dimension(20,100));
        }
    }

    private void addPrimaryControlls(JPanel targetPanel){
        for(JButton button: buttons){
            targetPanel.add(button);
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void registerListeners(ActionListener listener) {
        for(JButton button: buttons){
            button.addActionListener(listener);
        }
    }

    @Override
    public ArrayList<Object> getListenerObjects() {
        return null;
    }

    @Override
    public String[] getData() {
        return new String[0];
    }

    @Override
    public void setData(Object[] model) {

    }
}

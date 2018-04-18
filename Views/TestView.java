package Views;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TestView implements View {
    private String title = "Title";
    private JPanel view = new JPanel();

    public TestView(){
        view.add(new JLabel("test ok"));
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

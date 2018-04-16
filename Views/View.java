package Views;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public interface View {
    String title = "";
    JPanel view = null;

    /**
     * Method used to create a title
     * @return
     */
    public String getTitle();

    /**
     * Method used to get the JPanel for insertion into frame
     * @return
     */
    public JPanel getView();

    /**
     * Method used to register listeners
     * @param listener
     */
    public void registerListeners(ActionListener listener);

    /**
     * Method used to get updated/new listeners
     * @return
     */
    public ArrayList<Object> getListenerObjects();

    /**
     * Method used to get eventual data
     * @return
     */
    public String[] getData();

    /**
     * Method used to set the data to model
     * @param model
     */
    public void setData(Object[] model);
}

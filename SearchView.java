import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by User on 4/8/2018.
 */
public class SearchView extends JPanel{

    private JTextField isbnField, titleField, genreField, authorFirstNameField, authorLastNameField;
    private JButton searchButton;
    private JPanel content;
    private JRadioButton searchBook, searchAuthor;

    public SearchView() {}

    public JPanel getPanel() {

        setLayout(new GridLayout(0, 1, 5, 8));

        JLabel decision = new JLabel("What would you like to search for?");

        searchBook = new JRadioButton("Search for Book(s)", true);
        searchAuthor = new JRadioButton("Search for Author(s)");

        content = new JPanel(new GridLayout(1, 2));

        searchBook.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("uslo");
                    searchAuthor.setSelected(false);
                    content.removeAll();

                    JLabel isbn = new JLabel(" ISBN: ");
                    isbnField = new JTextField(12);

                    content.add(isbn);
                    content.add(isbnField);

                    JLabel bookTitle = new JLabel(" Book Title: ");
                    titleField = new JTextField(12);

                    content.add(bookTitle);
                    content.add(titleField);

                    JLabel bookGenre = new JLabel(" Book Genre: ");
                    genreField = new JTextField(12);

                    content.add(bookGenre);
                    content.add(genreField);

                    searchButton = new JButton("Search");
                    add(content);
                    add(searchButton);
                }//end of if
            }//end of ItemListener
        });

        searchAuthor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {

                    searchBook.setSelected(false);
                    content.removeAll();

                }
            }
        });

        setPreferredSize(new Dimension(400, 500));


        add(decision);
        add(searchBook);
        add(searchAuthor);

        return this;
    }

}

package Views;

import Models.Book;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookCatalogView implements View, ActionListener {

    private JPanel view;
    private String title;
    private int selectedId;             //selected book id
    private JScrollPane book_collection;
    private JLabel selected;
    private JButton next;
    private ArrayList<BookEntry> books = new ArrayList<>();

    public BookCatalogView(){

        this.title = "Book Catalog";
        selectedId = 0;

        configureView();

    }

    private void configureView() {
        view = new JPanel();
        view.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        selected = new JLabel("Selected: none");
        buttons.add(selected);
        next = new JButton("Next");
        buttons.add(next);
        view.add(buttons,BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        BookEntry entry = (BookEntry)(((JButton)e.getSource()).getParent());

        if(selectedId == 0) {
            selectedId = entry.getSelected();
            entry.select();
            this.selected.setText("Selected: " + entry.title.getText());
        }
        else if(selectedId != entry.getSelected()){
            for(BookEntry temp: books){
                if (temp.getSelected() == selectedId) temp.deselect();
            }
            selectedId = entry.getSelected();
            entry.select();
            this.selected.setText("Selected: " + entry.title.getText());
        }
        else {
            entry.deselect();
            selectedId = 0;
            this.selected.setText("Selected: none");
        }


    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public JPanel getView() {
        return this.view;
    }

    @Override
    public void registerListeners(ActionListener listener) {
        next.addActionListener(listener);
    }

    @Override
    public ArrayList<Object> getListenerObjects() {
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(next);

        return obj;
    }

    @Override
    public String[] getData() {
        return new String[]{selectedId+""};
    }

    @Override
    public void setData(Object[] model) {
        JPanel books = new JPanel();
        books.setLayout(new GridLayout(0,1));
        for(Object singularBook: model){
            BookEntry entry = new BookEntry((Book)singularBook);
            books.add(entry);
            this.books.add(entry);
            entry.registerInternalListeners(this);
        }

        book_collection = new JScrollPane(books);
        book_collection.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        view.add(book_collection, BorderLayout.CENTER);
    }

    class BookEntry extends JPanel{

        private int bookId;
        private JLabel title,author,genre,ISBN;
        private JButton select;
        public BookEntry(Book book){
            bookId = book.getId();

            this.title = new JLabel(book.getTitle());
            this.author = new JLabel(book.getAuthorFName() + " " + book.getAuthorLName());
            this.genre = new JLabel(book.getGenreName());
            this.ISBN = new JLabel(book.getISBN()+"");
            this.select = new JButton("Select");

            labelsToPanel();
        }

        public BookEntry(){
            this.title = new JLabel("Title");
            this.author = new JLabel("Author");
            this.genre = new JLabel("Genre");
            this.ISBN = new JLabel("ISBN");
            this.select = new JButton("        ");
            select.setEnabled(false);

            labelsToPanel();
        }

        private void labelsToPanel(){
            this.setLayout(new FlowLayout());
            int width = this.getWidth();

            this.title.setSize((int)(width*0.25),15);
            this.author.setSize((int)(width*0.25),15);
            this.genre.setSize((int)(width*0.15),15);
            this.ISBN.setSize((int)(width*0.25),15);

            this.add(this.title);
            this.add(this.author);
            this.add(this.ISBN);
            this.add(this.genre);
            this.add(this.select);
        }

        public void registerInternalListeners(ActionListener listener){
            select.addActionListener(listener);

        }

        public int getSelected(){
            return this.bookId;
        }

        public void deselect(){
            this.select.setText("Select");
        }

        public void select(){
            this.select.setText("Deselect");
        }

    }


}

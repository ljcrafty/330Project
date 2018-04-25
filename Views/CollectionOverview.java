package Views;

import Controllers.Injector;
import Models.Book;
import Models.Loan;
import Models.Reservation;
import Models.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class CollectionOverview implements View, ActionListener {

    private JPanel view;
    private String title, type, bookTitle, bookISBN;
    private int selectedId;             //selected book id
    private JScrollPane book_collection;
    private JLabel selected;
    private JButton next;
    private ArrayList<Entry> collection = new ArrayList<>();

    public CollectionOverview(String type){

        this.title = type + "Overview";
        selectedId = 0;
        this.type = type;
        configureView();

    }

    private void configureView() {
        view = new JPanel();
        view.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        selected = new JLabel("Selected: none");
        buttons.add(selected);
        
        if(type != "Loan" && type != "Reservation")
        {
            next = new JButton("Next");
            buttons.add(next);
        }
        
        view.add(buttons,BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        Entry entry = (Entry)(((JButton)e.getSource()).getParent());

        if(selectedId == 0) {
            selectedId = entry.getSelected();
            entry.select();
            this.selected.setText("Selected: " + entry.title);

            if(this.type == "Book")
            {
                this.bookTitle = entry.title;
                this.bookISBN = entry.getData().get(3).getText();
            }
        }
        else if(selectedId != entry.getSelected()){
            for(Entry temp: collection){
                if (temp.getSelected() == selectedId) temp.deselect();
            }
            selectedId = entry.getSelected();
            entry.select();
            this.selected.setText("Selected: " + entry.title);

            if(this.type == "Book")
            {
                this.bookTitle = entry.title;
                this.bookISBN = entry.getData().get(3).getText();
            }
        }
        else {
            entry.deselect();
            selectedId = 0;
            this.selected.setText("Selected: none");

            if(this.type == "Book")
            {
                this.bookTitle = "";
                this.bookISBN = "";
            }
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
        if(this.next != null)
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
        if(this.type == "Book")
            return new String[]{type, selectedId+"", this.bookTitle, this.bookISBN};

        return new String[]{type, selectedId+""};
    }

    @Override
    public void setData(Object[] model) {
        JPanel collection = new JPanel();
        collection.setLayout(new GridLayout(0,1));
        for(Object singularBook: model){
            Entry entry = null;
            switch(this.type){
                case "Book":{
                    entry = new Entry("Book",(Book)singularBook);
                }break;
                case "Loan": {
                    entry = new Entry("Loan", (Loan) singularBook);
                }break;
                case "Reservation":{
                    entry = new Entry("Reservation",(Reservation)singularBook);
                }break;
                case "User":{
                    entry = new Entry("User",(User)singularBook);
                }break;
            }

            collection.add(entry);
            this.collection.add(entry);
            entry.registerInternalListeners(this);
        }

        book_collection = new JScrollPane(collection);
        book_collection.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        view.add(book_collection, BorderLayout.CENTER);
    }

    class Entry extends JPanel{

        private int entryId;
//        private JLabel title,author,genre,ISBN;
        private ArrayList<JLabel> data = new ArrayList<>();
        private JButton select;
        private String title;

        public Entry(String type,Object data){

            switch(type){
                case "Book":{
                    addBookData((Book)data);
                }break;

                case "User":{
                    addUserData((User)data);
                }break;

                case "Loan":{
                    addLoanData((Loan)data);
                }break;

                case "Reservation":{
                    addReservationData((Reservation)data);
                }
            }


            labelsToPanel();
        }

        private void addBookData(Book book){
            entryId = book.getId();
            title = book.getTitle();

            this.data.add(new JLabel(book.getTitle()));
            this.data.add(new JLabel(book.getAuthorFName() + " " + book.getAuthorLName()));
            this.data.add(new JLabel(book.getGenreName()));
            this.data.add(new JLabel(book.getISBN()+""));
            this.select = new JButton("Select");
        }

        private void addUserData(User user){
            entryId = user.getId();
            title = user.getUsername();

            this.data.add(new JLabel(user.getId()+""));
            this.data.add(new JLabel(user.getFName() + " " + user.getLName()));
            this.data.add(new JLabel(user.getUsername()));
            this.select = new JButton("Select");
        }

        private void addLoanData(Loan loan){
            entryId = loan.getId();
            title = loan.getUserId() + "";
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            data.add(new JLabel(loan.getBook().getTitle()));
            data.add(new JLabel(loan.getBook().getAuthorFName()+" "+loan.getBook().getAuthorLName()));
            data.add( new JLabel(format.format(loan.getDueDate().getTime())) );

            User temp = Injector.getUser().getUser(loan.getUserId());
            data.add(new JLabel(temp.getFName()+temp.getLName()));
        }

        private void addReservationData(Reservation reservation){
            entryId = reservation.getId();
            title = reservation.getUserId() + "";
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            int userId = reservation.getUserId();
            User u = Injector.getUser().getUser(userId);
            String userFullName = u.getFName() + " " + u.getLName();

            data.add(new JLabel(userFullName));
            data.add(new JLabel(reservation.getBook().getTitle()));
            data.add(new JLabel(reservation.getBook().getfName()+ " "+ reservation.getBook().getlName()));
            data.add( new JLabel(format.format(reservation.getDateReserved())) );

        }

        private void labelsToPanel(){
            this.setLayout(new FlowLayout());

            for(JLabel label: data){
                this.add(label);
            }
            
            if(this.select != null)
               this.add(this.select);
        }

        public void registerInternalListeners(ActionListener listener){
            if(this.select != null)
               select.addActionListener(listener);

        }

        public ArrayList<JLabel> getData()
        {
            return this.data;
        }

        public int getSelected(){
            return this.entryId;
        }

        public void deselect(){
            if(this.select != null)
               this.select.setText("Select");
        }

        public void select(){
            if(this.select != null)
               this.select.setText("Deselect");
        }

    }


}

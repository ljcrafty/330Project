package Views;

import Controllers.AuthorController;
import Controllers.GenreController;
import Controllers.Injector;
import Controllers.UserController;
import Models.Author;
import Models.Genre;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchView implements View{

    private ArrayList<Component> components;
    private String type,title;
    private JPanel view;
    private JButton search;
    private int role;

    public SearchView(String type, int role){
        this.role = role;
        this.type = type;
        this.title = type + " Search";
        this.view = new JPanel();
        this.view.setLayout(new GridLayout(0,1));
        this.components = new ArrayList<>();

        switch(type){
            case "Book":{
                createBookSearch();
            }break;

            case "User":{
                createUserSearch();
            }break;
            case "Reservation":{
                createReservationSearch();
            }break;

        }

        addButtons();
    }

    private void addButtons() {
        JPanel buttons = new JPanel();
            buttons.setLayout(new FlowLayout());
            buttons.add(search = new JButton("Search"));

        view.add(buttons);
    }

    private void createBookSearch(){
        JComboBox author = new JComboBox();
        populateDropDown("author",author);
        JComboBox genre = new JComboBox();
        populateDropDown("genre",genre);

        JTextField title = new JTextField(10);

        this.components.add(author);
        this.components.add(genre);
        this.components.add(title);

        JPanel authorPanel = new JPanel();
            authorPanel.setLayout(new FlowLayout());
            authorPanel.add(new JLabel("Author"));
            authorPanel.add(author);

        JPanel genrePanel = new JPanel();
            genrePanel.setLayout(new FlowLayout());
            genrePanel.add(new JLabel("Genre"));
            genrePanel.add(genre);

        JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new FlowLayout());
            titlePanel.add(new JLabel("Title"));
            titlePanel.add(title);

        view.add(titlePanel);
        view.add(authorPanel);
        view.add(genrePanel);
    }

    private void createUserSearch(){
        JComboBox user = new JComboBox();
        populateDropDown("user",user);

        JTextField userId = new JTextField(10);
        JTextField username = new JTextField(10);

        components.add(user);
        components.add(userId);
        components.add(username);

        JPanel userPanel = new JPanel();
            userPanel.add(new JLabel("Full Name"));
            userPanel.add(user);

        JPanel idPanel = new JPanel();
            idPanel.add(new JLabel("User ID"));
            idPanel.add(userId);

        JPanel usernamePanel = new JPanel();
            usernamePanel.add(new JLabel("Username"));
            usernamePanel.add(username);

        view.add(userPanel);
        view.add(idPanel);
        view.add(usernamePanel);
    }

    private void createReservationSearch(){

        JTextField title = new JTextField(10);
        JComboBox author = new JComboBox();
        populateDropDown("author",author);

        components.add(title);
        components.add(author);

        JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new FlowLayout());
            titlePanel.add(new JLabel("Title"));
            titlePanel.add(title);

        JPanel authorPanel = new JPanel();
            authorPanel.setLayout(new FlowLayout());
            authorPanel.add(new JLabel("Author"));
            authorPanel.add(author);

        view.add(titlePanel);
        view.add(authorPanel);

        if(role == 1){
            JComboBox user = new JComboBox();
            populateDropDown("user",user);

            components.add(user);

            JPanel userPanel = new JPanel();
                userPanel.setLayout(new FlowLayout());
                userPanel.add(new JLabel("User"));
                userPanel.add(user);

            view.add(userPanel);
        }
    }

    private void populateDropDown(String type,JComboBox target){
        switch(type){
            case "author":{
                AuthorController ac = Injector.getAuthorController();
                ArrayList<Author> authors = ac.getAllAuthors();
                target.addItem("None Selected"); //not selected
                for(Author author: authors){
                    String name = author.getfName() + " " + author.getlName();
                    target.addItem(name);
                }
            }break;

            case "genre":{
                GenreController gc = Injector.getGenreController();
                ArrayList<Genre> genres = gc.getAllGenres();
                target.addItem("None Selected"); //not selected
                for(Genre genre: genres){
                    String name = genre.getName();
                    target.addItem(name);
                }
            }

            case "user":{
                UserController uc = Injector.getUser();
                ArrayList<User> users = uc.getAllUsers();
                target.addItem("None Selected");
                for(User user: users){
                    String name = user.getFName() + " " + user.getLName();
                    target.addItem(name);
                }
            }
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
        this.search.addActionListener(listener);
    }

    @Override
    public ArrayList<Object> getListenerObjects() {
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(search);

        return obj;
    }

    @Override
    public String[] getData() {
        String[] retVal = null;
        switch(this.type){
            case "Book":{
                retVal = new String[5];
                retVal[0] = type;
                String author = (String)(((JComboBox)components.get(0)).getSelectedItem());
                if (author.equals("None Selected")) {
                    retVal[1] = "";
                    retVal[2] = "";
                }
                else{
                    retVal[1] = author.split(" ")[0];
                    retVal[2] = author.split(" ")[1];
                }

                String genre = (String)(((JComboBox)components.get(1)).getSelectedItem());
                if(genre.equals("None Selected")){
                    retVal[3] = "";
                }
                else{
                    retVal[3] = genre;
                }

                retVal[4] = ((JTextField)components.get(2)).getText();

            }break;

            case "User":{
                retVal = new String[5];
                retVal[0] = type;
                String name = (String)(((JComboBox)components.get(0)).getSelectedItem());
                if(name.equals("None Selected")){
                    retVal[1] = "";
                    retVal[2] = "";
                }
                else{
                    retVal[1] = name.split(" ")[0];
                    retVal[2] = name.split(" ")[1];
                }

                for(int i = 3; i<5; i++){
                    retVal[i] = ((JTextField)(components.get(i-1))).getText();
                }

            }break;

            case "Reservation":{
                retVal = new String[6];

                retVal[0] = type;

                String author = (String)(((JComboBox)components.get(1)).getSelectedItem());
                if (author.equals("None Selected")) {
                    retVal[1] = "";
                    retVal[2] = "";
                }
                else{
                    retVal[1] = author.split(" ")[0];
                    retVal[2] = author.split(" ")[1];
                }

                String title = ((JTextField)components.get(0)).getText();
                retVal[3] = title;

                if(components.size() == 3){
                    String name = (String)(((JComboBox)components.get(3)).getSelectedItem());
                    if(name.equals("None Selected")){
                        retVal[4] = "";
                        retVal[5] = "";
                    }
                    else{
                        retVal[4] = name.split(" ")[0];
                        retVal[5] = name.split(" ")[1];
                    }
                }
                else{
                    retVal[4] = "OwnID";
                    retVal[5] = "OwnID";
                }


            }break;
        }

        return retVal;
    }

    @Override
    public void setData(Object[] model) {

    }
}

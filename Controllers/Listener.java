package Controllers;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import Models.*;
import Views.*;

public class Listener implements ActionListener
{
      private UserController uc;
      private BookDetailsController bdc;
      private AuthorController ac;
      private GenreController gc;
      private BookController bc;
      private Book bookToLoan;
      private String token;
      private int userId;
      private int userRole;
      
      public Listener()
      {
            this.uc = Injector.getUser();
            this.bdc = Injector.getBookDetailsController();
            this.ac = Injector.getAuthorController();
            this.gc = Injector.getGenreController();
            this.bc = Injector.getBookController();
            this.token = "";
            this.userId = 1;
            this.userRole = 1;
      }
      
      public void actionPerformed(ActionEvent e)
      {
            switch( e.getActionCommand() )
            {
                  case "start":
                        show(new LoginView(), e);
                        break;
                  
                  case "home":
                        bookToLoan = null;
                        home(e);
                        break;

                  case "save user":
                        saveUser(e);
                        break;

                  case "add book":
                        addBook(e);
                        break;

                  case "reserve":
                        reserve(e);
                        break;

                  case "loan search":
                  {
                        String[] data = this.getMainCont(e).getData();
                        bookToLoan = new Book( data[1], "", "", "", "", Integer.parseInt(data[0]), 
                              Long.parseLong(data[2]), Calendar.getInstance() );

                        View search = new SearchView("User",userRole);
                        show(search,e);
                        break;
                  }
                  case "Login":
                        login(e);            
                        break;

                  case "Next":
                        if( this.bookToLoan == null )
                              details(e);
                        else
                        {
                              this.loan(e);
                              bookToLoan = null;
                        }
                                 
                        break;
                  
                  case "Register":
                        show(new RegisterView(), e);
                        break;
                        
                  case "register with data":
                        register(e);            
                        break;
                  
                  case " My Profile":
                        profile(e);
                        break;
                        
                  case " Find Book":{
                        bookToLoan = null;
                        View search = new SearchView("Book",userRole);
                        show(search,e);
                  }break;
                  
                  case " My Loaned Books":
                        loans(e, "loans");
                        break;
                  
                  case " My Reservations":
                        loans(e, "reservations");
                        break;
                  
                  case "Search": {
                        String[] data = this.getMainCont(e).getData();
                        show(constructSearchResults(data),e);
                  }break;
                  
                  case " Add a Book":
                        show( new AddBookView(), e );
                        break;
                  
                  case " Add a User":
                        ProfileView temp = new ProfileView( new User() );
                        temp.setEdit(true);
                        show( temp, e );
                        break;

                  case " Search for a User":{
                        View search = new SearchView("User",userRole);
                        show(search,e);
                  }break;

                  case " Check Overdue Loans":{
                        ArrayList<Loan> loans = bc.getAllLoanDetails(true);
                        if(loans == null) loans = new ArrayList<>();    //in case there are no overdue books

                        Object[] data = new Object[loans.size()];

                        for(int i=0; i< loans.size(); i++){
                              data[i] = loans.get(i);
                        }

                        View v = new CollectionOverview("Loan");
                        v.setData(data);

                        show(v,e);
                  }break;

                  case " Search Reservations":{
                        View search = new SearchView("Reservation",userRole);
                        show(search,e);
                  }break;
                  default:
                        break;
            }//end switch
      }//end actionPerformed
      
      /**
       * Gets the MainContainer class that triggered an ActionEvent
       * @param e the ActionEvent that was triggered
       * @return the MainContainer associated with the ActionEvent
       */
      private MainContainer getMainCont(ActionEvent e)
      {
            JButton src = (JButton)e.getSource();
            MainContainer cont = (MainContainer) SwingUtilities.getRoot(src);
            return cont;
      }
   
      /**
       * Shows the given view to the user
       * @param view the view to show
       * @param e the event that triggered the action (to get the container to add the view to)
       */
      private void show(View view, ActionEvent e)
      {
            view.registerListeners(this);
            MainContainer cont = this.getMainCont(e);
            cont.addView(view);
      }

      /**
       * Shows an error to the user
       * @param msg the error message to show the user
       * @param title the title of the window that will show the message
       */
      private void error(String msg, String title)
      {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                  msg, title, JOptionPane.ERROR_MESSAGE);
      }

      /**
       * Brings a user to the main page depending on their authorization
       * @param e the event that triggered the addition
       */
      private void home(ActionEvent e)
      {
            if( uc.authenticate(this.userId + "", 1) )
            {
                  show( new MainView("librarian"), e );
            }
            else
            {
                  show( new MainView("user"), e );
            }
      }

      private void details(ActionEvent e)
      {
            String[] data = this.getMainCont(e).getData();
            String type = data[0];

            switch(type)
            {
                  case "Book":
                        Book book = new Book( data[2], "", "", "", "", Integer.parseInt(data[1]), 
                              Long.parseLong(data[3]), Calendar.getInstance() );
                        int bdId = bc.getBookDetailId(book);
                        Book realBook = bc.getABook(book, bdId);
                        BookView bv = new BookView(realBook, this.userRole);
                        show(bv, e);
                        break;

                  case "User":
                        User user = uc.getUser(Integer.parseInt(data[1]));
                        ProfileView pv = new ProfileView(user, false);
                        show(pv, e);
                        break;
            }
      }

      private void reserve(ActionEvent e)
      {
            String[] data = this.getMainCont(e).getData();
            Book book = new Book( data[1], "", "", "", "", 0, Long.parseLong(data[2]), Calendar.getInstance() );
            int id = bc.getBookDetailId(book);
            BookDetails details = bdc.getABook(id);

            if( bc.placeReservation( details, this.userId ) )
            {
                  this.getMainCont(e).goBack();
            }
            else
            {
                  error("Your reservation could not be added", "Error");
            }
      }

      private void loan(ActionEvent e)
      {
            //from search, include userId
            String[] data = this.getMainCont(e).getData();

            if( bc.loanBook( bookToLoan, Integer.parseInt(data[1])) )
            {
                  home(e);
            }
            else
            {
                  error("The loan could not be completed at this time", "Error");
            }
      }

      private void loans(ActionEvent e, String type)
      {
            LoansView lv = new LoansView(type);

            //get data
            if(type == "loans")
            {
                  Loan[] loans = bc.getLoans(this.userId);
                  lv.setData(loans);
            }
            else
            {
                  Reservation[] reservations = bc.getReservations(this.userId);
                  lv.setData(reservations);
            }

            //show view
            show( lv, e );
      }

      /**
       * Adds a new book to the database
       * @param e the event that triggered the addition
       */
      private void addBook(ActionEvent e)
      {
            String[] bookData = this.getMainCont(e).getData();
            String error = "";
            Calendar dob = Calendar.getInstance();
            
            //validate date
            if( !bookData[6].equals("") && !bookData[7].equals("") && !bookData[8].equals("") )
            {
                  int day = Integer.parseInt(bookData[6]);
                  int mo = Integer.parseInt(bookData[7]);
                  int yr = Integer.parseInt(bookData[8]);
                  dob.set(yr, mo, day);
            }
            else
                  error += "release date\n";

            //validate other
            error += (bookData[0].equals("")? "ISBN\n" : "");
            error += (bookData[1].equals("")? "Title\n" : "");
            error += (bookData[2].equals("")? "Author first name\n" : "");
            error += (bookData[3].equals("")? "Author last name\n" : "");
            error += (bookData[4].equals("")? "Genre name\n" : "");
            error += (bookData[5].equals("")? "Number of Copies\n" : "");
            
            //show errors
            if( !error.equals("") )
            {
                  JFrame frame = new JFrame();
                  error("Invalid input. Please fix the following fields:\n" + error, "Invalid input");
            }
            else
            {
                  //get author and genre details to get id
                  Author aut = ac.getAuthor( bookData[2], bookData[3] );
                  Genre gen = gc.getGenre(bookData[4]);

                  BookDetails temp = new BookDetails(0, Long.parseLong(bookData[0]), 
                        Integer.parseInt(bookData[5]), bookData[1], bookData[2], bookData[3], 
                        bookData[4], dob);
                  
                  //try to add book
                  if( bdc.addABook(temp, aut.getId(), gen.getId()) )
                  {
                        home(e);
                  }
                  else
                  {
                        JFrame frame = new JFrame();
                        error( "Cannot add data", "Creation error" );
                  }
            }
      }

      /**
       * Saves a user's data into the database or adds them if necessary
       * @param e the event that triggered the addition
       */
      private void saveUser(ActionEvent e)
      {
            String[] userData = this.getMainCont(e).getData();
            
            //if the user in getData has an id, it's an update call
            if( !userData[0].equals("0") )
            {
                  Calendar dob = Calendar.getInstance();
                  int day = Integer.parseInt(userData[5]);
                  int mo = Integer.parseInt(userData[6]);
                  int yr = Integer.parseInt(userData[7]);
                  dob.set(yr, mo, day);
                  
                  User user = new User( userData[1], userData[2], userData[3], 
                     userData[4], Integer.parseInt(userData[8]), dob,
                     Integer.parseInt(userData[0]) );

                  if( uc.updateUser(user) )
                  {
                        home(e);
                  }
                  else
                  {
                        error("There was a problem adding your data", "Database Error");
                  }
            }
            else //otherwise, it's an insert
            {
                  String date = userData[7] + "-" + userData[6] + "-" + userData[5];
                  userData[5] = date;
                  userData[6] = userData[8];
                  userData[7] = "";
                  userData[8] = "";
                  
                  java.util.List<String> temp = Arrays.asList(userData);
                  ArrayList<String> data = new ArrayList<String>();
                  data.addAll(temp);
                  User user = new User(data);

                  if( uc.addUser(user) )
                  {
                        home(e);
                  }
                  else
                  {
                        error("There was a problem adding your data", "Database Error");
                  }
            }
      }
   
      /**
       * Shows the profile view after a check
       * @param e the ActionEvent that was triggered
       */
      private void profile(ActionEvent e)
      {
            if( this.userId != 0 )
            {
                  //get data for view
                  User temp = uc.getUser(this.userId);
                  //User temp = new User("lauren", "randomStuff", "Lauren", "Johnston", 1, Calendar.getInstance(), 1);
                  View pview = new ProfileView(temp);
            
                  show( pview, e );
            }
            else //somehow they got past the login
            {
                  JFrame frame = new JFrame();
                  error("Please login to see your profile", "Invalid credentials");
                  show( new LoginView(), e );
            }
      }
      
      /**
       * Attempts to log in given the event
       * @param e the ActionEvent that was triggered
       */
      private void login(ActionEvent e)
      {
            //check credentials
            String[] fields = this.getMainCont(e).getData();
            String username = fields[0];
            String pass = fields[1];
            String token = uc.login( username, pass );
            
            //credentials were good, log them in
            if( !token.equals("no user") && !token.equals("invalid password") )
            {
                  this.userId = Integer.parseInt(token);
            
                  if( uc.authenticate(token, 1) )
                  {
                        show( new MainView("librarian"), e );
                        userRole = 2;
                  }
                  else
                  {
                        show( new MainView("user"), e );
                        userRole = 1;
                  }
            }
            else //failed login, give error
            {
            JFrame frame = new JFrame();
            error("Invalid credentials", "Login error");
            }
      }

      /**
       * Attempts to register given the event
       * @param e the ActionEvent that was triggered
       */
      private void register(ActionEvent e)
      {
            //get data
            String error = "";
            
            String[] fields = this.getMainCont(e).getData();
            Calendar dob = Calendar.getInstance();
            
            //validate date
            if( !fields[4].equals("") && !fields[5].equals("") && !fields[6].equals("") )
            {
                  int day = Integer.parseInt(fields[4]);
                  int mo = Integer.parseInt(fields[5]);
                  int yr = Integer.parseInt(fields[6]);
                  dob.set(yr, mo, day);
            }
            else
                  error += "date of birth\n";

            //validate other
            error += (fields[0].equals("")? "username\n" : "");
            error += (fields[1].equals("")? "password\n" : "");
            error += (fields[2].equals("")? "first name\n" : "");
            error += (fields[3].equals("")? "last name\n" : "");
            
            //show errors
            if( !error.equals("") )
            {
                  JFrame frame = new JFrame();
                  error("Invalid input. Please fix the following fields:\n" + error, "Invalid input");
            }
            else
            {
                  //make user
                  User temp = new User(fields[0], fields[1], fields[2], fields[3], 2, dob, 1);
                  
                  //try to add user
                  if( uc.addUser(temp) )
                  {
                        show( new LoginView(), e);
                  }
                  else
                  {
                        JFrame frame = new JFrame();
                        error( "Cannot add data", "Creation error" );
                  }
            }
      }

      private View constructSearchResults(String[] searchParams){
            String type = searchParams[0];

            CollectionOverview overview = new CollectionOverview(type);
            ArrayList results = null;
            switch(type){
                  case "Book":{
                        results = bc.searchBooks(searchParams);
                  }break;
                  case "Loan":{
                        results = bc.searchLoans(searchParams);
                  }break;
                  case "Reservation":{
                        System.out.println(bc.searchReservations(searchParams));
                        results = bc.searchReservations(searchParams);
                  }break;
                  case "User":{
                        results = uc.searchUsers(searchParams);
                  }break;
            }

            Object[] data = new Object[results.size()];

            for(int i=0; i< results.size(); i++){
                  data[i] = results.get(i);
            }

            overview.setData(data);

            return overview;
      }
}//end class
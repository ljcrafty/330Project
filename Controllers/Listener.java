package Controllers;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import Models.*;
import Views.*;

public class Listener implements ActionListener
{
      private HashMap<String, JTextField> fields;
      private UserController uc;
      private String token;
      private int userId;
      
      public Listener()
      {
            this.uc = new UserController();
            this.token = "";
            this.userId = 1;
      }
      
      public void actionPerformed(ActionEvent e)
      {
            switch( e.getActionCommand() )
            {
                  case "home":
                        if( uc.authenticate(token, 1) )
                        {
                              show( new MainView("librarian"), e );
                        }
                        else
                        {
                              show( new MainView("user"), e );
                        }
                        break;

                  case "save user":
                        //show();
                        break;

                  case "Login":
                        login(e);            
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
                        
                  case " Search By Author":
                        break;
                        
                  case " Search By Title":
                        break;
                  
                  case " Search By Genre":
                        break;
                  
                  case " My Loaned Books":
                        
                        break;
                  
                  case " My Reservations":
                        
                        break;
                  
                  case " Search for a User":
                        
                        break;
                  
                  case " Check Overdue Loans":
                        
                        break;
                  
                  case " Add a Book":
                        
                        break;
                  
                  case " Add a User":
                        
                        break;
                  
                  case "start":
                        show(new LoginView(), e);
                        break;
                  
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
       * Shows the profile view after a check
       * @param e the ActionEvent that was triggered
       */
      private void profile(ActionEvent e)
      {
            if( this.userId != 0 )
            {
                  //get data for view
                  //User temp = uc.getUser(this.userId);
                  User temp = new User("lauren", "randomStuff", "Lauren", "Johnston", 1, Calendar.getInstance(), 1);
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
                  }
                  else
                  {
                        show( new MainView("user"), e );
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
            //TODO: validate better
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
                  User temp = new User(fields[0], fields[1], fields[2], fields[3], 1, dob, 1);
                  
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
}//end class
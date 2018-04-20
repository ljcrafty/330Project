import Views.*;
import Controllers.*;

public class Start
{
   public static void main(String args[])
   {
      MainContainer mainContainer = new MainContainer();
      View v = new LoginView();//new MainView("librarian");//LoginView();
      v.registerListeners(new Listener());
      
      mainContainer.addView(v);
      
      /*
      view setup code:
      
      JPanel jp = new JPanel();
        LoginView lv = new LoginView();
        MainView libMain = new MainView("librarian");
        MainView usrMain = new MainView("user");
        RegisterView rv = new RegisterView();
      
        //configure card layout
        CardLayout cards = new CardLayout(5, 5);      
        jp.setLayout(cards);
        jp.add(lv, "login");
        jp.add(libMain, "librarianMain");
        jp.add(usrMain, "userMain");
        jp.add(rv, "register");
      
        cards.show(jp, "login");
        setContentPane(jp);
        

      */
   }
}
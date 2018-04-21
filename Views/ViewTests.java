package Views;

import Controllers.Listener;
import Models.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class ViewTests implements ActionListener {

    private MainContainer mainContainer;

    public ViewTests(){
        mainContainer = new MainContainer();
//        addView(createBookCatalog());
//        mainContainer.repaint();
//        addView(getRegister());
          addView(getSearch(JOptionPane.showInputDialog("Book/Loan/Reservation/User"),Integer.parseInt(JOptionPane.showInputDialog("1 - Admin, 2-User"))));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "Login" :{
                //View test = new ProfileView(0);
                //test.registerListeners(this);
                //addView(test);

            }break;

            case "Next":{
                JOptionPane.showMessageDialog(null,mainContainer.getData()[0]);
            }break;




            default:{
                JOptionPane.showMessageDialog(null,command);
            }
        }
    }

    private void addView(View view){
        mainContainer.addView(view);
    }

    private View createLoginView(){
        View v = new LoginView();
        v.registerListeners(this);
        return v;
    }

    private View createBookCatalog(){
        Book[] books = new Book[8];

        for(int i = 1; i<9; i++){


            String placeh = "test"+i;
            Book b = new Book(placeh,placeh,placeh,placeh,placeh,i,i,null);
            books[i-1] = b;
        }

        BookCatalogView bookCatalogView = new BookCatalogView();
        bookCatalogView.setData(books);
        bookCatalogView.registerListeners(this);

        return bookCatalogView;
    }

    private View getRegister(){
        View v = new RegisterView();
        v.registerListeners(new Listener());
        return v;
    }

    private View getSearch(String type, int role){
        View v = new SearchView(type, role);
        v.registerListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] data = mainContainer.getData();
                for(String entry: data){
                    System.out.println("Entry: "+entry);
                }
            }
        });

        return v;
    }

    public static void main(String args[]){
        new ViewTests();
    }
}

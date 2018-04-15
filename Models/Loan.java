package Models;

import java.util.ArrayList;
import java.util.Calendar;

public class Loan {



    int id;
    private Book book;
    private int userId;
    private Calendar dueDate;

    public Loan(int id, Book book, int userId, Calendar dueDate){
        this.id = id;
        this.book = book;
        this.userId = userId;
        this.dueDate = dueDate;
    }

    public Loan(ArrayList<String> dbResult){
        this.id = Integer.parseInt(dbResult.get(11));

        Calendar temp = Calendar.getInstance();
        String date = dbResult.get(14).split( " " )[0];
        int yr = Integer.parseInt( date.split("-")[0] );
        int mo = Integer.parseInt( date.split("-")[1] ) - 1;
        int day = Integer.parseInt( date.split("-")[2] );
        temp.set( yr, mo, day ); //YYYY-MM-DD
        book = new Book(dbResult.get(6),dbResult.get(7),dbResult.get(8),dbResult.get(9),dbResult.get(10),Integer.parseInt(dbResult.get(4)),Integer.parseInt(dbResult.get(5)),temp);
        userId = Integer.parseInt(dbResult.get(0));



        temp = Calendar.getInstance();
        date = dbResult.get(13).split( " " )[0];
        yr = Integer.parseInt( date.split("-")[0] );
        mo = Integer.parseInt( date.split("-")[1] ) - 1;
        day = Integer.parseInt( date.split("-")[2] );

        dueDate = temp;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }
}

package Models;

import java.util.ArrayList;
import java.util.Calendar;

public class Reservation {

    private BookDetails book;
    private User user;
    private Calendar dateReserved;

    public Reservation(BookDetails book, User user, Calendar date){
        this.book = book;
        this.user = user;
        this.dateReserved = date;
    }

    public Reservation(ArrayList<String> dbResult){

        Calendar temp = Calendar.getInstance();
        String date = dbResult.get(3).split( " " )[0];
        int yr = Integer.parseInt( date.split("-")[0] );
        int mo = Integer.parseInt( date.split("-")[1] ) - 1;
        int day = Integer.parseInt( date.split("-")[2] );
        temp.set( yr, mo, day ); //YYYY-MM-DD

        this.book = new BookDetails(Integer.parseInt(dbResult.get(0)),Long.parseLong(dbResult.get(1)),Integer.parseInt(dbResult.get(4)),dbResult.get(2),dbResult.get(5),dbResult.get(6),dbResult.get(7),temp);
    
        Calendar resDate = Calendar.getInstance();
        String da = dbResult.get(10).split( " " )[0];
        yr = Integer.parseInt( da.split("-")[0] );
        mo = Integer.parseInt( da.split("-")[1] ) - 1;
        day = Integer.parseInt( da.split("-")[2] );
        resDate.set( yr, mo, day ); //YYYY-MM-DD
        this.dateReserved = resDate;
        
        this.user = new User( Integer.parseInt(dbResult.get(11)), "", "", "1" );
    }

    public int getUserId(){
        return user.getId();
    }

    public int getBookId(){
        return book.getBook_id();
    }

    public BookDetails getBook() {
        return book;
    }

    public void setBook(BookDetails book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(Calendar dateReserved) {
        this.dateReserved = dateReserved;
    }
}

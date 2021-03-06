package Models;

import java.util.ArrayList;
import java.util.Calendar;

public class BookDetails {
    private int book_id;
    private int numCopies;
    private String title, fName, lName, genre;
    private long isbn;
    private Calendar releaseDate;

    public BookDetails(int book_id, long isbn, int numCopies,String title, String fName, String lName, String genre, Calendar releaseDate) {
        this.book_id = book_id;
        this.isbn = isbn;
        this.title = title;
        this.fName = fName;
        this.lName = lName;
        this.genre = genre;
        this.numCopies = numCopies;
        this.releaseDate = releaseDate;
    }

    public BookDetails(int book_id, long isbn, Calendar releaseDate, int numCopies, Genre genre, Author author){
        this.book_id = book_id;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.numCopies = numCopies;

        this.fName = author.getfName();
        this.lName = author.getlName();

        this.genre = genre.getName();
    }

    public BookDetails(ArrayList<String> data){
        this.book_id = Integer.parseInt(data.get(0));
        this.isbn = Long.parseLong(data.get(1));
        this.title = data.get(2);

        Calendar temp = Calendar.getInstance();
        String date = data.get(3).split( " " )[0];
        int yr = Integer.parseInt( date.split("-")[0] );
        int mo = Integer.parseInt( date.split("-")[1] ) - 1;
        int day = Integer.parseInt( date.split("-")[2] );
        temp.set( yr, mo, day ); //YYYY-MM-DD
        this.releaseDate = temp;

        this.numCopies = Integer.parseInt(data.get(4));

        this.fName = data.get(5);
        this.lName = data.get(6);

        this.genre = data.get(7);
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }

}

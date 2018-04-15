package Controllers;


public class Injector {


    private static final MySQLDatabase dbController = new MySQLDatabase("mysql", "3306", "localhost",
            "Library", "library", "V3ry5ecretC0de");

    private static final AuthorController authorController = new AuthorController();

    private static final BookDetailsController bookDetailsController = new BookDetailsController();

    private static final BookController bookController = new BookController();

    private static final GenreController genreController = new GenreController();

    private static final UserController user = new UserController();


    public static MySQLDatabase getDbController() {
        return dbController;
    }

    public static AuthorController getAuthorController() {
        return authorController;
    }

    public static BookDetailsController getBookDetailsController() {
        return bookDetailsController;
    }

    public static BookController getBookController() {
        return bookController;
    }

    public static GenreController getGenreController() {
        return genreController;
    }

    public static UserController getUser() {
        return user;
    }



}

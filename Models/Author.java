package Models;

import java.util.ArrayList;

public class Author {
    private int id;
    private String fName, lName;

    public Author(int id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }

    public Author(ArrayList<String> data){
        this.id = Integer.parseInt(data.get(0));
        this.fName = data.get(1);
        this.lName = data.get(2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

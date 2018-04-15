package Models;

import java.util.ArrayList;

public class Genre {
    private String name, description;
    private int id;

    public Genre(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Genre(ArrayList<String> data){
        this.id = Integer.parseInt(data.get(0));
        this.name = data.get(1);
        this.description = data.get(2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

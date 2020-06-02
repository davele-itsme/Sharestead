package dk.via.sharestead.model;

import com.google.gson.annotations.SerializedName;

public class Developer {
    private int id;
    private String name;

    public Developer(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

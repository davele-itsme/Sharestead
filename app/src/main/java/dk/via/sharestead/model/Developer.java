package dk.via.sharestead.model;

public class Developer {
    private int id;
    private String name;
    private String imageBackground;

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

package dk.via.sharestead.model;

public class Platform {
    private int id;
    private String name;

    public Platform(int id, String name)
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

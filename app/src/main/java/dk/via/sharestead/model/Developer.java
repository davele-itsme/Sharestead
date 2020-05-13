package dk.via.sharestead.model;

public class Developer {
    private int id;
    private String name;
    private String imageBackground;

    public Developer(int id, String name, String imageBackground)
    {
        this.id = id;
        this.name = name;
        this.imageBackground = imageBackground;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageBackground() {
        return imageBackground;
    }
}

package dk.via.sharestead.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    private int id;
    private String name;
    @SerializedName("image_background")
    private String imageBackground;

    public Genre(int id, String name, String imageBackground)
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

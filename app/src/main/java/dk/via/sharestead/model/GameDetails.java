package dk.via.sharestead.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameDetails {
    private int id;
    private String name;
    private String released;
    @SerializedName("background_image")
    private String backgroundImage;
    @SerializedName("background_image_additional")
    private String backgroundImageAdditional;
    @SerializedName("description_raw")
    private String description;
    private int metacritic;

    private ArrayList<Developer> developers;
    @SerializedName("parent_platforms")
    private ArrayList<Platform> platforms;
    private ArrayList<Genre> genres;

    public GameDetails(int id, String name, String released, String backgroundImage, String backgroundImageAdditional, String description, int metacritic,
                       ArrayList<Developer> developers, ArrayList<Platform> platforms, ArrayList<Genre> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.backgroundImage = backgroundImage;
        this.backgroundImageAdditional = backgroundImageAdditional;
        this.description = description;
        this.metacritic = metacritic;
        this.developers = developers;
        this.platforms = platforms;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleased() {
        return released;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getBackgroundImageAdditional() {
        return backgroundImageAdditional;
    }

    public String getDescription() {
        return description;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public ArrayList<Developer> getDevelopers() {
        return developers;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }


}

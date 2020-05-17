package dk.via.sharestead.model;

import java.util.ArrayList;

public class GameDetails {
    private int id;
    private String name;
    private String released;
    private String backgroundImage;
    private String backgroundImageAdditional;
    private String description;
    private int metacritic;

    private Developer developer;
    private ArrayList<Platform> platforms;
    private ArrayList<Genre> genres;

    public GameDetails(int id, String name, String released, String backgroundImage, String backgroundImageAdditional, String description, int metacritic,
                       Developer developer, ArrayList<Platform> platforms, ArrayList<Genre> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.backgroundImage = backgroundImage;
        this.backgroundImageAdditional = backgroundImageAdditional;
        this.description = description;
        this.metacritic = metacritic;
        this.developer = developer;
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

    public Developer getDeveloper() {
        return developer;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }


}

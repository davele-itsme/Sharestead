package dk.via.sharestead.model;

import java.util.ArrayList;

public class Game {
    private int id;
    private String name;
    private String released;
    private String backgroundImage;
    private String description;
    private int metacritic;
    private ArrayList<Developer> developers;
    private ArrayList<Platform> platforms;
    private ArrayList<Genre> genres;
    public Game(int id, String name, String released, String backgroundImage, String description, int metacritic, ArrayList<Developer> developers, ArrayList<Platform> platforms, ArrayList<Genre> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.backgroundImage = backgroundImage;
        this.description = description;
        this.metacritic = metacritic;
        this.developers = developers;
        this.platforms = platforms;
        this.genres = genres;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }
}

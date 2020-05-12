package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dk.via.sharestead.model.Developer;
import dk.via.sharestead.model.Game;
import dk.via.sharestead.model.Genre;
import dk.via.sharestead.model.Platform;

public class GameResponse {
    private int id;
    private String name;
    private String released;
    @SerializedName("background_image")
    private String backgroundImage;
    private String description;
    private int metacritic;

    private ArrayList<DeveloperValue> developers;
    @SerializedName("parent_platforms")
    private ArrayList<PlatformValue> platforms;
    private ArrayList<GenreValue> genres;

    private class DeveloperValue {
        private int id;
        private String name;
        @SerializedName("image_background")
        private String imageBackground;
    }

    private class PlatformValue {
        private int id;
        private String name;
    }

    private class GenreValue {
        private int id;
        private String name;
        @SerializedName("image_background")
        private String imageBackground;
    }

    public Game getGame() {
        ArrayList<Developer> developerArrayList = new ArrayList<>();
        ArrayList<Platform> platformArrayList = new ArrayList<>();
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        for (DeveloperValue developerValue : developers) {
            developerArrayList.add(new Developer(developerValue.id, developerValue.name, developerValue.imageBackground));
        }
        for (PlatformValue platformValue : platforms) {
            platformArrayList.add(new Platform(platformValue.id, platformValue.name));
        }
        for (GenreValue genreValue : genres) {
            genreArrayList.add(new Genre(genreValue.id, genreValue.name, genreValue.imageBackground));
        }
        return new Game(id, name, released, backgroundImage, description, metacritic, developerArrayList, platformArrayList, genreArrayList);
    }


}

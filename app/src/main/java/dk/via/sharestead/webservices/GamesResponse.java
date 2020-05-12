package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.Developer;
import dk.via.sharestead.model.Genre;
import dk.via.sharestead.model.Platform;

public class GamesResponse {
    private List<Game> results = new ArrayList<>();

    public List<Game> getGames()
    {
        return results;
    }

    public class Game{
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

        public String getDescription() {
            return description;
        }

        public int getMetacritic() {
            return metacritic;
        }

        public ArrayList<DeveloperValue> getDevelopers() {
            return developers;
        }

        public ArrayList<PlatformValue> getPlatforms() {
            return platforms;
        }

        public ArrayList<GenreValue> getGenres() {
            return genres;
        }
    }

    private class DeveloperValue {
        private int id;
        private String name;
        @SerializedName("image_background")
        private String imageBackground;

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

    private class PlatformValue {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private class GenreValue {
        private int id;
        private String name;
        @SerializedName("image_background")
        private String imageBackground;

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
}

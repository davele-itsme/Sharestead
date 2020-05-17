package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.via.sharestead.model.Developer;
import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.model.Genre;
import dk.via.sharestead.model.Platform;

public class GamesDetailsResponse {
    private int id;
    private String name;
    private String released;
    @SerializedName("background_image")
    private String backgroundImage;
    private String description;
    private int metacritic;
    private ArrayList<DeveloperValue> developers = new ArrayList<>();
    @SerializedName("parent_platforms")
    private ArrayList<PlatformValue> platforms = new ArrayList<>();
    @SerializedName("genres")
    private ArrayList<GenreValue> genres = new ArrayList<>();

    public GameDetails getGameDetails()
    {
        ArrayList<Developer> developerArrayList = new ArrayList<>();
        ArrayList<Platform> platformArrayList = new ArrayList<>();
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        for(DeveloperValue developerValue:developers)
        {
            developerArrayList.add(new Developer(developerValue.id, developerValue.name, developerValue.imageBackground));
        }
        for(PlatformValue platformValue:platforms)
        {
            platformArrayList.add(new Platform(platformValue.id, platformValue.name));
        }
        for(GenreValue genreValue: genres)
        {
            genreArrayList.add(new Genre(genreValue.id, genreValue.name, genreValue.imageBackground));
        }
        return new GameDetails(id, name, released, backgroundImage, description, metacritic, developerArrayList, platformArrayList, genreArrayList);
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

    public String getDescription() {
        return description;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public ArrayList<PlatformValue> getPlatforms() {
        return platforms;
    }

    public ArrayList<GenreValue> getGenres() {
        return genres;
    }

    public ArrayList<DeveloperValue> getDevelopers() {
        return developers;
    }

    private class DeveloperValue{
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

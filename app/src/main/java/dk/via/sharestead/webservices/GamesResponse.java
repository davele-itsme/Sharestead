package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.Developer;
import dk.via.sharestead.model.Game;
import dk.via.sharestead.model.Genre;
import dk.via.sharestead.model.Platform;

public class GamesResponse {
    private List<GameValue> results = new ArrayList<>();

    public List<Game> getGames() {
        List<Game> newGames = new ArrayList<>();
        for (GameValue game : results) {
            ArrayList<Developer> developers = new ArrayList<>();
            ArrayList<Platform> platforms = new ArrayList<>();
            ArrayList<Genre> genres = new ArrayList<>();
//            for(GameValue.DeveloperValue developerValue : game.developers)
//            {
//                Developer developer = new Developer(developerValue.id, developerValue.name, developerValue.imageBackground);
//                developers.add(developer);
//            }
//            for(GameValue.PlatformValue platformValue : game.platforms)
//            {
//                Platform platform = new Platform(platformValue.id, platformValue.name);
//                platforms.add(platform);
//            }
//            for(GameValue.GenreValue genreValue : game.genres)
//            {
//                Genre genre = new Genre(genreValue.id, genreValue.name, genreValue.imageBackground);
//                genres.add(genre);
//            }
            Game newGame = new Game(game.id, game.name, game.released, game.backgroundImage, game.description, game.metacritic, developers, platforms, genres);
            newGames.add(newGame);
        }
        return newGames;
    }


    private class GameValue {
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
        private ArrayList<GenreValue> genres = new ArrayList<>();

        public GameValue(int count) {
            this.name = String.valueOf(count);
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

        public ArrayList<DeveloperValue> getDevelopers() {
            return developers;
        }

        public ArrayList<PlatformValue> getPlatforms() {
            return platforms;
        }

        public ArrayList<GenreValue> getGenres() {
            return genres;
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
}

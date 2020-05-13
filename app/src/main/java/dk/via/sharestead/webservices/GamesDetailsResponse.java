package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.model.Genre;
import dk.via.sharestead.model.Platform;

public class GamesDetailsResponse {
    private List<GameValue> results = new ArrayList<>();

    public List<GameDetails> getGames() {
        List<GameDetails> details = new ArrayList<>();
        for (GameValue game : results) {
            ArrayList<Platform> platforms = new ArrayList<>();
            ArrayList<Genre> genres = new ArrayList<>();

            for (GameValue.PlatformValue platformValue : game.platforms) {
                Platform platform = new Platform(platformValue.id, platformValue.name);
                platforms.add(platform);
            }
            for (GameValue.GenreValue genreValue : game.genres) {
                Genre genre = new Genre(genreValue.id, genreValue.name, genreValue.imageBackground);
                genres.add(genre);
            }

            GameDetails newGameDetails = new GameDetails(game.id, game.name, game.released, game.backgroundImage, game.description, game.metacritic, platforms, genres);
            details.add(newGameDetails);
        }
        return details;
    }

    public class GameValue {
        private int id;
        private String name;
        private String released;
        @SerializedName("background_image")
        private String backgroundImage;
        private String description;
        private int metacritic;

        @SerializedName("parent_platforms")
        private ArrayList<PlatformValue> platforms = new ArrayList<>();
        @SerializedName("genres")
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

        public ArrayList<PlatformValue> getPlatforms() {
            return platforms;
        }

        public ArrayList<GenreValue> getGenres() {
            return genres;
        }

        public class PlatformValue {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public class GenreValue {
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

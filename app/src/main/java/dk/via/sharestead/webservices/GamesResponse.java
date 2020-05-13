package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.Game;

public class GamesResponse {
    private List<GameValue> results = new ArrayList<>();

    public List<Game> getGames()
    {
        List<Game> newGameDetails = new ArrayList<>();
        for(GameValue gameValue:results)
        {
            newGameDetails.add(new Game(gameValue.id, gameValue.name, gameValue.backgroundImage));
        }
        return newGameDetails;
    }



    private class GameValue{
        private int id;
        private String name;
        @SerializedName("background_image")
        private String backgroundImage;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBackgroundImage() {
            return backgroundImage;
        }
    }

}

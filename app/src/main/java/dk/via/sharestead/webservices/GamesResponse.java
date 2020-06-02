package dk.via.sharestead.webservices;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.Game;

public class GamesResponse {
    private List<Game> results = new ArrayList<>();

    public GamesResponse()
    {

    }

    public List<Game> getResults() {
        return results;
    }
}

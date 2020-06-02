package dk.via.sharestead.webservices;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.model.Platform;

public class PlatformResponse {
    private List<Platform> results = new ArrayList<>();

    public int getPlatformId(String platform) {
        int platformId = 0;
        for (Platform platformValue : results) {
            if(platformValue.getName().contains(platform)){
                platformId = platformValue.getId();
                break;
            }
        }
        return platformId;
    }

    public List<Platform> getResults() {
        return results;
    }
}

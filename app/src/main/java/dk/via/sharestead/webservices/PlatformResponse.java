package dk.via.sharestead.webservices;

import java.util.ArrayList;
import java.util.List;

public class PlatformResponse {
    private List<PlatformValue> results = new ArrayList<>();

    public int getPlatformId(String platform){
//        if(platform.equals(""))
        return 0;
    }

    private class PlatformValue{
        private int id;
        private String name;
    }
}

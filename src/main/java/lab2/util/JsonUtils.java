package lab2.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtils {
    public static int getTotalCountFromNewsfeedResponse(String json) {
        JsonElement elem = JsonParser.parseString(json);

        assert elem.isJsonObject();

        return elem.getAsJsonObject()
                .get("response").getAsJsonObject()
                .get("total_count").getAsInt();
    }
}

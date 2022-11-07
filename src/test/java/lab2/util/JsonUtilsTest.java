package lab2.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    private final String testResponse = """
            {
                "response":{
                    "count":1,
                    "items":[],
                    "total_count":1
                }
            }""";

    @Test
    public void getTotalCountFromNewsfeedResponse() {
        assertThat(JsonUtils.getTotalCountFromNewsfeedResponse(testResponse)).isEqualTo(1);
    }
}

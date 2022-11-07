package lab2.vk;

import lab2.config.VkConfig;
import lab2.util.JsonUtils;
import lab2.util.UrlUtils;

public class VkClient {
    private final VkConfig vkConfig;

    public VkClient(VkConfig config) {
        vkConfig = config;
    }

    public int getPostsCount(String hashTag, int startTime, int endTime) {
        assert startTime < endTime;

        String response = UrlUtils.readResponse(newsfeedSearchUrl(hashTag, startTime, endTime));
        return JsonUtils.getTotalCountFromNewsfeedResponse(response);
    }

    private String newsfeedSearchUrl(String hashTag, int startTime, int endTime) {
        assert startTime < endTime;

        return vkConfig.getSchema() +
                vkConfig.getSourceUrl() +
                ":" + vkConfig.getPort() +
                "/method/newsfeed.search" +
                "?q=%23" + hashTag +
                "&v=" + vkConfig.getVersion() +
                "&access_token=" + vkConfig.getAccessToken() +
                "&count=0" +
                "&start_time=" + startTime +
                "&end_time=" + endTime;
    }
}

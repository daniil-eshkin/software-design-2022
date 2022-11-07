package lab2.vk;

import lab2.util.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VkManager {
    private static final long TIMEOUT_MS = 200;
    private final VkClient vkClient;

    public VkManager(VkClient vkClient) {
        this.vkClient = vkClient;
    }

    public List<Integer> getPostsCountHist(String hashtag, int hours, Date date) {
        assert hours > 0 && hours <= 24;

        return getPostsCountHist(hashtag, TimeUtils.getHistTimestampsUntilDate(date, hours));
    }

    public List<Integer> getPostsCountHist(String hashTag, int hours) {
        assert hours > 0 && hours <= 24;

        return getPostsCountHist(hashTag, hours, new Date());
    }

    private List<Integer> getPostsCountHist(String hashTag, List<Integer> stamps) {
        assert stamps.size() >= 2 && stamps.size() <= 25;

        List<Integer> hist = new ArrayList<>();

        for (int i = 0; i < stamps.size() - 1; i++) {
            hist.add(vkClient.getPostsCount(hashTag,
                    stamps.get(i),
                    stamps.get(i + 1)));
            try {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return hist;
    }
}

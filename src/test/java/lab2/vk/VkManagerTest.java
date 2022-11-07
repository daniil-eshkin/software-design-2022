package lab2.vk;

import lab2.util.TimeUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VkManagerTest {
    private VkManager manager;
    private VkClient client;

    @Before
    public void before() {
        client = mock(VkClient.class);
        manager = new VkManager(client);
    }

    @Test
    public void getPostsCountHist() {
        Date date = new Date(100000000L);
        int hours = 2;
        String hashTag = "test";
        List<Integer> expected = List.of(1, 2);
        mockClient(hashTag, date, expected);

        List<Integer> hist = manager.getPostsCountHist(hashTag, hours, date);

        assertThat(hist).isEqualTo(expected);
    }

    private void mockClient(String hashTag, Date date, List<Integer> hist) {
        List<Integer> stamps = TimeUtils.getHistTimestampsUntilDate(date, hist.size());
        for (int i = 0; i < stamps.size() - 1; i++) {
            when(client.getPostsCount(hashTag, stamps.get(i), stamps.get(i + 1))).thenReturn(hist.get(i));
        }
    }
}

package lab2.vk;

import lab2.config.VkConfig;
import lab2.rule.HostReachableRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@HostReachableRule.HostReachable(configPath = VkIntegrationTest.CONFIG_PATH,
        path = VkIntegrationTest.SOURCE_URL)
public class VkIntegrationTest {
    public static final String CONFIG_PATH = "src/main/resources/application.conf";
    public static final String SOURCE_URL = "vk.source-url";

    private final VkConfig config = new VkConfig(CONFIG_PATH);

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void getPostsCountHist() {
        VkManager manager = new VkManager(new VkClient(config));
        assertThat(manager.getPostsCountHist("tag", 2)).hasSize(2);
    }
}

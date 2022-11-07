package lab2.util;

import lab2.config.VkConfig;
import lab2.rule.HostReachableRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@HostReachableRule.HostReachable(configPath = UrlUtilsTest.CONFIG_PATH,
        path = UrlUtilsTest.SOURCE_URL)
public class UrlUtilsTest {
    public static final String CONFIG_PATH = "src/main/resources/application.conf";
    public static final String SOURCE_URL = "vk.source-url";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    private final VkConfig config = new VkConfig(CONFIG_PATH);

    @Test
    public void read() {
        assertThat(UrlUtils
                .readResponse(config.getSchema() +
                        config.getSourceUrl() +
                        ":" + config.getPort() +
                        "/method/newsfeed.search"))
                .isNotEmpty();
    }
}

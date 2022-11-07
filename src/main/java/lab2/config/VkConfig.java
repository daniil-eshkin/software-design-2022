package lab2.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class VkConfig {
    private final String schema;
    private final String sourceUrl;
    private final String port;
    private final String version;
    private final String accessToken;

    public VkConfig(String path) {
        Config config = ConfigFactory.parseFile(new File(path)).getConfig("vk");
        schema = config.getString("schema");
        sourceUrl = config.getString("source-url");
        port = config.getString("port");
        version = config.getString("version");
        accessToken = config.getString("access-token");
    }

    public String getSchema() {
        return schema;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getPort() {
        return port;
    }

    public String getVersion() {
        return version;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

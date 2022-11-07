package lab2.util;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;

public class UrlUtilsWithStubServerTest {
    private static final int PORT = 1223;

    private final String response = """
                            {
                              "error":{
                                "error_code":5,
                                "error_msg":"User authorization failed: no access_token passed.",
                                "request_params":[
                                  {
                                    "key":"method",
                                    "value":"newsfeed.search"
                                  },
                                  {
                                    "key":"oauth",
                                    "value":"1"
                                  }
                                ]
                              }
                            }
                            """;

    @Test
    public void readResponse() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
                    .then(stringContent(response));

            String result = UrlUtils.readResponse("http://localhost:" + PORT + "/method/newsfeed.search");

            Assert.assertEquals(response, result);
        });
    }

    @Test(expected = UncheckedIOException.class)
    public void readResponseWithNotFoundError() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
                    .then(status(HttpStatus.NOT_FOUND_404));

            UrlUtils.readResponse("http://localhost:" + PORT + "/method/newsfeed.search");
        });
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
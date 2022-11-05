package ru.eshkin.sd.refactoring.servlet.http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletResponseBuilder {
    private final HttpServletResponse response;

    private HttpServletResponseBuilder(HttpServletResponse response) {
        this.response = response;
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static void buildFromText(HttpServletResponse response, String text) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(text);
    }

    public static HttpServletResponseBuilder htmlBody(HttpServletResponse response) throws IOException {
        HttpServletResponseBuilder builder = new HttpServletResponseBuilder(response);
        return builder.line("<html><body>");
    }

    public void build() throws IOException {
        line("</body></html>");
    }

    public HttpServletResponseBuilder header(String text) throws IOException {
        return line("<h1>" + text + "</h1>");
    }

    public HttpServletResponseBuilder line(String text) throws IOException {
        response.getWriter().println(text);
        return this;
    }

    public HttpServletResponseBuilder line(int val) throws IOException {
        response.getWriter().println(val);
        return this;
    }
}

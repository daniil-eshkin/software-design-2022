package sd.lab5.graph;

import sd.lab5.drawing.api.DrawingApi;

import java.util.List;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private final DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public void drawGraph() {
        for (int i = 0; i < size(); i++) {
            drawingApi.drawCircle(getCircleByVertex(i));
        }
        for (Edge e : edges()) {
            drawingApi.drawLine(getCircleByVertex(e.a).center(), getCircleByVertex(e.b).center());
        }
    }

    private DrawingApi.Circle getCircleByVertex(int vertex) {
        int n = size();
        long w = drawingApi.getDrawingAreaWidth();
        long h = drawingApi.getDrawingAreaHeight();

        double coef = Math.sqrt(2 - 2 * Math.cos(2 * Math.PI / (n + 1))) / 2;
        double K = 0.7;
        double R = K * w / 2 / (1 + K * coef) * Math.min((double)h / w, (double)w / h);
        double r = R * coef;

        double angle = 2 * Math.PI / n;
        double x = R * Math.cos(vertex * angle);
        double y = R * Math.sin(vertex * angle);

        return new DrawingApi.Circle(
                new DrawingApi.Point(
                        (double)w / 2 + x * (w > h ? (double)w / h : 1),
                        (double)h / 2 + y * (h > w ? (double)h / w : 1)
                ),
                r);
    }

    protected abstract int size();

    protected abstract List<Edge> edges();

    public record Edge(int a, int b) {}
}

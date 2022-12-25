package sd.lab5.graph;

import sd.lab5.drawing.api.DrawingApi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListGraph extends Graph {
    private final int size;
    private final List<Edge> edges;

    public ListGraph(DrawingApi drawingApi, int size, List<Edge> edges) {
        super(drawingApi);
        this.size = size;
        this.edges = edges;
    }

    public static ListGraph readFromFile(DrawingApi drawingApi, InputStream inputStream) {
        Scanner in = new Scanner(inputStream);
        int n = in.nextInt();
        int m = in.nextInt();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            edges.add(new Edge(a, b));
        }
        return new ListGraph(drawingApi, n, edges);
    }

    @Override
    protected int size() {
        return size;
    }

    @Override
    protected List<Edge> edges() {
        return edges;
    }
}

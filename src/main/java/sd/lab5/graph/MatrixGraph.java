package sd.lab5.graph;

import sd.lab5.drawing.api.DrawingApi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixGraph extends Graph {
    private final boolean[][] edges;

    public MatrixGraph(DrawingApi drawingApi, boolean[][] edges) {
        super(drawingApi);
        this.edges = edges;
    }

    public static MatrixGraph readFromSystemIn(DrawingApi drawingApi, InputStream inputStream) {
        Scanner in = new Scanner(inputStream);
        int n = in.nextInt();
        boolean[][] edges = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edges[i][j] = in.nextInt() == 1;
            }
        }
        return new MatrixGraph(drawingApi, edges);
    }

    @Override
    protected int size() {
        return edges.length;
    }

    @Override
    protected List<Edge> edges() {
        List<Edge> edgesList = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < i; j++) {
                if (edges[i][j]) {
                    edgesList.add(new Edge(i, j));
                }
            }
        }
        return edgesList;
    }
}

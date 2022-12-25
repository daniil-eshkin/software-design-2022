package sd.lab5;

import sd.lab5.drawing.AwtDrawGraph;
import sd.lab5.drawing.JavaFxDrawGraph;
import sd.lab5.drawing.api.DrawingApi;
import sd.lab5.graph.Graph;
import sd.lab5.graph.ListGraph;
import sd.lab5.graph.MatrixGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3
                || !args[0].equals("awt") && !args[0].equals("javafx")
                || !args[1].equals("matrix") && !args[1].equals("list")) {
            System.err.println("Usage: <drawingApi: awt|javafx> <graph: matrix|list> <input file>");
            return;
        }

        Function<DrawingApi, Graph> graphFactory;

        switch (args[1]) {
            case "matrix" -> graphFactory = drawingApi -> {
                try {
                    return MatrixGraph.readFromFile(
                            drawingApi,
                            new FileInputStream(args[2])
                    );
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            };
            case "list" -> graphFactory = drawingApi -> {
                try {
                    return ListGraph.readFromFile(
                            drawingApi,
                            new FileInputStream(args[2])
                    );
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            };
            default -> {
                System.err.println("Expected <matrix|list>, found " + args[1]);
                return;
            }
        }

        switch (args[0]) {
            case "awt" -> new AwtDrawGraph(graphFactory).drawGraph();
            case "javafx" -> {
                JavaFxDrawGraph.graphFactory = graphFactory;
                JavaFxDrawGraph.drawGraph();
            }
            default -> System.err.println("Expected <awt|javafx>, found " + args[0]);
        }
    }
}

package sd.lab5.drawing;

import javafx.application.Application;
import javafx.stage.Stage;
import sd.lab5.drawing.api.DrawingApi;
import sd.lab5.drawing.api.JavaFxDrawingApi;
import sd.lab5.graph.Graph;

import java.util.function.Function;

public class JavaFxDrawGraph extends Application {
    public static Function<DrawingApi, Graph> graphFactory;

    public static void drawGraph() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        var drawingApi = new JavaFxDrawingApi(primaryStage, 1280, 720);
        var graph = graphFactory.apply(drawingApi);
        graph.drawGraph();
        drawingApi.show();
    }
}

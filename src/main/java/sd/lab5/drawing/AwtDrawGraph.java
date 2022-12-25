package sd.lab5.drawing;

import sd.lab5.drawing.api.AwtDrawingApi;
import sd.lab5.drawing.api.DrawingApi;
import sd.lab5.graph.Graph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;

public class AwtDrawGraph extends Frame {
    private final Function<DrawingApi, Graph> graphFactory;

    public AwtDrawGraph(Function<DrawingApi, Graph> graphFactory) {
        this.graphFactory = graphFactory;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var graphics2D = (Graphics2D) g;
        graphics2D.clearRect(0, 0, 1280, 720);

        var drawingApi = new AwtDrawingApi((Graphics2D) g, 1280, 720);
        var graph = graphFactory.apply(drawingApi);
        graph.drawGraph();
    }

    public void drawGraph() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setSize(1280, 720);
        setVisible(true);
    }
}

package gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SceneGestures {

    private static final double MIN_SCALE = 0.1d;

    private DragContext sceneDragContext = new DragContext();

    PannablePane pane;

    public SceneGestures( PannablePane pane) {
        this.pane = pane;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {

        public void handle(MouseEvent event) {

            // left mouse button => panning
            if (!event.isPrimaryButtonDown())
                return;

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = pane.getTranslateX();
            sceneDragContext.translateAnchorY = pane.getTranslateY();

        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<>() {
        public void handle(MouseEvent event) {

            // left mouse button => panning
            if (!event.isPrimaryButtonDown())
                return;

            pane.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
            pane.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

            event.consume();
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = pane.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            if (Double.compare(scale, MIN_SCALE) < 0) {
                scale = MIN_SCALE;
            }

            double f = (scale / oldScale) - 1;

            double dx = (event.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY()));

            pane.setScale(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            pane.setPivot(f * dx, f * dy);

            event.consume();

        }

    };

}

package io.interacto.ex.draw.learning;

import io.interacto.jfx.interaction.help.DnDHelpAnimation;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AddRectHelpAnimation extends DnDHelpAnimation {
	public AddRectHelpAnimation(final Pane learningPane, final Pane widget) {
		super(learningPane, widget, 150, 500, 150, 350, "Press (left button) to start drawing a rectangle",
			"Drag", "Release");
	}

	@Override
	public Transition createTransition() {
		final Transition transition = super.createTransition();
		final Rectangle rec = new Rectangle(150, 150, 1d, 1d);
		helpPane.getChildren().add(rec);
		rec.toBack();
		rec.setFocusTraversable(false);
		rec.setMouseTransparent(true);
		rec.setFill(Color.LIGHTGRAY);
		rec.setStroke(Color.GRAY);
		rec.widthProperty().bind(ell.centerXProperty().subtract(rec.getX()));
		rec.heightProperty().bind(ell.centerYProperty().subtract(rec.getY()));
		rec.visibleProperty().bind(ell.visibleProperty());
		return transition;
	}
}

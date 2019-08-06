package io.github.interacto.ex.draw.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class MyCanvas extends Pane {
	/** The views of the shape. */
	private final Group shapesPane;
	private final Group tmpShape;


	public MyCanvas() {
		super();
		shapesPane = new Group();
		tmpShape = new Group();
		getChildren().add(shapesPane);
		getChildren().add(tmpShape);
	}
	

	public void setTmpShape(final Shape shape) {
		tmpShape.getChildren().clear();
		if(shape != null) {
			tmpShape.getChildren().addAll(shape);
		}
	}

	public Group getShapesPane() {
		return shapesPane;
	}
}

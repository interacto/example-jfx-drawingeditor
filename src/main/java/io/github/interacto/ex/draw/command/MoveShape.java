package io.github.interacto.ex.draw.command;

import io.github.interacto.command.AutoUnbind;
import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MoveShape extends ShapeCmd implements Undoable {
	private double mementoX;
	private double mementoY;
	@AutoUnbind private final DoubleProperty newX;
	@AutoUnbind private final DoubleProperty newY;

	public MoveShape(final MyShape shape, final DoubleBinding xBinding, final DoubleBinding yBinding) {
		super(shape);
		newX = new SimpleDoubleProperty();
		newY = new SimpleDoubleProperty();
		newX.bind(xBinding);
		newY.bind(yBinding);
	}

	@Override
	protected void doCmdBody() {
		redo();
	}

	@Override
	protected void createMemento() {
		mementoX = shape.getX();
		mementoY = shape.getY();
	}

	@Override
	public void undo() {
		shape.setX(mementoX);
		shape.setY(mementoY);
	}

	@Override
	public void redo() {
		shape.setX(newX.doubleValue());
		shape.setY(newY.doubleValue());
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return "Shape moved";
	}

	public double getNewX() {
		return newX.get();
	}

	public double getNewY() {
		return newY.get();
	}
}

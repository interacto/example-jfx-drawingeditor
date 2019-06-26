package io.interacto.ex.draw.command;

import io.interacto.ex.draw.model.MyShape;
import io.interacto.undo.Undoable;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

public class ChangeColour extends ShapeCmd implements Undoable {
	private Color newCol;
	private Color mementoCol;

	public ChangeColour(final Color col, final MyShape sh) {
		super(sh);
		newCol = col;
	}

	@Override
	protected void doCmdBody() {
		redo();
	}

	/*
	 * This command needs a memento to save the previous state of the modified object:
	 * its colour. This operation is automatically called a single time before the first execution of the
	 * command to produce the memento (here the former colour of the shape to modify).
	 */
	@Override
	protected void createMemento() {
		mementoCol = shape.getLineColor();
	}

	@Override
	public void undo() {
		shape.setLineColor(mementoCol);
	}

	@Override
	public void redo() {
		shape.setLineColor(newCol);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && newCol != null;
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return "Color changed";
	}
}

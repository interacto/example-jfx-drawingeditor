package io.github.interacto.ex.draw.command;

import io.github.interacto.ex.draw.model.MyDrawing;
import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;

/*
 * Defines a command that adds a shape into a drawing.
 * A command must inherits from CommandImpl.
 * A command may be undoable. In such a case, it must implements
 * the interface Undoable.
 */
public class AddShape extends ShapeCmd implements Undoable {
	/*
	 * The attributes of the commands are used to execute, undo, and redo them.
	 * They must be set throw setters, not using a constructor.
	 */
	private final MyDrawing drawing;

	public AddShape(final MyDrawing drawing, final MyShape shape) {
		super(shape);
		this.drawing = drawing;
	}

	@Override
	protected void doCmdBody() {
		/*
		 * This operation must contain the execution of the command.
		 * Here, adding the shape into the drawing and setting the drawing as modified.
		 */
		drawing.addShape(shape);
	}

	@Override
	public boolean canDo() {
		/*
		 * Checking that the parameter mandatory for the execution of the action are correct.
		 * here, checking that the drawing and the shape are not null,
		 * and that the drawing does not contain the shape already.
		 */
		return super.canDo() && drawing != null && !drawing.getShapes().contains(shape);
	}


	@Override
	public void undo() {
		/* Defines what to do for undoing the action. */
		drawing.getShapes().remove(shape);
	}

	@Override
	public void redo() {
		/*
		 * Defines what to do for redoing the action.
		 * Here, doing the same job that the execution of the action.
		 */
		doCmdBody();
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		/*
		 * This string is a message defining the goal of the action.
		 * It is used by the undo/redo manager to show a tooltip
		 * in the undo and redo widgets.
		 */
		return "Shape added";
	}
}

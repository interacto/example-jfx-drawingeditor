package io.interacto.ex.draw.instrument;

import io.interacto.command.library.Redo;
import io.interacto.command.library.Undo;
import io.interacto.jfx.instrument.JfxInstrument;
import io.interacto.jfx.undo.FXUndoCollector;
import io.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * Manages undos and redos.
 */
public class UndoRedoer extends JfxInstrument implements Initializable {
	/** The button used to undo commands. */
	@FXML private Button undoB;
	/** The button used to redo commands. */
	@FXML private Button redoB;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
		UndoCollector.INSTANCE.addHandler(this);

		undoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastUndoProperty().isNull());
		redoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastRedoProperty().isNull());

		undoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastUndo().map(undo -> new Tooltip(undo.getUndoName(null))).orElse(null), FXUndoCollector.INSTANCE.lastUndoProperty()));
		redoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastRedo().map(redo -> new Tooltip(redo.getUndoName(null))).orElse(null), FXUndoCollector.INSTANCE.lastRedoProperty()));
	}

	@Override
	protected void configureBindings() {
		// Undo and Redo are commands provided by Malai.
		buttonBinder(Undo::new).on(undoB).bind();
		buttonBinder(Redo::new).on(redoB).bind();
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		undoB.setVisible(act);
		redoB.setVisible(act);
	}
}

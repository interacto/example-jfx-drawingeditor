package io.github.interacto.ex.draw.presenter;

import io.github.interacto.command.library.Redo;
import io.github.interacto.command.library.Undo;
import io.github.interacto.jfx.undo.FXUndoCollector;
import io.github.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import static io.github.interacto.jfx.binding.Bindings.buttonBinder;

/**
 * Manages undos and redos.
 */
public class UndoRedoer implements Initializable {
	/** The button used to undo commands. */
	@FXML private Button undoB;
	/** The button used to redo commands. */
	@FXML private Button redoB;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		undoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastUndoProperty().isNull());
		redoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastRedoProperty().isNull());

		undoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastUndo().map(undo -> new Tooltip(undo.getUndoName(null))).orElse(null), FXUndoCollector.INSTANCE.lastUndoProperty()));
		redoB.tooltipProperty().bind(Bindings.createObjectBinding(() ->
			UndoCollector.INSTANCE.getLastRedo().map(redo -> new Tooltip(redo.getUndoName(null))).orElse(null), FXUndoCollector.INSTANCE.lastRedoProperty()));

		configureBindings();
	}

	private void configureBindings() {
		// Undo and Redo are commands provided by Interacto.
		buttonBinder(Undo::new).on(undoB).bind();
		buttonBinder(Redo::new).on(redoB).bind();
	}
}

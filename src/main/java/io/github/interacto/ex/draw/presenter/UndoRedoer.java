package io.github.interacto.ex.draw.presenter;

import io.github.interacto.command.library.Redo;
import io.github.interacto.command.library.Undo;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * Manages undos and redos.
 * A JavaFX controller can inherit of JfXInstrument to get some facilities. Not mandatory
 */
public class UndoRedoer extends JfxInstrument implements Initializable {
	/** The button used to undo commands. */
	@FXML private Button undoB;
	/** The button used to redo commands. */
	@FXML private Button redoB;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);

		// a JFXInstrument can manage disposable objects automatically
		// The following lines bind undoable objects to buttons dynamically: their status change
		// on changes on the stream of undoable/redoable objects
		addDisposable(UndoCollector.getInstance().redos().subscribe(redoable -> {
			redoB.setDisable(redoable.isEmpty());
			redoB.setTooltip(redoable.map(u -> new Tooltip(u.getUndoName(null))).orElse(null));
		}));
		addDisposable(UndoCollector.getInstance().undos().subscribe(undoable -> {
			undoB.setDisable(undoable.isEmpty());
			undoB.setTooltip(undoable.map(u -> new Tooltip(u.getUndoName(null))).orElse(null));
		}));
	}

	// This method is automatically called when the controller is activated
	@Override
	protected void configureBindings() {
		// Undo and Redo are commands provided by Interacto.
		buttonBinder()
			.toProduce(Undo::new)
			.on(undoB)
			.bind();

		buttonBinder()
			.on(redoB)
			.toProduce(Redo::new)
			.bind();
	}
}

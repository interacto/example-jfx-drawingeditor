package io.github.interacto.ex.draw.presenter;

import io.github.interacto.command.library.Redo;
import io.github.interacto.command.library.Undo;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import io.github.interacto.undo.UndoCollector;
import io.github.interacto.undo.Undoable;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(WidgetBindingExtension.class)
@ExtendWith(ApplicationExtension.class)
public class TestUndoRedoer {
	UndoRedoer undoredoer;
	Button undoB;
	Button redoB;
	Undoable undoable;

	@Start
	void start(final Stage stage) throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Undo.fxml"));
		stage.setScene(new Scene(loader.load()));
		stage.show();
		undoredoer = loader.getController();
	}

	@BeforeEach
	void setUp(final FxRobot robot) {
		undoable = Mockito.mock(Undoable.class);
		undoB = robot.lookup("#undoB").queryButton();
		redoB = robot.lookup("#redoB").queryButton();
	}

	void activateUndoB() {
		UndoCollector.getInstance().add(undoable);
	}

	void activateRedoB() {
		activateUndoB();
		UndoCollector.getInstance().undo();
	}

	@Test
	void testClickUndo(final FxRobot robot, final BindingsContext ctx) {
		activateUndoB();
		robot.clickOn(undoB);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Undo.class);
	}

	@Test
	void testClickRedo(final FxRobot robot, final BindingsContext ctx) {
		activateRedoB();
		robot.clickOn(redoB);
		WaitForAsyncUtils.waitForFxEvents();

		ctx.oneCmdProduced(Redo.class);
	}
}

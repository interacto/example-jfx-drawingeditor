package io.github.interacto.ex.draw.presenter;

import io.github.interacto.jfx.interaction.library.WidgetData;
import io.github.interacto.undo.UndoCollector;
import io.github.interacto.undo.Undoable;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Test class for the controller UndoRedoer. Generated by Interacto test-gen.
 */
@ExtendWith(ApplicationExtension.class)
public class UndoRedoerTest extends UndoRedoerBaseTest {
	Undoable undoable;

	@Start
	void start(final Stage stage) throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Undo.fxml"), null, new JavaFXBuilderFactory(), (cl) -> {
			if(cl == UndoRedoer.class) {
				return new UndoRedoer();
			}
			return Mockito.mock(cl);
		});
		stage.setScene(new Scene(loader.load()));
		stage.show();
		undoredoer = loader.getController();
	}

	@BeforeEach
	void setUpUndoRedoerTest() {
		undoable = Mockito.mock(Undoable.class);
	}

	@Override
	WidgetData<Button> dataButtonPressedToRedoUsingredoB(final FxRobot robot) {
		final WidgetData<Button> data = Mockito.mock(WidgetData.class);
		return data;
	}

	@Override
	WidgetData<Button> dataButtonPressedToUndoUsingundoB(final FxRobot robot) {
		final WidgetData<Button> data = Mockito.mock(WidgetData.class);
		return data;
	}

	@Override
	void activateButtonPressedToRedoUsingredoB(final FxRobot robot) {
		activateButtonPressedToUndoUsingundoB(robot);
		UndoCollector.getInstance().undo();
	}

	@Override
	void activateButtonPressedToUndoUsingundoB(final FxRobot robot) {
		UndoCollector.getInstance().add(undoable);
	}
}
package io.github.interacto.ex.draw.presenter;

import io.github.interacto.command.library.Redo;
import io.github.interacto.command.library.Undo;
import io.github.interacto.jfx.interaction.library.WidgetData;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Base test class for the controller UndoRedoer. Generated by Interacto test-gen. Do not edit.
 */
@ExtendWith(WidgetBindingExtension.class)
public abstract class UndoRedoerBaseTest {
	UndoRedoer undoredoer;

	Button undoB;

	Button redoB;

	@BeforeEach
	void setUpUndoRedoerBaseTest(final FxRobot robot) {
		this.undoB = robot.lookup("#undoB").query();
		this.redoB = robot.lookup("#redoB").query();
	}

	@Test
	void testNumberOfBindings(final BindingsContext ctx) {
		ctx.hasBindings(2);
	}

	@Test
	void testButtonPressedToUndoUsingundoB(final FxRobot robot, final BindingsContext ctx) {
		activateButtonPressedToUndoUsingundoB(robot);
		final WidgetData<Button> data = dataButtonPressedToUndoUsingundoB(robot);
		robot.clickOn(this.undoB);
		WaitForAsyncUtils.waitForFxEvents();
		final Undo cmd = ctx.oneCmdProducedAmong(Undo.class).getCommand();
		checkButtonPressedToUndoUsingundoB(cmd, data);
	}

	void activateButtonPressedToUndoUsingundoB(final FxRobot robot) {
	}

	void checkButtonPressedToUndoUsingundoB(final Undo cmd, final WidgetData<Button> data) {
	}

	abstract WidgetData<Button> dataButtonPressedToUndoUsingundoB(final FxRobot robot);

	@Test
	void testButtonPressedToRedoUsingredoB(final FxRobot robot, final BindingsContext ctx) {
		activateButtonPressedToRedoUsingredoB(robot);
		final WidgetData<Button> data = dataButtonPressedToRedoUsingredoB(robot);
		robot.clickOn(this.redoB);
		WaitForAsyncUtils.waitForFxEvents();
		final Redo cmd = ctx.oneCmdProducedAmong(Redo.class).getCommand();
		checkButtonPressedToRedoUsingredoB(cmd, data);
	}

	void activateButtonPressedToRedoUsingredoB(final FxRobot robot) {
	}

	void checkButtonPressedToRedoUsingredoB(final Redo cmd, final WidgetData<Button> data) {
	}

	abstract WidgetData<Button> dataButtonPressedToRedoUsingredoB(final FxRobot robot);
}
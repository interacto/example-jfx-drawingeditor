package io.github.interacto.ex.draw.presenter;

import io.github.interacto.ex.draw.command.AddShape;
import io.github.interacto.ex.draw.command.ChangeColour;
import io.github.interacto.ex.draw.command.MoveShape;
import io.github.interacto.ex.draw.view.MyCanvas;
import io.github.interacto.jfx.interaction.library.SrcTgtPointsData;
import io.github.interacto.jfx.test.BindingsContext;
import io.github.interacto.jfx.test.WidgetBindingExtension;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(WidgetBindingExtension.class)
public abstract class TestPencilBase {
	Pencil pencil;
	MyCanvas canvas;
	ColorPicker lineCol;
	Button save;

	@BeforeEach
	void setUp(final FxRobot robot) {
		canvas = robot.lookup("#canvas").query();
		lineCol = robot.lookup("#lineCol").query();
		save = robot.lookup("#save").query();
	}

	@Test
	void testNumberOfBindings(final BindingsContext ctx) {
		ctx.hasBindings(5);
	}

	void activateDnDOnCanvasToAddShape(final FxRobot robot) {
	}

	void checkDnDOnCanvasToAddShape(final AddShape cmd, final SrcTgtPointsData data) {
	}

	abstract SrcTgtPointsData dataTestDnDOnCanvasToAddShape(final FxRobot robot);

	abstract SrcTgtPointsData dataTestDnDOnCanvasToAddShapeKO(final FxRobot robot);

	@Test
	void testDnDOnCanvasToAddShape(final FxRobot robot, final BindingsContext ctx) {
		activateDnDOnCanvasToAddShape(robot);
		final var data = dataTestDnDOnCanvasToAddShape(robot);

		robot
			.moveTo(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY())
			.press(data.getButton())
			.moveTo(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY())
			.release(data.getButton());

		WaitForAsyncUtils.waitForFxEvents();

		final var cmd = ctx
			.oneCmdProducedAmong(AddShape.class)
			.getCommand();

		checkDnDOnCanvasToAddShape(cmd, data);
	}

	@Test
	void testDnDOnCanvasToAddShapeKO(final FxRobot robot, final BindingsContext ctx) {
		activateDnDOnCanvasToAddShape(robot);
		final var data = dataTestDnDOnCanvasToAddShapeKO(robot);

		robot
			.moveTo(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY())
			.press(data.getButton())
			.moveTo(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY())
			.release(data.getButton());

		WaitForAsyncUtils.waitForFxEvents();

		ctx
			.listAssert()
			.noneSatisfy(elt -> elt.ofType(AddShape.class));
	}

	void activateDnDOnCanvasToMoveShape(final FxRobot robot) {
	}

	abstract SrcTgtPointsData dataTestDnDOnCanvasToMoveShape(final FxRobot robot);

	void checkDnDOnCanvasToMoveShape(final MoveShape cmd, final SrcTgtPointsData data) {
	}

	@Test
	void testDnDOnCanvasToMoveShape(final FxRobot robot, final BindingsContext ctx) {
		activateDnDOnCanvasToMoveShape(robot);
		final var data = dataTestDnDOnCanvasToMoveShape(robot);

		robot
			.moveTo(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY())
			.press(data.getButton())
			.moveTo(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY())
			.release(data.getButton());

		WaitForAsyncUtils.waitForFxEvents();

		final var cmd = ctx
			.oneCmdProducedAmong(MoveShape.class)
			.getCommand();

		checkDnDOnCanvasToMoveShape(cmd, data);
	}

	@Test
	void testDnDOnCanvasToMoveShapeCancel(final FxRobot robot, final BindingsContext ctx) {
		activateDnDOnCanvasToMoveShape(robot);
		final var data = dataTestDnDOnCanvasToMoveShape(robot);

		robot
			.moveTo(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY())
			.press(data.getButton())
			.moveTo(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY())
			.type(KeyCode.ESCAPE);

		WaitForAsyncUtils.waitForFxEvents();

		ctx.listAssert()
			.noneSatisfy(elt -> elt.ofType(MoveShape.class));
	}

	void activateDnDOnLineColToChangeColour(final FxRobot robot) {
	}

	abstract SrcTgtPointsData dataTestDnDOnLineColToChangeColour(final FxRobot robot);

	void checkDnDOnLineColToChangeColour(final ChangeColour cmd, final SrcTgtPointsData data) {
	}

	@Disabled("monocle fails at running this test because of the cursor")
	@Test
	void testDnDOnLineColToChangeColour(final FxRobot robot, final BindingsContext ctx) {
		activateDnDOnLineColToChangeColour(robot);
		final var data = dataTestDnDOnLineColToChangeColour(robot);

		robot
			.moveTo(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY())
			.press(data.getButton())
			.moveTo(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY())
			.release(data.getButton());
		WaitForAsyncUtils.waitForFxEvents();

		final ChangeColour cmd = ctx
			.oneCmdProducedAmong(ChangeColour.class)
			.getCommand();

		checkDnDOnLineColToChangeColour(cmd, data);
	}
}

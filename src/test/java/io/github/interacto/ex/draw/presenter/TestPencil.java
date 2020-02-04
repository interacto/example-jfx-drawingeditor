package io.github.interacto.ex.draw.presenter;

import io.github.interacto.ex.draw.command.AddShape;
import io.github.interacto.ex.draw.command.ChangeColour;
import io.github.interacto.ex.draw.command.MoveShape;
import io.github.interacto.ex.draw.model.MyRect;
import io.github.interacto.jfx.interaction.library.SrcTgtPointsData;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class TestPencil extends TestPencilBase {
	@Start
	void start(final Stage stage) throws IOException {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UI.fxml"));
		stage.setScene(new Scene(loader.load()));
		stage.show();
		pencil = loader.getController();
	}

	@Override
	void activateDnDOnCanvasToAddShape(final FxRobot robot) {
	}

	@Override
	SrcTgtPointsData dataTestDnDOnCanvasToAddShape(final FxRobot robot) {
		final Point2D position = robot.point(canvas).query();
		final SrcTgtPointsData data = Mockito.mock(SrcTgtPointsData.class);
		Mockito.when(data.getButton()).thenReturn(MouseButton.PRIMARY);
		Mockito.when(data.getSrcLocalPoint()).thenReturn(new Point3D((int) position.getX(), (int) position.getY(), 0));
		Mockito.when(data.getTgtLocalPoint()).thenReturn(new Point3D((int) position.getX() + 10, (int) position.getY() + 30, 0));
		return data;
	}

	@Override
	SrcTgtPointsData dataTestDnDOnCanvasToAddShapeKO(final FxRobot robot) {
		final SrcTgtPointsData data = dataTestDnDOnCanvasToAddShape(robot);
		Mockito.when(data.getButton()).thenReturn(MouseButton.SECONDARY);
		return data;
	}

	@Override
	void checkDnDOnCanvasToAddShape(final AddShape cmd, final SrcTgtPointsData data) {
		final Point2D p1 = canvas.screenToLocal(data.getSrcLocalPoint().getX(), data.getSrcLocalPoint().getY());
		final Point2D p2 = canvas.screenToLocal(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY());

		assertThat(cmd.getShape()).isSameAs(pencil.getDrawing().getShapes().get(0));
		assertThat(cmd.getShape().getX()).isEqualTo(p1.getX(), Offset.offset(1d));
		assertThat(cmd.getShape().getY()).isEqualTo(p1.getY(), Offset.offset(1d));
		assertThat(((MyRect) cmd.getShape()).widthProperty().get()).isEqualTo(Math.abs(p2.getX() - p1.getX()), Offset.offset(0.1));
		assertThat(((MyRect) cmd.getShape()).heightProperty().get()).isEqualTo(Math.abs(p2.getY() - p1.getY()), Offset.offset(0.1));
	}


	@Override
	void activateDnDOnCanvasToMoveShape(final FxRobot robot) {
		final Point2D position = robot.point(canvas).query();
		robot
			.moveTo((int) position.getX(), (int) position.getY())
			.press(MouseButton.PRIMARY)
			.moveBy(10, 30)
			.release(MouseButton.PRIMARY);
		WaitForAsyncUtils.waitForFxEvents();
		shape = pencil.getDrawing().getShapes().get(0);
	}

	@Override
	SrcTgtPointsData dataTestDnDOnCanvasToMoveShape(final FxRobot robot) {
		final Point2D position = robot.point(canvas).query();
		final SrcTgtPointsData data = Mockito.mock(SrcTgtPointsData.class);
		Mockito.when(data.getButton()).thenReturn(MouseButton.SECONDARY);
		Mockito.when(data.getSrcLocalPoint()).thenReturn(new Point3D((int) position.getX(), (int) position.getY(), 0));
		Mockito.when(data.getTgtLocalPoint()).thenReturn(new Point3D((int) position.getX() - 25, (int) position.getY() + 50, 0));
		return data;
	}

	@Override
	void checkDnDOnCanvasToMoveShape(final MoveShape cmd, final SrcTgtPointsData data) {
		final Point2D p2 = canvas.screenToLocal(data.getTgtLocalPoint().getX(), data.getTgtLocalPoint().getY());
		assertThat(cmd.getShape()).isSameAs(shape);
		assertThat(cmd.getNewX()).isEqualTo(p2.getX(), Offset.offset(1d));
		assertThat(cmd.getNewY()).isEqualTo(p2.getY(), Offset.offset(1d));
	}

	@Override
	void activateDnDOnLineColToChangeColour(final FxRobot robot) {
		Platform.runLater(() -> lineCol.setValue(Color.GRAY));
		WaitForAsyncUtils.waitForFxEvents();
		activateDnDOnCanvasToMoveShape(robot);
	}

	@Override
	SrcTgtPointsData dataTestDnDOnLineColToChangeColour(final FxRobot robot) {
		final Point2D position = robot.point(lineCol).query();
		final Point2D position2 = robot.point(canvas.getShapesPane().getChildren().get(0)).query();
		final SrcTgtPointsData data = Mockito.mock(SrcTgtPointsData.class);
		Mockito.when(data.getButton()).thenReturn(MouseButton.PRIMARY);
		Mockito.when(data.getSrcLocalPoint()).thenReturn(new Point3D((int) position.getX(), (int) position.getY(), 0));
		Mockito.when(data.getTgtLocalPoint()).thenReturn(new Point3D((int) position2.getX(), (int) position2.getY(), 0));
		return data;
	}

	@Override
	void checkDnDOnLineColToChangeColour(final ChangeColour cmd, final SrcTgtPointsData data) {
		assertThat(cmd).hasFieldOrPropertyWithValue("newCol", Color.GRAY);
	}
}

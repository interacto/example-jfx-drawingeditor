package io.github.interacto.ex.draw.command;

import io.github.interacto.ex.draw.model.MyDrawing;
import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.jfx.test.UndoableCmdTest;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

/**
 * Test class for the command AddShape. Generated by Interacto test-gen.
 */
public class AddShapeTest extends UndoableCmdTest<AddShape> {
	MyDrawing drawing;
	MyShape shape;

	@AfterEach
	void tearDownAddShape() {
		drawing = null;
		shape = null;
	}

	@Override
	protected Stream<Runnable> cannotDoFixtures() {
		return Stream.of(
			() -> {
				drawing = Mockito.mock(MyDrawing.class);
				cmd = new AddShape(drawing, shape);
			},
			() -> {
				shape = Mockito.mock(MyShape.class);
				cmd = new AddShape(drawing, shape);
			},
			() -> {
				drawing = Mockito.mock(MyDrawing.class);
				shape = Mockito.mock(MyShape.class);
				Mockito.when(drawing.getShapes()).thenReturn(FXCollections.observableArrayList(shape));
				cmd = new AddShape(drawing, shape);
			});
	}

	@Override
	protected Stream<Runnable> canDoFixtures() {
		return Stream.of(
			() -> {
				drawing = Mockito.mock(MyDrawing.class);
				shape = Mockito.mock(MyShape.class);
				Mockito.when(drawing.getShapes()).thenReturn(FXCollections.observableArrayList());
				cmd = new AddShape(drawing, shape);
			});
	}

	@Override
	protected Stream<Runnable> doCheckers() {
		return Stream.of(() -> Mockito.verify(drawing, times(nbExec)).addShape(shape));
	}

	@Override
	protected Stream<Runnable> undoCheckers() {
		return Stream.of(() -> Mockito.verify(drawing, times(nbExec)).removeShape(shape));
	}
}
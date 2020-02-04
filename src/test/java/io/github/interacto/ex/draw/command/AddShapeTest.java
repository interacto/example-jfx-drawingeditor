package io.github.interacto.ex.draw.command;

import io.github.interacto.ex.draw.model.MyDrawing;
import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.jfx.test.UndoableCmdTest;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class AddShapeTest extends UndoableCmdTest<AddShape> {
	MyDrawing drawing;
	MyShape shape;

	@AfterEach
	void tearDownAddShape() {
		drawing = null;
		shape = null;
	}

	@Override
	protected Stream<Runnable> cannotDoConfigurations() {
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
	protected Stream<Runnable> canDoConfigurations() {
		return Stream.of(
			() -> {
				drawing = Mockito.mock(MyDrawing.class);
				shape = Mockito.mock(MyShape.class);
				Mockito.when(drawing.getShapes()).thenReturn(FXCollections.observableArrayList());
				cmd = new AddShape(drawing, shape);
			});
	}

	@Override
	protected Runnable doChecker() {
		return () ->
			Mockito.verify(drawing, times(nbExec)).addShape(shape);
	}

	@Override
	protected Runnable undoChecker() {
		return () -> Mockito.verify(drawing, times(nbExec)).removeShape(shape);
	}

	//	@Nested
//	class TestAddShapeKO {
//		@Test
//		void testShapeNull() {
//			drawing = Mockito.mock(MyDrawing.class);
//			cmd = new AddShape(drawing, shape);
//			assertThat(cmd.canDo()).isFalse();
//		}
//		@Test
//		void testDrawingNull() {
//			shape = Mockito.mock(MyShape.class);
//			cmd = new AddShape(drawing, shape);
//			assertThat(cmd.canDo()).isFalse();
//		}
//		@Test
//		void testDrawingAlreadyContains() {
//			drawing = Mockito.mock(MyDrawing.class);
//			shape = Mockito.mock(MyShape.class);
//			cmd = new AddShape(drawing, shape);
//			Mockito.when(drawing.getShapes()).thenReturn(FXCollections.observableArrayList(shape));
//			assertThat(cmd.canDo()).isFalse();
//		}
//	}
//
//	@Nested
//	class TestAddShapeOK {
//		@BeforeEach
//		void setUp() {
//			drawing = Mockito.mock(MyDrawing.class);
//			shape = Mockito.mock(MyShape.class);
//			Mockito.when(drawing.getShapes()).thenReturn(FXCollections.observableArrayList());
//			cmd = new AddShape(drawing, shape);
//		}
//
//		@Test
//		void testCanBeDone() {
//			assertThat(cmd.canDo()).isTrue();
//		}
//
//		@Test
//		void testShapeAdded() {
//			cmd.doIt();
//			cmd.done();
//			Mockito.verify(drawing).addShape(shape);
//		}
//
//		@Test
//		void testUndo() {
//			cmd.doIt();
//			cmd.done();
//			cmd.undo();
//			Mockito.verify(drawing).removeShape(shape);
//		}
//
//		@Test
//		void testRedo() {
//			cmd.doIt();
//			cmd.done();
//			cmd.undo();
//			cmd.redo();
//			Mockito.verify(drawing, Mockito.times(2)).addShape(shape);
//		}
//
//		@Test
//		void testUndoName() {
//			assertThat(cmd.getUndoName(null)).isNotEmpty();
//		}
//	}
}

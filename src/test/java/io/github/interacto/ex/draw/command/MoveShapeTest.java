package io.github.interacto.ex.draw.command;

import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.jfx.test.UndoableCmdTest;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;

public class MoveShapeTest extends UndoableCmdTest<MoveShape> {
	double mementoX;
	double mementoY;
	DoubleBinding newX;
	DoubleBinding newY;
	MyShape shape;

	@AfterEach
	void tearDownMoveShape() {
		newX = null;
		newY = null;
		shape = null;
	}

	@Override
	protected Stream<Runnable> cannotDoConfigurations() {
		return Stream.of(() -> cmd = new MoveShape(null, Mockito.mock(DoubleBinding.class), Mockito.mock(DoubleBinding.class)));
	}

	@Override
	protected Stream<Runnable> canDoConfigurations() {
		return Stream.of(() -> {
			newX = Bindings.createDoubleBinding(() -> 5d);
			newY = Bindings.createDoubleBinding(() -> 15d);
			shape = Mockito.mock(MyShape.class);
			Mockito.when(shape.getX()).thenReturn(10d);
			Mockito.when(shape.getY()).thenReturn(20d);
			cmd = new MoveShape(shape, newX, newY);
		});
	}

	@Override
	protected Runnable doChecker() {
		return () -> {
			Mockito.verify(shape, Mockito.times(nbExec)).setX(5d);
			Mockito.verify(shape, Mockito.times(nbExec)).setY(15d);
		};
	}

	@Override
	protected Runnable undoChecker() {
		return () -> {
			Mockito.verify(shape, Mockito.times(nbExec)).setX(10d);
			Mockito.verify(shape, Mockito.times(nbExec)).setY(20d);
		};
	}
}

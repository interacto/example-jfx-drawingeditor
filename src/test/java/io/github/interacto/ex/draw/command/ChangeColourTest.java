package io.github.interacto.ex.draw.command;

import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.jfx.test.UndoableCmdTest;
import java.util.stream.Stream;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class ChangeColourTest extends UndoableCmdTest<ChangeColour> {
	Color newCol;
	Color mementoCol;
	MyShape shape;

	@AfterEach
	void tearDownChangeColour() {
		newCol = null;
		mementoCol = null;
		shape = null;
	}

	@Override
	protected Stream<Runnable> cannotDoConfigurations() {
		return Stream.of(() -> {
			newCol = Color.LIGHTGRAY;
			cmd = new ChangeColour(newCol, null);
		}, () -> {
			shape = Mockito.mock(MyShape.class);
			cmd = new ChangeColour(null, shape);
		});
	}

	@Override
	protected Stream<Runnable> canDoConfigurations() {
		return Stream.of(() -> {
			newCol = Color.CORAL;
			mementoCol = Color.CORNSILK;
			shape = Mockito.mock(MyShape.class);
			Mockito.when(shape.getLineColor()).thenReturn(mementoCol);
			cmd = new ChangeColour(newCol, shape);
		});
	}

	@Override
	protected Runnable doChecker() {
		return () -> Mockito.verify(shape, times(nbExec)).setLineColor(newCol);
	}

	@Override
	protected Runnable undoChecker() {
		return () -> Mockito.verify(shape, times(nbExec)).setLineColor(mementoCol);
	}
}

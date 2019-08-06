package io.github.interacto.ex.draw.command;

import io.github.interacto.command.CommandImpl;
import io.github.interacto.ex.draw.model.MyShape;

abstract class ShapeCmd extends CommandImpl {
	MyShape shape;

	public ShapeCmd(final MyShape shape) {
		super();
		this.shape = shape;
	}

	public MyShape getShape() {
		return shape;
	}

	public void setShape(final MyShape shape) {
		this.shape = shape;
	}

	@Override
	public boolean canDo() {
		return shape != null;
	}
}

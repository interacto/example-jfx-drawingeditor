package io.github.interacto.ex.draw.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MyRect extends MyShape {
	private final DoubleProperty width;
	private final DoubleProperty height;

	public MyRect(final double x, final double y, final double w, final double h) {
		super(x, y);
		width = new SimpleDoubleProperty(w);
		height = new SimpleDoubleProperty(h);
	}


	public void setWidth(final double width) {
		if(width > 0d) {
			this.width.set(width);
		}
	}


	public void setHeight(final double height) {
		if(height > 0d) {
			this.height.set(height);
		}
	}

	public DoubleProperty widthProperty() {
		return width;
	}

	public DoubleProperty heightProperty() {
		return height;
	}
}

module interacto.ex.drawingEditor {
	requires interacto.java.api;
	requires interacto.javafx;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;

	exports io.github.interacto.ex.draw to javafx.graphics;
	exports io.github.interacto.ex.draw.instrument to javafx.fxml;
	exports io.github.interacto.ex.draw.view to javafx.fxml;

	opens io.github.interacto.ex.draw.instrument to javafx.fxml;
	opens io.github.interacto.ex.draw.command to interacto.javafx;
}
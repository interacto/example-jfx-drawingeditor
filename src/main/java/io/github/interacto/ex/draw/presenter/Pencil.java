package io.github.interacto.ex.draw.presenter;

import io.github.interacto.ex.draw.command.AddShape;
import io.github.interacto.ex.draw.command.ChangeColour;
import io.github.interacto.ex.draw.command.MoveShape;
import io.github.interacto.ex.draw.command.Save;
import io.github.interacto.ex.draw.learning.AddRectHelpAnimation;
import io.github.interacto.ex.draw.learning.MoveRectHelpAnimation;
import io.github.interacto.ex.draw.model.MyDrawing;
import io.github.interacto.ex.draw.model.MyRect;
import io.github.interacto.ex.draw.model.MyShape;
import io.github.interacto.ex.draw.view.MyCanvas;
import io.github.interacto.ex.draw.view.ViewFactory;
import io.github.interacto.ex.util.ColorCursor;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.Press;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import static io.github.interacto.jfx.binding.Bindings.anonCmdBinder;
import static io.github.interacto.jfx.binding.Bindings.buttonBinder;
import static io.github.interacto.jfx.binding.Bindings.nodeBinder;

public class Pencil implements Initializable {
	@FXML private MyCanvas canvas;
	@FXML private ColorPicker lineCol;
	@FXML private Button save;
	@FXML private Label textProgress;
	@FXML private Button cancel;
	@FXML private ProgressBar progressbar;
	/** The pane used to explain how the user interactions work. */
	@FXML private Pane learningPane;

	/** The model of the app. */
	private final MyDrawing drawing;


	public Pencil() {
		super();
		drawing = new MyDrawing();
	}

	private void configureBindings() {
		// A DnD interaction with the left button of the mouse will produce an AddShape command while interacting on the canvas.
		// A temporary view of the created shape is created and displayed by the canvas.
		// This view is removed at the end of the interaction.
		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new AddShape(drawing, new MyRect(i.getSrcLocalPoint().getX(), i.getSrcLocalPoint().getY(), 1, 1)))
			.on(canvas)
			.first((i, c) -> canvas.setTmpShape(ViewFactory.INSTANCE.createViewShape(c.getShape())))
			.then((i, c) -> {
				final MyRect sh = (MyRect) c.getShape();
				sh.setWidth(i.getTgtLocalPoint().getX() - sh.getX());
				sh.setHeight(i.getTgtLocalPoint().getY() - sh.getY());
			})
			.when(i -> i.getButton() == MouseButton.PRIMARY)
			.end(i -> canvas.setTmpShape(null))
			// The UI command creation process is logged:
			.log(LogLevel.INTERACTION)
			// strict start stops the interaction if the condition ('when') is not fulfilled at an interaction start.
			// Otherwise the interaction will run until the condition is fulfilled.
			.strictStart()
			.help(new AddRectHelpAnimation(learningPane, canvas))
			.bind();

		// A DnD interaction with the right button of the mouse moves the targeted shape.
		// To incrementally moves the shape, the DnD interaction has its parameter 'updateSrcOnUpdate' set to true:
		// At each interaction updates, the source point and object take the latest target point and object.
		// The DnD interaction can be stopped (aborted) by pressing the key 'ESC'. This cancels the ongoing command (that thus needs to be undoable).
		nodeBinder()
			// The command is created using two double bindings that are automatically updated on changes.
			// In the command these binding are @autoUnbind to be unbound on their command termination.
			.usingInteraction(() -> new DnD(true, true))
			.toProduce(i -> {
				final MyShape sh = i.getSrcObject().map(o -> (MyShape) o.getUserData()).get();
				return new MoveShape(sh,
					Bindings.createDoubleBinding(() -> sh.getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()), i.tgtScenePointProperty(), i.srcScenePointProperty()),
					Bindings.createDoubleBinding(() -> sh.getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY()), i.tgtScenePointProperty(), i.srcScenePointProperty()));
			})
			// The binding dynamically registers elements of the given observable list.
			// When nodes are added to this list, these nodes register the binding.
			// When nodes are removed from this list, their binding is cancelled.
			// This permits to interact on nodes (here, shapes) that are dynamically added to/removed from the canvas.
			.on(canvas.getShapesPane().getChildren())
			.when(i -> i.getButton() == MouseButton.SECONDARY)
			// this allows to execute the action each time the interaction updates (and 'when' is true).
			.continuousExecution()
			.first((i, c) -> {
				// Required to grab the focus to get key events
				Platform.runLater(() -> i.getSrcObject().get().requestFocus());
				i.getSrcObject().get().setEffect(new DropShadow(20d, Color.BLACK));
			})
			.endOrCancel(i -> i.getSrcObject().get().setEffect(null))
			.strictStart()
			.help(new MoveRectHelpAnimation(learningPane, canvas))
			// Throttling the received events to reduce the number of events to process.
			// In this specific case, this will cause a lag as a delay of 40 ms (at max).
//			.throttle(40L)
			.bind();


//		nodeBinder()
//			.usingInteraction(() -> new DnD(true, true))
//			.toProduce(i -> new MoveShape(i.getSrcObject().map(o ->(MyShape) o.getUserData()).orElse(null)))
//			// The binding dynamically registers elements of the given observable list.
//			// When nodes are added to this list, these nodes register the binding.
//			// When nodes are removed from this list, their binding is cancelled.
//			// This permits to interact on nodes (here, shapes) that are dynamically added to/removed from the canvas.
//			.on(canvas.getShapesPane().getChildren())
//			.then((i, c) -> c.setCoord(c.getShape().getX() + (i.getTgtScenePoint().getX() - i.getSrcScenePoint().getX()),
//									c.getShape().getY() + (i.getTgtScenePoint().getY() - i.getSrcScenePoint().getY())))
//			.when(i -> i.getButton() == MouseButton.SECONDARY)
//			// this allows to execute the command each time the interaction updates (and 'when' is true).
//			.continuousExecution()
//			.first((i, c) -> {
//				// Required to grab the focus to get key events
//				Platform.runLater(() -> i.getSrcObject().get().requestFocus());
//				i.getSrcObject().get().setEffect(new DropShadow(20d, Color.BLACK));
//			})
//			.end(i -> i.getSrcObject().get().setEffect(null))
//		    .strictStart()
//			.bind();

		/*
		 * A DnD on the colour picker produces ChangeCol commands when the target of the DnD is a shape
		 * (the shape we want to change the colour). The interim feedback changes the cursor during the DnD to show the dragged colour.
		 * Note that the feedback callback is not optimised here as the colour does not change during the DnD. The cursor
		 * should be changed in 'first'
		 */
		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new ChangeColour(lineCol.getValue(), null))
			.on(lineCol)
			.first(i -> lineCol.getScene().setCursor(new ColorCursor(lineCol.getValue())))
			.then((i, c) -> i.getTgtObject().map(view -> (MyShape) view.getUserData()).ifPresent(sh -> c.setShape(sh)))
			.when(i -> i.getTgtObject().orElse(null) instanceof Shape)
			.endOrCancel(i -> lineCol.getScene().setCursor(Cursor.DEFAULT))
			.bind();

		/*
		 * A mouse pressure creates an anonymous command that simply shows a message in the console.
		 */
		anonCmdBinder(() -> System.out.println("An example of the anonymous command."))
			.usingInteraction(Press::new)
			.on(canvas)
			.bind();

		/*
		 * A widget binding that execute a command asynchronously.
		 * Widgets and properties are provided to the binding to:
		 * show/hide the cancel button, provide widgets with information regarding the progress of the command execution.
		 */
		buttonBinder()
			.toProduce(Save::new)
			.on(save)
			.async(cancel, progressbar.progressProperty(), textProgress.textProperty())
			.bind();
	}


	/**
	 * Binds the model to the view. MVP layout: this presenter binds the model to the view. These two last do not know each others.
	 */
	public void bindModel() {
		final Group shapesPane = canvas.getShapesPane();

		drawing.getShapes().addListener((ListChangeListener.Change<? extends MyShape> ch) -> {
			while(ch.next()) {
				if(ch.wasAdded()) {
					shapesPane.getChildren().addAll(
						ch.getAddedSubList().stream().map(sh -> ViewFactory.INSTANCE.createViewShape(sh)).filter(Objects::nonNull).collect(Collectors.toList()));
				}
			}
		});
	}

	@Override
	public void initialize(final URL url, final ResourceBundle res) {
		bindModel();
		textProgress.visibleProperty().bind(cancel.visibleProperty());
		progressbar.visibleProperty().bind(cancel.visibleProperty());
		cancel.setVisible(false);
		configureBindings();
	}

	public MyDrawing getDrawing() {
		return drawing;
	}
}

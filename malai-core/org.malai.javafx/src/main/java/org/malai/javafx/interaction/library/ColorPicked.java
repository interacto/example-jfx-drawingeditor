/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxColorPickedTransition;

/**
 * An interaction dedicated to the use of colour pickers.
 * @author Arnaud BLOUIN
 */
public class ColorPicked extends NodeInteraction<ColorPicker> {
	private final EventHandler<ActionEvent> event;

	/**
	 * Creates the interaction.
	 */
	public ColorPicked() {
		super();
		initStateMachine();
		event = evt -> onJfxColorPicked((ColorPicker) evt.getSource());
	}


	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("picked");

		addState(pressed);

		new JfxColorPickedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ColorPicked.this.widget = this.widget;
			}
		};
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof ColorPicker) {
			node.removeEventHandler(ActionEvent.ACTION, event);
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof ColorPicker) {
			node.addEventHandler(ActionEvent.ACTION, event);
		}
	}
}

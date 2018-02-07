/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction2.library;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;

public class KeyPressure extends KeyInteraction<KeyPressureFSM, Event> {
	private final KeyPressureFSM.KeyPressureFSMHandler handler;

	public KeyPressure() {
		super(new KeyPressureFSM());

		handler = new KeyPressureFSM.KeyPressureFSMHandler() {
			@Override
			public void onKeyPressure(final KeyEvent event) {
				setKeyData(event);
			}

			@Override
			public void reinitData() {
				KeyPressure.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}
}
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
package org.malai.javafx.action.library;

/**
 * A save action.
 * @author Arnaud Blouin
 */
public class Save<B extends Object> extends IOAction<B> {
	/**
	 * Creates a save action.
	 */
	public Save() {
		super();
	}

	@Override
	protected void doActionBody() {
		ok = openSaveManager.save(file.getPath(), progressBar, statusWidget);
		ui.setModified(false);
	}
}

package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

public class SingleObjectFullscreenApp extends PApplet {

	private static final boolean FULLSCREEN_MODE = true;

	SimpleTouch simpleTouch;

	public static void main(String args[]) {
		PApplet.main(new String[] { SingleObjectFullscreenApp.class.getName() });
	}

	public void settings() {
		if (FULLSCREEN_MODE) {
			fullScreen(P3D);
		} else {
			size(800, 600, P3D);
		}
		smooth();
	}

	public void setup() {
		simpleTouch = new SimpleTouch(this);
		GridObject gridObject = new GridObject(this, width / 2 - 200, height / 2 - 200, 400, 400);
		simpleTouch.addTouchObject(gridObject);
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

/**
 * Move, scale, and rotate a visual object. 
 * 
 * Creates a grid object, and connects it to the SimpleTouch transformation and drawing process. 
 */
public class SingleObjectApp extends PApplet {

	SimpleTouch simpleTouch;

	public static void main(String[] args) {
		// Needs to set env var java.library.path=lib, explicitly, as jogl is not found, otherwise.
		// Thus, needs to be an application.
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.SingleObjectApp" });
	}

	public void setup() {
		size(800, 600, OPENGL);
		smooth();

		simpleTouch = new SimpleTouch(this);
		simpleTouch.addTouchObject(new GridObject(this, 20, 20, 300, 300));
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

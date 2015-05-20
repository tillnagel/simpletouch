package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

/**
 * Move, scale, and rotate multiple circular objects (with transparent background).
 */
public class RadialAndTransparentObjectApp extends PApplet {

	SimpleTouch simpleTouch;

	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.RadialAndTransparentObjectApp" });
	}

	public void setup() {
		size(800, 600, OPENGL);
		smooth();

		simpleTouch = new SimpleTouch(this);

		// Randomly generates some transformable objects.
		for (int i = 0; i < 10; i++) {
			float s = random(60, 400);
			RadialAndTransparentObject rObject = new RadialAndTransparentObject(this, random(width - s), random(height - s), s, s);
			simpleTouch.addTouchObject(rObject);
		}
	}

	public void draw() {
		background(200);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

public class ManuallyTransformApp extends PApplet {

	SimpleTouch simpleTouch;

	public static void main(String args[]) {
		PApplet.main(new String[] { ManuallyTransformApp.class.getName() });
	}

	public void settings() {
		size(800, 600, P3D);
		smooth();
	}

	public void setup() {
		simpleTouch = new SimpleTouch(this);

		GridObject gridObject = new GridObject(this, width / 2 - 400, height / 2 - 400, 800, 800);
		gridObject.color = color(140);
		// Scale to half its size
		gridObject.scale(0.5f);
		// Rotate by 10 degrees clock-wise
		gridObject.rotate(PApplet.radians(10));

		simpleTouch.addTouchObject(gridObject);
	}

	public void draw() {
		background(200);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}
}

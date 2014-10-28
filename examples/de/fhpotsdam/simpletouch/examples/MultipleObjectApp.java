package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;
import de.fhpotsdam.simpletouch.TouchObject;

public class MultipleObjectApp extends PApplet {

	SimpleTouch simpleTouch;

	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.MultipleObjectApp" });
	}

	public void setup() {
		size(625, 300, OPENGL);
		smooth();

		simpleTouch = new SimpleTouch(this);
		TouchObject to1 = new GridObject(this, 30, 30, 300, 150, "Grid A (always on top)");
		to1.setAlwaysOnTop(true);
		simpleTouch.addTouchObject(to1);
		simpleTouch.addTouchObject(new GridObject(this, 500, 50, 300, 300, "Grid B"));
		simpleTouch.addTouchObject(new GridObject(this, width / 2 - 100,
				height / 2 - 100, 200, 200, "Grid C"));
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

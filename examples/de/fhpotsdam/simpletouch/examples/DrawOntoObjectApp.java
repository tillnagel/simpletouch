package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

/**
 * Draws independent object onto transformed object.
 * 
 * Only absolute screen position is used, i.e. the position in the canvas of the moved, rotated, and
 * scaled object, but drawing of the inner object is independent, i.e. not rotated or scaled. Uses
 * getScreenFromObjectPosition to convert between local and screen coordinate systems.
 * 
 * For a dependent inner object, simply write your own class (such as GridObject or RadialObject),
 * and draw it there.
 */
public class DrawOntoObjectApp extends PApplet {

	SimpleTouch simpleTouch;

	GridObject gridObject;

	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.DrawOntoObjectApp" });
	}

	public void setup() {
		size(800, 600);
		smooth();

		simpleTouch = new SimpleTouch(this);
		gridObject = new GridObject(this, width / 2, height / 2, 300, 300);
		simpleTouch.addTouchObject(gridObject);
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		
		// Draws circle at the origin position (0,0) of the object.
		// Gets screen position for origin position in the local coordinate system. 
		float[] pos1 = gridObject.getScreenFromObjectPosition(0, 0);
		stroke(124);
		fill(200);
		ellipse(pos1[0], pos1[1], 20, 20);

		// Draws rectangle at center right position of the object.
		float[] pos2 = gridObject.getScreenFromObjectPosition(250, 150);
		fill(200);
		rect(pos2[0], pos2[1], 40, 40);
	}

}

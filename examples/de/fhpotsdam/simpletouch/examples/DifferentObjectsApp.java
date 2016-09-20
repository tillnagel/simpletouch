package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import processing.core.PImage;
import de.fhpotsdam.simpletouch.SimpleTouch;
import de.fhpotsdam.simpletouch.examples.threading.ImageObject;

public class DifferentObjectsApp extends PApplet {

	SimpleTouch simpleTouch;

	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.DifferentObjectsApp" });
	}

	public void settings() {
		size(700, 300, P3D);
		smooth();
	}

	public void setup() {
		simpleTouch = new SimpleTouch(this);

		GridObject go = new GridObject(this, 500, 50, 300, 300, "Grid");
		go.color = color(230, 103, 175);
		go.gridColor = color(255, 0, 0);
		simpleTouch.addTouchObject(go);
		PImage img = loadImage("incom-img1.png");
		simpleTouch.addTouchObject(new ImageObject(this, img, 200, 200));
		simpleTouch.addTouchObject(new RadialAndTransparentObject(this, 100, 100, 200, 200));
	}

	public void draw() {
		background(230);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

package de.fhpotsdam.simpletouch.examples;

import java.util.Arrays;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

public class Multiple3DObjectApp extends PApplet {

	private static final boolean FULLSCREEN_MODE = false;

	SimpleTouch simpleTouch;

	GridObject goB = null;

	public static void main(String args[]) {
		PApplet.main(new String[] { Multiple3DObjectApp.class.getName() });
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
		textFont(createFont("sans-serif", 12));

		simpleTouch = new SimpleTouch(this);
		simpleTouch.addTouchObject(new GridObject(this, width / 2 - 150, height / 2 - 150, 300, 300, "A"));
		goB = new GridObject(this, 0, 0, 300, 300, "B");
		;
		simpleTouch.addTouchObject(goB);
		simpleTouch.addTouchObject(new GridObject(this, 600, 50, 200, 200, "C"));
	}

	public void draw() {
		background(240);

		// hint(ENABLE_DEPTH_TEST);

		simpleTouch.draw();
		simpleTouch.drawCursors();

		fill(255, 0, 0);
		float[] v = goB.getScreenFromObjectPosition(10, 10);
		float x = screenX(v[0], v[1], v[2]);
		float y = screenY(v[0], v[1], v[2]);

		// hint(DISABLE_DEPTH_TEST);
		ellipse(x, y, 10, 10);
	}

	public void keyPressed() {
		if (key == 'x') {
			goB.rotate(0.1f, 0, 0);
		}
		if (key == 'X') {
			goB.rotate(-0.1f, 0, 0);
		}

		if (key == 'z') {
			goB.rotate(0, 0, 0.1f);
		}
		if (key == 'Z') {
			goB.rotate(0, 0, -0.1f);
		}

		float[] v = goB.getScreenFromObjectPosition(10, 10);
		println("xyz=" + Arrays.toString(v));
	}

	public void mouseMoved() {
		println("mouse=" + mouseX + "," + mouseY);

		if (goB.isHit(mouseX, mouseY)) {
			goB.color = color(255, 0, 0, 200);
		} else {
			goB.color = color(0, 0, 0, 200);
		}
	}

}

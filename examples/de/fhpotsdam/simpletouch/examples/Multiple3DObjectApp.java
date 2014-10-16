package de.fhpotsdam.simpletouch.examples;

import java.util.Arrays;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;

public class Multiple3DObjectApp extends PApplet {

	private static final boolean FULLSCREEN_MODE = false;

	SimpleTouch simpleTouch;

	GridObject goB = new GridObject(this, 0, 0, 300, 300, "B");

	public static void main(String[] args) {
		if (FULLSCREEN_MODE) {
			PApplet.main(new String[] { "--present", "--exclusive", "--bgcolor=#000000",
					"--hide-stop", "de.fhpotsdam.simpletouch.examples.MultipleObjectApp" });
		} else {
			PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.MultipleObjectApp" });
		}
	}

	public void setup() {
		if (FULLSCREEN_MODE) {
			size(displayWidth, displayHeight);
		} else {
			size(800, 600);
		}
		smooth();
		textFont(loadFont("Miso-Light-12.vlw"), 12);

		simpleTouch = new SimpleTouch(this);
		simpleTouch.addTouchObject(new GridObject(this, width / 2 - 150,
				height / 2 - 150, 300, 300, "A"));
		simpleTouch.addTouchObject(goB);
		simpleTouch.addTouchObject(new GridObject(this, 600, 50, 200, 200, "C"));
	}

	public void draw() {
		background(240);

		//hint(ENABLE_DEPTH_TEST);

		simpleTouch.draw();
		simpleTouch.drawCursors();

		fill(255, 0, 0);
		float[] v = goB.getScreenFromObjectPosition(10, 10);
		float x = screenX(v[0], v[1], v[2]);
		float y = screenY(v[0], v[1], v[2]);
		
		//hint(DISABLE_DEPTH_TEST);
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
		println("xyz="  + Arrays.toString(v));
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

package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import codeanticode.glgraphics.GLConstants;
import de.fhpotsdam.simpletouch.SimpleTouch;

public class SingleObjectFullscreenApp extends PApplet {

	private static final boolean FULLSCREEN_MODE = true;

	SimpleTouch simpleTouch;

	public static void main(String[] args) {
		if (FULLSCREEN_MODE) {
			PApplet.main(new String[] { "--present", "--exclusive", "--bgcolor=#000000",
					"--hide-stop", "de.fhpotsdam.simpletouch.examples.SingleObjectFullscreenApp" });
		} else {
			PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.SingleObjectFullscreenApp" });
		}
	}

	public void setup() {
		if (FULLSCREEN_MODE) {
			size(screen.width, screen.height, GLConstants.GLGRAPHICS);
		} else {
			size(800, 600, GLConstants.GLGRAPHICS);
		}
		smooth();
		
		simpleTouch = new SimpleTouch(this);
		GridObject gridObject = new GridObject(this, width/2-200, height/2-200, 400, 400);
		simpleTouch.addTouchObject(gridObject);
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

}

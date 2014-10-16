package de.fhpotsdam.simpletouch.examples.dynamic;

import processing.core.PApplet;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import de.fhpotsdam.simpletouch.SimpleTouch;
import de.fhpotsdam.simpletouch.examples.GridObject;

/**
 * Dynamically creates new transformable objects on double tap.
 * 
 * Besides, this examples shows how to use the same TuioClient for application and SimpleTouch.
 * 
 */
public class DoubleTapCreationApp extends PApplet implements TuioListener {

	TuioClient tuioClient;
	SimpleTouch simpleTouch;

	// double tap properties
	float oldTapX;
	float oldTapY;
	float doubleTapMaxTime = 20; // in frames
	float doubleTapTime = 0;
	float doubleTapDistance = 50; // in pixels
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.dynamic.DoubleTapCreationApp" });
	}

	public void setup() {
		size(800, 600);
		smooth();
		
		// Creates TuioClient and connects this app
		tuioClient = new TuioClient();
		tuioClient.connect();
		tuioClient.addTuioListener(this);

		// Creates SimpleTouch, and passes the TuioClient
		simpleTouch = new SimpleTouch(this, tuioClient);
		simpleTouch.addTouchObject(new GridObject(this, 100, 100, 200, 200));
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();

		doubleTapTime--;
	}

	public void doubleTapped(float x, float y) {
		simpleTouch.addTouchObject(new GridObject(this, x, y, 100, 100));
	}

	@Override
	public void addTuioCursor(TuioCursor cur) {
		float tapX = cur.getScreenX(width);
		float tapY = cur.getScreenY(height);

		if (dist(oldTapX, oldTapY, tapX, tapY) < doubleTapDistance && doubleTapTime > 0) {
			doubleTapped(tapX, tapY);
			oldTapX = -1;
			oldTapY = -1;
		} else {
			// Start new double tap timer
			doubleTapTime = doubleTapMaxTime;
			oldTapX = tapX;
			oldTapY = tapY;
		}
	}

	@Override
	public void removeTuioCursor(TuioCursor cur) {
	}

	@Override
	public void updateTuioCursor(TuioCursor cur) {
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
	}

	@Override
	public void refresh(TuioTime arg0) {
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
	}

}

package de.fhpotsdam.simpletouch.examples.inner;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.SimpleTouch;
import de.fhpotsdam.simpletouch.TouchObject;

public class InnerInteractionTestApp extends PApplet {

	private SimpleTouch simpleTouch;
	InnerImageObject imgObj1;
	InnerImageObject imgObj2;

	public static void main(String[] args) {
		PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.inner.InnerInteractionTestApp" });
	}

	public void setup() {
		size(800, 600, OPENGL);
		smooth();
		textFont(createFont("sans-serif", 12));

		simpleTouch = new SimpleTouch(this);
		imgObj1 = new InnerImageObject(this, "test-pattern.jpg", 20, 20, 300, 300);
		simpleTouch.addTouchObject(imgObj1);
		imgObj2 = new InnerImageObject(this, "fhp-logo.gif", 500, 220, 200, 350);
		simpleTouch.addTouchObject(imgObj2);

		addMouseWheelListener(new java.awt.event.MouseWheelListener() {
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
				mouseWheel(evt.getWheelRotation());
			}
		});
	}

	public void draw() {
		background(240);
		simpleTouch.draw();

		drawThingsOnObject1();
	}

	protected void drawThingsOnObject1() {
		float x = 240;
		float y = 180;
		float sxy[] = imgObj1.getScreenFromInnerObjectPosition(x, y);
		ellipse(sxy[0], sxy[1], 10, 10);

		float ixy[] = imgObj1.getInnerObjectFromScreenPosition(mouseX, mouseY);
		text(ixy[0] + "," + ixy[1], mouseX, mouseY);
	}

	public void mouseDragged() {
		for (TouchObject imgObj : simpleTouch.getTouchObjects()) {
			if (imgObj.isHit(mouseX, mouseY)) {
				// Inner offset is in object coordinates.
				float[] pxy = imgObj.getObjectFromScreenPosition(pmouseX, pmouseY);
				float[] mxy = imgObj.getObjectFromScreenPosition(mouseX, mouseY);

				float dx = mxy[0] - pxy[0];
				float dy = mxy[1] - pxy[1];

				imgObj.addInnerOffset(dx, dy);
			}
		}
	}

	public void mousePressed() {
		for (TouchObject imgObj : simpleTouch.getTouchObjects()) {
			if (imgObj.isHit(mouseX, mouseY)) {
				if (mouseEvent.getClickCount() == 2) {
					float[] xy = imgObj.getObjectFromScreenPosition(mouseX, mouseY);
					imgObj.panToObjectCenter(xy[0], xy[1]);
				}
			}
		}
	}

	public void mouseWheel(float delta) {
		for (TouchObject imgObj : simpleTouch.getTouchObjects()) {
			if (imgObj.isHit(mouseX, mouseY)) {
				imgObj.setInnerTransCenter(mouseX, mouseY);
				if (delta < 0) {
					imgObj.innerScale(0.9f);
				} else if (delta > 0) {
					imgObj.innerScale(1.1f);
				}
			}
		}
	}

	public void keyPressed() {
		for (TouchObject imgObj : simpleTouch.getTouchObjects()) {
			if (imgObj.isHit(mouseX, mouseY)) {
				if (key == 'c') {
					float[] xy = imgObj.getObjectFromInnerObjectPosition(240, 180);
					imgObj.panToObjectCenter(xy[0], xy[1]);
				}

				// set transformation center
				if (key == 'i') {
					imgObj.setInnerTransCenter(mouseX, mouseY);
				}

				// scale
				if (key == '+') {
					imgObj.innerScale(1.1f);
				}
				if (key == '-') {
					imgObj.innerScale(1 / 1.1f);
				}

				// rotate
				if (key == 'r') {
					imgObj.innerRotate(PI / 20);
				}
				if (key == 'l') {
					imgObj.innerRotate(-PI / 20);
				}

				// pan
				if (key == CODED) {
					if (keyCode == RIGHT) {
						imgObj.addInnerOffset(-10, 0);
					}
					if (keyCode == LEFT) {
						imgObj.addInnerOffset(10, 0);
					}
					if (keyCode == DOWN) {
						imgObj.addInnerOffset(0, -10);
					}
					if (keyCode == UP) {
						imgObj.addInnerOffset(0, 10);
					}
				}
			}
		}
	}
}

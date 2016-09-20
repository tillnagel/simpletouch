package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import de.fhpotsdam.simpletouch.TouchObject;

/**
 * Radial and transparent object to transform.
 */
public class RadialAndTransparentObject extends TouchObject {

	public static final float strokeWeight = 2;

	public RadialAndTransparentObject(PApplet p, float offsetX, float offsetY, float width, float height) {
		super(p, offsetX, offsetY, width, height);
	}

	@Override
	public void internalDraw() {
		pg.stroke(0, 30);
		pg.strokeWeight(strokeWeight);

		pg.fill(57, 230, 57, 20);
		pg.ellipse(width / 2, height / 2, width - strokeWeight, height - strokeWeight);
	}

	@Override
	public boolean isHit(int checkX, int checkY) {
		float[] check = getObjectFromScreenPosition(checkX, checkY);
		return PApplet.dist(check[0], check[1], width / 2, height / 2) < width / 2;
	}

}

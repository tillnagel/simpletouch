package de.fhpotsdam.simpletouch.examples.threading;

import processing.core.PApplet;
import processing.core.PImage;
import de.fhpotsdam.simpletouch.TouchObject;

public class ImageObject extends TouchObject {

	PImage image;

	public ImageObject(PApplet p, PImage image, float offsetX, float offsetY) {
		super(p, offsetX, offsetY, image.width, image.height);
		this.image = image;
	}

	@Override
	public void internalDraw() {
		pg.background(0);
		pg.image(image, 0, 0, width, height);
	}
}

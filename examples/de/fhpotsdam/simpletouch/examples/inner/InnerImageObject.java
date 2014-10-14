package de.fhpotsdam.simpletouch.examples.inner;

import processing.core.PApplet;
import processing.core.PImage;
import de.fhpotsdam.simpletouch.TouchObject;

public class InnerImageObject extends TouchObject {

	private PImage image;

	public InnerImageObject(PApplet p, String imageName, float offsetX, float offsetY, float width,
			float height) {
		super(p, offsetX, offsetY, width, height);

		this.image = p.loadImage(imageName);
	}

	@Override
	public void internalDraw() {
		pg.background(0);
		pg.image(image, 0, 0);
	}

	@Override
	public float getInnerWidth() {
		return image.width;
	}

	@Override
	public float getInnerHeight() {
		return image.height;
	}

	

}

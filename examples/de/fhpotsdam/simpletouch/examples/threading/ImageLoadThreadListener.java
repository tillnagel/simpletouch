package de.fhpotsdam.simpletouch.examples.threading;

import processing.core.PImage;

public interface ImageLoadThreadListener {

	public PImage loadImage(String imageName);

	public void onImageLoaded(PImage image, int index);
}

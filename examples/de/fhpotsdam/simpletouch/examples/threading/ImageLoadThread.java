package de.fhpotsdam.simpletouch.examples.threading;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageLoadThread extends Thread {

	protected ImageLoadThreadListener listener;
	protected String imageName;
	protected int index;

	public ImageLoadThread(ImageLoadThreadListener listener, String imageName, int index) {
		this.listener = listener;
		this.imageName = imageName;
		this.index = index;
		PApplet.println("Created new thread for " + imageName);
	}

	// We must implement run, this gets triggered by start()
	public void run() {
		PImage img = listener.loadImage(imageName);
		PApplet.println("Image loaded: " + imageName);
		listener.onImageLoaded(img, index);
	}

}

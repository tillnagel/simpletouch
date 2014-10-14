package de.fhpotsdam.simpletouch.examples.threading;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;
import codeanticode.glgraphics.GLConstants;
import de.fhpotsdam.simpletouch.SimpleTouch;

/**
 * Gets an RSS feed, and loads and displays the containing images.
 * Each image is loaded via an own thread 
 * 
 */
public class ImageApp extends PApplet implements ImageLoadThreadListener {

	private static final boolean FULLSCREEN_MODE = false;
	private static final int MAX_IMAGES = 10;

	private SimpleTouch simpleTouch;

	String feedUrl = "http://ffffound.com/home/tillnm/found/feed";
	
	PImage loading;

	public static void main(String[] args) {
		if (FULLSCREEN_MODE) {
			PApplet.main(new String[] { "--present", "--exclusive", "--bgcolor=#000000",
					"--hide-stop", "de.fhpotsdam.simpletouch.examples.threading.ImageApp" });
		} else {
			PApplet.main(new String[] { "de.fhpotsdam.simpletouch.examples.threading.ImageApp" });
		}
	}

	public void setup() {
		if (FULLSCREEN_MODE) {
			size(screen.width, screen.height, GLConstants.GLGRAPHICS);
		} else {
			size(1024, 768, GLConstants.GLGRAPHICS);
		}
		smooth();
		
		simpleTouch = new SimpleTouch(this);

		loadFeed();
	}

	public void draw() {
		background(240);

		simpleTouch.draw();
		simpleTouch.drawCursors();
	}

	public void onImageLoaded(PImage img, int index) {
		ImageObject imageObject = new ImageObject(this, img, random(width) - img.width / 2,
				random(height) - img.height / 2);
		simpleTouch.addTouchObject(imageObject);
	}

	public void loadFeed() {
		XMLElement rss = new XMLElement(this, feedUrl);
		// Get thumbnail of each element
		XMLElement[] imageURLElements = rss.getChildren("channel/item/media:content");
		for (int i = 0; i < imageURLElements.length && i < MAX_IMAGES; i++) {
			String imageURL = imageURLElements[i].getStringAttribute("url");
			Thread thread = new ImageLoadThread(this, imageURL, i);
			thread.start();
		}
	}

}

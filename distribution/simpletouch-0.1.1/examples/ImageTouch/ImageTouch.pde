import processing.opengl.*;
import codeanticode.glgraphics.*;
import TUIO.*;
import de.fhpotsdam.simpletouch.*;
import de.fhpotsdam.simpletouch.examples.threading.ImageObject;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, GLConstants.GLGRAPHICS);

  simpleTouch = new SimpleTouch(this);
  
  PImage img = loadImage("test-pattern.jpg");
  ImageObject imageObject = new ImageObject(this, img, 200, 200);
  simpleTouch.addTouchObject(imageObject);
}

void draw() {
  background(250);

  simpleTouch.draw();
}


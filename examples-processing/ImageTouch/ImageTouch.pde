import processing.opengl.*;
import codeanticode.glgraphics.*;
import TUIO.*;
import de.fhpotsdam.simpletouch.*;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, GLConstants.GLGRAPHICS);

  simpleTouch = new SimpleTouch(this);
  
  PImage img = loadImage("test-pattern.jpg");
  TouchImage touchImage = new TouchImage(this, img, 200, 200);
  simpleTouch.addTouchObject(touchImage);
}

void draw() {
  background(250);

  simpleTouch.draw();
}


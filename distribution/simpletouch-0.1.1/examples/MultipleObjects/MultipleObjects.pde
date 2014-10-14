import processing.opengl.*;
import codeanticode.glgraphics.*;
import TUIO.*;
import de.fhpotsdam.simpletouch.*;
import de.fhpotsdam.simpletouch.examples.GridObject;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, GLConstants.GLGRAPHICS);

  simpleTouch = new SimpleTouch(this);
  
  TouchColorRect tcr1 = new TouchColorRect(this, color(240, 212, 168, 200), 100, 100, 400, 400);
  simpleTouch.addTouchObject(tcr1);
  simpleTouch.addTouchObject(new TouchColorRect(this, color(255, 244, 194, 200), 200, 470, 200, 400));
  simpleTouch.addTouchObject(new TouchColorRect(this, color(203, 219, 149, 200), random(width), random(height), 300, 200));
}

void draw() {
  background(250);

  simpleTouch.draw();
}


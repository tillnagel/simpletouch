import processing.opengl.*;
import codeanticode.glgraphics.*;
import TUIO.*;
import de.fhpotsdam.simpletouch.*;
import de.fhpotsdam.simpletouch.examples.GridObject;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, GLConstants.GLGRAPHICS);

  simpleTouch = new SimpleTouch(this);
  simpleTouch.addTouchObject(new TouchRect(this, 200, 200, 400, 400));
}

void draw() {
  background(250);
  
  simpleTouch.draw();
}

import TUIO.*;
import de.fhpotsdam.simpletouch.*;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, OPENGL);

  simpleTouch = new SimpleTouch(this);
  
  MovingTouchObject mto = new MovingTouchObject(this, 200, 200, 400, 400);
  simpleTouch.addTouchObject(mto);
}

void draw() {
  background(250);

  simpleTouch.draw();
}


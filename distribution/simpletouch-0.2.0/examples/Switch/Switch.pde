import TUIO.*;
import de.fhpotsdam.simpletouch.*;

SimpleTouch simpleTouch;

void setup() {
  size(800, 800, OPENGL);

  simpleTouch = new SimpleTouch(this);
  
  TouchSwitch ts = new TouchSwitch(this, 200, 200, 400, 400);
  simpleTouch.addTouchObject(ts);
}

void draw() {
  background(250);

  simpleTouch.draw();
  simpleTouch.drawCursors();
}


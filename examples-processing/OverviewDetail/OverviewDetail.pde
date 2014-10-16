import processing.opengl.*;
import codeanticode.glgraphics.*;
import TUIO.*;
import de.fhpotsdam.simpletouch.*;

SimpleTouch simpleTouch;
PImage bgImage;
TouchRect touchRect;

int loupeSize = 200;
float loupeFactor = 2.4;

void setup() {
  size(800, 450, GLConstants.GLGRAPHICS);

  simpleTouch = new SimpleTouch(this);

  bgImage = loadImage("test-pattern.jpg");

  float viewportSize = loupeSize / loupeFactor;
  touchRect = new TouchRect(this, color(255, 0, 0, 127), 200, 200, viewportSize, viewportSize);
  simpleTouch.addTouchObject(touchRect);
}

void draw() {
  // Draw background image
  image(bgImage, 0, 0, 800, 450);
  
  // Get position
  int x = int(touchRect.getX() * loupeFactor);
  int y = int(touchRect.getY() * loupeFactor);
  PImage clip = bgImage.get(x, y, loupeSize, loupeSize);
  
  // Draw detail image (clip) and border
  noStroke();
  fill(200, 127); 
  rect(580, 10, 210, 210);
  image(clip, 585, 15);
  
  // Draw viewport (as touch object)
  simpleTouch.draw();
  simpleTouch.drawCursors();
}


class TouchSwitch extends TouchObject {

  boolean active = false;

  TouchSwitch(PApplet p, float x, float y, float width, float height) {
    super(p, x, y, width, height);
  }

  void internalDraw() {
    pg.noStroke();
    if (isTapped()) {
      pg.fill(127, 0, 0, 127);
    }
    else {
      if (active) {
        pg.fill(255, 0, 0, 127);
      }
      else {
        pg.fill(127, 127);
      }
    }
    pg.rect(0, 0, width, height);
  }

  void tapped() {
    active = !active;
  }
}


class TouchRect extends TouchObject {
  
  public TouchRect(PApplet p, float x, float y, float width, float height) {
    super(p, x, y, width, height);
  }
  
  public void internalDraw() {
    pg.noStroke();
    pg.fill(127, 127);
    pg.rect(0, 0, width, height);
  }
  
}

class TouchRect extends TouchObject {
  
  color col;
  
  public TouchRect(PApplet p, color c, float x, float y, float width, float height) {
    super(p, x, y, width, height);
    col = c;
  }
  
  public void internalDraw() {
    pg.strokeWeight(4);
    pg.stroke(col);
    pg.noFill();
    pg.rect(0, 0, width, height);
  }
  
}

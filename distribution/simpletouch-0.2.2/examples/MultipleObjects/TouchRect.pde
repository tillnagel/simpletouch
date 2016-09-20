class TouchColorRect extends TouchObject {
  
  color col;
  
  public TouchColorRect(PApplet p, color c, float x, float y, float width, float height) {
    super(p, x, y, width, height);
    col = c;
  }
  
  public void internalDraw() {
    pg.noStroke();
    pg.fill(col);
    pg.rect(0, 0, width, height);
  }
  
}

class MovingTouchObject extends TouchObject {
  
  float vx = 1.0;
  float vy = 1.0;
  
  public MovingTouchObject(PApplet p, float x, float y, float width, float height) {
    super(p, x, y, width, height);
  }
  
  public void internalDraw() {
    if (!isTapped) {
      animate();
    }
    
    pg.noStroke();
    pg.fill(127, 127);
    pg.ellipse(0, 0, width, height);
  }
  
  public void animate() {
    float nx = getX() + vx;
    float ny = getY() + vy;
    
    if (nx < 0 || nx > p.width) {
      vx = -vx;
    }
    if (ny < 0 || ny > p.height) {
      vy = -vy;
    }
    
    setPosition(nx, ny);
  }  
  
}

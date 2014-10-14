class TouchImage extends TouchObject {

  PImage img;

  public TouchImage(PApplet p, PImage image, float offsetX, float offsetY) {
    super(p, offsetX, offsetY, image.width, image.height);
    this.img = image;
  }

  public void internalDraw() {
    pg.background(0);
    pg.image(img, 0, 0, width, height);
  }
}


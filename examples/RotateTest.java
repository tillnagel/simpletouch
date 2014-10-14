import processing.core.PApplet;

public class RotateTest extends PApplet {

	float rot = PI / 4;
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "RotateTest" });
	}

	public void setup() {
		size(250, 250, JAVA2D);
		smooth();
		stroke(140);
	}

	public void draw() {
		background(255);
		drawTest();
	}

	public void drawTest() {
		rot = rot + 0.01f;

		translate(width / 2, height / 2);
		rotate(rot);

		line(4, -1, 102, -1);
		line(4, +1, 102, +1);
		line(4, +3, 102, +3);
	}

	public void keyPressed() {
		println("renderer=" + g.getClass().getName());
	}
	
}

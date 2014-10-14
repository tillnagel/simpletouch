package de.fhpotsdam.simpletouch.examples;

import processing.core.PApplet;
import processing.core.PFont;
import de.fhpotsdam.simpletouch.TouchObject;

/**
 * Simple transformable object displaying a grid for testing purposes.
 */
public class GridObject extends TouchObject {

	protected int color;
	protected int gridColor;

	String name;

	PFont font;

	public GridObject(PApplet p, float offsetX, float offsetY, float width, float height) {
		super(p, offsetX, offsetY, width, height);

		this.color = p.color(p.random(255), p.random(255), p.random(255), 230);
		this.gridColor = p.color(0, 150);
		this.font = p.loadFont("ml.vlw");
	}

	public GridObject(PApplet p, float offsetX, float offsetY, float width, float height,
			String name) {
		this(p, offsetX, offsetY, width, height);
		this.name = name;
	}

	/**
	 * Draws a 10x10px sized grid, and an optional label.
	 */
	@Override
	public void internalDraw() {
		pg.fill(color);
		pg.stroke(gridColor);

		for (float i = 0; i <= width - 10; i += 10) {
			for (float j = 0; j <= height - 10; j += 10) {
				pg.rect(i, j, 10, 10);
			}
		}

		if (name != null) {
			pg.textFont(font, 12);
			float w = pg.textWidth(name);
			pg.fill(0, 200);
			pg.rect(9, 10, w + 4, 12);
			pg.fill(255, 240);
			pg.text(name, 10, 20);
		}
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}

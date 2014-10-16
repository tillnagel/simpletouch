package de.fhpotsdam.simpletouch;

import processing.core.PApplet;
import processing.core.PMatrix3D;
import processing.core.PVector;
import codeanticode.glgraphics.GLGraphicsOffScreen;

/**
 * Simple transformable object.
 * 
 * 
 * rotate(), setOffset(), etc are outer object methods
 * 
 * innerRotate(), setInnerOffset(), etc are inner (off-screen buffer) object methods
 * 
 * 
 * REVISIT For 3D conversion methods need to add one more layer (using screenX, and screenY to
 * convert from world to screen)
 */
public abstract class TransformableObject {

	/** The Processing applet. */
	protected PApplet p;

	/** Width of this object */
	protected float width;
	/** Height of this object */
	protected float height;

	/** Offset of the outer object. In screen coordinates. */
	protected PVector offset = new PVector();

	/** Transformation center, to rotate and scale around. In screen coordinates. */
	private PVector transCenter = new PVector();

	/** Rotation angle */
	protected PVector angle = new PVector(0, 0, 0);

	/** Scaling factor */
	protected float scale = 1;

	/** Outer transformation matrix. */
	private PMatrix3D matrix = new PMatrix3D();

	/** Offset of the inner object. In object coordinates. */
	protected PVector innerOffset = new PVector();

	/** Transformation center of the inner object. In object coordinates. */
	private PVector innerTransCenter = new PVector(0, 0);

	/** Inner rotation angle. */
	protected PVector innerAngle = new PVector(0, 0, 0);

	/** Inner scaling factor. */
	protected float innerScale = 1;

	/** Inner transformation matrix. */
	private PMatrix3D innerMatrix = new PMatrix3D();

	/** PGraphics to draw all graphics on. Is used as inner canvas. */
	protected GLGraphicsOffScreen pg;

	public TransformableObject(PApplet p, float offsetX, float offsetY, float width, float height) {
		this.p = p;

		this.width = width;
		this.height = height;

		this.offset.x = offsetX;
		this.offset.y = offsetY;

		this.transCenter.x = width / 2;
		this.transCenter.y = height / 2;

		this.innerTransCenter.x = width / 2;
		this.innerTransCenter.y = height / 2;

		calculateMatrix();
	}
	
	boolean noGraphicScale = false;
	
	public void draw() {
		if (pg == null) {
			// Do this in draw, as otherwise jogl fails with "invalid memory access"
			pg = new GLGraphicsOffScreen(p, (int) width, (int) height);
		}
		
		
		// Transforms inner PGraphics, and draws internal object onto off-screen buffer.
		pg.beginDraw();
		pg.pushMatrix();
		pg.translate(innerOffset.x, innerOffset.y);
		pg.applyMatrix(innerMatrix);

		if (noGraphicScale) {
			// Be aware that markers on inner only seem to be on wrong positions!
			// They are correct, but due to non-scaling it does look that way.
			// To be used for WMS tile handling (different images instead of scaling)
			float testX = pg.screenX(100, 100);
			PApplet.println("testX:" + testX);
			pg.scale(1.0f / innerScale);
		}

		internalDraw();

		pg.popMatrix();
		pg.endDraw();

		// Transforms outer object, and draws off-screen buffer onto screen.
		p.pushMatrix();
		p.translate(offset.x, offset.y);
		p.applyMatrix(matrix);
		p.image(pg.getTexture(), 0, 0);
		p.popMatrix();
	}

	/**
	 * The internal draw method. Needs to be implemented in sub classes.
	 */
	public abstract void internalDraw();
	
	/**
	 * Checks whether given screen coordinates are hitting this object.
	 * 
	 * @param checkX
	 *            Horizontal screen position to hit test.
	 * @param checkY
	 *            Vertical screen position to hit test.
	 * @return true if the given positions are within this object.
	 */
	public boolean isHit(int checkX, int checkY) {
		float[] check = getObjectFromScreenPosition(checkX, checkY);
		return (check[0] > 0 && check[0] < 0 + width && check[1] > 0 && check[1] < 0 + height);
	}

	// Conversion methods -------------------------------------------

	public float[] getObjectFromScreenPosition(float x, float y) {
		return getTransformedPosition(x, y, true);
	}

	public float[] getScreenFromObjectPosition(float x, float y) {
		return getTransformedPosition(x, y, false);
	}

	public float[] getScreenFromInnerObjectPosition(float x, float y) {
		float objectXY[] = getObjectFromInnerObjectPosition(x, y);
		float screenXY[] = getScreenFromObjectPosition(objectXY[0], objectXY[1]);
		return screenXY;
	}

	public float[] getInnerObjectFromScreenPosition(float x, float y) {
		float objectXY[] = getObjectFromScreenPosition(x, y);
		float innerObjectXY[] = getInnerObjectFromObjectPosition(objectXY[0], objectXY[1]);
		return innerObjectXY;
	}

	public float[] getObjectFromInnerObjectPosition(float x, float y) {
		return getInnerTransformedPosition(x, y, false);
	}

	public float[] getInnerObjectFromObjectPosition(float x, float y) {
		return getInnerTransformedPosition(x, y, true);
	}

	private float[] getTransformedPosition(float x, float y, boolean inverse) {
		if (inverse) {
			x -= offset.x;
			y -= offset.y;
		}

		float[] xyz = new float[3];
		PMatrix3D m = new PMatrix3D();
		m.apply(matrix);
		if (inverse) {
			m.invert();
		}
		m.mult(new float[] { x, y, 0 }, xyz);

		if (!inverse) {
			xyz[0] += offset.x;
			xyz[1] += offset.y;
		}

		return xyz;
	}

	private float[] getInnerTransformedPosition(float x, float y, boolean inverse) {
		if (inverse) {
			x -= innerOffset.x;
			y -= innerOffset.y;
		}

		float[] xyz = new float[3];
		PMatrix3D m = new PMatrix3D();
		m.apply(innerMatrix);
		if (inverse) {
			m.invert();
		}
		m.mult(new float[] { x, y, 0 }, xyz);

		if (!inverse) {
			xyz[0] += innerOffset.x;
			xyz[1] += innerOffset.y;
		}

		return xyz;
	}

	// Matrix calculations ------------------------------------------

	protected void calculateMatrix() {
		PMatrix3D invMatrix = new PMatrix3D();
		invMatrix.apply(matrix);
		invMatrix.invert();

		float originalCenterX = invMatrix.multX(transCenter.x, transCenter.y);
		float originalCenterY = invMatrix.multY(transCenter.x, transCenter.y);

		matrix = new PMatrix3D();
		matrix.translate(transCenter.x, transCenter.y);
		matrix.scale(scale);
		matrix.rotateZ(angle.z);
		matrix.translate(-originalCenterX, -originalCenterY);
	}

	protected void calculateInnerMatrix() {
		PMatrix3D invMatrix = new PMatrix3D();
		invMatrix.apply(innerMatrix);
		invMatrix.invert();

		float originalCenterX = invMatrix.multX(innerTransCenter.x, innerTransCenter.y);
		float originalCenterY = invMatrix.multY(innerTransCenter.x, innerTransCenter.y);

		innerMatrix = new PMatrix3D();
		innerMatrix.translate(innerTransCenter.x, innerTransCenter.y);
		innerMatrix.scale(innerScale);
		innerMatrix.rotateZ(innerAngle.z);
		innerMatrix.translate(-originalCenterX, -originalCenterY);
	}

	// Public transformation methods --------------------------------

	/**
	 * Sets inner transformation center.
	 * 
	 * @param x
	 *            X in screen coordinates.
	 * @param y
	 *            Y in screen coordinates.
	 */
	public void setTransCenter(float x, float y) {
		// NB Offset subtraction due to special handling (i.e. not included in matrix)
		this.transCenter.x = x - offset.x;
		this.transCenter.y = y - offset.y;
	}

	/**
	 * Sets outer transformation center.
	 * 
	 * @param x
	 *            X in screen coordinates.
	 * @param y
	 *            Y in screen coordinates.
	 */
	public void setInnerTransCenter(float x, float y) {
		// Inner trans center is in object coordinates, conversion from screen to obj
		float[] xy = getObjectFromScreenPosition(x, y);
		// NB Offset subtraction due to special handling (i.e. not included in innerMatrix)
		this.innerTransCenter.x = xy[0] - innerOffset.x;
		this.innerTransCenter.y = xy[1] - innerOffset.y;
	}

	public void rotate(float angle) {
		this.angle.z += angle;
		calculateMatrix();
	}

	public void innerRotate(float angle) {
		this.innerAngle.z += angle;
		calculateInnerMatrix();
	}

	public void rotate(float xAngle, float yAngle, float zAngle) {
		this.angle.x += xAngle;
		this.angle.y += yAngle;
		this.angle.z += zAngle;
		calculateMatrix();
	}

	public void scale(float scale) {
		this.scale *= scale;
		calculateMatrix();
	}

	public void innerScale(float scale) {
		this.innerScale *= scale;
		calculateInnerMatrix();
	}

	public void addOffset(float dx, float dy) {
		this.offset.x += dx;
		this.offset.y += dy;
		calculateMatrix();
	}

	public void addInnerOffset(float dx, float dy) {
		this.innerOffset.x += dx;
		this.innerOffset.y += dy;
		calculateInnerMatrix();
	}

	public void setInnerOffset(float x, float y) {
		this.innerOffset.x = x;
		this.innerOffset.y = y;
		calculateInnerMatrix();
	}

	public void setOffset(float x, float y) {
		this.offset.x = x;
		this.offset.y = y;
		calculateMatrix();
	}
	
	public void setPosition(float x, float y) {
		setOffset(x, y);
	}
	
	public float getX() {
		return offset.x;
	}

	public float getY() {
		return offset.y;
	}

	/**
	 * Pans object center to given pos in object coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	public void panToObjectCenter(float x, float y) {
		float dx = getWidth() / 2 - x;
		float dy = getHeight() / 2 - y;

		addInnerOffset(dx, dy);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getInnerWidth() {
		return getWidth();
	}

	public float getInnerHeight() {
		return getHeight();
	}

	// Helper methods -------------------------------------

	protected float getAngleBetween(float x1, float y1, float x2, float y2) {
		float difY = y1 - y2;
		float difX = x1 - x2;
		float angle = PApplet.atan2(difY, difX);
		return angle;
	}

}

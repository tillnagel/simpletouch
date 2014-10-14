/**
 * SimpleTouch is a simple library to manipulate objects by touch.
 *
 * Copyright (c) 2011, Till Nagel
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * - Neither the name of the Fachhochschule Potsdam nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * @author		Till Nagel, FH Potsdam
 * @modified	##date##
 * @version		##version##
 */

package de.fhpotsdam.simpletouch;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class SimpleTouch implements TuioListener {

	protected PApplet p;

	protected TuioClient tuioClient;

	protected List<TouchObject> touchObjects = new ArrayList<TouchObject>();

	private PFont font;

	/**
	 * Creates the SimpleTouch library.
	 * 
	 * @param p
	 *            The Processing applet.
	 */
	public SimpleTouch(PApplet p) {
		this(p, null);
	}

	/**
	 * Creates the SimpleTouch library with the provided tuioClient.
	 * 
	 * @param p
	 *            The Processing applet.
	 * @param tuioClient
	 *            The TUIO client. Must be connected, already.
	 */
	public SimpleTouch(PApplet p, TuioClient tuioClient) {
		this.p = p;

		// Create or set tuioClient
		if (tuioClient == null) {
			this.tuioClient = new TuioClient();
			this.tuioClient.connect();
		} else {
			this.tuioClient = tuioClient;
		}
		// Add this as listener
		this.tuioClient.addTuioListener(this);

		// To disconnect tuioClient after applet stops
		p.registerDispose(this);
	}

	/**
	 * Gets the TuioClient, connected to this SimpleTouch.
	 * 
	 * @return The TuioClient.
	 */
	public TuioClient getTuioClient() {
		return tuioClient;
	}

	public void dispose() {
		tuioClient.disconnect();
	}

	/**
	 * Draws all TouchObjects with their current transformations.
	 */
	public void draw() {
		synchronized (touchObjects) {
			for (TouchObject transObject : touchObjects) {
				transObject.draw();
			}
		}
	}

	/**
	 * Adds a TouchObject, so it can be manipulated by finger interactions.
	 * 
	 * @param touchObject
	 */
	public void addTouchObject(TouchObject touchObject) {
		synchronized (touchObjects) {
			touchObjects.add(touchObject);
		}
	}

	public List<TouchObject> getTouchObjects() {
		return touchObjects;
	}

	public void addTuioCursor(TuioCursor tcur) {

		// Tapping is handled here (and not in TouchObject), as list re-ordering is
		// needed.

		// List is drawn from beginning (low) to end (top).
		// Topmost hit will be put topmost overall. If none was hit, list order does not change.

		TouchObject hitObject = null;
		// Hit test iterates from end to beginning, with the first hit returned.
		for (int i = touchObjects.size() - 1; i >= 0; i--) {
			TouchObject ttObj = touchObjects.get(i);
			if (ttObj.isHit(tcur.getScreenX(p.width), tcur.getScreenY(p.height))) {
				ttObj.addTuioCursor(tcur);
				hitObject = ttObj;
				break;
			}
		}

		// Put hit object to end of list, so it will be the topmost.
		synchronized (touchObjects) {
			if (hitObject != null) {
				touchObjects.remove(hitObject);
				touchObjects.add(hitObject);
			}
		}

		synchronized (touchObjects) {
			for (int i = touchObjects.size() - 1; i >= 0; i--) {
				TouchObject ttObj = touchObjects.get(i);
				if (ttObj.isAlwaysOnTop()) {
					touchObjects.remove(ttObj);
					touchObjects.add(ttObj);
				}
			}
		}
	}

	public void removeTuioCursor(TuioCursor tcur) {
		// Pass trough remove-event to all objects, to allow fingerUp also out of boundaries,
		// as objects decide themselves (via cursor-id) whether cursor belongs to it.
		for (TouchObject ttObj : touchObjects) {
			ttObj.removeTuioCursor(tcur);
		}
	}

	public void updateTuioCursor(TuioCursor tcur) {
		// Updates go to all hit ones, independent of z-index
		for (TouchObject ttObj : touchObjects) {
			if (ttObj.isHit(tcur.getScreenX(p.width), tcur.getScreenY(p.height))) {
				ttObj.updateTuioCursor(tcur);
			}
		}
	}

	/**
	 * Draws all TuioCursors for debug purposes.
	 */
	public void drawCursors() {
		for (TuioCursor tuioCursor : tuioClient.getTuioCursors()) {
			drawCursor(tuioCursor);
		}
	}

	/**
	 * Draws a TuioCursor as small circle with ID as label. For debug purposes.
	 * 
	 * @param tc
	 *            The cursor to draw.
	 */
	public void drawCursor(TuioCursor tc) {
		if (tc == null)
			return;

		if (font == null) {
			// Load font first time a cursor was drawn
			font = p.loadFont("ml.vlw");
		}

		p.stroke(50, 100);
		p.fill(230, 150);
		p.ellipse(tc.getScreenX(p.width), tc.getScreenY(p.height), 15, 15);
		p.fill(10);
		p.textFont(font);
		p.textSize(12);
		p.text(tc.getCursorID(), tc.getScreenX(p.width) - 3, tc.getScreenY(p.height) + 4);
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
	}

	@Override
	public void refresh(TuioTime arg0) {
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
	}
}

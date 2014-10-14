simpletouch
===========

A library for Processing to create simple multitouch sketches in Processing.

Supports tapping, dragging, and pinching to select, move, rotate and scale visual items.
This library handles TUIO cursors to assist manipulating graphical objects using simple multitouch gestures.
It features direct transformations, i.e. each finger position stays at its respective object position while transforming.

A visual item is drawn in its own canvas to enable independent inner and outer transformations.
Methods are provided for converting between the local coordinate system and the Processing canvas.
This library is intended to work as OpenGL sketches, and uses GLGraphics for offscreen buffer renderings.

(At the moment, works only for Processing 1.5)

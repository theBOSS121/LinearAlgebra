package com.lukauranic.linearalgebra.utilities;

public class Line2d {

	public double angle = 0;
	public Point2d p;
	
	public Line2d(Point2d p, double angle) {
		this.p = p;
		this.angle = angle;
	}
	
	public Line2d(Point2d p, Point2d p2) {
		this.p = p;
		angle = Math.atan2(p2.y - p.y, p2.x - p.x);
	}
	
	
}

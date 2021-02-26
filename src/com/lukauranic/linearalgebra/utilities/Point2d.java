package com.lukauranic.linearalgebra.utilities;

import com.lukauranic.linearalgebra.LinearAlgebra;

public class Point2d {

	public static final Point2d ORIGIN = new Point2d(LinearAlgebra.WIDTH / 2, LinearAlgebra.HEIGHT / 2);
	
	public double x, y;
	
	public Point2d(double x, double y) {
		setPosition(x, y);
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}

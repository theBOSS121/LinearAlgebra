package com.lukauranic.linearalgebra;

import java.awt.Graphics2D;
import java.util.Random;

import com.lukauranic.linearalgebra.graphics.Renderer;
import com.lukauranic.linearalgebra.utilities.Line2d;
import com.lukauranic.linearalgebra.utilities.Point2d;

public class Game {
	
	Random rand = new Random();
	
	public Game() {
		init();
	}
	
	Point2d p;
	Line2d line;
	double angle = 0;
	int counter = 0;
	
	public void init() {
		p = new Point2d(10, 10);
		line = new Line2d(Point2d.ORIGIN, angle);
	}
	
	public void update() {
		if(counter % 30 == 0) {
			p.setPosition(rand.nextInt(Renderer.width), rand.nextInt(Renderer.width));

			angle += 0.1;
			line.angle = angle;
		}
		
		counter++;
	}
	
	public void render() {
		Renderer.renderBackground();
		
		Renderer.renderLine(p, Point2d.ORIGIN, 0xffffffff);
		Renderer.renderPoint(Point2d.ORIGIN, 5, 0xffffff00);
		Renderer.renderLine(line, 0xffff0000);
		
		for(int i = 0; i < Renderer.pixels.length; i++) {
			LinearAlgebra.pixels[i] = Renderer.pixels[i];
		}
	}
	
	public void postRender(Graphics2D g) {

//		g.setColor(Color.WHITE);
//		g.setStroke(new BasicStroke());
//		for(int i = 0; i < cube.connections.length; i++) {
//			Point3d p = cube.points[cube.connections[i][0]];
//			Point3d p2 = cube.points[cube.connections[i][1]];
//			
//			if(p.z <= 0 || p2.z <= 0) continue;
//			Point3d pp = perspective(p);
//			Point3d pp2 = perspective(p2);
//			g.drawLine((int) pp.x, (int) pp.y, (int) pp2.x, (int) pp2.y);
//		}	
	}
}

package com.lukauranic.linearalgebra.graphics;

import com.lukauranic.linearalgebra.LinearAlgebra;
import com.lukauranic.linearalgebra.utilities.Line2d;
import com.lukauranic.linearalgebra.utilities.Point2d;

public class Renderer {
	public static int width = LinearAlgebra.WIDTH, height = LinearAlgebra.HEIGHT;
	public static int[] pixels = new int[width * height];
	
	public static void renderBackground() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = 0xff000000;
			}
			
		}
	}
	
	public static void renderSprite(Sprite s, int xp, int yp) {
		if(xp < -s.width || yp < -s.height || xp >= width || yp >= height) return;
		
		for(int y = 0; y < s.height; y++) {
			int yy = y + yp;
			if(yy >= height || yy < 0) continue;
			for(int x = 0; x < s.width; x++) {
				int xx = x + xp;
				if(xx < 0 || x >= width) continue;
				int col = combineColors(s.pixels[x + y * s.width], xx, yy);
				pixels[xx + yy * width] = col;
			}
		}
	}

	private static int combineColors(int col, int x, int y) {
		int pCol = pixels[x + y * width];
		int a = (col >> 24) & 0xff;
		if(a == 0xff) return col;
		if(a <= 0) return pCol;
		
		int pr = (pCol >> 16) & 0xff;
		int pg = (pCol >> 8) & 0xff;
		int pb = (pCol) & 0xff;
		int r = (col >> 16) & 0xff;
		int g = (col >> 8) & 0xff;
		int b = (col) & 0xff;
		
		int nr = (int) (pr - ((pr - r) * (a / 255f)));
		int ng = (int) (pg - ((pg - g) * (a / 255f)));
		int nb = (int) (pb - ((pb - b) * (a / 255f)));
		
		return (nr << 16) | (ng << 8) | nb;
	}
	

	public static void renderPoint(Point2d p, int r, int col) {
		int xx = (int) p.x - r;
		int yy = (int) p.y - r;
		for(int y = yy; y < yy + 2 * r; y++) {
			for(int x = xx; x < xx + 2 * r; x++) {
				int xDiff = (int) (x - p.x);
				int yDiff = (int) (y - p.y);
				if(Math.sqrt(xDiff * xDiff + yDiff * yDiff) < r) {
					pixels[x + y * width] = col;					
				}
			}
		}
	}
	
	public static void renderLine(Line2d line, int col) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				double xDiff = line.p.x - x;
				double yDiff = line.p.y - y;
				double angle = Math.atan2(yDiff, xDiff);
//				if(angle - line.angle < 0.2 && angle - line.angle > -0.2) pixels[x + y * width] = col;
			}
		}
	}
	
	public static void renderLine(Point2d p, Point2d p2, int col) {
		int x1 = (int) p.x;
		int x2 = (int) p2.x;
		int y1 = (int) p.y;
		int y2 = (int) p2.y;
		int xDiff = Math.abs(x2 - x1);
		int yDiff = Math.abs(y2 - y1);		
		if(xDiff >= yDiff) {
			if(x1 > x2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			for(int x = x1; x <= x2; x++) {
				if(x < 0 || (k * x + n) < 0) continue;
				if(x >= width || (k * x + n) >= height) continue;
				pixels[x + (int) (k * x + n) * width] = col;
			}
		}else {
			if(y1 > y2) {
				int x3 = x1;
				int y3 = y1;
				x1 = x2;
				x2 = x3;
				y1 = y2;
				y2 = y3;
			}
			double k = (y2 - y1) / (double) (x2 - x1);
			double n = y1 - k * x1;
			if(x2 - x1 == 0) {
				for(int y = y1; y < y2; y++) {
					if(y < 0) continue;
					if(x1 < 0 || x1 >= width || y >= height) continue;
					pixels[x1 + y * width] = col;					
				}
			}else {
				for(int y = y1; y <= y2; y++) {
					if(y < 0 || (y - n) / k < 0) continue;
					if(y >= height || (y - n) / k >= height) continue;
					pixels[(int) ((y - n) / k) + y * width] = col;
				}				
			}
		}
	}
	
//	
//	public static void renderSprite(Sprite s, int sx, int sy, int sz, double rx, double ry, double rz) {
//		Point3d p;
//		for(int y = 0; y < s.height; y++) {
//			int screenY = y + sy;
//			for(int x = 0; x < s.width; x++) {				
//				int screenX = x + sx;
//				Point3d point = new Point3d(x - s.width / 2, y - s.height / 2, 0);
//				p = Renderer.rotateX(point, rx);
//				point.x = p.x;
//				point.y = p.y;
//				point.z = p.z;
//				p = Renderer.rotateY(point, ry);
//				point.x = p.x;
//				point.y = p.y;
//				point.z = p.z;
//				p = Renderer.rotateZ(point, rz);
//				point.x = p.x;
//				point.y = p.y;
//				point.z = p.z;
//				
//				
//				screenX = (int) point.x + sx;
//				screenY = (int) point.y + sy;
//				
//				int index = (int) (screenX + screenY * width);
//				if(index < 0 || index >= pixels.length) continue;
//				
//				pixels[index] = s.pixels[x + y * s.width];
//			}
//		}		
//	}
//
//	
////	some bugs
//	public static void renderLine(int x1, int y1, int x2, int y2, int col) {
//		int xDiff = Math.abs(x2 - x1);
//		int yDiff = Math.abs(y2 - y1);		
//		if(xDiff >= yDiff) {
//			if(x1 > x2) {
//				int x3 = x1;
//				int y3 = y1;
//				x1 = x2;
//				x2 = x3;
//				y1 = y2;
//				y2 = y3;
//			}
//			double k = (y2 - y1) / (double) (x2 - x1);
//			double n = y1 - k * x1;
//			for(int x = x1; x <= x2; x++) {
//				if(x < 0 || (k * x + n) < 0) continue;
//				if(x >= width || (k * x + n) >= height) continue;
//				pixels[x + (int) (k * x + n) * width] = col;
//			}
//		}else {
//			if(y1 > y2) {
//				int x3 = x1;
//				int y3 = y1;
//				x1 = x2;
//				x2 = x3;
//				y1 = y2;
//				y2 = y3;
//			}
//			double k = (y2 - y1) / (double) (x2 - x1);
//			double n = y1 - k * x1;
//			if(x2 - x1 == 0) {
//				for(int y = y1; y < y2; y++) {
//					if(y < 0) continue;
//					if(x1 < 0 || x1 >= width || y >= height) continue;
//					pixels[x1 + y * width] = col;					
//				}
//			}else {
//				for(int y = y1; y <= y2; y++) {
//					if(y < 0 || (y - n) / k < 0) continue;
//					if(y >= height || (y - n) / k >= height) continue;
//					pixels[(int) ((y - n) / k) + y * width] = col;
//				}				
//			}
//		}
//	}
//	
//	
//
//	static double kt, kl, kr, kb, nt, nl, nb, nr;
//	static int lt = -1, lb = -1, rt = -1, rb = -1;
//    static int top, left, right, bottom, cx, cy;
//	
//    public static void renderRectangle(List<Point3d> points, int col) {
//        points.sort(Comparator.comparingDouble(Point3d::getY));
//        points.sort(Comparator.comparingDouble(Point3d::getX));
//
//        if(points.get(0).y <= points.get(1).y) lt = 0;
//        else lt = 1;
//        if(lt == 0) lb = 1;
//        else lb = 0;
//        if(points.get(2).y <= points.get(3).y) rt = 2;
//        else rt = 3;
//        if(rt == 2) rb = 3;
//        else rb = 2;
//   
////            y = kx + n;
////            k = (y2 - y1) / (x2 - x1)
//        setTopAndBottom(points);     
//        calculateLinearEquations(points);   
//        
//
////      check if top and bottom lines intersects and change two points if they do
//		int aaa;
//		double xI = -(nt - nb) / (kt - kb);
//		double yI = kt * xI + nt;	
//		if(xI > left && xI < right && yI > top && yI < bottom) {
//			 aaa = lb;
//			 lb = lt;
//			 lt = aaa;
//			 aaa = rt;
//			 rt = lb;
//			 lb = aaa;
//			 calculateLinearEquations(points);
//		}
//        
//
//        boolean centerIsInside = false;
//        
//        cx = (right - left) / 2 + left;
//        cy = (bottom - top) / 2 + top;
//        
//        centerIsInside = checkIfCenterIsInside(points);
//        if(!centerIsInside) {
//        	aaa = rt;
//        	rt = lb;
//        	lb = aaa;
//        	aaa = lt;
//        	lt = rb;
//        	rb = aaa;
//        	calculateLinearEquations(points);
//        	centerIsInside = checkIfCenterIsInside(points);
//        	if(!centerIsInside) {
//            	aaa = rt;
//            	rt = lb;
//            	lb = aaa;
//            	aaa = lt;
//            	lt = rb;
//            	rb = aaa;
//        	}
//        }
//        if(!centerIsInside) {
//        	aaa = rt;
//        	rt = rb;
//        	rb = aaa;
//        	aaa = lt;
//        	lt = lb;
//        	lb = aaa;
//        	calculateLinearEquations(points);  
//        	centerIsInside = checkIfCenterIsInside(points); 
//        	if(!centerIsInside) {
//            	aaa = rt;
//            	rt = rb;
//            	rb = aaa;
//            	aaa = lt;
//            	lt = lb;
//            	lb = aaa;
//        	}
//        }
//        if(!centerIsInside) {
//        	aaa = lt;
//        	lt = rt;
//        	rt = aaa;
//        	aaa = lb;
//        	lb = rb;
//        	rb = aaa;
//            calculateLinearEquations(points);
//        	centerIsInside = checkIfCenterIsInside(points);
//        	if(!centerIsInside) {
//            	aaa = lt;
//            	lt = rt;
//            	rt = aaa;
//            	aaa = lb;
//            	lb = rb;
//            	rb = aaa;
//        	}
//        }
//        
//        for(int y = top; y <= bottom; y++) {
//        	for(int x = left; x <= right; x++) {
//        		if(kt * x + nt <= y) {
//        			if(kb * x + nb >= y) {
//    					if((y - nl) / kl <= x || (Double.isInfinite((y - nl) / kl) && x > points.get(lt).x)) {
//    						if((y - nr) / kr >= x || (Double.isInfinite((y - nr) / kr) && x > points.get(rt).x)) {
//    							if(x >= width || x < 0) continue;
//    							if(y >= height || y < 0) continue;
//    							if(x + y * width < 0 || x + y * width >= pixels.length) continue;
//    							pixels[x + y * width] = col;     
//    							if(x == cx && y == cy) pixels[x + y * width] = 0xffffffff;
//    						}            			            				            					
//    					}            				
//        			}                                                                           
//        		}
//        	}
//        }
//        
//        Renderer.renderLine((int) points.get(lt).x, (int) points.get(lt).y, (int) points.get(rt).x, (int) points.get(rt).y, 0xff00ff00);
//        Renderer.renderLine((int) points.get(rt).x, (int) points.get(rt).y, (int) points.get(rb).x, (int) points.get(rb).y, 0xff00ff00);
//        Renderer.renderLine((int) points.get(lt).x, (int) points.get(lt).y, (int) points.get(lb).x, (int) points.get(lb).y, 0xff00ff00);
//        Renderer.renderLine((int) points.get(lb).x, (int) points.get(lb).y, (int) points.get(rb).x, (int) points.get(rb).y, 0xff00ff00);
//    }
//	
//    private static boolean checkIfCenterIsInside(List<Point3d> points) {
//    	if(kt * cx + nt <= cy) {
//			if(kb * cx + nb >= cy) {
//				if((cy - nl) / kl <= cx || (Double.isInfinite((cy - nl) / kl) && cx > points.get(lt).x)) {
//					if((cy - nr) / kr >= cx || (Double.isInfinite((cy - nr) / kr) && cx > points.get(rt).x)) {
////						if(cx + cy * width < 0 || cx + cy * width >= pixels.length) return false;
//						return true;
//					}            			            				            					
//				}            				
//			}
//    	}
//		return false;
//	}
//
//	private static void setTopAndBottom(List<Point3d> points) {
//        if(points.get(lt).y <= points.get(rt).y) {
//        	top = (int) points.get(lt).y;
//        }else {
//        	top = (int) points.get(rt).y;                                          
//        }
//        if(points.get(lb).y >= points.get(rb).y) {
//        	bottom = (int) points.get(lb).y;
//        }else {
//        	bottom = (int) points.get(rb).y;                                 
//        }
//        if(points.get(lt).x <= points.get(lb).x) {
//        	left = (int) points.get(lt).x;
//        }else {
//        	left = (int) points.get(lb).x;                                          
//        }
//        if(points.get(rt).x >= points.get(rb).x) {
//        	right = (int) points.get(rt).x;
//        }else {
//        	right = (int) points.get(rb).x;                                       
//        }		
//	}
//
//	private static void calculateLinearEquations(List<Point3d> points) {
//	    if(points.get(lt).x != points.get(rt).x) {
//	    	kt = (points.get(rt).y - points.get(lt).y)  / (points.get(rt).x - points.get(lt).x);
//	    }else {
//	        kt = Double.MAX_VALUE;
//	    }
//	    nt = points.get(lt).y - kt * points.get(lt).x;
//	    if(points.get(lb).x != points.get(rb).x) {
//	        kb = (points.get(rb).y - points.get(lb).y)  / (points.get(rb).x - points.get(lb).x);
//	    }else {
//	        kb = Double.MAX_VALUE;
//	    }
//	    nb = points.get(lb).y - kb * points.get(lb).x;
//	    if(points.get(lt).x != points.get(lb).x) {
//	        kl = (points.get(lb).y - points.get(lt).y)  / (points.get(lb).x - points.get(lt).x);
//	    }else {
//	        kl = Double.MAX_VALUE;
//	    }
//	    nl = points.get(lt).y - kl * points.get(lt).x;
//	    if(points.get(rt).x != points.get(rb).x) {
//	        kr = (points.get(rb).y - points.get(rt).y)  / (points.get(rb).x - points.get(rt).x);
//	    }else {
//	        kr = Double.MAX_VALUE;
//	    }
//	    nr = points.get(rt).y - kr * points.get(rt).x;		
//	}
//
////	two points in space also does perspective
//	public static void renderLine(Point3d p, Point3d p2, int col) {
//		Point3d pp = perspective(p);
//		Point3d pp2 = perspective(p2);
//		int x1 = (int) pp.x;
//		int x2 = (int) pp2.x;
//		int y1 = (int) pp.y;
//		int y2 = (int) pp2.y;
//		int xDiff = Math.abs(x2 - x1);
//		int yDiff = Math.abs(y2 - y1);		
//		if(xDiff >= yDiff) {
//			if(x1 > x2) {
//				int x3 = x1;
//				int y3 = y1;
//				x1 = x2;
//				x2 = x3;
//				y1 = y2;
//				y2 = y3;
//			}
//			double k = (y2 - y1) / (double) (x2 - x1);
//			double n = y1 - k * x1;
//			for(int x = x1; x <= x2; x++) {
//				if(x < 0 || (k * x + n) < 0) continue;
//				if(x >= width || (k * x + n) >= height) continue;
//				pixels[x + (int) (k * x + n) * width] = col;
//			}
//		}else {
//			if(y1 > y2) {
//				int x3 = x1;
//				int y3 = y1;
//				x1 = x2;
//				x2 = x3;
//				y1 = y2;
//				y2 = y3;
//			}
//			double k = (y2 - y1) / (double) (x2 - x1);
//			double n = y1 - k * x1;
//			if(x2 - x1 == 0) {
//				for(int y = y1; y < y2; y++) {
//					if(y < 0) continue;
//					if(x1 < 0 || x1 >= width || y >= height) continue;
//					pixels[x1 + y * width] = col;					
//				}
//			}else {
//				for(int y = y1; y <= y2; y++) {
//					if(y < 0 || (y - n) / k < 0) continue;
//					if(y >= height || (y - n) / k >= height) continue;
//					pixels[(int) ((y - n) / k) + y * width] = col;
//				}				
//			}
//		}
//	}
//	
//	public static Point3d perspective(Point3d pp) {
//		Point3d p = new Point3d(pp.x - ThreeD.WIDTH / 2, pp.y - ThreeD.HEIGHT / 2, pp.z);
//		if(p.z == 0) p.z = 1;
//		double x = p.x * 512 / p.z;
//		double y = p.y * 512 / p.z;
//		double z = 0;
//		return new Point3d(x + ThreeD.WIDTH / 2, y + ThreeD.HEIGHT / 2, z);
//	}
//	
//	public static Point3d rotateZ(Point3d pp, double angle) {
//		Point3d p = new Point3d(pp.x, pp.y, pp.z);
//		double x = p.x * Math.cos(angle) - p.y * Math.sin(angle);
//		double y = p.x * Math.sin(angle) + p.y * Math.cos(angle);
//		double z = p.z;
//		return new Point3d(x, y, z);
//	}
//	
//	public static Point3d rotateX(Point3d pp, double angle) {
//		Point3d p = new Point3d(pp.x, pp.y, pp.z);
//		double x = p.x;
//		double y = p.z * Math.sin(angle) + p.y * Math.cos(angle);
//		double z = p.z * Math.cos(angle) - p.y * Math.sin(angle);
//		return new Point3d(x, y, z);
//	}
//	
//	public static Point3d rotateY(Point3d pp, double angle) {
//		Point3d p = new Point3d(pp.x, pp.y, pp.z);
//		double x = p.z * Math.sin(angle) + p.x * Math.cos(angle);
//		double y = p.y;
//		double z = p.z * Math.cos(angle) - p.x * Math.sin(angle);
//		return new Point3d(x, y, z);
//	}
}

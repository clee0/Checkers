package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;
import main.*;

public class Piece {
	private Point location;
	private Color color;
	private boolean king;
	private int index;
	
	public Piece(int row, int column, Color color) {
		this.location = new Point(row, column);
		this.color = color;
	}
	
	public Piece(){
		location = null;
		color = null;
		king = false;
		index = 0;
	}
	
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point p){
		this.location = p;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isKing() {
		return king;
	}
	public void setKing(boolean king) {
		this.king = king;
	}

	public int getIndex(){
		return this.index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public void draw(Graphics g, Board board, int width, int height) {
		 g.setColor(this.getColor());
         int centerX = (int) this.getLocation().getX() * width;
         int centerY = (int) this.getLocation().getY() * height;
         //System.out.println(centerX + "Center x " + centerY + " CenterY");
         g.fillOval(centerX, centerY, width, height);
         g.setColor(Color.black);
         g.drawOval(centerX, centerY, width, height);
         g.drawOval(centerX, centerY, width-5, height-5);
         if(this.isKing()){
        	 g.drawString("K", centerX+20, centerY+25);
         }
         g.setColor(Color.orange);
         g.drawString(String.valueOf(this.index), centerX+20, centerY+25);
	}
	
	public void drawHighlight(Graphics g, Board board, int width, int height){
		 g.setColor(Color.cyan);
         int centerX = (int) this.getLocation().getX() * width;
         int centerY = (int) this.getLocation().getY() * height;
         //System.out.println(centerX + "Center x " + centerY + " CenterY");
         g.fillOval(centerX, centerY, width, height);
         g.setColor(Color.black);
         g.drawOval(centerX, centerY, width, height);
         g.drawOval(centerX, centerY, width-5, height-5);
	}

}

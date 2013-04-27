package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Tile {
	private int tileRow,tileCol;
	public Color color;
	private int index;
	private boolean hasPiece;
	private boolean endTile;
	private Piece piece;
	private boolean isJumpTile;
	private ArrayList<Piece> jumpingPiece;
	
	public Tile() {
		
	}
	
	public Tile(int row, int column) {
		tileRow = row;
		tileCol = column;

		isJumpTile = false;
		jumpingPiece = new ArrayList<Piece>();
		hasPiece = false;
	}

	public void draw(Graphics g, Board board, int width, int height){
		int leftCoord = this.getTileColumn()*width;
		int topCoord = this.getTileRow()*height;
		g.setColor((this.getColor()));
		g.fillRect(leftCoord, topCoord, width, height);
		g.setColor(Color.black);
		g.drawRect(leftCoord, topCoord, width, height);

		//shows index of cell 
		g.setColor(Color.green);
		//g.drawString(String.valueOf(this.getIndex()), leftCoord +20, topCoord +25);

		//show if King row
		g.drawString(String.valueOf(this.HasPiece()), leftCoord +15, topCoord + 15);

	}

	Color getColor() {
		return this.color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public int getTileColumn() {
		return this.tileCol;
	}

	public void setTileColumn(int column) {
		this.tileCol = column;
	}

	public int getTileRow() {
		return this.tileRow;
	}

	public void setTileRow(int row) {
		this.tileRow = row;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isEndTile() {
		return endTile;
	}

	public void setEndTile(boolean endTile) {
		this.endTile = endTile;
	}

	@Override
	public boolean equals(Object o) {
		Tile t = (Tile) o;
		if ( t.tileCol == tileCol && t.tileRow == tileRow )
			return true;
		return false;
	}

	public void setHasPiece(boolean b) {
		this.hasPiece = b;
	}
	public boolean HasPiece(){
		return this.hasPiece;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean isJumpTile() {
		return isJumpTile;
	}

	public void setJumpTile(boolean isJumpTile) {
		this.isJumpTile = isJumpTile;
	}

	public ArrayList<Piece> getJumpingPiece() {
		return jumpingPiece;
	}

	public void setJumpingPiece(ArrayList<Piece> jumpingPiece) {
		this.jumpingPiece = jumpingPiece;
	}
	
	public void addJumpingPiece(Piece piece) {
		this.jumpingPiece.add(piece);
	}

	public boolean isHasPiece() {
		return hasPiece;
	}

	@Override
	public String toString() {
		return (tileRow + " " + tileCol + " " + index + " " + hasPiece);
	}

}

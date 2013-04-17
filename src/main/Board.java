package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.*;

public class Board extends JPanel {
	private ArrayList<Tile> tiles;
	private ArrayList<Piece> pieces;
	private static final int NUMROWS = 8;
	private static final int NUMCOLUMNS = 8;
	private ArrayList<Tile> targets;
	private Piece selectedPiece;
	private JPanel board;

	public Board() {
		board = new JPanel();
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();
		targets = new ArrayList<Tile>();
		setSize(new Dimension(800, 800));
		Graphics g = null;
		loadBoard();
		addMouseListener(new BoardListener());
		board.paintAll(g);
		//calcTargets(pieces.get(5), true);
		//board.paintAll(g);
	}

	public void loadBoard() {

		/************************************************************************
		 * TileCreation - creates the array list of Tiles to use for the board *
		 ************************************************************************/
		Tile tempTile;
		int curIndex = 1;
		int curTile = 0;
		for (int i = 0; i < NUMROWS; i++) {
			for (int j = 0; j < NUMCOLUMNS; j++) {
				tempTile = new Tile();
				// set tile info
				tempTile.setTileRow(i);
				tempTile.setTileColumn(j);
				// Color allocation: mod switch to change each row
				// Black is the only usable color so it only resize
				if (i % 2 == 0) {
					if (curTile % 2 == 0) {
						tempTile.setColor(Color.white);
					} else {
						tempTile.setColor(Color.black);
						tempTile.setIndex(curIndex);
						curIndex++;
					}
				} else {
					if (curTile % 2 == 0) {
						tempTile.setColor(Color.black);
						tempTile.setIndex(curIndex);
						curIndex++;
					} else {
						tempTile.setColor(Color.white);
					}
				}
				if ((i == 0 || i == (NUMROWS - 1))
						&& (tempTile.color == Color.black)) {
					tempTile.setEndTile(true);
				} else {
					tempTile.setEndTile(false);
				}
				tiles.add(tempTile);
				curTile++;
			}
		}

		/***********************************************************************
		 * PiecesCreation - creates the array list of
		 * Pieces to use for the board *
		 ************************************************************************/
		Piece tempPiece;
		int index = 1;
		for (int i = 1; i < (NUMCOLUMNS * NUMROWS); i++) {
			if ((tiles.get(i).getIndex() != 0) && ((tiles.get(i).getTileRow() != 3) && (tiles.get(i).getTileRow() != 4))) {
				tempPiece = new Piece();
				tempPiece.setIndex(index);
				index++;
				tempPiece.setLocation(new Point((int) ((tiles.get(i).getTileColumn())), (int) (tiles.get(i).getTileRow())));
				tempPiece.setKing(false);
				if (tempPiece.getLocation().getY() < 3) {
					tempPiece.setColor(Color.red);
				} else {
					tempPiece.setColor(Color.white);
				}
				pieces.add(tempPiece);
			}
		}
	}

	Tile tempTarget;

	public void calcTargets(Piece piece, boolean king){
		targets = new ArrayList<Tile>();
		tempTarget = new Tile();
		if(king == false){
			if(piece.getColor() == Color.white){
				if(piece.getLocation().getX() == 0){
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
					targets.add(tempTarget);
				}else if(piece.getLocation().getX() == 7){
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
					targets.add(tempTarget);
				}else{
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
					targets.add(tempTarget);
					tempTarget = new Tile();
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
					targets.add(tempTarget);
				}
			}else{
				if(piece.getLocation().getX() == 0){
					System.out.println((int)(piece.getLocation().getX()) + " this is the X " + (int)(piece.getLocation().getY()) + " this is the Y");
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
					targets.add(tempTarget);
				}else if(piece.getLocation().getX() == 7){
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
					targets.add(tempTarget);
				}else{
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
					targets.add(tempTarget);
					tempTarget = new Tile();
					tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
					tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
					targets.add(tempTarget);
				}
			}
		}else{
			if(piece.getLocation().getX() == 0){
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
				targets.add(tempTarget);
				tempTarget = new Tile();
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
				targets.add(tempTarget);
			}else if(piece.getLocation().getX() == 7){
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
				targets.add(tempTarget);
				tempTarget = new Tile();
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
				targets.add(tempTarget);
			}else{
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
				targets.add(tempTarget);
				tempTarget = new Tile();
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())-1));
				targets.add(tempTarget);
				tempTarget = new Tile();
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())+1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
				targets.add(tempTarget);
				tempTarget = new Tile();
				tempTarget.setTileColumn(((int)(piece.getLocation().getX())-1));
				tempTarget.setTileRow(((int)(piece.getLocation().getY())+1));
				targets.add(tempTarget);
			}
		}
	}

	public void checkLocation(Point location) {
		int width = this.getWidth()/NUMCOLUMNS;
		int height = this.getHeight()/NUMROWS;
		int row = (int) (location.getY()/height);
		int column = (int) (location.getX()/width);

		boolean validTarget = false;
		for(Tile c : this.getTargets()) {
			if(row == c.getTileRow() && column == c.getTileColumn()) {
				validTarget = true;
			}
		}
		if(validTarget) {
			Point target;
			target = new Point(column, row);
			//selectedPiece.setLocation(target);
			repaint();
		}
		else{
			//JOptionPane.showMessageDialog(null,"That is not a valid target.", "That is not a valid target.", JOptionPane.ERROR_MESSAGE);
		}
	}
	//mouse listener
	private class BoardListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point location = e.getPoint();
			repaint();
			//calcTargets(selectedPiece);
			//checkLocation(location);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {		
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth() / NUMCOLUMNS;
		int height = this.getHeight() / NUMROWS;
		for (Tile tile : this.getTiles()) {
			tile.draw(g, this, width, height);
		}
		for (Piece pieces : this.getPieces()) {
			pieces.draw(g, this, width, height);
			//if(pieces == selectedPiece){
			//pieces.drawHighlight(g, this, width, height);
			//}
		}
		for (Tile c : this.getTargets()) {
			int leftCoord = c.getTileColumn() * width;
			int topCoord = c.getTileRow() * height;
			g.setColor(Color.cyan);
			g.fillRect(leftCoord, topCoord, width, height);
			g.setColor(Color.black);
			g.drawRect(leftCoord, topCoord, width, height);
		}
	}

	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}

	public ArrayList<Piece> getPieces() {
		return this.pieces;
	}

	public ArrayList<Tile> getTargets(){
		return targets;
	}
}

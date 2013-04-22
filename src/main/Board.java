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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
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
	private ArrayList<Tile> jumpTargets;
	private Piece selectedPiece;
	private JPanel board;
	private Boolean[] visited;

	public Board() {
		board = new JPanel();
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();	
		targets = new ArrayList<Tile>();
		jumpTargets = new ArrayList<Tile>();
		selectedPiece = null;
		setSize(new Dimension(800, 800));
		Graphics g = null;
		loadBoard();
		addMouseListener(new BoardListener());
		board.paintAll(g);
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

		this.visited = new Boolean[tiles.size()];
		for(int i = 0; i < visited.length; i++)
			visited[i] = false;

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
				tiles.get(i).setHasPiece(true);
				if (tempPiece.getLocation().getY() < 3) {
					tempPiece.setColor(Color.red);
				} else {
					tempPiece.setColor(Color.BLACK);
				}
				pieces.add(tempPiece);
				tiles.get(i).setPiece(tempPiece);
			}
		}
	}

	Tile tempTarget;

	public void startTargets(Piece piece) {
		this.targets = calcTargets(piece.getLocation(), piece.getColor(), piece.isKing());
	}

	public ArrayList<Tile> calcTargets(Point startLoc, Color color, boolean king){
		ArrayList<Tile> targets = new ArrayList<Tile>();

		for(Tile t:tiles) {
			for(Piece p:pieces) {
				Point tileLoc = new Point(t.getTileColumn(),t.getTileRow());
				if(tileLoc.equals(p.getLocation())) {
					t.setHasPiece(true);
					t.setPiece(p);
					break;
				}
				else {
					t.setHasPiece(false);
					t.setPiece(null);
				}
			}
		}

		if(king == false){
			if(color == Color.BLACK){
				if(startLoc.x == 0){
					targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
				}else if(startLoc.getX() == 7){
					targets.add(getTileAt(startLoc.y-1,startLoc.x-1));
				}else{
					targets.add(getTileAt(startLoc.y-1,startLoc.x-1));
					targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
				}
			}else{
				if(startLoc.getX() == 0){
					targets.add(getTileAt(startLoc.y+1,startLoc.x+1));
				}else if(startLoc.getX() == 7){
					targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
				}else{
					targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
					targets.add(getTileAt(startLoc.y+1,startLoc.x+1));
				}
			}
		}else{
			if(startLoc.getX() == 0 && startLoc.getY() != 7){
				targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
				targets.add(getTileAt(startLoc.y+1,startLoc.x+1));
			}else if(startLoc.getX() == 0 && startLoc.getY() == 7){
				targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
			}else if(startLoc.getX() == 7 && startLoc.getY() != 0){
				targets.add(getTileAt(startLoc.y-1,startLoc.x-1));
				targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
			}else if(startLoc.getX() == 7 && startLoc.getY() == 0){
				targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
			}else if(startLoc.getY() == 7){
				targets.add(getTileAt(startLoc.y-1,startLoc.x-1));
				targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
			}else if(startLoc.getY() == 0){
				targets.add(getTileAt(startLoc.y+1,startLoc.x+1));
				targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
			}else{
				targets.add(getTileAt(startLoc.y-1,startLoc.x-1));
				targets.add(getTileAt(startLoc.y-1,startLoc.x+1));
				targets.add(getTileAt(startLoc.y+1,startLoc.x+1));
				targets.add(getTileAt(startLoc.y+1,startLoc.x-1));
			}
		}
		for(Tile target: getTargets()){
			for(Tile tiles: getTiles()){
				if(target.getTileColumn() == tiles.getTileColumn() && target.getTileRow() == tiles.getTileRow()){
					target.setHasPiece(tiles.HasPiece());
					if(tiles.HasPiece())
						target.setPiece(tiles.getPiece());
				}
			}
		}

		// Jump detection
		ArrayList<Tile> tempTargets = new ArrayList<Tile>(targets);
		this.jumpTargets.clear();
		for(Tile t: tempTargets) {
			if(t.HasPiece()) {
				Piece tempPiece = t.getPiece();
				targets.remove(t);
				if(!color.equals(tempPiece.getColor())) {
					int colDiff = tempPiece.getLocation().x - startLoc.x;
					int rowDiff = tempPiece.getLocation().y - startLoc.y;
					Tile tempTile = getTileAt(tempPiece.getLocation().y + rowDiff, tempPiece.getLocation().x + colDiff);
					if(tempTile != null && !tempTile.HasPiece()) {
						targets.add(getTileAt(tempPiece.getLocation().y + rowDiff, tempPiece.getLocation().x + colDiff));
						getTileAt(tempPiece.getLocation().y + rowDiff, tempPiece.getLocation().x + colDiff).setJumpTile(true);
						getTileAt(tempPiece.getLocation().y + rowDiff, tempPiece.getLocation().x + colDiff).setJumpingPiece(t.getPiece());
						this.jumpTargets.add(getTileAt(tempPiece.getLocation().y + rowDiff, tempPiece.getLocation().x + colDiff));
					}
				}
			}
		}

		return targets;
	}

	// n-jump detection
	public void nJump(Piece piece) {

		ArrayList<Tile> jumpTargets = new ArrayList<Tile>(this.jumpTargets);
		ArrayList<Tile> newTargets = new ArrayList<Tile>();
		if(jumpTargets.isEmpty())
			return;

		for(Tile t: jumpTargets) {
			calcTargets(new Point(t.getTileColumn(), t.getTileRow()), piece.getColor(), piece.isKing());
			if(!this.jumpTargets.isEmpty()) {
				for(Tile t2:this.jumpTargets) {
					if(!visited[t2.getIndex()]) {
						this.targets.add(t2);
						newTargets.add(t2);
						visited[t2.getIndex()] = true;
					}
				}
				this.jumpTargets = new ArrayList<Tile>(newTargets);
				nJump(piece);
			}
		}

		this.visited = new Boolean[tiles.size()];
		for(int i = 0; i < visited.length; i++)
			visited[i] = false;
	}

	public void checkLocation(int row, int column) {
		boolean validTarget = false;
		for(Tile c : this.getTargets()) {
			for(Tile t: getTiles()){
				if(row == c.getTileRow() && column == c.getTileColumn() && t.getTileRow() == c.getTileRow() && t.getTileColumn() == c.getTileColumn()) {
					validTarget = true;
					t.setHasPiece(true);
					t.setPiece(selectedPiece);
				}
			}
		}
		if(validTarget) {
			Point target;
			target = new Point(column, row);
			if((row == 0 && selectedPiece.getColor() == Color.BLACK) || (row == 7 && selectedPiece.getColor() == Color.red)){
				selectedPiece.setKing(true);
			}
			getTileAt(selectedPiece.getLocation().y,selectedPiece.getLocation().x).setHasPiece(false);
			getTileAt(selectedPiece.getLocation().y,selectedPiece.getLocation().x).setPiece(null);
			selectedPiece.setLocation(target);
			if(getTileAt(target.y,target.x).isJumpTile()) {
				//this.pieces.remove(getTileAt(target.y,target.x).getJumpingPiece());
				for(Tile t:tiles) {
					if(t.isJumpTile())
						this.pieces.remove(t.getJumpingPiece());
				}
			}
			resetTargets();
			selectedPiece = null;
			for(Tile t:tiles) {
				t.setJumpTile(false);
				t.setJumpingPiece(null);
			}
			repaint();
		}
		else if(targets.size() == 0){
			JOptionPane.showMessageDialog(null,"There are no valid targets. Please select a new piece", "ERROR", JOptionPane.ERROR_MESSAGE);
			selectedPiece = null;
		}else{
			JOptionPane.showMessageDialog(null,"That is not a valid target.", "That is not a valid target.", JOptionPane.ERROR_MESSAGE);
			selectedPiece = null;
			targets.clear();
		}
	}
	//mouse listener
	private class BoardListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point location = e.getPoint();
			int width = getWidth()/NUMCOLUMNS;
			int height = getHeight()/NUMROWS;
			int row = (int) (location.getY()/height);
			int column = (int) (location.getX()/width);
			if(selectedPiece == null){
				for(Tile tiles: getTiles()){	
					if(row == tiles.getTileRow() && column == tiles.getTileColumn() && tiles.HasPiece() == true){
						for(Piece p: getPieces()){
							if(p.getLocation().getY() == row && p.getLocation().getX() == column){
								selectedPiece = p;
								startTargets(selectedPiece);

								nJump(selectedPiece);
								//if(targets.size() == 0){
								//canJump(selectedPiece, selectedPiece.isKing());
								//}
								if(targets.size()!= 0){
									//tiles.setHasPiece(false);
								}
							}
						}
					}
				}
			}else{
				checkLocation(row,column);
			}
			repaint();	
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
			if(pieces == selectedPiece){
				pieces.drawHighlight(g, this, width, height);
			}
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


	// This will remove a piece from the board.
	public void removePiece(Piece remove) {
		pieces.remove(remove);
	}

	// Returns the tile at given row and column.
	public Tile getTileAt(int row, int col) {
		for (Tile t : tiles) {
			if (t.getTileRow() == row && t.getTileColumn() == col)
				return t;
		}
		return null;
	}

	public void resetTargets(){
		targets.removeAll(targets);
	}
	public void clearPieces(){
		for(Tile t: getTiles()){
			t.setHasPiece(false);
		}
		getPieces().clear();
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

	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(Piece selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	public Boolean[] getVisited() {
		return visited;
	}

	public void setVisited(Boolean[] visited) {
		this.visited = visited;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}
}

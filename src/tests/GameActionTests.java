package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import main.Board;
import main.CheckerGame;
import main.Piece;
import main.Tile;

import org.junit.*;

public class GameActionTests {
	
	private CheckerGame game;
	private Board board;
	private ArrayList<Piece> pieces;
	ArrayList<Tile> targets;
	
	@Before
	public void setup() {
		game = new CheckerGame();
		board = new Board();
		pieces = board.getPieces();
		targets = new ArrayList<Tile>();
		
	}
	
	// TESTS THE calcTargets() function in the Board class
	@Test
	public void testBasicTargetSelection() {
		
		// Testing one that can't move at all
		Piece piece1 = pieces.get(1);
		board.calcTargets(piece1, piece1.isKing());
		targets = board.getTargets();
		assertEquals(0, targets.size());
		
		//Testing one that can move to one place
		Piece piece13 = pieces.get(13);
		board.calcTargets(piece13, piece13.isKing());
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue( targets.contains(new Tile(4, 1)) );
		
		//Testing one that can move to two places
		Piece piece10 = pieces.get(10);
		board.calcTargets(piece10, piece10.isKing());
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(3, 4)) );
		
	}
	
	@Test
	public void testAdvancedTargetSelection() {
	
		//Testing one that can jump a piece
		Piece piece15 = pieces.get(15);
		Piece jumpedPiece = new Piece(4, 3, Color.red);
		
		//Add the new piece to the set location
		board.getPieces().add(jumpedPiece);
		board.calcTargets(piece15, piece15.isKing());
		assertEquals(2, targets.size());
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );
		
		
		//Testing one that could potentially double jump a piece
		board.getPieces().remove(5);
		board.getPieces().remove(7);
		
		//We made room for the piece to double jump to two different spaces
		board.calcTargets(piece15, piece15.isKing());
		assertEquals(3, targets.size());
		assertTrue( targets.contains(new Tile(1, 4)) );
		assertTrue( targets.contains(new Tile(1, 0)) );
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );
		
	}
	
	@Test
	public void testBasicKingTargetSelection() {
		board.getPieces().clear();
	}
	
	@Test
	public void testAdvancedKingTargetSelection() {
		
	}

}

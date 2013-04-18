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
		Piece piece12 = pieces.get(12);
		board.calcTargets(piece12, piece12.isKing());
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue( targets.contains(new Tile(4, 1)) );

		//Testing one that can move to two places
		Piece piece9 = pieces.get(9);
		board.calcTargets(piece9, piece9.isKing());
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
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );


		//Testing one that could potentially double jump a piece
		board.getPieces().remove(5);
		board.getPieces().remove(7);

		//We made room for the piece to double jump to two different spaces
		board.calcTargets(piece15, piece15.isKing());
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue( targets.contains(new Tile(1, 4)) );
		assertTrue( targets.contains(new Tile(1, 0)) );
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );
		
		// Make sure pieces cannot jump friendly pieces
		Piece piece19 = pieces.get(19);
		board.calcTargets(piece19, piece19.isKing());
		targets = board.getTargets();
		assertEquals(0, targets.size());
	}

	@Test
	public void testBasicKingTargetSelection() {

		// Clear the board
		board.clearPieces();

		// Add a king
		Piece king = new Piece(0,3,Color.red);
		king.setKing(true);

		// Test movement on player's edge
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
			assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(2,1)));
		assertTrue(targets.contains(new Tile(4,1)));

		// Test movement on opponent's edge
		king.setLocation(new Point(2,7));
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		
		// Test movement on left side
		king.setLocation(new Point(0,3));
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(2,1)));
		assertTrue(targets.contains(new Tile(4,1)));

		// Test movement on right side
		king.setLocation(new Point(7,4));
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(3,6)));
		assertTrue(targets.contains(new Tile(5,6)));

		// Test movement in upper-right corner
		king.setLocation(new Point(7,0));
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		System.out.println(targets.get(0).getTileRow());
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(1,6)));

		// Test movement in bottom-left corner
		king.setLocation(new Point(0,7));
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(6,1)));
	}

	@Test
	public void testAdvancedKingTargetSelection() {

		// Add a king
		Piece king = new Piece(5,2,Color.RED);
		king.setKing(true);
		
		// Add an opponent piece, test jump
		Piece victim1 = new Piece(4,1,Color.BLACK);
		board.getPieces().add(victim1);
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(4,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		
		// Add a second opponent piece, test backwards jump
		Piece victim2 = new Piece(6,3,Color.BLACK);
		board.getPieces().add(victim2);
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(4,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(7,4)));
		
		// Add a third opponent piece, test double jump
		Piece victim3 = new Piece(2,1,Color.BLACK);
		board.getPieces().add(victim3);
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(5,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		assertTrue(targets.contains(new Tile(1,3)));
		
		// Add a friendly piece, ensure king cannot jump
		Piece ally = new Piece(4,3,Color.RED);
		board.getPieces().add(ally);
		board.calcTargets(king,king.isKing());
		targets = board.getTargets();
		assertEquals(4,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		assertTrue(targets.contains(new Tile(1,3)));
	}

}

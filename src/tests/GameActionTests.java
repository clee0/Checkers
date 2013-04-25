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
		board = new Board(game);
		pieces = board.getPieces();
		targets = new ArrayList<Tile>();
	}
	
	/*@Test
	public void test1() {
		Piece p1 = new Piece(3,6,Color.BLACK);
		Piece p2 = new Piece(4,5,Color.RED);
		Piece p3 = new Piece(4,3,Color.RED);
		Piece p4 = new Piece(4,1,Color.RED);
		board.startTargets(p1);
		//board.nJump(p1);
		assertEquals(4,board.getTargets().size());
	}*/

	// TESTS THE calcTargets() function in the Board class
	@Test
	public void testBasicTargetSelection() {

		// Testing one that can't move at all
		Piece piece1 = pieces.get(1);
		board.startTargets(piece1);
		targets = board.getTargets();
		assertEquals(0, targets.size());

		//Testing one that can move to one place
		Piece piece12 = pieces.get(12);
		board.startTargets(piece12);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue( targets.contains(new Tile(4, 1)) );

		///Testing one that can move to two places
		Piece piece9 = pieces.get(9);
		board.startTargets(piece9);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(3, 4)) );
		
		//Test the bottom-left corner movement 
		board.clearPieces();
		Piece cornerPiece = new Piece(0,7, Color.BLACK);
		cornerPiece.setKing(false);
		board.startTargets(cornerPiece);
		targets = board.getTargets();
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(6,1)));
		
		//test the top right corner movement
		cornerPiece.setLocation(new Point(7,0));
		cornerPiece.setColor(Color.red);
		board.startTargets(cornerPiece);
		targets = board.getTargets();
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(1,6)));
	
	}

	@Test
	public void testAdvancedTargetSelection() {
		
		board.clearPieces();

		//Testing one that can jump a piece
		Piece piece15 = new Piece(4, 5, Color.BLACK);
		Piece jumpedPiece = new Piece(3, 4, Color.RED);

		//Add the new piece to the set location
		board.getPieces().add(jumpedPiece);
		board.getPieces().add(piece15);
		board.startTargets(piece15);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );


		//Testing one that could potentially double jump a piece
		Piece dblJumped = new Piece(3,2,Color.RED);
		board.getPieces().add(dblJumped);

		//We made room for the piece to double jump to two different spaces
		board.startTargets(piece15);
		board.nJump(piece15);
		targets = board.getTargets();
		for(Tile t:targets)
			System.out.println(t);
		assertEquals(3, targets.size());
		assertTrue( targets.contains(new Tile(1, 4)) );
		assertTrue( targets.contains(new Tile(3, 2)) );
		assertTrue( targets.contains(new Tile(4, 5)) );
		
		// Make sure pieces cannot jump friendly pieces
		Piece ally = new Piece(5,4,Color.BLACK);
		board.getPieces().add(ally);
		board.startTargets(piece15);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(new Tile(3,2)));
	}

	@Test
	public void testBasicKingTargetSelection() {

		// Clear the board
		board.clearPieces();

		// Add a king
		Piece king = new Piece(0,3,Color.red);
		king.setKing(true);

		/// Test movement on player's edge
		board.startTargets(king);
		targets = board.getTargets();
			assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(2,1)));
		assertTrue(targets.contains(new Tile(4,1)));

		// Test movement on opponent's edge
		king.setLocation(new Point(2,7));
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		
		// Test movement on left side
		king.setLocation(new Point(0,3));
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(2,1)));
		assertTrue(targets.contains(new Tile(4,1)));

		// Test movement on right side
		king.setLocation(new Point(7,4));
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(2,targets.size());
		assertTrue(targets.contains(new Tile(3,6)));
		assertTrue(targets.contains(new Tile(5,6)));

		// Test movement in upper-right corner
		king.setLocation(new Point(7,0));
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(1,6)));

		// Test movement in bottom-left corner
		king.setLocation(new Point(0,7));
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(1,targets.size());
		assertTrue(targets.contains(new Tile(6,1)));
	}

	/*@Test
	public void testAdvancedKingTargetSelection() {
		
		board.clearPieces();

		// Add a king
		Piece king = new Piece(2,5,Color.RED);
		king.setKing(true);
		
		// Add an opponent piece, test jump
		Piece victim1 = new Piece(1,4,Color.BLACK);
		board.getPieces().add(victim1);
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(4,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		
		// Add a second opponent piece, test backwards jump
		Piece victim2 = new Piece(3,6,Color.BLACK);
		board.getPieces().add(victim2);
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(4,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(7,4)));
		
		// Add a third opponent piece, test double jump
		Piece victim3 = new Piece(1,2,Color.BLACK);
		board.getPieces().add(victim3);
		
		Boolean visited[] = new Boolean[board.getTiles().size()];
		for(int i = 0; i < visited.length; i++)
			visited[i] = false;
		board.setVisited(visited);
		
		board.startTargets(king);
		board.nJump(king);
		targets = board.getTargets();
		assertEquals(5,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(4,3)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(6,3)));
		assertTrue(targets.contains(new Tile(1,3)));
		
		// Add a friendly piece, ensure king cannot jump
		Piece ally = new Piece(3,4,Color.RED);
		board.getPieces().add(ally);
		board.startTargets(king);
		targets = board.getTargets();
		assertEquals(3,targets.size());
		assertTrue(targets.contains(new Tile(3,0)));
		assertTrue(targets.contains(new Tile(6,1)));
		assertTrue(targets.contains(new Tile(7,4)));
	}*/
	
	@Test
	public void testRemove() {
		board.clearPieces();
		// Add pieces to the board.
		Piece remove1 = new Piece(0, 0, Color.BLACK);
		Piece remove2 = new Piece(0, 2, Color.BLACK);
		Piece remove3 = new Piece(0, 4, Color.BLACK);
		Piece remove4 = new Piece(0, 6, Color.BLACK);
		Piece remove5 = new Piece(1, 0, Color.RED);
		Piece remove6 = new Piece(1, 2, Color.RED);
		Piece remove7 = new Piece(1, 4, Color.RED);
		Piece remove8 = new Piece(1, 6, Color.RED);
	
		board.getPieces().add(remove1);
		board.getPieces().add(remove2);
		board.getPieces().add(remove3);
		board.getPieces().add(remove4);
		board.getPieces().add(remove5);
		board.getPieces().add(remove6);
		board.getPieces().add(remove7);
		board.getPieces().add(remove8);
		
		// Remove the pieces added.
		board.removePiece(remove1);
		board.removePiece(remove2);
		board.removePiece(remove3);
		board.removePiece(remove4);
		board.removePiece(remove5);
		board.removePiece(remove6);
		board.removePiece(remove7);
		board.removePiece(remove8);
		
		// Check if the tiles have a piece on them.
		assertFalse(board.getTileAt(0, 0).HasPiece());
		assertFalse(board.getTileAt(0, 2).HasPiece());
		assertFalse(board.getTileAt(0, 4).HasPiece());
		assertFalse(board.getTileAt(0, 6).HasPiece());
		assertFalse(board.getTileAt(1, 0).HasPiece());
		assertFalse(board.getTileAt(1, 2).HasPiece());
		assertFalse(board.getTileAt(1, 4).HasPiece());
		assertFalse(board.getTileAt(1, 6).HasPiece());	
	}
	
	// Test checkLocation function
	@Test
	public void testMove() {
		
		
		// Clear board
		board.clearPieces();
		
		// Add a test piece
		Piece piece = new Piece(2,5,Color.BLACK);
		board.getPieces().add(piece);
		
		// Test move function
		board.setSelectedPiece(piece);
		board.startTargets(piece);
		board.checkLocation(4, 1);
		assertTrue(board.getTileAt(4,1).HasPiece());
		assertFalse(board.getTileAt(5,2).HasPiece());
		
		// Ensure move function does not move to invalid targets
		board.checkLocation(5,2);
		assertTrue(board.getTileAt(4,1).HasPiece());
		assertFalse(board.getTileAt(5,2).HasPiece());
		
		// Test kinging (all 8 possible kinging tiles)
		board.clearPieces();
		
		Piece black1 = new Piece(0,1,Color.BLACK);
		Piece black2 = new Piece(2,1,Color.BLACK);
		Piece black3 = new Piece(4,1,Color.BLACK);
		Piece black4 = new Piece(6,1,Color.BLACK);
		Piece red1 = new Piece(1,6,Color.RED);
		Piece red2 = new Piece(3,6,Color.RED);
		Piece red3 = new Piece(5,6,Color.RED);
		Piece red4 = new Piece(7,6,Color.RED);
		board.getPieces().add(black1);
		board.getPieces().add(black2);
		board.getPieces().add(black3);
		board.getPieces().add(black4);
		board.getPieces().add(red1);
		board.getPieces().add(red2);
		board.getPieces().add(red3);
		board.getPieces().add(red4);
		
		board.setSelectedPiece(black1);
		board.startTargets(black1);
		board.checkLocation(0,1);
		board.setSelectedPiece(black2);
		board.startTargets(black2);
		board.checkLocation(0,3);
		board.setSelectedPiece(black3);
		board.startTargets(black3);
		board.checkLocation(0,5);
		board.setSelectedPiece(black4);
		board.startTargets(black4);
		board.checkLocation(0,7);
		board.setSelectedPiece(red1);
		board.startTargets(red1);
		board.checkLocation(7,0);
		board.setSelectedPiece(red2);
		board.startTargets(red2);
		board.checkLocation(7,2);
		board.setSelectedPiece(red3);
		board.startTargets(red3);
		board.checkLocation(7,4);
		board.setSelectedPiece(red4);
		board.startTargets(red4);
		board.checkLocation(7,6);
		
		assertTrue(black1.isKing());
		assertTrue(black2.isKing());
		assertTrue(black3.isKing());
		assertTrue(black4.isKing());
		assertTrue(red1.isKing());
		assertTrue(red2.isKing());
		assertTrue(red3.isKing());
		assertTrue(red4.isKing());
	}

	@Test
	public void testJump() {
		
		// Clear board
		board.clearPieces();
		
		// Add two pieces
		Piece jumper = new Piece(2,5,Color.BLACK);
		Piece victim = new Piece(3,4,Color.RED);
		board.getPieces().add(jumper);
		board.getPieces().add(victim);
		
		// Jump, check piece is removed and tiles are updated
		board.setSelectedPiece(jumper);
		board.startTargets(jumper);
		board.checkLocation(3,4);
		board.setSelectedPiece(jumper);
		board.startTargets(jumper);
		assertTrue(board.getTileAt(3,4).HasPiece());
		assertFalse(board.getTileAt(4,3).HasPiece());
		assertFalse(board.getTileAt(5,2).HasPiece());
	}
}

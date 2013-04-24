package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.*;

public class CheckerGame extends JFrame{
	public enum Player {RED, WHITE}
	private Board board;
	private Player currentPlayer;
	private boolean gameOver;
	
	private int numWhitePieces;
	private int numRedPieces;
	private int numWhiteKings;
	private int numRedKings;
	private String whoseTurn;
	
	private JTextField whitePieces;
	private JTextField redPieces;
	private JTextField whiteKings;
	private JTextField redKings;
	private JTextField whoseTurnField;
	
	public CheckerGame(){
		setSize(new Dimension(600, 700));
		board = new Board(this);
		this.add(board, BorderLayout.CENTER);
		createBottomPanel();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}

	public void setup(){
		
	}

	public void turn(){
		
	}
	
	public void nextPlayer(){
		
	}
	
	public void splash(){
		
	}
	
	private void createBottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(0, 3));
		
		JPanel redPanel = new JPanel(new GridLayout(2, 2));		
		JPanel turnPanel = new JPanel(new GridBagLayout());
		JPanel whitePanel = new JPanel(new GridLayout(2, 2));
		
		redPanel.setBorder(BorderFactory.createTitledBorder("RED"));
		turnPanel.setBorder(BorderFactory.createTitledBorder("Whose turn?"));
		whitePanel.setBorder(BorderFactory.createTitledBorder("WHITE"));
		
		JLabel whitePiecesLabel = new JLabel("Pieces:");
		JLabel redPiecesLabel = new JLabel("Pieces:");
		JLabel whiteKingsLabel = new JLabel("Kings:");
		JLabel redKingsLabel = new JLabel("Kings:");
		
		whitePieces = new JTextField();
		redPieces = new JTextField();
		whiteKings = new JTextField();
		redKings = new JTextField();
		whoseTurnField = new JTextField();
		
		whitePieces.setEditable(false);
		redPieces.setEditable(false);
		whiteKings.setEditable(false);
		redKings.setEditable(false);
		whoseTurnField.setEditable(false);
		
		// Initialize the text fields
		setNumWhitePieces(12);
		setNumWhiteKings(0);
		setNumRedPieces(12);
		setNumRedKings(0);
		setWhoseTurn("RED");		
		
		redPanel.add(redPiecesLabel);
		redPanel.add(redPieces);
		redPanel.add(redKingsLabel);
		redPanel.add(redKings);
		
		whitePanel.add(whitePiecesLabel);
		whitePanel.add(whitePieces);
		whitePanel.add(whiteKingsLabel);
		whitePanel.add(whiteKings);
		
		turnPanel.add(whoseTurnField);	
		
		bottomPanel.add(redPanel);
		bottomPanel.add(turnPanel);
		bottomPanel.add(whitePanel);
		
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createNewGameItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createNewGameItem() {
		JMenuItem item = new JMenuItem("New Game");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}	
	
	public void newGame() {
		this.dispose();
		CheckerGame game = new CheckerGame();
		game.setVisible(true);
		run();
	}
	
	public void setNumWhitePieces(int numWhitePieces) {
		this.numWhitePieces = numWhitePieces;
		this.whitePieces.setText(Integer.toString(numWhitePieces));
	}

	public void setNumRedPieces(int numRedPieces) {
		this.numRedPieces = numRedPieces;
		this.redPieces.setText(Integer.toString(numRedPieces));
	}

	public void setNumWhiteKings(int numWhiteKings) {
		this.numWhiteKings = numWhiteKings;
		this.whiteKings.setText(Integer.toString(numWhiteKings));
	}

	public void setNumRedKings(int numRedKings) {
		this.numRedKings = numRedKings;
		this.redKings.setText(Integer.toString(numRedKings));
	}

	public void setWhoseTurn(String whoseTurn) {
		this.whoseTurn = whoseTurn;
		this.whoseTurnField.setText(whoseTurn);
	}
	
	public void setGameOver(String winner) {
		this.gameOver = true;
		JOptionPane popup = new JOptionPane();
		String message = "Game over. " + winner + " wins.";
		popup.showMessageDialog(this, message, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public static void main(String[] args) {
		CheckerGame game = new CheckerGame();
		game.setVisible(true);
		game.run();
	}
	
	public void run() {
		
	}

}
	
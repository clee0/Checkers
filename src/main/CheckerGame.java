package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import main.*;

public class CheckerGame extends JFrame{
	public enum Player {RED, BLACK}
	private Board board;
	private Player currentPlayer;
	private boolean gameOver;
	public CheckerGame(){
		board = new Board();
		setSize(new Dimension(400,400));
		this.add(board, BorderLayout.CENTER);
		
		
	}
	public void setup(){
		
	}

	public void turn(){
		
	}
	public void nextPlayer(){
		
	}
	
	public void splash(){
		
	}
	public static void main(String[] args) {
		CheckerGame newThing = new CheckerGame();
		newThing.setVisible(true);

	}

}
	
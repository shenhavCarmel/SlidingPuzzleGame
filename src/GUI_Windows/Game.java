package GUI_Windows;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import Logic.Pair;
import Logic.PuzzlePiece;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;

public class Game extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int n;
	private String imgName;
	private PuzzlePiece[][] matrix;
	private JPanel boardPanel;
	private Stack<PuzzlePiece> undoStack;
	private LinkedList<int[][]> gameBoards;
	private boolean isFinish;
	
	// steps counter
	private Integer stp;
	private JLabel lblSteps;
	private JLabel lblStepsCount;

	// timer 
	private Timer time;
	private int seconds;
	private JLabel lbltimer;
	private JButton btnExit;
	private JLabel lblBackground;

	
	// Constructor
	public Game(Integer n, String img, LinkedList<int[][]> boards) {
		setTitle("Sliding Puzzle Game");
		
		this.isFinish = false;
		imgName = img;
		this.n = n;
		this.stp = 0;
		this.undoStack = new Stack<>();
		this.gameBoards = boards;

		matrix = new PuzzlePiece[n][n];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		boardPanel = new JPanel();
		boardPanel.setBounds(147, 37, 300, 300);
		contentPane.add(boardPanel);
		
		this.addKeyListener(new KeyListener() {

			@Override
			// Handle the keyBoard
			public void keyPressed(KeyEvent e) {
				if (!getIsFinish()) {
					Pair Blank = whereBlank();
					int keyCode = e.getKeyCode();

					switch( keyCode ) { 

					case KeyEvent.VK_UP:

						// handle up
						if (!(Blank.getX() == n-1)) {
							undoStack.push(matrix[Blank.getX()+1][Blank.getY()]);
							SwitchPieces(Blank.getX()+1, Blank.getY(), Blank.getX(), Blank.getY());
							stp++;
							lblStepsCount.setText(stp.toString());
						}
						break;

					case KeyEvent.VK_DOWN:

						// handle down 
						if (!(Blank.getX() == 0)) {
							undoStack.push(matrix[Blank.getX()-1][Blank.getY()]);
							SwitchPieces(Blank.getX()-1, Blank.getY(), Blank.getX(), Blank.getY());
							stp++;
							lblStepsCount.setText(stp.toString());
						}
						break;

					case KeyEvent.VK_LEFT:

						// handle left
						if (!(Blank.getY() == n-1)) {
							undoStack.push(matrix[Blank.getX()][Blank.getY()+1]);
							SwitchPieces(Blank.getX(), Blank.getY()+1, Blank.getX(), Blank.getY());
							stp++;
							lblStepsCount.setText(stp.toString());
						}
						break;

					case KeyEvent.VK_RIGHT :

						// handle right
						if (!(Blank.getY() == 0)) {
							undoStack.push(matrix[Blank.getX()][ Blank.getY()-1]);
							SwitchPieces(Blank.getX(), Blank.getY()-1, Blank.getX(), Blank.getY());
							stp++;
							lblStepsCount.setText(stp.toString());
						}
						break;

					case KeyEvent.VK_Z :
						
						// Handle if pressed ctrl+z - undo
						if (e.isControlDown()) {
							undoAction();
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.setFocusable(true);
		this.requestFocusInWindow();
		 
		// set timer
		lbltimer = new JLabel ();
		lbltimer.setText("00:00:00");
		lbltimer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbltimer.setBounds(22, 209, 89, 23);
		contentPane.add(lbltimer);
		time = new Timer (1000,this);
		time.start();

		// set steps counter
		this.lblStepsCount = new JLabel("0");
		lblStepsCount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStepsCount.setForeground(SystemColor.desktop);
		lblStepsCount.setBounds(30, 126, 107, 31);
		contentPane.add(lblStepsCount);
		if (n>0)
			boardPanel.setLayout(new GridLayout(n, n, 0, 0));
		else
			boardPanel.setLayout(new GridLayout(3, 3, 0, 0));

		// initialize the board according to choosing
		initBoard();

		JButton btnUndo = new JButton("UNDO");
		btnUndo.setBackground(new Color(255, 228, 181));
		btnUndo.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!getIsFinish()) {
					undoAction();
				}
			}
		});
		btnUndo.setBounds(22, 43, 89, 23);
		contentPane.add(btnUndo);

		JButton btnBack = new JButton("NEW GAME");
		
		// sets the background
		btnBack.setBackground(new Color(255, 228, 181));
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Settings setWindow = new Settings(getGameBoards());
				setWindow.setVisible(true);
				dispose();
			}
		});

		btnBack.setBounds(22, 280, 107, 23);
		contentPane.add(btnBack);

		lblSteps = new JLabel("Steps:");
		lblSteps.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblSteps.setBounds(22, 92, 63, 23);
		contentPane.add(lblSteps);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblTime.setBounds(22, 168, 63, 30);
		contentPane.add(lblTime);
		
		btnExit = new JButton("EXIT");
		btnExit.setBackground(new Color(255, 228, 181));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(32, 314, 89, 23);
		contentPane.add(btnExit);
		
		// sets the background
		ImageIcon background = new ImageIcon(Settings.class.getResource("/Pics/more/background.jpeg"));
		Image resizedBkg = background.getImage();
		background = new ImageIcon(resizedBkg);
		
		JLabel lblBackText = new JLabel("");
		lblBackText.setOpaque(true);
		lblBackText.setBackground(new Color(255, 255, 224));
		lblBackText.setBounds(10, 77, 131, 167);
		contentPane.add(lblBackText);
		
		lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 484, 361);
		resizedBkg.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), java.awt.Image.SCALE_SMOOTH);
		lblBackground.setIcon(background);
		contentPane.add(lblBackground);
	}

	// check if the game is finished
	public boolean getIsFinish() {
		return this.isFinish;
	}

	private void undoAction() {
		if (!undoStack.isEmpty()) {
			PuzzlePiece pieceToUndo = undoStack.pop();
			MoveBoard(pieceToUndo.getCurrPos().getX(), pieceToUndo.getCurrPos().getY());
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public LinkedList<int[][]> getGameBoards() {
		return this.gameBoards;
	}

	private void initBoard() {	
		int[][] boardToPlay = getRandomBoard();
		for(int r = 0; r < n; r++){
			for(int c = 0; c < n; c++){

				matrix[r][c] = new PuzzlePiece(boardToPlay[r][c], n, this.imgName, boardPanel.getHeight(), r,c);
				boardPanel.add(matrix[r][c]);
				matrix[r][c].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {	
						if (!getIsFinish()) {
							PuzzlePiece puzzleClicked = (PuzzlePiece)e.getSource();
							if (MoveBoard(puzzleClicked.getCurrPos().getX(), 
									puzzleClicked.getCurrPos().getY())) {

								// push the moving piece into the undo stack
								undoStack.push(puzzleClicked);
								stp++;
								lblStepsCount.setText(stp.toString());

							}
						}
					}
				}); 
			}
		}

	}

	// finds the blank piece
	private Pair whereBlank() {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (matrix[i][j].getIsBlank())
					return new Pair(i,j);
			}
		}
		return null;
	}

	private int[][] getRandomBoard() {
		LinkedList<int[][]> matrixInSize = new LinkedList<>();

		// collect all the matrix in the requested size
		for (int[][] currMat : this.gameBoards) {
			if (currMat.length == this.n) {
				matrixInSize.add(currMat);
			}
		}

		// get a random matrix from the list
		Random ran = new Random();
		int x = ran.nextInt(matrixInSize.size());

		return matrixInSize.get(x);
	}

	private void checkIfFinish() {
		boolean check = true;
		for (int i=0; i<this.n; i++) {
			for (int j=0; j<this.n; j++) {
				if (!matrix[i][j].isInPos())
					check = false;
			}
		}
		if (check) {
			this.isFinish = true;
			displayWin();
		}
	}

	// display the overall picture
	private void displayWin() {

		this.boardPanel.removeAll();
		this.boardPanel.setLayout(null);
		JLabel pic = new JLabel();
		pic.setSize(boardPanel.getWidth(), boardPanel.getHeight());
		BufferedImage img = null;
		try {
			img = ImageIO.read(Settings.class.getResource(
					"/Pics/"+this.imgName+"/"+this.imgName+".jpeg"));
		} catch (Exception e) {
			try {
				img = ImageIO.read(Settings.class.getResource(
						"/Pics/"+this.imgName+"/"+this.imgName+".jpg"));
			}
			 catch (Exception e1) {
				 e1.printStackTrace();
			 }
		}
		Image dimg = img.getScaledInstance(pic.getWidth(), pic.getHeight(),
				Image.SCALE_SMOOTH);

		pic.setIcon(new ImageIcon(dimg));
		boardPanel.add(pic);
		boardPanel.revalidate();
		boardPanel.repaint();

		time.stop();
		VictoryWindow winWindow = new VictoryWindow(lbltimer.getText(), lblStepsCount.getText(), this.gameBoards);
		winWindow.setVisible(true);
	}

	//moving boards (buttons) and check if can move
	public boolean MoveBoard (int r, int c)
	{
		boolean isMoved = false;
		if (isLegalMove(r+1, c)) {
			SwitchPieces(r, c, r+1, c);
			isMoved = true;
		}
		else if (isLegalMove(r-1, c)) {
			SwitchPieces(r, c, r-1, c);
			isMoved = true;
		}
		else if (isLegalMove(r, c+1)) {
			SwitchPieces(r, c, r, c+1);
			isMoved = true;
		}
		else if (isLegalMove(r, c-1)) {
			SwitchPieces(r, c, r, c-1);
			isMoved = true;
		}

		return isMoved;
	}

	// check if the move is legal
	private boolean isLegalMove(int a, int b) {
		if (a >= 0 && a < n && b >= 0 && b < n)
			if (matrix[a][b].getIsBlank())
				return true;
		return false;
	}

	// moves the piece according to the user's request
	private void SwitchPieces(int x, int y, int xBlank, int yBlank) {

		matrix[x][y].setCurrPos(xBlank, yBlank);
		matrix[xBlank][yBlank].setCurrPos(x, y);

		PuzzlePiece tmp = matrix[x][y];
		matrix[x][y] = matrix[xBlank][yBlank];
		matrix[xBlank][yBlank] = tmp;

		boardPanel.removeAll();

		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				boardPanel.add(matrix[i][j]);		
			}
		}

		boardPanel.revalidate();
		boardPanel.repaint();

		checkIfFinish();
	}


	// timer
	public void actionPerformed(ActionEvent e) {

		seconds++;
		String hour = "";
		String min = "";
		String sec = "";
		hour = hour + seconds / 3600;
		hour = Check(hour);
		min = min + (seconds % 3600)/60;
		min = Check(min);
		sec = sec + seconds % 60;
		sec = Check(sec);
		lbltimer.setText(hour + ":" + min + ":" + sec);

	}

	private String Check (String t) {
		if (t.length() == 1) {
			t = "0" + t;
		}
		return t;
	}
}

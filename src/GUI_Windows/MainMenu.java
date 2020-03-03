package GUI_Windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

public class MainMenu extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	private LinkedList<int[][]> boards;

	// Constructor
	public MainMenu() {
		setTitle("Sliding Puzzle Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// load boards from csv
		this.boards = scanCSV();
		
		JButton btnStart = new JButton("Start!");
		btnStart.setBackground(new Color(245, 222, 179));
		btnStart.setFont(new Font("Broadway", Font.PLAIN, 17));
		btnStart.setBounds(160, 124, 95, 66);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Settings setWindow = new Settings(getBoards());
				setWindow.setVisible(true);
				dispose();
			}
		});
		
		contentPane.add(btnStart);

		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(255, 228, 181));
		btnExit.setFont(new Font("Segoe UI Semilight", Font.BOLD, 13));
		btnExit.setBounds(160, 227, 89, 23);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		contentPane.add(btnExit);

		JLabel lblSlidingPuzzleGame = new JLabel("Sliding Puzzle Game");
		lblSlidingPuzzleGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlidingPuzzleGame.setBackground(UIManager.getColor("Button.light"));
		lblSlidingPuzzleGame.setForeground(UIManager.getColor("Button.foreground"));
		lblSlidingPuzzleGame.setFont(new Font("Broadway", Font.BOLD | Font.ITALIC, 22));
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        lblSlidingPuzzleGame.setBorder(border);
		lblSlidingPuzzleGame.setBounds(73, 66, 290, 47);
		lblSlidingPuzzleGame.setOpaque(true);
		contentPane.add(lblSlidingPuzzleGame);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(MainMenu.class.getResource("/Pics/more/background.jpeg")));
		lblBackground.setBounds(0, 0, 434, 261);
		contentPane.add(lblBackground);

	}	
	
	public LinkedList<int[][]> getBoards() {
		return this.boards;
	}

	// loads the arangment from the csv file
	private LinkedList<int[][]> scanCSV() {
		LinkedList<String[][]> lstBoards = new LinkedList<>();
		LinkedList<int[][]> boards = new LinkedList<>();
		File file = new File("boards.csv");
		try {
			Scanner scanner = new Scanner(file);
			int boardSize;
			while (scanner.hasNext()) {

				// get and set the board size
				boardSize = Integer.parseInt(scanner.nextLine());
				lstBoards.add(new String[boardSize][boardSize]);
				
				// init curr board according to the csv file
				for (int rows = 0; rows < boardSize; rows++) {
					(lstBoards.getLast())[rows] = scanner.nextLine().split(",");				
				}
			}
			
			// cast matrixes to int matrixes
			for (String[][] currStrMat : lstBoards) {		
				int n = currStrMat.length;
				int[][] newMat = new int[n][n];
				for (int i=0; i < n; i++) {
					for (int j=0; j < n; j++) {
						newMat[i][j] = Integer.parseInt(currStrMat[i][j]);
					}
				}
				boards.add(newMat);	
			}
			scanner.close();
			return boards;
		} 

		// file doesn't exist
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "CSV file not found", "Eorro", JOptionPane.WARNING_MESSAGE);	
			
			return null;
		}
		
	}
}

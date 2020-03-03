package GUI_Windows;

import java.awt.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class Settings extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btnSizeGroup = new ButtonGroup();
	private final ButtonGroup btnImgGroup = new ButtonGroup();
	private LinkedList<JRadioButton> lstImg = new LinkedList<>();
	private LinkedList<int[][]> boards;

	// buttons
	private JRadioButton rdbtn3size;
	private JRadioButton rdbtn4size;
	private JRadioButton rdbtn5size;

	// Contractor
	public Settings(LinkedList<int[][]> gameBoards) {
		setTitle("Sliding Puzzle Game");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.boards = gameBoards;

		checkBoardsSizes();

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(222, 184, 135)));
		panel.setBackground(new Color(240, 255, 240));
		panel.setBounds(21, 36, 414, 209);
		contentPane.add(panel);
		panel.setLayout(null);


		// init the radio buttons- pictures
		JRadioButton rdbSushi = new JRadioButton("Feeling Asian");
		rdbSushi.setBackground(new Color(240, 255, 240));
		rdbSushi.setSelected(true);
		rdbSushi.setActionCommand("sushi");
		btnImgGroup.add(rdbSushi);
		lstImg.add(rdbSushi);
		rdbSushi.setBounds(40, 143, 100, 23);
		panel.add(rdbSushi);

		JRadioButton rdbCat = new JRadioButton("Mitzi");
		rdbCat.setBackground(new Color(240, 255, 240));
		rdbCat.setActionCommand("cat");
		btnImgGroup.add(rdbCat);
		lstImg.add(rdbCat);
		rdbCat.setBounds(181, 143, 54, 23);
		panel.add(rdbCat);

		JRadioButton rdbCyber = new JRadioButton("cyber cyber cyber");
		rdbCyber.setBackground(new Color(240, 255, 240));
		rdbCyber.setActionCommand("cyber");
		btnImgGroup.add(rdbCyber);
		lstImg.add(rdbCyber);
		rdbCyber.setBounds(280, 143, 128, 23);
		panel.add(rdbCyber);

		// set icon 
		JButton btnSushi = new JButton("");
		btnSushi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbSushi.setSelected(true);
			}
		});
		btnSushi.setBounds(30, 36, 100, 100); 
		ImageIcon sushiImg = new ImageIcon(Settings.class.getResource("/Pics/sushi/sushi.jpg"));

		// transform it 
		Image resizedSushi = sushiImg.getImage(); 
		
		// scale it the smooth way  
		resizedSushi = resizedSushi.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); 
		
		// transform it back
		sushiImg = new ImageIcon(resizedSushi);  

		btnSushi.setIcon(sushiImg);;
		panel.add(btnSushi);


		// set icon 
		JButton btnCat = new JButton("");
		btnCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbCat.setSelected(true);
			}
		});
		btnCat.setBounds(155, 38, 100, 100);
		ImageIcon catImg = new ImageIcon(Settings.class.getResource("/Pics/cat/cat.jpeg"));

		Image resizedCat = catImg.getImage(); // transform it 
		resizedCat = resizedCat.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		catImg = new ImageIcon(resizedCat);  // transform it back

		btnCat.setIcon(catImg);
		panel.add(btnCat);	

		// set icon
		JButton btnCyber = new JButton("");
		btnCyber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbCyber.setSelected(true);
			}
		});
		btnCyber.setBounds(287, 38, 100, 100);
		ImageIcon cyberImg = new ImageIcon(Settings.class.getResource("/Pics/cyber/cyber.jpeg"));

		Image resizedCyber = cyberImg.getImage(); // transform it 
		resizedCyber = resizedCyber.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		cyberImg = new ImageIcon(resizedCyber);  // transform it back

		btnCyber.setIcon(cyberImg);
		panel.add(btnCyber);

		// Start the game
		JButton btnStart = new JButton("Start!");
		btnStart.setBackground(new Color(255, 228, 181));
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Integer sizeSelection = Integer.parseInt(btnSizeGroup.getSelection().getActionCommand());
				String imgSelection = btnImgGroup.getSelection().getActionCommand();
				Game gameWindow = new Game(sizeSelection, imgSelection, getBoards());
				gameWindow.setVisible(true);
				dispose();
			}
		});
		btnStart.setBounds(21, 259, 89, 23);
		contentPane.add(btnStart);

		// start the game with a random picture
		JButton btnShuffel = new JButton("Random Pic & Start");
		btnShuffel.setBackground(new Color(255, 228, 181));
		btnShuffel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnShuffel.setBounds(118, 173, 173, 23);
		panel.add(btnShuffel);

		// radio buttons- board size
		this.rdbtn3size = new JRadioButton("3x3");
		rdbtn3size.setBackground(new Color(240, 255, 240));
		rdbtn3size.setBounds(148, 6, 55, 23);
		panel.add(rdbtn3size);
		rdbtn3size.setSelected(true);
		btnSizeGroup.add(rdbtn3size);
		rdbtn3size.setActionCommand("3");

		this.rdbtn4size = new JRadioButton("4x4");
		rdbtn4size.setBackground(new Color(240, 255, 240));
		rdbtn4size.setBounds(228, 6, 55, 23);
		panel.add(rdbtn4size);
		btnSizeGroup.add(rdbtn4size);
		rdbtn4size.setActionCommand("4");

		this.rdbtn5size = new JRadioButton("5x5");
		rdbtn5size.setBackground(new Color(240, 255, 240));
		rdbtn5size.setBounds(319, 6, 54, 23);
		panel.add(rdbtn5size);
		btnSizeGroup.add(rdbtn5size);
		rdbtn5size.setActionCommand("5");

		JLabel lblBoardSize = new JLabel("Board Size:");
		lblBoardSize.setBounds(40, 0, 78, 30);
		panel.add(lblBoardSize);
		lblBoardSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnShuffel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Integer sizeSelection = Integer.parseInt(btnSizeGroup.getSelection().getActionCommand());
				String imgSelection = lstImg.get(getRandomPicIndex()).getActionCommand();
				Game gameWindow = new Game(sizeSelection, imgSelection, getBoards());
				gameWindow.setVisible(true);
				dispose();
			}
		});


		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(255, 228, 181));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnExit.setBounds(346, 259, 89, 23);
		contentPane.add(btnExit);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Settings.class.getResource("/Pics/more/background.jpeg")));
		label.setBounds(0, 0, 458, 293);
		contentPane.add(label);

	}

	private int getRandomPicIndex() {
		Random ran = new Random();
		return ran.nextInt(btnImgGroup.getButtonCount());
	}

	// which board size has been selected
	private void checkBoardsSizes() {
		boolean size3 = false;
		boolean size4 = false;
		boolean size5 = false;
		for (int[][] currMat : this.boards) {
			switch (currMat.length) {
			case 3:
				size3 = true;
				break;
			case 4:
				size4 = true;
				break;
			case 5:
				size5 = true;
				break;
			default:
				break;
			}
		}
		if (!size3) 
			rdbtn3size.setEnabled(false);
		if (!size4) 
			rdbtn4size.setEnabled(false);
		if (!size5) 
			rdbtn5size.setEnabled(false);
	}

	public LinkedList<int[][]> getBoards() {
		return this.boards;
	}
}

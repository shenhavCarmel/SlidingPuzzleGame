package GUI_Windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class VictoryWindow extends JFrame {

	private JPanel contentPane;
	private String stepsCount;
	private String time;
	private LinkedList<int[][]> boardGames;

	
	// Constructor
	public VictoryWindow(String time, String steps, LinkedList<int[][]> boardGames) {
		this.stepsCount = steps;
		
		// timer
		this.time = time;
		this.boardGames = boardGames;
		
		this.setVisible(true);
		
		setTitle("Victory!!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// show the player his time score
		JLabel lblTime = new JLabel("time : "+this.time);
		lblTime.setFont(new Font("Pristina", Font.PLAIN, 24));
		lblTime.setForeground(new Color(255, 250, 250));
		lblTime.setBounds(98, 158, 202, 37);
		contentPane.add(lblTime);
		
		// show the player his steps score
		JLabel lblSteps = new JLabel("steps : "+this.stepsCount);
		lblSteps.setForeground(new Color(255, 250, 250));
		lblSteps.setFont(new Font("Pristina", Font.PLAIN, 24));
		lblSteps.setBounds(98, 193, 201, 37);
		contentPane.add(lblSteps);
		
		// show the player his score
		JLabel lblYourScore = new JLabel("Your score:");
		lblYourScore.setFont(new Font("Verdana Pro Cond Light", Font.BOLD, 24));
		lblYourScore.setForeground(new Color(255, 255, 255));
		lblYourScore.setBounds(158, 106, 142, 56);
		contentPane.add(lblYourScore);
		
		// you won label
		JLabel lblCongratulations = new JLabel("Congratulations!");
		lblCongratulations.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 66));
		lblCongratulations.setForeground(new Color(255, 255, 255));
		lblCongratulations.setBounds(51, 41, 334, 87);
		contentPane.add(lblCongratulations);
		
		ImageIcon dab = new ImageIcon(MainMenu.class.getResource("/Pics/more/Unicorn.png"));
		Image resizedDab = dab.getImage();
		
		JLabel labelEmoji = new JLabel("");
		labelEmoji.setBounds(10, 158, 85, 85);
		resizedDab = resizedDab.getScaledInstance(labelEmoji.getWidth(), labelEmoji.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		dab = new ImageIcon(resizedDab);
		labelEmoji.setIcon(dab);
		contentPane.add(labelEmoji);
		
		// set background
		ImageIcon background = new ImageIcon(MainMenu.class.getResource("/Pics/more/backgroundWin.jpg"));
		Image resizedBackground = background.getImage();
		
		JButton btnExit = new JButton("Great! thanks");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setFont(new Font("Verdana Pro Cond SemiBold", Font.PLAIN, 14));
		btnExit.setBackground(new Color(248, 248, 255));
		btnExit.setBounds(289, 212, 135, 31);
		contentPane.add(btnExit);
		
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 434, 261);
		resizedBackground = resizedBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		background = new ImageIcon(resizedBackground);
		lblBackground.setIcon(background);
		contentPane.add(lblBackground);
	}
	
	private LinkedList<int[][]> getGameBoards() {
		return this.boardGames;
	}

}

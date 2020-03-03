package Logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import GUI_Windows.Settings;

public class PuzzlePiece extends JButton implements ActionListener{

	private Pair winPos;
	private Pair currPos;
	private boolean isBlank;
	private BufferedImage image;
	private int index;
	private int boardSize;
	private ImageIcon icon;

	public PuzzlePiece(int index, int boardSize, String imgName, int panelSize, int r, int c) {
		super();
		this.index = index;
		if (index != 0) {
			String imgPath = "/Pics/"+imgName+"/"+imgName+"_"+boardSize+"x"+boardSize+"/"+(index)+".jpeg";
			this.icon = new ImageIcon(Settings.class.getResource(imgPath));
			Image resizedImg = icon.getImage(); // transform it 
			resizedImg = resizedImg.getScaledInstance(panelSize / boardSize, panelSize / boardSize,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			this.icon = new ImageIcon(resizedImg);  // transform it back
		}
		this.boardSize = boardSize;
		this.currPos = new Pair(r,c);

		this.setSize(panelSize / boardSize, panelSize / boardSize);

		//this.isBlank = (index == boardSize * boardSize - 1);
		this.isBlank = this.index == 0;

		if (this.isBlank) {
			this.setIcon(null);
		}
		else {
			this.setIcon(icon);
		}

		// calculate the winning position for puzzle piece
		int i=0,j=0;

		//i = (index) / boardSize;
		//j = (index) % boardSize;
		int counter = 1;
		for (int k=0; k<boardSize; k++) {
			for (int m=0; m<boardSize; m++) {
				if (this.index == counter) {
					i = k ; 
					j = m;	
				}
				counter++;
			}
		}
		if (index == 0) {
			i = boardSize-1;
			j = boardSize-1;
		}
		/*
		if (index % boardSize == 0) {
			j = boardSize-1;
			i = (index / boardSize) - 1;
		}
		else {
			j = (index % boardSize) - 1;
			i = index / boardSize;
		}
		 */
	
		winPos = new Pair(i,j);
	}

	public boolean getIsBlank() {
		return isBlank;
	}

	public void showIcon() {
		this.setIcon(this.icon);
	}
	public Pair getWinPos() {
		return this.winPos;
	}

	public void setCurrPos(int r, int c) {
		this.currPos.setX(r);
		this.currPos.setY(c);
	}

	public Pair getCurrPos() {
		return this.currPos;
	}

	public boolean isInPos() {
		return (this.currPos.getX()==this.winPos.getX() && this.currPos.getY() == this.winPos.getY());
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}

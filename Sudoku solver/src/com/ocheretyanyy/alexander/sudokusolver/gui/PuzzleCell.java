package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class PuzzleCell extends JButton{
	
	private static final long serialVersionUID = 633045286168257825L;

	private final int nullValue = 0;
	private int value;
	
	private final Color backgroundColor = Color.WHITE;
	private final Font font = new Font("Arial", Font.BOLD, 20);
	
	protected PuzzleCell() {
		value = nullValue;
		setBackground(backgroundColor);
		setFont(font);
		setFocusPainted(false);
		setBorder(null);
	}
	
	protected PuzzleCell(String label) {
		this();
		setText(label);
	}
	
	protected int getValue() {
		return value;
	}
	
	protected void setValue(int value) {	
		if(value != nullValue) {
			this.value = value;
			setText(Integer.toString(value));
		}
		else {
			clear();
		}
	}
	
	protected void clear() {
		value = nullValue;
		setText(null);
	}
}
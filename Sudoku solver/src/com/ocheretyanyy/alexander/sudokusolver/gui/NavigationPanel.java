package com.ocheretyanyy.alexander.sudokusolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ocheretyanyy.alexander.sudokusolver.SudokuSolverManager;

/**
 * @author Alexander Ocheretyanyy
 * @since 29-01-2021
 * @version 1.0
 */
public class NavigationPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3079910462033816949L;
	
	private final PuzzleFrame slave;
	private final JButton solveButton = new JButton();
	private final JButton clearButton = new JButton();
	private final JButton openFileButton = new JButton();
	
	private final Dimension dimension = new Dimension(160, 50);
	private final Color backgroundColor = Color.WHITE;
	private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
	private final Font font = new Font("Arial", Font.BOLD, 20);
	
	private final String solve = "Solve puzzle";
	private final String clear = "Clear board"; 
	private final String openFile = "Open file";
	private final String inputFileType = "Sudoku files";
	private final String inputFileExtension = "sud";
	
	protected NavigationPanel(PuzzleFrame slave) {
		this.slave = slave;
		
		initButton(solveButton, solve);
		initButton(clearButton, clear);
		initButton(openFileButton, openFile);
		
		add(solveButton);
		add(clearButton);
		add(openFileButton);
	}
	
	private void initButton(JButton button, String label) {
		button.setPreferredSize(dimension);
		button.setBackground(backgroundColor);
		button.setBorder(border);
		button.setFont(font);
		button.setText(label);
		button.setFocusable(false);
		button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton pressedButton = (JButton)e.getSource();
			SudokuSolverManager defaultManager = SudokuSolverManager.getDefaultManager();

			if(pressedButton.getText().equals(solve)) {
				defaultManager.solve();
			}
			else if(pressedButton.getText().equals(clear)) {
				defaultManager.clearPuzzleAndBoard();
			}
			else if(pressedButton.getText().equals(openFile)) {
				offerToLoadFile();
			}
		}
	}
	
	private void offerToLoadFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(inputFileType, inputFileExtension);
		fileChooser.setFileFilter(filter);
		
		int returnValue = fileChooser.showOpenDialog(slave);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			SudokuSolverManager defaultManager = SudokuSolverManager.getDefaultManager();
			defaultManager.openFile(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
}

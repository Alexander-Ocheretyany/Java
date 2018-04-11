package com.ocheretyany.alexander;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class is created to handle the action when a user pastes some code into a program. It re-applies a filter
 * to the whole document so as to display it correctly. 
 * @author Alexander Ocheretyany
 */
public class PasteAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private Action action;

	public PasteAction(Action action) {
		this.action = action;
	}

	public void actionPerformed(ActionEvent e) {
		action.actionPerformed(e);
		TextEditor.getCurrentTextPanel().reFilter();
	}
	
}

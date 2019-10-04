/**
 * This class is used to create a model for main list of items that are being downloaded.
 */

package com.ocheretyany.alexander;

import javax.swing.DefaultListModel;

public class Panel {
	private DefaultListModel<String> model;
	
	Panel(){
		model = null;
	}
	
	public void setPanel(Data a){
		if(model == null){
			model = new DefaultListModel<String>();
		}
		model.addElement(a.getName());
	}
	
	public DefaultListModel<String> getModel(){
		return model;
	}
	
	public void deleteFromModel(Data a){
		
		String nameOfPiece = a.getName();
		
		for(int i = 0; i < model.size(); i++){
			if(model.getElementAt(i).equals(nameOfPiece)){
				model.removeElementAt(i);
				i = model.size();
			}
		}
	}
}

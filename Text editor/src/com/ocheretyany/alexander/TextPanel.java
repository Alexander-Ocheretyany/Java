package com.ocheretyany.alexander;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.AbstractDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * The class extends JPanel and is used to create a panel in which a user can edit
 * their code
 * @author Alexander Ocheretyany
 */
public class TextPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextPane textArea; // Text area for code
	private JScrollPane scroll; // Scroll panel to make the text area scrollable
	private ArrayList<String> dictionary; // Contains all keywords of the chosen language
	Thread filtering;
	boolean terminate;
	
	/**
	 * Creates a panel with a special document filter
	 */
	public TextPanel() {
		
		textArea = new JTextPane(); // Create a text area
		scroll = new JScrollPane(textArea); // Set up scroll bars
		setLayout(new BorderLayout()); // Layout of the panel
		add(scroll, BorderLayout.CENTER); // Add text area and scroll bars to the panel
		Action action = textArea.getActionMap().get("paste-from-clipboard");
		textArea.getActionMap().put("paste-from-clipboard", new PasteAction(action));
		
		HTMLEditorKit editorKit = new HTMLEditorKit();
		textArea.setEditorKit(editorKit);
		textArea.setDocument(editorKit.createDefaultDocument());
		textArea.setContentType("text/plain");
		textArea.setCaretPosition(0);
		dictionary = TextEditor.getCurrentDictionary();
		((AbstractDocument) textArea.getDocument()).setDocumentFilter(new myDocumentFilter());
		scroll.invalidate();
		scroll.validate();
		scroll.repaint();
		TextEditor.setCurrentTextPanel(this);
		
		terminate = false;
	}
	
	/**
	 * Creates a panel for a given as the argument file
	 * @param f file
	 */
	public TextPanel(File f) {
		this(); // Call the default (with no arguments) constructor
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String s = null;
			while(reader.ready()) {
				if(s == null) {
					s = reader.readLine();
				} else {
					s += "\n";
					s = s + reader.readLine();
				}
			}
			textArea.setText(s);
			reader.close();
		} catch (IOException e) {}
		
	}
	
	/**
	 * Returns text from the text area
	 * @return text from the text area
	 */
	public String getText() {
		return textArea.getText();
	}
	
	/**
	 * Inner class which is used to create a filter to filter code. It finds
	 * all matches of the keywords of a chosen language in a code and highlights
	 * them.
	 */
	private final class myDocumentFilter extends DocumentFilter
	{
        private final StyledDocument styledDocument = textArea.getStyledDocument();
        private final StyleContext styleContext = StyleContext.getDefaultStyleContext();
        private final AttributeSet greenAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.GREEN);
        private final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

	    // Use a regular expression to find the words you are looking for
	    Pattern pattern = buildPattern();
	    //Pattern pattern_2 = Pattern.compile("//.*/n"); // In-line comments
	    
	    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
	        super.insertString(fb, offset, text, attributeSet);
	        handleTextChanged();
	    }

	    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
	        super.remove(fb, offset, length);
	        handleTextChanged();
	    }

	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
	        super.replace(fb, offset, length, text, attributeSet);
	        handleTextChanged();
	    }

	    /**
	     * Runs all updates later, not during the event notification.
	     */
	    private void handleTextChanged()
	    {
	    	filtering = new Thread(new Runnable() {
	            public void run() {
	                updateTextStyles();
	            }
	    	});
	    	
	    	filtering.start();
	    }

	    /**
	     * Builds the regular expression that looks for the whole word
	     * of each word that you wish to find.
	     * The "\\b" is the beginning or end of a word boundary.
	     * The "|" is a regex "or" operator.
	     */
	    private Pattern buildPattern()
	    {
	        StringBuilder sb = new StringBuilder();
	        dictionary = TextEditor.getCurrentDictionary();
	        
	        for (String token : dictionary) {
	            sb.append("\\b"); // Start of word boundary
	            sb.append(token);
	            sb.append("\\b|"); // End of word boundary and an or for the next word
	        }
	        
	        if (sb.length() > 0) {
	            sb.deleteCharAt(sb.length() - 1); // Remove the trailing "|"
	        }
	        
	        Pattern p = Pattern.compile(sb.toString());
	        
	        return p;
	    }
	    
	    /**
	     * Updates text style
	     */
	    private void updateTextStyles()
	    {
	        // Clear existing styles
	        styledDocument.setCharacterAttributes(0, textArea.getText().length(), blackAttributeSet, true);

	        // Look for tokens and highlight them
	        Matcher matcher = pattern.matcher(textArea.getText());

	        while (matcher.find() && !terminate) {
	            // Change the color of recognized tokens
	        	String text = textArea.getText();
	        	boolean multiComment = false;
	        	boolean comment = false;
	        	int start = matcher.start();
	        	
	        	text.charAt(0);
	        	// Check for a multiline comment
	        	for(int i = start - 1; i >= 0; --i) {
	        		if(i > 1 && text.charAt(i) == '/' && text.charAt(i - 1) == '*') {
	        			multiComment = false;
	        			break;
	        		}
	        		
	        		if((i > 0 && text.charAt(i) == '*' && text.charAt(i - 1) == '/') || (i > 1 && text.charAt(i) == '*' && text.charAt(i - 1) == '*' && text.charAt(i - 2) == '/')) {
	        			multiComment = true;
	        			break;
	        		}
	        	}
	        	
	        	// Check for single-line comment
	        	if(!multiComment) {
		        	for(int i = start - 1; i >= 0; --i) {
		        		if(text.charAt(i) == '\n' || i == 0) {
		        			comment = false;
		        			break;
		        		} else if(i > 1 && text.charAt(i) == '/' && text.charAt(i - 1) == '/') {
		        			comment = true;
		        			break;
		        		}
		        	}
	        	}
	        	
	        	if(!multiComment && !comment) {
	        		styledDocument.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), greenAttributeSet, false);
	        	}
	        }
	        	        
	        pattern = buildPattern();
	    }
	}
	
	public void reFilter() {
		terminate = true;
		((AbstractDocument) textArea.getDocument()).setDocumentFilter(new myDocumentFilter());
		terminate = false;
	}
}

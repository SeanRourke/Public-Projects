package database;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// A custom filter for Swing text components that only allows numeric characters and limits the length of the input
class NumberFilter extends DocumentFilter {
    
    // Number of characters allowed.
    private int length = 0;
    
    // Constructor to set the maximum length for the input
    public NumberFilter(int length) {
        this.length = length;
    }
    
    // Called when text is being inserted into the document
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws BadLocationException {
        // Check if the input string contains only numeric characters
        if (isNumeric(string)) {
            // If a maximum length has been set and inserting the new text would exceed it, don't insert anything
            if (this.length > 0 && fb.getDocument().getLength() + string.length() > this.length) {
                return;
            }
            // If the input is valid, insert it into the document
            super.insertString(fb, offset, string, attr);
        }
    }
    
    // Called when existing text is being replaced in the document
    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {
        // Check if the replacement text contains only numeric characters
        if (isNumeric(text)) {
            // If a maximum length has been set and replacing the text would result in a document that is too long, don't do anything
            if (this.length > 0 && fb.getDocument().getLength() + text.length() - length > this.length) {
                return;
            }
            // If the input is valid, replace the existing text in the document with it
            super.replace(fb, offset, length, text, attrs);
        }
    }
    
    // Helper method to check if a string contains only numeric characters
    private boolean isNumeric(String text) {
        if (text == null) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

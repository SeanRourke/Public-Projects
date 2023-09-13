package database;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// A custom filter for Swing text components that only allows numeric characters and limits the length of the input
class DecimalFilter extends DocumentFilter {
    
    // Number of characters allowed.
    private int length = 0;
    
    // Constructor to set the maximum length for the input
    public DecimalFilter(int length) {
        this.length = length;
    }
    
    // Called when text is being inserted into the document
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);
        if (isNumeric(sb.toString())) {
            if (this.length > 0 && sb.length() > this.length) {
                return;
            }
            super.insertString(fb, offset, string, attr);
        }
    }
    
    // Called when existing text is being replaced in the document
    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);
        if (isNumeric(sb.toString())) {
            if (this.length > 0 && sb.length() > this.length) {
                return;
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }
    // Helper method to check if a string contains only numeric characters
    // only allow one decimal point
    private boolean isNumeric(String text) {
        if (text == null) {
            return false;
        }
        int numDecimals = 0;
        for (int iCount = 0; iCount < text.length(); iCount++) {
            char c = text.charAt(iCount);
            if (!Character.isDigit(c)) {
                if (c == '.') {
                    numDecimals++;
                    if (numDecimals > 1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
package de.weltraumschaf.caythe.frontend;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class Source {
    public static final char EOL = '\n';
    public static final char EOF = (char) 0;

    private BufferedReader reader;
    private String line;
    private int lineNum;
    private int currentPos;

    public Source(BufferedReader reader) throws IOException {
        this.reader = reader;
        lineNum     = 0;
        currentPos  = -2;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getLineNum() {
        return lineNum;
    }

    public char currentChar() throws Exception {
        // First time?
        if (-2 == currentPos) {
            readLine();
            return nextChar();
        }
        // At end of file?
        else if (null == line) {
            return EOF;
        }
        // At end of line?
        else if ( (-1 == currentPos) || (line.length() == currentPos) ){
            return EOL;
        }
        // Need to read the next line?
        else if (currentPos > line.length()) {
            readLine();
            return nextChar();
        }
        // REturn th character at the current position.
        else {
            return line.charAt(currentPos);
        }
    }

    public char nextChar() throws Exception {
        ++currentPos;
        return currentChar();
    }

    public char peekChar() throws Exception {
        currentChar();

        if (null == line) {
            return EOF;
        }

        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    private void readLine() throws IOException {
        line       = reader.readLine(); // Null when at end of the source.
        currentPos = -1;

        if (null != line) {
            ++lineNum;
        }
    }

    public void close() throws Exception {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}

package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.intermediate.Code;
import de.weltraumschaf.caythe.intermediate.CodeKey;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.SymbolTableEntry;
import de.weltraumschaf.caythe.intermediate.codeimpl.CodeNodeImpl;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class ParseTreePrinter {

    private static final int INDENT_WIDTH = 4;
    private static final int LINE_WIDTH   = 80;

    private PrintStream ps;      // output print stream
    private int length;          // output line length
    private String indent;       // indent spaces
    private String indentation;  // indentation of a line
    private StringBuilder line;  // output line

    /**
     * Constructor
     * @param ps the output print stream.
     */
    public ParseTreePrinter(PrintStream ps) {
        this.ps          = ps;
        this.length      = 0;
        this.indentation = "";
        this.line        = new StringBuilder();

        // The indent is INDENT_WIDTH spaces.
        this.indent = "";

        for (int i = 0; i < INDENT_WIDTH; ++i) {
            this.indent += " ";
        }
    }

    /**
     * Print the intermediate code as a parse tree.
     * @param iCode the intermediate code.
     */
    public void print(Code iCode) {
        ps.println("\n===== INTERMEDIATE CODE =====\n");
        printNode((CodeNodeImpl) iCode.getRoot());
        printLine();
    }

    /**
     * Print a parse tree node.
     * @param node the parse tree node.
     */
    private void printNode(CodeNodeImpl node) {
        // Opening tag.
        append(indentation);
        append("<" + node.toString());

        printAttributes(node);
        printTypeSpec(node);

        ArrayList<CodeNode> childNodes = node.getChildren();

        // Print the node's children followed by the closing tag.
        if (( childNodes != null ) && ( childNodes.size() > 0 )) {
            append(">");
            printLine();

            printChildNodes(childNodes);
            append(indentation);
            append("</" + node.toString() + ">");
        } // No children: Close off the tag.
        else {
            append(" ");
            append("/>");
        }

        printLine();
    }

    /**
     * Print a parse tree node's attributes.
     * @param node the parse tree node.
     */
    private void printAttributes(CodeNodeImpl node) {
        String saveIndentation = indentation;
        indentation += indent;

        Set<Map.Entry<CodeKey, Object>> attributes = node.entrySet();
        Iterator<Map.Entry<CodeKey, Object>> it = attributes.iterator();

        // Iterate to print each attribute.
        while (it.hasNext()) {
            Map.Entry<CodeKey, Object> attribute = it.next();
            printAttribute(attribute.getKey().toString(), attribute.getValue());
        }

        indentation = saveIndentation;
    }

    /**
     * Print a node attribute as key="value".
     * @param keyString the key string.
     * @param value the value.
     */
    private void printAttribute(String keyString, Object value) {
        // If the value is a symbol table entry, use the identifier's name.
        // Else just use the value string.
        boolean isSymTabEntry = value instanceof SymbolTableEntry;
        String valueString = isSymTabEntry ?
                             ( (SymbolTableEntry) value ).getName() :
                             value.toString();

        String text = keyString.toLowerCase() + "=\"" + valueString + "\"";
        append(" ");
        append(text);

        // Include an identifier's nesting level.
        if (isSymTabEntry) {
            int level = ( (SymbolTableEntry) value ).getSymbolTable().getNestingLevel();
            printAttribute("LEVEL", level);
        }
    }

    /**
     * Print a parse tree node's child nodes.
     * @param childNodes the array list of child nodes.
     */
    private void printChildNodes(ArrayList<CodeNode> childNodes) {
        String saveIndentation = indentation;
        indentation += indent;

        for (CodeNode child : childNodes) {
            printNode((CodeNodeImpl) child);
        }

        indentation = saveIndentation;
    }

    /**
     * Print a parse tree node's type specification.
     * @param node the parse tree node.
     */
    private void printTypeSpec(CodeNodeImpl node) {
    }

    /**
     * Append text to the output line.
     * @param text the text to append.
     */
    private void append(String text) {
        int textLength = text.length();
        boolean lineBreak = false;

        // Wrap lines that are too long.
        if (length + textLength > LINE_WIDTH) {
            printLine();
            line.append(indentation);
            length = indentation.length();
            lineBreak = true;
        }

        // Append the text.
        if (!( lineBreak && text.equals(" ") )) {
            line.append(text);
            length += textLength;
        }
    }

    /**
     * Print an output line.
     */
    private void printLine() {
        if (length > 0) {
            ps.println(line);
            line.setLength(0);
            length = 0;
        }
    }
}

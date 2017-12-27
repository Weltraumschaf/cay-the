package de.weltraumschaf.caythe.tools.ide.idea.parsing;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 */
public interface CayTheTokenTypes {
  IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
  IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

  IElementType END_OF_LINE_COMMENT = new CayTheElementType("END_OF_LINE_COMMENT");
  IElementType KEY_CHARACTERS = new CayTheElementType("KEY_CHARACTERS");
  IElementType VALUE_CHARACTERS = new CayTheElementType("VALUE_CHARACTERS");
  IElementType KEY_VALUE_SEPARATOR = new CayTheElementType("KEY_VALUE_SEPARATOR");

  TokenSet COMMENTS = TokenSet.create(END_OF_LINE_COMMENT);
  TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
}

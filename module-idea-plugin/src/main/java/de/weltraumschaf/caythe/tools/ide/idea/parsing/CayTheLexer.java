package de.weltraumschaf.caythe.tools.ide.idea.parsing;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 */
public class CayTheLexer extends FlexAdapter {
  public CayTheLexer() {
//    super(new _CayTheLexer((Reader)null));
    super(null);
  }
}

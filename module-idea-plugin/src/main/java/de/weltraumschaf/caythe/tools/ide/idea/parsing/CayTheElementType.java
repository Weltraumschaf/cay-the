package de.weltraumschaf.caythe.tools.ide.idea.parsing;

import com.intellij.psi.tree.IElementType;
import de.weltraumschaf.caythe.tools.ide.idea.CayTheLanguage;
import org.jetbrains.annotations.NonNls;

/**
 */
public class CayTheElementType extends IElementType {
  public CayTheElementType(@NonNls String debugName) {
    super(debugName, CayTheLanguage.INSTANCE);
  }

  @SuppressWarnings({"HardCodedStringLiteral"})
  public String toString() {
    return "CayThe:" + super.toString();
  }
}

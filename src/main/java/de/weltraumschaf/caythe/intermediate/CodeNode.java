package de.weltraumschaf.caythe.intermediate;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public interface CodeNode {

    public CodeNodeType getType();

    public CodeNode getParent();

    public CodeNode addChild(CodeNode node);

    public ArrayList<CodeNode> getChildren();

    public void setAttribute(CodeKey key, Object value);

    public Object getAttribute(CodeKey key);

    public CodeNode copy();

    public void setTypeSpecification(TypeSpecification type);

    public TypeSpecification getTypeSpecification();

    /**
     * This is part of HashMap which extens the implementation..
     *
     * @todo Remove this.
     * 
     * @return
     */
    public Set<Map.Entry<CodeKey, Object>> entrySet();
}

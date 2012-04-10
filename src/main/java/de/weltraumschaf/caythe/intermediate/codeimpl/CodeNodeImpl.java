package de.weltraumschaf.caythe.intermediate.codeimpl;

import de.weltraumschaf.caythe.intermediate.*;
import java.util.*;

/**
 *
 * @todo USe composition instead of inheritance for the HashMap.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CodeNodeImpl extends HashMap<CodeKey, Object> implements CodeNode {

    private CodeNodeType type;
    private CodeNode parent;
    private ArrayList<CodeNode> children;
    private TypeSpecification typeSpecification;

    public CodeNodeImpl(CodeNodeType type) {
        this.type     = type;
        this.parent   = null;
        this.children = new ArrayList<CodeNode>();
    }

    @Override
    public TypeSpecification getTypeSpecification() {
        return typeSpecification;
    }

    @Override
    public void setTypeSpecification(TypeSpecification type) {
        typeSpecification = type;
    }

    @Override
    public CodeNode addChild(CodeNode node) {
        if (node != null) {
            children.add(node);
            ((CodeNodeImpl) node).parent = this;
        }

        return node;
    }

    @Override
    public CodeNode copy() {
        // Create a copy with the same type.
        CodeNodeImpl copy = (CodeNodeImpl) CodeFactory.createCodeNode(type);

        Set<Map.Entry<CodeKey, Object>> attributes = entrySet();
        Iterator<Map.Entry<CodeKey, Object>> it = attributes.iterator();

        // Copy attributes
        while (it.hasNext()) {
            Map.Entry<CodeKey, Object> attribute = it.next();
            copy.put(attribute.getKey(), attribute.getValue());
        }

        return copy;
    }

    @Override
    public Object getAttribute(CodeKey key) {
        return get(key);
    }

    @Override
    public ArrayList<CodeNode> getChildren() {
        return children;
    }

    @Override
    public CodeNode getParent() {
        return parent;
    }

    @Override
    public CodeNodeType getType() {
        return type;
    }

    @Override
    public void setAttribute(CodeKey key, Object value) {
        put(key, value);
    }

    @Override
    public String toString() {
        return type.toString();
    }

}

package de.weltraumschaf.caythe.intermediate.codeimpl;

import de.weltraumschaf.caythe.intermediate.CodeFactory;
import de.weltraumschaf.caythe.intermediate.CodeKey;
import de.weltraumschaf.caythe.intermediate.CodeNode;
import de.weltraumschaf.caythe.intermediate.CodeNodeType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @license http://www.weltraumschaf.de/the-beer-ware-license.txt THE BEER-WARE LICENSE
 */
public class CodeNodeImpl extends HashMap<CodeKey, Object> implements CodeNode {

    private CodeNodeType type;             // node type
    private CodeNode parent;               // parent node
    private ArrayList<CodeNode> children;  // children array list

    public CodeNodeImpl(CodeNodeType type) {
        this.type     = type;
        this.parent   = null;
        this.children = new ArrayList<CodeNode>();
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
        CodeNodeImpl copy = (CodeNodeImpl) CodeFactory.createICodeNode(type);

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

}

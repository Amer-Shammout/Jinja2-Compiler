package AST.template.jinja.expr;

import AST.template.jinja.JinjaNode;

public abstract class JinjaExpr extends JinjaNode {

    public JinjaExpr(String nodeName, int lineNumber) {
        super(nodeName, lineNumber);
    }
}

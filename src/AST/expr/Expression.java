package AST.expr;

import AST.ASTNode;

public abstract class Expression extends ASTNode {

    public Expression(String nodeName, int lineNumber) {
        super(nodeName, lineNumber);
    }
}
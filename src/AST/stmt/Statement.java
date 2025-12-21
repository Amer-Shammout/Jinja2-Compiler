package AST.stmt;

import AST.ASTNode;

public abstract class Statement extends ASTNode {

    public Statement(String nodeName, int lineNumber) {
        super(nodeName, lineNumber);
    }
}

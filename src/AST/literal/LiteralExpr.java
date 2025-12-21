package AST.literal;

import AST.expr.Expression;

public abstract class LiteralExpr extends Expression {

    public LiteralExpr(String nodeName, int lineNumber) {
        super(nodeName, lineNumber);
    }
}

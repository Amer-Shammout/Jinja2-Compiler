package AST.expr;

import AST.ASTNode;
import java.util.List;

public class ParenExpr extends Expression {

    private Expression expr;

    public ParenExpr(Expression expr, int line) {
        super("ParenExpr", line);
        this.expr = expr;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(expr);
    }
}

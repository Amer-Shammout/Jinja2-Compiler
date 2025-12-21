package AST.stmt;

import AST.ASTNode;
import AST.expr.Expression;

import java.util.ArrayList;
import java.util.List;

public class DelStmt extends Statement {

    private List<Expression> targets;

    public DelStmt(List<Expression> targets, int line) {
        super("DelStmt", line);
        this.targets = targets;
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(targets);
    }
}

package AST.expr;

import java.util.List;
import AST.ASTNode;

public class AttributeExpr extends Expression {
    private final Expression base;
    private final String attribute;

    public AttributeExpr(Expression base, String attribute, int line) {
        super("AttributeExpr", line);
        this.base = base;
        this.attribute = attribute;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(base);
    }

    public Expression getBase() { return base; }
    public String getAttribute() { return attribute; }
}


//obj.x
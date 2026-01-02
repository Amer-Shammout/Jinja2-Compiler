package AST.template.jinja.expr;

import AST.ASTNode;

import java.util.List;

public class JinjaAttrExpr extends JinjaExpr {

    private final JinjaExpr target;
    private final String attribute;

    public JinjaAttrExpr(JinjaExpr target, String attribute, int lineNumber) {
        super("JinjaAttrExpr", lineNumber);
        this.target = target;
        this.attribute = attribute;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(target);
    }

    @Override
    public String toString() {
        return "JinjaAttr ." + attribute + " (line " + lineNumber + ")";
    }
}

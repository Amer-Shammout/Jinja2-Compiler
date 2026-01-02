package AST.template.jinja.expr;

public class JinjaIdentifierExpr extends JinjaExpr {

    private final String name;

    public JinjaIdentifierExpr(String name, int lineNumber) {
        super("JinjaIdentifierExpr", lineNumber);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "JinjaIdentifier \"" + name + "\" (line " + lineNumber + ")";
    }
}

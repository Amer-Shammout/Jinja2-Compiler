package AST.template.jinja.expr;

public class JinjaNumberLiteralExpr extends JinjaExpr {

    private final String text;

    public JinjaNumberLiteralExpr(String text, int lineNumber) {
        super("JinjaNumberLiteralExpr", lineNumber);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "JinjaNumber " + text + " (line " + lineNumber + ")";
    }
}

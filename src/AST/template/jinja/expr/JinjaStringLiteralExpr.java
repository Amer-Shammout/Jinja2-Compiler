package AST.template.jinja.expr;

public class JinjaStringLiteralExpr extends JinjaExpr {

    private final String rawText;

    public JinjaStringLiteralExpr(String rawText, int lineNumber) {
        super("JinjaStringLiteralExpr", lineNumber);
        this.rawText = rawText;
    }

    public String getRawText() {
        return rawText;
    }

    @Override
    public String toString() {
        return "JinjaString " + rawText + " (line " + lineNumber + ")";
    }
}

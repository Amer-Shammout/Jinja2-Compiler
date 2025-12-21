package AST.literal;

public class BooleanLiteralExpr extends LiteralExpr {

    private boolean value;

    public BooleanLiteralExpr(boolean value, int lineNumber) {
        super("BooleanLiteralExpr", lineNumber);
        this.value = value;
    }
}

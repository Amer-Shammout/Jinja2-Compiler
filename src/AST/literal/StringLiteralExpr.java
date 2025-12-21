package AST.literal;

public class StringLiteralExpr extends LiteralExpr {

    private String value;

    public StringLiteralExpr(String value, int lineNumber) {
        super("StringLiteralExpr", lineNumber);
        this.value = value;
    }
}

package AST.literal;

public class NumberLiteralExpr extends LiteralExpr {

    private String value;

    public NumberLiteralExpr(String value, int lineNumber) {
        super("NumberLiteralExpr", lineNumber);
        this.value = value;
    }

}

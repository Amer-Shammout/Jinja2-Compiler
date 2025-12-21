package AST.stmt;

import java.util.List;

public class GlobalStmt extends Statement {

    private List<String> names;

    public GlobalStmt(List<String> names, int line) {
        super("GlobalStmt", line);
        this.names = names;
    }
}

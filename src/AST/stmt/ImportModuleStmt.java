package AST.stmt;

import java.util.List;

public class ImportModuleStmt extends Statement {

    private List<String> modules;

    public ImportModuleStmt(List<String> modules, int line) {
        super("ImportModuleStmt", line);
        this.modules = modules;
    }
}

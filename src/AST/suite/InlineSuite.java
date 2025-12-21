package AST.suite;

import AST.ASTNode;
import AST.stmt.Statement;

import java.util.List;

public class InlineSuite extends Suite {

    private Statement statement;

    public InlineSuite(Statement statement, int lineNumber) {
        super("InlineSuite", lineNumber);
        this.statement = statement;
    }

    @Override
    public List<ASTNode> getChildren() {
        return List.of(statement);
    }

}

package AST.suite;

import AST.ASTNode;
import AST.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public class BlockSuite extends Suite {

    private List<Statement> statements;

    public BlockSuite(List<Statement> statements, int lineNumber) {
        super("BlockSuite", lineNumber);
        this.statements = statements;
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(statements);
    }
}

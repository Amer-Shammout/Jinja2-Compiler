package AST;

import AST.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public class Program extends ASTNode {

    private List<Statement> statements;

    public Program(List<Statement> statements, int lineNumber) {
        super("Program", lineNumber);
        this.statements = statements;
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(statements);
    }
}

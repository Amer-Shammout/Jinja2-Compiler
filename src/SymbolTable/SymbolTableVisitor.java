package SymbolTable;

import AST.ASTNode;
import AST.Program;
import AST.expr.*;
import AST.stmt.*;
import AST.suite.*;
import java.util.List;

public class SymbolTableVisitor {

    private final FlaskSymbolTable symbolTable;

    public SymbolTableVisitor() {
        this.symbolTable = new FlaskSymbolTable();
        addPredefinedFlaskSymbols();
    }

    public FlaskSymbolTable getSymbolTable() {
        return symbolTable;
    }

    // Entry point
    public void visitProgram(Program program) {
        visitStatements(program.getChildren());
    }

    public void build(Program program) {
        visitProgram(program);
    }


    private void addPredefinedFlaskSymbols() {
        String[] predefined = {
                "Flask", "render_template", "request", "redirect", "url_for"
        };
        for (String name : predefined) {
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                    name,
                    FlaskSymbolTable.SymbolType.VARIABLE,
                    -1
            ));
        }
    }

    private void visitStatements(List<ASTNode> statements) {
        for (ASTNode stmt : statements)
            visitStatement(stmt);
    }

    private void visitStatement(ASTNode stmt) {
        if (stmt instanceof AssignmentStmt) visitAssignment((AssignmentStmt) stmt);
        else if (stmt instanceof AssignmentChainStmt) visitAssignmentChain((AssignmentChainStmt) stmt);
        else if (stmt instanceof FunctionDefStmt) visitFunction((FunctionDefStmt) stmt);
        else if (stmt instanceof IfStmt) visitIf((IfStmt) stmt);
        else if (stmt instanceof WhileStmt) visitWhile((WhileStmt) stmt);
        else if (stmt instanceof ForStmt) visitFor((ForStmt) stmt);
        else if (stmt instanceof ReturnStmt) visitReturn((ReturnStmt) stmt);
        else if (stmt instanceof Suite) visitSuite((Suite) stmt);
        else if (stmt instanceof ClassDefStmt) visitClass((ClassDefStmt) stmt);
        else if (stmt instanceof DecoratedStmt) visitDecorated((DecoratedStmt) stmt);
        else if (stmt instanceof PassStmt) {} // do nothing
    }

    private void visitAssignment(AssignmentStmt stmt) {
        ASTNode target = stmt.getChildren().get(0);
        if (target instanceof IdentifierExpr)
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                    ((IdentifierExpr) target).getName(),
                    FlaskSymbolTable.SymbolType.VARIABLE,
                    target.getLineNumber()
            ));
        for (ASTNode child : stmt.getChildren())
            visitExpression(child);
    }

    private void visitAssignmentChain(AssignmentChainStmt stmt) {
        List<ASTNode> children = stmt.getChildren();
        int n = children.size();
        for (int i = 0; i < n - 1; i++) {
            ASTNode target = children.get(i);
            if (target instanceof IdentifierExpr)
                symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                        ((IdentifierExpr) target).getName(),
                        FlaskSymbolTable.SymbolType.VARIABLE,
                        target.getLineNumber()
                ));
        }
        visitExpression(children.get(n - 1));
    }

    private void visitFunction(FunctionDefStmt stmt) {
        symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                stmt.getName(),
                FlaskSymbolTable.SymbolType.FUNCTION,
                stmt.getLineNumber()
        ));

        symbolTable.enterScope(stmt.getName());

        for (String param : stmt.getParameters())
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                    param,
                    FlaskSymbolTable.SymbolType.PARAMETER,
                    stmt.getLineNumber()
            ));

        visitSuite((Suite) stmt.getChildren().get(0));

        symbolTable.exitScope();
    }

    private void visitClass(ClassDefStmt stmt) {
        symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                stmt.getName(),
                FlaskSymbolTable.SymbolType.VARIABLE,
                stmt.getLineNumber()
        ));

        symbolTable.enterScope("class:" + stmt.getName());

        visitSuite(stmt.getBody());

        symbolTable.exitScope();
    }

    private void visitDecorated(DecoratedStmt stmt) {
        visitStatement(stmt.getChildren().get(stmt.getChildren().size() - 1));
    }

    private void visitIf(IfStmt stmt) {
        visitExpression(stmt.getChildren().get(0));

        symbolTable.enterScope("if");
        visitSuite((Suite) stmt.getChildren().get(1));
        symbolTable.exitScope();

        int i = 2;
        while (i < stmt.getChildren().size()) {
            ASTNode node = stmt.getChildren().get(i);
            if (node instanceof Suite) {
                symbolTable.enterScope("elif");
                visitSuite((Suite) node);
                symbolTable.exitScope();
            } else visitExpression(node);
            i++;
        }

        ASTNode last = stmt.getChildren().get(stmt.getChildren().size() - 1);
        if (last instanceof Suite) {
            symbolTable.enterScope("else");
            visitSuite((Suite) last);
            symbolTable.exitScope();
        }
    }

    private void visitWhile(WhileStmt stmt) {
        visitExpression(stmt.getChildren().get(0));
        symbolTable.enterScope("while");
        visitSuite((Suite) stmt.getChildren().get(1));
        symbolTable.exitScope();
    }

    private void visitFor(ForStmt stmt) {
        ASTNode iterator = stmt.getChildren().get(0);
        ASTNode iterable = stmt.getChildren().get(1);
        ASTNode body = stmt.getChildren().get(2);

        if (iterator instanceof IdentifierExpr)
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(
                    ((IdentifierExpr) iterator).getName(),
                    FlaskSymbolTable.SymbolType.VARIABLE,
                    iterator.getLineNumber()
            ));

        visitExpression(iterable);

        symbolTable.enterScope("for");
        visitSuite((Suite) body);
        symbolTable.exitScope();
    }

    private void visitReturn(ReturnStmt stmt) {
        visitExpression(stmt.getChildren().get(0));
    }

    private void visitSuite(Suite suite) {
        if (suite != null)
            visitStatements(suite.getChildren());
    }

    private void visitExpression(ASTNode expr) {
        if (expr instanceof IdentifierExpr) {
            FlaskSymbolTable.Symbol s = symbolTable.lookup(((IdentifierExpr) expr).getName());
            if (s == null)
                System.err.println("Warning: Undefined symbol '" + ((IdentifierExpr) expr).getName() +
                        "' at line " + expr.getLineNumber());
        } else if (expr instanceof CallExpr) {
            visitExpression(((CallExpr) expr).getFunction());
            for (ASTNode arg : ((CallExpr) expr).getArguments())
                visitExpression(arg);
        } else if (expr instanceof AttributeExpr) {
            visitExpression(((AttributeExpr) expr).getBase());
        }
    }
}

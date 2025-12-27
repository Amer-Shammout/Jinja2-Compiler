//package SymbolTable;
//
//import AST.Program;
//import AST.ASTNode;
//import AST.expr.IdentifierExpr;
//import AST.expr.CallExpr;
//import AST.stmt.*;
//import AST.suite.*;
//
//import java.util.List;
//
///**
// * SymbolTableVisitor يقوم بزيارة AST ويملأ FlaskSymbolTable
// */
//public class SymbolTableVisitor {
//
//    private final FlaskSymbolTable symbolTable;
//
//    public SymbolTableVisitor() {
//        this.symbolTable = new FlaskSymbolTable();
//    }
//
//    public FlaskSymbolTable getSymbolTable() {
//        return symbolTable;
//    }
//
//    // البداية من Program (global scope)
//    public void visitProgram(Program program) {
//        visitStatements(program.getChildren());
//    }
//
//    // زيارة قائمة statements
//    private void visitStatements(List<ASTNode> statements) {
//        for (ASTNode stmt : statements) {
//            visitStatement(stmt);
//        }
//    }
//
//    // زيارة statement حسب نوعه
//    private void visitStatement(ASTNode stmt) {
//        if (stmt instanceof AssignmentStmt) {
//            visitAssignment((AssignmentStmt) stmt);
//        } else if (stmt instanceof AssignmentChainStmt) {
//            visitAssignmentChain((AssignmentChainStmt) stmt);
//        } else if (stmt instanceof FunctionDefStmt) {
//            visitFunction((FunctionDefStmt) stmt);
//        } else if (stmt instanceof IfStmt) {
//            visitIf((IfStmt) stmt);
//        } else if (stmt instanceof WhileStmt) {
//            visitWhile((WhileStmt) stmt);
//        } else if (stmt instanceof ForStmt) {
//            visitFor((ForStmt) stmt);
//        } else if (stmt instanceof ReturnStmt) {
//            visitReturn((ReturnStmt) stmt);
//        } else if (stmt instanceof Suite) {
//            visitSuite((Suite) stmt);
//        } else {
//            System.err.println("Warning: Unknown statement type " + stmt.getNodeName());
//        }
//    }
//
//    // AssignmentStmt → إضافة target كـ variable
//    private void visitAssignment(AssignmentStmt stmt) {
//        if (stmt.getChildren().size() >= 1) {
//            ASTNode target = stmt.getChildren().get(0);
//            if (target instanceof IdentifierExpr) {
//                String name = ((IdentifierExpr) target).getName();
//                symbolTable.addSymbol(new Symbol(name, Symbol.SymbolType.VARIABLE, target.getLineNumber()));
//            }
//        }
//        // زيارة القيمة (RHS)
//        for (ASTNode child : stmt.getChildren()) {
//            visitExpression(child);
//        }
//    }
//
//    // AssignmentChainStmt → إضافة كل targets كـ variables
//    private void visitAssignmentChain(AssignmentChainStmt stmt) {
//        List<ASTNode> children = stmt.getChildren();
//        int n = children.size();
//        for (int i = 0; i < n - 1; i++) { // آخر واحد RHS
//            ASTNode target = children.get(i);
//            if (target instanceof IdentifierExpr) {
//                String name = ((IdentifierExpr) target).getName();
//                symbolTable.addSymbol(new Symbol(name, Symbol.SymbolType.VARIABLE, target.getLineNumber()));
//            }
//        }
//        // زيارة RHS
//        visitExpression(children.get(n - 1));
//    }
//
//    // FunctionDefStmt → إضافة function + parameters + body scope
//    private void visitFunction(FunctionDefStmt stmt) {
//        // إضافة function إلى current scope
//        symbolTable.addSymbol(new Symbol(stmt.getName(), Symbol.SymbolType.FUNCTION, stmt.getLineNumber()));
//
//        // إنشاء scope جديد للدالة
//        symbolTable.enterScope(stmt.getName());
//
//        // إضافة parameters كـ symbols
//        for (String param : stmt.getParameters()) {
//            symbolTable.addSymbol(new Symbol(param, Symbol.SymbolType.PARAMETER, stmt.getLineNumber()));
//        }
//
//        // زيارة body
//        visitSuite((Suite) stmt.getChildren().get(0));
//
//        symbolTable.exitScope();
//    }
//
//    // IfStmt → كل block suite يدخل scope جديد
//    private void visitIf(IfStmt stmt) {
//        // زيارة condition
//        visitExpression(stmt.getChildren().get(0));
//
//        // thenSuite
//        symbolTable.enterScope("if_then");
//        visitSuite((Suite) stmt.getChildren().get(1));
//        symbolTable.exitScope();
//
//        // elifs
//        int i = 2;
//        while (i < stmt.getChildren().size()) {
//            ASTNode node = stmt.getChildren().get(i);
//            if (node instanceof Suite) {
//                symbolTable.enterScope("elif");
//                visitSuite((Suite) node);
//                symbolTable.exitScope();
//            } else {
//                visitExpression(node); // condition
//            }
//            i++;
//        }
//
//        // elseSuite → عادة آخر child إذا موجود suite
//        ASTNode lastChild = stmt.getChildren().get(stmt.getChildren().size() - 1);
//        if (lastChild instanceof Suite) {
//            symbolTable.enterScope("else");
//            visitSuite((Suite) lastChild);
//            symbolTable.exitScope();
//        }
//    }
//
//    // WhileStmt → scope للـ body
//    private void visitWhile(WhileStmt stmt) {
//        visitExpression(stmt.getChildren().get(0)); // condition
//        symbolTable.enterScope("while");
//        visitSuite((Suite) stmt.getChildren().get(1));
//        symbolTable.exitScope();
//    }
//
//    // ForStmt → scope للـ body + iterator variable
//    private void visitFor(ForStmt stmt) {
//        ASTNode iterator = stmt.getChildren().get(0);
//        ASTNode iterable = stmt.getChildren().get(1);
//        ASTNode body = stmt.getChildren().get(2);
//
//        // إضافة iterator كـ variable
//        if (iterator instanceof IdentifierExpr) {
//            symbolTable.addSymbol(new Symbol(((IdentifierExpr) iterator).getName(), Symbol.SymbolType.VARIABLE, iterator.getLineNumber()));
//        }
//
//        visitExpression(iterable); // iterable
//
//        symbolTable.enterScope("for_body");
//        visitSuite((Suite) body);
//        symbolTable.exitScope();
//    }
//
//    // ReturnStmt → زيارة value
//    private void visitReturn(ReturnStmt stmt) {
//        visitExpression(stmt.getChildren().get(0));
//    }
//
//    // Suite → زيارة statements
//    private void visitSuite(Suite suite) {
//        visitStatements(suite.getChildren());
//    }
//
//    // زيارة Expression
//    private void visitExpression(ASTNode expr) {
//        if (expr instanceof IdentifierExpr) {
//            // استخدام variable / function → lookup
//            Symbol s = symbolTable.lookup(((IdentifierExpr) expr).getName());
//            if (s == null) {
//                System.err.println("Error: Undefined symbol '" + ((IdentifierExpr) expr).getName() + "' at line " + expr.getLineNumber());
//            }
//        } else if (expr instanceof CallExpr) {
//            // زيارة function expression
//            visitExpression(((CallExpr) expr).getFunction());
//            // زيارة arguments
//            for (ASTNode arg : ((CallExpr) expr).getArguments()) {
//                visitExpression(arg);
//            }
//        }
//    }
//}
//
//////////////////////////////////////////

package SymbolTable;

import AST.ASTNode;
import AST.Program;
import AST.expr.IdentifierExpr;
import AST.expr.CallExpr;
import AST.stmt.*;
import AST.suite.*;

import java.util.List;

public class SymbolTableVisitor {

    private final FlaskSymbolTable symbolTable;

    public SymbolTableVisitor() { this.symbolTable = new FlaskSymbolTable(); }

    public FlaskSymbolTable getSymbolTable() { return symbolTable; }

    public void visitProgram(Program program) {
        visitStatements(program.getChildren());
    }

    private void visitStatements(List<ASTNode> statements) {
        for (ASTNode stmt : statements) visitStatement(stmt);
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
    }

    private void visitAssignment(AssignmentStmt stmt) {
        ASTNode target = stmt.getChildren().get(0);
        if (target instanceof IdentifierExpr)
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(((IdentifierExpr) target).getName(),
                    FlaskSymbolTable.SymbolType.VARIABLE, target.getLineNumber()));
        for (ASTNode child : stmt.getChildren()) visitExpression(child);
    }

    private void visitAssignmentChain(AssignmentChainStmt stmt) {
        List<ASTNode> children = stmt.getChildren();
        int n = children.size();
        for (int i = 0; i < n - 1; i++) {
            ASTNode target = children.get(i);
            if (target instanceof IdentifierExpr)
                symbolTable.addSymbol(new FlaskSymbolTable.Symbol(((IdentifierExpr) target).getName(),
                        FlaskSymbolTable.SymbolType.VARIABLE, target.getLineNumber()));
        }
        visitExpression(children.get(n - 1));
    }

    private void visitFunction(FunctionDefStmt stmt) {
        symbolTable.addSymbol(new FlaskSymbolTable.Symbol(stmt.getName(),
                FlaskSymbolTable.SymbolType.FUNCTION, stmt.getLineNumber()));
        symbolTable.enterScope(stmt.getName());
        for (String param : stmt.getParameters())
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(param,
                    FlaskSymbolTable.SymbolType.PARAMETER, stmt.getLineNumber()));
        visitSuite((Suite) stmt.getChildren().get(0));
        symbolTable.exitScope();
    }

    private void visitIf(IfStmt stmt) {
        visitExpression(stmt.getChildren().get(0)); // condition
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
            symbolTable.addSymbol(new FlaskSymbolTable.Symbol(((IdentifierExpr) iterator).getName(),
                    FlaskSymbolTable.SymbolType.VARIABLE, iterator.getLineNumber()));
        visitExpression(iterable);
        symbolTable.enterScope("for");
        visitSuite((Suite) body);
        symbolTable.exitScope();
    }

    private void visitReturn(ReturnStmt stmt) { visitExpression(stmt.getChildren().get(0)); }

    private void visitSuite(Suite suite) { visitStatements(suite.getChildren()); }

    private void visitExpression(ASTNode expr) {
        if (expr instanceof IdentifierExpr) {
            FlaskSymbolTable.Symbol s = symbolTable.lookup(((IdentifierExpr) expr).getName());
            if (s == null)
                System.err.println("Error: Undefined symbol '" + ((IdentifierExpr) expr).getName() +
                        "' at line " + expr.getLineNumber());
        } else if (expr instanceof CallExpr) {
            visitExpression(((CallExpr) expr).getFunction());
            for (ASTNode arg : ((CallExpr) expr).getArguments()) visitExpression(arg);
        }
    }
}

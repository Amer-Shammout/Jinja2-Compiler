import AST.ASTGraphvizPrinter;
import AST.Program;
import AST.visitor.ProgramVisitor;
import SymbolTable.FlaskSymbolTable;
import antlr.FlaskLexer;
import antlr.FlaskParser;
import org.antlr.v4.runtime.*;
import java.nio.file.*;


public class Main {
    public static void main(String[] args) throws Exception {


        Path p = Paths.get("src/Tests/FinalTests/app.py").toAbsolutePath();
        CharStream input = CharStreams.fromPath(p);


        FlaskLexer lexer = new FlaskLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FlaskParser parser = new FlaskParser(tokens);

        ProgramVisitor programVisitor = new ProgramVisitor();
        Program program = programVisitor.visitProg(parser.prog());

        FlaskSymbolTable flaskSymbolTable = new FlaskSymbolTable();
        SymbolTable.SymbolTableVisitor stVisitor = new SymbolTable.SymbolTableVisitor();
        stVisitor.visitProgram(program);
        flaskSymbolTable = stVisitor.getSymbolTable();
        flaskSymbolTable.printSymbolTable();


        ASTGraphvizPrinter.print(program, "ast.dot");

        System.out.println("Symbol table and AST generated successfully!");
    }
}

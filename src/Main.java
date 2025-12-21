import AST.ASTGraphvizPrinter;
import AST.ASTPrinter;
import AST.Program;
import AST.visitor.ProgramVisitor;
import antlr.FlaskLexer;
import antlr.FlaskParser;
import org.antlr.v4.runtime.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Path p = Paths.get("src/Tests/Flask/python2.txt").toAbsolutePath();
        CharStream input = CharStreams.fromPath(p);

        FlaskLexer lexer = new FlaskLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FlaskParser parser = new FlaskParser(tokens);

        ProgramVisitor visitor = new ProgramVisitor();
        Program program = visitor.visit(parser.prog());

        ASTGraphvizPrinter.print(program, "ast.dot");

    }
}

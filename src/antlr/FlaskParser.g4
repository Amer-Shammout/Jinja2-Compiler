parser grammar FlaskParser;

@header {
package antlr;
}



options {
    tokenVocab = FlaskLexer;
}

// -----------------------------------------
// Program
// -----------------------------------------

prog
    : stmt* EOF
    ;

// -----------------------------------------
// Statements (order is VERY important)
// -----------------------------------------

stmt
    : assignment NEWLINE            # AssignmentStmt
    | return_stmt NEWLINE           # ReturnStmt
    | pass_stmt NEWLINE             # PassStmt
    | break_stmt NEWLINE            # BreakStmt
    | continue_stmt NEWLINE         # ContinueStmt
    | expr NEWLINE                  # ExprStmt
    | NEWLINE                       # EmptyLine
    ;

// -----------------------------------------
// Simple Statements (Level 2)
// -----------------------------------------

assignment
    : IDENTIFIER ASSIGN expr
    ;

return_stmt
    : RETURN expr
    ;

pass_stmt
    : PASS
    ;

break_stmt
    : BREAK
    ;

continue_stmt
    : CONTINUE
    ;

// -----------------------------------------
// Expressions (Level 1)
// -----------------------------------------

expr
    : expr (PLUS | MINUS) term      # AddSub
    | term                          # ToTerm
    ;

term
    : term (STAR | DIV) factor      # MulDiv
    | factor                        # ToFactor
    ;

factor
    : atom                          # ToAtom
    ;

atom
    : INT                           # IntAtom
    | FLOAT                         # FloatAtom
    | SCIENTIFIC                    # SciAtom
    | IDENTIFIER                    # IdAtom
    | LPAREN expr RPAREN            # ParenExpr
    ;

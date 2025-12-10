parser grammar FlaskParser;

@header {
package antlr;
}


options {
    tokenVocab = FlaskLexer;
}

// -------------------------
//   Level 1: Expressions
// -------------------------

prog
    : stmt* EOF
    ;

stmt
    : expr NEWLINE                # ExprStatement
    | NEWLINE                     # EmptyLine
    ;

expr
    : expr (PLUS | MINUS) term    # AddSub
    | term                        # ToTerm
    ;

term
    : term (STAR | DIV) factor    # MulDiv
    | factor                      # ToFactor
    ;

factor
    : atom                        # ToAtom
    ;

atom
    : INT                         # IntAtom
    | FLOAT                       # FloatAtom
    | IDENTIFIER                  # IdAtom
    | LPAREN expr RPAREN          # ParenExpr
    ;

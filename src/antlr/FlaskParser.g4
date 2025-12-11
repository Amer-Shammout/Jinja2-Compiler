parser grammar FlaskParser;

@header {
package antlr;
}

options {
    tokenVocab = FlaskLexer;
}

// ======================================================
// PROGRAM
// ======================================================

prog
    : stmt* EOF
    ;

// ======================================================
// STATEMENTS
// ======================================================

stmt
    : simple_stmt NEWLINE              # SimpleStmtLine
    | compound_stmt                    # CompoundStmt
    | NEWLINE                          # EmptyLine
    ;

// ======================================================
// SIMPLE STATEMENTS
// ======================================================

simple_stmt
    : assignment
    | return_stmt
    | pass_stmt
    | break_stmt
    | continue_stmt
    | expr
    ;

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

// ======================================================
// COMPOUND STATEMENTS
// ======================================================

compound_stmt
    : if_stmt                          # IfStmt
    | while_stmt                       # WhileStmt
    | for_stmt                         # ForStmt
    | func_def                         # FuncDefStmt
    | class_def                        # ClassDefStmt
    ;



if_stmt
    : IF expr COLON suite
      (ELIF expr COLON suite)*
      (ELSE COLON suite)?
    ;



while_stmt
    : WHILE expr COLON suite
    ;



for_stmt
    : FOR IDENTIFIER IN expr COLON suite
    ;



func_def
    : DEF IDENTIFIER LPAREN parameters? RPAREN COLON suite
    ;

parameters
    : IDENTIFIER (COMMA IDENTIFIER)*
    ;



class_def
    : CLASS IDENTIFIER (LPAREN IDENTIFIER RPAREN)? COLON suite
    ;



suite
    : simple_stmt                     # InlineSuite
    | NEWLINE INDENT stmt+ DEDENT     # BlockSuite
    ;

// ======================================================
// EXPRESSIONS
// ======================================================

expr
    : expr OR expr                                 # OrExpr
    | expr AND expr                                # AndExpr
    | expr (LT | GT | LE | GE | EQ | NE) expr      # CompareExpr
    | expr (PLUS | MINUS) expr                     # AddSubExpr
    | expr (STAR | DIV) expr                       # MulDivExpr
    | NOT expr                                     # NotExpr
    | (PLUS | MINUS) expr                          # UnaryPMExpr
    | primary                                      # PrimaryExpr
    ;

// ======================================================
// ATOM
// ======================================================

atom
    : literal                                      # LiteralAtom
    | IDENTIFIER                                   # IdAtom
    | LPAREN expr RPAREN                           # ParenExpr
    ;


// ======================================================
// PRIMARY EXPRESSIONS
// ======================================================

primary
    : atom trailer*                         # PrimaryRoot
    ;

trailer
    : LPAREN arguments? RPAREN              # CallTrailer
    | DOT IDENTIFIER                        # AttrTrailer
    | LBRACK expr RBRACK                    # IndexTrailer
    ;

arguments
    : expr (COMMA expr)*                    # ArgList
    ;



// ======================================================
// LITERALS
// ======================================================

literal
    : number_literal                               # NumberLiteral
    | string_literal                               # StringLiteral
    | boolean_literal                              # BooleanLiteral
    | NONE                                         # NoneLiteral
    ;

number_literal
    : INT                                          # IntNumber
    | FLOAT                                        # FloatNumber
    | SCIENTIFIC                                   # SciNumber
    ;

string_literal
    : TRIPLE_STRING                                # TripleString
    | SINGLE_STRING                                # SingleString
    | DOUBLE_STRING                                # DoubleString
    ;

boolean_literal
    : TRUE                                         # TrueLiteral
    | FALSE                                        # FalseLiteral
    ;

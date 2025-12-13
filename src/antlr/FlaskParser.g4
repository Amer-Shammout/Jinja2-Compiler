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
    | del_stmt
    | global_stmt
    | import_stmt
    | expr
    ;

// ------------------------------------------------------
// ASSIGNMENT (supports chaining on LHS + multiple assignment)
// ------------------------------------------------------

assignment
    : assign_target (ASSIGN assign_target)* ASSIGN expr
    ;

// LHS target: identifier + (.name | [expr])*
assign_target
    : IDENTIFIER ((DOT IDENTIFIER) | (LBRACK expr RBRACK))*
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

del_stmt
    : DEL expr (COMMA expr)*
    ;

global_stmt
    : GLOBAL IDENTIFIER (COMMA IDENTIFIER)*
    ;

import_stmt
    : IMPORT dotted_name (COMMA dotted_name)*                # ImportModule
    | FROM dotted_name IMPORT IDENTIFIER (COMMA IDENTIFIER)* # FromImport
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
    | decorated                        # DecoratedStmt
    ;

// ---------------- IF ----------------

if_stmt
    : IF expr COLON suite
      (ELIF expr COLON suite)*
      (ELSE COLON suite)?
    ;

// ---------------- WHILE ----------------

while_stmt
    : WHILE expr COLON suite
    ;

// ---------------- FOR ----------------

for_stmt
    : FOR IDENTIFIER IN expr COLON suite
    ;

// ---------------- FUNCTION DEF ----------------

func_def
    : DEF IDENTIFIER LPAREN parameters? RPAREN COLON suite
    ;

parameters
    : IDENTIFIER (COMMA IDENTIFIER)*
    ;

// ---------------- CLASS DEF ----------------

class_def
    : CLASS IDENTIFIER (LPAREN IDENTIFIER RPAREN)? COLON suite
    ;

// ---------------- DECORATORS ----------------

decorator
    : AT primary NEWLINE
    ;

decorated
    : decorator+ (func_def | class_def)
    ;

// ---------------- SUITE ----------------

suite
    : simple_stmt NEWLINE                       # InlineSuite
    | NEWLINE INDENT stmt+ DEDENT               # BlockSuite
    ;

// ======================================================
// EXPRESSIONS
// ======================================================

expr
    : expr OR expr                                 # OrExpr
    | expr AND expr                                # AndExpr
    | expr (LT | GT | LE | GE | EQ | NE) expr      # CompareExpr
    | expr IS (NOT)? expr                          # IsExpr
    | expr (PLUS | MINUS) expr                     # AddSubExpr
    | expr (STAR | DIV | INT_DIV) expr             # MulDivExpr
    | NOT expr                                     # NotExpr
    | (PLUS | MINUS) expr                          # UnaryPMExpr
    | primary                                      # PrimaryExpr
    | LAMBDA lambda_params? COLON expr             # LambdaExpr
    ;

lambda_params
    : IDENTIFIER (COMMA IDENTIFIER)*
    ;

// ======================================================
// PRIMARY EXPRESSIONS (Calls, Attributes, Indexing)
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
    : expr (COMMA expr)*
    ;

// ======================================================
// ATOM
// ======================================================

atom
    : literal                               # LiteralAtom
    | IDENTIFIER                            # IdAtom
    | LPAREN expr RPAREN                    # ParenExpr
    ;

// ======================================================
// LITERALS (SET ONLY)
// ======================================================

literal
    : number_literal                        # NumberLiteral
    | string_literal                        # StringLiteral
    | boolean_literal                       # BooleanLiteral
    | NONE                                  # NoneLiteral
    | set_literal                           # SetLiteral
    | list_literal                          # listLiteral
    ;

set_literal
    : LBRACE expr COMMA expr (COMMA expr)* COMMA? RBRACE
    ;

list_literal
    : LBRACK expr COMMA expr (COMMA expr)* COMMA? RBRACK
    ;

number_literal
    : INT                                   # IntNumber
    | FLOAT                                 # FloatNumber
    | SCIENTIFIC                            # SciNumber
    ;

string_literal
    : TRIPLE_STRING                         # TripleString
    | SINGLE_STRING                         # SingleString
    | DOUBLE_STRING                         # DoubleString
    ;

boolean_literal
    : TRUE                                  # TrueLiteral
    | FALSE                                 # FalseLiteral
    ;

// ======================================================
// HELPERS
// ======================================================

dotted_name
    : IDENTIFIER (DOT IDENTIFIER)*
    ;

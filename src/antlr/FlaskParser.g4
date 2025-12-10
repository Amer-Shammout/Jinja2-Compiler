parser grammar FlaskParser;

@header {
package antlr;
}

options {
    tokenVocab = FlaskLexer;
}

// -----------------------------------------
// PROGRAM
// -----------------------------------------

prog
    : stmt* EOF
    ;

// -----------------------------------------
// STATEMENTS
// -----------------------------------------

stmt
    : assignment NEWLINE              # AssignmentStmt
    | return_stmt NEWLINE             # ReturnStmt
    | pass_stmt NEWLINE               # PassStmt
    | break_stmt NEWLINE              # BreakStmt
    | continue_stmt NEWLINE           # ContinueStmt
    | expr NEWLINE                    # ExprStmt
    | compound_stmt                   # CompoundStmt
    | NEWLINE                         # EmptyLine
    ;

// -----------------------------------------
// SIMPLE STATEMENTS (Level 2)
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
// COMPOUND STATEMENTS (Level 3)
// -----------------------------------------

compound_stmt
    : if_stmt
    | while_stmt
    | for_stmt
    | func_def
    | class_def
    ;

// IF
if_stmt
    : IF expr COLON suite
      (ELIF expr COLON suite)*
      (ELSE COLON suite)?
    ;

// WHILE
while_stmt
    : WHILE expr COLON suite
    ;

// FOR
for_stmt
    : FOR IDENTIFIER IN expr COLON suite
    ;

// FUNCTION DEF
func_def
    : DEF IDENTIFIER LPAREN parameters? RPAREN COLON suite
    ;

parameters
    : IDENTIFIER (COMMA IDENTIFIER)*
    ;

// CLASS DEF
class_def
    : CLASS IDENTIFIER (LPAREN IDENTIFIER RPAREN)? COLON suite
    ;

// SUITE (BLOCKS)
suite
    : NEWLINE INDENT stmt+ DEDENT      # BlockSuite
    | stmt                              # InlineSuite
    ;

// -----------------------------------------
// EXPRESSIONS (Level 1+4)
// -----------------------------------------

// expr = logical_or
expr
    : logic_or
    ;

// logical OR
logic_or
    : logic_or OR logic_and             # OrExpr
    | logic_and                         # ToAnd
    ;

// logical AND
logic_and
    : logic_and AND logic_not           # AndExpr
    | logic_not                         # ToNot
    ;

// unary: NOT
logic_not
    : NOT logic_not                     # NotExpr
    | comparison                        # ToCompare
    ;

// comparisons
comparison
    : arith_expr (comp_op arith_expr)*  # Compare
    ;

// comparison operators
comp_op
    : LT
    | GT
    | LE
    | GE
    | EQ
    | NE
    ;

// Arithmetic +, -
arith_expr
    : arith_expr (PLUS | MINUS) term    # AddSub
    | term                              # ToTerm
    ;

// *, /
term
    : term (STAR | DIV) factor          # MulDiv
    | factor                            # ToFactor
    ;

// unary +/-
factor
    : (PLUS | MINUS) factor             # UnaryPM
    | atom                              # ToAtom
    ;

// ATOM
atom
    : INT                               # IntAtom
    | FLOAT                             # FloatAtom
    | SCIENTIFIC                        # SciAtom
    | IDENTIFIER                        # IdAtom
    | LPAREN expr RPAREN                # ParenExpr
    ;

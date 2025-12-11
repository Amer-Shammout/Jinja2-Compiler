lexer grammar TemplateLexer;

@header { package antlr; }

@members {
    boolean isTagName = false;
}


// ---------- DEFAULT MODE: outside tags (text, comments) ----------
// whitespace outside tags
HTML_WS : [ \t\r\n]+ -> skip ;

DOCTYPE
    : '<!' 'DOCTYPE' (~'>')* '>'
    ;


HTML_COMMENT
    : '<!--' .*? '-->' -> skip
    ;

// ---------------- JINJA2 BLOCKS (Default Mode) ----------------

// Jinja2 Comment
JINJA_COMMENT
    : '{#' .*? '#}' -> skip
    ;

// Jinja2 Expression Start:  {{ ... }}
JINJA_EXPR_START
    : '{{' -> pushMode(JINJA_EXPR_MODE)
    ;

// Jinja2 Statement Start:  {% ... %}
JINJA_STMT_START
    : '{%' -> pushMode(JINJA_STMT_MODE)
    ;

HTML_OPEN_TAG
    : '<' { isTagName = true; } -> pushMode(TAG_MODE)
    ;


// TEXT outside tags
HTML_TEXT
    : (~('<' | '{'))+ {   // تجاهل كل ما هو ليس < أو {
        String t = getText();
        if (t.trim().isEmpty()) skip();
        else setText(t.trim());
    }
    ;




// ---------------- TAG_MODE: inside < ... > ----------------
mode TAG_MODE;

HTML_CLOSE_TAG
    : '>' -> popMode
    ;

// self-closing "/>"
HTML_SLASH_CLOSE
    : '/>' -> popMode
    ;

// slash alone </element>
HTML_SLASH
    : '/'
    ;

// tag name
HTML_TAG_NAME
    : {isTagName}? [a-zA-Z][a-zA-Z0-9\-]* { isTagName = false; }
    ;

// attribute name
HTML_ATTR_NAME
    : [a-zA-Z_:][a-zA-Z0-9_:.-]*
    ;

// equals
HTML_EQUALS : '=' ;

// attribute value
HTML_STRING
    : '"' (~["\r\n])* '"'
    | '\'' (~['\r\n])* '\''
    ;

// whitespace inside tag (skip)
TAG_WS : [ \t\r\n]+ -> skip ;

// ---------------- JINJA_EXPR_MODE ----------------
mode JINJA_EXPR_MODE;

JINJA_EXPR_END
    : '}}' -> popMode
    ;

JINJA_EXPR_DOT : '.';


// identifiers
JINJA_ID
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

// Pipe for filters
JINJA_EXRR_PIPE : '|';

JINJA_EXPR_STRING
    : '"' (~["\r\n])* '"'
    | '\'' (~['\r\n])* '\''
    ;

// numbers
JINJA_NUMBER
    : '0' ('.' [0-9]+)?
    | [1-9] [0-9]* ('.' [0-9]+)?
    ;

// operators
JINJA_OP
    : '==' | '!=' | '>=' | '<=' | '>' | '<' | '+' | '-' | '*' | '/' | '%'
    ;

// whitespace inside jinja expression
JINJA_EXPR_WS : [ \t\r\n]+ -> skip;

// fallback
//JINJA_EXPR_TEXT
//    : ~[}]+
//    ;

// ---------------- JINJA_STMT_MODE ----------------
mode JINJA_STMT_MODE;

JINJA_STMT_END
    : '%}' -> popMode
    ;

JINJA_STMT_DOT : '.';

// keywords for statements
JINJA_FOR       : 'for';
JINJA_ENDFOR    : 'endfor';
JINJA_IF_STMT   : 'if';
JINJA_ELIF_STMT : 'elif';
JINJA_ELSE_STMT : 'else';
JINJA_ENDIF     : 'endif';
JINJA_IN        : 'in';
JINJA_BLOCK     : 'block';
JINJA_ENDBLOCK  : 'endblock';

// logical operators
JINJA_AND       : 'and';
JINJA_OR        : 'or';
JINJA_NOT       : 'not';

// identifiers
JINJA_STMT_ID
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

// numbers
JINJA_STMT_NUMBER
    : '0' ('.' [0-9]+)?
    | [1-9] [0-9]* ('.' [0-9]+)?
    ;

// Pipe for filters
JINJA_STMT_PIPE : '|';

JINJA_STMT_STRING
    : '"' (~["\r\n])* '"'
    | '\'' (~['\r\n])* '\''
    ;

// operators
JINJA_STMT_OP
    : '==' | '!=' | '>=' | '<=' | '>' | '<' | '+' | '-' | '*' | '/' | '%'
    ;

// whitespace inside jinja stmt
JINJA_STMT_WS : [ \t\r\n]+ -> skip;

// fallback
//JINJA_STMT_TEXT
//    : ~[%]+
//    ;
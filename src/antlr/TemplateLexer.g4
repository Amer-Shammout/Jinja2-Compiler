lexer grammar TemplateLexer;

@header { package antlr; }

@members {
    boolean isTagName = false;
}


// ---------- DEFAULT MODE: outside tags (text, comments) ----------
// whitespace outside tags
DOCTYPE
    : '<!' 'DOCTYPE' (~'>')* '>'
    ;

HTML_WS : [ \t\r\n]+ -> skip ;

HTML_COMMENT
    : '<!--' .*? '-->' -> skip
    ;

HTML_OPEN_TAG
    : '<' { isTagName = true; } -> pushMode(TAG_MODE)
    ;


// TEXT outside tags
HTML_TEXT
    : (~'<')+ {
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

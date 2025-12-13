parser grammar TemplateParser;

@header { package antlr; }

options {
    tokenVocab = TemplateLexer;
}

// ======================================================
// LEVEL 1: HTML TEXT ONLY
// ======================================================

template
    : html_document EOF
    ;

html_document
    : html_node*
    ;

// فقط نص خارج الوسوم
html_node
    : HTML_TEXT                 # HtmlTextNode
    ;

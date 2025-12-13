parser grammar TemplateParser;

@header { package antlr; }

options {
    tokenVocab = TemplateLexer;
}

// ======================================================
// LEVEL 1: HTML TEXT ONLYS
// LEVEL 2: HTML TAGS (NO ATTRIBUTES)
// ======================================================

// ---------------- ROOT ----------------

template
    : doctype? document EOF
    ;

// <!DOCTYPE html>
doctype
    : DOCTYPE
    ;

// ---------------- DOCUMENT ----------------

document
    : html_element+
    ;

// ---------------- ELEMENT ----------------

// <tag> content </tag>
html_element
    : open_tag html_content close_tag
    ;

// ---------------- CONTENT ----------------

// ما داخل التاغ
html_content
    : (html_element | HTML_TEXT)*
    ;

// ---------------- TAGS ----------------

open_tag
    : HTML_OPEN_TAG HTML_TAG_NAME HTML_CLOSE_TAG
    ;

close_tag
    : HTML_OPEN_TAG HTML_SLASH HTML_TAG_NAME HTML_CLOSE_TAG
    ;




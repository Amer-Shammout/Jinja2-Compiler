parser grammar TemplateParser;

@header { package antlr; }

options {
    tokenVocab = TemplateLexer;
}

// ======================================================
// LEVEL 1: HTML TEXT ONLYS
// LEVEL 2: HTML TAGS (NO ATTRIBUTES)
// HTML LEVEL 3: ATTRIBUTES + SELF-CLOSING
// ======================================================

// ---------------- ROOT ----------------

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

html_element
    : normal_element
    | self_closing_element
    ;

// <tag ...> content </tag>
normal_element
    : open_tag html_content close_tag
    ;

// <tag ... />
self_closing_element
    : HTML_OPEN_TAG HTML_TAG_NAME attribute* HTML_SLASH_CLOSE
    ;

// ---------------- CONTENT ----------------

html_content
    : (html_element | HTML_TEXT)*
    ;

// ---------------- TAGS ----------------

open_tag
    : HTML_OPEN_TAG HTML_TAG_NAME attribute* HTML_CLOSE_TAG
    ;

close_tag
    : HTML_OPEN_TAG HTML_SLASH HTML_TAG_NAME HTML_CLOSE_TAG
    ;

// ---------------- ATTRIBUTES ----------------

attribute
    : HTML_ATTR_NAME HTML_EQUALS HTML_STRING
    ;
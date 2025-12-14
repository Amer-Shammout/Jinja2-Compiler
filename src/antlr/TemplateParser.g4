



parser grammar TemplateParser;

@header { package antlr; }

options {
    tokenVocab = TemplateLexer;
}

// ======================================================
// ROOT
// ======================================================

template
    : doctype? document EOF                          # TemplateRoot
    ;

// <!DOCTYPE html>
doctype
    : DOCTYPE                                        # DoctypeDecl
    ;

// ======================================================
// LEVEL 1: HTML TEXT ONLYS
// LEVEL 2: HTML TAGS (NO ATTRIBUTES)
// HTML LEVEL 3: ATTRIBUTES + SELF-CLOSING
// ======================================================

document
    : html_element+                                  # HtmlDocument
    ;


html_element
    : style_element                                  # StyleHtmlElement
    | normal_element                                 # NormalHtmlElement
    | self_closing_element                           # SelfClosingHtmlElement
    ;


normal_element
    : open_tag html_content close_tag                # NormalElement
    ;


self_closing_element
    : HTML_OPEN_TAG HTML_TAG_NAME attribute* HTML_SLASH_CLOSE
                                                     # SelfClosingElement
    ;


html_content
    : (html_element | HTML_TEXT)*                    # HtmlContent
    ;


open_tag
    : HTML_OPEN_TAG HTML_TAG_NAME attribute* HTML_CLOSE_TAG
                                                     # OpenTag
    ;

close_tag
    : HTML_OPEN_TAG HTML_SLASH HTML_TAG_NAME HTML_CLOSE_TAG
                                                     # CloseTag
    ;


attribute
    : HTML_ATTR_NAME HTML_EQUALS HTML_STRING          # HtmlAttribute
    ;

// ======================================================
// CSS LEVEL 1 (BASIC STRUCTURE)
// ======================================================


style_element
    : style_open css_stylesheet CSS_STYLE_END         # StyleElement
    ;


style_open
    : HTML_OPEN_TAG t=HTML_TAG_NAME attribute* HTML_CLOSE_TAG
      { $t.getText().equalsIgnoreCase("style") }?     # StyleOpenTag
    ;



css_stylesheet
    : css_rule*                                      # CssStylesheet
    ;


css_rule
    : css_selector css_block                         # CssRule
    ;


css_selector
    : css_selector_item+                             # CssSelector
    ;

css_selector_item
    : CSS_UNIVERSAL                                  # UniversalSelector
    | CSS_IDENT                                      # TagSelector
    | CSS_HASH CSS_IDENT                             # IdSelector
    | CSS_DOT CSS_IDENT                              # ClassSelector
    | CSS_COLON CSS_IDENT                            # PseudoSelector
    ;


css_block
    : CSS_LBRACE css_declaration* CSS_RBRACE         # CssBlock
    ;


css_declaration
    : CSS_PROPERTY CSS_COLON_IN_BLOCK css_value CSS_SEMICOLON?
                                                     # CssDeclaration
    ;


css_value
    : CSS_VALUE                                      # CssValue
    ;

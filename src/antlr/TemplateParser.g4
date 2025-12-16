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
    : (jinja_expression | html_element | HTML_TEXT)*  # HtmlContent
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
    : HTML_ATTR_NAME HTML_EQUALS attribute_value      # HtmlAttribute
    ;

attribute_value
    : HTML_STRING                                    # HtmlStringValue
    ;

// ======================================================
// CSS LEVEL 1 (BASIC STRUCTURE)
// CSS LEVEL 2 (Selector Composition & Combinators)
// CSS LEVEL 3 (Multiple Values & Keywords)
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
    : css_selector_sequence
      (CSS_COMMA css_selector_sequence)*             # CssSelector
    ;

css_selector_sequence
    : css_compound_selector
      (explicit_combinator css_compound_selector)*   # SelectorSequence
    ;

css_compound_selector
    : css_type_selector? css_sub_selector*           # CompoundSelector
    ;

css_type_selector
    : CSS_UNIVERSAL                                  # UniversalTypeSelector
    | CSS_IDENT                                      # TagTypeSelector
    ;

css_sub_selector
    : CSS_HASH CSS_IDENT                             # IdSubSelector
    | CSS_DOT  CSS_IDENT                             # ClassSubSelector
    | CSS_COLON CSS_IDENT                            # PseudoSubSelector
    ;

explicit_combinator
    : CSS_GT                                        # ChildCombinator
    | CSS_PLUS                                      # AdjacentSiblingCombinator
    | CSS_TILDE                                     # GeneralSiblingCombinator
    ;

css_block
    : CSS_LBRACE css_declaration* CSS_RBRACE         # CssBlock
    ;

css_declaration
    : CSS_PROPERTY CSS_COLON_IN_BLOCK
      css_value_list
      CSS_SEMICOLON?                                 # CssDeclaration
    ;

css_value_list
    : css_space_value_list
      (css_comma_value_list)*                        # CssValueList
    ;

css_space_value_list
    : css_value_atom+                                # SpaceSeparatedValues
    ;

css_comma_value_list
    : css_value_separator
      css_space_value_list                           # CommaSeparatedValues
    ;

css_value_separator
    : CSS_COMMA_IN_BLCOK                             # CommaInBlock
    ;

// ======================================================
// CSS VALUE ATOMS (Jinja enabled here)
// ======================================================

css_value_atom
    : jinja_expression                               # CssJinjaValue
    | CSS_VALUE                                      # CssPrimitiveValue
    | css_function_call                              # CssFunctionValue
    ;




css_function_call
    : CSS_FUNCTION css_function_args? CSS_RPAREN     # CssFunctionCall
    ;

css_function_args
    : css_value_list                                 # CssFunctionArgs
    ;

// ======================================================
// LEVEL 3: JINJA2 EXPRESSIONS
// ======================================================

// Accept Jinja start token from any lexer mode
jinja_expr_start
    : JINJA_EXPR_START
    | JINJA_EXPR_START_IN_TAG
    | JINJA_EXPR_START_IN_CSS
    | JINJA_EXPR_START_IN_CSS_BLOCK
    ;

jinja_expression
    : jinja_expr_start jinja_expr JINJA_EXPR_END      # JinjaExpression
    ;

jinja_expr
    : jinja_term (JINJA_OP jinja_term)*               # JinjaBinaryExpr
    ;

jinja_term
    : jinja_atom jinja_postfix*                       # JinjaTerm
    ;

jinja_postfix
    : JINJA_EXPR_DOT JINJA_ID                         # JinjaMemberAccess
    | jinja_call                                      # JinjaCallPostfix
    | jinja_filter                                    # JinjaFilterPostfix
    ;

jinja_call
    : JINJA_EXPR_LPAREN jinja_arg_list? JINJA_EXPR_RPAREN
                                                     # JinjaCall
    ;

jinja_filter
    : JINJA_EXPR_PIPE JINJA_ID
      (JINJA_EXPR_LPAREN jinja_arg_list? JINJA_EXPR_RPAREN)? # JinjaFilter
    ;

jinja_arg_list
    : jinja_expr (JINJA_EXPR_COMMA jinja_expr)*       # JinjaArgList
    ;

jinja_atom
    : JINJA_ID                                        # JinjaIdAtom
    | JINJA_EXPR_STRING                               # JinjaStringAtom
    | JINJA_NUMBER                                    # JinjaNumberAtom
    | JINJA_EXPR_LPAREN jinja_expr JINJA_EXPR_RPAREN   # JinjaParenAtom
    ;

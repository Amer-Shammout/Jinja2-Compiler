



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


// ======================================================
// CSS STYLESHEET
// ======================================================

css_stylesheet
    : css_rule*                                      # CssStylesheet
    ;

css_rule
    : css_selector css_block                         # CssRule
    ;


// ======================================================
// CSS SELECTORS
// ======================================================

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


// ======================================================
// CSS COMBINATORS
// ======================================================

explicit_combinator
    : CSS_GT                                        # ChildCombinator
    | CSS_PLUS                                      # AdjacentSiblingCombinator
    | CSS_TILDE                                     # GeneralSiblingCombinator
    ;


// ======================================================
// CSS BLOCK
// ======================================================

css_block
    : CSS_LBRACE css_declaration* CSS_RBRACE         # CssBlock
    ;


// ======================================================
// CSS DECLARATIONS
// ======================================================

css_declaration
    : CSS_PROPERTY CSS_COLON_IN_BLOCK
      css_value_list
      CSS_SEMICOLON?                                 # CssDeclaration
    ;


// ======================================================
// CSS VALUES
// ======================================================


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
    :  CSS_COMMA_IN_BLCOK                            # CommaInBlock
    ;


// ======================================================
// CSS VALUE ATOMS
// ======================================================

css_value_atom
    : CSS_VALUE                                     # CssPrimitiveValue
    | css_function_call                              # CssFunctionValue
    ;


// ======================================================
// CSS FUNCTIONS
// ======================================================

css_function_call
    : CSS_FUNCTION css_function_args? CSS_RPAREN     # CssFunctionCall
    ;

css_function_args
    : css_value_list                                 # CssFunctionArgs
    ;

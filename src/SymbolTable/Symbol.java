package SymbolTable;

import java.util.List;

/**
 * Symbol يمثل أي اسم معرف في البرنامج:
 * - Variable
 * - Function
 * - Parameter
 */
public class Symbol {

    public enum SymbolType {
        VARIABLE,
        FUNCTION,
        PARAMETER
    }

    private final String name;         // اسم المتغير أو الدالة
    private final SymbolType type;     // نوع الرمز (Variable / Function / Parameter)
    private final int lineNumber;      // رقم السطر في التعريف
    private final List<String> parameters; // لو Function، قائمة الباراميترات

    // Constructor للمتغير أو الباراميتر
    public Symbol(String name, SymbolType type, int lineNumber) {
        this.name = name;
        this.type = type;
        this.lineNumber = lineNumber;
        this.parameters = null; // غير مطبق للمتغيرات/باراميترات
    }

    // Constructor للدوال (Function)
    public Symbol(String name, int lineNumber, List<String> parameters) {
        this.name = name;
        this.type = SymbolType.FUNCTION;
        this.lineNumber = lineNumber;
        this.parameters = parameters;
    }

    // Getters
    public String getName() { return name; }
    public SymbolType getType() { return type; }
    public int getLineNumber() { return lineNumber; }
    public List<String> getParameters() { return parameters; }

    @Override
    public String toString() {
        switch (type) {
            case VARIABLE:
                return "Variable: " + name + " (line " + lineNumber + ")";
            case PARAMETER:
                return "Parameter: " + name + " (line " + lineNumber + ")";
            case FUNCTION:
                return "Function: " + name + " (line " + lineNumber + "), params=" + parameters;
            default:
                return name;
        }
    }
}

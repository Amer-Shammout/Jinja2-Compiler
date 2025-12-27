package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Scope يمثل أي نطاق في البرنامج:
 * - global
 * - function
 * - block / loop / if / while
 */
public class Scope {

    private final String name;                    // اسم الـ scope (مثال: "global", "function foo")
    private final Scope parentScope;             // الـ parent scope (null إذا global)
    private final Map<String, Symbol> symbols;   // الرموز في هذا الـ scope
    private final List<Scope> childScopes;       // scopes فرعية

    // Constructor
    public Scope(String name, Scope parentScope) {
        this.name = name;
        this.parentScope = parentScope;
        this.symbols = new HashMap<>();
        this.childScopes = new ArrayList<>();
    }

    // Add symbol للـ scope الحالي
    public boolean addSymbol(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            // الرمز موجود مسبقًا في هذا scope → خطأ duplicate
            System.err.println("Error: Symbol '" + symbol.getName() + "' already defined in scope '" + name + "'");
            return false;
        }
        symbols.put(symbol.getName(), symbol);
        return true;
    }

    // Lookup → يبحث عن symbol في هذا scope أو parent scopes recursively
    public Symbol lookup(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        if (parentScope != null) return parentScope.lookup(name);
        return null; // غير موجود
    }

    // إنشاء scope فرعي
    public Scope createChildScope(String childName) {
        Scope child = new Scope(childName, this);
        childScopes.add(child);
        return child;
    }

    // Getters
    public String getName() { return name; }
    public Scope getParentScope() { return parentScope; }
    public List<Scope> getChildScopes() { return childScopes; }
    public Map<String, Symbol> getSymbols() { return symbols; }

    // Print symbols في هذا scope بشكل مرتب
    public void printScope(String indent) {
        System.out.println(indent + "Scope: " + name);
        for (Symbol s : symbols.values()) {
            System.out.println(indent + "  " + s);
        }
        for (Scope child : childScopes) {
            child.printScope(indent + "  ");
        }
    }
}

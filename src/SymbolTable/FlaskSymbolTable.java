//package SymbolTable;
//
///**
// * FlaskSymbolTable يمثل جدول الرموز الرئيسي للـ Flask AST
// * يحتوي على global scope و current scope أثناء الزيارة
// */
//public class FlaskSymbolTable {
//
//    private final Scope globalScope;    // الـ global scope
//    private Scope currentScope;         // الـ scope الحالي أثناء traversal
//
//    // Constructor → يبدأ بالـ global scope
//    public FlaskSymbolTable() {
//        this.globalScope = new Scope("global", null);
//        this.currentScope = globalScope;
//    }
//
//    // الدخول إلى scope جديد (مثلاً Function أو Block)
//    public void enterScope(String name) {
//        Scope child = currentScope.createChildScope(name);
//        currentScope = child;
//    }
//
//    // الخروج من الـ scope الحالي → العودة إلى parent
//    public void exitScope() {
//        if (currentScope.getParentScope() != null) {
//            currentScope = currentScope.getParentScope();
//        } else {
//            System.err.println("Warning: Already at global scope, cannot exit further");
//        }
//    }
//
//    // إضافة Symbol في الـ current scope
//    public boolean addSymbol(Symbol symbol) {
//        return currentScope.addSymbol(symbol);
//    }
//
//    // البحث عن Symbol بدءًا من current scope ثم parent scopes
//    public Symbol lookup(String name) {
//        return currentScope.lookup(name);
//    }
//
//    // Getter للـ global scope
//    public Scope getGlobalScope() { return globalScope; }
//
//    // Print كامل الـ Symbol Table
//    public void printTable() {
//        System.out.println("==== Flask Symbol Table ====");
//        globalScope.printScope("");
//        System.out.println("============================");
//    }
//
//    // Helper → الحصول على الـ scope الحالي
//    public Scope getCurrentScope() {
//        return currentScope;
//    }
//}

////////////////////////////////////

package SymbolTable;

        import java.util.*;

public class FlaskSymbolTable {

    public enum SymbolType { VARIABLE, FUNCTION, PARAMETER }

    public static class Symbol {
        private final String name;
        private final SymbolType type;
        private final String dataType;
        private final int lineNumber;

        public Symbol(String name, SymbolType type, int lineNumber) {
            this(name, type, "unknown", lineNumber);
        }

        public Symbol(String name, SymbolType type, String dataType, int lineNumber) {
            this.name = name;
            this.type = type;
            this.dataType = dataType;
            this.lineNumber = lineNumber;
        }

        public String getName() { return name; }
        public SymbolType getType() { return type; }
        public String getDataType() { return dataType; }
        public int getLineNumber() { return lineNumber; }

        @Override
        public String toString() {
            return name + " : " + type + " (" + dataType + ") [line " + lineNumber + "]";
        }
    }

    private static class Scope {
        private final String name;
        private final Map<String, Symbol> symbols = new LinkedHashMap<>();

        public Scope(String name) { this.name = name; }

        public boolean contains(String name) { return symbols.containsKey(name); }
        public void addSymbol(Symbol s) { symbols.put(s.getName(), s); }
        public Symbol lookup(String name) { return symbols.get(name); }
        public String getName() { return name; }
        public Collection<Symbol> getSymbols() { return symbols.values(); }
    }

    private final Deque<Scope> scopeStack = new ArrayDeque<>();
    private int scopeCounter = 0;

    public FlaskSymbolTable() { enterScope("global"); }

    public void enterScope(String baseName) {
        scopeCounter++;
        String uniqueName = baseName + "_" + scopeCounter;
        scopeStack.push(new Scope(uniqueName));
    }

    public void exitScope() {
        if (!scopeStack.isEmpty()) scopeStack.pop();
    }

    public void addSymbol(Symbol s) {
        Scope current = scopeStack.peek();
        if (current != null) {
            if (current.contains(s.getName())) {
                System.err.println("Warning: Symbol '" + s.getName() + "' already declared in scope " + current.getName());
            } else {
                current.addSymbol(s);
            }
        }
    }


    public Symbol lookup(String name) {
        for (Scope scope : scopeStack) {
            Symbol s = scope.lookup(name);
            if (s != null) return s;
        }
        return null;
    }

    public void printSymbolTable() {
        System.out.println("=== Symbol Table ===");
        for (Scope scope : scopeStack) {
            System.out.println("Scope: " + scope.getName());
            for (Symbol s : scope.getSymbols()) {

                int line = s.lineNumber >= 0 ? s.lineNumber : 0;
                System.out.printf("  %s : %s [line %d]%n",
                        s.name,
                        s.type,
                        line);
            }
        }
        System.out.println("===================");
    }


}

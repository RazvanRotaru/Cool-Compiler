package cool.structures;

public class TypeSymbol extends Symbol {
    public TypeSymbol(String name) {
        super(name);
    }
    
    // Symboluri aferente tipurilor, definite global
    public static final ClassSymbol INT   =  new ClassSymbol(SymbolTable.globals ,"Int");
    public static final ClassSymbol STRING = new ClassSymbol(SymbolTable.globals ,"String");
    public static final ClassSymbol BOOL  = new ClassSymbol(SymbolTable.globals ,"Bool");
    public static final ClassSymbol OBJECT  = new ClassSymbol(SymbolTable.globals ,"Object");
    public static final ClassSymbol SELF_TYPE  = new ClassSymbol("SELF_TYPE");
    public static final ClassSymbol IO  = new ClassSymbol("IO");
}

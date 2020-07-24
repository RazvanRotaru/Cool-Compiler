package cool.structures;

import java.util.*;

public class ClassSymbol extends IdSymbol implements Scope{
	
	public Map<String, Symbol> symbols = new LinkedHashMap<>();
	public Map<String, Symbol> methods = new LinkedHashMap<>();
	//protected Map<String, ClassSymbol> classSymbols = new LinkedHashMap<>();
    
    protected Scope parent;
    public Scope inherits;
//    protected Scope parentScope;
//	protected ClassSymbol parentClass;
    
	public ClassSymbol(Scope parentScope, String name) {
		super(name);
		// TODO Auto-generated constructor stub
//		this.parentScope = parentScope;
//		this.parentClass = parentClass;
		this.parent = parentScope;
		this.inherits = parentScope;
//		var selfType = new Symbol("SELF_TYPE");
//		selfType.add(selfType);
		
//		Symbol selfType = new Symbol("SELF_TYPE");
		
	//	symbols.put(selfType.getName(), selfType);
	}
	
	public ClassSymbol(Scope parentScope, String name, Scope inherits) {
		super(name);
		// TODO Auto-generated constructor stub
		this.parent = parentScope;
		this.inherits = inherits;

		

//		symbols.put(selfType.getName(), selfType);	
//		symbols.put(selfType.getName(), selfType);
	}

	public ClassSymbol(String name) {
		// TODO Auto-generated constructor stub
		super(name);

//		symbols.put(selfType.getName(), selfType);
		
//		symbols.put(selfType.getName(), selfType);
	}
	
	public void setType(Scope cls) {
		this.parent = cls;
		this.inherits = cls;
	}

	@Override
	public boolean add(Symbol sym) {
		// TODO Auto-generated method stub
		if (symbols.containsKey(sym.getName()))
            return false;
        
        symbols.put(sym.getName(), sym);
        
        return true;
	}
	
	public boolean addMethod(Symbol sym) {
		// TODO Auto-generated method stub
		if (symbols.containsKey(sym.getName()))
            return false;
        
        methods.put(sym.getName(), sym);
        
        return true;
	}

	@Override
	public boolean contains(Symbol sym) {
		if (symbols.containsKey(sym.getName()))
			return true;
		
		return false;
	}
	
	public boolean containsMethod(Symbol sym) {
		if (methods.containsKey(sym.getName()))
			return true;
		
		return false;
	}
	
	public boolean parentContains(Symbol sym) {
		if (parent != null) {
			if (parent.contains(sym))
				return true;
			return parent.parentContains(sym);
		}
		return false;
	}
	public Symbol localLookup(String str) {
		// TODO Auto-generated method stub
		var sym = symbols.get(str);
        
        if (sym != null)
            return sym;
		return null;
	}
	
	@Override
	public Symbol lookup(String str) {
		// TODO Auto-generated method stub
        var sym = symbols.get(str);

//		System.out.println("Checking scope: " + this.getName() + " for " + str);
        if (sym != null) {
//        	System.out.println("found " + str + " in " + this.getName());
        	return sym;
        }
        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null)
            return parent.lookup(str);
        
        return null;
	}
	
	public Symbol lookupMethod(String str) {
		// TODO Auto-generated method stub
		var sym = methods.get(str);
        
    	if (sym != null) {
//        	System.out.println("found " + str + " in " + this.getName());
        	return sym;
        }
        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
    	if (inherits == null)
    		return null;
    				
    	var inhSym = ((ClassSymbol) SymbolTable.globals).lookup(((Symbol) inherits).getName());
//		System.out.println("Will try scope: " + inhSym.getName() + " for " + str);
        if (inhSym != null)
            return ((ClassSymbol) inhSym).lookupMethod(str);
        
        return null;
	}
	
	public boolean checkCycle(String str) {
		// TODO Auto-generated method stub
//			
			if (inherits == null)
				return false;
			
			var sym = ((ClassSymbol) SymbolTable.globals).lookup(((Symbol) inherits).getName());

//			System.out.println("searching " + str + " in " + sym);
			// Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
	        // în domeniul de deasupra.
	        if (sym != null) {
	    		if (sym.toString().equals(str))
	    			return true;
	            return ((ClassSymbol) sym).checkCycle(str);
	        }
	        return false;
	}
	
	public boolean toRefresh() {
		if (parent != inherits)
			return true;
		return false;
	}
	
	public boolean inheritanceLookup(Symbol s) {
		// TODO Auto-generated method stub
        
		if (inherits == null)
			return false;
		
		var sym = SymbolTable.globals.lookup(((IdSymbol) inherits).getName());
//		System.out.println("searching " + s + " in " + sym);
//		System.out.println(((ClassSymbol) sym).symbols);
		// Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (sym != null) {
    		if (((ClassSymbol) sym).contains(s)) {
//    			System.out.println("found " + s.getName() + " in " + ((ClassSymbol) sym).getName());
    			return true;
    		}
//    		var inheritScope = SymbolTable.globals.lookup(((IdSymbol) inherits).getName());
//    		System.out.println("checking " + inheritScope + " with " + ((ClassSymbol)inheritScope).symbols);
            return ((ClassSymbol) sym).inheritanceLookup(s);
        }
        return false;
	}

	@Override
	public Scope getParent() {
		// TODO Auto-generated method stub
		return parent;
	}
	
	public Scope getScope() {
		// TODO Auto-generated method stub
		return parent;
	}
	
	public Map<String, Symbol> getFormals(Symbol sym){
//		System.out.println("getting formals for " + sym.getName() + " from " + this.getName());
		var symbol = lookupMethod(sym.getName());
		if (symbol != null) {
			return ((MethodSymbol) symbol).getFormals();
		}
		return null;
	}
	
	public Integer getFormalsNumber(Symbol sym){
//		System.out.println("getting formals for " + sym.getName() + " from " + this.getName());
		var symbol = lookupMethod(sym.getName());
		if (symbol != null) {
			return ((MethodSymbol) symbol).getFormalsNumber();
		}
		return null;
	}
	
	public boolean inherits(Symbol sym) {
		var name = sym.getName();
		
//		System.out.println("trying |" + getName() + "| with " + name);
//		System.out.println(getName() + " inherits " + inherits);
		if (name.equals("SELF_TYPE")) {
//			System.out.println("self type resolvse to " + ((ClassSymbol) sym).getParent());
			if (((ClassSymbol) sym).getParent() == this) {
//				System.out.println("e egal");
				return true;
			}
		}
		
		if (this.getName().equals(name))
			return true;
		
		if (inherits != null) {
			var inhScope = SymbolTable.globals.lookup(((IdSymbol) inherits).getName());
			if (inhScope != null) 
				return ((ClassSymbol) inhScope).inherits(sym);
		}
		return false;
	}

}

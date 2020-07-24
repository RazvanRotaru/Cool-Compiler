package cool.structures;

import java.util.*;

public class MethodSymbol extends IdSymbol implements Scope {
	protected Map<String, Symbol> symbols = new LinkedHashMap<>();
	private Map<String, Symbol> params = new LinkedHashMap<>();
	protected Scope parent;
	protected Integer paramsSize;
	
	public MethodSymbol(Scope parentScope, String name) {
		super(name);
		// TODO Auto-generated constructor stub
		this.paramsSize = 0;
		this.parent = parentScope;
	}
	
	@Override
	public boolean add(Symbol sym) {
		if (symbols.containsKey(sym.getName()))
			return false;
		symbols.put(sym.getName(), sym);
		return true;
	}
	
	public void countParam(Symbol sym) {
		paramsSize += 1;
	}
	
	public boolean add_param(Symbol sym) {
		paramsSize += 1;
		if (symbols.containsKey(sym.getName()))
			return false;
		params.put(sym.getName(), sym);
		symbols.put(sym.getName(), sym);
		return true;
	}
	
	@Override
	public Symbol lookup(String str) {
		// TODO Auto-generated method stub
		var sym = symbols.get(str);
        
//		System.out.println("");
//		System.out.println("Checking scope: " + this.getName() + " for " + str);
//		System.out.println("trying parent: " + parent);
        if (sym != null) {
//        	System.out.println("found " + str + " in " + this.getName());
        	return sym;
        }
        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null)
            return parent.lookup(str);
        
        System.out.println("didn't find anything");
        return null;
	}
	@Override
	public Scope getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public Map<String, Symbol> getFormals(){
		return params;
	}

	public Integer getFormalsNumber(){
		return paramsSize;
	}
	
	public void setType(Symbol sym) {
    	symbols.put(sym.getName(), sym);
    }
	
	@Override
	public boolean contains(Symbol sym) {
		// TODO Auto-generated method stub
		if (symbols.containsKey(sym.getName()))
			return true;
		
		return false;
	}

	@Override
	public boolean parentContains(Symbol sym) {
		// TODO Auto-generated method stub
		if (parent.contains(sym))
			return true;
		if (parent != null)
			return parent.parentContains(sym);
		
		return false;
	}
}
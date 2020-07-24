package cool.structures;

public interface Scope {
    public boolean add(Symbol sym);
    
    public Symbol lookup(String str);
    
    public Scope getParent();

	boolean contains(Symbol sym);

	public boolean parentContains(Symbol sym);
}

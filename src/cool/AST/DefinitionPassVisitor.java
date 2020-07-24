package cool.AST;
import cool.structures.*;

public class DefinitionPassVisitor implements ASTVisitor<Void>{
	Scope currentScope = null;

	@Override
	public Void visit(Id id) {
		// TODO Auto-generated method stub
		if (id.getToken().getText().equals("self")) {
			return null;
		}
		// Căutăm simbolul în domeniul curent.
        var symbol = (IdSymbol)currentScope.lookup(id.getToken().getText());
        
        id.setScope(currentScope);
        
//        System.out.println(id.getToken().getText());
        // Semnalăm eroare dacă nu există.
        if (id.getToken().getText().equals("self")) {
        	// s-ar putea sa trebuiasca sa fie SELF_TYPE(clasa_parinte)
        	symbol = (IdSymbol) ((IdSymbol) currentScope.getParent()).getType();
        }
//        System.out.println(symbol);
//        if (symbol == null) {
//            SymbolTable.error(id.ctx,
//                  id.token, id.getToken().getText() + " undefined");
//            return null;
//        }
        
        // Atașăm simbolul nodului din arbore.
        id.setSymbol(symbol);
		return null;
	}

	@Override
	public Void visit(Int intt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Type type) {
		// TODO Auto-generated method stub
//		var symbol = (IdSymbol)currentScope.lookup(type.getToken().getText());
//		System.out.println(symbol);
        return null;
	}

	@Override
	public Void visit(Bool bool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(If iff) {
		// TODO Auto-generated method stub
		iff.cond.accept(this);
        iff.thenBranch.accept(this);
        iff.elseBranch.accept(this);
		return null;
	}

	@Override
	public Void visit(StringLiteral string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Assign assign) {
		// TODO Auto-generated method stub
//		assign.id.accept(this);
////		System.out.println("expresia " + assign.id.getToken().getText() + " este " + assign.expr.getToken().getText());
//        var value = assign.expr.accept(this);
//        if (value == null) {
//        	SymbolTable.error(assign.expr.ctx,  assign.expr.getToken(),
//                    "Undefined identifier " + assign.expr.getToken().getText());
//        	return null;
//        }
		return null;
	}

	@Override
	public Void visit(MethodCall method) {
		// TODO Auto-generated method stub
		var id = method.methodName;
		
//		System.out.println("methoda " + id.getToken().getText() + " apaleata de "  + method.caller.toString());
        method.caller.accept(this);
        for (var arg: method.args) {
            arg.accept(this);
        }
        id.setScope(currentScope);
		return null;
	}

	@Override
	public Void visit(OwnMethodCall method) {
		// TODO Auto-generated method stub
		var id = method.methodName;
        for (var arg: method.args) {
            arg.accept(this);
        }
        id.setScope(currentScope);
		return null;
	}

	@Override
	public Void visit(While whilee) {
		// TODO Auto-generated method stub
		whilee.cond.accept(this);
        whilee.loopBranch.accept(this);
        return null;
	}

	@Override
	public Void visit(Block block) {
		// TODO Auto-generated method stub
		for (var v: block.content)
			v.accept(this);
		return null;
	}

	@Override
	public Void visit(LetIn letin) {
		// TODO Auto-generated method stub
		for (var v : letin.vars)
			v.accept(this);
		letin.body.accept(this);
		return null;
	}

	@Override
	public Void visit(Case casee) {
		// TODO Auto-generated method stub
//		casee.var.accept(this);
		for (var v : casee.cases)
			v.accept(this);
		return null;
	}

	@Override
	public Void visit(NewType type) {
		// TODO Auto-generated method stub
//		System.out.println("apelam new de " + type.type.getToken().getText());
		type.type.accept(this);
		return null;
	}

	@Override
	public Void visit(InBrackets ib) {
		// TODO Auto-generated method stub
		ib.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(Negate neg) {
		// TODO Auto-generated method stub
		neg.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(IsVoid isvoid) {
		// TODO Auto-generated method stub
		isvoid.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(LessThan lt) {
		// TODO Auto-generated method stub
		lt.left.accept(this);
		lt.right.accept(this);
		return null;
	}

	@Override
	public Void visit(LessEqual le) {
		// TODO Auto-generated method stub
		le.left.accept(this);
		le.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Equal eq) {
		// TODO Auto-generated method stub
		eq.left.accept(this);
		eq.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Not nott) {
		// TODO Auto-generated method stub
		nott.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(Plus plus) {
		// TODO Auto-generated method stub
		plus.left.accept(this);
		plus.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Minus minus) {
		// TODO Auto-generated method stub
		minus.left.accept(this);
		minus.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Mult mult) {
		// TODO Auto-generated method stub
		mult.left.accept(this);
		mult.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Div div) {
		// TODO Auto-generated method stub
		div.left.accept(this);
		div.right.accept(this);
		return null;
	}

	@Override
	public Void visit(Formal formal) {
		// TODO Auto-generated method stub
		// La definirea unei variabile, creăm un nou simbol.
        // Adăugăm simbolul în domeniul de vizibilitate curent.
        // Atașăm simbolul nodului din arbore si setăm scope-ul
        // pe variabila de tip Id, pentru a îl putea obține cu
        // getScope() în a doua trecere.
        var id   = formal.name;
        var type = formal.type;
        
        var symbol = new IdSymbol(id.getToken().getText());

        // Verificăm dacă parametrul deja există în scope-ul curent.
        if (! ((MethodSymbol)currentScope).add_param(symbol)) {
            SymbolTable.error(id.ctx, id.getToken(),
            		"Method " + ((MethodSymbol)currentScope).getName() + " of class " +
               			 ((ClassSymbol)currentScope.getParent()).getName() + " redefines formal parameter " + 
            				id.getToken().getText());
            return null;
        }

        if (id.getToken().getText().equals("self")) {
        	SymbolTable.error(id.ctx, id.getToken(),
        			"Method " + ((MethodSymbol)currentScope).getName() + " of class " +
        			 ((ClassSymbol)currentScope.getParent()).getName() + " has formal parameter with illegal name self");
        	return null;
        }
        
        if (type.getToken().getText().equals("SELF_TYPE")) {
        	SymbolTable.error(type.ctx, type.getToken(),
            		"Method " + ((MethodSymbol)currentScope).getName() + " of class " +
               			 ((ClassSymbol)currentScope.getParent()).getName() + " has formal parameter " + 
            				id.getToken().getText() + " with illegal type SELF_TYPE");
            return null;
        }
        
        id.setSymbol(symbol);
        id.setScope(currentScope);
        
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
        // Căutăm tipul variabilei.
		/*
		 * var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
		 * 
		 * // Semnalăm eroare dacă nu există. if (typeSymbol == null) {
		 * SymbolTable.error(type.ctx, id.getToken(), "formal " +
		 * id.getToken().getText() + " has undefined type " +
		 * type.getToken().getText()); return null; }
		 * 
		 * // Reținem informația de tip în cadrul simbolului aferent // variabilei
		 * symbol.setType(typeSymbol);
		 */
        
        symbol.setType(new ClassSymbol(type.getToken().getText()));
        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
	}

	@Override
	public Void visit(Atribute atr) {
		// TODO Auto-generated method stub
		// La definirea unei variabile, creăm un nou simbol.
        // Adăugăm simbolul în domeniul de vizibilitate curent.

        var id   = atr.id;
        var type = atr.type;
        
        if (id.getToken().getText().equals("self")) {
            SymbolTable.error(id.ctx, id.getToken(), 
            		"Class " + ((IdSymbol)currentScope).getName() +" has attribute with illegal name self");
            return null;        	
        }
        
        var symbol = new IdSymbol(id.getToken().getText());
       
        if (currentScope.contains(symbol)) {
            SymbolTable.error(id.ctx, id.getToken(), 
            		"Class " + ((IdSymbol)currentScope).getName() +" redefines attribute " + id.getToken().getText());
            return null;
        }
//        System.out.println("adding " + symbol + " to " + currentScope);
        currentScope.add(symbol);
//        System.out.println(((ClassSymbol)currentScope).symbols);
        // Atașăm simbolul nodului din arbore.
        id.setSymbol(symbol);
        
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
        // Căutăm tipul variabilei.
//        System.out.println("cautam tipul atribitului " + id.getToken().getText() + " din scopeul " + currentScope.toString() + " (" + currentScope.getClass() + ")");
		
        var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
		if (typeSymbol != null)
			symbol.setType(typeSymbol);
        /*
		 * 
		 * // Semnalăm eroare dacă nu există. if (typeSymbol == null) {
		 * SymbolTable.error(type.ctx, id.getToken(), "attribute " +
		 * id.getToken().getText() + " has undefined type " +
		 * type.getToken().getText()); return null; }
		 * 
		 * // Reținem informația de tip în cadrul simbolului aferent // variabilei
		 * symbol.setType(typeSymbol);
		 * 
		 * if (atr.expr != null) atr.expr.accept(this);
		 */
        
        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
	}

	@Override
	public Void visit(Method method) {
		// TODO Auto-generated method stub
		// Asemeni variabilelor globale, vom defini un nou simbol
        // pentru funcții. Acest nou FunctionSymbol va avea că părinte scope-ul
        // curent currentScope și va avea numele funcției.
        //
        // Nu uitați să updatati scope-ul curent înainte să fie parcurs corpul funcției,
        // și să îl restaurati la loc după ce acesta a fost parcurs.
        var id   = method.id;
        var type = method.type;
        
        var methodSymbol = new MethodSymbol(currentScope, id.getToken().getText());
        currentScope = methodSymbol;

        // Verificăm faptul că o metoda cu același nume nu a mai fost
        // definită până acum.
        if (((ClassSymbol) currentScope.getParent()).containsMethod(methodSymbol)) {
            SymbolTable.error(id.ctx, id.getToken(),
            		"Class " + ((ClassSymbol) currentScope.getParent()).getName() + " redefines method " + id.getToken().getText());
            currentScope = currentScope.getParent();
            return null;
        }
        
        ((ClassSymbol) currentScope.getParent()).addMethod(methodSymbol);
//        System.out.println("adaug metoda " + id.getToken().getText() + " la " + currentScope.getParent().toString());
        id.setSymbol(methodSymbol);
        id.setScope(currentScope);
        method.id = id;
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
		/*
		 * // Căutăm tipul funcției. var typeSymbol =
		 * (TypeSymbol)currentScope.lookup(type.getToken().getText());
		 * 
		 * // Semnalăm eroare dacă nu există. if (typeSymbol == null) {
		 * SymbolTable.error(type.ctx, id.getToken(), id.getToken().getText() +
		 * " has undefined return type " + type.getToken().getText()); return null; }
		 * 
		 * // Reținem informația de tip în cadrul simbolului aferent funcției.
		 * methodSymbol.setType(typeSymbol);
		 */
        var typeSymbol = new ClassSymbol(type.getToken().getText());
        methodSymbol.setType(typeSymbol);
        for (var formal: method.formals) {
            formal.accept(this);
        }

        method.body.accept(this);

        currentScope = currentScope.getParent();
        return null;
	}

	@Override
	public Void visit(ClassDef classDef) {
		// TODO Auto-generated method stub
		var id   = classDef.self_type;
        var type = classDef.base_type;
        var retScope = currentScope;
        // Verificam daca numele clasei este acceptat
        if (id.getToken().getText().equals("SELF_TYPE")) {	
        	SymbolTable.error(classDef.self_type.ctx, classDef.self_type.getToken(), 
        			"Class has illegal name SELF_TYPE");
//        	currentScope = currentScope.getParent();
            return null;
        }
        
        var classSymbol = new ClassSymbol(currentScope, id.getToken().getText());
        if (type != null && type.getToken() != null) {
        	var scope = currentScope.lookup(type.getToken().getText());
//        	System.out.println(scope);
        	if (scope == null) {
        		scope = new ClassSymbol(currentScope, type.getToken().getText());
        		classSymbol = new ClassSymbol(currentScope, id.getToken().getText(), (Scope) scope);
        	} else {
//        	System.out.println(scope);
        		classSymbol = new ClassSymbol((Scope) scope, id.getToken().getText(), (Scope) scope);
//        		((ClassSymbol) scope).add(classSymbol);
        	}
        } 
//        System.out.println("parent of " + classSymbol.getName() + " is " + classSymbol.getScope() + " inheriting " + classSymbol.inherits);
    	
        currentScope = classSymbol;

        
        // Verificăm faptul că o funcție cu același nume nu a mai fost
        // definită până acum.
//        System.out.println(currentScope.getParent());
//        System.out.println(((ClassSymbol)currentScope.getParent().getParent()).symbols);
 
        if (((ClassSymbol)currentScope).parentContains(classSymbol)) {
        	SymbolTable.error(classDef.self_type.ctx, classDef.self_type.getToken(), 
        			"Class " + classDef.self_type.getToken().getText() + " is redefined");
        	currentScope = retScope;
            return null;
        }
        while (currentScope != SymbolTable.globals) {
//	        System.out.println("adding " + classSymbol.getName() + " to " + currentScope);
	        currentScope.add(classSymbol);
	        currentScope = currentScope.getParent();
        }
//        System.out.println("adding " + classSymbol.getName() + " to " + currentScope);
        currentScope.add(classSymbol);
//        System.out.println("adding " + classSymbol.getName() + " to " + retScope);
//        retScope.add(classSymbol);
        currentScope = classSymbol;
        id.setSymbol(classSymbol);
        id.setScope(currentScope);
        
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
        for (var formal: classDef.body) {
            formal.accept(this);
        }

        currentScope = retScope;
        return null;
	}

	@Override
	public Void visit(LetVar letvar) {
		// TODO Auto-generated method stub
        // La definirea unei variabile, creăm un nou simbol.
        // Adăugăm simbolul în domeniul de vizibilitate curent.
        var id   = letvar.id;
//        var type = letvar.type;
        
//        var symbol = new IdSymbol(id.getToken().getText());

        if (id.getToken().getText().equals("self")) {
        	SymbolTable.error(id.ctx, id.getToken(), 
        			"Let variable has illegal name self");
        	return null;
        }
//        // Semnalăm eroare dacă există deja variabila în scope-ul curent.
//        if (! currentScope.add(symbol)) {
//            SymbolTable.error(id.ctx,
//                  id.getToken(), " redefined");
//            return null;
//        }

        // Atașăm simbolul nodului din arbore.
//        id.setSymbol(symbol);
        
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
        // Căutăm tipul variabilei.
//        var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
//        
//        // Semnalăm eroare dacă nu există.
//        if (typeSymbol == null) {
//            SymbolTable.error(type.ctx,
//                    id.getToken(), " has undefined type ");
//            return null;
//        }
        
        // Reținem informația de tip în cadrul simbolului aferent
        // variabilei
//        symbol.setType(typeSymbol);
        
//        if (letvar.expr != null)
//        	letvar.expr.accept(this);
        
        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
	}

	@Override
	public Void visit(CaseVar casevar) {
		// TODO Auto-generated method stub
		// La definirea unei variabile, creăm un nou simbol.
        // Adăugăm simbolul în domeniul de vizibilitate curent.
        var id   = casevar.id;
//        var type = casevar.type;
        
//        var symbol = new IdSymbol(id.getToken().getText());

        if (id.getToken().getText().equals("self")) {
        	SymbolTable.error(id.ctx, id.getToken(), 
        			"Case variable has illegal name self");
        	return null;
        }
        // Semnalăm eroare dacă există deja variabila în scope-ul curent.
//        if (! currentScope.add(symbol)) {
//            SymbolTable.error(id.ctx,
//                  id.getToken(), " redefined");
//            return null;
//        }

        // Atașăm simbolul nodului din arbore.
//        id.setSymbol(symbol);
        
        // TODO 1: Reținem informația de tip în simbolul nou creat.
        
        // Căutăm tipul variabilei.
//        var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
        
        // Semnalăm eroare dacă nu există.
//        if (typeSymbol == null) {
//            SymbolTable.error(type.ctx,
//                    id.getToken(), " has undefined type ");
//            return null;
//        }
        
        // Reținem informația de tip în cadrul simbolului aferent
        // variabilei
//        symbol.setType(typeSymbol);
        
//        System.out.println("caseVar " + id.getToken().getText() + " cu expr " + casevar.expr.toString());
//        casevar.expr.accept(this);
        
        // Tipul unei definiții ca instrucțiune în sine nu este relevant.
        return null;
	}

	@Override
	public Void visit(Program program) {
		// TODO Auto-generated method stub
//		currentScope = ((ClassSymbol) new DefaultScope(SymbolTable.globals));

		currentScope = SymbolTable.globals;
//        System.out.println("default scope " + currentScope);
//        System.out.println(((ClassSymbol)currentScope.getParent()).symbols);
        for (var stmt: program.stmts)
            stmt.accept(this);
        
        if (currentScope.lookup("Main") == null) {
//        	System.err.println("Semantic error: No method main in class Main");
        	SymbolTable.error("No method main in class Main");
        }
        return null;
	}

}

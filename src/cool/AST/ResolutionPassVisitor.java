package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import cool.lexer.CoolLexer;
import cool.structures.*;

public class ResolutionPassVisitor implements ASTVisitor<TypeSymbol> {
	Scope currentScope = null;

	@Override
	public TypeSymbol visit(Id id) {
		// TODO Auto-generated method stub
		
		if (id.getToken().getText().equals("self")) {
//			System.out.println("[ID] returns " + TypeSymbol.SELF_TYPE);
			return TypeSymbol.SELF_TYPE;
//			var retScope = currentScope;
//			while (!(retScope instanceof ClassSymbol)) 
//				retScope = retScope.getParent();
//			System.out.println("var " + id.getToken().getText() + " has type " + retScope);
//			return (TypeSymbol) retScope;
		}
		var symbol = currentScope.lookup(id.getToken().getText());
//		System.out.println("type " + ((IdSymbol)symbol).getType() + " of " + id.getToken().getText() + " <- " + currentScope);
		
        if (symbol == null) {
            SymbolTable.error(id.ctx, id.getToken(), 
            		"Undefined identifier " + id.getToken().getText());
            return null;
        }

        // TODO 2: Întoarcem informația de tip salvată deja în simbol încă de la
        // definirea variabilei.
        return ((IdSymbol) symbol).getType();
	}

	@Override
	public TypeSymbol visit(Int intt) {
		// TODO Auto-generated method stub
//		System.out.println("aici");
        return (ClassSymbol) SymbolTable.globals.lookup("Int");
    }

	@Override
	public TypeSymbol visit(Type type) {
		// TODO Auto-generated method stub
		if (type.getToken() == null)
			return null;
		
//		if (type.getToken().getText().equals("self") || type.getToken().getText().equals("SELF_TYPE")) {
//			var retScope = currentScope;
//			while (!(retScope instanceof ClassSymbol)) 
//				retScope = retScope.getParent();
//			System.out.println("var " + type.getToken().getText() + " has type " + retScope);
//			return (TypeSymbol) retScope;
//		}
		
//		if (type.getToken().getText().equals("SELF_TYPE")) {
//			return (IdSymbol) currentScope;
//		}
//		System.out.println("searching type " + type.getToken().getText() + " in "  + currentScope);
		var symbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
		
		if (symbol == null) {
//			System.out.println("found nada");
			return null;
		}
		
		return symbol;
	}

	@Override
	public TypeSymbol visit(Bool bool) {
		// TODO Auto-generated method stub
        return (ClassSymbol) SymbolTable.globals.lookup("Bool");
	}

	@Override
	public TypeSymbol visit(If iff) {
		// TODO Auto-generated method stub
		var condType = iff.cond.accept(this);
//		System.out.println("THEN");
        var thenType = iff.thenBranch.accept(this);
//        System.out.println("BRANCH =>" + thenType);
        var elseType = iff.elseBranch.accept(this);
        
        // TODO 4: Verificați tipurile celor 3 componente, afișați eroare
        // dacă este cazul, și precizați tipul expresiei.
        
        if (condType != null && !condType.getName().equals("Bool")) {
            SymbolTable.error(iff.cond.ctx, iff.cond.getToken(),
                    "If condition has type " + condType.getName() + " instead of Bool");
        }
        
        while (thenType != null && elseType != null  && ((ClassSymbol) thenType).localLookup(elseType.getName()) == null) {
//			var ret = thenType;
        	if (thenType.getName().equals("SELF_TYPE")) {
        		thenType = (TypeSymbol) currentScope;
        		while (!(thenType instanceof ClassSymbol))
        			thenType = (TypeSymbol) ((Scope) thenType).getParent();
        	}
//        	System.out.println("for caseRet " + thenType.getName() + " variant " + elseType);
			thenType = (TypeSymbol) ((ClassSymbol) thenType).getParent();
//			System.out.println("trying caseRet " + ret + " variant " + caseType);				
		}
        
//        System.out.println("if type is " + thenType + " for cond " + iff.cond.getToken().getText());
        return thenType;

	}

	@Override
	public TypeSymbol visit(StringLiteral string) {
		// TODO Auto-generated method stub
		return (ClassSymbol) SymbolTable.globals.lookup("String");
	}

	@Override
	public TypeSymbol visit(Assign assign) {
		// TODO Auto-generated method stub
		var idType   = assign.id.accept(this);
        var exprType = assign.expr.accept(this);
        var selfType = currentScope;
        
        while (!(selfType instanceof ClassSymbol))
        	selfType = selfType.getParent();
        
//        System.out.println("[ASSIGN] id " + idType + "  = " + exprType);
        
        if (exprType != null && exprType.getName().equals("SELF_TYPE"))
        	exprType = new ClassSymbol(selfType, "SELF_TYPE");
        
        if (idType.getName().equals("SELF_TYPE"))
        	idType = new ClassSymbol(selfType, "SELF_TYPE");
        
        if (assign.id.getToken().getText().equals("self")) {
        	SymbolTable.error(assign.id.ctx, assign.id.getToken(),
                    "Cannot assign to self");
            return null;
        }
        // TODO 5: Verificăm dacă expresia cu care se realizează atribuirea
        // are tipul potrivit cu cel declarat pentru variabilă.
        
        if (idType == null || exprType == null)
            return null;
        
        if (!((ClassSymbol) exprType).inherits(idType)) {
//        	System.out.println("problem " + idType + " with " + ((ClassSymbol) idType).symbols + " != " + exprType + " with " + ((ClassSymbol) exprType).symbols );
            SymbolTable.error(assign.expr.ctx, assign.expr.getToken(),
                    "Type " + exprType.getName() + " of assigned expression is incompatible with declared type " + 
                			idType.getName() + " of identifier " + assign.id.getToken().getText());
            return null;
        }
        
        return exprType;
	}

	@Override
	public TypeSymbol visit(MethodCall method) {
		// TODO Auto-generated method stub
		 // Verificăm dacă funcția există în scope. Nu am putut face
        // asta în prima trecere din cauza a forward references.
        //
        // De asemenea, verificăm că Id-ul pe care se face apelul de funcție
        // este, într-adevăr, o funcție și nu o variabilă.
        //
        // Hint: pentru a obține scope-ul, putem folosi call.id.getScope(),
        // setat la trecerea anterioară.
//		System.out.println("\n=======");
		var id = method.methodName;
        var caller = method.caller.accept(this);
        if (caller != null && caller.getName().equals("SELF_TYPE")) {
        	if (((Scope) caller).getParent() != null)
        		caller = (TypeSymbol) ((Scope) caller).getParent();
        	else
        		caller = (TypeSymbol) currentScope;
//        	System.out.println("changeing scope " + caller);
        	while(!(caller instanceof ClassSymbol))
        		caller = (TypeSymbol) ((Scope) caller).getParent();
//        	System.out.println("new caller " + caller );
        }
        var selfType = caller;
        var staticCaller = method.type;
//        System.out.println("for " + id.getToken().getText() + " from " + caller + " static " + staticCaller.getToken());
        
        IdSymbol symbol = (IdSymbol) ((ClassSymbol) caller).lookupMethod(id.getToken().getText());
//        System.out.println("method " + symbol + " found");
//        if (methodSymbol != null)
//        	System.out.println("method " + id.getToken().getText() + " has type " + (methodSymbol).getType());
         // Verificam daca este definita metoda
        if (staticCaller.getToken() != null) {
        	if (staticCaller.getToken().getText().equals("SELF_TYPE")) {
        		SymbolTable.error(staticCaller.ctx, staticCaller.getToken(), 
            			"Type of static dispatch cannot be SELF_TYPE");
        		return null;
        	}
        	
        	var dispatch = staticCaller.accept(this);
        	
        	if (dispatch == null) {
        		SymbolTable.error(staticCaller.ctx, staticCaller.getToken(), 
            			"Type " + staticCaller.getToken().getText() + " of static dispatch is undefined");
        		return null;
        	}
        	
        	if (!((ClassSymbol) caller).inherits(dispatch)) {
        		SymbolTable.error(staticCaller.ctx, staticCaller.getToken(), 
            			"Type " + dispatch.getName() + " of static dispatch is not a superclass of type " + caller.getName());
        		return null;
        	}
        	
        	caller = dispatch;
        	symbol = (IdSymbol) ((ClassSymbol) caller).lookupMethod(id.getToken().getText());
	        // Verificam daca este definita metoda
        }
        var methodSymbol = (IdSymbol)symbol;        
        if (methodSymbol != null && methodSymbol.getName().equals("SELF_TYPE"))
        	methodSymbol = (IdSymbol) selfType;
        
//        System.out.println(methodSymbol + " found in " + caller);
        if (methodSymbol == null) {
        	SymbolTable.error(id.ctx, id.getToken(), 
        			"Undefined method " + method.methodName.getToken().getText() + " in class " + caller.getName());
        	return null;
        }

        var formalsNo = ((ClassSymbol) ((ClassSymbol) caller)).getFormalsNumber(methodSymbol);
        var formals = ((ClassSymbol) ((ClassSymbol) caller)).getFormals(methodSymbol);
//        var methodScope = ((ClassSymbol) currentScope.getParent()).lookup(methodSymbol.getName());
    	

//    	System.out.println(formalsNo + " number of formals for " + id.getToken().getText() + " from " + caller);
//    	System.out.println(formalsNo + " number of formals for " + id.getToken().getText() + " from parent " + ((ClassSymbol) currentScope.getParent()).getName());
//    	System.out.println(formals);
        if (formalsNo != null && formalsNo != method.args.size()) {
        	SymbolTable.error(id.ctx, id.getToken(),
            		"Method " + id.getToken().getText() + " of class " +
            				caller.getName() + " is applied to wrong number of arguments");
            return methodSymbol.getType();
        } else {
        	if (formals != null) {
	        	var formalIterator = formals.entrySet().iterator();
	            var actualIterator = method.args.iterator();
	            
	            // Verificam suprascrierea cu tipuri diferite de parametrii
	            while (formalIterator.hasNext()) {
	                var formal = formalIterator.next();
	                var actual = actualIterator.next();
	                
	                var formalType = ((IdSymbol)formal.getValue()).getType();
	                var actualType = actual.accept(this);
	                
//	                System.out.println("formal " + formal.getValue() + " has type " + formalType + " ---- "  + actualType);
	                if (formalType == null || actualType == null)
	                    continue;
	                
	                if (!((ClassSymbol) actualType).inherits(formalType)) {
	                    SymbolTable.error(actual.ctx, actual.getToken(),
	                            "In call to method " + id.getToken().getText() + " of class " + 
	                            		caller.getName() + ", actual type " + actualType.getName() + 
	                            			" of formal parameter " + formal.getKey() + " is incompatible with declared type "
	                            				+ formalType.getName());
		                return methodSymbol.getType();
	                }
	            }
	    	}
        }
//        System.out.println("whit type " + methodSymbol.getType());
        if (methodSymbol.getType().getName().equals("SELF_TYPE")) {
//        	System.out.println("returning new SELFTYPE class with parent " + selfType);
        	return new ClassSymbol((ClassSymbol) selfType, "SELF_TYPE");
        }
        return (ClassSymbol)(methodSymbol.getType());
	}

	@Override
	public TypeSymbol visit(OwnMethodCall method) {
		// TODO Auto-generated method stub
		 // Verificăm dacă funcția există în scope. Nu am putut face
        // asta în prima trecere din cauza a forward references.
        //
        // De asemenea, verificăm că Id-ul pe care se face apelul de funcție
        // este, într-adevăr, o funcție și nu o variabilă.
        //
        // Hint: pentru a obține scope-ul, putem folosi call.id.getScope(),
        // setat la trecerea anterioară.
//		System.out.println("\n +++++++++++");
        var id = method.methodName;
//		System.out.println("for method " + id.getToken().getText());
        var caller = currentScope;
        while (!(caller instanceof ClassSymbol))
        	caller = caller.getParent();
        var selfType = caller;
        
        var symbol = ((ClassSymbol) caller).lookupMethod(id.getToken().getText());

//        System.out.println("own method call " + id.getToken().getText() + " by " + caller);
        var methodSymbol = (IdSymbol)symbol;
        
        if (methodSymbol == null) {
        	SymbolTable.error(id.ctx, id.getToken(), 
        			"Undefined method " + method.methodName.getToken().getText() + " in class " + ((Symbol) caller).getName());
        	return null;
        }
        
        if (methodSymbol.getName().equals("SELF_TYPE"))
        	methodSymbol = (IdSymbol) caller;
        
        // TODO 6: Verificați dacă numărul parametrilor actuali coincide
        // cu cel al parametrilor formali, și că tipurile sunt compatibile.
        
        var formalsNo = ((ClassSymbol) ((ClassSymbol) caller)).getFormalsNumber(methodSymbol);
        var formals = ((ClassSymbol) ((ClassSymbol) caller)).getFormals(methodSymbol);
//        var methodScope = ((ClassSymbol) currentScope.getParent()).lookup(methodSymbol.getName());
    	
//    	System.out.println(formals);
        if (formalsNo != null && formalsNo != method.args.size()) {
        	SymbolTable.error(id.ctx, id.getToken(),
            		"Method " + id.getToken().getText() + " of class " +
            				((Symbol) caller).getName() + " is applied to wrong number of arguments");
            return methodSymbol.getType();
        } else {
        	if (formals != null) {
	        	var formalIterator = formals.entrySet().iterator();
	            var actualIterator = method.args.iterator();
	            
	            // Verificam suprascrierea cu tipuri diferite de parametrii
	            while (formalIterator.hasNext()) {
	                var formal = formalIterator.next();
	                var actual = actualIterator.next();
	                
	                var formalType = ((IdSymbol)formal.getValue()).getType();
	                var actualType = actual.accept(this);
	                
//	                System.out.println("formal " + formal.getValue() + " has type " + formalType + " ---- "  + actualType);
	                if (formalType == null || actualType == null)
	                    continue;
	                
	                if (!((ClassSymbol) actualType).inherits(formalType)) {
	                    SymbolTable.error(actual.ctx, actual.getToken(),
	                            "In call to method " + id.getToken().getText() + " of class " + 
	                            		((Symbol) caller).getName() + ", actual type " + actualType.getName() + 
	                            			" of formal parameter " + formal.getKey() + " is incompatible with declared type "
	                            				+ formalType.getName());
		                return methodSymbol.getType();
	                }
	            }
	    	}
        }
        if (methodSymbol.getType().getName().equals("SELF_TYPE"))
        	return (ClassSymbol) selfType;
        return methodSymbol.getType();
	}

	@Override
	public TypeSymbol visit(While whilee) {
		// TODO Auto-generated method stub
		var condSymbol = whilee.cond.accept(this);
		var retSymbol = (ClassSymbol) SymbolTable.globals.lookup("Object");
		
		if (condSymbol == null || retSymbol == null)
			return retSymbol;
		
		if (!condSymbol.getName().equals("Bool")) {
			SymbolTable.error(whilee.cond.ctx, whilee.cond.getToken(), 
					"While condition has type " + condSymbol.getName() + " instead of Bool");
//			return null;
		}
		return retSymbol;
	}

	@Override
	public TypeSymbol visit(Block block) {
		// TODO Auto-generated method stub
//		var lastExpr = block.content.get(block.content.size() - 1);
//		System.out.println("in block");
		TypeSymbol retSymbol = null;

		for (var expr : block.content)
			retSymbol = expr.accept(this);
//        if (symbol == null) {
//            SymbolTable.error(last_expr.ctx,
//            		last_expr.getToken(), last_expr.getToken().getText() + " is undefined");
//            return null;
//        }

        // TODO 2: Întoarcem informația de tip salvată deja în simbol încă de la
        // definirea variabilei.
        return retSymbol;		
	}

	@Override
	public TypeSymbol visit(LetIn letin) {
		// TODO Auto-generated method stub
		var letScope = new MethodSymbol(currentScope, "let");
		var retScope = currentScope;
		
		currentScope = letScope;
		
		for (var v : letin.vars)
			v.accept(this);
		var retSymbol = letin.body.accept(this);
		
		currentScope = retScope;
		
		return retSymbol;
	}

	@Override
	public TypeSymbol visit(Case casee) {
		// TODO Auto-generated method stub
		var ret = casee.var.accept(this);
		boolean did = false;
//		System.out.println("");
//		System.out.println("case var " + casee.var.getToken().getText() + " has type " + ret);
		for (var v : casee.cases) {
			var caseType = v.accept(this);
//			System.out.println("variant " + v.getToken().getText() + " of type " + caseType);
//			System.out.println((((ClassSymbol) caseType).symbols));
			if (!did) {
				ret = caseType;
				did = true;
			};
			while (ret != null && caseType != null  && ((ClassSymbol) ret).localLookup(caseType.getName()) == null) {
//				System.out.println("for caseRet " + ret.getName() + " variant " + caseType);
				ret = (TypeSymbol) ((ClassSymbol) ret).getParent();
//				System.out.println("trying caseRet " + ret + " variant " + caseType);				
			}
		}
		return ret;
	}

	@Override
	public TypeSymbol visit(NewType type) {
		// TODO Auto-generated method stub
		var nt_type = type.type.accept(this);
		if (nt_type == null) {
		   SymbolTable.error(type.type.ctx, type.type.getToken(), 
				   "new is used with undefined type " + type.type.getToken().getText());
		   return null;
		}
		return nt_type;
	}

	@Override
	public TypeSymbol visit(InBrackets ib) {
		// TODO Auto-generated method stub
		var ret = ib.expr.accept(this);
		return ret;
	}

	@Override
	public TypeSymbol visit(Negate neg) {
		// TODO Auto-generated method stub
		var retType = neg.expr.accept(this);
		
		if (retType == null)
			return null;
		
		if (!retType.getName().equals("Int")) {
			SymbolTable.error(neg.expr.ctx, neg.expr.getToken(), 
					   "Operand of " + neg.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		return retType;
	}

	@Override
	public TypeSymbol visit(IsVoid isvoid) {
		// TODO Auto-generated method stub
		return (TypeSymbol) ((ClassSymbol) SymbolTable.globals).lookup("Bool");
	}

	@Override
	public TypeSymbol visit(LessThan lt) {
		// TODO Auto-generated method stub
		var retType = lt.left.accept(this);
		
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(lt.left.ctx, lt.left.getToken(), 
					   "Operand of " + lt.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = lt.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(lt.right.ctx, lt.right.getToken(), 
					   "Operand of " + lt.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null)
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Bool");
	}

	@Override
	public TypeSymbol visit(LessEqual le) {
		// TODO Auto-generated method stub
		var retType = le.left.accept(this);
		
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(le.left.ctx, le.left.getToken(), 
					   "Operand of " + le.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = le.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(le.right.ctx, le.right.getToken(), 
					   "Operand of " + le.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null)
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Bool");
	}
	
	@Override
	public TypeSymbol visit(Equal eq) {
		// TODO Auto-generated method stub
		var retType = eq.left.accept(this);
		
		if (retType == null)
			return null;
		
		if (retType.getName().equals("Int") || retType.getName().equals("Bool") || retType.getName().equals("String")) {
			var retType2 = eq.right.accept(this);
			
			if (retType2 == null)
				return null;
			
			if (!retType.getName().equals(retType2.getName())) {
				SymbolTable.error(eq.ctx, eq.token,
						"Cannot compare " + retType.getName() + " with " + retType2.getName());
				return null;
			}
			
		}
        return (ClassSymbol) SymbolTable.globals.lookup("Bool");
	}

	@Override
	public TypeSymbol visit(Not nott) {
		// TODO Auto-generated method stub
		var retType = nott.expr.accept(this);
		
		if (retType == null)
			return null;
		
		if (!retType.getName().equals("Bool")) { 
			SymbolTable.error(nott.expr.ctx, nott.expr.token,
					"Operand of not has type " + retType.getName() + " instead of Bool");
			return null;
		}
		return retType;
	}

	@Override
	public TypeSymbol visit(Plus plus) {
		// TODO Auto-generated method stub
		var retType = plus.left.accept(this);
//		System.out.println(plus.left.getToken().getText() + " has type " + retType);
		
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(plus.left.ctx, plus.left.getToken(), 
					   "Operand of " + plus.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = plus.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(plus.right.ctx, plus.right.getToken(), 
					   "Operand of " + plus.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null) 
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Int");
	}

	@Override
	public TypeSymbol visit(Minus minus) {
		// TODO Auto-generated method stub
		var retType = minus.left.accept(this);
		
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(minus.left.ctx, minus.left.getToken(), 
					   "Operand of " + minus.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = minus.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(minus.right.ctx, minus.right.getToken(), 
					   "Operand of " + minus.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null)
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Int");
	}

	@Override
	public TypeSymbol visit(Mult mult) {
		// TODO Auto-generated method stub
		var retType = mult.left.accept(this);
		
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(mult.left.ctx, mult.left.getToken(), 
					   "Operand of " + mult.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = mult.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(mult.right.ctx, mult.right.getToken(), 
					   "Operand of " + mult.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null)
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Int");
	}

	@Override
	public TypeSymbol visit(Div div) {
		// TODO Auto-generated method stub
		var retType = div.left.accept(this);
		
//		System.out.println(div.left.getToken().getText() + " -- " + retType + "( ");
		if (retType != null && !retType.getName().equals("Int")) {
			SymbolTable.error(div.left.ctx, div.left.getToken(), 
					   "Operand of " + div.getToken().getText() + 
					     " has type " + retType.getName() + " instead of Int");
			return null;
		}
		
		var retType2 = div.right.accept(this);
		
		if (retType2 != null && !retType2.getName().equals("Int")) {
			SymbolTable.error(div.right.ctx, div.right.getToken(), 
					   "Operand of " + div.getToken().getText() + 
					     " has type " + retType2.getName() + " instead of Int");
			return null;
		}
		
		if (retType == null || retType2 == null) 
			return null;
		
        return (ClassSymbol) SymbolTable.globals.lookup("Int");
	}

	@Override
	public TypeSymbol visit(Formal formal) {
		// TODO Auto-generated method stub
		var id   = formal.name;
        var type = formal.type;
		var symbol = id.getSymbol();
		if (symbol == null)
			return null;
		
        var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
        
        // Semnalăm eroare dacă nu există.
        if (typeSymbol == null) {
        	SymbolTable.error(type.ctx, type.getToken(),
            		"Method " + ((MethodSymbol)currentScope).getName() + " of class " +
               			 ((ClassSymbol)currentScope.getParent()).getName() + " has formal parameter " + 
            				id.getToken().getText() + " with undefined type " + type.getToken().getText());
            return null;
        }
        
        // Reținem informația de tip în cadrul simbolului aferent
        // variabilei
        symbol.setType(typeSymbol);
        ((MethodSymbol) currentScope).setType(symbol);
        
//        System.out.println("setting type of formal " + id.getToken().getText() + " to " + typeSymbol);
        return typeSymbol;
 	}

	@Override
	public TypeSymbol visit(Atribute attr) {
		// TODO Auto-generated method stub
		var type = attr.type;
		var id = attr.id;
		
		var symbol = id.getSymbol();
		if (symbol == null) 
			return null;
		
//		System.out.println();
		var typeSymbol = type.accept(this);
//        if (typeSymbol.getName().equals("SELF_TYPE"))
//        	typeSymbol = type.accept(this);
//		System.out.println("attribute " + symbol + " " + typeSymbol);
        // Semnalăm eroare dacă există deja variabila în scope-ul curent.

//		System.out.println("check inheritance for " + symbol + " from " + currentScope);
        if (((ClassSymbol) currentScope).inheritanceLookup(symbol)) {
            SymbolTable.error(id.ctx, id.getToken(), 
            		"Class " + ((IdSymbol)currentScope).getName() +" redefines inherited attribute " + id.getToken().getText());
            return null;
        }
        
        // Semnalăm eroare dacă nu există.
//        System.out.println("attribute " + attr.id.getToken().getText() + " has type " + typeSymbol);
        if (typeSymbol == null) {
           SymbolTable.error(type.ctx, type.getToken(), 
        		   "Class " + ((ClassSymbol) currentScope).getName() + " has attribute " + id.getToken().getText() 
        		   		+ " with undefined type " + type.getToken().getText());
            return null;
        }
        
        // Reținem informația de tip în cadrul simbolului aferent
        // variabilei
        symbol.setType(typeSymbol);
        
        if (attr.expr != null) {
        	var exprType = attr.expr.accept(this);
//        	System.out.println("expression type " + exprType + " dif " + typeSymbol);
        	// Verificam daca atributul este initializat cu un tip compatibil
        	if (exprType != null && !((ClassSymbol) exprType).inherits(typeSymbol)) {
        		SymbolTable.error(attr.expr.ctx, attr.expr.getToken(), 
        				"Type " + exprType.getName() + " of initialization expression of attribute " + id.getToken().getText()
        					+ " is incompatible with declared type " + type.getToken().getText());
                return null;
        	}
        }
        
        return typeSymbol;
	}

	@Override
	public TypeSymbol visit(Method method) {
		// TODO Auto-generated method stub
		var id   = method.id;
        var type = method.type;
        var methodSymbol = id.getSymbol();
		// TODO 5: Verificăm dacă tipul de retur declarat este compatibil
		// cu cel al corpului.
		
        // Verificam suprascrierea cu numar diferit de parametrii
        if (methodSymbol == null)
        	return null;
        	
//        System.out.println("method " + methodSymbol + "<-" + id.getToken().getText());
//        System.out.println(currentScope + "are definite ");
        
        var formalsNo = ((ClassSymbol) currentScope.getParent()).getFormalsNumber(methodSymbol);
        var formals = ((ClassSymbol) currentScope.getParent()).getFormals(methodSymbol);
//        var methodScope = ((ClassSymbol) currentScope.getParent()).lookup(methodSymbol.getName());
    	
//    	System.out.println(formalsNo + " number of formals for " + id.getToken().getText() + " from parent " + ((ClassSymbol) currentScope.getParent()).getName());
//    	System.out.println(formals);
        if (formalsNo != null && formalsNo != method.formals.size()) {
        	SymbolTable.error(type.ctx, id.getToken(),
            		"Class " + ((ClassSymbol)currentScope).getName() + " overrides method " + id.getToken().getText() 
            			+ " with different number of formal parameters");
            return null;
        } else {
        	if (formals != null) {
	        	var formalIterator = formals.entrySet().iterator();
	            var actualIterator = method.formals.iterator();
	            
	            currentScope = id.getScope();
	            // Verificam suprascrierea cu tipuri diferite de parametrii
	            while (formalIterator.hasNext()) {
	                var formal = formalIterator.next();
	                var actual = actualIterator.next();
	                
	                var formalType = ((IdSymbol)formal.getValue()).getType();
	                var actualType = actual.accept(this);
	                
//	                System.out.println("formal " + formal.getValue() + " has type " + formalType + " ---- "  + actualType);
	                if (formalType == null || actualType == null)
	                    continue;
	                
	                if (formalType != actualType) {
	                    SymbolTable.error(actual.type.ctx, actual.type.getToken(),
	                            "Class " + ((ClassSymbol)currentScope.getParent()).getName() + " overrides method " + id.getToken().getText() +
	                            " but changes type of formal parameter " + actual.getToken().getText() + 
	                            " from " + formalType.getName() + " to " + actualType.getName());
		                currentScope = currentScope.getParent();
		                return null;
	                }
	            }
	            currentScope = currentScope.getParent();
        	} else {
        		var actualIterator = method.formals.iterator();
        		currentScope = id.getScope();
//        		System.out.println("changing scope to " + currentScope);
	            // Verificam suprascrierea cu tipuri diferite de parametrii
	            while (actualIterator.hasNext()) {
	                var actual = actualIterator.next();
	                actual.accept(this);
	            }
	            currentScope = currentScope.getParent();
        	}
        }
        
        
        // Căutăm tipul funcției.
        var typeSymbol = (TypeSymbol)currentScope.lookup(type.getToken().getText());
        
        // Semnalăm eroare dacă nu există.
        if (typeSymbol == null) {
            SymbolTable.error(type.ctx, id.getToken(),
            		"Class " + ((ClassSymbol)currentScope).getName() + " has method " + id.getToken().getText() 
            			+ " with undefined return type " + type.getToken().getText());
            return null;
        }
        
        if (typeSymbol.getName().equals("SELF_TYPE")) {
        	var selfType = currentScope;
        	while (!(selfType instanceof ClassSymbol))
        		selfType = selfType.getParent();
        	typeSymbol = new ClassSymbol(selfType, "SELF_TYPE");
        }
        
        
//        System.out.println("aista " + currentScope.toString());
        var aux = ((ClassSymbol) currentScope.getParent()).lookupMethod(id.getToken().getText());
        if (aux != null) {
	        var defType = ((MethodSymbol) aux).getType();
//	        System.out.println("type (" + defType.getName()  + ")|(" + typeSymbol.getName() + ") for " + id.getToken().getText() + " in class " + currentScope.getParent());
	        if (defType != null && !defType.getName().equals(typeSymbol.getName())) {
                SymbolTable.error(type.ctx, type.getToken(),
                        "Class " + ((ClassSymbol)currentScope).getName() + " overrides method " + id.getToken().getText() +
                        " but changes return type from " + defType.getName() + " to " + typeSymbol.getName());
            	return null;
	        }
        }
        // Reținem informația de tip în cadrul simbolului aferent funcției.
//        System.out.println("setting type of " + id.getToken().getText() + " to " + typeSymbol);
        methodSymbol.setType(typeSymbol);
		
        currentScope = id.getScope();
//		System.out.println("changing scope to " + currentScope + " for body " + method.body);

        var bodyType = method.body.accept(this);
        currentScope = currentScope.getParent();
        
        if (bodyType != null && bodyType.getName().equals("SELF_TYPE"))
			bodyType = new ClassSymbol(currentScope, "SELF_TYPE");
		
		if (typeSymbol == null || bodyType == null)
		    return null;
		
//		System.out.println("+++++++++");
//		System.out.println("searching for " + bodyType.getName() + " in " + ((ClassSymbol)typeSymbol));
//		var auxx = ((ClassSym/bol)typeSymbol).localLookup(bodyType.getName());
//		System.out.println("current scope: " + ((ClassSymbol)typeSymbol) + " has " + ((ClassSymbol)typeSymbol).symbols);
//		System.out.println("TYPE FOUND: " + auxx);
//		System.out.println("======");
//		System.out.println("checking if " + bodyType + " inherits " + typeSymbol);
		if (!((ClassSymbol)bodyType).inherits(typeSymbol)) {
//			System.out.println(typeSymbol + " != " + bodyType);
			SymbolTable.error(method.body.ctx, method.body.getToken(),
		            "Type " + bodyType + " of the body of method " + id.getToken().getText() +
		            	" is incompatible with declared return type " + typeSymbol);
		    return null;
		}
//		System.out.println("======");
		
		return typeSymbol;
	}

	@Override
	public TypeSymbol visit(ClassDef classDef) {
		// TODO Auto-generated method stub
        var classSymbol = classDef.self_type.getSymbol();
        if (classSymbol == null)
        	return null;
        
//        System.out.println(currentScope + " c");
//        System.out.println(currentScope + " c");
//        System.out.println(classSymbol + " for class " + classDef.self_type.getToken().getText());
//        System.out.println("");
//        System.out.println("in class " + classDef.self_type.getToken().getText() + " with parent " + ((ClassSymbol) classSymbol).getScope());
        
        // Semnalăm eroare dacă nu există.
        currentScope = classDef.self_type.getScope();
//        System.out.println("current scope " + currentScope);
        var btype = classDef.base_type;
        if (btype.getToken() != null) {
//        	System.out.println("searching for " + btype.getToken().getText() + " in " + currentScope.getParent());
//	        var baseType = ((ClassSymbol) currentScope.getParent()).lookup(btype.getToken().getText());
//	        System.out.println("found " + baseType + " in " + currentScope.getParent());
//	        ((ClassSymbol) classSymbol).setType((Scope) baseType);
//	        
//	        if (baseType != null) {
//	        	baseType = (Symbol) ((ClassSymbol) baseType).inherits;
//	        	while (baseType != null && ((ClassSymbol)baseType).toRefresh()) {
//		        	
//		        	System.out.println("searching fUr " +baseType.getName() + " in " + currentScope.getParent());
//			        var nbaseType = ((ClassSymbol) currentScope.getParent()).lookup(baseType.getName());
//			        ((ClassSymbol) baseType).setType((Scope) nbaseType);
//			        baseType = (ClassSymbol) nbaseType;
//			        System.out.println("found " + baseType + " in " + currentScope.getParent());
//			        baseType = (Symbol) ((ClassSymbol) baseType).inherits;
//		        }
//        	}
//        	System.out.println(((ClassSymbol) currentScope).toRefresh());
        	var baseType = ((ClassSymbol) SymbolTable.globals).lookup(btype.getToken().getText());
        	((ClassSymbol) currentScope).setType((Scope) baseType);
//        	System.out.println("changed class " + classDef.self_type.getToken().getText() + " with parent " + ((ClassSymbol) classSymbol).getScope() + " | " + ((ClassSymbol) classSymbol).getScope().getParent());
//        	System.out.println(((ClassSymbol)((ClassSymbol) classSymbol).getScope()).symbols);
//        	 System.out.println(((ClassSymbol) currentScope).toRefresh());
        }
//        System.out.println(b_type);
        
        
        if (classDef.base_type.getToken() != null) {
            var type = ((TypeSymbol)((ClassSymbol)currentScope).lookup(classDef.base_type.getToken().getText()));
//            System.out.println(classDef.base_type.getToken().getText() + " -> " + type);
            // Verificăm dacă tipul clasei parinte este declarat
            if (type == null) {
//            	System.out.println(classDef.self_type.getToken().getText());
	        	SymbolTable.error(classDef.base_type.ctx, classDef.base_type.getToken(),
	        			 "Class " + classDef.self_type.getToken().getText() 
	        			  	+ " has undefined parent " + classDef.base_type.getToken().getText());
	            currentScope = currentScope.getParent();
	        	return null;
            }
            
            var typeStr = type.getName();
	        
	        
	        // Semnalăm eroare dacă nu este permis.
	        if (typeStr.equals("Bool") || typeStr.equals("Int")
	        		|| typeStr.equals("String") || typeStr.equals("SELF_TYPE"))
	        {
	        	SymbolTable.error(classDef.base_type.ctx, classDef.base_type.getToken(),
	        			 "Class " + classDef.self_type.getToken().getText() 
	        			  	+ " has illegal parent " + classDef.base_type.getToken().getText());
	        	currentScope = currentScope.getParent();
	            return null;
	        }
            
            // TODO Verificam inheritance cycle
            if (((ClassSymbol) currentScope).checkCycle(classDef.self_type.getToken().getText()) == true) {
            	SymbolTable.error(classDef.self_type.ctx, classDef.self_type.getToken(),
            			 "Inheritance cycle for class " + classDef.self_type.getToken().getText());
            	currentScope = currentScope.getParent();
            	return null;
            }
            
            classSymbol.setType(type);
        }
        
      //  currentScope = classDef.self_type.getScope();
//        var formls = ((ClassSymbol)currentScope).symbols;
//        var formlIterator = formls.entrySet().iterator();
//        
//        while (formlIterator.hasNext()) {
//            var formal = formlIterator.next();
//            System.out.println(formal);
//        }
     // Căutăm tipul clasei parinte
        
        var id   = classDef.self_type.getSymbol().getType();
        for (var elm: classDef.body)
        	elm.accept(this);
        
        currentScope = currentScope.getParent();
        	
        return id;
	}

	@Override
	public TypeSymbol visit(LetVar letvar) {
		// TODO Auto-generated method stub
//		System.out.println("=====");
		var varType = letvar.type.accept(this);
//		System.out.println("visiting " + letvar.id.getToken().getText() + "(" + varType + ")"+ " from " + currentScope);
		
		var symbol = new IdSymbol(letvar.id.getToken().getText());
		
//		var idSymbol = (TypeSymbol)currentScope.lookup(letvar.id.getToken().getText());
//		var typeSymbol = (TypeSymbol)currentScope.lookup(letvar.type.getToken().getText());
        
        // Semnalăm eroare dacă nu există.
        if (varType == null) {
            SymbolTable.error(letvar.type.ctx, letvar.type.getToken(), 
            		"Let variable " + letvar.id.getToken().getText() + " has undefined type " + letvar.type.getToken().getText());
            return null;
        }
        
        symbol.setType(varType);
        
        var retScope = currentScope;
        
		if (letvar.expr != null) {
			var exprType = letvar.expr.accept(this);
//			System.out.println("expression " + letvar.expr.getToken().getText() + " - " + exprType +  " + "  + varType + " =>");
//			System.out.println(varType.getName().equals(exprType.getName()));
			if (exprType != null && !((ClassSymbol) exprType).inherits(varType)) {
	            SymbolTable.error(letvar.expr.ctx, letvar.expr.getToken(),
	                    "Type " + exprType.getName() + " of initialization expression of identifier " +
	            			letvar.id.getToken().getText() + " is incompatible with declared type " + letvar.type.getToken().getText());
	    		currentScope.add(symbol);
	            currentScope = retScope;
	            return null;
			}
		}
		
		currentScope.add(symbol);
		currentScope = retScope;
		return varType;
	}

	@Override
	public TypeSymbol visit(CaseVar casevar) {
		// TODO Auto-generated method stub
		var varType = casevar.type.accept(this);		
		var exprType = casevar.expr.accept(this);
		
		if (casevar.type.getToken().getText().equals("SELF_TYPE")) {
			SymbolTable.error(casevar.type.ctx, casevar.type.getToken(), 
            		"Case variable " + casevar.id.getToken().getText() + " has illegal type SELF_TYPE");
            return null;
		}
		
		if (varType == null) {
            SymbolTable.error(casevar.type.ctx, casevar.type.getToken(), 
            		"Case variable " + casevar.id.getToken().getText() + " has undefined type " + casevar.type.getToken().getText());
            return null;
        }
		
//		if (exprType != varType)
//            SymbolTable.error(casevar.type.ctx, casevar.type.getToken(),
//                    "Let variable type does not match expression type " + casevar.type.getToken().getText());
		
		return exprType;
	}

	@Override
	public TypeSymbol visit(Program program) {
		// TODO Auto-generated method stub
		for (var stmt: program.stmts) {
            stmt.accept(this);
        }
        return null;
	}
	
	TypeSymbol checkBinaryOpTypes(ParserRuleContext ctx, Token token, Expression e1, Expression e2) {
        var type1 = e1.accept(this);
        var type2 = e2.accept(this);
        
        if (type1 == null || type2 == null)
            return null;
        
        if (type1 == TypeSymbol.INT && type2 == TypeSymbol.INT)
        	return type1;
        
        if (token.getType() == CoolLexer.EQ &&
            type1 == TypeSymbol.BOOL && type2 == TypeSymbol.BOOL)
            return type1;
        
        SymbolTable.error(ctx, token,
                "Operands of " + token.getText() + " have incompatible types");
        
        return null;
    }

}

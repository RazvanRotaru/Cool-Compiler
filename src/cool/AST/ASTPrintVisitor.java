package cool.AST;

public class ASTPrintVisitor implements ASTVisitor<Void> {
	int indent = 0;
	
	void printIndent(String str) {
        for (int i = 0; i < indent; i++)
            System.out.print("  ");
        System.out.println(str);
    }
	
	@Override
	public Void visit(Id id) {
		// TODO Auto-generated method stub
		if (id.token != null)
			printIndent(id.token.getText());
		return null;
	}

	@Override
	public Void visit(Int intt) {
		// TODO Auto-generated method stub
		if (intt.token != null)
			printIndent(intt.token.getText());
		return null;
	}

	@Override
	public Void visit(Type type) {
		// TODO Auto-generated method stub
		if (type.token != null)
			printIndent(type.token.getText());
		return null;
	}

	@Override
	public Void visit(Bool bool) {
		// TODO Auto-generated method stub
		if (bool.token != null)
			printIndent(bool.token.getText());
		return null;
	}

	@Override
	public Void visit(If iff) {
		// TODO Auto-generated method stub
		printIndent("if");
		indent++;
		iff.cond.accept(this);
		iff.thenBranch.accept(this);
		iff.elseBranch.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(StringLiteral string) {
		// TODO Auto-generated method stub
		if (string.token != null)
			printIndent(string.token.getText());
		return null;
	}

	@Override
	public Void visit(Assign assign) {
		// TODO Auto-generated method stub
		printIndent("<-");
		indent++;
		assign.id.accept(this);
		assign.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(MethodCall method) {
		// TODO Auto-generated method stub
		printIndent(".");
		indent++;
		method.caller.accept(this);
		method.type.accept(this);
		method.methodName.accept(this);
		for (var v : method.args) 
			v.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(OwnMethodCall method) {
		// TODO Auto-generated method stub
		printIndent("implicit dispatch");
		indent++;
		method.methodName.accept(this);
		for (var v : method.args) 
			v.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(While whilee) {
		// TODO Auto-generated method stub
		printIndent("while");
		indent++;
		whilee.cond.accept(this);
		whilee.loopBranch.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Block block) {
		// TODO Auto-generated method stub
		printIndent("block");
		indent++;
		for (var v : block.content) 
			v.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(LetIn letin) {
		// TODO Auto-generated method stub
		printIndent("let");
		indent++;
		for (var v : letin.vars)
			v.accept(this);
		letin.body.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Case casee) {
		// TODO Auto-generated method stub
		printIndent("case");
		indent++;
		casee.var.accept(this);
		for (var v : casee.cases)
			v.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(NewType type) {
		// TODO Auto-generated method stub
		printIndent("new");
		indent++;
		type.type.accept(this);
		indent--;
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
		printIndent("~");
		indent++;
		neg.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(IsVoid isvoid) {
		// TODO Auto-generated method stub
		printIndent("isvoid");
		indent++;
		isvoid.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(LessThan lt) {
		// TODO Auto-generated method stub
		printIndent(lt.token.getText());
		indent++;
		lt.left.accept(this);
		lt.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(LessEqual le) {
		// TODO Auto-generated method stub
		printIndent(le.token.getText());
		indent++;
		le.left.accept(this);
		le.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Equal eq) {
		// TODO Auto-generated method stub
		printIndent(eq.token.getText());
		indent++;
		eq.left.accept(this);
		eq.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Not nott) {
		// TODO Auto-generated method stub
		printIndent("not");
		indent++;
		nott.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Plus plus) {
		// TODO Auto-generated method stub
		printIndent(plus.token.getText());
		indent++;
		plus.left.accept(this);
		plus.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Minus minus) {
		// TODO Auto-generated method stub
		printIndent(minus.token.getText());
		indent++;
		minus.left.accept(this);
		minus.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Mult mult) {
		// TODO Auto-generated method stub
		printIndent(mult.token.getText());
		indent++;
		mult.left.accept(this);
		mult.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Div div) {
		// TODO Auto-generated method stub
		printIndent(div.token.getText());
		indent++;
		div.left.accept(this);
		div.right.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Formal formal) {
		// TODO Auto-generated method stub
		printIndent("formal");
		indent++;
		formal.name.accept(this);
		formal.type.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Atribute atr) {
		// TODO Auto-generated method stub
		printIndent("attribute");
		indent++;
		atr.id.accept(this);
		atr.type.accept(this);
		if (atr.expr != null)
			atr.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Method method) {
		// TODO Auto-generated method stub
		printIndent("method");
		indent++;
		method.id.accept(this);
		for (var v : method.formals)
			v.accept(this);
		method.type.accept(this);
		method.body.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(ClassDef classDef) {
		// TODO Auto-generated method stub
		printIndent("class");
		indent++;
		classDef.self_type.accept(this);
		classDef.base_type.accept(this);
		for (var v : classDef.body)
			v.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(LetVar letvar) {
		// TODO Auto-generated method stub
		printIndent("local");
		indent++;
		letvar.id.accept(this);
		letvar.type.accept(this);
		if (letvar.expr != null)
			letvar.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(CaseVar casevar) {
		// TODO Auto-generated method stub
		printIndent("case branch");
		indent++;
		casevar.id.accept(this);
		casevar.type.accept(this);
		casevar.expr.accept(this);
		indent--;
		return null;
	}

	@Override
	public Void visit(Program program) {
		// TODO Auto-generated method stub
		printIndent("program");
        indent++;
        for (var stmt : program.stmts) {
        	stmt.accept(this);
        }
        indent--;
		return null;
	}

}

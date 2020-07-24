package cool.AST;

import java.util.LinkedList;

import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;

public class ASTConstructor extends CoolParserBaseVisitor<ASTNode> {

	@Override
	public ASTNode visitProgram(CoolParser.ProgramContext ctx) {
		
		LinkedList<ASTNode> stmts = new LinkedList<>();
        for (var child : ctx.children) {
            ASTNode stmt = visit(child);
            if (stmt != null) {
                stmts.add(stmt);
            }
        }

        return new Program(stmts, ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitClassDef(CoolParser.ClassDefContext ctx) {
		LinkedList<Feature> body = new LinkedList<>();
        for (var feat : ctx.body)
            body.add((Feature)visit(feat));

        return new ClassDef(
            new Id(ctx.self_type, ctx),
            new Id(ctx.base_type, ctx),
            new Id(ctx.id, ctx),
            body,
            ctx.start,
            ctx
        );
	}
	
	@Override
	public ASTNode visitAtribute(CoolParser.AtributeContext ctx) {
		if (ctx.atrib_expr == null)
			return new Atribute(
					new Type(ctx.type, ctx),
					new Id(ctx.id, ctx),
					null,
					ctx.start,
					ctx);
		return new Atribute(
				new Type(ctx.type, ctx),
				new Id(ctx.id, ctx),
				(Expression) visit(ctx.atrib_expr),
				ctx.start,
				ctx);
	}
	
	@Override
	public ASTNode visitMethod(CoolParser.MethodContext ctx) {
		LinkedList<Formal> formals = new LinkedList<>();
		
		for (var frm : ctx.args) 
			formals.add((Formal)visit(frm));
		
		return new Method(
				new Type(ctx.returnType, ctx),
				new Id(ctx.id, ctx),
				formals,
				(Expression) visit(ctx.body),
				ctx.start,
				ctx);
	}
	
	@Override
	public ASTNode visitFormal(CoolParser.FormalContext ctx) {
		return new Formal(
				new Type(ctx.type, ctx),
				new Id(ctx.id, ctx),
				ctx.start,
				ctx);
	}
	
	@Override
	public ASTNode visitCaseVar(CoolParser.CaseVarContext ctx) {
		return new CaseVar(
				new Id(ctx.id, ctx),
				new Type(ctx.type, ctx),
				(Expression) visit(ctx.expression),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitLetVar(CoolParser.LetVarContext ctx) {
		if (ctx.expression == null)
			return new LetVar(
					new Id(ctx.id, ctx),
					new Type(ctx.type, ctx),
					null,
					ctx.start, ctx);
		return new LetVar(
				new Id(ctx.id, ctx),
				new Type(ctx.type, ctx),
				(Expression) visit(ctx.expression),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitLetIn(CoolParser.LetInContext ctx) {
		LinkedList<LetVar> vars = new LinkedList<>();
		
		for (var v : ctx.vars)
			vars.add((LetVar) visit(v));
		
		return new LetIn(
				vars,
				(Expression) visit(ctx.body),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitNew(CoolParser.NewContext ctx) {
		return new NewType(
				new Type(ctx.type, ctx),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitMinus(CoolParser.MinusContext ctx) {
		return new Minus(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitString(CoolParser.StringContext ctx) {
		return new StringLiteral(
				ctx.STRING_LITERAL().getSymbol(), ctx);
	}
	
	@Override
	public ASTNode visitBool(CoolParser.BoolContext ctx) {
		return new Bool(
				ctx.BOOL().getSymbol(), ctx);
	}
	
	@Override
	public ASTNode visitIsvoid(CoolParser.IsvoidContext ctx) {
		return new IsVoid(
				(Expression) visit(ctx.expression),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitLess(CoolParser.LessContext ctx) {
		return new LessThan(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitWhile(CoolParser.WhileContext ctx) {
		return new While(
				(Expression) visit(ctx.cond),
				(Expression) visit(ctx.loopBranh),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitInt(CoolParser.IntContext ctx) {
		return new Int(ctx.INT().getSymbol(), ctx);
	}
	
	@Override
	public ASTNode visitPlus(CoolParser.PlusContext ctx) {
		return new Plus(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
		
	@Override
	public ASTNode visitBrackets(CoolParser.BracketsContext ctx) {
		return new InBrackets(
				(Expression) visit(ctx.expression),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitDivision(CoolParser.DivisionContext ctx) {
		return new Div(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitEqual(CoolParser.EqualContext ctx) {
		return new Equal(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitBoolNot(CoolParser.BoolNotContext ctx) {
		return new Not(
				(Expression) visit(ctx.expression),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitLessEq(CoolParser.LessEqContext ctx) {
		return new LessEqual(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitNegate(CoolParser.NegateContext ctx) {
		return new Negate(
				(Expression) visit(ctx.expression),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitBlock(CoolParser.BlockContext ctx) {
		LinkedList<Expression> content = new LinkedList<>();
		
		for (var v : ctx.body)
			content.add((Expression) visit(v));
		
		return new Block(
				content,
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitId(CoolParser.IdContext ctx) {
		return new Id(ctx.ID().getSymbol(), ctx);
	}
	
	@Override
	public ASTNode visitMultiply(CoolParser.MultiplyContext ctx) {
		return new Mult(
				(Expression) visit(ctx.left),
				(Expression) visit(ctx.right),
				ctx.op, ctx);
	}
	
	@Override
	public ASTNode visitIf(CoolParser.IfContext ctx) {
		return new If((Expression)visit(ctx.cond),
                (Expression)visit(ctx.thenBranch),
                (Expression)visit(ctx.elseBranch),
                ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitCase(CoolParser.CaseContext ctx) {
		LinkedList<CaseVar> cases = new LinkedList<>();
		
		for (var v : ctx.cases)
			cases.add((CaseVar) visit(v));
		
		return new Case(
				(Expression) visit(ctx.var),
				cases,
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {
		LinkedList<Expression> args = new LinkedList<>();
		
		for (var v : ctx.args)
			args.add((Expression) visit(v));
		
		return new OwnMethodCall(
				new Id(ctx.id, ctx),
				args,
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitAssign(CoolParser.AssignContext ctx) {
		return new Assign(
				new Id(ctx.id, ctx),
				(Expression) visit(ctx.value),
				ctx.start, ctx);
	}
	
	@Override
	public ASTNode visitMethodCall(CoolParser.MethodCallContext ctx) {
		LinkedList<Expression> args = new LinkedList<>();
		
		for (var v : ctx.args)
			args.add((Expression) visit(v));
		
		return new MethodCall(
				(Expression) visit(ctx.caller),
				new Id(ctx.id, ctx),
				new Type(ctx.staticT, ctx),
				args,
				ctx.start, ctx);
	}
}

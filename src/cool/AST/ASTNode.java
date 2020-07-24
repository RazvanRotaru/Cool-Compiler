package cool.AST;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import cool.structures.*;

import java.util.*;

public abstract class ASTNode {
	// Reținem un token descriptiv al nodului, pentru a putea afișa ulterior
    // informații legate de linia și coloana eventualelor erori semantice.
    Token token;
    ParserRuleContext ctx;

    ASTNode(Token token, ParserRuleContext ctx) {
        this.token = token;
        this.ctx = ctx;
    }
    
    Token getToken() {
        return token;
    }
    
    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }
}

abstract class Expression extends ASTNode {
    Expression(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }
}

class Type extends ASTNode {
    Type(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Identificatori
class Id extends Expression {
	private IdSymbol symbol;
    private Scope scope;
    
	Id(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    IdSymbol getSymbol() {
        return symbol;
    }

    void setSymbol(IdSymbol symbol) {
        this.symbol = symbol;
    }
    
    Scope getScope() {
        return scope;
    }

    void setScope(Scope scope) {
        this.scope = scope;
    }
}

// Literali întregi
class Int extends Expression {
    Int(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class CaseVar extends ASTNode {
	Id id;
	Type type;
	Expression expr;
	
	CaseVar(Id id, Type type, Expression expr, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.id = id;
		this.type = type;
		this.expr = expr;
	}
	
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class LetVar extends ASTNode {
	Id id;
	Type type;
	Expression expr;
	
	LetVar(Id id, Type type, Expression expr, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.id = id;
		this.type = type;
		this.expr = expr;
	}
	
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}


// Construcția if.
class If extends Expression {
    // Sunt necesare trei câmpuri pentru cele trei componente ale expresiei.
    Expression cond;
    Expression thenBranch;
    Expression elseBranch;

    If(Expression cond,
       Expression thenBranch,
       Expression elseBranch,
       Token start,
       ParserRuleContext ctx) {
        super(start, ctx);
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class StringLiteral extends Expression {
    StringLiteral(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Bool extends Expression {
    Bool(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Token-ul pentru un Assign va fi '='
class Assign extends Expression {
    Id id;
    Expression expr;

    Assign(Id id, Expression expr, Token token, ParserRuleContext ctx) {
        super(token, ctx);
        this.id = id;
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Pentru un MethodCall avem ca reprezentare
class MethodCall extends Expression {
    Expression caller;
    Id methodName;
    Type type;
    LinkedList<Expression> args;

    MethodCall(Expression caller, Id name, Type type, LinkedList<Expression> args, Token start, ParserRuleContext ctx) {
        super(start, ctx);
        this.caller = caller;
        this.methodName = name;
        this.type = type;
        this.args = args;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class OwnMethodCall extends Expression {
    Id methodName;
    LinkedList<Expression> args;
    
    OwnMethodCall(Id name, LinkedList<Expression> args, Token start, ParserRuleContext ctx) {
        super(start, ctx);
        this.methodName = name;
        this.args = args;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class While extends Expression {
	Expression cond;
	Expression loopBranch;
	
	While(Expression cond, Expression loopBranch, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.cond = cond;
		this.loopBranch = loopBranch;
	}
	
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Block extends Expression {
	LinkedList<Expression> content;
	
	Block(LinkedList<Expression> content, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.content = content;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class LetIn extends Expression {
	LinkedList<LetVar> vars;
	Expression body;
	
	LetIn(LinkedList<LetVar> vars, Expression body, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.vars = vars;
		this.body = body;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Case extends Expression {
	Expression var;
	LinkedList<CaseVar> cases;
	
	Case(Expression var, LinkedList<CaseVar> cases, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.var = var;
		this.cases = cases;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class NewType extends Expression {
	Type type;
	
	NewType(Type type, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.type = type;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Negate extends Expression {
	Expression expr;
	
	Negate(Expression expr, Token op, ParserRuleContext ctx) {
		super(op, ctx);
		this.expr = expr;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class IsVoid extends Expression {
	Expression expr;
	
	IsVoid(Expression expr, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.expr = expr;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Pentru un PlusMinus avem 'op=(PLUS | MINUS)' ca reprezentare
class Plus extends Expression {
    Expression left;
    Expression right;

    Plus(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Minus extends Expression {
    Expression left;
    Expression right;

    Minus(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Pentru un MultDiv avem 'op=(MULT | DIV)' ca reprezentare
class Mult extends Expression {
    Expression left;
    Expression right;

    Mult(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Div extends Expression {
    Expression left;
    Expression right;

    Div(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class LessThan extends Expression {
	Expression left;
    Expression right;

    LessThan(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class LessEqual extends Expression {
	Expression left;
    Expression right;

    LessEqual(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Equal extends Expression {
	Expression left;
    Expression right;

    Equal(Expression left, Expression right, Token op, ParserRuleContext ctx) {
        super(op, ctx);
        this.left = left;
        this.right = right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Not extends Expression {
	Expression expr;
	
	Not(Expression expr, Token op, ParserRuleContext ctx) {
		super(op, ctx);
		this.expr = expr;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class InBrackets extends Expression {
	Expression expr;
	
	InBrackets(Expression expr, Token start, ParserRuleContext ctx) {
		super(start, ctx);
		this.expr = expr;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

///////////////
abstract class Feature extends ASTNode {
    Feature(Token token, ParserRuleContext ctx) {
        super(token, ctx);
    }
}

// Argumentul din definita unei functii
class Formal extends ASTNode {
    Type type;
    Id name;

    Formal(Type type, Id name, Token token, ParserRuleContext ctx) {
        super(token, ctx);
        this.type = type;
        this.name = name;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Initializarea poate sa lipseasca
class Atribute extends Feature {
    Type type;
    Id id;
    Expression expr;

    Atribute(Type type, Id id, Expression expr, Token token, ParserRuleContext ctx) {
        super(token, ctx);
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Definirea unei functii
class Method extends Feature {
    Type type;
    Id id;
    LinkedList<Formal> formals;
    Expression body;

    Method(Type type, Id id, LinkedList<Formal> formals, Expression body, Token token, ParserRuleContext ctx) {
        super(token, ctx);
        this.type = type;
        this.id = id;
        this.formals = formals;
        this.body = body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class ClassDef extends ASTNode {
	Id self_type;
	Id base_type;
	Id id;
	LinkedList<Feature> body;
	
	ClassDef(Id self_type, Id base_type, Id id, LinkedList<Feature> body, Token token, ParserRuleContext ctx) {
		super(token, ctx);
		this.self_type = self_type;
		this.base_type = base_type;
		this.id = id;
		this.body = body;
	}
	
	public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

class Program extends ASTNode {
    LinkedList<ASTNode> stmts;

    Program(LinkedList<ASTNode> stmts, Token token, ParserRuleContext ctx) {
        super(token, ctx);
        this.stmts = stmts;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}


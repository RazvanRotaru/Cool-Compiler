package cool.AST;

public interface ASTVisitor<T> {
    T visit(Id id);
    T visit(Int intt);
    T visit(Type type);
    T visit(Bool bool);
    
    T visit(If iff);
    T visit(StringLiteral string);
    T visit(Assign assign);
    T visit(MethodCall method);
    T visit(OwnMethodCall method);
    T visit(While whilee);
    T visit(Block block);
    T visit(LetIn letin);
    T visit(Case casee);
    T visit(NewType type);
    T visit(InBrackets ib);    
    
    T visit(Negate neg);
    T visit(IsVoid isvoid);
    T visit(LessThan lt);
    T visit(LessEqual le);
    T visit(Equal eq);
    T visit(Not nott);
    T visit(Plus plus);
    T visit(Minus minus);
    T visit(Mult mult);
    T visit(Div div);
   
    T visit(Formal formal);
    T visit(Atribute property);
    T visit(Method method);
    T visit(ClassDef classDef);
    T visit(LetVar letvar);
    T visit(CaseVar casevar);
    
    T visit(Program program);
}

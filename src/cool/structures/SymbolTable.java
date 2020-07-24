package cool.structures;

import java.io.File;

import org.antlr.v4.runtime.*;

import cool.compiler.Compiler;
import cool.parser.CoolParser;

import cool.structures.TypeSymbol;

public class SymbolTable {
    public static Scope globals;
    
    private static boolean semanticErrors;
    
    public static void defineBasicClasses() {
        globals = new DefaultScope(null);
        semanticErrors = false;
        
        // TODO Populate global scope.
        
        // Create Object class
        var objectScope = new ClassSymbol(globals, "Object");
	    
        // Add Object class functions
        {   
	        var abort_m = new MethodSymbol(objectScope, "abort");
	        abort_m.setType(TypeSymbol.OBJECT);
	        objectScope.addMethod(abort_m);
	        
	        var type_name_m = new MethodSymbol(objectScope, "type_name");
	        type_name_m.setType(TypeSymbol.STRING);
	        objectScope.addMethod(type_name_m);
	        
	        var copy_m = new MethodSymbol(objectScope, "copy");
	        copy_m.setType(TypeSymbol.SELF_TYPE);
	        objectScope.addMethod(copy_m);
        }
        
        // Add IO class and its functions
        {
        	var IOScope = new ClassSymbol(objectScope, "IO");
        	
        	var out_string_m = new MethodSymbol(IOScope, "out_string");
        	var formal = new IdSymbol("x");
        	formal.setType(TypeSymbol.STRING);
        	out_string_m.add_param(formal);
        	out_string_m.setType(TypeSymbol.SELF_TYPE);
        	IOScope.addMethod(out_string_m);
        	
        	var out_int_m = new MethodSymbol(IOScope, "out_int");
        	formal = new IdSymbol("x");
        	formal.setType(TypeSymbol.INT);
        	out_int_m.add_param(formal);
        	out_int_m.setType(TypeSymbol.SELF_TYPE);
        	IOScope.addMethod(out_int_m);
        	
        	var in_string_m = new MethodSymbol(IOScope, "in_string");
        	in_string_m.setType(TypeSymbol.STRING);
        	IOScope.addMethod(in_string_m);
        	
        	var in_int_m = new MethodSymbol(IOScope, "in_int");
        	in_int_m.setType(TypeSymbol.INT);
        	IOScope.addMethod(in_int_m);
        	
        	IOScope.add(new ClassSymbol("IO"));
        	
        	objectScope.add(IOScope);
        }
        
        // Add Int class
        {
        	var intScope = new ClassSymbol(objectScope, "Int");
        	intScope.add(new ClassSymbol("Int"));
        	
        	objectScope.add(intScope);
        	
        }
        
        // Add String class and its functions
        {
        	var stringScope = new ClassSymbol(objectScope, "String");
        	
        	var length_m = new MethodSymbol(stringScope, "length");
        	length_m.setType(TypeSymbol.INT);
        	stringScope.addMethod(length_m);
        	
        	var concat_m = new MethodSymbol(stringScope, "concat");
        	var formal = new IdSymbol("s");
        	formal.setType(TypeSymbol.STRING);
        	concat_m.add_param(formal);
        	concat_m.setType(TypeSymbol.STRING);
        	stringScope.addMethod(concat_m);
        	
        	var substr_m = new MethodSymbol(stringScope, "substr");
        	formal = new IdSymbol("i");
        	formal.setType(TypeSymbol.INT);
        	substr_m.add_param(formal);
        	formal = new IdSymbol("l");
        	formal.setType(TypeSymbol.INT);
        	substr_m.add_param(formal);
        	substr_m.setType(TypeSymbol.STRING);
        	stringScope.addMethod(substr_m);
        	
        	stringScope.add(new ClassSymbol("String"));
        	
        	objectScope.add(stringScope);
        }
        
        // Add Bool class
        {
        	var boolScope = new ClassSymbol(objectScope, "Bool");
        	boolScope.add(new ClassSymbol("Bool"));
        	
        	objectScope.add(boolScope);
        }
        
        objectScope.add(new ClassSymbol(objectScope, "SELF_TYPE"));
        objectScope.add(objectScope);
        
        globals = objectScope;
    }
    
    /**
     * Displays a semantic error message.
     * 
     * @param ctx Used to determine the enclosing class context of this error,
     *            which knows the file name in which the class was defined.
     * @param info Used for line and column information.
     * @param str The error message.
     */
    public static void error(ParserRuleContext ctx, Token info, String str) {
        while (! (ctx.getParent() instanceof CoolParser.ProgramContext))
            ctx = ctx.getParent();
        
        String message = "\"" + new File(Compiler.fileNames.get(ctx)).getName()
                + "\", line " + info.getLine()
                + ":" + (info.getCharPositionInLine() + 1)
                + ", Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static void error(String str) {
        String message = "Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static boolean hasSemanticErrors() {
        return semanticErrors;
    }
}

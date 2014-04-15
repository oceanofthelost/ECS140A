public class Parser {

    private SymbolTable st = new SymbolTable();
    //all of the first sets 
    TK firstMultop[] = {TK.TIMES, TK.DIVIDE, TK.none};
    TK firstAddop[] = {TK.PLUS, TK.MINUS,TK.none};
    TK firstRelop[] = {TK.EQ, TK.LT, TK.GT, TK.NE, TK.LE, TK.GE, TK.none};
    TK firstFactor[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstTerm[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstSimple[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstExpression[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstCommands[] = {TK.ARROW, TK.none};
    TK firstGuarded_Command[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstGuarded_Commands[] = {TK.LPAREN, TK.ID, TK.NUM,TK.SQUARE, TK.ROOT,TK.none};
    TK firstFa[] = {TK.FA, TK.none};
    TK firstDo[] = {TK.DO,TK.none};
    TK firstIf[] = {TK.IF, TK.none};
    TK firstPrint[] = {TK.PRINT, TK.none};
    TK firstAssignment[] = {TK.ID, TK.none};
    TK firstStatement[] = {TK.ID, TK.PRINT, TK.IF, TK.DO,TK.FA, TK.none};
    TK firstStatement_List[] = {TK.ID, TK.PRINT, TK.IF, TK.DO, TK.FA, TK.none};
    TK firstDeclarations[] = {TK.VAR, TK.none};
    TK firstBlock[] = {TK.VAR, TK.ID, TK.PRINT,TK.IF,TK.DO,TK.FA,TK.none};
    TK firstProgram[] = {TK.VAR, TK.ID, TK.PRINT,TK.IF,TK.DO,TK.FA,TK.none};

    //flag thats used for nested do statments
    boolean isDo;
    
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() 
    {
        tok = scanner.scan();
    }
    
    //parses the input file and then puts a token in tok. 
    //it makes sure that tok is not the EOF token
    private Scan scanner;
    Parser(Scan scanner) 
    {
        this.scanner = scanner;
        scan();
        Program();
        if( tok.kind != TK.EOF )
        {
            parse_error("junk after logical end of program");
        }
        else
        {
            st.print_table();
        }
    }

    //used so i dont have to sype System.out.println for 
    //printing c code
    private void println(String str)
    {
        System.out.println(str);    
    }

    //used to i dont have to type System.out.print for
    //printing c code
    private void print(String str)
    {
        System.out.print(str);
    }
    
    //used to convert and print e variables for c code
    private void print_variable(String str)
    {
        System.out.print(" x_"  + str);
    }

    //the start of the program. 
    private void Program() 
    {
        //include the c libraries we will use
        println("#include <stdio.h>");
        println("#include <math.h>");
        
        //used so we can call the root function
        println("int root(int passed)");
        println("{");
        println("if(passed < 0)");
        println("{");
        println("return 0;");
        println("}");
        println("else");
        println("{");
        println("return (int)sqrt(passed);");
        println("}");
        println("}");
        
        //used so we can use the square function 
        println("int square(int passed)");
        println("{");
        println("return pow(passed,2);");
        println("}");
        //start of the main program in c
        println("int main()");
        Block();
    }
    
    private void Block() 
    {
        //start a block by including { 
        println("{");
        //when we enter a new block we push a new array list on to the symbol 
        //table. 
        st.push_block();
        //we make sure that the next element is part of the 
        //declerations. if not we then procede to statment list
        if(first(firstDeclarations))
        {
            Declarations();
        }
        Statement_List();
        //when leaving a block we pop off the top element of the symbol table
        st.pop_block();
        //end a block by including }
        println("}");
    }
    
    //all variables will be added here 
    private void Declarations() 
    {
        //make sure that the first token is of type var
        mustbe(TK.VAR);
        //we read in all of the e variables
        while( is(TK.ID) ) 
        {
            //we check to make sure that the e variable is not already declared. if it is 
            //we continue translating otherwise we generate c code that represents the e 
            //variable in c. 
            if(!st.add_element(tok.string, tok.lineNumber))
            {
                print("int");
                print_variable(tok.string);
                println(" = -12345;");
                scan();
            }
            else
            {
                scan();
            }
        }
        //if we are done declaring variables we must have rav present. 
        mustbe(TK.RAV);
    }

    //start of the statment list of e
    private void Statement_List()
    {
        while(first(firstStatement))
        {
            Statement();
        }
    }

    //all of the valid statments that e contains. 
    //if there is no valid statment we print an error and
    //stop translating
    private void Statement()
    {
        if(first(firstAssignment))
        {
            Assignment();
        }
        else if(first(firstPrint))
        {
            Print();
        }
        else if(first(firstIf))
        {
            If();
        }
        else if(first(firstDo))
        {
            Do();
        }
        else if(first(firstFa))
        {
            Fa();
        }
        else
        {
            parse_error("statment");
        }
    }

    //the block that does all assignment
    public void Assignment()
    {
        //first value must be a id token
        if(is(TK.ID))
        {
            //if we have a id token then we have a variable. we then check with the 
            //symbol table to make sure that the variable has been declared. if the 
            //variable has not been declared we stop translating otherwise we generate 
            //teh valid c code for the variable
            st.valid_element(tok.string,tok.lineNumber,"Assign");
            print_variable(tok.string);
            scan();
        }      
        //we make sure the next token is the assign token. if so we generate the = operator
        //in the c code. then we read in the expression that the variable will be assigned. 
        mustbe(TK.ASSIGN);
        print(" = ");
        Expression();
        println(";");
    }

    //calld the print statment of e. 
    public void Print()
    {
        //if we have the print token then we generate the c equivlent of print 
        //which is printf. we then generate the print f 
        //with the proper expression that will be printed. 
        mustbe(TK.PRINT);
        print("printf(\"%d\\n\",");
        Expression();
        println(");");

    }

    public void If()
    {
        //if the token is an if then we generate the if statment in c
        mustbe(TK.IF);
        print("if(");
        //we then print the if condition
        Guarded_Commands();
        //make sure that the if is indeed terminated. 
        mustbe(TK.FI);
    }

    public void Do()
    {
        //print the do statment as long as we have the do token. 
        mustbe(TK.DO);
        //set the do flag so just in case we are going to have 
        //a nested do statment. 
        isDo = true;
        //e do is equivlent to c while loop. so we generate a while and 
        //then print the while conditions and what is being evaluated. 
        print("while(");
        Guarded_Commands();
        //once we are leaving the do statment we make the isdo flag false. 
        isDo = false;
        mustbe(TK.OD);
    }

    private void Fa()
    {
        String fa_var = "x_";

        mustbe(TK.FA);
        if(is(TK.ID))
        {
            st.valid_element(tok.string,tok.lineNumber,"Assign");
            fa_var+=tok.string;
            scan();
        }
        mustbe(TK.ASSIGN);
        print("for("+fa_var+" = ");
        Expression();
        print(";");
        mustbe(TK.TO);
        print(fa_var + " <= ");
        Expression();
        println(";" +fa_var + "++)");
        println("{");
        if(is(TK.ST))
        {
            print("if(");
            scan();
            Expression();
            print(")");
        }
        Commands();
        println("}");
        mustbe(TK.AF); 
    }

    private void Guarded_Commands()
    {
        Guarded_Command();
        while(is(TK.BOX))
        {
            if(isDo)
            {
                print("while(");
                scan();
                Guarded_Command(); 
            }
            else
            {
                print("else if(");
                scan();
                Guarded_Command();
            }
        }
        if(is(TK.ELSE))
        {
            println("else");
            scan();
            Commands();
        }
    }

    private void Guarded_Command()
    {
        Expression();
        println(")");
        Commands(); 
    }

    private void Commands()
    {
        mustbe(TK.ARROW);
        Block();
    }
    
    private void Expression()
    {
         Simple();
         if(first(firstRelop))
         {
            Relop();
            Simple();
         }
    }

    private void Simple()
    {
        Term();
        while(first(firstAddop))
        {
            Addop();
            Term();
        }
    }

    private void Term()
    {
        Factor();
        while(first(firstMultop))
        {
            Multop();
            Factor();
        }
    }

    private void Factor()
    {
        
        if(is(TK.LPAREN))
        {
            print("(");
            scan();
            Expression();
            mustbe(TK.RPAREN);
            print(")");
        }
        else if(is(TK.ID))
        {
            st.valid_element(tok.string, tok.lineNumber,"Used");
            print_variable(tok.string);
            scan();
        }
        else if(is(TK.NUM))
        {
            print(tok.string);
            scan();
        }
        else if(is(TK.SQUARE))
        {
            scan();
            print("square(");
            Expression();
            print(")");
        }
        else if(is(TK.ROOT))
        {
            scan();
            print("root(");
            Expression();
            print(")");
        }
        else
        {
            parse_error("factor");
        }

    }

    private void Relop()
    {
        if(is(TK.EQ))
        {
            print(" == ");
            scan();
        }
        else if(is(TK.LT))
        {
            print(" < ");
            scan();
        }
        else if(is(TK.GT))
        {
            print(" > ");
            scan();
        }
        else if(is(TK.NE))
        {
            print(" != ");
            scan();
        }
        else if(is(TK.LE))
        {
            print(" <= ");
            scan();
        }
        else if(is(TK.GE))
        {
            print(" >= ");
            scan();
        }
        else
        {
            parse_error("relop");
        }
    }

    private void Addop()
    {
        if(is(TK.PLUS))
        {
            print(" + ");
            scan();
        }
        else if(is(TK.MINUS))
        {
            print(" - ");
            scan();
        }
        else
        {
            parse_error("addop");
        }
    }

    private void Multop()
    {
        if(is(TK.TIMES))
        {
            print(" * ");
            scan();
        }
        else if(is(TK.DIVIDE))
        {
            print(" / ");
            scan();
        }
        else
        {
            parse_error("multop()");
        }
    }

    // is current token what we want?
    private boolean is(TK tk) 
    {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
        if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
        }
        scan();
    }

    private boolean first(TK[] firstset)
    {
        //iterate over the array of first sets
        int i = 0;
        //as long as we have not reached the end of a first set and we have 
        //not found our element in the first set we continue to iterate
        while(firstset[i] != tok.kind && firstset[i] != TK.none)
        {
            i++;
        }
        //if we iterated through the entire array and did not find our 
        //element in the array then we know that we should return false. if
        //we itereated and we found our our element we should return true. 
        return firstset[i] != TK.none;
    }

    private void parse_error(String msg) {
        System.err.println( "can't parse: line "
                            + tok.lineNumber + " " + msg );
        System.exit(1);
    }
}

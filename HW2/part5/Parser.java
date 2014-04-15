public class Parser {

    private SymbolTable st = new SymbolTable();
    //all of the first sets 
    TK firstMultop[] = {TK.TIMES, TK.DIVIDE, TK.none};
    TK firstAddop[] = {TK.PLUS, TK.MINUS,TK.none};
    TK firstRelop[] = {TK.EQ, TK.LT, TK.GT, TK.NE, TK.LE, TK.GE, TK.none};
    TK firstFactor[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
    TK firstTerm[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
    TK firstSimple[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
    TK firstExpression[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
    TK firstCommands[] = {TK.ARROW, TK.none};
    TK firstGuarded_Command[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
    TK firstGuarded_Commands[] = {TK.LPAREN, TK.ID, TK.NUM,TK.none};
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


    boolean isDo;
    
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    private void scan() 
    {
        tok = scanner.scan();
    }

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

    private void println(String str)
    {
        System.out.println(str);    
    }

    private void print(String str)
    {
        System.out.print(str);
    }

    private void print_variable(String str)
    {
        System.out.print(" x_"  + str);
    }

    private void Program() 
    {
        println("#include <stdio.h>");
        println("int main()");
        Block();
    }
    
    private void Block() 
    {
        println("{");
        st.push_block();
        if(first(firstDeclarations))
        {
            Declarations();
        }
        Statement_List();
        st.pop_block();
        println("}");
    }
    
    //all variables will be added here 
    private void Declarations() 
    {
        mustbe(TK.VAR);
        while( is(TK.ID) ) 
        {
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
        mustbe(TK.RAV);
    }

    private void Statement_List()
    {
        while(first(firstStatement))
        {
            Statement();
        }
    }

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

    public void Assignment()
    {
        if(is(TK.ID))
        {
            st.valid_element(tok.string,tok.lineNumber,"Assign");
            print_variable(tok.string);
            scan();
        }      
        mustbe(TK.ASSIGN);
        print(" = ");
        Expression();
        println(";");
    }

    public void Print()
    {
        mustbe(TK.PRINT);
        print("printf(\"%d\\n\",");
        Expression();
        println(");");

    }

    public void If()
    {
        mustbe(TK.IF);
        print("if(");
        Guarded_Commands();
        mustbe(TK.FI);
    }

    public void Do()
    {
        mustbe(TK.DO);
        isDo = true;
        print("while(");
        Guarded_Commands();
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

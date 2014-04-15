import java.util.*;

public class SymbolTable{
	Stack <ArrayList<Elements>> st;
	
	public SymbolTable()
    {
		st = new Stack <ArrayList<Elements>> ();
	}
	
	public void push_block()
    {
		ArrayList<Elements> lst = new ArrayList<Elements>();
		st.push(lst);
	}
	
	public void pop_block()
    {
		st.pop();
	}
	
//this is the method that will be used in declarations. We just need to make sure the variable is
    //not already declared in this level. true = inserted, false = not inserted
    public void add_element(String Name, int LineNumber)
    {
        //will execute when element is not in the symbol table
        if(!search_block(Name,LineNumber,st.peek())) 
        {
            Elements emt = new Elements(Name,LineNumber);
            st.peek().add(emt); //adds an element to current block
        }
        //executes when variable is already in the symbol table
        else
        {
            System.err.println("variable "+ Name + " is redeclared on line " + LineNumber);
        }
    }

    public void valid_element(String Name, int LineNumber)
    {
        
        if(!search_stack(Name,LineNumber))
        {
            System.err.println("undeclared variable " + Name + " on line " + LineNumber);
            System.exit(1);
        }
        
    }

    //true: varble already exists
    //false: element does not exsist
    public boolean search_stack(String variable_id, int value)
    {
        boolean isFound = false;
        ListIterator<ArrayList<Elements>> Blocks= st.listIterator(st.size());
        while(Blocks.hasPrevious() && !isFound)
        {
            ArrayList<Elements> current_block= Blocks.previous();
            isFound=search_block(variable_id, value, current_block);
        }
        return isFound;
    }

    public boolean search_block(String variable_id, int value, ArrayList<Elements> variables)
    {
        boolean isFound=false; //variable is new
        for(Elements i: variables)
        {
            if(i.getName().equals(variable_id))
           {
                i.setValue(value);
                isFound=true; //variable already declared
               break;
           }
        }
        return(isFound);
    }
}

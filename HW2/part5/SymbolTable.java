import java.util.*;

public class SymbolTable{
	Stack <ArrayList<Elements>> st;
    //the following stacks are used for part 4
	Stack <ArrayList<Elements>> scope;
    //contains the value of the current stack size
    int Level;

	public SymbolTable()
    {
        //st: main symbol table
		st = new Stack <ArrayList<Elements>> ();
        //used in part 4 to properly display the variable data
        scope = new Stack<ArrayList<Elements>>();
	    Level = -1;
    }
	
    //add a new stack element to the symbol table
	public void push_block()
    {
        //creae a new empty array list and then put that onto the symbol table
		ArrayList<Elements> lst = new ArrayList<Elements>();
		st.push(lst);
        scope.push(lst);
        //since we are now in a different scope we need to increase level by 1. 
        Level++;
	}
	
    //left a scope level so we pop a block off of the stack
	public void pop_block()
    {
		st.pop();
        //need to decrese level by 1. 
        Level--;
	}
    
    //used in part 4 of the assignment. 
    public void print_table()
    {
        //table will become the reverse stack of scope. this is so that the 
        //variables that were declared first will be displayed first. we do 
        //this for part 4. 
        Stack<ArrayList<Elements>> table = new Stack<ArrayList<Elements>>();
        while(!scope.isEmpty())
        {
            //tranfering the elements between the two stacks
            table.push(scope.peek());
            scope.pop();
        }
        
        //we now print the content of table
        ArrayList<Elements> current_block;
        while(!table.isEmpty())
        {
            current_block=table.peek();
            for(Elements i: current_block)
            {
                i.print();
            }
            table.pop();
        }
    }
	
    public boolean isRedeclared(String variable_id, int value)
    {
        boolean isFound=false; 
        ArrayList<Elements> variables = st.peek();
        for(Elements i: variables)
        {
            if(i.getName().equals(variable_id))
            {
                isFound=true; 
                break;
            }
        }
        return(isFound);
    }


    //interface for adding new variables to the symbol table
    public boolean add_element(String Name, int LineNumber)
    {
        boolean valid;
        valid = search_block(Name,LineNumber,st.peek(),"Add");
        //will execute when element is not in the symbol table
        //I pass the add string since search block requires that 
        //parameter. It is Add since we are adding a value to the symbol table. 
        if(!valid) 
        {
            Elements emt = new Elements(Name,LineNumber,Level);
            st.peek().add(emt); //adds an element to current block
        }
        //executes when variable is already in the symbol table
        else
        {
            System.err.println("variable "+ Name + " is redeclared on line " + LineNumber);
        }
        return valid;
    }

    //interface to see if when a variable is used it is declared
    public void valid_element(String Name, int LineNumber, String AssignOrUsed)
    {
        //we call search stack. If it finds the variable in the stack then the variable is valid. 
        //if search_stack returns false that means the element was ever declared and we print 
        //an error and stop translation. 
        if(!search_stack(Name,LineNumber,AssignOrUsed))
        {
            System.err.println("undeclared variable " + Name + " on line " + LineNumber);
            System.exit(1);
        }
    }

    //true: varble already exists
    //false: element does not exsist
    public boolean search_stack(String variable_id, int value, String AssignOrUsed)
    {
        //we use the iterator so we can traverse the stack. 
        boolean isFound = false;
        ListIterator<ArrayList<Elements>> Blocks= st.listIterator(st.size());
        //will stop when we have ether checked every element in the stack or we have found a matching 
        //element in the stack.
        while(Blocks.hasPrevious() && !isFound)
        {
            ArrayList<Elements> current_block= Blocks.previous();
            //call the block search method. 
            isFound=search_block(variable_id, value, current_block,AssignOrUsed);
        }
        return isFound;
    }

    //true: variable is in the symbol table
    //false: variable is not in symbol table
    ////this is the main search function. it is used for when variables are declared and also when we are
    //checking to see if the vairable when used is a valid variable
    public boolean search_block(String variable_id, int value, ArrayList<Elements> variables, String AssignOrUsed)
    {
        boolean isFound=false; //variable is new
        //iterate over the array list of elements
        for(Elements i: variables)
        {
            //if the string of the of our element matches one in the array list two things will
            //happen. First isFound becomes true since we have found the element. then depending on what
            //string was passed to this function sopthing else will occure as well. 
            if(i.getName().equals(variable_id))
            {
                isFound=true; //variable already declared
                //if we passed assign as the 4th varible then we update the assign arraylist of 
                //the element. otherwise if the 4th argument is used we update the array list of used 
                //for the element. 
                if(AssignOrUsed.equals("Assign"))
                {
                    i.putAssigned(value);
                }
                else if(AssignOrUsed.equals("Used"))
                {
                    i.putUsed(value);
                }
                //we break from the loop since there is no need to keep checking when the element is found to be in the 
                //symbol table. 
                break;
            }
        }
        return(isFound);
    }
}

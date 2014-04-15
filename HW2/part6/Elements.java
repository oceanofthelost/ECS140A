import java.util.*;

class Elements{
	
	private String name;
	private int value;
    private int level;
    private ArrayList<Integer> assigned;
    private ArrayList<Integer> used;

	public Elements(String name, int value, int level)
    {
		this.name = name;
		this.value = value;
        this.assigned = new ArrayList<Integer>();
        this.used = new ArrayList<Integer>();
        this.level=level;
	}

	public void print()
    {
        System.err.println(name); 
        System.err.println("  declared on line " + value + " at nesting depth " +  level);
        if(assigned.isEmpty())
        {
            System.err.println("  never assigned");
        }
        else
        {
            int count=0;
            System.err.print("  assigned to on:");
            for(int i=0; i<assigned.size();i++)
            {
                count=IntCount(assigned,assigned.get(i));
                if(count > 1)
                {
                    System.err.print(" ");
                    System.err.print(assigned.get(i) + "(" + count + ")");
                    i=i+count-1;
                    count=0;
                }
                else
                {
                    System.err.print(" ");
                    System.err.print(assigned.get(i));
                }
            }
            System.err.print("\n");
        }
        if(used.isEmpty())
        {
            System.err.println("  never used");
        }
        else
        {
            int count=0;
            System.err.print("  used on:");
            for(int i=0; i<used.size();i++)
            {
                count=IntCount(used,used.get(i));
                if(count > 1)
                {
                    System.err.print(" ");
                    System.err.print(used.get(i) + "(" + count + ")");
                    i=i+count-1;
                    count=0;
                }
                else
                {
                    System.err.print(" ");
                    System.err.print(used.get(i));
                }
            }
            System.err.print("\n");
        }
    }

    public void putAssigned(int value)
    {
        assigned.add(value);
    }

    public void putUsed(int value)
    {
        used.add(value);
    }

	public String getName()
    {
		return(name);
	}

    public void setValue(int value)
    {
		this.value = value;
	}
	
	public int getValue()
    {
		return(value);
	}

    private int IntCount(ArrayList<Integer> al, int compare)
    {
        int count=0;
        for(Integer i : al)
        {
            if(i.intValue() == compare)
            {
                count=count+1;
            }
        }
        return count;
    }
}

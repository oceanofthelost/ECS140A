public class JumbleUser
{
    public static int lengthLongestNDCSS1(Jumble J)
    {
        JumbleIt it = new JumbleIt(J);
        int maxSequence = 0;
        int subSequence = 0;
        int current = 0;
        int old = 0;

        if(it.hasNext())
        {
            try
            {
        	   old=it.next();
           	}
            catch (UsingIteratorPastEndException e) 
            {
                System.out.println("= caught UsingIteratorPastEndException from SeqIt");
            }
            subSequence+=1;
            maxSequence+=1;
        }
        while(it.hasNext())
        {
            try
            {
        	   current=it.next();
            }
            catch (UsingIteratorPastEndException e) {
            System.out.println("= caught UsingIteratorPastEndException from SeqIt");
            }
            if(old <= current)
        	{
        		subSequence+=1;
        	}
        	//found a smaller value
        	else
        	{
        		if(subSequence > maxSequence)
        		{
        			maxSequence=subSequence;
        		}
        		subSequence=1;
        	}
        	old=current;
        }

		if(subSequence > maxSequence)
        {
        	maxSequence=subSequence;
        }
		return maxSequence;
    }
    public static int lengthLongestNDCSS2(Jumble J)
    {
        JumbleIt it = new JumbleIt(J);
        int maxSequence = 0;
        int subSequence = 0;
        int current = 0;
        int old = 0;

        try
        {
            old=it.next();
            subSequence+=1;
            maxSequence+=1;
        }
        catch(UsingIteratorPastEndException e)
        {
            return 0;
        }

        try
        {
            while(true)
            {
                current=it.next();
                if(old <= current)
                {
                    subSequence+=1;
                }
                //found a smaller value
                else
                {
                    if(subSequence > maxSequence)
                    {
                        maxSequence=subSequence;
                    }
                    subSequence=1;
                }
                old=current;
            }
        }
        catch(UsingIteratorPastEndException e)
        {
            if(subSequence > maxSequence)
            {
                maxSequence=subSequence;
            }
            return maxSequence;
        }
        //return maxSequence;
    }
}
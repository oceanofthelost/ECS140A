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
        	old=it.next();
        	subSequence+=1;
        	maxSequence+=1;
        }
        while(it.hasNext())
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

		if(subSequence > maxSequence)
        {
        	maxSequence=subSequence;
        }
		return maxSequence;
    }
}

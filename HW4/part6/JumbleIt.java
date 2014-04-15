public class JumbleIt implements SeqIt
{
    private Jumble J;
    private int Index;

    /**
     * default constructor for an iterator
     * for the jumble int
     * @param  J the jumble object we will iterate over
     * @return   an iterator to a single element in the jumble
     */
    public JumbleIt(Jumble J)
    {
        Index = 0;
        this.J = J;
    }

    /**
     * @return: boolean value. true if there is a next element
     *          otherwise we return false when there is no next element
     */
    public boolean hasNext()
    {
        return Index < J.size;
    }

    /**
     * returns the next element in the sequence if the sequence 
     * @return [description]
     */
    public int next() throws UsingIteratorPastEndException
    {
        if(!hasNext())
        {
            throw new UsingIteratorPastEndException();
        }
        Index+=1;
        return J.values[Index-1];
    }
}

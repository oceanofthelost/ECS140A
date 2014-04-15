public class DeltaIt implements SeqIt
{
    /**
     * private data members for the iterator
     */
    private Delta D;
    private int Index;

    /**
     * default constructor
     * @param  D sequence we are iteratoing over
     * @return   an iterator to a single element in the sequence
     */
    public DeltaIt(Delta D)
    {
        this.D = D;
        this.Index = 0;
    }

    /**
     * finds out if the sequence has a next element
     * @return true if there is a next element
     *         false if there is not a next element
     */
    public boolean hasNext()
    {
        return Index < D.num;
    }

    public int next() throws UsingIteratorPastEndException
    {
        if(!hasNext())
        {
            throw  new UsingIteratorPastEndException();
        }
        Index+=1;
        return D.initial+D.delta*(Index-1);
    }
}

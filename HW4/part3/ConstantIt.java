public class ConstantIt implements SeqIt
{
    /*
     * private data members for ConstantIt 
     */
    private Constant C;
    private int Index;

    /**
     * Deafault constructor for Constant iterator
     * @param  C sequence we are iterating over
     * @return   an iterator to a single element i n the sequence
     */
    public ConstantIt(Constant C)
    {
        this.Index = 0;
        this.C = C;
    }

    /**
     * finds out if the sequence has a next element
     * @return true if there is a next element
     *         false if there is not a next element 
     */
    public boolean hasNext()
    {
        return Index < C.num;
    }

    /**
     * returns the next element in the sequence
     * @return the next value in the sequence
     */
    public int next()
    {
        if(!hasNext())
        {
            System.err.println("ConstantIt called past end");
            System.exit(1);
        }
        Index+=1;
        return C.value;
    }
}

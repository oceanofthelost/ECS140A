public class Constant extends Seq
{
    //num is the number of times value occures
    protected int num;
    //the value in the sequence
    protected int value;

    //default constructor
    public Constant(int num, int value)
    {
        this.num = num;
        //if num is equal to zero that means we have an empty sequence.
        //then we make the value of the sequence equal to o. 
        this.value = num==0 ? 0 : value;     
    }
    
    //returns the min of the sequence which is 
    //equal to value. 
    public int min()
    {
        return value;
    }

    /**
     * creates an iterator for this class
     * @return iterator to an instance of this class
     */
    public SeqIt createSeqIt()
    {
        Constant C = new Constant(num,value);
        return new ConstantIt(C);
    }

    public String toString()
    {
        return "[ " + num + " : " + value + " ]";
    }
}

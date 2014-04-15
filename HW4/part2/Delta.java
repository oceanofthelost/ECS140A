public class Delta extends Seq
{
    //number of elements in the sequence
    protected int num;
    //the forst value in the sequence
    protected int initial;
    //the value that determins the next value in the sequence
    protected int delta;

    //default constructor
    public Delta(int num, int initial, int delta)
    {
        this.num = num;
        //if num is zero that means we have an empty sequence so
        //delta and initial are equal to zeros. This simplifies logic 
        //later on
        this.initial = num==0 ?  0 : initial;
        this.delta = num==0 ? 0 : delta;
    }

    //returns the min of the sequence.
    public int min()
    {
        //if delta is min that means the last element in the sequence is 
        //the min value so we return that element, otherwise we return the 
        //initial value. 
        return delta<0 ? initial+delta*(num-1) : initial;
    }

    //overloded toString so we can use print on our class. 
    public String toString()
    {
        return "< " + num + " : " + initial + " &"+delta + " >"; 
    }
}

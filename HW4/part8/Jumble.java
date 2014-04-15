public class Jumble extends Seq
{
    //the array of numbers in are sequence. Jumble is a random sequence
    protected int values[];
    //the amount of elements in the jumble sequence
    protected int size;

    //the default constructor. 
    public Jumble(int [] values)
    {
        //the amount of elements in the sequence
        this.size = values.length;
        this.values = new int[this.size];
        //we copy the array so that we do not just have a reference to 
        //the sequence in our class. This makes it so that the jumble sequence
        //is not effeted by changes in the orgional array. 
        System.arraycopy(values,0,this.values,0,this.size);
    }

    //returns the min element of the sequence
    public int min()
    {
        //if the sequence is empty the min value is 0. 
        //otherwise we set min value as the first 
        //element in the sequence. 
        int minValue = size==0 ? 0 : values[0];

        //we now scan every elemnt in the array to fid the min. 
        //we compare each element to the current min and if the
        //element is smaller than the current min we then set 
        //the min to the element. 
        for(int i : values)
        {
            //checking to see if current element is smaller than 
            //the current min. 
            if(minValue > i)
            {
                minValue=i;
            }
        }

        //retuning the min value. 
        return minValue;
    }

    /**
     * creates an iterator for this class
     * @return returns an iterator to the instance of this class
     */
    public SeqIt createSeqIt()
    {
        Jumble J = new Jumble(values);
        return new JumbleIt(J);
    }

    //makes it so we can use the system print function for our class
    public String toString()
    {
        String result;

        result = "{ " + size + " : ";

        for(int i : values)
        {
            result +=i + " ";
        }

        result += "}";

        return result;
    }
}

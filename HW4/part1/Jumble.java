public class Jumble extends Seq
{
    protected int values[];
    protected int size;

    public Jumble(int [] values)
    {
        this.size = values.length;
        this.values = new int[this.size];
        System.arraycopy(values,0,this.values,0,this.size);
    }

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

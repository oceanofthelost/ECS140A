class Plus{
	public static Seq plus(Seq s, Seq s1){
		Seq s2 = new Constant(0,0);
		SeqIt q = s.createSeqIt();
		SeqIt q1 = s1.createSeqIt();
		int num = 0; //size
		int first = 0;    //added value of sequence
		int next = 0; //added value of sequence
		int delta = 0;
		int deltaTest = 0;
		boolean isDelta = true;
		boolean isConstant = true;
		
		try{
            //sets up the info for the while loop
            if(q.hasNext() && q1.hasNext())
            {
                num++;
                first = q.next() + q1.next();
            }
            
            if(q.hasNext() && q1.hasNext())
            {
                num++;
                next = q.next() + q1.next();
                //initial delta
                delta = next - first;
                if(first != next)
                {
                    isConstant = false;
                }
                first = next;
            }

            while(q.hasNext() && q1.hasNext())
            {  
                //finds the next added number of the sequence
                next = q.next() + q1.next();
                deltaTest = next - first;
                first = next;
                
                if(delta != deltaTest)
                {
                    isDelta = false;
                }
                if(delta != 0 || deltaTest != 0)
                {
                    isConstant = false;
                }
                num++;
            }
            
            if(isConstant == true)
            { 
                q = s.createSeqIt();
                q1 = s1.createSeqIt();
                s2 = new Constant(num, q.next() + q1.next());
            }
            else if(isDelta == true)
            {
                q = s.createSeqIt();
                q1 = s1.createSeqIt();
                s2 = new Delta(num, q.next() + q1.next(), delta);
            }
            else
            {
                q = s.createSeqIt();
                q1 = s1.createSeqIt();
                
                int [] a = new int[num];
                for(int i = 0; i<num; i++)
                {
                    a[i] = q.next() + q1.next();
                }
                s2 = new Jumble(a);
            }
            
        }
		catch(UsingIteratorPastEndException e)
        {
			
		}
		return s2;
	}
}

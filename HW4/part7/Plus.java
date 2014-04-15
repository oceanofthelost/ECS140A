public class Plus
{
	/*
	 * adds two constant series together
	 * @param  C1: constant sequence 1
	 * @param  C2: second constant sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Constant C1, Constant C2)
	{
		return new Constant(C1.num < C2.num ? C1.num : C2.num,C1.value + C2.value);
	}

	/*
	 * adds two delta series together
	 * @param  D1: Delta sequence 1
	 * @param  D2: second Delta sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Delta D1, Delta D2)
	{
		return new Delta(D1.num < D2.num ? D1.num : D2.num,D1.initial + D2.initial, D1.delta + D2.delta);		
	}

	/*
	 * adds two jumble series together
	 * @param  J1: Jumble sequence 1
	 * @param  J2: second Jumble sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Jumble J1, Jumble J2)
	{
		//result will contain the sequence that is smallest
		Jumble result = new Jumble(J1.size < J2.size ? J1.values : J2.values);
		//we find the size of the smallest sequence
		int size = J1.size < J2.size ? J1.size : J2.size;

		//calculate the added value fo the two sequences
		//element by element
		for(int i=0; i<size; i++)
		{
			result.values[i]=J1.values[i]+J2.values[i];
		}

		//return the result of the added sequence
		return result;
 	}

 	/*
	 * adds two sequences together
	 * @param  C: constant sequence
	 * @param  D: delta  sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Constant C, Delta D)
	{
		return new Delta(C.num < D.num ? C.num : D.num,C.value + D.initial,D.delta);
	}

	/*
	 * adds two sequence together
	 * @param  D: Delta sequence 
	 * @param  C: Constant sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Delta D, Constant C)
	{
		return new Delta(C.num < D.num ? C.num : D.num,C.value + D.initial,D.delta);
	}
	
	/*
	 * adds two sequences together
	 * @param  C: Constant sequence
	 * @param  J: Jumble sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Constant C, Jumble J) 
	{
		SeqIt it_C = C.createSeqIt();
		SeqIt it_J = J.createSeqIt();
		int size = 0;
		int[] result;

		try
		{
			while(it_C.hasNext() && it_J.hasNext())
			{
				it_C.next();
				it_J.next();
				size+=1;
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

        result = new int[size];
        it_C = C.createSeqIt();
		it_J = J.createSeqIt();
		try
		{
			for(int i=0;i<size;i++)
			{
				result[i]=it_C.next()+it_J.next();
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

		return new Jumble(result);
	}

	/*
	 * adds two sequences together
	 * @param  J: Jumble sequence
	 * @param  C: constant sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Jumble J, Constant C)
	{
		SeqIt it_C = C.createSeqIt();
		SeqIt it_J = J.createSeqIt();
		int size = 0;
		int[] result;

		try
		{
			while(it_C.hasNext() && it_J.hasNext())
			{
				it_C.next();
				it_J.next();
				size+=1;
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

        result = new int[size];
        it_C = C.createSeqIt();
		it_J = J.createSeqIt();
		try
		{
			for(int i=0;i<size;i++)
			{
				result[i]=it_C.next()+it_J.next();
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

		return new Jumble(result);
	}

	/*
	 * adds two sequences together
	 * @param  D: Delta sequence 
	 * @param  J: Jumble sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Delta D,Jumble J)
	{
		SeqIt it_D = D.createSeqIt();
		SeqIt it_J = J.createSeqIt();
		int size = 0;
		int[] result;

		try
		{
			while(it_D.hasNext() && it_J.hasNext())
			{
				it_D.next();
				it_J.next();
				size+=1;
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

        result = new int[size];
        it_D = D.createSeqIt();
		it_J = J.createSeqIt();
		try
		{
			for(int i=0;i<size;i++)
			{
				result[i]=it_D.next()+it_J.next();
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

		return new Jumble(result);
	}

	/*
	 * adds two sequences together
	 * @param  J: Jumble sequence
	 * @param  D: Delta sequence
	 * @return    new sequence that is sequence 1 
	 *            added to sequence 2. 
	 */
	public static Seq plus(Jumble J, Delta D)
	{
		SeqIt it_D = D.createSeqIt();
		SeqIt it_J = J.createSeqIt();
		int size = 0;
		int[] result;

		try
		{
			while(it_D.hasNext() && it_J.hasNext())
			{
				it_D.next();
				it_J.next();
				size+=1;
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

        result = new int[size];
        it_D = D.createSeqIt();
		it_J = J.createSeqIt();
		try
		{
			for(int i=0;i<size;i++)
			{
				result[i]=it_D.next()+it_J.next();
			}
		}
		catch (UsingIteratorPastEndException e) 
		{
			System.out.println("myprintc oops! caught UsingIteratorPastEndException");
        }

		return new Jumble(result);
	}
}
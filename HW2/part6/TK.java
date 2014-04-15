/* *** This file is given as part of the programming assignment. *** */

// Token Kind (internal representations of tokens)

public class TK {
	
	private String str;
	
	private TK(String str) {
		this.str = str;
	}
    
    public String toString(){
        return str;
    }

 	public static TK VAR = new TK("VAR");     // var
	public static TK RAV = new TK("RAV");     // rav
	public static TK PRINT = new TK("PRINT"); // print
	public static TK IF = new TK("IF");     // if
	public static TK FI = new TK("FI");     // fi
	public static TK DO = new TK("DO");     // do
	public static TK OD = new TK("OD");     // od
	public static TK ELSE = new TK("ELSE");    // else
 	public static TK FA = new TK("FA");     // fa
	public static TK AF = new TK("AF");      // af
	public static TK TO = new TK("TO");      // to
	public static TK ST = new TK("ST");      // st
  
	public static TK ASSIGN = new TK("ASSIGN");   // :=
	public static TK LPAREN = new TK("LPAREN");   // (
	public static TK RPAREN = new TK("RPAREN");   // )
	public static TK PLUS = new TK("PLUS");     // +
    public static TK MINUS = new TK("MINUS"); //-
	public static TK TIMES = new TK("TIMES");    // *
	public static TK DIVIDE = new TK("DIVIDE");   // /

	public static TK EQ = new TK("EQ");       // =
	public static TK NE = new TK("NE");       // /=
	public static TK LT = new TK("LT");       // <
	public static TK GT = new TK("GT");       // >
	public static TK LE = new TK("LE");       // <=
	public static TK GE = new TK("GE");      // >=
    public static TK SQUARE = new TK("^");   // ^
    public static TK ROOT = new TK("@");    // @

	public static TK ARROW = new TK("ARROW");    // ->
	public static TK BOX = new TK("BOX");      // []

	public static TK ID = new TK("ID");       // identifier

	public static TK NUM = new TK("NUM");      // number

	public static TK EOF = new TK("EOF");      // end of file

	// ERROR special error token kind (for scanner to return to parser)
	public static TK ERROR = new TK("ERROR");
	// none marks end of each first set in parsing.
	// you might not need this.
	public static TK none = new TK("none");
}


import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


public class ErrorListener extends BaseErrorListener{
	private StringBuilder buf = new StringBuilder();
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
			Object offendingSymbol,
			int line, int charPositionInLine,
			String msg,
			RecognitionException e)
	{
	List<String> stack = (( Parser)recognizer).getRuleInvocationStack();
	Collections.reverse(stack);
	buf.append( "\nError found in: " +stack+"" );
	buf.append( "\nline " + line + " char " + charPositionInLine + ": " + msg + "\n");
	}
	
	public String errorMessage(){
		if (buf.toString().isEmpty()){
			return "";
		}
		return buf.toString();
	}
}

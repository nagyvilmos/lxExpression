/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexa.core.expression;

import lexa.core.expression.function.FunctionLibrary;
import lexa.core.expression.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import lexa.core.data.DataSet;

/**
 *
 * @author William
 * @since YYYY-MM
 */
public class FunctionCall
        extends Expression {

	static void function(ExpressionTokens tokens)
			throws ExpressionException
	{
		int start= tokens.getPointer();

		if (tokens.getType() != WordType.START_FUNCTION)
		{
			throw new ExpressionException("Start of function expected\n" + tokens.getPhrase(start-1,start+1));
		}
		String name = tokens.next().getWord();
		if (new Element(name).isLiteral())
		{
			throw new ExpressionException("Expected function ID\n" + tokens.getPhrase(start,start + 2));
		}
		boolean done = false;
		tokens.next();
		ArrayList<Expression> args = new ArrayList<>();
		while (!done && !tokens.done())
		{
			if (tokens.getType() == WordType.END_FUNCTION)
			{
				done = true;
				continue;
			}
			Element.element(tokens);
			args.add(tokens.expression());
		}
		if (!done)
		{
			throw new ExpressionException("Missing end of function for " + name + '\n' + tokens.getPhrase(start,start + 2));
		}
		tokens.setExpression(new FunctionCall(tokens.getLibrary(), name, args));
		tokens.next();
	}

    private final Function function;
    private final Expression[] argumentExpressions;
    FunctionCall(FunctionLibrary library, String name, ArrayList<Expression> args)
			throws ExpressionException
	{
        this.function = library.getFunction(name);
        this.argumentExpressions = args.toArray(new Expression[args.size()]);
    }

    @Override
    public Object evaluate(DataSet data) throws ExpressionException {
        return this.function.evaluate(data,this.argumentExpressions);
    }

    @Override
    public String toString() {
        return "function{" + function.getName() + ' ' + Arrays.toString(argumentExpressions)  + '}';
    }
}

/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * package-info.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-08-09   WNW -           Added support for expression lists and conditionals.
 * 2013-08-11   WNW -           Added basic function support.
 * 2013-10-02	WNW -			Tidy up for build.
 * 2014-07-18	WNW	-			Huge refactoring to get rid of the parser.
 *								Responsibility now of the Expression classes.
 *================================================================================
 */
/**
 * Provides the classes for expression evaluation.
 * <p>Expressions are built using the {@link lexa.core.expression.Expression#parse Expression.parse} function
 * that takes a {@link java.lang.String} and converts it to a block of expressions.
 * When evaluating an expression, an optional {@link lexa.core.data.DataSet} is used to provide
 * the data for any calculations.
 * <p>An example usage of the parser would be:
 * <pre>
 * Expression ex = Expression.parse("id &gt;= 20 &amp;&amp; id &lt;= 30"); // get id's between 20 and 30 inclusive.
 * DataSet data = new DataSet();
 * data.put("id", 33);
 * system.out.println(ex.evaluate(data)); // returns false</pre>
 * 
 * <p>Expressions strings describe the expression to be built.  As the expression is parameterised with a
 * {@link lexa.core.data.DataSet} it can be used multiple times and provide different results depending
 * on the input data.
 * <p>The format for a valid expression is:
 * <pre>
 * EXPRESSION     ::= EXPRESSION ";" EXPRESSION
 *                  | ID "=" PRIMARY
 *                  | LOGICAL_OR
 * LOGICAL_OR     ::= LOGICAL_AND "||" LOGICAL_OR
 *                  | LOGICAL_AND
 * LOGICAL_AND    ::= COMPARE "&amp;&amp;" LOGICAL_AND
 *                  | COMPARE
 * COMPARE        ::= SUM "&lt;" SUM
 *                  | SUM "&lt;=" SUM
 *                  | SUM "==" SUM
 *                  | SUM "!=" SUM
 *                  | SUM "&gt;=" SUM
 *                  | SUM "&gt;" SUM
 *                  | SUM
 * SUM            ::= TERM "+" SUM
 *                  | TERM "-" SUM
 *                  | TERM
 * TERM           ::= FACTOR "*" TERM
 *                  | FACTOR "/" TERM
 *                  | FACTOR
 * FACTOR         ::= CONDITION "^" FACTOR
 *                  | CONDITION "%" FACTOR
 *                  | CONDITION
 * CONDITION      ::= PRIMARY "?" PRIMARY ":" PRIMARY
 *                  | PRIMARY
 * PRIMARY        ::= "-" ELEMENT
 *                  | "!" ELEMENT
 *                  | "@" ELEMENT
 *                  | ELEMENT
 * ELEMENT        ::= ID
 *                  | CONSTANT
 *                  | "(" EXPRESSION ")"
 *                  | "[" FUNCTION "]"
 * FUNCTION       ::= ID
 *                  | ID ARGUMENTS
 * ARGUMENTS      ::= PRIMARY
 *                  | PRIMARY ARGUMENTS
 * </pre>
 * <p>Expressions can be concatenated together to produce a script.  Each expression is 
 * evaluated in order as long as the results are not null.  The result of the last
 * expression evaluated is then returned.
 * 
 * <p><b>TO DO</b>
 * <ul>
 * <li>Looping of some form</li>
 * </ul>
 *
 * @author William N-W
 * @since 2013-03
 */
package lexa.core.expression;

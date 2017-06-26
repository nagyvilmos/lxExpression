/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionTest.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *================================================================================
 */

package lxexpression;

import lexa.test.TestClass;
import lexa.test.TestRun;

/**
 * Tester for the expression evaluator.
 * <br>
 * Uses a {@link lexa.core.data.DataSet} file to store test expressions.
 * @author William
 * @since 2013-03
 * @see lexa.core.data
 * @see lexa.core.expression
 * @see lexa.core.expression.function
 * @see lexa.core.expression.function.standard
 */
public class ExpressionTest {

    /**
     *
     * @param args
     */
    public static void main(String ... args)
    {
        TestClass[] tests = new TestClass[]
        {
            new TestExpressionParsing(),
            new TestFunctionLibrary(),
            new TestMappedData(),
            new TestExpressionScript(
                    (args != null && args.length > 0) ?
                            args[0] :
                            "test.expression.lexa")
        };
        System.out.println(
                new TestRun(tests)
                        .execute()
                        .getReport(false, true)
        );
    }

   }


/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionTest.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2013-08-10   WNW             Moved into main project to make testing a monkey's
 *                              tadger easier.
 * 2013-10-02	WNW				Make the test support function overload
 * 2013-10-24	WNW				Clean up code & comments.
 * 2014-07-11	WNW	v2			Redux - Lose the expression builder [eek]
 *================================================================================
 */

package lxExpression;

//import java.io.*;

import lexa.test.TestClass;
import lexa.test.TestRun;

/**
 * Tester for the expression evaluator.
 * <br>
 * Uses a {@see DataSet} file to store test expressions.
 * @author William
 * @since 2013-03
 * @see lexa.core.data
 * @see lexa.core.expression
 * @see lexa.core.expression.function
 * @see lexa.core.expression.function.standard
 */
public class ExpressionTest {
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
                        .getReport()
        );  
    }

   }


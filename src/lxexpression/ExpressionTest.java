/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ExpressionTest.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *==============================================================================
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
     * Test the expression engine
     *
     * <p>If an argument is provided then the file is tested other wise this
     * runs a standard set of tests for the expressions engine.
     *
     * @param args the name of the test file if required.
     */
    public static void main(String ... args)
    {
        TestClass[] tests = args != null ?
                new TestClass[]
                {
                    new TestExpressionScript(args[0])
                } :
                new TestClass[]
                {
                    new TestExpressionParsing(),
                    new TestFunctionLibrary(),
                    new TestMappedData(),
                     new TestExpressionScript("test.expression.lexa")
                };

        System.out.println(
                new TestRun(tests)
                        .execute()
                        .getReport(false, true)
        );
    }

   }


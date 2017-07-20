/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * TestExpressionScript.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2017
 *==============================================================================
 */
package lxexpression;

import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 * Test the {@link lexa.core.expression.map} package
 * @author  williamnw
 * @since   2017-07
 */
public class TestMappedData extends TestClass
{

    /**
     *
     */
    public TestMappedData()
    {
    }


    @TestAnnotation
    public TestResult mapNotImplimented()
    {
        // just so we know it doesn't work yet
        return TestResult.result(false);
    }
}

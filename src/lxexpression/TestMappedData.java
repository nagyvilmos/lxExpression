/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxexpression;

import lexa.test.TestAnnotation;
import lexa.test.TestClass;
import lexa.test.TestResult;

/**
 *
 * @author william
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

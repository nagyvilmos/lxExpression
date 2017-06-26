/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This provides internal functions for the Lexa Expression Parser.
 * <p>These are added to the {@link lexa.core.expression.function.FunctionLibrary function library}
 * and are available to all {@link lexa.core.expression.Expression expressions}.
<p>The functions supported are grouped by type:
<dl>
<dt>{@link lexa.core.expression.function.standard.DataFunctions DataFunctions}</dt>
	<dd>Allows the manipulation of complete {@link lexa.core.data.DataSet data sets}.</dd>
<dt>{@link lexa.core.expression.function.standard.MathsFunctions MathsFunctions}</dt>
	<dd>Mathematical functions such as trigonometry.</dd>
<dt>{@link lexa.core.expression.function.standard.NullFunctions NullFunctions}</dt>
	<dd>Handlers for when values are null.</dd>
<dt>{@link lexa.core.expression.function.standard.StringFunctions StringFunctions}</dt>
	<dd>String manipulation functions</dd>
</dl>
 */
package lexa.core.expression.function.standard;

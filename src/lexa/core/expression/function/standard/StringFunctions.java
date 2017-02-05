/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.function.Function;

/**
 *
 * The format is [string.function *]
 * @author william
 */
public class StringFunctions
{
	private StringFunctions()
	{
	
	}
	
	public static Function[] getFunctions()
	{
		Function[] functions =
		{
			ends(),
			find(),
			findAfter(),
			findBefore(),
			findLast(),
			format(),
			length(),
			lower(),
			matches(),
			replace(),
			replaceFirst(),
			starts(),
			substring(),
			upper()	
		};

		return functions;
	}

	private static Function ends()
	{
		return new StaticFunction("string.ends", "string", "end")
		{
			@Override
			public String describe()
			{
				return "check if a string ends with a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").endsWith(arguments.getString("end"));
			}
		};
	}

	private static Function find()
	{
		return new StaticFunction("string.find", "string", "find")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").indexOf(arguments.getString("find"));
			}
		};
	}
	private static Function findAfter()
	{
		return new StaticFunction("string.findAfter", "string", "find", "start")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value after a given postion";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").indexOf(arguments.getString("find"),
							arguments.getInteger("start"));
			}
		};
	}

	private static Function findBefore()
	{
		return new StaticFunction("string.findBefore", "string", "find", "before")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value before a given postion";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").lastIndexOf(arguments.getString("find"),
							arguments.getInteger("before"));
			}
		};
	}

	private static Function findLast()
	{
		return new StaticFunction("string.findLast", "string", "find")
		{
			@Override
			public String describe()
			{
				return "check if a string contains a value from the back";
			}
			@Override
			public Object execute(DataSet arguments)
			{
					return arguments.getString("string").lastIndexOf(arguments.getString("find"));
			}
		};
	}

	private static Function format()
	{
		
		return new StaticFunction("string.format", "string", "~")
					// ~ the asteriks means 0 or many more args
		{
			@Override
			public String describe()
			{
				return "create a formated string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				Object[] args = new Object[arguments.size()-1];
				for (int a = 0;
						a < args.length;
						a++) {
					Object o = arguments.getValue("~" + a);
					args[a] = o == null ? ":NULL:" : o;
				}
				String fs = arguments.getString("string");
				return String.format(fs, args);
			}
		};
	}

	private static Function length()
	{
		return new StaticFunction("string.length", "string")
		{
			@Override
			public String describe()
			{
				return "get the length of a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").length();
			}
		};
	}

	private static Function lower()
	{
		return new StaticFunction("string.lower", "string")
		{
			@Override
			public String describe()
			{
				return "convert a string to lower case";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").toLowerCase();
			}
		};
	}

	private static Function matches()
	{
		return new StaticFunction("string.matches", "string", "regrxp")
		{
			@Override
			public String describe()
			{
				return "check if a string matches a regular expression";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").matches(arguments.getString("regrxp"));
			}
		};
	}

	private static Function replace()
	{
		return new StaticFunction("string.replace", "string", "search", "replace")
		{
			@Override
			public String describe()
			{
				return "replace all values in a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").replaceAll(
						arguments.getString("search"), arguments.getString("search"));
			}
		};
	}
	private static Function replaceFirst()
	{
		return new StaticFunction("string.replaceFirst", "string", "search", "replace")
		{
			@Override
			public String describe()
			{
				return "replace the first occurance of a value in a string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").replaceFirst(
						arguments.getString("search"), arguments.getString("search"));
			}
		};
	}

	private static Function starts()
	{
		return new StaticFunction("string.starts", "string", "search")
		{
			@Override
			public String describe()
			{
				return "check if a string starts with a value";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").
						startsWith(arguments.getString("search"));
			}
		};
	}

	private static Function substring()
	{
		return new StaticFunction("string.sub", "string", "start", "end")
		{
			@Override
			public String describe()
			{
				return "create a substring from positions start to end";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").substring(
						arguments.getInteger("start"), arguments.getInteger("end"));
			}
		};
	}

	private static Function upper()
	{
		return new StaticFunction("string.upper", "string")
		{
			@Override
			public String describe()
			{
				return "convert a string to upper case";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getString("string").toUpperCase();
			}
		};
	}
}

/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ExpressionTokens.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *==============================================================================
 */
package lexa.core.expression;

import java.util.Arrays;
import lexa.core.expression.function.FunctionLibrary;

/**
 * Tokenised string for parsing an expression
 * @author  williamnw
 * @since   2013-03
 */
final class ExpressionTokens
{

	/** the input text for the expression */
	private final String expressionText;
	/** the index of the word starts in the input text */
	private final int[] words;
	/** the types of each of the words */
	private final WordType[] types;
	/** current pointer */
	private int pointer;
	/** the expression built from the input */
	private Expression expression;
	private final FunctionLibrary library;

	FunctionLibrary getLibrary()
	{
		return library;
	}

	ExpressionTokens(String expressionText, FunctionLibrary library)
	{
		this.expressionText = expressionText.trim();
		this.library = library;
		int[] wordBuilder = new int[this.expressionText.length() + 1];
		boolean inWord = false;
		boolean inLiteral = false;
		char endOfLitteral = '"'; // only matters when in litteral is set.
		int w = 0;
		final String WHITE_SPACE = " \t\n";
		final String SINGLE_CHARACTER_WORD = "+-/*()?:;^%[]@";
		for (int c = 0;
				c < this.expressionText.length();
				c++)
		{
			char ch = this.expressionText.charAt(c);
			if (inLiteral)
			{
				if ('\\' == ch)
				{
					// skip the next character;
					c++;
				}
				else
				{
					if (ch == endOfLitteral)
					{
						inLiteral = false;
						inWord = false;
					}
				}
			}
			else
			{
				// is this a special word?
				if (SINGLE_CHARACTER_WORD.indexOf(ch, 0) != -1)
				{
					// single character;
					wordBuilder[w++] = c;
					inWord = false;
				}
				else
				{
					if ("!=<>".indexOf(ch, 0) != -1)
					{
						wordBuilder[w++] = c;
						inWord = false;
						if (c + 1 < this.expressionText.length() &&
								 "=<>".indexOf(this.expressionText.charAt(c + 1), 0) != -1)
						{
							c++;
						}
					}
					else
					{
						if (inWord)
						{
							if (WHITE_SPACE.indexOf(ch, 0) != -1)
							{
								inWord = false;
							}
						}
						else
						{
							if (WHITE_SPACE.indexOf(ch, 0) == -1)
							{
								if ("\"'".indexOf(ch) != -1)
								{
									inLiteral = true;
									endOfLitteral = ch;
								}
								wordBuilder[w++] = c;
								inWord = true;
							}
						}
					}
				}
			}
		}
		// last word:
		wordBuilder[w++] = this.expressionText.length();
		this.words = Arrays.copyOf(wordBuilder, w);
		this.types = new WordType[w];
		for (int t = 0;
				t < w;
				t++)
		{
			this.types[t] = WordType.getType(getWord(t));
		}
		this.expression = null;
		this.pointer = 0;
	}

	/**
	 * Get a word.
	 * @param word The required word
	 * @return The word.
	 */
	String getWord(int word)
	{
		if (word < 0 || word >= this.getWordCount())
		{
			return null;
		}
		return this.expressionText.substring(
				this.words[word],
				this.words[word + 1]).trim();
	}

	/**
	 * Get the number of words
	 * @return The number of words.
	 */
	int getWordCount()
	{
		return this.words.length - 1;
	}

	/**
	 * Get the type of a word.
	 * @param word The required word
	 * @return The type of word.
	 */
	WordType getType(int word)
	{
		if (word < 0)
		{
			return getType(0);
		}
		else
		{
			if (word >= this.getWordCount())
			{
				return WordType.NULL;
			}
		}
		return this.types[word];
	}

	String getPhrase(int start, int end) {
		int startPos =
				(start < 0) ? 0 :
				(start >= this.words.length-1) ? this.words.length-2: start;
		int endPos =
				(end < start) ? start :
				(end >= this.words.length-1) ? this.words.length-2: end;
		startPos = this.words[startPos];
		endPos = this.words[endPos+1];

		return this.expressionText.substring(startPos, endPos);

	}

	Expression expression()
	{
		return this.expression;
	}

	boolean done()
	{
		//DANGER - is it > or >= ?
		return (this.pointer >= this.getWordCount());
	}

	int getPointer()
	{
		return this.pointer;
	}
	void setExpression(Expression expression)
	{
		this.expression = expression;
	}

	WordType getType()
	{
		return getType(this.pointer);
	}
	ExpressionTokens movePointer(int offset)
	{
		this.pointer = this.pointer + offset;
		return this;
	}

	ExpressionTokens previous()
	{
		return movePointer(1);
	}
	ExpressionTokens next()
	{
		return movePointer(1);
	}

	String getWord()
	{
		return this.getWord(this.getPointer());
	}

    @Override
    public String toString()
    {
        return '{' + this.expressionText + "} @ {" + this.getWord() + '}';
    }
}

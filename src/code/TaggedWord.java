package code;

public class TaggedWord {
	
	String word;
	String tag;
	public TaggedWord(String word, String tag)
	{
		this.word=word;
		this.tag=tag;
	}
	public String getWord()
	{
		return word;
	}
	public String getTag()
	{
		return tag;
	}
	public void setWord(String s)
	{
		this.word=s;
		
	}
	public void setTag(String s)
	{
		this.tag=s;
		
	}

}

package code;

import java.util.*;

public class NumeralLexiconNormaliser {
	
ArrayList<Sentence> allSentences=new ArrayList<Sentence>();
	
	public NumeralLexiconNormaliser(String tagged)
	{
	
		String[] sentences=tagged.split(" \\.\\_\\. ");
		for(int i=0;i<sentences.length;i++)
		{
			
			Sentence s=new Sentence(sentences[i]);
			allSentences.add(s);
			
			
		}
		//construct 'Sentence's from the string and store them on the arraylist allSentence
		//// ( which stores the original sentence and the tagged sentence)
	}
	
	public String replaceWordsWithDigits(int digitsOfPrecision)
	{
		String writeToFile="";
		for(int i=0;i<allSentences.size();i++)
		{
			writeToFile+=allSentences.get(i).replaceWithDigits(digitsOfPrecision);
			if(!writeToFile.endsWith(" ? ") || !writeToFile.endsWith(" ! "))
				writeToFile+=". ";
		}
		
		return writeToFile;
		//for all the ‘Sentence's, call the method replaceWithDigits(), the method returns a string, which is returned to the main to be written in the Output file
	}

}

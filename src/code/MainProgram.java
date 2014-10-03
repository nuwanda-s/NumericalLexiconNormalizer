package code;

import java.io.*;
import java.util.Scanner;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class MainProgram {
	
	public static void main(String[] arg) throws IOException
	{
		String[] args=new String[2];
		//get user to enter the name of input file and its path
		//There are two command line arguments : args[0] - input file's path  and args[1]-input file's name (Eg : Input.txt)
		//Here we have provided the path and name of input file manually using the variable String args[2]
		
		//please provide Digits Of Precision required in case of decimal numbers as a command line argument
		
		args[0]="E:\\Workspace\\NumericalLexiconNormalizer\\src\\code\\";
		args[1]="Input.txt";
		
		Scanner sc=new Scanner("System.in");
		
		int digitsOfPrecision;
		
		try{
			
			File inputFile=new File(args[0]+args[1]);
			FileReader fr=new FileReader(inputFile);
			BufferedReader textReader=new BufferedReader(fr);
	
			MaxentTagger tagger = new MaxentTagger("E:\\workspace\\NumericalLexiconNormalizer\\src\\taggers\\english-left3words-distsim.tagger");//model
		
			String sample="";	//original string which will later on store input file’s contents
			String line=null;
			while((line=textReader.readLine())!=null)
			{
				sample+=line;
			}
		
			String tagged = tagger.tagString(sample); //tagged words string
			
			NumeralLexiconNormaliser nln=new NumeralLexiconNormaliser(tagged);
			System.out.println(tagged);
			
			System.out.println("Please enter the digits of precision in case of decimal numbers as a command line Argument:");
			//digitsOfPrecision=Integer.parseInt(arg[0]);
			digitsOfPrecision=4;
			
			String parsed = nln.replaceWordsWithDigits(digitsOfPrecision);
			System.out.println(parsed);
			
			try{
				Writer textWriter;
				File outputFile= new File(args[0]+"\\Output.txt");
				FileWriter fw=new FileWriter(outputFile);
				textWriter=new BufferedWriter(fw);
				
				textWriter.write(parsed);	//writing the parsed string to the Output file
				textWriter.flush();
				
				fw.close();
			}
			
			catch(Exception ex){
				
				System.out.println(ex);
				
			}
	
			fr.close();
				
		}
		catch(Exception e){
			
			System.out.println(e);
			System.exit(1);
		
		}
		
		sc.close();
}

}

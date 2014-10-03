package code;

import java.util.*;

public class Words {

	private final Hashtable<String, Double>  weightsIndian= new Hashtable<String, Double>();
	private final Hashtable<String, Double>  weightsInternational= new Hashtable<String, Double>();
	private final Hashtable<String, Double>  numbers= new Hashtable<String, Double>();
	private final Hashtable<String, Double>  digits= new Hashtable<String, Double>();
	private final Hashtable<String, Double> tens=new Hashtable<String, Double>();
	private final Hashtable<String, Double>  nonCD= new Hashtable<String, Double>();
	private final Hashtable<String, Double>  nonCDfractions= new Hashtable<String, Double>();
	private final Hashtable<String, Integer> ordinalNumbers=new Hashtable<String, Integer>();
	private final Hashtable<String, Integer> ordinalWeights=new Hashtable<String, Integer>();
	private boolean isInternational,isIndian;
	
	
		public Words(){
		/*the constructor calls addWords() and sets isIndian and IsInternational as false.*/

		addWords();
		isInternational=false;
		isIndian=false;
	}
	
	
	public void addWords(){
		
		/*this method fills up the hash tables.*/
		digits.put("zero", 0.0);
		digits.put("one", 1.0);
		digits.put("two", 2.0);
		digits.put("three",3.0);
		digits.put("four", 4.0);
		digits.put("five", 5.0);
		digits.put("six", 6.0);
		digits.put("seven", 7.0);
		digits.put("eight", 8.0);
		digits.put("nine", 9.0);
		numbers.put("ten",10.0);
		numbers.put("eleven", 11.0);
		numbers.put("twelve", 12.0);
		numbers.put("thirteen", 13.0);
		numbers.put("fourteen", 14.0);
		numbers.put("fifteen",15.0);
		numbers.put("sixteen",16.0);
		numbers.put("seventeen",17.0);
		numbers.put("eighteen",18.0);
		numbers.put("nineteen",19.0);
		tens.put("twenty",20.0);
		tens.put("thirty",30.0);
		tens.put("forty",40.0);
		tens.put("fifty",50.0);
		tens.put("sixty",60.0);
		tens.put("seventy",70.0);
		tens.put("eighty",80.0);
		tens.put("ninety",90.0);
		
		weightsIndian.put("hundred", 100.0);
		weightsIndian.put("thousand", 1000.0);
		weightsIndian.put("lakh", 100000.0);
		weightsIndian.put("crore", 10000000.0);
		weightsIndian.put("arab", 1000000000.0);
		weightsIndian.put("kharab", 1000000000000.0);
		
		weightsInternational.put("hundred", 100.0);
		weightsInternational.put("thousand", 1000.0);
		weightsInternational.put("million", 1000000.0);
		weightsInternational.put("billion", 1000000000.0);
		weightsInternational.put("trillion", 1000000000000.0);
		
		ordinalNumbers.put("first", 1);
		ordinalNumbers.put("second", 2);
		ordinalNumbers.put("third", 3);
		ordinalNumbers.put("fourth", 4);
		ordinalNumbers.put("fifth", 5);
		ordinalNumbers.put("sixth", 6);
		ordinalNumbers.put("seventh", 7);
		ordinalNumbers.put("eighth", 8);
		ordinalNumbers.put("ninth", 9);
		ordinalNumbers.put("tenth", 10);
		ordinalNumbers.put("eleventh", 11);
		ordinalNumbers.put("twelfth", 12);
		ordinalNumbers.put("thirteenth", 13);
		ordinalNumbers.put("fourteenth", 14);
		ordinalNumbers.put("fifteenth", 15);
		ordinalNumbers.put("sixteenth", 16);
		ordinalNumbers.put("seventeenth", 17);
		ordinalNumbers.put("eighteenth", 18);
		ordinalNumbers.put("nineteenth", 19);
		ordinalNumbers.put("twentieth", 20);
		ordinalNumbers.put("hundredth",100);
		ordinalNumbers.put("thousandth",1000);
		ordinalNumbers.put("millionth",1000000);
		ordinalNumbers.put("billionth",1000000000);
		
		ordinalWeights.put("hundredth",100);
		ordinalWeights.put("thousandth",1000);
		ordinalWeights.put("millionth",1000000);
		ordinalWeights.put("billionth",1000000000);
		
		nonCDfractions.put("half", 0.50);
		nonCDfractions.put("quarter", 0.25);
		
		nonCD.put("dozen", 12.0);
		nonCD.put("both", 2.0);
		nonCD.put("nil",0.0);
		nonCD.put("a", 1.0);
		
	}
	
	/*these two methods return the value to which the specified key is mapped 
	 * (in the respective hash tables of ‘Words’, or null if this map contains no mapping for the key.
*/
	public Double getKeyIndian(String word){
		return weightsIndian.get(word);
	}
	
	public Double getKeyInternational(String word){
		return weightsInternational.get(word);
	}
	
	public Double getKeyTimeHours(String hour)
	{
		if(digits.containsKey(hour))
			return digits.get(hour);
		else if(hour.equalsIgnoreCase("eleven"))  
			return 11.0;
		else if(hour.equalsIgnoreCase("twelve"))
			return 12.0;
		else 
			return -1.0; 
	}
	
	public boolean containsNonCDWord(String numString){
		/*returns true when numString is a NonCD word, i.e. it's a key in the 'nonCD' Hashtable*/
		return nonCD.containsKey(numString);
	}
	
	public boolean containsNonCDFractionWord(String numString){
		/*returns true when numString is a NonCDFraction word, i.e. it's a key in the 'nonCDfractions' Hashtable*/
		return nonCDfractions.containsKey(numString);
	}
	
	public boolean containsOrdinalNumbers(String numString){
		/*returns true when numString is an ordinal, i.e. it's a key in the 'ordinalNumbers' Hashtable*/
		return ordinalNumbers.containsKey(numString);
	}
	
	public boolean containsIndianWeights(String numString){
		/*returns true when numString is an Indian number system's 'weight', i.e. it's a key in the 'weightsIndian' Hashtable*/
		return weightsIndian.containsKey(numString);
	}

	public boolean containsInternationalWeights(String numString){
		/*returns true when numString is an international number system's 'weight', i.e. it's a key in the 'weightsInternational' Hashtable*/
		return weightsInternational.containsKey(numString);
	}
	
	public boolean containsTens(String numString){
		/*returns true when numString is a key in the 'tens' Hashtable*/
		return tens.containsKey(numString);
	}
	public boolean containsDigits(String numString){
		/*returns true when numString is a key in the 'digits' Hashtable*/
		return digits.containsKey(numString);
	}
	
	public boolean containsNumbers(String numString){
		/*returns true when numString is a key in the 'numbers' Hashtable*/
		return numbers.containsKey(numString);
	}
	
	public boolean containsWord(String numString){
		/*returns true if numString is a key in ANY of the Hashtables*/
		if( containsNonCDWord(numString) || containsNonCDFractionWord(numString) || containsOrdinalNumbers(numString) || containsIndianWeights(numString) || containsInternationalWeights(numString) || containsTens(numString) || containsDigits(numString) || containsNumbers(numString) )
			return true;
		else 
			return false;
	}
	
	public void setWeightSystem(){
		/*resets ‘isInternational’ and ‘isIndian’ variables.
	We need this method to ensure that if in a sentence there are two numString one containing Indian and 
	another containing International weights (like both ‘lakh’ and ‘billion’ in the same sentence). 
	When one numString is encountered one of the system of weights is set true
 and hence before evaluating the other numString, we need to set the current system of weights to false.*/
		isIndian=false;
		isInternational=false;
	}
	
	

	
	
	
	
	public Double searchNumericalEquivalent(ArrayList<TaggedWord> numString){
		/*
We use this method for finding the numerical equivalents of non-negative whole numbers and a few fractional words like half and quarter. The following types of cases are evaluated by this method:
1. one billion twelve thousand
2. lakh
3. half a dozen
4. half million
*/
		
		Double sumToBeReturned=0.0,productToBeReturned=1.0;
		boolean indexFound=false, returnProduct=false, hasWholeNumber=false, hasAnd=false;
		Double number;
		number=removeWeight("trillion", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("kharab", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("billion", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("arab", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("crore", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("million", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		number=removeWeight("lakh", numString);
		sumToBeReturned+=number;
		if(number==-1)
			return -1.0;	//wrong Input
		sumToBeReturned+=removeWeight("thousand", numString);
		sumToBeReturned+=removeWeight("hundred", numString);

		
		if(numString.size()>1)
		{
			
			if( (numbers.containsKey(numString.get(0).getWord())|| digits.containsKey(numString.get(0).getWord()) ) && tens.containsKey(numString.get(1).getWord()) )
			{	
				
				if(numbers.containsKey(numString.get(0).getWord()) && tens.containsKey(numString.get(1).getWord()))
					sumToBeReturned+=100*numbers.get(numString.remove(0).getWord());
				else if(digits.containsKey(numString.get(0).getWord()) && tens.containsKey(numString.get(1).getWord()))
					sumToBeReturned+=100*digits.get(numString.remove(0).getWord());
					
			}	
		}
		
		while(numString.size()!=0 ){
			
			if(numString.get(0).getWord().equalsIgnoreCase("point"))
				break;
			if(digits.containsKey(numString.get(0).getWord()) && !returnProduct )
			{
				hasWholeNumber=true;							//there is a whole number at start so usually consecutive numbers are to be added  Eg: two and a half
				sumToBeReturned+=digits.get(numString.remove(0).getWord());
			}
			else if(numbers.containsKey(numString.get(0).getWord()) && !returnProduct)		//for cases like half of nine
			{																				//since half is encountered before hand returnProduct is true so whole num is to
				hasWholeNumber=true;														//be multiplied to half and not to be added to sumToBeReturned
				sumToBeReturned+=numbers.get(numString.remove(0).getWord());
			}
			else if(tens.containsKey(numString.get(0).getWord()) && !returnProduct)
			{
				hasWholeNumber=true;
				sumToBeReturned+=tens.get(numString.remove(0).getWord());
			}
			else if(numString.get(0).getWord().equals("and"))
			{
				hasAnd=true;
				numString.remove(0);
			}
			else if(nonCDfractions.containsKey(numString.get(0).getWord()) && hasWholeNumber && hasAnd)
			{
				sumToBeReturned+=nonCDfractions.get(numString.remove(0).getWord());	//words like half/quarter are added if preceded by a whole number  Eg: two and a half
			}
			
			else if( ( nonCD.containsKey(numString.get(0).getWord()) || nonCDfractions.containsKey(numString.get(0).getWord()) || numString.get(0).getTag().equals("CD")) && !hasWholeNumber && !hasAnd )
			{		
				returnProduct=true;														//numbers are to be multiplied as in eg: half a dozen,half a quarter,half of twenty					
				if(nonCD.containsKey(numString.get(0).getWord()))
					productToBeReturned*=nonCD.get(numString.remove(0).getWord());
				else if(nonCDfractions.containsKey(numString.get(0).getWord()))
					productToBeReturned*=nonCDfractions.get(numString.remove(0).getWord());
				else if( numString.get(0).getTag().equals("CD") )
					productToBeReturned*=searchNumericalEquivalent(numString);
			}
			else if( (nonCD.containsKey(numString.get(0).getWord()) || nonCDfractions.containsKey(numString.get(0).getWord())) && hasWholeNumber && !hasAnd)
			{
				returnProduct=true;							//if dozen is encountered then the whole number before it is to be multiplied but the whole number is till now
				if( numString.get(0).getWord().equals("dozen") )
				{	productToBeReturned*=12*sumToBeReturned;	// stored in sumToBeReturned For eg: two and a half dozen, twenty six dozen
					numString.remove(0);
				}
				else if(numString.get(0).getWord().equals("quarter"))
				{
					productToBeReturned*=0.25*sumToBeReturned;	// stored in sumToBeReturned For eg: two and a half dozen, twenty six dozen
					numString.remove(0);
				}
				else if(numString.get(0).getWord().equals("half"))
				{
					productToBeReturned*=0.5*sumToBeReturned;	// stored in sumToBeReturned For eg: two and a half dozen, twenty six dozen
					numString.remove(0);
				}
				else
				{
					productToBeReturned*=1.0*sumToBeReturned;	// stored in sumToBeReturned For eg: two and a half dozen, twenty six dozen
					numString.remove(0);
				}
			}
			else
				numString.remove(0);
			
		}
		
		
		indexFound=false;
		if(numString.size()>0  && numString.get(0).getWord().equals("point")) 
			indexFound=true; 

		int powerOfTen=1;
		if(indexFound){
			numString.remove(0);
			while(numString.size()>0 ){
				if(digits.containsKey(numString.get(0).getWord()))
				{	
					sumToBeReturned+=digits.get((numString.remove(0).getWord()))/Math.pow(10,powerOfTen++);
				}
				else if( nonCD.containsKey(numString.get(0).getWord()) )
				{
					sumToBeReturned*=nonCD.get(numString.remove(0).getWord());	
				}
				else if(tens.containsKey(numString.get(0).getWord()))
				{
					System.out.println("Decimal Numbers are of the format: one hundred ninety six point FIVE EIGHT!!!");
					sumToBeReturned+=tens.get((numString.remove(0).getWord()))/100;
					powerOfTen++;
				}
				else
				{
					System.out.println("Decimal Numbers are of the format: one hundred ninety six point FIVE EIGHT!!!");
					numString.remove(0);
					powerOfTen++;
				}
			}
			
		}
		
		
		if(!returnProduct)
			if(sumToBeReturned==0.0)
				return 1.0;
			else
				return sumToBeReturned;
		else
			return productToBeReturned;
		
	}
	
	public Double removeWeight(String weight, ArrayList<TaggedWord> numString){
		/*This method is used by searchNumericalEquivalent. 
		 * It removes the weight words and the words before them from the numString,
		 *  and returns the numerical value of the part that has been removed.*/
		ArrayList<TaggedWord> toPass=new ArrayList<TaggedWord>();
		
		Double faceValue=0.0, number=0.0;
		int index=-1;
		
		for(int i=0;i<numString.size();i++)
		{	if(numString.get(i).getWord().equalsIgnoreCase(weight))
			{	
				if(!isInternational && !isIndian)
				{
					if(weight.equalsIgnoreCase("trillion") || weight.equalsIgnoreCase("billion") || weight.equalsIgnoreCase("million"))
						isInternational=true;	
					else
						isIndian=true; 
				}
				index=i;
				break;
			}
		}
		
		if(index==-1)
		{						// weight not found
			return 0.0;
		}
		
		if(index!=-1)
		{
			for(int i=0;i<index;i++)
				toPass.add(numString.remove(0));	
			
			numString.remove(0);
			faceValue=searchNumericalEquivalent(toPass);
		
			if(isIndian && weightsIndian.containsKey(weight))
				number+=faceValue*weightsIndian.get(weight);
			
			else if(isInternational && weightsInternational.containsKey(weight))
				number+=faceValue*weightsInternational.get(weight);
			
			else
				number=-1.0;		//wrong input
			
		}

		return number;
	}
	

	
	public boolean ifArrayListContainsHyphens(ArrayList<TaggedWord> a)
	{
		for(int i=0;i<a.size();i++)
		{
			if(ifContainsHyphens(a.get(i).getWord()))
			{
				return true;
			}
		}
		return false;
	}

	
	public long searchOrdinalEquivalent(ArrayList<TaggedWord> ordinalString)
	{	
		/*This method evaluates ordinal strings like ‘one hundred and second’= 102*/
		
		if(ifArrayListContainsHyphens(ordinalString))
		{
			
			ordinalString=withoutHyphensArrayList(ordinalString);
		}
		
		//two hundred and twentieth
		ArrayList<TaggedWord> ordinalNumber=new ArrayList<TaggedWord>();
		ArrayList<TaggedWord> rest=new ArrayList<TaggedWord>();
		ArrayList<TaggedWord> toMultiply=new ArrayList<TaggedWord>();
		long number=0;
		ordinalNumber.add(ordinalString.remove(ordinalString.size()-1));
		boolean containsWeightth=false;
		boolean containsWeight=false;
		rest=ordinalString;
		
		if(!rest.isEmpty())
		{
			if(ordinalWeights.containsKey((ordinalNumber.get(0).getWord()))||ordinalWeights.containsKey(ordinalNumber.get(0).getWord().substring(0,ordinalNumber.get(0).getWord().length()-1)))
			{
				
				containsWeightth=true;
				
				int i=rest.size()-1;
				
				while(i>=0)
				{
					if(weightsInternational.contains(rest.get(i).getWord()))
					{
						containsWeight=true;
						for(int j=i+1;j<rest.size();j++)
						{
							toMultiply.add(rest.remove(i+1));
						}
						break;
						
					}
					else
					{
						
						i--;
					}
					
				}
				
				if(!containsWeight)
				{
					
					for(int k=0;k<rest.size();k++)
					{
						toMultiply.add(rest.remove(0));
					}
				}
				
				
			}
			if(rest.size()>0)
				number+=searchNumericalEquivalent(rest);
			
		}
		
		
		if(ordinalNumbers.containsKey(ordinalNumber.get(0).getWord()))
		{	
			if(containsWeightth)
			{
				
				number+=searchNumericalEquivalent(toMultiply)*ordinalNumbers.get(ordinalNumber.get(0).getWord());
			}
			else
			{
				number+=ordinalNumbers.get(ordinalNumber.get(0).getWord());
			}
			
		}
		else if(ordinalNumbers.containsKey(ordinalNumber.get(0).getWord().substring(0,ordinalNumber.get(0).getWord().length()-1 )))
		{	
			
			if(containsWeightth)
			{
				number+=searchNumericalEquivalent(toMultiply)*ordinalNumbers.get(ordinalNumber.get(0).getWord().substring(0,ordinalNumber.get(0).getWord().length()-1));
			}
			else
			{
				number+=ordinalNumbers.get(ordinalNumber.get(0).getWord().substring(0,ordinalNumber.get(0).getWord().length()-1));
			}
			//number+=ordinalNumbers.get(ordinalNumber.get(0).getWord().substring(0,ordinalNumber.get(0).getWord().length()-1));
		}
		
		return number;
	}
	



	
	
	double searchFractionEquivalent(ArrayList<TaggedWord> fractionString)
	{
		/*
	This method evaluates strings that represent fractions. 
	(Makes use of searchNumericalEquivalent and searchOrdinalEquivalent)
		 */
		//two thirds=2/3
		//two-thirds=2/4
		//one third=1/3
		//one-third=1/3
		//twenty four twenty-fifths = 24/25
		//two hundred twenty fifths = 200/25 or 220/5
		//one hundred and twenty second=122nd or 1/122 (one hundred-and-twenty-second)
		//2/578 = "two five hundred seventy-eighths"
		//235/3,406 = "two hundred thirty-five three thousand four hundred-sixths." 
		//5 1/4 = "five and one-fourth"
		//The fraction 1/2 = "one-half," the fraction 3/2 is "three-halves" and the fraction 17/2 is "seventeen-halves."
		//200 1/22 = " two hundred and one twenty-second
		//one by thirty six hundredth 1/3600
	
		double fraction=1;
		double number=0;
		ArrayList<TaggedWord> fractionalPart=new ArrayList<TaggedWord>();
		ArrayList<TaggedWord> rest=new ArrayList<TaggedWord>();
		ArrayList<TaggedWord> wholeNumber=new ArrayList<TaggedWord>();
		boolean isHyphenated=false;
//		boolean notOrdinal=false;
		for(int i=0;i<fractionString.size();i++)
		{	for(int j=0;j<fractionString.get(i).getWord().length();j++)
			{	
				if(fractionString.get(i).getWord().charAt(j)=='-')
					isHyphenated=true;
			}
		}
		for(int i=0;i<fractionString.size()-1;i++)
		{	if(fractionString.get(i).getWord().equalsIgnoreCase("and") && (!((weightsInternational.containsKey(fractionString.get(i-1).getWord())|| weightsIndian.containsKey(fractionString.get(i-1).getWord()))  && (tens.containsKey(fractionString.get(i+1).getWord() )|| numbers.containsKey(fractionString.get(i+1).getWord())||digits.containsKey(fractionString.get(i+1).getWord())))||ifContainsHyphens(fractionString.get(i+1).getWord()) || (fractionString.get(i+1).getWord().equalsIgnoreCase("one") && !fractionString.get(i+1).getWord().endsWith("s"))))
			{	
				for(int j=0;j<i;j++)
					wholeNumber.add(fractionString.remove(0));
				fractionString.remove(0);
				
				number=searchNumericalEquivalent(wholeNumber);
			
				break;
			}
			
		}
		
		for(int i=0;i<fractionString.size();i++)
		{	if(fractionString.get(i).getWord().equals("by")||fractionString.get(i).getWord().equals("over"))
			{	
				for(int j=0;j<i;j++)
					rest.add(fractionString.remove(0));
			
				
				fraction*=searchNumericalEquivalent(rest);
				fractionString.remove(0);
				fractionalPart=fractionString;
				
				if(!fractionalPart.isEmpty())
					fraction=fraction/searchNumericalEquivalent(fractionalPart);
				fraction+=number;
				return fraction;
			}
		}
		
		if(!fractionString.get(fractionString.size()-1).getWord().endsWith("s"))
		{	rest.add(new TaggedWord("one", "CD"));
			if(!fractionString.get(0).getWord().equals("one"))
			{	for(int i=0;i<fractionString.size();i++)
					fractionalPart.add(fractionString.get(i));
			}
			else
			{	for(int i=1;i<fractionString.size();i++)
				fractionalPart.add(fractionString.get(i));
			}
			fraction*=searchNumericalEquivalent(rest);
			if(isHyphenated)
				fractionalPart=withoutHyphensArrayList(fractionalPart);
			fraction=fraction/searchOrdinalEquivalent(fractionalPart);
			fraction+=number;
			return fraction;
		}

		
		
		for(int i=0;i<fractionString.size()-2;i++)//two five hundredths
		{
			if( tens.containsKey( fractionString.get(i).getWord() ) && tens.containsKey( fractionString.get(i+1).getWord() ) 
					||( ( digits.containsKey(fractionString.get(i).getWord() )|| numbers.contains( fractionString.get(i).getWord() ) ) && 
					(digits.containsKey(fractionString.get(i+1).getWord())||numbers.contains( fractionString.get(i+1).getWord() ) ) || (digits.containsKey( fractionString.get(i).getWord() ) && tens.containsKey( fractionString.get(i+1).getWord()) )) )
					{	
						for(int j=0;j<=i;j++)
						{	
							rest.add(fractionString.remove(0));
						}
						fractionalPart=fractionString;
				
				if(isHyphenated)
				{
					fractionalPart=this.withoutHyphensArrayList(fractionalPart);
					rest=withoutHyphensArrayList(rest);
				}
					
					fraction*=searchNumericalEquivalent(rest);
					if(!fractionalPart.isEmpty())	
						fraction=fraction/searchOrdinalEquivalent(fractionalPart);
					fraction+=number;
					return fraction;
				}
			
				
		}
		
		
		
		if(!isHyphenated)

				{	
					
					//else//thirty twenty thirds
						
						int index=fractionString.size()-2;
							
							for(int j=0;j<index+1;j++)
								rest.add(fractionString.remove(0));
							fraction*=searchNumericalEquivalent(rest);
							
							fractionalPart=fractionString;
														
							if(!fractionalPart.isEmpty())
							{	
								fraction=fraction/(searchOrdinalEquivalent(fractionalPart));
							}
							
							fraction+=number;
							return fraction;
						
					}
				
				
					
				else	
				{	if(fractionString.size()==1)
					{	
						fraction=searchFractionEquivalent(withoutHyphensArrayList(fractionString));
						fraction+=number;
						return fraction;
					}
					ArrayList<TaggedWord> fractionalPartWithoutHyphens=new ArrayList<TaggedWord>();
					ArrayList<TaggedWord> restWithoutHyphens=new ArrayList<TaggedWord>();
					for(int i=0;i<fractionString.size()-1;i++)
					{	if(ifContainsHyphens(fractionString.get(i).getWord()))
						{	for(int j=0;j<=i;j++)
								rest.add(fractionString.remove(j));
							fractionalPart=fractionString;
							restWithoutHyphens=withoutHyphensArrayList(rest);
							fractionalPartWithoutHyphens=withoutHyphensArrayList(fractionalPart);
							fraction*=searchNumericalEquivalent(restWithoutHyphens);
							if(!fractionalPart.isEmpty())
								fraction=fraction/searchOrdinalEquivalent(fractionalPartWithoutHyphens);
							fraction+=number;
							return fraction;
						}
					}
					fractionalPart.add(fractionString.remove(fractionString.size()-1));
					rest=fractionString;
					restWithoutHyphens=withoutHyphensArrayList(rest);
					fractionalPartWithoutHyphens=withoutHyphensArrayList(fractionalPart);
					fraction*=searchNumericalEquivalent(restWithoutHyphens);
					if(!fractionalPart.isEmpty())
						fraction=fraction/searchOrdinalEquivalent(fractionalPartWithoutHyphens);
					fraction+=number;
					return fraction;
		
				}
		
				}


	
	
	String searchTimeEquivalent(ArrayList<TaggedWord> timeString)
	{
		/*
This method is called for strings that represent time, like ‘quarter to three’, ‘three thirty’ etc.
*/
		//converts these cases:
//		"eight-thirty" 
//		"fifteen minutes to three?"
//		fifteen minutes past three
//		"half past three",
//		"quarter to three" 
//		"half after three?" 
//		 "half eight"
		
		String time="";
		double hourInDigits=0.0;
		double minInDigits=0.0;
		ArrayList<TaggedWord> hour=new ArrayList<TaggedWord>();
		ArrayList<TaggedWord> min=new ArrayList<TaggedWord>();
	
		boolean allCD=true;
		boolean containsPast=false;
		boolean containsTo=false;
		
		for(int i=0;i<timeString.size();i++)
		{
			if(timeString.get(i).getTag().equals("CD"))
			{
			
			}
			else if(timeString.get(i).getWord().equals("to"))
			{
				allCD=false;
				containsTo=true;
			}
			else if(timeString.get(i).getWord().equals("past")||timeString.get(i).getWord().equals("after"))
			{
				allCD=false;
				containsPast=true;
			}
			else if(timeString.get(i).getWord().equals("quarter")||timeString.get(i).getWord().equals("half"))
			{
				allCD=false;
			}
			
		}
		
		if(allCD)
		{
			// twelve thirty two
			//fourteen fifty two
			//twenty-two fifty-four
			//twelve two
			hour.add(timeString.get(0));
			if(timeString.size()==2)
			{
				min.add(timeString.get(1));
			}
			else if(timeString.size()>2)
			{
				int k=1;
				if(tens.containsKey(timeString.get(0).getWord()) && digits.containsKey( timeString.get(1).getWord() ) )
				{
					hour.add(timeString.get(1));
					k++;
				}
				while(k<timeString.size())
				{
					min.add(timeString.get(k));
					k++;
				}
					
			}
			else
			{
				for(int i=1;i<timeString.size();i++)
				{
					min.add(timeString.get(i));
				}
			}
			hourInDigits=searchNumericalEquivalent(hour);
			minInDigits=searchNumericalEquivalent(min);
		}
		else if(containsTo)
		{
			int i=0;
			boolean isCD=false;
			while(!timeString.get(i).getWord().equals("to"))
			{
				if(timeString.get(i).getTag().equals("CD"))
				{
					min.add(timeString.get(i));
					isCD=true;
				}
				else if(nonCDfractions.containsKey(timeString.get(i).getWord()))
				{
					min.add(timeString.get(i));
				}
				i++;
			}
			if(isCD)
				minInDigits=60.0-searchNumericalEquivalent(min);
			else
			{
				minInDigits=60.0-searchNumericalEquivalent(min)*60;
			}
			i++;
			while(i<timeString.size())
			{
				hour.add(timeString.get(i));
				i++;
			}
			hourInDigits=searchNumericalEquivalent(hour)-1;
		}
		else if(containsPast)
		{
			int i=0;
			boolean isCD=false;
			while(!(timeString.get(i).getWord().equals("past")||timeString.get(i).getWord().equals("after")))
			{
				if(timeString.get(i).getTag().equals("CD"))
				{
					isCD=true;
					min.add(timeString.get(i));
					
				}
				else if(nonCDfractions.containsKey(timeString.get(i).getWord()))
				{
					min.add(timeString.get(i));
				}
				else
				{
					
				}
				
				i++;
			}
			if(isCD)
				minInDigits=searchNumericalEquivalent(min);
			else
			{
				minInDigits=searchNumericalEquivalent(min)*60;
			}
			i++;
			while(i<timeString.size())
			{
				hour.add(timeString.get(i));
				i++;
			}
			hourInDigits=searchNumericalEquivalent(hour);
		}
		else
		{
			if(timeString.size()==2)
			{
				
				min.add(timeString.get(0));
				hour.add(timeString.get(1));
				minInDigits=searchNumericalEquivalent(min)*60;
				hourInDigits=searchNumericalEquivalent(hour);
			}
			
		}
		String hourS=getWholePart(hourInDigits) +"";
		String minS=getWholePart(minInDigits)+"";
		minS=String.format("%02d", Integer.parseInt(minS));
		time=hourS+":"+minS;
		
		
		return time;
		
	}

	public long getWholePart(double num)
	{
		//Returns the whole part of a decimal number
		//25 for 25.3333
		long wholePart=0;
		wholePart= (long) num;
		return wholePart;
	}


		public boolean ifContainsHyphens(String word){
			//returns true if ‘word’ contains hyphens
			for(int i=0;i<word.length();++i)
				if(word.charAt(i)=='-')
				{
					return true;
				}
			return false;
		}
		
		public ArrayList<TaggedWord> withoutHyphensArrayList(ArrayList<TaggedWord> input)
		/*
		Splits all the hyphenated words at the hyphens into separate words i.e. separate 'TaggedWords'
		 */
		{	String []parts;
			
			ArrayList<TaggedWord> withoutHyphens=new ArrayList<TaggedWord>();
			for(int i=0;i<input.size();i++)
			{	
				parts=input.get(i).getWord().split("-");
				
				for(int j=0;j<parts.length;j++)
				{
					TaggedWord tg=new TaggedWord(parts[j],"CD");
					withoutHyphens.add(tg);
				}
			}
			
			return withoutHyphens;
		}
	
}


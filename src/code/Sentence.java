package code;

import java.util.ArrayList;



public class Sentence {
	
	ArrayList<TaggedWord> taggedWords=new ArrayList<TaggedWord>(); 
	ArrayList<TaggedWord> WrongInput= new ArrayList<TaggedWord>();
	
	private int currentIndex;
	Words dictionary=new Words();
	private String finalSentence;
	boolean containsMinus,containsFinalWeightAfterDecimal,isOrdinal, isFraction, isTime, isOrdinary;
	private Double finalWeightAfterDecimal=0.0;
	
	public Sentence(String taggedSentence)
	{ 
		/*taggedSentence is a string of the form: I_PRP have_VBP three_CD cars_NNS, 
		 * the constructor forms ‘TaggedWord’ objects out of it */
			currentIndex=0;
			finalSentence="";
			containsMinus=false;
			containsFinalWeightAfterDecimal=false;
			isOrdinal=false;
			isFraction=false;
			isTime=false;
			isOrdinary=false;
			
			//If the user inputs wrong numeral String, them it is replaced by another string "Wrong Input, Check Notation"
			// Eg: forty million ninety lakh thirty three       ( Both International and Indian system used )
			// Eg: four hundred ninety six point seventy eight  ( correct string should be : four hundred ninety six point seven eight)
			WrongInput.add(new TaggedWord("Wrong","JJ"));
			WrongInput.add(new TaggedWord("Input","NN"));
			WrongInput.add(new TaggedWord(",","_,"));
			WrongInput.add(new TaggedWord("Check","VB"));
			WrongInput.add(new TaggedWord("Notation","NN"));
		
			String[] words=taggedSentence.split(" ");
			
			// Now constructing TaggedWords from taggedSentence and adding them to the ArrayList
			/*e.g. taggedSentence=Two_CD thirds_NNS of_IN the_DT total_JJ  surface_NN area_NN of_IN Earth_NNP are_VBP covered_VBN by_IN water_NN
			*/
		
			for(int i=0;i<words.length;i++)
			{																							
					String[] wordAndTag=words[i].split("_");
					TaggedWord t=new TaggedWord(wordAndTag[0].toLowerCase(), wordAndTag[1]);
					taggedWords.add(t);
			}
		
	}
	
	
	//checks if the CD tagged string is already a numeral and hence does not needs to be replaced at all.
	// Eg: 23_CD people_NNS died_VBD in_IN the_DT bus_NN accident_NN ._.
	public boolean isDigit(String numString){
		//returns true if numString is a number like “1222” or "122.2" 
		//(some number written as digits)
			boolean isDigit=false;
			for(int i=0;i<numString.length();++i)
					if(Character.isDigit(numString.charAt(i)) || numString.charAt(i)=='.' || numString.charAt(i)=='/')
							isDigit=true;
					else if(i==numString.length()-1 && numString.charAt(i)=='s')
					{
						isDigit=true;				// eg: During the 1980s, everyone followed.... 
					}
					else
					{
							isDigit=false; 
							break;
					}
			return isDigit;
		
	}
	
	
	
	
	/*This method traverses the sentence to find the String numstring which is to be parsed to numerals. A while loop which runs till the end of the arrayList is reached
	 * looks for CD tagged words. If a CD tagged word is found, then it looks for words like MINUS or POINT as these arent taged as CD. 
	 */
	
		
	public ArrayList<TaggedWord> findNumString()
	{
			currentIndex=0;
			ArrayList<TaggedWord> numString=new ArrayList<TaggedWord>();
			boolean containsPoint=false;
		
			containsMinus=false;
		
			while(currentIndex<taggedWords.size() ){
					
					if( taggedWords.get(currentIndex).getTag().equals("CD") || dictionary.containsIndianWeights(taggedWords.get(currentIndex).getWord()) )
					{	
						
							if((currentIndex-1)>=0 && taggedWords.get(currentIndex-1).getWord().equalsIgnoreCase("minus"))	//checking if its a negative number Eg: minus fifty three
							{	
									containsMinus=true; 		//EG:"MINUS FIFTY THREE"
									//P.S. : one advantage here is that for numbers like "MINUS FIFTY THREE POINT FIVE SIX BILLION" it wont check for "minus" again n again
									if(finalSentence.endsWith(" minus "))
										finalSentence=finalSentence.substring(0, finalSentence.length()-6);	//removing the word "minus"
									else if(finalSentence.endsWith(" Minus "))
										finalSentence=finalSentence.substring(0, finalSentence.length()-6);	//removing the word "Minus"
									//else //nothing; sometimes as in "HALF OF MINUS", "minus" is detected with "half of" so not written in finalSentence, in next While iteration for "twenty" minus is checked 
							}
							else if( (currentIndex-1)>=0 && taggedWords.get(currentIndex-1).getWord().equalsIgnoreCase("a") && finalSentence.endsWith(" a ") )
									finalSentence=finalSentence.substring(0, finalSentence.length()-2);// removing "a" from finalSentence eg: "A HUNDRED THOUSAND butterflies" = 100000 butterflies(w/o "a")
							
							
							
							//checking if its a time format
							if( (currentIndex+3)<taggedWords.size() && (  taggedWords.get(currentIndex+1).getWord().equals("minutes") && (taggedWords.get(currentIndex+2).getWord().equals("to") || taggedWords.get(currentIndex+2).getWord().equals("past")) && taggedWords.get(currentIndex+3).getTag().equals("CD")  ) )
							{		// NINE MINUTES TO/PAST FOUR
								
									numString.add(taggedWords.get(currentIndex++));		// 15(or FIFTEEN)
									numString.add(taggedWords.get(currentIndex++));		// MINUTES
									numString.add(taggedWords.get(currentIndex++));		// TO/PAST
									numString.add(taggedWords.get(currentIndex++));		// THREE(or 3)
									isTime=true;
                             }
							
							else if( currentIndex+2<taggedWords.size() && ( (taggedWords.get(currentIndex+1).getWord().equals("past") || taggedWords.get(currentIndex+1).getWord().equals("to"))&& (taggedWords.get(currentIndex+2).getTag().equals("CD") && dictionary.getKeyTimeHours(taggedWords.get(currentIndex+2).getWord())<13.0 && dictionary.getKeyTimeHours(taggedWords.get(currentIndex+2).getWord())>0.0 && !ifContainsHyphen(taggedWords.get(currentIndex+2).getWord()) ) ) )
							{
								
								numString.add(taggedWords.get(currentIndex++));		// 15 OR FIFTEEN 
								numString.add(taggedWords.get(currentIndex++));		// PAST/TO
								numString.add(taggedWords.get(currentIndex++));		// THREE(or 3)
								isTime=true;
                
							}
							
							//checking if its a digit
							else if( isDigit(taggedWords.get(currentIndex).getWord()) )
							{	
									//for cases like 20 DOZEN, 20 MILLION, 20 HUNDRED THOUSAND, 20 LAKH
									if( (currentIndex+1)<taggedWords.size() && ( dictionary.containsInternationalWeights(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsIndianWeights(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex+1).getWord()) )  )
									{	
											numString.add(taggedWords.get(currentIndex));			//adding the digit
											++currentIndex;
										
											if(currentIndex>=taggedWords.size())
													break;
										
											while( taggedWords.get(currentIndex).getTag().equals("CD") || dictionary.containsIndianWeights(taggedWords.get(currentIndex).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex).getWord()) )
											{	
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
													
													if(currentIndex>=taggedWords.size())
															break;
											}
											
											dictionary.setWeightSystem();
											return numString;
									}	
									
									else
									{		// it is a digit only eg: 20 people went to the movie yesterday or like i will be there in 15 minutes.
											finalSentence+=taggedWords.get(currentIndex).getWord()+" ";
											++currentIndex;
									}	
								
								
							}
							
							else	//isn't a digit
							{	
								
									
									if(ifContainsHyphen(taggedWords.get(currentIndex).getWord()))				//Words like TWO-AND-A-HALF, HALF-A-DOZEN  (which are tagged as CD)
									{	
											ArrayList<TaggedWord> withoutHyphens=new ArrayList<TaggedWord>();
											withoutHyphens=removeHyphens(taggedWords.get(currentIndex).getWord());
											
											for(int i=0;i<withoutHyphens.size();++i)
													numString.add(withoutHyphens.get(i));
										
									}
									else																	//simple CD words like FIVE, TWENTY, HUNDRED, MILLION, LAKH	 
											numString.add(taggedWords.get(currentIndex));
									
									++currentIndex;
									
									if(currentIndex>=taggedWords.size())
											break;
									 				
									if( taggedWords.get(currentIndex).getWord().equals("point"))
									{		//decimal numbers
											boolean finalWeightAtEnd=false;
											containsPoint=true;
											numString.add(taggedWords.get(currentIndex));
											++currentIndex;
											
											if(currentIndex>=taggedWords.size() || !taggedWords.get(currentIndex).getTag().equals("CD") )	
											{	
													numString.remove(numString.size()-1);		// if the sentence ends like: He managed to score only ONE POINT. here its no the decimal point.
													--currentIndex;							    //now currentIndex at "point"
													return numString;							//next while iteration will be for "point" and eventually it will get added to the finalSentence. 
																								//or eg: Only ONE POINT was awarded to team B.
											}
											
											if(containsPoint)
											{
													while( currentIndex<taggedWords.size() && (taggedWords.get(currentIndex).getTag().equals("CD") || ( (taggedWords.get(currentIndex).getWord().equals("over") || taggedWords.get(currentIndex).getWord().equals("by")) && currentIndex+1<taggedWords.size() && taggedWords.get(currentIndex+1).getTag().equals("CD") ) || dictionary.containsIndianWeights(taggedWords.get(currentIndex).getWord())))
													{
													
															if( taggedWords.get(currentIndex).getWord().equals("over") || taggedWords.get(currentIndex).getWord().equals("by") )
																isFraction=true;
															numString.add(taggedWords.get(currentIndex));
															++currentIndex;
															if(currentIndex>=taggedWords.size())
															{		
																	finalWeightAtEnd=true;
																	break;
															}
													}
																
													if(dictionary.containsIndianWeights(numString.get(numString.size()-1).getWord()))
													{
															containsFinalWeightAfterDecimal=true;
															finalWeightAfterDecimal=dictionary.getKeyIndian(numString.remove(numString.size()-1).getWord());
															if(finalWeightAtEnd)
																	return numString;	
													}
													
													else if(dictionary.containsInternationalWeights(numString.get(numString.size()-1).getWord()))
													{
															containsFinalWeightAfterDecimal=true;
															finalWeightAfterDecimal=dictionary.getKeyInternational(numString.remove(numString.size()-1).getWord());
															if(finalWeightAtEnd)
																	return numString;
													}
													else
													{
															//do nothing; next while loop iteration	eg: ONE POINT TWO, ONE POINT TWO DOZEN
													}
								
											}		
										
									}		
									
									//for "two hundred AND twenty"
									else if(taggedWords.get(currentIndex).getWord().equals("and"))
									{
											numString.add(taggedWords.get(currentIndex));
											++currentIndex;		
											
											if(currentIndex>=taggedWords.size())
											{	
													numString.remove(numString.size()-1);			//for incorrect sentences ending with and
													--currentIndex;									//return numString and then iterate for "and"
													if(numString.size()>0)											
															return numString;
													//else  will never be reached
													//break;
											}
											
											if( currentIndex+1<taggedWords.size() && (  taggedWords.get(currentIndex).getWord().equals("a") && ( dictionary.containsNonCDFractionWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex+1).getWord()) )  ) )
											{			// for TWO AND "A HALF"
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
											}
											
											else if( currentIndex+1<taggedWords.size() && (  dictionary.containsNonCDFractionWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex+1).getWord()) )  )
											{		// TWO AND "HALF"
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
											}
											
											else if( taggedWords.get(currentIndex).getTag().equals("JJ") || taggedWords.get(currentIndex).getTag().equals("NNS") || taggedWords.get(currentIndex).getTag().equals("NN") )
											{		//HUNDRED AND "TWENTY-THIRDS"
												
											}
											
											else if( taggedWords.get(currentIndex).getTag().equals("CD") ){
													//continue	//instead of a new while adding CDs we opt for a next iteration as next CD word has to take care of "point" eg:one over one point five
												if( numString.size()-2>=0 && ( !( (dictionary.containsIndianWeights(numString.get(numString.size()-2).getWord()) || dictionary.containsInternationalWeights(numString.get(numString.size()-2).getWord()) ) && dictionary.containsDigits(taggedWords.get(currentIndex).getWord())) && !( ( dictionary.containsIndianWeights(numString.get(numString.size()-2).getWord()) || dictionary.containsInternationalWeights(numString.get(numString.size()-2).getWord()) ) && dictionary.containsTens(taggedWords.get(currentIndex).getWord())) && !(( dictionary.containsIndianWeights(numString.get(numString.size()-2).getWord()) || dictionary.containsInternationalWeights(numString.get(numString.size()-2).getWord()) ) && dictionary.containsNumbers(taggedWords.get(currentIndex).getWord())) )	)	//eg: "FOUR AND FIVE", "ELEVEN AND TEN", "THIRTY AND FIVE", "FORTY AND FIFTY", "TEN AND HUNDRED"
												{	
													numString.remove(numString.size()-1);		//remove and from numString
													--currentIndex;								//next while loop iteration start from "and"
													
													return numString;							
												}
											}
											
											else
											{
													numString.remove(numString.size()-1);
													--currentIndex;
													if(numString.size()>0)
															return numString;
													//else would had not been here as this and is encountered only after a CD
											}	
									}		
									
									else if( currentIndex+1<taggedWords.size() && ( (taggedWords.get(currentIndex).getWord().equals("over") || taggedWords.get(currentIndex).getWord().equals("by")) && (taggedWords.get(currentIndex+1).getTag().equals("CD") || dictionary.containsIndianWeights(taggedWords.get(currentIndex+1).getWord()) ) ) )
									{		// eg: TWO OVER NINE,FIFTY BY THIRTY TWO
										
											if(taggedWords.get(currentIndex).getWord().equals("over") || taggedWords.get(currentIndex).getWord().equals("by") )
													isFraction=true;
											numString.add(taggedWords.get(currentIndex));
											++currentIndex;				//instead of a new while adding CDs we opt for a next iteration as next CD word has to take care of "point" eg:one over one point five
									}
									
									else if(taggedWords.get(currentIndex).getTag().equals("NNS") || taggedWords.get(currentIndex).getTag().equals("JJ") || taggedWords.get(currentIndex).getTag().equals("NN"))
									{ 
										
											if( ifContainsHyphen(taggedWords.get(currentIndex).getWord()) )
											{
													ArrayList<TaggedWord> withoutHyphens=new ArrayList<TaggedWord>();
													withoutHyphens=removeHyphens(taggedWords.get(currentIndex).getWord());
													boolean hasCD=false;			//checks if all words are CD eg: THREE half-times, HUNDRED TWENTY-THIRDS 
													int i=0;
													while(i<withoutHyphens.size())
													{	
															++i;
															if(   dictionary.containsWord(withoutHyphens.get(i-1).getWord()) || (withoutHyphens.get(i-1).getWord().charAt( withoutHyphens.get(i-1).getWord().length()-1 )=='s' && dictionary.containsOrdinalNumbers(withoutHyphens.get(i-1).getWord().substring(0, withoutHyphens.get(i-1).getWord().length()-1)) ) )
															{	
																	hasCD=true;
																	if(withoutHyphens.get(i-1).getWord().charAt( withoutHyphens.get(i-1).getWord().length()-1 )=='s' && dictionary.containsOrdinalNumbers(withoutHyphens.get(i-1).getWord().substring(0, withoutHyphens.get(i-1).getWord().length()-1)) )
																	{
																		isFraction=true;
																	}
															}
															else 
															{	
																	hasCD=false;
																	break;
															}
													}													
														
													if(hasCD)
													{
															numString.add(taggedWords.get(currentIndex));
															++currentIndex;
													}
													else
													{
															//do nothing
													}
											}
											
											else if(dictionary.containsIndianWeights(taggedWords.get(currentIndex).getWord()))
											{
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
												
													if(currentIndex>=taggedWords.size())
													{
															return numString;
													}												
													else if(currentIndex+1<taggedWords.size() && (taggedWords.get(currentIndex).getWord().equals("and") && !dictionary.containsIndianWeights(taggedWords.get(currentIndex+1).getWord()) ) )
													{
															numString.add(taggedWords.get(currentIndex));
															++currentIndex;
													}
												
											}
											
											else if( dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex).getWord()) )	//THIRD, TWELFTH, HUNDREDTH, BILLIONTH 
											{
													
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
													
													if(currentIndex>=taggedWords.size())
													{
															if(numString.size()>0)
																	return numString;
															else 
																	break;
													}
											}
											
											else if( taggedWords.get(currentIndex).getWord().charAt( taggedWords.get(currentIndex).getWord().length()-1 )=='s' && dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex).getWord().substring(0, taggedWords.get(currentIndex).getWord().length()-1)) && !taggedWords.get(currentIndex).getWord().equals("seconds") )	//THIRDS,FOURTHS
											{
													isFraction=true;
													numString.add(taggedWords.get(currentIndex));
													++currentIndex;
													if(currentIndex>=taggedWords.size())
													{
															if(numString.size()>0)
																	return numString;
															else 
																	break;
													}
											}
											
											else if( taggedWords.get(currentIndex).getWord().equals("halves") || taggedWords.get(currentIndex).getWord().equals("quarters"))
											{		// eg: FIVE HALVES of the cake 
													if(taggedWords.get(currentIndex).getWord().equals("halves"))
															numString.add(new TaggedWord("0.5","CD"));
													else
														numString.add(new TaggedWord("0.25","CD"));
													++currentIndex;
													isOrdinary=true;
											}
											
											else
											{	
													
													//continue ... for eg : ten_CD dozen_NN
													//what to do now?? Think over it.
													//return numString;
											}	
									}	
									
									else
									{		//continue
											//there is a non Cardinal word after a Cardinal word, which maybe of use(eg:one dozen) or may not be of use(Eg:one boy)
									}
								
							} //end of else when its not a digit
					
					}  //end of else if checking tag CD

					
					else if( taggedWords.get(currentIndex).getTag().equals("NN") || taggedWords.get(currentIndex).getTag().equals("NNS") || taggedWords.get(currentIndex).getTag().equals("JJ") || taggedWords.get(currentIndex).getTag().equals("DT") || taggedWords.get(currentIndex).getTag().equals("PDT") || taggedWords.get(currentIndex).getTag().equals("RB") )
					{		
							// for words starting with HALF/QUARTER/DOZEN  or words like THIRD/FOURTH
							
							if( dictionary.containsNonCDFractionWord(taggedWords.get(currentIndex).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex).getWord()) || dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex).getWord()) || (dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex).getWord().substring(0, taggedWords.get(currentIndex).getWord().length()-1)) && !taggedWords.get(currentIndex).getWord().equals("seconds") /*seconds is the time one while others like thirds,fourths need to be included*/)  )
							{		
									if(taggedWords.get(currentIndex).getWord().equals("a"))
											finalSentence+="a ";
									else
									{	
											if(taggedWords.get(currentIndex).getWord().equals("second"))
											{
													if(dictionary.containsNonCDFractionWord(numString.get(0).getWord()))	//checking only for "HALF A SECOND" AND "QUARTER A SECOND"
													{
															while(numString.size()!=0)
															{
																	finalSentence+=numString.remove(0).getWord()+" ";
															}
													}// eg: "half a second"	is actually a time and here it is not to be parsed	
													
													finalSentence+=taggedWords.get(currentIndex).getWord()+" ";
											}
											else
													numString.add(taggedWords.get(currentIndex));
									}
									
									++currentIndex;
								
									if(currentIndex>=taggedWords.size())
									{		
											break;
									}
							
									if( currentIndex+1<taggedWords.size() && (   taggedWords.get(currentIndex).getWord().equals("past") || ( taggedWords.get(currentIndex).getWord().equals("of") && ( taggedWords.get(currentIndex+1).getTag().equals("CD") || dictionary.containsNonCDFractionWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsIndianWeights(taggedWords.get(currentIndex+1).getWord()) || taggedWords.get(currentIndex+1).getWord().equals("minus") ) ) || (taggedWords.get(currentIndex+1).getWord().equals("a") && ( taggedWords.get(currentIndex+1).getTag().equals("CD") || dictionary.containsNonCDFractionWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsNonCDWord(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsIndianWeights(taggedWords.get(currentIndex+1).getWord() )  ) ) || taggedWords.get(currentIndex).getWord().equals("a") || (taggedWords.get(currentIndex).getWord().equals("to") && (taggedWords.get(currentIndex+1).getTag().equals("CD"))  )  || (taggedWords.get(currentIndex).getWord().equals("after") && taggedWords.get(currentIndex+1).getTag().equals("CD") ) )    )
									{		
											//half past /half after eight/half a million/half to three/half after three/ half of twenty,lakh,quarter(and "half of the class" is to be ignored) 
											if(taggedWords.get(currentIndex).getWord().equals("past") || taggedWords.get(currentIndex).getWord().equals("to") || taggedWords.get(currentIndex).getWord().equals("after") )
													isTime=true;			//so as not to include some cases eg: "The first half flew past his stomach and the other HALF PAST my head"
											
											numString.add(taggedWords.get(currentIndex));
											++currentIndex;
											
											if(taggedWords.get(currentIndex).getWord().equals("minus"))		// eg: "half of minus twenty" then minus isn't be added to numString and hence ++currentIndex and the next while iteration will start from next CD word after "minus" and consequently detect "minus" 
											{		++currentIndex;
									
													if(currentIndex>=taggedWords.size())	//for an incorrect sentence that ends with minus.
													{										// eg: "half of minus." then only half is parsed and minus remains as it is.
															numString.remove(numString.size()-1);
															--currentIndex;
															return numString;
													}
											}											
									}
							}		
							
							else if( ifContainsHyphen(taggedWords.get(currentIndex).getWord()) )
							{
									//cases like two-and-a-half, twenty-fourths 
								
									boolean hyphenatedNumeral=false,containsOrdinal=false, containsHalves=false, containsQuarters=false;
									ArrayList<TaggedWord> temp=new ArrayList<TaggedWord>();
									temp=removeHyphens(taggedWords.get(currentIndex).getWord());
									
									for(int i=0;i<temp.size();++i)
									{		
											if(  dictionary.containsWord(temp.get(i).getWord()) || ( taggedWords.get(currentIndex).getWord().charAt( taggedWords.get(currentIndex).getWord().length()-1 )=='s' && dictionary.containsOrdinalNumbers(temp.get(i).getWord().substring(0, temp.get(i).getWord().length()-1)) ) || temp.get(i).getWord().equals("and") || temp.get(i).getWord().equals("halves") || temp.get(i).getWord().equals("quarters") )
											{	
													hyphenatedNumeral=true;
													
													if(dictionary.containsOrdinalNumbers(temp.get(i).getWord().substring(0, temp.get(i).getWord().length()-1)) || dictionary.containsOrdinalNumbers(temp.get(i).getWord()) )
															containsOrdinal=true;
													if(temp.get(i).getWord().equals("halves"))
															containsHalves=true;
													else if(temp.get(i).getWord().equals("quarters"))
															containsQuarters=true;
													
											}
											else 
											{		
													hyphenatedNumeral=false;
													break;
											}
									}
									
									
									if(  currentIndex+1<taggedWords.size() && ( dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex+1).getWord()) || dictionary.containsOrdinalNumbers(taggedWords.get(currentIndex+1).getWord().substring(0, taggedWords.get(currentIndex+1).getWord().length()-1)) || ifContainsHyphen(taggedWords.get(currentIndex+1).getWord()) /*&& every word should be a CD*/ )  )
											containsOrdinal=true;			//eg: one-hundred-and-twenty thirds= 120/3 or one-hundred-twenty twenty-thirds
								
									if(hyphenatedNumeral)
									{	
											if(containsOrdinal && !containsHalves && !containsQuarters)
											{
													numString.add(taggedWords.get(currentIndex));		//twenty-fourths   adding with hyphens
											}
											else
											{
													for(int i=0;i<temp.size();++i)
													{		// eg: half-a-quarter_JJ/NN ; adding in numString without hyphens  or three-quarters
															if(temp.get(i).getWord().equals("halves"))
															{		numString.add(new TaggedWord("0.5","CD"));
																	isOrdinary=true;
															}
															else if(temp.get(i).getWord().equals("quarters"))
															{		numString.add(new TaggedWord("0.25","CD"));
																	isOrdinary=true;
															}
															else
																numString.add(temp.get(i));
																
																		
													}						
											}
											
									}
									else
									{
											if(numString.size()>0)
													return numString;
											else	
													finalSentence+=taggedWords.get(currentIndex).getWord()+" ";
									}
									++currentIndex;
							}
							else
							{
									
									if(numString.size()>0)									// for eg: if sentence is he scored one/CD mark/NN. numString=one and then we check mark/NN but we found 
											{		return numString;		}				//its not to be included so only "one" is returned to be parsed. Next while loop iteration will start from
																							//checking mark
																							//another eg: twenty four seconds
									finalSentence+=taggedWords.get(currentIndex).getWord()+" ";
									++currentIndex;
							}
					}
					
					else
					{
							
							if(numString.size()==0)
							{		
									finalSentence+=taggedWords.get(currentIndex).getWord()+" ";
									++currentIndex;
							}
							else
							{
									dictionary.setWeightSystem();
									return numString;
							}
				
					}
		
			}		//end of while loop
			
			if(numString.size()==0)
			{		numString.add(new TaggedWord(" ", " "));	
			}
			return numString ;
			
	}
	
	
	
	
	
	
	
	
	//passes the numString to Words which returns its numeral equivalent  
	public Double wordsToDigits(ArrayList<TaggedWord> numString)
	{
		/*
	once numString has been found, 
	this function invokes searchNumericalEquivalent of a Words object 
	to calculate the numerical Equivalent of the string
		 */
			Double number;
			number=dictionary.searchNumericalEquivalent(numString);
			//returns the number corresponding to the numString, in this case 0.666666
			
			return number;
			
	}

	public Double wordsToDigits(Double toReplace,ArrayList<TaggedWord> numString)
	{
		/*We made this overloaded method for cases like "20 million" 
	toReplace=20 and numString="million" 
	The numString is evaluated by Algorithm 1 and 
	its numericalEquivalent is multiplied by 20 and the final product is returned. */
			Double number;
			number=dictionary.searchNumericalEquivalent(numString);
			number*=toReplace;
			return number;
	}
	
	public String wordsToOrdinals( ArrayList<TaggedWord> numString)
	{		
		/*
		Same as the method Double wordsToDigits(ArrayList<TaggedWord> numString)  but this is for ordinal-representing strings.
		*/
			String ordinalEquivalent="";
			Long number=dictionary.searchOrdinalEquivalent(numString);
			ordinalEquivalent=""+number+"";
			if(number%10==1)
				ordinalEquivalent+="st";
			else if(number%10==2)
				ordinalEquivalent+="nd";
			else if(number%10==3)
				ordinalEquivalent+="rd";
			else
				ordinalEquivalent+="th";
	
			return ordinalEquivalent;
	}

	public String replaceWithDigits(int digitsOfPrecision)
	{
		/*
this method decides whether the numString is a regular numString, or ordinal or Time or fraction. Then it invokes the respective method to evaluate the string. e.g. if the string is ordinal, it invokes wordsToOrdinals(numString)
*/
	
			while(currentIndex<taggedWords.size())
			{
					ArrayList<TaggedWord> numString=this.findNumString();
				
					String numberEquivalent="";
					boolean checkNumString=false;
					//System.exit(1);
					
//					for(int i=0;i<numString.size();++i)
//						System.out.println("** "+numString.get(i).getWord());
//						System.out.println();
					if( !isOrdinal && !isFraction && !isTime && !isOrdinary)
					{	
							for( int count=0;count<numString.size();++count )
							{
									if(ifContainsHyphen(numString.get(count).getWord()))
									{		
											checkNumString=true;
											break;
		                            }
									else if( dictionary.containsOrdinalNumbers(numString.get(count).getWord()) || 
											dictionary.containsOrdinalNumbers(numString.get(count).getWord().substring(0, numString.get(count).getWord().length()-1)) )
									{       
											if(dictionary.containsOrdinalNumbers(numString.get(count).getWord().substring(0, numString.get(count).getWord().length()-1)))
											{		isFraction=true;
													break;
											}
											else
											{	
													checkNumString=true;
													break;
											}
									}
		
							}
					}
					
					if(checkNumString && !isFraction)
					{		
											
							if( currentIndex<taggedWords.size() && taggedWords.get(currentIndex).getWord().equals("of"))
							{
									
									if(currentIndex-numString.size()-1>0)
									{		
											if(taggedWords.get(currentIndex-numString.size()-1).getWord().equals("a") || taggedWords.get(currentIndex-numString.size()).getWord().equals("one") || taggedWords.get(currentIndex-numString.size()).getWord().startsWith("one") || numString.get(numString.size()-1).getWord().endsWith("s") )
											{		isFraction=true;}//call searchFractionEquivalent		//eg: He gave me A THIRD OF his cake. He gave me ONE HUNDREDTH OF his cake. He gave me ONE-HUNDREDTH OF his cake. He gave me TWENTY-THREE TWENTY-THIRDS of his cake.
											else
													isOrdinal=true;//call searchOrdinalEquivalent		//eg: Its THIRD OF May today.
									}		
									else 
											isFraction=true;
							}
							else if(currentIndex==taggedWords.size())
							{
									isOrdinal=true;//call searchOrdinalEquivalent
							}
							
							else
							{
								if(!numString.get(numString.size()-1).getWord().endsWith("s") && !(numString.get(0).getWord().equalsIgnoreCase("one")))
									isOrdinal=true;//call searchOrdinalEquivalent
								else
									isFraction=true;
	
							}
					}		
							
					
					if(numString.size()<4 && numString.size()>1 && !isTime && !isOrdinal && !isFraction && !isOrdinary)//to determine if its a time
					{
							boolean allCDWords=false;
							
												
							if(!isTime && !dictionary.containsNonCDFractionWord(numString.get(1).getWord()) && !dictionary.containsNonCDWord(numString.get(1).getWord()) && ( numString.get(0).getWord().equals("eleven") || numString.get(0).getWord().equals("twelve") ||  numString.get(0).getWord().equals("ten")|| dictionary.containsDigits(numString.get(0).getWord()) ))
									allCDWords=true;		// checking if string has CD words ( sometimes eleven/twelve are tagged JJ and for "eight-thirty" after removing hyphens we tagged them as " " which wont be detected)
									
							if(allCDWords && !isTime)
							{	
									if(numString.size()<=3)					//five thirty-two
									{ 
											ArrayList<TaggedWord> min = new ArrayList<TaggedWord>();
											for(int i=0;i<numString.size();++i)
													min.add(numString.get(i));
											min.remove(0);
											double minDigits;
											if(!ifContainsHyphen(min.get(0).getWord()) && !dictionary.containsNonCDFractionWord(min.get(0).getWord()) && !dictionary.containsNonCDWord(min.get(0).getWord()) )
												minDigits= dictionary.searchNumericalEquivalent(min);
											else
											{	ArrayList<TaggedWord> withouthyphenTime=new ArrayList<TaggedWord>();
												withouthyphenTime=removeHyphens(min.get(0).getWord());
												minDigits=dictionary.searchNumericalEquivalent(withouthyphenTime);
											}
											if(minDigits<60 && minDigits!=0.5 && minDigits!=0.25)
											{
													isTime=true;
											}
												
											
									}
							}	
							
							else if(numString.size()==2)
							{
										if(numString.get(0).getWord().equals("half") && (dictionary.containsDigits(numString.get(1).getWord()) || dictionary.containsNumbers(numString.get(1).getWord()) )  )
										{
											isTime=true;
										}
							}
	
					}
					
					if( !isTime && !isFraction && !isOrdinal && !isOrdinary && numString.size()==1 && ifContainsHyphen(numString.get(0).getWord()))//for time like eight-thirty
					{	
						boolean hyphenatedAllCDWords=false;
						ArrayList<TaggedWord> withouthyphenetedTime=new ArrayList<TaggedWord>();
						withouthyphenetedTime=removeHyphens(numString.get(0).getWord());
						if( (withouthyphenetedTime.get(0).getWord().equals("eleven") || withouthyphenetedTime.get(0).getWord().equals("twelve") || withouthyphenetedTime.get(0).getWord().equals("ten") ||dictionary.containsDigits(withouthyphenetedTime.get(0).getWord()) ) && !dictionary.containsNonCDFractionWord(numString.get(1).getWord()) && !dictionary.containsNonCDWord(numString.get(1).getWord())  )
							hyphenatedAllCDWords=true;// checking if string has CD words ( sometimes eleven/twelve are tagged JJ and for "eight-thirty" after removing hyphens we tagged them as " " which wont be detected)
						else
							hyphenatedAllCDWords=false;
						if( hyphenatedAllCDWords )
						{			ArrayList<TaggedWord> min = new ArrayList<TaggedWord>();
									for(int i=0;i<withouthyphenetedTime.size();++i)
										min.add(numString.get(i));
									min.remove(0);
									if( !dictionary.containsNonCDFractionWord(min.get(0).getWord()) && !dictionary.containsNonCDWord(min.get(0).getWord()))
									{
											double minDigits= dictionary.searchNumericalEquivalent(min);
											if(minDigits<60 && minDigits!=0.5 && minDigits!=0.25)
											{
													isTime=true;
											}
									}
			
						}
					}


					Double number=0.0;
					boolean wrongInput=false, endOfSentence=false;
					
					if(numString.get(0).getWord().equals(" ") && numString.get(0).getTag().equals(" "))
							endOfSentence=true;
					else if(!numString.equals(WrongInput))
					{		
							
							if( isTime )
							{
										numberEquivalent=dictionary.searchTimeEquivalent(numString);
                            }

							else if( isOrdinal )
							{			
										numberEquivalent=wordsToOrdinals(numString);
                            }
                            else if( isFraction )
                            {		
										number=dictionary.searchFractionEquivalent(numString);
                            }
                            else if(isDigit(numString.get(0).getWord()))
							{	
										Double num=Double.parseDouble((numString.get(0).getWord()));			//words like 20 million
										numString.remove(0);
										number=wordsToDigits(num,numString);
							}
							
							else if(isDigit(numString.get(numString.size()-1).getWord()))
							{
										Double num=Double.parseDouble((numString.get(numString.size()-1).getWord()));	//words like half of 20
										numString.remove(numString.size()-1);
										number=wordsToDigits(num,numString);
							}
							
							else
									number=wordsToDigits(numString);
								
					}
					
					else	
							wrongInput=true;
						
					if(number==-1.0)
							wrongInput=true;
						
					for(int i=0;i<currentIndex;++i)
							taggedWords.remove(0);		
				
					if(containsFinalWeightAfterDecimal)
					{	
							number = number*finalWeightAfterDecimal;	
							containsFinalWeightAfterDecimal=false;	
					}
					
					
					if(endOfSentence)
							finalSentence+="";
					
					else if(wrongInput)
							finalSentence+="//Wrong Input, check notation// ";

                    else if(containsMinus)
					{
                    	number=number*-1;
                    	String numberEq=roundOff(number, digitsOfPrecision);
                    	finalSentence+=numberEq+" ";
                    }
					
                    else if(isOrdinal || isTime)
                    		finalSentence+= numberEquivalent +" ";
					
                    else
					{
                    	String numberEq=roundOff(number, digitsOfPrecision);
                    	finalSentence+=numberEq + " ";
                    }
					
					currentIndex=0;
					isTime=false;
					isOrdinal=false;
					isFraction=false;
					isOrdinary=false;
					dictionary.setWeightSystem();
	
			}
			
			
			return finalSentence;
	    
	}
	
	
	public String getSentence()
	{
		/*prints a ‘Sentence’ on the screen
	 */
			String s="";
			
			for(int i=0;i<this.taggedWords.size();i++)
			{
					s+=this.taggedWords.get(i).getWord()+ " ";
			}
			
			return s;
	}
	
	
	public boolean ifContainsHyphen(String word){
		/*
	returns true if word contains hyphen(“-”)else returns false
		 */
			
			for(int i=0;i<word.length();++i)
					if(word.charAt(i)=='-')
							return true;
			
			return false;
	}
	
	
	public ArrayList<TaggedWord> removeHyphens(String word)
	{	
		/*
		breaks word at every hyphen. Each part is stored in the arrayList and returned.
		For example if  word = two-and-a-half , ArrayList will have two, and ,a ,half as its components. Their tags are set to “ ”. 
		*/
			ArrayList<TaggedWord> withoutHyphen=new ArrayList<TaggedWord>();
			String newWord="";
			for(int i=0;i<word.length();i++)
			{	
					if(word.charAt(i)!='-')
							newWord+=word.charAt(i);
					else
					{	
							withoutHyphen.add(new TaggedWord(newWord, " "));
							newWord="";
					}
			}
			
			withoutHyphen.add(new TaggedWord(newWord," "));
			return withoutHyphen;
	}
	
	public static String setPrecision(double nbr , int decimalPlaces)
	{
		/*
		converts nbr to a number with ‘decimalPlaces’ number of digits after decimal point.
		 */
		//function receives the number and the no. of decimal places we want			    
			    int integer_Part = (int) nbr;
			    double float_Part = nbr - integer_Part;
			    
			    String final_nbr = String.valueOf(integer_Part) + "." ;
			    
			    String floatPart=float_Part+"";
			    int i=2;
			    
			    while(i<=decimalPlaces+1)
			    {		
			    		if(i<floatPart.length() && floatPart.charAt(i)=='.')			//The float part can be of two forms: 0.453 and -0.453 
			    				System.out.print("");
			    		else if(i<floatPart.length())
			    				final_nbr+=floatPart.charAt(i);
			    		else
			    			final_nbr+="0";
			    	++i;
			    }
			    
			    return final_nbr;

	}
	public String roundOff(double nbr, int decimalPlaces)
	{
		/**/
			int integerPart = (int) nbr;
			double floatPart = nbr - integerPart;
			if(floatPart>=0.99 )
			{
					if(nbr>0)
							return (getWholePart(nbr)+1) +"";
					else
							return (getWholePart(nbr)-1)+"";
			}
			else if(floatPart==0)
			{										
					return getWholePart(nbr)+"";		//its a whole number so return the whole part only
			}
			else
			{
					return (setPrecision(nbr,decimalPlaces));
			}
	}


	public long getWholePart(double num)
	{
			long wholePart=0;
			wholePart= (long) num;
			return wholePart;
	}


}



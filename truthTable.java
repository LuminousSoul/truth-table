import java.util.*;					//Scanner for input
import java.math.*;					//Math for 2^x

/*
 * 
 * User enters a String -> a logical expression
 * 		String contains ONLY: a-Z, A-Z, (, ), *, +, -
 * 		Logical expression is correct
 * 		Always within parenthasies (NOT "A+B" but "(A+B)"
 * 
 * Prints: 
 * 		Individual variables, no Duplicates
 * 		Logical subexpressions
 * 		Truth table
 * 
 * AND = *
 * OR  = +
 * NOT = -
 * 
 */


public class truthTable {
	
	public static void main(String args[]){
		
		Scanner input = new Scanner(System.in);						//Variable for input
		System.out.print("Input a logical expression: ");			//Ask for expression
		String expression = input.nextLine();						//get expression into String
		
		
		expression = expression.replace(" ", "");					//Gets rid of any spaces entered for readability
		
		
		//~~~Gets a Arraylist of variables~~~
		ArrayList<Character> variables = new ArrayList<Character>();
		variables = getLetterVariables(expression);					//Call FUNCTION, param: String
				
		//This will return a list of variables. No duplicates
		
		
		//~~~The set of logical subexpression~~~						//This section will get each subexpression
		Stack<String> subExpression = new Stack<String>();				//To store sub expressions for printing
		HashMap<Integer, String> hash = new HashMap<Integer, String>();	//Contain expression for calculating
		
		int index1 = expression.length() -1,	 index2 = 0;			//Indexs for searching "(" and ")"
		int hashCount = 1;												//for hash
		
		
		//PRE-CONDITION: index1 is a number larger than -1, Stack list and Hashmap are available
		//POST-CONDITION: Creates a list full of LE for a Stack list and Hashmap
		while(index1 > -1 ){											//while the indexs are within the length
			
			//PRE-CONDITION: expression has "(" character
			//POST-CONDITION: the index of "("
			while(expression.charAt(index1) != '('){					//index looks for first "(" starting on right
				index1--;
			}
			index2 = index1+1;											//Based on found"(", look for the eqivalent ")"
			int equalB = 1;												//count for brackets
			
			
			//PRE-CONDITION: equalB is not zero, expression has ")" or "("
			//POST-CONDITION: Finds the index of the  equivalent ")" to "("
			while (equalB != 0){										//Ends when count is 0
				if (expression.charAt(index2) == ')'){					//If found ")", decrement count
					equalB--;
				} else if (expression.charAt(index2) == '('){			//If found "(", increment
					equalB++;
				}
				
				if (equalB != 0){										//search next character
					index2++;
				}
			}
			

			if (!subExpression.contains(expression.substring(index1+1, index2))){	//if list doesn't have substring
				subExpression.add(expression.substring(index1+1,   index2));		//Add to list
			}
			if (!hash.containsValue(expression.substring(index1+1, index2))){		//If has doesn't have subtring
				hash.put(hashCount, expression.substring(index1+1, index2) );		//Add to hash
				hashCount++;
			}
			
			index1--;																//Move "(" index left for next search
		}
		
		
		
		
		//Full truth table
		int row = (int) Math.pow((double) 2, (double)variables.size());				//Row is 2^(number of variables)
		int column = variables.size() + hash.size();								//column is number of var + num hash(LE)
		
		String[][] table = new String[row+1][column];								//Row +1 for the labels
		
		
		int i=0;															//To add labels to table
		
		//PRE-CONDITION: i =0, variables.size() is not zero, table(2D array) is accessible
		//POST-CONDITION: Fills the variables labels on truth table
		while(i<variables.size()){											//Loop baswed on var num
			table[0][i] = variables.get(i).toString();						//Add the var to that index
			i++;
		}
		
		i=0;																//Loop based on hash count
		//PRE-CONDITION: hash is not empty, variables.size() is an int, i=0
		//POST-CONDITION: Fills the rest of the label row with LE1, LE2 ... strings
		while(i<hash.size()){
			table[0][i+variables.size()] = "LE"+(i+1);						//Add to table after variable num
			i++;
		}
		

		//Putting "T" and "F" based on num of variables
		int numT = row ;											//numT = 2^(varnum)
		boolean pTrue = true;										//True
		int numOfTF = 1;											//number of adding true and false
		
		
		//PRE-CONDITION: variables.size() is not 0, i=0
		//POST-CONDITION: Fills each variable spot with "T" or "F"
		for (i=0; i < variables.size(); i++){						//Loop based on number of variable
			numT /= 2;												//numT = the number of T and False beside each other
			int currentIndex=0;										//current index for row set to zero
			
			
			//PRE-CONDITION: numOfTF is a multiple of 2
			//POST-CONDITION: Loop based on the number of T to F (TTFFTTFF = 2, TFTFTF = 3)
			for(int p=0; p < numOfTF; p++){							//Number of T and F beside each other(eg TTFFTTFF = 2)
				
				//PRE-CONDITION: k=0, will only run twice
				//POST-CONDITION: Used to change boolean from true to false
				for(int k=0; k<2; k++){								//loop for adding "T" then "F"
					
					//PRE-CONDITION: numT, number of consecutive truth values, j=0, pTrue: boolean, table(2Darray)
					//POST-CONDITION: Fills a "T" or "F" section
					for(int j=0;j<numT;j++){						//Loop for adding consecutive "T" or "F"
						if(pTrue){
							table[currentIndex+1][i] = "T";			//Set current index to "T" if pTrue
						} else {
							table[currentIndex+1][i] = "F";			//Set current index to "F" if pTrue is false
						}
						currentIndex++;								//Increment to next index
					}
					pTrue = false;									//For the next iteration of the upper loop, adds false
				}
				pTrue = true;										//Reset
			}
			numOfTF *= 2;											//mults 2 to increase the numb of loop. TFTFTFTF = 4
		}
		
		
		//CALCULATING THE VALUES
		
		//PRE-CONDITION: hash.size() integer, numLE integer
		//POST-CONDITION: Calculates all expressions
		for(int numLE =1; numLE <= hash.size();numLE++){			//Loops based on number of LE
			int goingThrough = 1;				
			
			//PRE-CONDITION: goingThrough: integer less than table.length
			//POST-CONDITION: Calcualtes expression for 1 LE
			while(goingThrough != table.length){					//Goes through each index for LE
				String logE = hash.get(numLE);						//Get expression based on numLE
				
				
				//PRE-CONDITION: hash.size() integer, k integer, String LogE(current LE)
				//POST-CONDITION: Replace inner LE with truth values
				for(int k=hash.size(); k > 0; k--){					//Starting from the biggest LE to smallest
					String logReplace = hash.get(k);				//Get LE from hash
					if (logE.contains(logReplace) && numLE != k &&
							table[goingThrough][(variables.size()+(k-1))] != null){	//If the current LE contains another LE that is solved
						logE = logE.replace("("+logReplace+")",table[goingThrough][(variables.size()+(k-1))]);	//Replace
					}
				}
				
				
				//PRE-CONDITION:  
				//POST-CONDITION: 
				for(int k=0; k<variables.size(); k++){							//For number of variables
					logE = logE.replace(table[0][k], table[goingThrough][k]);	//Replace all occuring variables with "F" "T" 
				}
				
				
				if(logE.substring(0,1).equals("-")){							//If "-" is at the beginning
					logE= NOTfunc(logE.substring(1));							//Send to NOT function
				} else if(logE.substring(1,2).equals("+")){						//If middle == "+"
					logE = ORfunc(logE.substring(0,1) , logE.substring(2));		//Send to OR func
				} else if(logE.substring(1,2).equals("*")){						//If middle =="*"
					logE = ANDfunc(logE.substring(0,1) , logE.substring(2));	//Send to AND func
				}
				
				
				table[goingThrough][variables.size() + numLE-1]  =  logE;		//Place truth value in table
				goingThrough++;													//Switch row(down)
			}
		}
		
		
		
		//Prints individual variables
		System.out.println("\nSet of Independant variables:");
		i=0;
		
		//PRE-CONDITION: variables arraylist
		//POST-CONDITION: prints each value in variables
		while (i < variables.size()){											//Iterate through variables
			System.out.println((i+1) + ": " + variables.get(i) );
			i++;
		}
		
		
		//Prints logical subexpression
		System.out.println("\nSet of logical subexpressions and logical expression:");
		Set set = hash.entrySet();
		Iterator iterator = set.iterator();
		
		
		//PRE-CONDITION: iterator, 
		//POST-CONDITION: Printgs out each LE within Hash
		while(iterator.hasNext()){													//While there is another proceding
			Map.Entry mapEntry = (Map.Entry) iterator.next();						//mapEntry equals the next occuring
			System.out.println("LE"+mapEntry.getKey()+": " + mapEntry.getValue());	//Prints entry
		}
		
		
		//Prints table
		System.out.println("\nTruth Table:");
		
		//PRE-CONDITION:  table 2D array
		//POST-CONDITION: Print out the whole table
		for(i=0; i<table.length; i++){
			
			//PRE-CONDITION: table[i] column of array
			//POST-CONDITION:Printg a column from table
			for(int j =0; j<table[i].length; j++){
				System.out.printf("%-10s" ,table[i][j]);
			}
			System.out.println();
		}
		
		
		
		
	}
	
	
	
	//Method that gets the alphabetic variables, removes duplicates and sorts it
	public static ArrayList getLetterVariables(String string){
		ArrayList<Character> variables = new ArrayList<Character>();
		int i =0, index =0;
		
		//PRE-CONDITION:  string(expression), empty variables
		//POST-CONDITION: variables arraylist with all aphabet characters
		while (i < string.length()){
			int ascVal = (int)string.charAt(i);				//Get ascii value of a character
			if ((ascVal >= 65 && ascVal <= 90) || (ascVal >= 97 && ascVal <= 122)){ 	//If ascii value is a-z, A-Z
				variables.add(string.charAt(i));
			}
			i++;
		}
		
		
		//Search for duplicates
		i = 0;												//Index that is being compared
		
		//PRE-CONDITION:  variables arraylist
		//POST-CONDITION: Gets rid of duplicates
		while (i < variables.size()){						//Keeps going until the end of the list
			int f = i+1;									//Index that goes through each character
			while(f < variables.size()){					//Keeps going until the end of the list
				if (variables.get(i) == variables.get(f)){	//If the characters are equal
					variables.remove(f);					//Remove from list, shifts left
					f--;									//Decrement i to check the same character again
				}
				f++;
			}
			i++;
		}
		
		//Orders the variables
		Collections.sort(variables);						//Sorts the variables within ArrayList alphabetically
		return variables;									//Returns the list
	}
	
	
	
	public static String ANDfunc(String A, String B){
		if (A.equals("T") && B.equals("T")){				//If input1 and input 2 both equal "T"
			return "T";										//return "T"
		}
		return "F";											//Otherwise return "F"
	}
	
	
	public static String ORfunc(String A, String B){
		if(A.equals("T") || B.equals("T")){					//If either input1 or input2 equal "T"
			return "T";										//return "T"
		}
		return "F";											//otherwise return "F"
	}
	
	
	public static String NOTfunc(String A){
		if (A.equals("T")){									//If A equals "T"
			return "F";										//return "F"
		}
		return "T";											//otherwise return "T"
	}
	
	
}
# General
This program takes in a logical expression and creates a truth table along side all subexpressions in the expression


## Problem Description
A. _Input:_ Logical Expression as a String (Inputted by user)
B. _Output:_ 
- Set of independant varriables
- Set of logical expressions
- Full Truth Table

## Problem Specifications
A. Input
- Program should ask user to input a String
- The String represents a logical expression
- The only characters allowed to be used:
  - English Alphabet letters from a to z and A to Z (case sensitive)
  - Left and Right parenthesis: (  )
  - Symbols: *  +  -
- You can assume that the String is in correct format. You do not need to check if the user:
  - Entered a non-valid character
  - Parentheses are not matching
  - Logical errors

B. Output
- The set of independant variables
- The set of logical subexpression and the logical expression
- The full truth table

## General Specifications
- Each of the Symbols represent a logical expression:
  - AND <-> *
  - OR <-> +
  - NOT <-> -
 


## Programming Techniques
- Stack Class (LIFO)
- List
- Dictionary
- Arrays

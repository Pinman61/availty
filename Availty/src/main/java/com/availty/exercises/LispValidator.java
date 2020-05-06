package com.availty.exercises;

import java.util.Scanner;
import java.util.Stack; 

public class LispValidator {

	public static void main(String[] args) {
		// Scanner to read one line of command line Input from User
		// checks for valid enclosing chars ([{}]) and ignores other chars 
        Scanner in = new Scanner(System.in); 
  
        String s = in.nextLine(); 
        System.out.println("LISP input ="+ s + " valid =" + isValidString(s) + "."); 
         
        
        in.close(); 
	}
	
	public static boolean isValidString(String x) {
		char chars[] = x.toCharArray();
		Stack<Character> set = new Stack();
		for (Character c : chars) {
			if (c == '{' || c == '[' || c == '(') {
				set.push(c);
			} else if (c == ']') {
				if (set.isEmpty() || set.peek() != '[') {
					return false;
				} 
				set.pop();
			} else if (c == ')') {
				if (set.isEmpty() ||  set.peek() != '(') 
					return false;
				set.pop();	
			} else if (c == '}') {
				if (set.isEmpty() ||  set.peek() != '{') 
					return false;
				set.pop();	
			}
		}
		return set.size() == 0;
	}	
}

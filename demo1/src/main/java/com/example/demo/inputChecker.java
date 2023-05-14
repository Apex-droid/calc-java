package com.example.demo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputChecker {
	
	private static Stack<Character> scope_stack = new Stack<>(); 
	private final static String digit = "((\\d+\\.?(\\d+)?(E\\d+)?)|E)";
	private static String dig_sy = "[\\d\\.E]+";
	private static String scopeO ="(\\()";
	private static String scopeC = "(\\))";
	private static String hp_sign =  "((\\*)|(\\/)|(\\%))";
	private static String lp_sign = "((\\+)|(\\-))";
	private static String sin_co = "((cos)|(sin)|(asin)|(acos)|(tan)|(atan)|(ln)|(log)|(sqrt))";
	private static String lpSingE = lp_sign + "(" + scopeC + "|" +  hp_sign + "|" +  "(\\^)" + ")";
	private static String hpSingE = hp_sign + "(" + scopeC  + "|" +  "(\\^)" + "|" + hp_sign + ")";
	private static String DE = "("+ digit +"((X)|" + sin_co + "|" + scopeO + "))";
	private static String XE = "(X)("+ digit +"|" + sin_co + "|" + scopeO + "|" + "(X)" + ")";
	private static String scopeOE = scopeO + "(" + hp_sign + "|" + scopeC + "^" + ")";
	private static String scopeCE = scopeC + "(" + "(X)" +  "|" + digit + "|" + scopeO +
	"|" + sin_co + ")";
	private static String powE = "((\\^)" +  "(" + hp_sign + "|(\\^)" + "|" + scopeC + "))";
	private static String three_lpE = lp_sign + lp_sign + lp_sign;
	private static String end = "^(.+?(" + hp_sign +  "|" + lp_sign + "|" +  "(\\^)"  + "|" + scopeO + "|" + sin_co + "))$";
	private static String begin =  "(?<=^)(" + hp_sign +  "|" + scopeC + ")";
	private static String ExpRegex = lpSingE +  "|" + hpSingE +   
	"|" + DE +  "|" + XE +  "|" + scopeOE + "|" + end + "|" + begin 
	+ "|" + scopeCE + "|" + powE + "|" + three_lpE ;
	
	private static Pattern p = Pattern.compile(ExpRegex);
	private  static Pattern half_p = Pattern.compile("((enter x: )|(input error\n try other x: ))" + digit);
	
	
	public static boolean scopeCheck( String exp)
	{
		for(int i = 0; i < exp.length(); i++)
			if(exp.charAt(i) == '(')
				scope_stack.push(exp.charAt(i));
			else if(exp.charAt(i) == ')'){
				if (scope_stack.isEmpty())
					return false;
			else if (scope_stack.peek() == '(') 
					scope_stack.pop();				 
			}
		if (scope_stack.isEmpty())
			return true;
		return false;
	}
	
	public static boolean Check(String exp)
	{
		Matcher m = p.matcher(exp);
		if (scopeCheck(exp)) {
			if (m.find())
				return false;
			if (!(symbolsOrdering("[a-z]+", sin_co, exp) && symbolsOrdering(dig_sy, digit, exp)))
				return false;
		}
		else
			return false;
		return true;
	}
	public static boolean halfCheck(String exp){
		 Matcher m = half_p.matcher(exp);
	        if (m.matches())
	        	return true;
	        return false;
	}
	
	private static boolean symbolsOrdering(String symbols, String order, String exp)
	{
		Pattern liters = Pattern.compile(symbols);
		Matcher m = liters.matcher(exp);
		while(m.find())
			if(!exp.substring(m.start(),m.end()).matches(order))
				return false;
		return true;
	}
}
package com.example.demo;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputChecker {
	
	private static Stack<Character> scope_stack = new Stack<>(); 
	private static String scopeO ="(\\()";
	private static String scopeC = "(\\))";
	private static String hp_sign =  "((\\*)|(\\/)|(\\%))";
	private static String lp_sign = "((\\+)|(\\-))";
	private static String sin_co = " ((cos)|(sin)|(asin)|(acos)|(tan)|(atan)|(ln)|(log)|(sqrt))";
	private static String lpSingE = lp_sign + "(" + scopeC + "|" +  hp_sign + "|" +  "(\\^)" + ")";
	private static String hpSingE = hp_sign + "(" + scopeC  + "|" +  "(\\^)" + "|" + hp_sign + ")";
	private static String DDE = "(\\d+\\.(\\d+)?)(\\d+\\.(\\d+)?)";
	private static String DE = "(\\d+\\.?(\\d+)?)((X)|" + sin_co + "|" + scopeO + ")";
	private static String XE = "(X)((\\d+\\.?(\\d+)?)|" + sin_co + "|" + scopeO + "|" + "(X)" + ")";
	private static String scopeOE = scopeO + "(" + hp_sign + "|" + scopeC + "^" + ")";
	private static String scopeCE = scopeC + "(" + "(X)" +  "|" + "(\\d+\\.?(\\d+)?)" + "|" + scopeO +
	"|" + sin_co + ")";
	private static String powE = "(\\^)" +  "(" + hp_sign +  "|" + lp_sign  + "|(\\^)" + "|" + scopeC + ")";
	private static String three_lpE = lp_sign + lp_sign + lp_sign  ;
	private static String end = "^(.+?(" + hp_sign +  "|" + lp_sign + "|" + scopeO + "|" + sin_co +  "))$";
	private static String begin =  "(?<=^)(" + hp_sign +  "|" + scopeC + ")";
	private static String ExpRegex = lpSingE +  "|" + hpSingE +  "|" + DDE +  
	"|" + DE +  "|" + XE +  "|" + scopeOE + "|" + end + "|" + begin 
	+ "|" + scopeCE + "|" + powE + "|" + three_lpE ;
	
	private static Pattern p = Pattern.compile(ExpRegex);
	private  static Pattern half_p = Pattern.compile("((enter x: )|(input error\n try other x: ))" + "(\\d+\\.?(\\d+)?)");
	private static Pattern liters = Pattern.compile("[a-z]");
	
	public static boolean scopeCheck(String exp)
	{
		for(int i = 0; i < exp.length(); i++)
			if(exp.charAt(i) == ')')
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
		if (scopeCheck(exp)){
			if (m.find())
				return false;
			m = liters.matcher(exp);
			while(m.find())
				if(!exp.substring(m.start(),m.end()).matches(sin_co))
					return false;
			return true;
		}
		else 
			return false;
	}
	public static boolean halfCheck(String exp){
		 Matcher m = half_p.matcher(exp);
	        if (m.matches())
	        	return true;
	        return false;
	}
}
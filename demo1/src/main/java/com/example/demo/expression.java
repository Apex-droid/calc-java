package com.example.demo;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Stack;
import static java.lang.Math.*;

public class expression {
    public boolean half_done ;

    private   ArrayList<String> stack_list;

    private ArrayList<String> str;

    private Stack<String> stack;

    protected double X;


    protected final static String scopes = "(\\()|(\\))|";
    protected final static String siNco = "(cos)|(sin)|(asin)|(acos)|(tan)|(atan)|(ln)|(log)|(sqrt)";
    protected final static String sings = "(\\+)|(\\-)|(\\*)|(\\^)|(\\%)|(\\/)|u|U|" + siNco;
    protected final static String digits = "((\\d+\\.?(\\d+)?(E\\d+)?)|E)|";
    protected final static String digit = "((\\d+\\.?(\\d+)?(E\\d+)?)|E)";
    protected final static String regular = scopes + digits + sings + "|(X)";
    protected final static Pattern p = Pattern.compile(regular);

   
   private static  NavigableMap<String, Integer> priority = new TreeMap<String,Integer>()
   {
       private static final long serialVersionUID = 1L;

	{
           put("(", 0);
           put("", 0);
           put("+", 1);
           put("-", 1);
           put("*", 2);
           put("/", 2);
           put("%", 2);
           put("^", 3);
           put("u", 4);
           put("U", 4);
           put("cos", 5);
           put("sin", 5);
           put("asin", 5);
           put("acos", 5);
           put("tan", 5);
           put("atan", 5);
           put("ln", 5);
           put("log", 5);
           put("sqrt", 5);

       }
   };

   public void rebuild_ex(String to_parse){
       this.str.clear();
       this.stack.clear();
       this.stack_list.clear();
       half_done = false; 
       parse(to_parse);
   }
   
   expression() {
       this.str = new ArrayList<>();
       this.stack = new Stack<>();
       this.stack_list = new ArrayList<>();
   }


        public void get_X(double X) {
            this.X = X;
        }
    public  void reverse_stack() {
        int i = stack_list.size();

        while(i-- > 0)
            stack.push(stack_list.get(i));
        //stack_list.clear();
        //stack = second_stack;
        //if (!x_pos.isEmpty())
           // half_done = true;

    }
    public void parse_half(String for_parse) {
       String substr = new String();
       Pattern dig = Pattern.compile(digit);
       Matcher m = dig.matcher(for_parse);
       while(m.find())
    	   substr = for_parse.substring(m.start(), m.end());
        X = Double.valueOf(substr);
              //  stack.pop();
                //stack.push(str);
            //
          //}
    }
    private void sign_sort(String sign, Stack<String> second_stack) {
        if (priority.get(second_stack.peek()) < priority.get(sign))
            second_stack.push(sign);
        else {
            stack_list.add(second_stack.pop());
            second_stack.push(sign);
        }
    }
    protected void parse(String for_parse){

        Stack<String> second_stack = new Stack<>();
        //ArrayList<String> second_stack = new ArrayList<>();
        Matcher m = p.matcher(for_parse);
        second_stack.add("");
        //int inter_arr = second_stack.size() - 1;
        while (m.find())
            str.add(for_parse.substring(m.start(), m.end()));
        for (int i = 0; i < str.size(); i++)
        {
            if(str.get(i).matches(digits) || str.get(i).matches("X"))
                stack_list.add(str.get(i));
            else if (str.get(i).matches("(\\()")){
                second_stack.push(str.get(i));
            }
            else if (str.get(i).matches("(\\))")){
                while(!second_stack.peek().matches("(\\()")){
                    stack_list.add(second_stack.pop());
                }
                second_stack.pop();
            }
            else if (str.get(i).matches(sings)) {
                if(str.get(i).matches("\\-") && (i == 0 
                		|| !(str.get(i - 1).matches(digits) || str.get(i - 1).matches("(\\))"))))
                    sign_sort("u", second_stack);
                else if(str.get(i).matches("\\+") && (i == 0 
                		|| !(str.get(i - 1).matches(digits) || str.get(i - 1).matches("(\\))"))))
                    sign_sort("U", second_stack);
                else
                    sign_sort(str.get(i), second_stack);
            }
        }
        while (!second_stack.isEmpty())
            stack_list.add(second_stack.pop());
       //X_pre();
             //return second_stack;
        reverse_stack();
    }

    
    protected  void  X_pre() {
    	 for(String str : stack_list)
             if(str.equals("X"))
                 half_done = true;
    }
    
    public  Double calc(){
        //parse(for_parse);
        double temp;
        Stack <Double> digit_stack = new Stack<>();
        digit_stack.push(0.0);
        while (stack.peek() != "") {
            if (stack.peek().matches(digits)) {
            	if (stack.peek().matches("E")) {
            		digit_stack.push(2.71828183);
            		stack.pop();
            	}
            	else
            		digit_stack.push(Double.valueOf(stack.pop()));
            }
            else if(stack.peek().matches("X")) {
                digit_stack.push(X);
                stack.pop();
            }
            else if (stack.peek().matches(sings))
            {
                switch (stack.pop()){
                    case "+":
                        digit_stack.push(digit_stack.pop() + digit_stack.pop());
                    break;
                    case "-":
                        temp = digit_stack.pop();
                        digit_stack.push(-temp + digit_stack.pop());
                        break;
                    case "u":
                        digit_stack.push(-digit_stack.pop());
                        break;
                    case "U":
                        break;
                    case "/":
                        temp = digit_stack.pop();
                        digit_stack.push(digit_stack.pop() / temp);
                        break;
                    case "*":
                        digit_stack.push(digit_stack.pop() * digit_stack.pop());
                    break;
                    case "^":
                        temp = digit_stack.pop();
                        digit_stack.push(pow(digit_stack.pop(),temp));
                        break;
                    case "cos":
                        digit_stack.push(cos(digit_stack.pop()));
                        break;
                    case "sin":
                        digit_stack.push(sin(digit_stack.pop()));
                        break;
                    case "asin":
                        digit_stack.push(asin(digit_stack.pop()));
                        break;
                    case "acos":
                        digit_stack.push(acos(digit_stack.pop()) );
                        break;
                    case "tan":
                        digit_stack.push(tan(digit_stack.pop()));
                        break;
                    case "atan":
                        digit_stack.push(atan(digit_stack.pop()));
                    case "ln":
                        digit_stack.push(log(digit_stack.pop()));
                        break;
                    case "log":
                        digit_stack.push(log10(digit_stack.pop()));
                        break;
                    case "sqrt":
                        digit_stack.push(sqrt(digit_stack.pop()));
                        break;
                }

            }
        }
            return(digit_stack.pop());
    }

}

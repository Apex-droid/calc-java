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

    private  Stack<String> stack ;

    private ArrayList<Integer> x_pos ;
    private ArrayList<String> str;
    private ArrayList<String> second_stack;

    private final static String scopes = "(\\()|(\\))|";
    private final static String siNco = "(cos)|(sin)";
    private final static String sings = "(\\+)|(\\-)|(\\*)|(\\^)|(\\%)|(\\/)|" + siNco;
    private final static  String digits = "(\\d+\\.?(\\d+)?)|";
    private final static String regular = scopes + digits + sings;
    private final static Pattern p = Pattern.compile(regular);

    private  static Pattern half_p = Pattern.compile("Eter x: " + "(\\d+\\.?(\\d+)?)");
   private static  NavigableMap<String, Integer> priority = new TreeMap<>()
   {
       {
           put("(",0);
           put("", 0);
           put("+", 1);
           put("-", 1);
           put("*", 2);
           put("/", 2);
           put("%", 2);
           put("^", 3);
           put("cos", 4);
           put("sin", 4);
       }
   };

   expression(String to_parse){

       this.str= new ArrayList<>();
       this.stack = new Stack<>();
       second_stack = new ArrayList<>();
       parse(to_parse);
   }



    public  void reverse_stack() {
        int i = second_stack.size();

        while(i-- > 0)
            stack.push(second_stack.get(i));
        second_stack.clear();
        //stack = second_stack;
        //if (!x_pos.isEmpty())
           // half_done = true;

    }
    public void parse_half(String for_parse) {

        Matcher m = half_p.matcher(for_parse);
        if (m.matches())
            for_parse.replaceAll("Eter x: ","");
        for(String str: second_stack) //{
            if (str.equals("X"))
                str = for_parse;
        reverse_stack();
              //  stack.pop();
                //stack.push(str);
            //}
        //}
    }
    private void parse(String for_parse){

        //ArrayList<String> second_stack = new ArrayList<>();
        Matcher m = p.matcher(for_parse);
        second_stack.add("");
        //int inter_arr = second_stack.size() - 1;
        while (m.find())
            str.add(for_parse.substring(m.start(), m.end()));
        for (String sub: str)
        {
            if(sub.matches(digits))
                stack.push(sub);
            else if (sub.matches("(\\()")){
                second_stack.add(sub);
            }
            else if (sub.matches("(\\))")){
                while(!second_stack.get(second_stack.size() - 1).matches("(\\()")){
                    stack.push(second_stack.get(second_stack.size() - 1));
                    second_stack.remove(second_stack.size() - 1);
                }
                second_stack.remove(second_stack.size() - 1);
            }
            else if (sub.matches(sings)) {
                if (priority.get(second_stack.get(second_stack.size() - 1)) <= priority.get(sub)){
                    second_stack.add(sub);
                }
                else {
                    stack.push(second_stack.get(second_stack.size() - 1));
                    second_stack.remove(second_stack.size() - 1);
                    second_stack.add(sub);
                }
            }
        }
        for(String str : second_stack)
                if(str.equals("X"))
                    half_done = true;
             //return second_stack;
        //reverse_stack();
    }

    public  double calc(/*String for_parse*/){
        //parse(for_parse);
        double temp;
        Stack <Double> digit_stack = new Stack<>();
        while (stack.peek() != "") {
            if (stack.peek().matches(digits))
                digit_stack.push(Double.valueOf(stack.pop()));
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
                    case "/":
                        temp = digit_stack.pop();
                        digit_stack.push(digit_stack.pop() * temp);
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
                }

            }
        }
            return(digit_stack.pop());
    }

}

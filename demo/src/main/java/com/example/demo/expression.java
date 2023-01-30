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
    private ArrayList<String> str = new ArrayList<>();

    private final static String scopes = "(\\()|(\\))|";
    private final static String siNco = "(cos)|(sin)";
    private final static String sings = "(\\+)|(\\-)|(\\*)|(\\^)|(\\%)|(\\/)|" + siNco;
    private final static  String digits = "(\\d+\\.?(\\d+)?)|";
    private final static String regular = scopes + digits + sings;
    private final static Pattern p = Pattern.compile(regular);

    private  static Pattern half_p = Pattern.compile("Eter x:" + "(\\d+\\.?(\\d+)?)");
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

        ArrayList<String> str = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        ArrayList<Integer> x_pos = new ArrayList<>();
        parse(to_parse);

   }



    public  void reverse_stack(Stack<String> second_stack) {
        int xpos = 0;

        while(!stack.isEmpty()) {
            second_stack.push(stack.pop());
            if (second_stack.peek().equals("x"))
                x_pos.add(xpos++);
        }
        stack = second_stack;
        if (!x_pos.isEmpty())
            half_done = true;

    }
    public void parse_half(String for_parse) {

        Matcher m = half_p.matcher(for_parse);
        if ()

    }
    private  void parse(String for_parse){

        Stack<String> second_stack = new Stack<>();
        Matcher m = p.matcher(for_parse);
        second_stack.push("");
        while (m.find())
            str.add(for_parse.substring(m.start(), m.end()));
        for (String sub: str)
        {
            if(sub.matches(digits))
                stack.push(sub);
            else if (sub.matches("(\\()")){
                second_stack.push(sub);
            }
            else if (sub.matches("(\\))")){
                while(!second_stack.peek().matches("(\\()")){
                    stack.push(second_stack.pop());
                }
                second_stack.pop();
            }
            else if (sub.matches(sings)) {
                if (priority.get(second_stack.peek()) <= priority.get(sub)){
                    second_stack.push(sub);
                }
                else {
                    stack.push(second_stack.pop());
                    second_stack.push(sub);
                }
            }
        }
        reverse_stack(second_stack);
        if
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

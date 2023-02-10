package com.example.demo;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

import static java.lang.Math.*;

public class expression {
    public boolean half_done ;

    private   ArrayList<String> stack_list;

    private ArrayList<Integer> x_pos ;
    private ArrayList<String> str;

    private Stack<String> stack;

    private double X;


    private final static String scopes = "(\\()|(\\))|";
    private final static String siNco = "(cos)|(sin)";
    private final static String sings = "(\\+)|(\\-)|(\\*)|(\\^)|(\\%)|(\\/)|" + siNco;
    private final static  String digits = "(\\d+\\.?(\\d+)?)|";
    private final static String regular = scopes + digits + sings + "|(X)";
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
       this.stack_list = new ArrayList<>();
       parse(to_parse);
   }


        public void get_X(double X)
        {
            this.X = X;
        }
    public  void reverse_stack() {
        int i = stack_list.size();

        while(i-- > 0)
            stack.push(stack_list.get(i));
        stack_list.clear();
        //stack = second_stack;
        //if (!x_pos.isEmpty())
           // half_done = true;

    }
    public void parse_half(String for_parse) {

        Matcher m = half_p.matcher(for_parse);
        if (m.matches())
            for_parse.replaceAll("Eter x: ","");
        //for(String str: stack_list)
                //Collections.replaceAll(stack_list,"X",for_parse);
        X = Double.valueOf(for_parse);
              //  stack.pop();
                //stack.push(str);
            //
          //}
    }
    private void parse(String for_parse){

        Stack<String> second_stack = new Stack<>();
        //ArrayList<String> second_stack = new ArrayList<>();
        Matcher m = p.matcher(for_parse);
        second_stack.add("");
        //int inter_arr = second_stack.size() - 1;
        while (m.find())
            str.add(for_parse.substring(m.start(), m.end()));
        for (String sub: str)
        {
            if(sub.matches(digits) || sub.matches("X"))
                stack_list.add(sub);
            else if (sub.matches("(\\()")){
                second_stack.push(sub);
            }
            else if (sub.matches("(\\))")){
                while(!second_stack.peek().matches("(\\()")){
                    stack_list.add(second_stack.pop());
                }
                second_stack.pop();
            }
            else if (sub.matches(sings)) {
                if (priority.get(second_stack.peek()) <= priority.get(sub)){
                    second_stack.push(sub);
                }
                else {
                    stack_list.add(second_stack.pop());
                    //second_stack.remove(second_stack.size() - 1);
                    second_stack.push(sub);
                }
            }
        }
        while (!second_stack.isEmpty())
            stack_list.add(second_stack.pop());
        for(String str : stack_list)
                if(str.equals("X"))
                    half_done = true;
             //return second_stack;
        reverse_stack();
    }

    public  double calc(){
        //parse(for_parse);
        double temp;
        Stack <Double> digit_stack = new Stack<>();
        digit_stack.push(0.0);
        while (stack.peek() != "") {
            if (stack.peek().matches(digits))
                digit_stack.push(Double.valueOf(stack.pop()));
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

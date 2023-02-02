package com.example.demo;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Model {

    private static expression expr;
    private String text = "";

    interface Listener {
         void txt_changer(String txt);
    }


    Listener listener;

    public void rebuild_ex()
    {
        expr = new expression(text);
    }
    public void edit(String new_txt) {
        if (new_txt.equals("<-"))
            text = StringUtils.chop(text);
        else
            text += new_txt;
        if (this.listener != null)
            listener.txt_changer(text);
    }
    public void enter()
    {
        if(expr.half_done) {
            expr.parse_half(text);
            text = Double.toString(expr.calc());
            expr.half_done = false;
        }
        else {
            expr = new expression(text);
                if(!expr.half_done) {
                    expr.reverse_stack();
                    text = Double.toString(expr.calc());
                }
                else
                    text = "enter x: ";
        }
        listener.txt_changer(text);
    }
    public double y_graf_func(double x) {
        String X = Double.toString(x);
        expr.parse_half(X);
        return (expr.calc());
    }

}

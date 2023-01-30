package com.example.demo;
import org.apache.commons.lang3.StringUtils;

public class Model {

    private static expression expr = new expression();
    private String text = "ththth";

    interface Listener {
         void txt_changer(String txt);
    }


    Listener listener;

    public void edit(String new_txt) {
        if (new_txt.equals("<-"))
            text = StringUtils.chop(text);
        else
            text += new_txt;
        if (this.listener != null)
            listener.txt_changer(text);
    }
    public void enter(String new_txt)
    {
        if(expr.half_done) {
            expr.parse_half(new_txt);
            expr.calc();
        }
        else {
            expr = new expression(new_txt);
                if(!expr.half_done)
                    text = expr.calc();
                else
                    text = "enter x: ";
        }
        listener.txt_changer(text);
    }
}

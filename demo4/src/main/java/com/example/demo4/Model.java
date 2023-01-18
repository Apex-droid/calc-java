package com.example.demo4;
//import java.StringUtils;

public class Model {

    private String text = "";

    interface Listener {
         void txt_changer(String txt);
    }

    Listener listener;

    public void edit(String new_txt) {
        text += new_txt;
        if(this.listener != null)
            listener.txt_changer(text);
       // if (new_txt == "<-")

    };

}

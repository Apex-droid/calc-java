package com.example.demo;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Formatter;
import java.util.Objects;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class Model {

    private static expression expr;
    private String text = "";
    private History history = new History();
     Model() {

         try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ALEX\\Desktop\\save.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream))
         {objectOutputStream.writeObject(history);}
         catch (IOException e) {
             System.err.println("Error writing from file");
         }

         //catch(IOException e) {
         //throw new RuntimeException(e);

     }

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
    public void enter()  {
        NumberFormat nf = new DecimalFormat("0.######");
        if(expr != null && expr.half_done) {
            expr.parse_half(text);
            history.add_op(text);
            text = nf.format(expr.calc());
            history.add_res(text);
            expr.half_done = false;
            save_hist();
        }
        else {
            expr = new expression(text);
                if(!expr.half_done) {
                    history.add_op(text);
                    expr.reverse_stack();
                    text = nf.format(expr.calc());
                    history.add_res(text);
                    save_hist();
                }
                else
                    text = "enter x: ";
        }
        listener.txt_changer(text);
    }
    public double y_graf_func(double x) {
        rebuild_ex();
        expr.get_X(x);
        return (expr.calc());
    }
    public boolean isHistEmpty() {
        if (history.size() == 0)
            return true;
        else
            return false;
    }
    //@Override
    protected void save_hist()  {
        //FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Username\\Desktop\\save.ser");
        //ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        //History savedGame = (History) objectInputStream.readObject();
        try (FileInputStream fileInputStream = new FileInputStream("\\Users\\ahelper\\Desktop\\save.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream))
        {History savedGame = (History) objectInputStream.readObject();}
        catch (IOException | ClassNotFoundException e) {
            System.err.println("save error");
        }
    }

    public String[] history_array() {
        return history.result_array();
    }

}

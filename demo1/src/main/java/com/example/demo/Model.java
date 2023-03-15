package com.example.demo;
import org.apache.commons.lang3.StringUtils;
import java.nio.file.Path; 
import javafx.collections.ObservableArray;

import java.io.*;
import java.util.Formatter;
import java.util.Objects;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class Model extends expression {

    //private static expression expr;
    private String text = "";
    
    private History history = new History();
    private int histIndex;
    Model() {
          File file = new File("/Users/ahelper/Documents/save.ser");
            if (file.exists() && !file.isDirectory()) {
                try (FileInputStream fileInputStream = new FileInputStream("/Users/ahelper/Documents/save.ser");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    history = (History) objectInputStream.readObject();
                }
                catch (IOException | ClassNotFoundException e){
                    System.err.println("Error writing from file");
                }
            }
            else
            try {
                    file.createNewFile();
                }
                catch (IOException e){
                    System.err.println("Error of file creating");
                }
            histIndex = history.size() - 1;
         //catch(IOException e) {
         //throw new RuntimeException(e);
    }

    interface Listener {
        void txt_changer(String txt);
         //void xen_method();
    }
    interface xenListener {
        //void txt_changer(String txt);
        void xen_method();
    }


    Listener listener;
    xenListener listener2;

    public void rebuild_ex() {
        rebuild_ex(text);
    }
    public void edit(String new_txt) {
        int textlen = text.length();
        if (text.equals("input error ")) {
            text = "";
        }
        if (new_txt.equals("<-")) {
            if (!text.equals("") && text.charAt(text.length()- 1) != ' ') {
                text = StringUtils.chop(text);
            }
        }
        else {
            if (new_txt.matches(siNco) && (new_txt.length() + text.length()) < 255) {
                text += new_txt + "(";
            }
            else if((new_txt.length() + text.length()) < 256) {
                text += new_txt;
            }
        }
        if (this.listener != null) {
            listener.txt_changer(text);
        }
    }

    public void clean(){
        text = "";
        if (this.listener != null) {
            listener.txt_changer(text);
        }
    }
    public void unknown_with_checking()
    {
        if (inputChecker.Check(text)) {
            if(listener2 != null) {
                listener2.xen_method();
            }
        }
        else {
            text = "input error ";
        }
        if (this.listener != null) {
            listener.txt_changer(text);
        }
    }
    public void enter()  {
        NumberFormat nf = new DecimalFormat ("0.#######");
       
        Double result;
            if(half_done) {
                if(inputChecker.halfCheck(text)) {
                parse_half(text);
                //history.add_op(text);
                text = nf.format(calc());
                history.add_res(text + "\nwhen X = " + X);
                histIndex = history.size() - 1;
                half_done = false;
                save_hist();
            }
            else {
                    text = "input error\n try other x: ";
                }
            }
            else if (inputChecker.Check(text)) {
                rebuild_ex(text);
                X_pre();
                history.add_op(text);
                if(!half_done) {
                    result = calc();
                    if (!Double.isNaN(result)) {
                        text = String.valueOf(result);
                    }
                    else {
                        text = "" + result;
                    }
                    history.add_res(text);
                    histIndex = history.size() - 1;
                    save_hist();
                }
                else
                    text = "enter x: ";
            }
            else
                text = "input error ";
        if (listener != null)
            listener.txt_changer(text);
    }
    public double y_graf_func(double x) {
        reverse_stack();
        get_X(x);
        return (calc());
    }
    public boolean isHistEmpty() {
        if (history.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    //@Override
    protected void save_hist() {
        //FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Username\\Desktop\\save.ser");
        //ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        //History savedGame = (History) objectInputStream.readObject();
         
        try (FileOutputStream fileOutputStream = new FileOutputStream("/Users/ahelper/Documents/save.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(history);
        }
        catch (IOException e) {
            System.err.println("save error");
        }
    }


    
    public void takeHistoryUp() {
        if (history.size()!= 0) {
            text = history.get_operation(histIndex);
            histIndex--;
            if (histIndex < 0) {
                histIndex = history.size() - 1;
            }
            if (this.listener != null) {
                listener.txt_changer(text);
            }
        }
    }
     public void takeHistoryDown() {
        if (history.size()!= 0) {
            text = history.get_operation(histIndex);
            histIndex++;
            if (histIndex == history.size()){
                histIndex = 0;
            }
            if (this.listener != null) {
                listener.txt_changer(text);
            }
        }
    }
    public void expFromHist(int histIndex) {
        text = history.get_operation(histIndex);
        if (this.listener != null) {
            listener.txt_changer(text);
        }
    }
    public void HistoryClear() {
        history.clear();
        histIndex = 0;
        save_hist();
    }
    // TODO Auto-generated method stub
    public String[] result_array() {
        return (history.result_array());
    }
    public String getTxt() {
        return text;
    }
}

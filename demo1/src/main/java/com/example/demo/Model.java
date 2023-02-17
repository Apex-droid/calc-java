package com.example.demo;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Formatter;
import java.util.Objects;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class Model extends expression {

    //private static expression expr;
    private String text = "";
    
    private History history = new History();
    private int histIndex = history.size() - 1;
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

    public void rebuild_ex() {
        rebuild_ex(text);
    }
    public void edit(String new_txt) {                                                            
    	if (text.equals("input error "))
    		text = "";
        if (new_txt.equals("<-")){
        	if (!text.equals("") && text.charAt(text.length()- 1) != ' ')
        		text = StringUtils.chop(text);
        }
        else {
        	if (new_txt.matches(siNco))
        		text += new_txt + "(";
        	else 
        		text += new_txt;
        	
        }
        if (this.listener != null)
            listener.txt_changer(text);
    }
    public void enter()  {
        NumberFormat nf = new DecimalFormat ("0.######");
        if (inputChecker.Check(text))
        {
        	if(half_done) {
        		parse_half(text);
        		history.add_op(text);
        		text = nf.format(calc());
        		history.add_res(text);
        		half_done = false;
        		save_hist();
        	}
        	else {
        		rebuild_ex(text);
                if(!half_done) {
                	history.add_op(text);
                	reverse_stack();
                	text = nf.format(calc());
                	history.add_res(text);
                	save_hist();
                }
                else
                    text = "enter x: ";
        	}
        }
        else
        	text = "input error ";
        listener.txt_changer(text);
    }
    public double y_graf_func(double x) {
        rebuild_ex(text);
        get_X(x);
        return (calc());
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
    
    private void takeHistoryUp() {
    	
    	text = history.get_operation(histIndex);
    	histIndex--;
    	if (histIndex < 0)
    		histIndex = history.size();
    	if (this.listener != null)
            listener.txt_changer(text);
    }
 private void takeHistoryDown() {
    	
    	text = history.get_operation(histIndex);
    	histIndex++;
    	if (histIndex == history.size())
    		histIndex = 0;
    	if (this.listener != null)
            listener.txt_changer(text);
    }

}

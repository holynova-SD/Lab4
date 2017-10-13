/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 *
 * @author qiusuo
 */
public class searchWordFromInternet {
    String solution (String word){
        String resultString="";
        Process process = null;
        BufferedReader bufrIn = null;

        try {
            String cmd = "C:\\Program Files\\Anaconda3\\python.exe  searchWordPro.py "+word;
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            if(bufrIn!=null)
                resultString=bufrIn.readLine();
            
        } catch (Exception e) {
        }
        
        
        if(resultString.length()>0)
            return resultString;
        else return  word;
        
    }
}

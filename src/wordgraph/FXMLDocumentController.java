/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;

import java.io.File;
import javax.imageio.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javafx.event.EventType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author qiusuo
 */
public class FXMLDocumentController implements Initializable {
    static Map<String, node> nodes = new TreeMap<String, node>();
    final String pathString = "C:\\Users\\qiusuo\\Documents\\NetBeansProjects\\WordGraph\\picture.gif";
    boolean firstwalk=true;
    boolean onlinetag=false;
    @FXML
    private TextArea textarea;
    @FXML
    private Button generate;
    @FXML
    private Button join;
    @FXML
    private TextField filepath;
    @FXML
    private TextField word1;
    @FXML
    private TextField word2;
    @FXML
    private Button search;
    @FXML
    private Button randomwalk;
    @FXML
    private Button distance;
    @FXML
    private ImageView imgaarea;
    @FXML
    private TextArea inforarea;
    @FXML
    private Button stopwalk;

    MyThread myThread=new MyThread();
    @FXML
    private Button offline;
    @FXML
    private Button online;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == generate) {
            String textString = filepath.getText();
            try {
                nodes.clear();
                System.out.println(textString);
                createDirectedGraph(textString);

                showDirectedGraph(nodes);

                //paint mypaint= new paint();
                //mypaint.paint(pathString);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));
                ///String cmd = " C:\\Program Files (x86)\\ACD Systems\\ACDSee\\GFMF\\ACDSeeGFMF.exe  C:\\Users\\qiusuo\\Documents\\NetBeansProjects\\WordGraph\\picture.gif";
                //Runtime.getRuntime().exec(cmd).waitFor();

            } catch (Exception e) {
            }
            //System.out.println(textString);
        }
        if (event.getSource() == join) {
            String textString = textarea.getText();

            inforarea.setText(generateNewText(nodes, textString));
        }
        if (event.getSource() == search) {
            String word_1 = word1.getText();
            String word_2 = word2.getText();
            if (word_2.length() > 0 && word_1.length() > 0) {

                Vector<String> bridgeStrings = queryBridgeWords(nodes, word_1, word_2);
                String toshowString = "The bridge word: ";
                for (String wordString : bridgeStrings) {
                    toshowString += (wordString + "  ");
                }
                if (bridgeStrings.size() > 0) {
                    inforarea.setText(toshowString);
                    try {
                        MarkPoint markPoint = new MarkPoint();
                        markPoint.solution(bridgeStrings, nodes);
                        imgaarea.setImage(new Image(new FileInputStream(pathString)));
                    } catch (Exception e) {
                    }

                } else {
                    inforarea.setText("No bridge words from word1 to word2!");
                }

            } else {
                inforarea.setText("No word1 or word2 in the graph!");
            }
        }
        if (event.getSource() == distance) {
            String word_1 = word1.getText();
            String word_2 = word2.getText();
            if (word_1.length() > 0 && word_2.length() > 0) {
                Vector<String> resultStrings = calcShortestPath(nodes, word_1, word_2);

                String toshowString = "";
                for (int j = 0; j < resultStrings.size(); j++) {
                    toshowString += (resultStrings.elementAt(j) + " \n");
                }
                
                inforarea.setText(toshowString);
                try {
                    RebuildGraph rebuildGraph = new RebuildGraph();
                    Vector<Vector<String>> result = new Vector<Vector<String>>();
                    for(String sen:resultStrings){
                         Vector<String>tempStrings=new Vector<String>();
                         for(String word:sen.split(" "))
                             if(word.length()>0)
                                 tempStrings.add(0,word);
                         result.add(tempStrings);
                    }
                   
                    rebuildGraph.solution(result, nodes);
                    imgaarea.setImage(new Image(new FileInputStream(pathString)));
                } catch (Exception e) {
                }
            } else if (word_1.length() > 0) {

                String toshowString = "";
                for (String wordString : nodes.keySet()) {
                    if (!wordString.equals(word_1)) {
                        toshowString += (calcShortestPath(nodes, word_1, wordString) + "\n");
                    }
                }
                inforarea.setText(toshowString);
            } else {
                inforarea.setText("No input word!");
            }

        }
        if(event.getSource()==offline){
            System.out.println("offline");
            onlinetag=false;
            System.out.println(onlinetag);
        }
        if(event.getSource()==online){
            System.out.println(online);
            onlinetag=true;
            System.out.println(onlinetag);
        }
        if (event.getSource() == randomwalk) {
//            String trace=randomWalk(nodes);
//            Vector<String> traceString = new Vector<String>();
//            for(String wordString:trace.split(" ")){
//                if(wordString.length()>0)
//                    traceString.add(wordString);
//            }
//            
//            String toshowString = "";
//            for (String wordString : traceString) {
//                toshowString += wordString + " ";
//            }
//            Vector<Vector<String>> result = new Vector<Vector<String>>();
//            Vector<String> traceStringreverse = new Vector<>();
//            for (int i = traceString.size() - 1; i >= 0; i--) {
//                traceStringreverse.add(traceString.elementAt(i));
//            }
//            try {
//                result.add(traceStringreverse);
//                RebuildGraph rebuildGraph = new RebuildGraph();
//                rebuildGraph.solution(result, nodes);
//                imgaarea.setImage(new Image(new FileInputStream(pathString)));
//            } catch (Exception e) {
//            }
//            inforarea.setText(toshowString);
            myThread.MyThread_set(imgaarea, pathString, inforarea, nodes);
            //myThread.set_start();
            if(firstwalk)
                myThread.start();
            myThread.resume();
            firstwalk=false;
        }
        if(event.getSource()==stopwalk){
            //myThread.set_stop();
            myThread.suspend();
            System.out.println("wordgraph.FXMLDocumentController.handleButtonAction()");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgaarea.setFitWidth(1000);
        imgaarea.setFitHeight(780);
    }

    public void createDirectedGraph(String filename) throws Exception {

        File file = new File(filename);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        Vector<String> reads = new Vector<String>();
        searchWordFromInternet seach=new searchWordFromInternet();
        while (temp != null) {
            reads.add(temp);
            temp = br.readLine();
        }
        br.close();

        for (int i = 0; i < reads.size(); i++) {
            String line = "";
            for (int j = 0; j < reads.elementAt(i).length(); j++) {
                if (Character.isLetter(reads.elementAt(i).charAt(j))) {
                    line += reads.elementAt(i).charAt(j);
                } else {
                    line += ' ';
                }
            }
            reads.setElementAt(line, i);
        }

        Vector<Vector<String>> data = new Vector<Vector<String>>();

        Vector<String> sentence = new Vector<String>();
        for (String oneline : reads) {
            for (String word : oneline.split(" ")) {

                if(onlinetag){
                    System.out.println("online");
                    if (word.length() >= 1) {

                    sentence.add(seach.solution(word.toLowerCase()));
                    }
                }
                else{
                    if (word.length() > 3) {
                    if (word.charAt(word.length() - 1) == 's' && word.charAt(word.length() - 2) == 'e' && word.charAt(word.length() - 3) == 'i') {
                        StringBuilder newword = new StringBuilder();
                        for (int i = 0; i < word.length() - 3; i++) {
                            newword.append(word.charAt(i));
                        }
                        newword.append('y');
                        word = newword.toString();
                    }
                    if (word.charAt(word.length() - 1) == 'd' && word.charAt(word.length() - 2) == 'e' && word.charAt(word.length() - 3) == 'i') {
                        StringBuilder newword = new StringBuilder();
                        for (int i = 0; i < word.length() - 3; i++) {
                            newword.append(word.charAt(i));
                        }
                        newword.append('y');
                        word = newword.toString();
                    }
                    if (word.charAt(word.length() - 1) == 'g' && word.charAt(word.length() - 2) == 'n' && word.charAt(word.length() - 3) == 'i') {
                        StringBuilder newword = new StringBuilder();
                        for (int i = 0; i < word.length() - 3; i++) {
                            newword.append(word.charAt(i));
                        }
                        word = newword.toString();
                        }
                    }
                    
                    if (word.length() >= 1) {

                    sentence.add(word.toLowerCase());
                    }
                    
                    
                }

            }
        }
        if (sentence.size() >= 1) {
            data.add(sentence);
        }

        for (Vector<String> line : data) {
            node cur = null;
            if (nodes.containsKey(line.elementAt(0))) {
                cur = nodes.get(line.elementAt(0));
            } else {
                cur = new node(line.elementAt(0));
                nodes.put(line.elementAt(0), cur);
            }
            if (line.size() > 1) {
                for (int i = 1; i < line.size(); i++) {
                    if (cur.child.containsKey(line.elementAt(i))) {
                        cur.child.put(line.elementAt(i), cur.child.get(line.elementAt(i)) + 1);
                        cur = nodes.get(line.elementAt(i));
                    } else {
                        if (nodes.containsKey(line.elementAt(i))) {
                            cur.child.put(line.elementAt(i), 1);
                            cur = nodes.get(line.elementAt(i));
                        } else {
                            node n = new node(line.elementAt(i));
                            nodes.put(line.elementAt(i), n);
                            cur.child.put(line.elementAt(i), 1);
                            cur = n;
                        }
                    }
                }
            }
        }
    }

    void showDirectedGraph(Map<String, node> nodes) throws IOException, InterruptedException {

        Set<String> visit = new TreeSet<String>();

        StringBuilder content = new StringBuilder();
        content.append("digraph graphname{ \n");
        Stack<String> stk = new Stack<String>();
        for (String word : nodes.keySet()) {
            if (nodes.get(word).child.size() == 0) {
                content.append(word + "; \n");
            }
            if (visit.contains(word)) {
                //System.out.println(word);
                continue;
            } else {
                stk.push(word);
                String temp = "";
                while (stk.size() >= 1) {
                    temp = stk.pop();
                    visit.add(temp);
                    //System.out.println(temp);
//					DataInputStream in=new DataInputStream(System.in);
//					char ch = in.readChar();
                    for (String son : nodes.get(temp).child.keySet()) {
                        System.out.println(temp + "->" + son + ";");
                        content.append(temp + "->" + son + "[ label = " + nodes.get(temp).child.get(son) + " ]; \n");
                        if (!visit.contains(son)) {
                            stk.push(son);
                        }
                    }
                }
            }
        }
        content.append("}\n");

        try {
            String cont = content.toString();
            File file = new File("dotfile");

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(cont);
            bufferWritter.close();
            System.out.println("Done");
        } catch (IOException e) {
        }

        try {
            String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe  -Tgif dotfile -o picture.gif";
            Runtime.getRuntime().exec(cmd).waitFor();

        } catch (IOException e) {
        }
    }

    Vector<String> queryBridgeWords(Map<String, node> nodes, String word1, String word2) {
        Vector<String> result = new Vector<String>();
        if (!nodes.containsKey(word1) || !nodes.containsKey(word2)) {
            return result;
        }
        for (String son : nodes.get(word1).child.keySet()) {
            if (nodes.get(son).child.containsKey(word2)) {
                result.add(son);
            }
        }

        return result;
    }

    String generateNewText(Map<String, node> nodes, String inputText) {
        StringBuilder builder = new StringBuilder();
        String result = "";
        
        for (int i = 0; i < inputText.length(); i++) {
            if (Character.isLetter(inputText.charAt(i))) {
                builder.append(inputText.charAt(i));
            } else {
                builder.append(" ");
            }
        }
        Vector<String> temp = new Vector<String>();
        searchWordFromInternet seach=new searchWordFromInternet();
        for (String word : builder.toString().split(" ")) {
            if (word.length() >= 1) {
                if(onlinetag==true){
                    temp.add(seach.solution(word.toLowerCase()));
                }
                else temp.add(word.toLowerCase());
            }
        }
        StringBuilder result_builder = new StringBuilder();

        Vector<String> bridge = null;
        boolean first_tag = true;
        for (int i = 0; i < temp.size() - 1; i++) {
            if (first_tag) {
                result_builder.append(" " + temp.elementAt(i));
                first_tag = false;
            }
            bridge=queryBridgeWords(nodes,temp.elementAt(i),temp.elementAt(i+1));
            if ((bridge).size() > 0) {
                result_builder.append(" " + bridge.elementAt(0));
            }
            result_builder.append(" " + temp.elementAt(i + 1));
        }
        result = result_builder.toString();
        return result;
    }

    Vector<String> calcShortestPath(Map<String, node> nodes, String word1, String word2) {
        Map<String, Vector<String>> path = ShortestPath(nodes, word1);
        Vector<Vector<String>> result = new Vector<Vector<String>>();

        Stack<String> stk = new Stack<String>();
        String cur = word2;
        stk.push(cur);
        Stack<Vector<String>> stkv = new Stack<Vector<String>>();
        Vector<String> temp = new Vector<String>();
        while (stk.size() > 0) {
            cur = stk.pop();
            temp.add(cur);
            //System.out.println(cur);
            for (int i = 0; i < path.get(cur).size(); i++) {
                if (path.get(cur).elementAt(i) == word1) {
                    temp.add(word1);
                    result.add(temp);
                    if (stkv.size() >= 1) {
                        temp = stkv.pop();
                    }
                } else {
                    stk.push(path.get(cur).elementAt(i));
                    if (i >= 1) {
                        Vector<String> tt = new Vector<String>(temp);
                        stkv.push(tt);
                    }
                }
            }
        }
        Vector<String> reStrings = new Vector<String>();
        for (Vector<String> lineStrings : result) {
            StringBuilder tempBuilder = new StringBuilder();
            for(int i=lineStrings.size()-1;i>=0;i--)
                tempBuilder.append(lineStrings.elementAt(i)+" ");
            reStrings.add(tempBuilder.toString());
        }
        return reStrings;
    }

    Map<String, Vector<String>> ShortestPath(Map<String, node> nodes, String word1) {
        if (!nodes.containsKey(word1)) {
            return null;
        }
        Map<String, Integer> lenth = new TreeMap<String, Integer>();
        Set<String> visit = new TreeSet<String>();
        Map<String, Vector<String>> path = new TreeMap<String, Vector<String>>();

        Stack<String> stk = new Stack<String>();
        stk.push(word1);
        visit.add(word1);
        lenth.put(word1, 0);
        String temp = "";
        while (stk.size() >= 1) {
            temp = stk.pop();

            for (String son : nodes.get(temp).child.keySet()) {
                if (!visit.contains(son)) {
                    stk.push(son);
                    Vector<String> value = new Vector<String>();
                    value.add(temp);
                    path.put(son, value);
                    lenth.put(son, lenth.get(temp) + nodes.get(temp).child.get(son));
                    visit.add(son);
                } else {
                    if (lenth.get(temp) + nodes.get(temp).child.get(son) < lenth.get(son)) {
                        Vector<String> value = path.get(son);
                        value.clear();
                        value.add(temp);
                        //if(stk.contains(son)) stk.remove(son);
                        stk.push(son);
                        lenth.put(son, lenth.get(temp) + nodes.get(temp).child.get(son));
                        path.put(son, value);
                    } else if (lenth.get(temp) + nodes.get(temp).child.get(son) == lenth.get(son)) {
                        Vector<String> value = path.get(son);
                        value.add(temp);
                        path.put(son, value);
                    }
                }

            }
        }
        return path;
    }

    String randomWalk(Map<String, node> nodes) {
        Set<String> visit = new TreeSet<String>();
        Random r1 = new Random();
        int pos = (int) (Math.abs(r1.nextInt()) % nodes.size());
        String cur = null;
        int i = 0;
        for (String key : nodes.keySet()) {
            if (i == pos) {
                cur = key;
                break;
            }
            i++;
        }
        Vector<String> result = new Vector<String>();
        result.add(cur);
        while (true) {
            if (nodes.get(cur).child.size() == 0) {
                break;
            }
            int size = nodes.get(cur).child.keySet().size();
            pos = (int) (Math.abs(r1.nextInt()) % size);
            i = 0;
            String next = "";
            for (String key : nodes.get(cur).child.keySet()) {
                if (i == pos) {
                    next = key;
                    break;
                }
                i++;
            }
            String edge = cur + " " + next;
            if (!visit.contains(edge)) {
                visit.add(edge);
                System.out.println(edge);
            } else {
                break;
            }
            cur = next;
            result.add(next);
        }
        StringBuilder tempBuilder = new StringBuilder();
        for (String word : result) {
            tempBuilder.append(word + " ");
        }
        return tempBuilder.toString();
    }

   
}

class MyThread extends Thread {
ImageView imgaarea=null;
String pathString = "";
TextArea inforarea=null;
Map<String, node> nodes=null;
public volatile boolean exit = true;
    
   void MyThread_set(ImageView imagearea,String pathString,TextArea textArea,Map<String, node> nodes){
        this.imgaarea=imagearea;
        this.inforarea=textArea;
        this.pathString=pathString;
        this.nodes=nodes;
    }

    public MyThread() {
    }
    public void set_stop () {
        this.exit=false;
    }
    public void set_start () {
        this.exit=true;
    }
    @Override
   public void run() {
        while(exit){
            String trace=randomWalk(nodes);
            Vector<String> traceString = new Vector<String>();
            for(String wordString:trace.split(" ")){
                if(wordString.length()>0)
                    traceString.add(wordString);
            }
            
            String toshowString = "";
            for (String wordString : traceString) {
                toshowString += wordString + " ";
            }
            Vector<Vector<String>> result = new Vector<Vector<String>>();
            Vector<String> traceStringreverse = new Vector<>();
            for (int i = traceString.size() - 1; i >= 0; i--) {
                traceStringreverse.add(traceString.elementAt(i));
            }
            try {
                result.add(traceStringreverse);
                RebuildGraph rebuildGraph = new RebuildGraph();
                rebuildGraph.solution(result, nodes);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));
            } catch (Exception e) {
            }
            inforarea.setText(toshowString);
            
            System.out.println("wordgraph.MyThread.run()");
            try{
                    sleep(1000);
                    }
            catch(Exception e){
                
            }
      }
    }
   
   String randomWalk(Map<String, node> nodes) {
        Set<String> visit = new TreeSet<String>();
        Random r1 = new Random();
        int pos = (int) (Math.abs(r1.nextInt()) % nodes.size());
        String cur = null;
        int i = 0;
        for (String key : nodes.keySet()) {
            if (i == pos) {
                cur = key;
                break;
            }
            i++;
        }
        Vector<String> result = new Vector<String>();
        result.add(cur);
         
        while (true) {
            if (nodes.get(cur).child.size() == 0) {
                break;
            }
            int size = nodes.get(cur).child.keySet().size();
            pos = (int) (Math.abs(r1.nextInt()) % size);
            i = 0;
            String next = "";
            for (String key : nodes.get(cur).child.keySet()) {
                if (i == pos) {
                    next = key;
                    break;
                }
                i++;
            }
            String edge = cur + " " + next;
            if (!visit.contains(edge)) {
                visit.add(edge);
              //  System.out.println(edge);
            } else {
                break;
            }
            cur = next;
            result.add(next);
        }
        StringBuilder tempBuilder = new StringBuilder();
        for (String word : result) {
            tempBuilder.append(word + " ");
        }
        return tempBuilder.toString();
    }

}
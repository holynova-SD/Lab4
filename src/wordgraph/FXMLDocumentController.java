package wordgraph;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javafx.scene.image.Image;
/**
 * @author qiusuo
 */
public class FXMLDocumentController implements Initializable {
    /** */
    private static Map<String, Node> nodes = new TreeMap<String, Node>();
    /** */
    private final String pathString = "/Users/apple/git/Lab4/picture.gif";
    //final String pathString =
    //"C:\\Users\\86345\\Documents\\NetBeansProjects" +
    //"\\WordGraph_last\\WordGraph\\picture.gif";
    /** */
    private boolean firstwalk = true;
    /** */
    private boolean onlinetag;
    /** */
    @FXML
    private TextArea textarea;
    /** */
    @FXML
    private Button generate;
    /** */
    @FXML
    private Button join;
    /** */
    @FXML
    private TextField filepath;
    /** */
    @FXML
    private TextField word1;
    /** */
    @FXML
    private TextField word2;
    /** */
    @FXML
    private Button search;
    /** */
    @FXML
    private Button randomwalk;
    /** */
    @FXML
    private Button distance;
    /** */
    @FXML
    private ImageView imgaarea;
    /** */
    @FXML
    private TextArea inforarea;
    /** */
    @FXML
    private Button stopwalk;
    /** */
    private MyThread myThread = new MyThread();
    /** */
    @FXML
    private Button offline;
    /** */
    @FXML
    private Button online;
    /** */
    public static final Integer THREE = 3;
    /** */
    public static final Integer WIDTH = 1000;
    /** */
    public static final Integer HEIGHT = 780;
    /**
     * .
     * @return .
     */
    public boolean isOnlinetag() {
        return this.onlinetag;
    }
    /**
     * .
     * @param tag ;
     */
    public void setOnlinetag(final boolean tag) {
        this.onlinetag = tag;
    }
    /**
     * .
     * @return .
     */
    public TextArea getTextarea() {
        return this.textarea;
    }
    /**
     * .
     * @param area ;
     */
    public void setTextarea(final TextArea area) {
        this.textarea = area;
    }
    /**
     * .
     * @return .
     */
    public Button getGenerate() {
        return this.generate;
    }
    /**
     * .
     * @param paraGenerate ;
     */
    public void setGenerate(final Button paraGenerate) {
        this.generate = paraGenerate;
    }
    /**
     * .
     * @return .
     */
    public Button getJoin() {
        return this.join;
    }
    /**
     * .
     * @param paraJoin ;
     */
    public void setJoin(final Button paraJoin) {
        this.generate = paraJoin;
    }
    /**
     *
     * @param event ;
     */
    @FXML
    private void handleButtonAction(final ActionEvent event) {
        onlinetag = false;
        if (event.getSource() == generate) {
            final String textString = filepath.getText();
            try {
                nodes.clear();
                createDirectedGraph(textString);
                showDirectedGraph(nodes);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));
            } catch (Exception e) {
            }
            // System.out.println(textString);
        }
        if (event.getSource() == join) {
            final String textString = textarea.getText();

            inforarea.setText(generateNewText(nodes, textString));
        }
        if (event.getSource() == search) {
            final String wordOne = word1.getText();
            final String wordTwo = word2.getText();
            if (wordTwo.length() > 0 && wordOne.length() > 0) {

                final Vector<String> bridgeStrings =
                    queryBridgeWords(nodes, wordOne, wordTwo);
                String toshowString = "The bridge word: ";
                for (final String wordString : bridgeStrings) {
                    toshowString += (wordString + "  ");
                }
                if (bridgeStrings.isEmpty()) {
                  inforarea.setText("No bridge words from word1 to word2!");
                } else {
                  inforarea.setText(toshowString);
                  try {
                      final MarkPoint markPoint = new MarkPoint();
                      markPoint.solution(bridgeStrings, nodes);
                      imgaarea.setImage(new Image(new
                          FileInputStream(pathString)));
                  } catch (Exception e) {
                  }
                }
            } else {
                inforarea.setText("No word1 or word2 in the graph!");
            }
        }
        if (event.getSource() == distance) {
            final String wordOne = word1.getText();
            final String wordTwo = word2.getText();
            if (wordOne.length() > 0 && wordTwo.length() > 0) {
                final Vector<String> resultStrings =
                    calcShortestPath(nodes, wordOne, wordTwo);

                String toshowString = "";
                for (int j = 0; j < resultStrings.size(); j++) {
                    toshowString += (resultStrings.elementAt(j) + " \n");
                }

                inforarea.setText(toshowString);
                try {
                    final RebuildGraph rebuildGraph = new RebuildGraph();
                    final Vector<Vector<String>> result =
                        new Vector<Vector<String>>();
                    final Vector<String> tempStrings = new Vector<String>();
                    for (final String sen : resultStrings) {
                        tempStrings.clear();
                        for (final String word : sen.split(" ")) {
                            if (word.length() > 0) {
                                tempStrings.add(0, word);
                            }
                        }
                        result.add(tempStrings);
                    }

                    rebuildGraph.solution(result, nodes);
                    imgaarea.setImage(new Image(new
                        FileInputStream(pathString)));
                } catch (Exception e) {
                }
            } else if (wordOne.length() > 0) {

                String toshowString = "";
                for (final String wordString : nodes.keySet()) {
                    if (!wordString.equals(wordOne)) {
                        toshowString += (calcShortestPath(nodes,
                            wordOne, wordString) + "\n");
                    }
                }
                inforarea.setText(toshowString);
            } else {
                inforarea.setText("No input word!");
            }

        }
        if (event.getSource() == offline) {
            onlinetag = false;
        }
        if (event.getSource() == online) {
            onlinetag = true;
        }
        if (event.getSource() == randomwalk) {
            // String trace=randomWalk(nodes);
            // Vector<String> traceString = new Vector<String>();
            // for(String wordString:trace.split(" ")){
            // if(wordString.length()>0)
            // traceString.add(wordString);
            // }
            //
            // String toshowString = "";
            // for (String wordString : traceString) {
            // toshowString += wordString + " ";
            // }
            // Vector<Vector<String>> result = new Vector<Vector<String>>();
            // Vector<String> traceStringreverse = new Vector<>();
            // for (int i = traceString.size() - 1; i >= 0; i--) {
            // traceStringreverse.add(traceString.elementAt(i));
            // }
            // try {
            // result.add(traceStringreverse);
            // RebuildGraph rebuildGraph = new RebuildGraph();
            // rebuildGraph.solution(result, nodes);
            // imgaarea.setImage(new Image(new FileInputStream(pathString)));
            // } catch (Exception e) {
            // }
            // inforarea.setText(toshowString);
            myThread.myThreadSet(imgaarea, pathString, inforarea, nodes);
            // myThread.setStart();
            if (firstwalk) {
                myThread.start();
            }
            myThread.resume();
            firstwalk = false;
        }
        if (event.getSource() == stopwalk) {
            // myThread.setStop();
            myThread.suspend();
        }
    }
    /**
     *
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO
        imgaarea.setFitWidth(WIDTH);
        imgaarea.setFitHeight(HEIGHT);
    }
    /**
     *
     * @param filename filename
     * @throws Exception Exception
     */
    public void createDirectedGraph(final String filename) throws Exception {

        final File file = new File(filename);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        final BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        final Vector<String> reads = new Vector<String>();
        final SearchWordFromInternet seach = new SearchWordFromInternet();
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

        final Vector<Vector<String>> data = new Vector<Vector<String>>();

        final Vector<String> sentence = new Vector<String>();
        for (final String oneline : reads) {
            for (String word : oneline.split(" ")) {

                if (onlinetag) {
                    if (word.length() >= 1) {

                        sentence.add(seach.solution(word.toLowerCase(
                            Locale.ENGLISH)));
                    }
                } else {
                    if (word.length() > THREE) {
                        if (word.charAt(word.length() - 1) == 's'
                            && word.charAt(word.length() - 2) == 'e'
                                && word.charAt(word.length() - THREE) == 'i') {
                            final StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - THREE; i++) {
                                newword.append(word.charAt(i));
                            }
                            newword.append('y');
                            word = newword.toString();
                        }
                        if (word.charAt(word.length() - 1) == 'd'
                            && word.charAt(word.length() - 2) == 'e'
                                && word.charAt(word.length() - THREE) == 'i') {
                            final StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - THREE; i++) {
                                newword.append(word.charAt(i));
                            }
                            newword.append('y');
                            word = newword.toString();
                        }
                        if (word.charAt(word.length() - 1) == 'g'
                            && word.charAt(word.length() - 2) == 'n'
                                && word.charAt(word.length() - THREE) == 'i') {
                            final StringBuilder newword = new StringBuilder();
                            for (int i = 0; i < word.length() - THREE; i++) {
                                newword.append(word.charAt(i));
                            }
                            word = newword.toString();
                        }
                    }

                    if (word.length() >= 1) {

                        sentence.add(word.toLowerCase(Locale.ENGLISH));
                    }

                }

            }
        }
        if (!sentence.isEmpty()) {
            data.add(sentence);
        }

        for (final Vector<String> line : data) {
            Node cur = null;
            if (nodes.containsKey(line.elementAt(0))) {
                cur = nodes.get(line.elementAt(0));
            } else {
                cur = new Node(line.elementAt(0));
                nodes.put(line.elementAt(0), cur);
            }
            if (line.size() > 1) {
                for (int i = 1; i < line.size(); i++) {
                    if (cur.getChild().containsKey(line.elementAt(i))) {
                        cur.getChild().put(line.elementAt(i),
                            cur.getChild().get(line.elementAt(i)) + 1);
                        cur = nodes.get(line.elementAt(i));
                    } else {
                        if (nodes.containsKey(line.elementAt(i))) {
                            cur.getChild().put(line.elementAt(i), 1);
                            cur = nodes.get(line.elementAt(i));
                        } else {
                            final Node n = new Node(line.elementAt(i));
                            nodes.put(line.elementAt(i), n);
                            cur.getChild().put(line.elementAt(i), 1);
                            cur = n;
                        }
                    }
                }
            }
        }
    }
    /**
     * ..
     * @param graphNodes ;
     * @throws IOException ;
     * @throws InterruptedException ;
     */
    final void showDirectedGraph(final Map<String, Node> graphNodes)
        throws IOException, InterruptedException {

        final Set<String> visit = new TreeSet<String>();

        final StringBuilder content = new StringBuilder();
        content.append("digraph graphname{ \n");
        final Stack<String> stk = new Stack<String>();
        for (final String word : graphNodes.keySet()) {
            if (graphNodes.get(word).getChild().size() == 0) {
                content.append(word + "; \n");
            }
            if (visit.contains(word)) {
                // System.out.println(word);
                continue;
            } else {
                stk.push(word);
                String temp = "";
                while (stk.size() >= 1) {
                    temp = stk.pop();
                    visit.add(temp);
                    // System.out.println(temp);
                    // DataInputStream in=new DataInputStream(System.in);
                    // char ch = in.readChar();
                    for (final String son : graphNodes.get(
                        temp).getChild().keySet()) {
                        content.append(temp + "->" + son + "[ label = "
                        + graphNodes.get(temp).getChild().get(son) + " ]; \n");
                        if (!visit.contains(son)) {
                            stk.push(son);
                        }
                    }
                }
            }
        }
        content.append("}\n");

        try {
            final String cont = content.toString();
            final File file = new File("dotfile");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            final FileWriter fileWritter =
                new FileWriter(file.getName(), false);
            final BufferedWriter bufferWritter =
                new BufferedWriter(fileWritter);
            bufferWritter.write(cont);
            bufferWritter.close();
        } catch (IOException e) {
        }

        try {
            final String cmd =
                "/usr/local/bin/dot -Tgif dotfile -o picture.gif";
            //String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe"
            //+ "-Tgif dotfile -o picture.gif";
            Runtime.getRuntime().exec(cmd).waitFor();

        } catch (IOException e) {

        }
    }
    /**
     * .
     * @param graphNodes ;
     * @param wordFirst ;
     * @param wordSecond ;
     * @return .
     */
    final Vector<String> queryBridgeWords(final Map<String, Node> graphNodes,
        final String wordFirst, final String wordSecond) {
        final Vector<String> result = new Vector<String>();
        if (!graphNodes.containsKey(wordFirst)
            || !graphNodes.containsKey(wordSecond)) {
            return result;
        }
        for (final String son : graphNodes.get(wordFirst).getChild().keySet()) {
            if (graphNodes.get(son).getChild().containsKey(wordSecond)) {
                result.add(son);
            }
        }

        return result;
    }
    /**
     * .
     * @param graphNodes ;
     * @param inputText ;
     * @return .
     */
    final String generateNewText(final Map<String, Node> graphNodes,
        final String inputText) {
        final StringBuilder builder = new StringBuilder();
        String result = "";

        for (int i = 0; i < inputText.length(); i++) {
            if (Character.isLetter(inputText.charAt(i))) {
                builder.append(inputText.charAt(i));
            } else {
                builder.append(" ");
            }
        }
        final Vector<String> temp = new Vector<String>();
        final SearchWordFromInternet seach = new SearchWordFromInternet();
        for (final String word : builder.toString().split(" ")) {
            if (word.length() >= 1) {
                if (onlinetag) {
                    temp.add(seach.solution(word.toLowerCase(Locale.ENGLISH)));
                } else {
                    temp.add(word.toLowerCase(Locale.ENGLISH));
                }
            }
        }
        final StringBuilder resultBuilder = new StringBuilder();

        Vector<String> bridge = null;
        boolean firstTag = true;
        for (int i = 0; i < temp.size() - 1; i++) {
            if (firstTag) {
                resultBuilder.append(" " + temp.elementAt(i));
                firstTag = false;
            }
            bridge = queryBridgeWords(graphNodes, temp.elementAt(i),
                temp.elementAt(i + 1));
            if ((bridge).size() > 0) {
                resultBuilder.append(" " + bridge.elementAt(0));
            }
            resultBuilder.append(" " + temp.elementAt(i + 1));
        }
        result = resultBuilder.toString();
        return result;
    }
    /**
     * .
     * @param graphNodes ;
     * @param wordFirst ;
     * @param wordSecond ;
     * @return .
     */
    final Vector<String> calcShortestPath(final Map<String, Node> graphNodes,
        final String wordFirst, final String wordSecond) {
        final Map<String, Vector<String>> path =
            shortestPath(graphNodes, wordFirst);
        final Vector<Vector<String>> result = new Vector<Vector<String>>();

        final Stack<String> stk = new Stack<String>();
        String cur = wordSecond;
        stk.push(cur);
        final Stack<Vector<String>> stkv = new Stack<Vector<String>>();
        Vector<String> temp = new Vector<String>();
        while (stk.size() > 0) {
            cur = stk.pop();
            temp.add(cur);
            // System.out.println(cur);
            for (int i = 0; i < path.get(cur).size(); i++) {
                if (path.get(cur).elementAt(i) == wordFirst) {
                    temp.add(wordFirst);
                    result.add(temp);
                    if (stkv.size() >= 1) {
                        temp = stkv.pop();
                    }
                } else {
                    stk.push(path.get(cur).elementAt(i));
                    if (i >= 1) {
                        final Vector<String> tt = new Vector<String>(temp);
                        stkv.push(tt);
                    }
                }
            }
        }
        final Vector<String> reStrings = new Vector<String>();
        for (final Vector<String> lineStrings : result) {
            final StringBuilder tempBuilder = new StringBuilder();
            for (int i = lineStrings.size() - 1; i >= 0; i--) {
                tempBuilder.append(lineStrings.elementAt(i) + " ");
            }
            reStrings.add(tempBuilder.toString());
        }
        return reStrings;
    }
    /**
     * .
     * @param graphNodes ,
     * @param wordFirst ,
     * @return .
     */
    final Map<String, Vector<String>> shortestPath(
        final Map<String, Node> graphNodes,
        final String wordFirst) {
        if (!graphNodes.containsKey(wordFirst)) {
            return null;
        }
        final Map<String, Integer> lenth = new TreeMap<String, Integer>();
        final Set<String> visit = new TreeSet<String>();
        final Map<String, Vector<String>> path =
            new TreeMap<String, Vector<String>>();

        final Stack<String> stk = new Stack<String>();
        stk.push(wordFirst);
        visit.add(wordFirst);
        lenth.put(wordFirst, 0);
        String temp = "";
        while (stk.size() >= 1) {
            temp = stk.pop();

            for (final String son : graphNodes.get(temp).getChild().keySet()) {
                if (visit.contains(son)) {
                  if (lenth.get(temp) + graphNodes.get(
                      temp).getChild().get(son) < lenth.get(son)) {
                      final Vector<String> value = path.get(son);
                      value.clear();
                      value.add(temp);
                      // if(stk.contains(son)) stk.remove(son);
                      stk.push(son);
                      lenth.put(son, lenth.get(temp)
                          + graphNodes.get(temp).getChild().get(son));
                      path.put(son, value);
                  } else if (lenth.get(temp)
                      + graphNodes.get(temp).getChild().get(son)
                      == lenth.get(son)) {
                      final Vector<String> value = path.get(son);
                      value.add(temp);
                      path.put(son, value);
                  }
                } else {
                  stk.push(son);
                  final Vector<String> value = new Vector<String>();
                  value.add(temp);
                  path.put(son, value);
                  lenth.put(son, lenth.get(temp)
                      + graphNodes.get(temp).getChild().get(son));
                  visit.add(son);
                }

            }
        }
        return path;
    }
    /**
     * .
     * @param graphNodes ;
     * @return .
     */
    final String randomWalk(final Map<String, Node> graphNodes) {
        final Set<String> visit = new TreeSet<String>();
        final Random ramdomOne = new Random();
        int pos = ramdomOne.nextInt(graphNodes.size());
        //int pos = (int) (Math.abs(ramdomOne.nextInt()) % graphNodes.size());
        String cur = null;
        int i = 0;
        for (final String key : graphNodes.keySet()) {
            if (i == pos) {
                cur = key;
                break;
            }
            i++;
        }
        final Vector<String> result = new Vector<String>();
        result.add(cur);
        while (true) {
            if (graphNodes.get(cur).getChild().size() == 0) {
                break;
            }
            final int size = graphNodes.get(cur).getChild().keySet().size();
            pos = Math.abs(ramdomOne.nextInt(size));
            //pos = (int) (Math.abs(ramdomOne.nextInt()) % size);
            i = 0;
            String next = "";
            for (final String key : graphNodes.get(cur).getChild().keySet()) {
                if (i == pos) {
                    next = key;
                    break;
                }
                i++;
            }
            final String edge = cur + " " + next;
            if (visit.contains(edge)) {
                break;
            } else {
                visit.add(edge);
            }
            cur = next;
            result.add(next);
        }
        final StringBuilder tempBuilder = new StringBuilder();
        for (final String word : result) {
            tempBuilder.append(word + " ");
        }
        return tempBuilder.toString();
    }

}

/**
 * .
 * @author HolynovaSD
 *.
 */
class MyThread extends Thread {
    /** */
    private ImageView imgaarea;
    /** */
    private String pathString;
    /** */
    private TextArea inforarea;
    /** */
    private Map<String, Node> nodes;
    /** */
    private boolean exit;
    /***/
    public static final Integer SLEEP_TIME = 1000;
    /**
     * .
     * @param imagearea ;
     * @param path ;
     * @param textArea ;
     * @param graphNodes ;
     */
    void myThreadSet(final ImageView imagearea, final String path,
        final TextArea textArea, final Map<String, Node> graphNodes) {
        this.imgaarea = imagearea;
        this.inforarea = textArea;
        this.pathString = path;
        this.nodes = graphNodes;
    }
    /**
     * .
     */
    MyThread() {
        this.imgaarea = new ImageView();
        this.pathString = "";
        this.inforarea = new TextArea();
        this.nodes = null;
        this.exit = true;
    }
    /**
     * .
     */
    public void setStop() {
        this.exit = false;
    }
    /**
     * .
     */
    public void setStart() {
        this.exit = true;
    }

    @Override
    public void run() {
        while (exit) {
            final String trace = randomWalk(nodes);
            final Vector<String> traceString = new Vector<String>();
            for (final String wordString : trace.split(" ")) {
                if (wordString.length() > 0) {
                    traceString.add(wordString);
                }
            }

            String toshowString = "";
            for (final String wordString : traceString) {
                toshowString += wordString + " ";
            }
            final Vector<Vector<String>> result = new Vector<Vector<String>>();
            final Vector<String> traceStringreverse = new Vector<>();
            for (int i = traceString.size() - 1; i >= 0; i--) {
                traceStringreverse.add(traceString.elementAt(i));
            }
            try {
                result.add(traceStringreverse);
                final RebuildGraph rebuildGraph = new RebuildGraph();
                rebuildGraph.solution(result, nodes);
                imgaarea.setImage(new Image(new FileInputStream(pathString)));
            } catch (Exception e) {
            }
            inforarea.setText(toshowString);
            try {
                sleep(SLEEP_TIME);
            } catch (Exception e) {
            }
        }
    }
    /**
     * .
     * @param graphNodes ;
     * @return .
     */
    String randomWalk(final Map<String, Node> graphNodes) {
        final Set<String> visit = new TreeSet<String>();
        final Random ramdomOne = new Random();
        int pos = Math.abs(ramdomOne.nextInt(graphNodes.size()));
        //int pos = (int) (Math.abs(ramdomOne.nextInt()) % graphNodes.size());
        String cur = null;
        int i = 0;
        for (final String key : graphNodes.keySet()) {
            if (i == pos) {
                cur = key;
                break;
            }
            i++;
        }
        final Vector<String> result = new Vector<String>();
        result.add(cur);

        while (true) {
            if (graphNodes.get(cur).getChild().size() == 0) {
                break;
            }
            final int size = graphNodes.get(cur).getChild().keySet().size();
            pos = Math.abs(ramdomOne.nextInt(size));
            //pos = (int) (Math.abs(ramdomOne.nextInt()) % size);
            i = 0;
            String next = "";
            for (final String key : graphNodes.get(cur).getChild().keySet()) {
                if (i == pos) {
                    next = key;
                    break;
                }
                i++;
            }
            final String edge = cur + " " + next;
            if (visit.contains(edge)) {
                break;
                // System.out.println(edge);
            } else {
                visit.add(edge);
            }
            cur = next;
            result.add(next);
        }
        final StringBuilder tempBuilder = new StringBuilder();
        for (final String word : result) {
            tempBuilder.append(word + " ");
        }
        return tempBuilder.toString();
    }

}

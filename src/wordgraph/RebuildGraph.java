package wordgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

/**
 * @author qiusuo
 */
public class RebuildGraph {
    /**
     * .
     */
    RebuildGraph() {
        //Nothing special is needed here.
    }
    /**
     * .
     * @param result ;
     * @param nodes ;
     * @throws Exception ;
     */
    final void solution(final Vector<Vector<String>> result,
        final Map<String, Node> nodes) throws Exception {
        final File file = new File("dotfile");
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        final BufferedReader br =
            new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        final Vector<String> reads = new Vector<String>();
        while (temp != null) {
            reads.add(temp + "\n");
            temp = br.readLine();
        }
        br.close();
        int i = 0;
        //System.out.println(nodes.get("To").getChild().size());
        final String[] colors = new String[]{"blue", "green", "purple",
            "yellow", "red", "pink", "palegoldenrod",
            "palegreen", "paleturquoise",
            "palevioletred", "pansy", "papayawhip", "peachpuff", "peru",
            "pink", "salmon", "camel", "amber", "khaki", "maroon", "green",
            "blue", "red", "scarlet", "mauve"};
        for (final Vector<String> path : result) {
            for (int j = path.size() - 1; j > 0; j--) {

                reads.insertElementAt("\n" + path.elementAt(j) + " -> "
                + path.elementAt(j - 1) + "[ label = "
                    + nodes.get(path.elementAt(j)).getChild().get(
                        path.elementAt(j - 1))
                    + ", color = " + colors[i] + " ] \n", reads.size() - 1);

            }
            i++;
        }

        final StringBuilder content = new StringBuilder();
        for (final String lineString : reads) {
            content.append(lineString);
        }
        try {
            final String cont = content.toString();
            final File fileout = new File("dotfilenew.dot");

            //if fileout doesnt exists, then create it
            if (!fileout.exists()) {
                fileout.createNewFile();
            }
            //true = append fileout
            final FileWriter fileWritter =
                new FileWriter(fileout.getName(), false);
            final BufferedWriter bufferWritter =
                new BufferedWriter(fileWritter);
            bufferWritter.write(cont);
            bufferWritter.close();
        } catch (IOException e) {
        }

        try {
            final String cmd = "/usr/local/bin/dot"
        + " -Tgif dotfilenew.dot -o picture.gif";
            //String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe"
            //+ " -Tgif dotfilenew -o picture.gif";
            Runtime.getRuntime().exec(cmd).waitFor();

        } catch (IOException e) {
        }
    }
}

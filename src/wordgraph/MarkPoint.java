package wordgraph;

import java.io.*;
import java.util.Map;
import java.util.Vector;
/**
 *
 * @author qiusuo
 */
public class MarkPoint {
    void solution( Vector<String> result,Map<String,node>  nodes) throws  Exception{
        File file=new File("dotfile");
	if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        temp=br.readLine();
        Vector<String> reads=new Vector<String>();
	while(temp!=null){
            reads.add(temp+"\n");
            temp=br.readLine();
	}
        br.close();
        int i=0;
        //System.out.println(nodes.get("To").child.size());
        String [] colors=new String[]{"blue","green","purple","yellow","red","pink","palegoldenrod","palegreen","paleturquoise","palevioletred","pansy","papayawhip","peachpuff","peru","pink","salmon","camel","amber","khaki","maroon","green","blue","red","scarlet","mauve"} ;
        for(String word:result){
          //System.out.println(nodes.get(path.elementAt(j)).child.get(path.elementAt(j-1)));
                reads.insertElementAt("\n"+word+"[ color = "+colors[i]+" ] \n", reads.size()-1);
           
        }
            
        StringBuilder content=new StringBuilder();
        for(String lineString:reads){
            content.append(lineString);
        }
          try{
			 String cont = content.toString();
			 File fileout =new File("dotfilenew");

			 //if fileout doesnt exists, then create it
			if(!fileout.exists()){
			fileout.createNewFile();
			}
			//true = append fileout
			 FileWriter fileWritter = new FileWriter(fileout.getName(),false);
			 BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			 bufferWritter.write(cont);
			 bufferWritter.close(); System.out.println("Done");
			 }
		 catch(IOException e){}
		 
		 try{
			 String cmd = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe  -Tgif dotfilenew -o picture.gif";
			 Runtime.getRuntime().exec(cmd).waitFor();
                        
			 }catch(IOException e){}     
    }
}

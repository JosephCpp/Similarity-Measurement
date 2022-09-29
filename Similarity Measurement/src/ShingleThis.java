import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;


public class ShingleThis 
{
    private String inFile;  //input file
    private ArrayList<String> wordsInFile;  //file converted to an array/list of Strings
    private Queue<String> q1;  //Queue to hold the final answer
    private int count = 0;   // count of number of words
    private int k;

    public ShingleThis(String fileName, int kgrams)
    {   
        inFile = fileName;
        k = kgrams;
        q1 = new Queue<String>(); //your k-grams will be stored here for your answer
        wordsInFile = new ArrayList<String>();  
    }

    public static void main(String[] args)
    {
        String str;
        BufferedReader in;
       // String inFile = args[0];
        String inFile = "data/greenEggs.txt";  //clintonStateOfTheUnion2000.txt
       // int kgrams = Integer.parseInt(args[1]);
        int kgrams = 2;

        ShingleThis work = new ShingleThis(inFile,kgrams);

        try{
            in = new BufferedReader( new FileReader((new File(FileSystems.getDefault().getPath(inFile).toString())))); //open file and start to read it.
            while( (str = in.readLine()) != null) { //read each line and save it to str. 
                StringTokenizer strT = new StringTokenizer(str,"@-,# .!"); //remove all the items @-,# .! from the str.

                while (strT.hasMoreTokens()) { //add the words to wordsInFile collection
                    String strAdd = strT.nextToken().replaceAll(
                            "[^a-zA-Z0-9]", "");//keep only characters
                    work.wordsInFile.add(strAdd.toLowerCase());
                  // System.out.print(strAdd.toLowerCase() + " ");
                    work.count++;   //counter for number of words in the arraylist      
                }
            }
            in.close();  //always close file after reading/writing
        } 
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        work.grammify();
        //System.out.printf("%d\n",work.q1.dequeue().hashCode());
        while (!work.q1.isEmpty()) {
            //System.out.printf("0x%x\n",work.q1.dequeue().hashCode());
            System.out.println(work.q1.dequeue());
        }
    }

    public void grammify() {
    	for(int i = 0; i < wordsInFile.size() - k + 1; i++)
    	{
    		String str = "";
    		for (int j = i; j < i + k && j < wordsInFile.size(); j++)
    		{
//    			if(j < wordsInFile.size())
    			str += wordsInFile.get(j) + " ";
    			
    		}
//    		q1.en
    		q1.enqueue(str);
    	}

    }
}//end class

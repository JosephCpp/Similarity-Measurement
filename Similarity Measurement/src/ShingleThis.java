import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;


public class ShingleThis 
{
    private ArrayList<String> wordsInFile;  //file converted to an array/list of Strings
    private Queue<String> q1;  //Queue to hold the final answer
    private int count = 0;   // count of number of words
    private int k;

    public ShingleThis(String fileName, int kgrams)
    {   
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
        int kgrams = 2; //changing this to test different k values

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
    	//iterates through all the words in the file, stopping in a way that, depending
    	//on what k is, stops so the last word is included in a k-length shingle
    	for(int i = 0; i < count - k + 1; i++)
    	{
    		String str = "";
    		//starts looping through the file starting from the index i, stopping when 
    		//either the last word has been looped through, or the loop has counted k times forward
    		for (int j = i; j < i + k && j < count; j++)
    		{
    			//Concatenates all the words that are k times forward, including i into
    			//one string to be enqueued
    			str += wordsInFile.get(j) + " ";
    			
    		}
    		//remove leading whitespace
    		str.trim();
    		//if a duplicate is detected, the boolean variable becomes true and 
    		//str is not enqueued
    		boolean duplicate = false;
    		for(String s: q1)
    		{
    			if (s.equals(str))
    			{
    				duplicate = true;
    			}
    		}
    		if (!duplicate)
    		{
    			q1.enqueue(str);
    			
    		}
    	}

    } // end of grammify
}//end class

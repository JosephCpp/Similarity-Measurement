import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//file is meant for testing obtaining the shingles/kgrams of a text file
public class ShingleThis 
{
	private String inFile01;  //input file 01
    private String inFile02;  //input file 02
    private ArrayList<String> wordsInFile01;  //file 1 converted to an array/list of Strings
    private ArrayList<String> wordsInFile02;  //file 2 converted to an array/list of Strings
    private Queue<String> q1;   // contains strings from file 1
    private Queue<String> q2;   // contains strings from file 2
    private int count1, count2;      // count of number of words
    private boolean detailedOutput; //self added variable to determine if program should output other values
    private static double jSimMeasure;
    private int k;
    public ShingleThis(String fileName01, String fileName02, int kgrams, boolean detailedOutput) {   
        inFile01 = fileName01;
        inFile02 = fileName02;
        k = kgrams;
        q1 = new Queue<String>(); //your k-grams will be stored here for your answer
        q2 = new Queue<String>(); 
        wordsInFile01 = new ArrayList<String>();  
        wordsInFile02 = new ArrayList<String>(); 
        count1=count2 = 0;
        jSimMeasure = 1.0;
    }

    public static void main(String[] args, boolean detailedOutput) 
    {
        String str;
        BufferedReader in;
        //for command line passed files
        //String inFile01 = args[0];
        //String inFile02 = args[1];
        //int kgrams = Integer.parseInt(args[2]);
        String inFile01 = "bushStateOfTheUnion2004.txt";
        String inFile02 = "clintonStateOfTheUnion1996.txt";
        int kgrams = 2;
        
        ShingleThis work = new ShingleThis(inFile01, inFile02, kgrams, detailedOutput);

        try{
            in = new BufferedReader( new FileReader(new File(FileSystems.getDefault().getPath("C:/Users/" + System.getProperty("user.name") + "/Documents/" + inFile01).toString()))); //open file and start to read it.
            while( (str = in.readLine()) != null) { //read each line and save it to str. 
                StringTokenizer strT = new StringTokenizer(str,"@-,# .!"); //remove all the items @-,# .! from the str.

                while (strT.hasMoreTokens()) { //add the words to wordsInFile collection
                    String strAdd = strT.nextToken().replaceAll(
                            "[^a-zA-Z0-9]", "");//keep only characters
                    work.wordsInFile01.add(strAdd.toLowerCase());                 
                    work.count1++;   //counter for number of words in the arraylist      
                }
            }
            in.close();  //always close file after reading/writing
        } 
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        
        try{
            in = new BufferedReader( new FileReader((new File(FileSystems.getDefault().getPath("C:/Users/" + System.getProperty("user.name") + "/Documents/" + inFile02).toString())))); //open file and start to read it.
            while( (str = in.readLine()) != null) { //read each line and save it to str. 
                StringTokenizer strT = new StringTokenizer(str,"@-,# .!"); //remove all the items @-,# .! from the str.
                while (strT.hasMoreTokens()) { //add the words to wordsInFile collection
                    String strAdd = strT.nextToken().replaceAll(
                            "[^a-zA-Z0-9]", "");//keep only characters
                    work.wordsInFile02.add(strAdd.toLowerCase());                 
                    work.count2++;   //counter for number of words in the arraylist      
                }
            }
            in.close();  //always close file after reading/writing
        } 
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        work.grammify(); 

        work.getSimilarity();
    
        System.out.println("Using k = " + kgrams );
        System.out.print("Similarity between " + inFile01 + " and " + inFile02 + " :" );
        
        System.out.printf("%.4f \n", jSimMeasure);  //print to 4 decimal places
    }

	private void getSimilarity() 
	{
		float sharedValuesCount = 0;
		count1 = q1.size();
	    count2 = q2.size();
	    for (String s1 : q1) //nested for each loop to count shared values
	    {
	      for(String s2 : q2)
	      {
	    	  if (s1.equals(s2))
	    	  {
	    		  sharedValuesCount++;
	    	  }
	      }
	    }
	    //added print statements for testing values
			   if (detailedOutput)
			   {
				   System.out.println("shared values count: " + sharedValuesCount);
				    System.out.println("--");
				    System.out.println("count of queue 1: " + count1);
				    System.out.println("count of queue 2: " + count2);
				    System.out.println("----");
				    System.out.println(sharedValuesCount);
				    System.out.println("over");
				    System.out.println(count1 + count2 - sharedValuesCount);
				    System.out.println("which evalues to: ");
			   }
			    //quantity of shared values over (quantity of values in both arraylists combined minus the quantity of shared values)
					jSimMeasure = (float)(sharedValuesCount/(float)(count1 + count2 - sharedValuesCount)); 
	}
	public Queue<String> fileToShingle(ArrayList<String> wordsInFile, int count, int kGrams)
	{
		Queue<String> queue = new Queue<String>();
		//iterate through all the words in a file, stopping so the last shingle is always included.
		for(int i = 0; i < count - kGrams + 1; i++)
		{
			String kShingle = "";
			
			//stop at the last word of the shingle, or the end of the file.
			for(int j = i; j < i + k && j < count; j++)
			{

				//adds word to the kShingle
				kShingle += wordsInFile.get(j) + " ";
			}
			kShingle.trim(); //remove whitespace
			boolean good = true; //check for duplicates before queueing
			for(String s : queue) 
			{
				if (s.equals(kShingle))
				{
					good = false;
				}
			}
			if (good)
			{
				queue.enqueue(kShingle);
			}
			
		}
		
		return queue;
		
	}
	public void grammify() 
	{
		q1 = fileToShingle(wordsInFile01, count1, k);
		q2 = fileToShingle(wordsInFile02, count2, k);
	}
}


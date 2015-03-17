import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;



/**
 * tlacMain
 * this is the main class for this program PhyloTLaC which stands for 
 * Phylogenetic Tree Lookups and Comparisons
 * <p>Bugs: will be updated as program is modified
 * 
 * 
 * @author asobin
 *
 */
public class tlacMain 
{
	public static String regex = ","; //used bc inputFile is csv
	/**
	 * main method 
	 * @param args is an array of string file names/locations
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		boolean stop = false;
		if(args.length > 2 || args.length == 0)
		{
			System.out.println("Program accepts only " +
					"two command line arguments. Goodbye");
			System.exit(-1);
		}

		//next we read in and generate the tree from command line args
		File treeFile = new File(args[1]);
		//makes sure file contents exist and are readable
		if(!treeFile.canRead() || !treeFile.exists())
		{
			System.out.println("Error: cannot access input file");
			System.exit(-1);
		}

		//setup the tree
		Tree tree = new Tree();
		try //this is where the tree will be populated
		{
			
			Scanner scn = new Scanner(treeFile);
			while(scn.hasNextLine())
			{
				String[] lineData = scn.nextLine().split(regex);
				PhyloNodeID ID1 = new PhyloNodeID(lineData[1]);
				//System.out.println(lineData[1] +" "+ lineData[0]);
				if(lineData[0].isEmpty())
				{
					tree.addPhyloNodeID(ID1,  null); 
				}//end if
				
				tree.addPhyloNodeID(ID1,lineData[0]);

			}//end while	
			scn.close(); 
		}//end try
		
		catch(FileNotFoundException e)
		{
			System.out.println("file not found");
		}
		
		System.out.println("Height: "+tree.getMaxGenerations());
		System.out.println("Root ID: "+ tree.getFirstAncestor());
		System.out.println("Number of nodes:  "
		+ tree.getNumberOfTreeNodes());
		
		double newMatrix[][];
		//BufferedWriter writer = new BufferedWriter(new FileWriter("fileOutput.txt", true));
		newMatrix = getFile(args);
		/**
		 * if you want to transpose the array uncomment this section
		 * double[][] finalMatrix = transpose(newMatrix);
		for(int i=0; i<finalMatrix.length;i++){
			for(int j=0; j<finalMatrix[0].length;j++)
			{
				writer.write(String.valueOf(finalMatrix[i][j])+",");

			}
			writer.newLine();
		}
		writer.close();
		 **/
		
		
		while(!stop)
		{
			Scanner scn = new Scanner(System.in);
			System.out.println("Enter a node ID: ");
			String input = scn.nextLine();
			if (input.length() > 3)
			{
				if(tree.contains(input))
				{
					final String parentName = tree.getParent(input); //TODO write this method in the Tree Class
					final String childName = input;
					final int parentsIndex;
					final int childsIndex;
					File indexRefLookUp = new File(args[0]);
					Scanner indexLU = new Scanner(indexRefLookUp);
					String[] lineLU = indexLU.nextLine().split(regex);
					indexLU.close();
					for(int i =0; i < lineLU.length; i++)
					{
						//determines index of parent
						if(lineLU[i].equals(parentName))
						{
							parentsIndex = i;
						}
						//determines index of child
						if(lineLU[i].equals(childName))
						{
							childsIndex = i;
						}
						
					}//end for
					
					Stack<Double> parentStack = new Stack<Double>();
					for(int f = 0; f < newMatrix.length; f++)
					{
						parentStack.push(newMatrix[f][parentsIndex]);
					}
					Stack<Double> childStack = new Stack<Double>();
					for(int q=0; q < newMatrix.length; q++)
					{
						childStack.push(newMatrix[q][childsIndex]);
					}
					
					//write methods that compare the values of the stacks
					//added(parentStack, childStack);
					//lost(parentStack, childStack);
					//shared(parentStack, childStack);
					//neverThere(parentStack, childStack);
					
					
				}//end if
			}//end if
		}//end while 
		
		

	}

	/**
	 * getFile
	 * method is used to import the file data and setup the tree and stack
	 * that will be used do the data analysis
	 * @param args
	 */
	public static double[][] getFile(String[] args)
	{
		try 
		{
			//initialize the file as a variable
			File initialFile = new File(args[0]);
			//setup scanner for file
			Scanner sc = new Scanner(initialFile);
			//first line is special case
			String[] firstLine = sc.nextLine().split(regex);

			//line is the line of input being read in thru the inputFile
			int line = 0;
			//array of doubles will hold the data to be put in the stacks
			double [][] theData = new double [1000][firstLine.length];
			while(sc.hasNext())
			{
				String lineIn = sc.nextLine();
				String[] lineInAsString = lineIn.split(regex);
				//System.out.print(lineInAsString[1]+",");
				for(int i = 2; i < lineInAsString.length; i++)
				{
					theData[line][i] = Double.parseDouble(lineInAsString[i]);
				}
				line++;

			}

			sc.close();

			return theData;
		}
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * transpose method can be used if you wish to transpose the matrix
	 * @param array
	 * @return
	 */
	public static double[][] transpose (double[][] array) {
		if (array == null || array.length == 0)//empty or unset array, nothing do to here
			return array;

		int width = array.length;
		int height = array[0].length;

		double[][] array_new = new double[height][width];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				array_new[y][x] = array[x][y];
			}
		}
		return array_new;
	}//end transpose
	
	/**
	 * TODO write this method
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int added(Stack<Double> parent, Stack<Double> child)
	{
		int added = 0;
		
		return added;
	}

	/**
	 * TODO write this method
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int lost(Stack<Double> parent, Stack<Double> child)
	{
		int lost = 0;
		
		return lost;
	}
	
	/**
	 * TODO write this method
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int shared(Stack<Double> parent, Stack<Double> child)
	{
		int shared = 0;
		
		return shared;
	}
	/**
	 * TODO write this method
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int neverThere(Stack<Double> parent, Stack<Double> child)
	{
		int neverThere = 0;
		
		return neverThere;
	}

}

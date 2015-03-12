import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		if(args.length > 2 || args.length == 0)
		{
			System.out.println("Program accepts only " +
					"two command line arguments. Goodbye");
			System.exit(-1);
		}
		//setup the BST
		Tree tree = new Tree();
		//next we read in and generate the tree from command line args
		File treeFile = new File(args[1]);
		Scanner scn = new Scanner(treeFile);
		while(scn.hasNextLine())
		{
			String[] lineData = scn.nextLine().split(regex);
			PhyloNodeID ID1 = new PhyloNodeID(lineData[1], lineData[0]);
			//System.out.println(lineData[1] +" "+ lineData[0]);
			if(lineData[0].isEmpty())
			{
				tree.addTreeNode(ID1,  null); 
			}//end if
			else
				tree.addTreeNode(ID1,lineData[0]);

		}//end while
		System.out.println(tree.getNumberOfTreeNodes());
		
		scn.close(); 
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


	}

	/**
	 * getFile
	 * method is used to import the file data and setup the tree and stack
	 * that will be used do the data analysis
	 * @param args
	 */
	public static double[][] getFile(String[] args)
	{
		//namesOfNodes is an array list that will store the names of the nodes
		//and be used later when using the stacks for comparisons
		ArrayList<String> namesOfNodes = new ArrayList<String>();
		try 
		{
			//initialize the file as a variable
			File initialFile = new File(args[0]);
			//setup scanner for file
			Scanner sc = new Scanner(initialFile);
			//first line is special case
			String[] firstLine = sc.nextLine().split(regex);
			for(int i =1; i < firstLine.length; i++)
			{
				namesOfNodes.add(firstLine[i]);

			}

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
	}

}

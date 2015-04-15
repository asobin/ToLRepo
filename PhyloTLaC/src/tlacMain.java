import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * @throws EmptyStackException 
	 */
	public static void main(String[] args) throws IOException, EmptyStackException
	{
		boolean done = false;
		
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
				PhyloNodeID ID1 = new PhyloNodeID(lineData[1], lineData[0]);
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
		
		while(!done)
		{
			double newMatrix[][];
			newMatrix = getFile(args);
			Scanner scn = new Scanner(System.in);
			System.out.print("Enter a node ID (press 'x' to exit): ");
			String input = scn.nextLine();
			
			if(input.substring(0).equalsIgnoreCase("x"))
			{
				done = true;
				System.out.println("exit");
				break;
			}
			
			if (input.length() > 2)
			{	
				if(tree.contains(input))
				{
					final String parentName = 
							tree.getPhyloNodeID(input).getParentName();
					final String childName = input;
					int parentsIndex = 0;
					int childsIndex = 0;

					System.out.println("Edge: "+parentName+"->"+childName);

					File indexRefLookUp = new File(args[0]);
					Scanner indexLU = new Scanner(indexRefLookUp);
					String[] lineLU = indexLU.nextLine().split(regex);
					indexLU.close();
					for(int i = 0; i < lineLU.length; i++)
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

					final int pI = parentsIndex;
					final int cI = childsIndex;

					Stack<Double> parentStackOne = new Stack<Double>();
					Stack<Double> parentStackTwo = new Stack<Double>();
					Stack<Double> parentStackThree = new Stack<Double>();
					Stack<Double> parentStackFour = new Stack<Double>();

					for(int f = 0; f < newMatrix.length; f++)
					{
						parentStackOne.push(newMatrix[f][pI]);
						parentStackTwo.push(newMatrix[f][pI]);
						parentStackThree.push(newMatrix[f][pI]);
						parentStackFour.push(newMatrix[f][pI]);
					}

					Stack<Double> childStackOne = new Stack<Double>();
					Stack<Double> childStackTwo = new Stack<Double>();
					Stack<Double> childStackThree = new Stack<Double>();
					Stack<Double> childStackFour = new Stack<Double>();

					for(int q=0; q < newMatrix.length; q++)
					{
						childStackOne.push(newMatrix[q][cI]);
						childStackTwo.push(newMatrix[q][cI]);
						childStackThree.push(newMatrix[q][cI]);
						childStackFour.push(newMatrix[q][cI]);
					}

					//write methods that compare the values of the stacks
					//first checking if internal or leaf edge
					if(input.startsWith("1") || input.startsWith("2"))
					{
						System.out.println("Internal Nodes added: "+
								addedInternal(parentStackOne, childStackOne));

						System.out.println("Internal Nodes lost: " +
								lostInternal(parentStackTwo, childStackTwo));

						System.out.println("Internal Nodes shared: " +
								sharedInternal(parentStackThree, childStackThree));

						System.out.println("Internal Nodes never there: "+ 
								neverThereInternal(parentStackFour, childStackFour));
					}
					else //outputs for the leaf nodes
					{
						System.out.println("Leaf Nodes added: "+
								addedLeaf(parentStackOne, childStackOne));

						System.out.println("Leaf Nodes lost: "+ 
								lostLeaf(parentStackTwo, childStackTwo));

						System.out.println("Leaf Nodes shared: "+
								sharedLeaf(parentStackThree, childStackThree));

						System.out.println("Leaf Nodes never there: "+
								neverThereLeaf(parentStackFour, childStackFour));
					}

				}//end if

			}//end if
		}
		System.exit(0);

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
			double [][] theData = new double [28420][firstLine.length];
			while(sc.hasNext())
			{
				String lineIn = sc.nextLine();
				String[] lineInAsString = lineIn.split(regex);
				for(int i = 1; i < lineInAsString.length; i++)
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
	 * this is a method that checks the internal edge of the tree
	 * for featureIDs that were added along this edge
	 * we use the following general rule to determine a gene as added:
	 * parent >= 0.75 && child <= 0.25
	 * @param parent
	 * @param child
	 * @return
	 * @throws EmptyStackException 
	 */
	public static int addedInternal(Stack<Double> parent, Stack<Double> child) 
			throws EmptyStackException
	{
		int addedIn = 0;

		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval <= 0.25)
			{
				addedIn++;
			}

		}
		return addedIn;
	}

	/**
	 * this is a method that checks the a leaf edge of the tree
	 * for featureIDs that were added along this edge
	 * we use the following general rule to determine a gene as added:
	 * parent >= 0.75 && child == 1
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int addedLeaf(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int addedL = 0;

		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval == 1)
			{
				addedL++;
			}
		}

		return addedL;
	}

	/**
	 * this is a method that checks the internal edge of the tree
	 * for featureIDs that were lost along this edge
	 * we use the following general rule to determine a gene as lost:
	 * parent <= 0.25 && child >= 0.75
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int lostInternal(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int lostIn = 0;

		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval >= 0.75)
			{
				lostIn++;
			}
		}
		return lostIn;
	}

	/**
	 * this is a method that checks the leaf edge of the tree
	 * for featureIDs that were lost along this edge
	 * we use the following general rule to determine a gene as lost:
	 * parent <= 0.25 && child == 0
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int lostLeaf(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int lostL = 0;
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval == 0)
			{
				lostL++;
			}
		}

		return lostL;
	}


	/**
	 * this is a method that checks the internal edge of the tree
	 * for featureIDs that were shared along this edge
	 * we use the following general rule to determine a gene as shared:
	 * parent <= 0.25 && child <= 0.25
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int sharedInternal(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int sharedInt = 0;
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval <= 0.25)
			{
				sharedInt++;
			}
		}
		return sharedInt;
	}

	/**
	 * this is a method that checks the leaf edge of the tree
	 * for featureIDs that were shared along this edge
	 * we use the following general rule to determine a gene as shared:
	 * parent <= 0.25 && child == 1
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int sharedLeaf(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int sharedL = 0;
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval == 1.0)
			{
				sharedL++;
			}
		}
		return sharedL;
	}


	/**
	 * this is a method that checks the internal edge of the tree
	 * for featureIDs that were never there along this edge
	 * we use the following general rule to determine a gene wasn't there:
	 * parent >= 0.75 && child >= 0.75
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int neverThereInternal
	(Stack<Double> parent, Stack<Double> child) throws EmptyStackException
	{
		int neverThereInt = 0;
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval >= 0.75)
			{
				neverThereInt++;
			}
		}
		return neverThereInt;
	}

	/**
	 * this is a method that checks the leaf of the tree
	 * for featureIDs that were never there along this edge
	 * we use the following general rule to determine a gene wasn't there:
	 * parent >= 0.75 && child == 0
	 * @param parent
	 * @param child
	 * @return
	 */
	public static int neverThereLeaf(Stack<Double> parent, Stack<Double> child)
			throws EmptyStackException
	{
		int neverThereL = 0;
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval == 0)
			{
				neverThereL++;
			}
		}
		return neverThereL;
	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
			DataContainer data = getFile(args);
			double[][] newMatrix = data.matrix;
			String[] featureIds = data.featureIds;
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
					Stack<String> featureIdStackOne = new Stack<String>();
					Stack<String> featureIdStackTwo = new Stack<String>();
					Stack<String> featureIdStackThree = new Stack<String>();
					Stack<String> featureIdStackFour = new Stack<String>();
					Stack<Double> parentStackOne = new Stack<Double>();
					Stack<Double> parentStackTwo = new Stack<Double>();
					Stack<Double> parentStackThree = new Stack<Double>();
					Stack<Double> parentStackFour = new Stack<Double>();
					for(int n = 0; n < featureIds.length; n++)
					{
						featureIdStackOne.push(featureIds[n]);
						featureIdStackTwo.push(featureIds[n]);
						featureIdStackThree.push(featureIds[n]);
						featureIdStackFour.push(featureIds[n]);
					}
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
						ArrayList<String> addedInternal = 
								addedInternal(parentStackOne, childStackOne, 
										featureIdStackOne);
						ArrayList<String> lostInternal = 
								lostInternal(parentStackTwo, childStackTwo, 
										featureIdStackTwo);
						ArrayList<String> sharedInternal = 
								sharedInternal(parentStackThree, childStackThree, 
										featureIdStackThree);					
						ArrayList<String> neverThereInternal = 
								neverThereInternal(parentStackFour, childStackFour, 
										featureIdStackFour);
						System.out.println("Internal Nodes added: " + 
								addedInternal.size());
						System.out.println("Internal Nodes lost: " + 
								lostInternal.size());
						System.out.println("Internal Nodes shared: " + 
								sharedInternal.size());
						System.out.println("Internal Nodes never there: "+ 
								neverThereInternal.size());				
						//write the added internal file
						PrintWriter pwAddedInt = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Added/Edge_"+parentName+"_"+childName+"_Added.txt");
						Iterator<String> itr = addedInternal.iterator();
						while(itr.hasNext())
						{
							pwAddedInt.write(itr.next()+" ");
						}
						pwAddedInt.close();			
						//write the lost internal file
						PrintWriter pwLostInt = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Lost/Edge_"+parentName+"_"+childName+"_Lost.txt");
						Iterator<String> itrB = lostInternal.iterator();
						while(itrB.hasNext())
						{
							pwLostInt.write(itrB.next()+" ");
						}
						pwLostInt.close();			
						//write the shared internal file
						PrintWriter pwSharedInt = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Shared/Edge_"+parentName+"_"+childName+"_Shared.txt");
						Iterator<String> itrC = sharedInternal.iterator();
						while(itrC.hasNext())
						{
							pwSharedInt.write(itrC.next()+" ");
						}
						pwSharedInt.close();				
						//write the never there internal file
						PrintWriter pwNeverThereInt = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Never There/Edge_"+parentName+"_"+childName+"_Never_There.txt");
						Iterator<String> itrD = neverThereInternal.iterator();
						while(itrD.hasNext())
						{
							pwNeverThereInt.write(itrD.next()+" ");
						}
						pwNeverThereInt.close();					
					}
					else //outputs for the leaf nodes
					{
						ArrayList<String> addedLeaf = 
								addedLeaf(parentStackOne, childStackOne, 
										featureIdStackOne);
						ArrayList<String> lostLeaf = 
								lostLeaf(parentStackTwo, childStackTwo, 
										featureIdStackTwo);
						ArrayList<String> sharedLeaf = 
								sharedLeaf(parentStackThree, childStackThree, 
										featureIdStackThree);					
						ArrayList<String> neverThereLeaf = 
								neverThereLeaf(parentStackFour, childStackFour, 
										featureIdStackFour);						
						System.out.println("Leaf Nodes added: "+
								addedLeaf.size());
						System.out.println("Leaf Nodes lost: "+ 
								lostLeaf.size());
						System.out.println("Leaf Nodes shared: "+
								sharedLeaf.size());
						System.out.println("Leaf Nodes never there: "+
								neverThereLeaf.size());					
						//write the added leaf file
						PrintWriter pwAddedLeaf = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Added/Edge_"+parentName+"_"+childName+"_Added.txt");
						Iterator<String> itrE = addedLeaf.iterator();
						while(itrE.hasNext())
						{
							pwAddedLeaf.write(itrE.next()+" ");
						}
						pwAddedLeaf.close();					
						//write the lost internal file
						PrintWriter pwLostLeaf = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Lost/Edge_"+parentName+"_"+childName+"_Lost.txt");
						Iterator<String> itrF = lostLeaf.iterator();
						while(itrF.hasNext())
						{
							pwLostLeaf.write(itrF.next()+" ");
						}
						pwLostLeaf.close();					
						//write the shared internal file
						PrintWriter pwSharedLeaf = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Shared/Edge_"+parentName+"_"+childName+"_Shared.txt");
						Iterator<String> itrG = sharedLeaf.iterator();
						while(itrG.hasNext())
						{
							pwSharedLeaf.write(itrG.next()+" ");
						}
						pwSharedLeaf.close();				
						//write the never there internal file
						PrintWriter pwNeverThereLeaf = new PrintWriter("C:/Users/Alexander/Documents/PernaProjectDirectoryMain/Never There/Edge_"+parentName+"_"+childName+"_Never_There.txt");
						Iterator<String> itrH = neverThereLeaf.iterator();
						while(itrH.hasNext())
						{
							pwNeverThereLeaf.write(itrH.next()+" ");
						}
						pwNeverThereLeaf.close();
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
	public static DataContainer getFile(String[] args)
	{
		try 
		{
			//initialize the file as a variable
			File initialFile = new File(args[0]);
			//setup scanner for file
			Scanner sc = new Scanner(initialFile);
			//first line is special case
			String[] firstLine = sc.nextLine().split(regex);
			String[] featureIds = new String[28420];
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
				featureIds[line] = lineInAsString[0].replaceAll("\"", "");
				line++;
			}
			sc.close();
			return new DataContainer(theData, featureIds);
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
	public static ArrayList<String> addedInternal(Stack<Double> parent, Stack<Double> child,
			Stack<String> featureIds) 
					throws EmptyStackException
					{
		ArrayList<String> addedInternal = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			double pval = parent.pop();
			double cval = child.pop();
			String featureID = featureIds.pop();
			if(pval >= 0.75 && cval <= 0.25)
			{
				addedInternal.add(featureID);
			}
		}
		return addedInternal;
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
	public static ArrayList<String> addedLeaf(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> addedLeaf= new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval == 1)
			{
				addedLeaf.add(featureID);
			}
		}
		return addedLeaf;
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
	public static ArrayList<String> lostInternal(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> lostInternal = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval >= 0.75)
			{
				lostInternal.add(featureID);
			}
		}
		return lostInternal;
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
	public static ArrayList<String> lostLeaf(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> lostLeaf = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval == 0)
			{
				lostLeaf.add(featureID);
			}
		}
		return lostLeaf;
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
	public static ArrayList<String> sharedInternal(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> sharedInternal = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval <= 0.25)
			{
				sharedInternal.add(featureID);
			}
		}
		return sharedInternal;
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
	public static ArrayList<String> sharedLeaf(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> sharedLeaf = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval <= 0.25 && cval == 1.0)
			{
				sharedLeaf.add(featureID);
			}
		}
		return sharedLeaf;
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
	public static ArrayList<String> neverThereInternal
	(Stack<Double> parent, Stack<Double> child, Stack<String> featureIds) 
			throws EmptyStackException
			{
		ArrayList<String> neverThereInternal = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval >= 0.75)
			{
				neverThereInternal.add(featureID);
			}
		}
		return neverThereInternal;
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
	public static ArrayList<String> neverThereLeaf(Stack<Double> parent, 
			Stack<Double> child, Stack<String> featureIds)
					throws EmptyStackException
					{
		ArrayList<String> neverThereLeaf = new ArrayList<String>();
		while(!parent.isEmpty() && !child.isEmpty())
		{
			String featureID = featureIds.pop();
			double pval = parent.pop();
			double cval = child.pop();
			if(pval >= 0.75 && cval == 0)
			{
				neverThereLeaf.add(featureID);
			}
		}
		return neverThereLeaf;
					}
}
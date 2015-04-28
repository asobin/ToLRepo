import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Tree
{
    static TreeNode<PhyloNodeID> root; //TreeNode that represents root of a tree
    String firstAncestor; //will hold the name of the parent at the root TreeNode.
    int maxGenerations; //holds the value of the # of generations in the tree
    PhyloNodeID ID; //the TreeNode of the desired TreeNode
    List<PhyloNodeID> thisTreeNodesData; //holds the eye color of the desired people
    List<PhyloNodeID> siblings; //holds the siblings of an individual
    /**
     * Tree
     * constructs a family tree, initializes all variables
     */
    public Tree()
    {
        root = null;
        maxGenerations = 0;
        thisTreeNodesData = new ArrayList<PhyloNodeID>();
        siblings = new ArrayList<PhyloNodeID>();
        firstAncestor = null;
    }    
    /**
     * getFirstAncestor
     * gets the name of the first ancestor in this family tree
     * @return firstAncestor
     */
    public String getFirstAncestor()
    {
        if(root != null)
        {
            firstAncestor = root.getPhyloNodeID().getName();
            return firstAncestor;
        }
        else
        {
            //if root is removed
            return "there is no ancestor";
        }
    }
    /**
     * getNumberOfTreeNodes
     * Return the number of TreeNodes in this tree
     * @return numberOfTreeNodes
     */
    public int getNumberOfTreeNodes()
    {
        int numberOfTreeNodes = 0;
        if(root != null)
        {
            numberOfTreeNodes = getNumberOfTreeNodes(root) + 1; //1 for the root
        }
        return numberOfTreeNodes;
    }
    /**
     * getNumberOfTreeNodes
     * private recursive method returns the number of TreeNodes in the tree
     * @param TreeNode
     * @return numberOfTreeNodes
     */
    private int getNumberOfTreeNodes(TreeNode<PhyloNodeID> TreeNode)
    {
        int numberOfTreeNodes = TreeNode.getChildren().size();
        //for each TreeNode in the children's list increment count
        for(TreeNode<PhyloNodeID> child : TreeNode.getChildren())
        {
            numberOfTreeNodes += getNumberOfTreeNodes(child);
        }
        return numberOfTreeNodes;
    }
    /**
     * getMaxGenerations
     * Return the maximum number of generations from the first ancestor to the
     * farthest descendant in the family tree.
     * @return maxGenerations
     */
    public int getMaxGenerations()
    {
        //Return 0 for an empty family tree
        if(root == null)
        {
            return 0;
        }
        maxGenerations = getMaxGenerations(root);
        return maxGenerations;
    }
    /**
     * getMaxGenerations
     * private recursive method keeps count of the number of generations/height
     * of the tree
     * @param root2
     * @return maxHeight
     */
    private int getMaxGenerations(TreeNode<PhyloNodeID> root2)
    {
        if(root2 == null)
        {
            return 0; //base
        }
        if(root2.getChildren().isEmpty())
        {
            return 1; //base
        }
        int maxHeight = 0;
        Iterator <TreeNode<PhyloNodeID>> itr = root2.getChildren().iterator();
        while(itr.hasNext())
        {
            int childHt = getMaxGenerations(itr.next());
            if(childHt > maxHeight)
            {
                maxHeight = childHt;
            }
        }
        return 1+maxHeight;
    }
    /**
     * getPhyloNodeID
     * Returns the TreeNode of given name
     * @param name
     * @return TreeNode
     */
    PhyloNodeID getPhyloNodeID(String name)
    {
        if(contains(name))
        {
            return findPhyloNodeID(root,name).getPhyloNodeID();
        }
        return null;
    }
    /**
     * addTreeNode
     * Add TreeNode with given parent.
     * @param TreeNode
     * @param parentName
     * @return true iff TreeNode is added correctly(no duplicate/has parent)
     */
    public boolean addPhyloNodeID(PhyloNodeID ID, String parentName)
    {
        //When the root and parentName are both null, return true.
        //Do not throw an IllegalArgumentException for this circumstance only.
        if(parentName == null && root == null)
        {
            root = new TreeNode<PhyloNodeID>(ID, null);
            return true;
        }
        //When the parentName of the node being added refers to the root
        //as its parent.
        if(parentName.equals(root.getPhyloNodeID().getName()))
        {
            TreeNode<PhyloNodeID> childTreeNode =
            		new TreeNode<PhyloNodeID>(ID, root);
            root.addChild(childTreeNode);
            return true;
        }
        //if the family tree already has this TreeNode, don't do anything
        //and return false
        if(contains(ID.getName()))
        {           
        	return false;
        }
        //If there is no such parent, return false
        if(contains(parentName) == false)
        {
            return false;
        }
        //otherwise add the TreeNode and return true.
        addNewChild(root, parentName, ID);
        return true;
    }
    /**
     * contains
     * Return true if the family tree contains TreeNode with given name,
     * otherwise return false.
     * @param name
     * @return true iff the family tree contains name
     */
    public boolean contains(String name)
    {
        //return contains(root, name);
        if(findPhyloNodeID(root,name) == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    /**
     * removeWithName
     * Remove the TreeNode with given Name from the family tree, and remove all
     * that TreeNode's descendants at the same time.
     * @param name
     * @return true iff removal was valid (name exists)
     */
    public boolean removeWithName(String name)
    {
        //If there is no such TreeNode, return false
        if(!contains(name))
        {
            return false;
        }
        if(root.getPhyloNodeID().getName().equals(name))
        {
            root = null;
            maxGenerations = 0;
            thisTreeNodesData = new ArrayList<PhyloNodeID>(); 
            siblings = new ArrayList<PhyloNodeID>();
            firstAncestor = null;
            return true;
        }
        // otherwise, return true after the removal
        return removePhyloNodeID(name);
    }
    /**
     * removeTreeNode
     * private recursive method that determines if a TreeNode was removed or not
     * @param name
     * @return true iff TreeNode was properly removed
     */
    private boolean removePhyloNodeID(String name)
    {
        if(findPhyloNodeID(root,name) != null)
        {
            TreeNode<PhyloNodeID> removed = findPhyloNodeID(root,name);
            removed.getParent().getChildren().remove(removed);
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * getSiblings
     * Return the list of siblings of this TreeNode.
     * @param name
     * @return siblings
     */
    public List<PhyloNodeID> getSiblings(String name)
    {
        //If there is no such TreeNode in the family tree or the TreeNode doesn't
        //have any sibling, return null.
        siblings = new ArrayList<PhyloNodeID>();
        siblings = getSiblings(root, name);
        return siblings;
    }
    /**
     * getSiblings
     * gets and returns the list of TreeNodes for the siblings list
     * @param n
     * @param name
     * @return siblings
     */
    private List<PhyloNodeID> getSiblings(TreeNode<PhyloNodeID> n, String name)
    {
        if(n == null)
        {
            return null;
        }
        if(n.getPhyloNodeID().getName().equals(name))
        {
            List<TreeNode<PhyloNodeID>> tmp = n.getParent().getChildren();
            Iterator<TreeNode<PhyloNodeID>> itr = tmp.iterator();
            while(itr.hasNext())
            {
                TreeNode<PhyloNodeID> tmp1 = itr.next();
                //if youre not the TreeNode passed in add to list
                if(!tmp1.getPhyloNodeID().getName().equals(name))
                {
                    siblings.add(tmp1.getPhyloNodeID());
                }//end if
            }//end while
        }//end if
        Iterator<TreeNode<PhyloNodeID>> itr = n.getChildren().iterator();
        while(itr.hasNext())
        {
            getSiblings(itr.next(),name);
        }
        return siblings;
    }
    /**
     * addNewChild
     * recursive private method for addNewChild
     * @param name
     * @param parentName
     * @param p
     */
    private void addNewChild(TreeNode<PhyloNodeID> name,
            String parentName, PhyloNodeID p)
    {
    	//if the parent node doesn't have any children.
        if(name.getChildren() == null)
            return; //base case
        //if the parent node's name matches the parent name of the child node
        if(name.getPhyloNodeID().getName().equals(parentName))
        {
            name.addChild(new TreeNode<PhyloNodeID>(p, name));
            return; //base case  
        }
        //iterate thru parent's getChildren list
        Iterator<TreeNode<PhyloNodeID>> itr = name.getChildren().iterator();
        while(itr.hasNext())
        {
            addNewChild(itr.next(), parentName, p); //recursive case
        }
    }
    /**
     * findPhyloNodeID
     * Returns the TreeNode with given name, otherwise
     * return null.
     * @param n, name
     * @return TreeNode if found in the tree, otherwise return null
     */
    private TreeNode<PhyloNodeID> findPhyloNodeID
    (TreeNode<PhyloNodeID> n , String name)
    {
        if(n == null)
        {
            return null; //base
        }
        if(n.getPhyloNodeID().getName().equals(name))
        {
            return n; //base
        }
        if(n.getChildren().isEmpty())
        {
            return null; //base
        }
        Iterator<TreeNode<PhyloNodeID>> itr = n.getChildren().iterator();
        while(itr.hasNext())
        {
            TreeNode<PhyloNodeID> tmp = findPhyloNodeID(itr.next(),name);
            //when the recursion finds a TreeNode, return that TreeNode
            if(tmp != null)
            {
                return tmp;
            }
        }
        return null;
    }   
}
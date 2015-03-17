import java.util.*;
public class TreeNode<E>
{
	private PhyloNodeID ID;
	private TreeNode<E> parentNode;
	private List<TreeNode<E>> children;
	
	/** Constructs a TreeNode with PhyloNodeID and parentNode. */
	public TreeNode (PhyloNodeID ID, TreeNode<E> parentNode)
	{
		this.ID = ID;
		this.parentNode = parentNode;
		children = new ArrayList<TreeNode<E>>();
	}
	/** Return the PhyloNodeID in this node */
	public PhyloNodeID getPhyloNodeID()
	{
		return ID;
	}
	/** Return the parent for the PhyloNodeID in this node */
	public TreeNode<E> getParent()
	{
		return parentNode;
	}
	/** Return the children for the PhyloNodeID in this node */
	public List<TreeNode<E>> getChildren()
	{
		return children;
	}
	/** Return the PhyloNodeID in this node */
	public void addChild(TreeNode<E> childNode)
	{
		children.add(childNode);
	}
}

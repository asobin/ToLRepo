public class PhyloNodeID 
{	
	private String name;
	private String parentName;
	/**Constructs a node with name, and nodesData */
	public PhyloNodeID(String name, String parentName)
	{
		this.name = name;
		this.parentName = parentName;
	}
	/** Return the name of this node */
	public String getName()
	{
		return name;
	}
	public String getParentName()
	{
		return parentName;
	}
}

public class PhyloNodeID {
	
	   private String name;
	    private String nodesData;
	   
	    /**Constructs a node with name, and nodesData */
	    public PhyloNodeID(String name, String nodesData)
	    {
	        this.name = name;
	        this.nodesData = nodesData;
	       
	    }
	    /** Return the name of this node */
	    public String getName()
	    {
	        return name;
	    }
	    /** Return the data related to this node */
	    public String getTreeNodesWithThisData()
	    {
	        return nodesData;
	    }
		}



public class PhyloNodeID {
	
	   private String name;
	    private String nodesData;
	   
	    /**Constructs a node with name, nodesData, and weight. */
	    public PhyloNodeID(String name, String nodesData)
	    {
	        this.name = name;
	        this.nodesData = nodesData;
	       
	    }
	    /** Return the name of this person */
	    public String getName()
	    {
	        return name;
	    }
	    /** Return the data related to this person */
	    public String getTreeNodesWithThisData()
	    {
	        return nodesData;
	    }
		}


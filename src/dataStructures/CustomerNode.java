package dataStructures;

import java.util.ArrayList;

/**
 * This class represents a customer node.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class CustomerNode extends Node implements Cloneable{

	/**
	 * Total flow to a node (how many routes visit the node at a given bap iteration)
	 */
	public double total_flow_to_node;
	
	/**
	 * This method creates a new customer node
	 * @param i id
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param e early arrival time
	 * @param l late arrival time
	 * @param s service time
	 */
	public CustomerNode(int i, int or_i,double x, double y, double e,double l, double s,String ty, double rewar) {
		
		id = i;
		original_id = or_i;
		coor_x = x;
		coor_y = y;
		tw_a = e;
		tw_b = l;
		service = s;
		type = ty;
		reward = rewar;
		
		// Arcs:
		
		forwardStar = new ArrayList<Arc>();
		forwardStar_drive = new ArrayList<Arc>();
		forwardStar_walk = new ArrayList<Arc>();
		
	}

	
	
	/**
	 * This method returns a string with all the information of the customer node.
	 * @return
	 */
	public String toCompleteString() {
		return "Customer: "+id+" located at: ("+coor_x+";"+coor_y+")"+" may be visited by: ("+tw_a+";"+tw_b+")";
	}
	
}

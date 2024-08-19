package dataStructures;

import java.util.ArrayList;

/**
 * This class represents the start node
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class StartDepot extends Node{

	
	/**
	 * This method creates a new source depot node
	 * @param i id
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param e early arrival time
	 * @param l late arrival time
	 */
	public StartDepot(int i, int or_i, double x, double y, double e,double l) {
		
		id = i;
		original_id = or_i;
		coor_x = x;
		coor_y = y;
		tw_a = e;
		tw_b = l;
		type = "Depot";
		reward = 0;
		
		// Arcs:
		
		forwardStar = new ArrayList<Arc>();
		forwardStar_drive = new ArrayList<Arc>();
		forwardStar_walk = new ArrayList<Arc>();
	
	}
	
	public String toCompleteString() {
		return "SourceDepot: "+id+" located at: ("+coor_x+";"+coor_y+")"+" may be visited by: ("+tw_a+";"+tw_b+")";
	}

	
	
}


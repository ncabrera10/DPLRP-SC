package constraints;

import java.io.PrintWriter;

import dataStructures.CustomerNode;
import dataStructures.DataHandler;
import dataStructures.Solution;

/**
 * This class contains the logic to check if every node is served by at the most one route.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class Cons_NodeServed {

	public Cons_NodeServed() {
		
	}
	
	/**
	 * This method checks the constraint for all routes:
	 * @param solution
	 * @param instance
	 * @param output
	 * @param precision
	 * @return
	 */
	public boolean checkConstraint(Solution solution, DataHandler data, boolean output, int precision,PrintWriter pw) {
		for (Integer route : solution.getRoutesUnique()) {
			if (!checkConstraint(route,solution, data, output, precision,pw)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method checks a route
	 * @param route
	 * @param instance
	 * @param output
	 * @param precision
	 * @return
	 */
	public boolean checkConstraint(int routeID,Solution sol, DataHandler data,boolean output, int precision,PrintWriter pw) {

		//Iterate through the customer nodes:
		
		for(CustomerNode node:data.customer_nodes) {
			
			// Check if the node is visited by more than one route:
			
			if(sol.getRoutes_visiting_customer().containsKey(node.id)) {
				if(sol.getRoutes_visiting_customer().get(node.id).size() > 1) {
					return false;
				}
			}
	

		}
		
		//If nothing happened:
		
			return true;
	}
}

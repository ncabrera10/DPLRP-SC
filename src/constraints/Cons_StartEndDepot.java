package constraints;

import java.io.PrintWriter;
import dataStructures.DataHandler;
import dataStructures.Solution;

/**
 * This class contains the logic to check if all routes start and end at the depot.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class Cons_StartEndDepot {

	public Cons_StartEndDepot() {
		
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

		//Iterate through the lists:
		
		int numArcs = sol.getHeads().size();
		boolean first = true;
		boolean start = true;
		boolean end = false;
		
		for(int i = 0;i<numArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int route = sol.getRoutes().get(i);
			
			if(routeID == route && first) {
				
				if(tail == data.start_depot.id) {
					start = true;
				}else {
					start = false;
				}
				
				first = false;
				 
			}
			
			if(!first && routeID != route) {
				
				if(sol.getHeads().get(i-1)-1 == data.start_depot.id) {
					end = true;
				}else {
					end = false;
				}
				break;
			}
			
			if(i == numArcs-1 && sol.getHeads().get(i)-1 == data.start_depot.id) {
				end = true;
			}else {
				end = false;
			}
			
		}

		
		//Check the constraints:
		
		if(!start || !end) {
			pw.println("Route "+routeID+" does not start/end at the depot: "+start+" - "+end);
			return false;
		}
		
		//If nothing happened:
		
			return true;
	}
}

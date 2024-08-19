package constraints;

import java.io.PrintWriter;
import java.util.ArrayList;

import dataStructures.Arc;
import dataStructures.DataHandler;
import dataStructures.Solution;

/**
 * This class contains the logic to check if a route respects the capacity constraints.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class Cons_RouteCapacity {

	public Cons_RouteCapacity() {
		
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

		//Initialize the important values:
		
		Double duration=0.0,drivingTime=0.0,drivingDistance=0.0,walkingTime=0.0,walkingDistance=0.0,serviceTime = 0.0, load = 0.0;
		ArrayList<Integer> nodesInRoute = new ArrayList<Integer>();
		
		//Iterate through the lists:
		
		int numArcs = sol.getHeads().size();
		for(int i = 0;i<numArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int head = sol.getHeads().get(i)-1;
		
			int type = sol.getTypes().get(i);
			int route = sol.getRoutes().get(i);
			
			Arc arc = null;
			if(type == 1) {
				arc = data.arcs.get(tail+"-"+head);
			}else {
				arc = data.arcs.get(tail+"_"+head);
			}
			if(arc == null) {
				return false;
			}
			if(routeID == route) {
				
				if(!nodesInRoute.contains(tail)) {
					nodesInRoute.add(tail);
				}
				if(type == 1) {
					
					drivingTime += arc.getTime();
					drivingDistance += arc.getDistance();
					if(tail == data.start_depot.id) {
						
						duration = Math.max(arc.getTime() + duration,data.customer_nodes.get(head-1).tw_a);
						
					}else if(head == data.start_depot.id) {
						duration = data.customer_nodes.get(tail-1).service + arc.getTime() + duration;
						serviceTime += data.customer_nodes.get(tail-1).service;
						load += data.customer_nodes.get(tail-1).demand;
					}
					else {
						duration = Math.max(data.customer_nodes.get(tail-1).service + arc.getTime() + duration,data.customer_nodes.get(head-1).tw_a);
						serviceTime += data.customer_nodes.get(tail-1).service;
						load += data.customer_nodes.get(tail-1).demand;
					}
					
					
				}else {
					
					walkingTime += arc.getTime();
					walkingDistance += arc.getDistance();
					if(tail == data.start_depot.id) {
						
						duration = Math.max(arc.getTime() + duration,data.customer_nodes.get(head-1).tw_a);
						
					}else if(head == data.start_depot.id) {
						duration = data.customer_nodes.get(tail-1).service + arc.getTime() + duration;
						serviceTime += data.customer_nodes.get(tail-1).service;
						load += data.customer_nodes.get(tail-1).demand;
					}
					else {
						duration = Math.max(data.customer_nodes.get(tail-1).service + arc.getTime() + duration,data.customer_nodes.get(head-1).tw_a);
						serviceTime += data.customer_nodes.get(tail-1).service;
						load += data.customer_nodes.get(tail-1).demand;
					}
				}
				 
			}
			
			
		}
		

		//Check the constraints:
		
		if(load - Math.pow(10, -precision) > DataHandler.route_capacity) {
			pw.println("Route "+routeID+" has a total load of "+load+" > "+DataHandler.route_capacity);
			return false;
		}
		
		//If nothing happened:
		
			return true;
	}
}

package constraints;

import java.io.PrintWriter;

import dataStructures.Arc;
import dataStructures.DataHandler;
import dataStructures.Solution;
import parameters.GlobalParameters;

/**
 * This class contains all the logic to check if each route fulfills the 
 * maximum walking distance constraint
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class Cons_MaxWalkDistance {

	public Cons_MaxWalkDistance() {
		
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
		
		Double duration=0.0,drivingTime=0.0,drivingDistance=0.0,walkingTime=0.0,walkingDistance=0.0,serviceTime = 0.0,parkingTime =0.0,waitingTime=0.0;
		
		//Iterate through the lists:
		
		int numArcs = sol.getHeads().size();
		boolean parked = false;
		int parking_spot = -1;
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
			
			if(routeID == route) {
				
				
				if(!parked) {
					
					if(type == 1) {
						
						parkingTime += GlobalParameters.PARKING_TIME_MIN;
						drivingTime += arc.getTime();
						drivingDistance += arc.getDistance();
						
						duration += arc.getTime();
						
						if(head != 0) {
							if(duration < data.customer_nodes.get(head-1).tw_a) {
								
								waitingTime += data.customer_nodes.get(head-1).tw_a - duration;
								duration = data.customer_nodes.get(head-1).tw_a;
								
							}
							
							duration += data.customer_nodes.get(head-1).service;
							serviceTime += data.customer_nodes.get(head-1).service;
							
						}
						
					}else {
						
						walkingTime += arc.getTime();
						walkingDistance += arc.getDistance();
						
						duration += arc.getTime();
						
						if(duration < data.customer_nodes.get(head-1).tw_a) {
							
							waitingTime += data.customer_nodes.get(head-1).tw_a - duration;
							duration = data.customer_nodes.get(head-1).tw_a;
							
						}
						
						duration += data.customer_nodes.get(head-1).service;
						serviceTime += data.customer_nodes.get(head-1).service;
						
						parked = true;
						parking_spot = tail;
					}
					
				}else {
					
					if(head == parking_spot) {
						
						walkingTime += arc.getTime();
						walkingDistance += arc.getDistance();
						
						duration += arc.getTime();
						
						parked = false;
						parking_spot = -1;
						
					}else {
						
						walkingTime += arc.getTime();
						walkingDistance += arc.getDistance();
						
						duration += arc.getTime();
						
						if(duration < data.customer_nodes.get(head-1).tw_a) {
							
							waitingTime += data.customer_nodes.get(head-1).tw_a - duration;
							duration = data.customer_nodes.get(head-1).tw_a;
							
						}
						
						duration += data.customer_nodes.get(head-1).service;
						serviceTime += data.customer_nodes.get(head-1).service;
						
					}
					
				}
			}	
			
			
		
		parkingTime -= GlobalParameters.PARKING_TIME_MIN;
		
			
			
		}
		

		//Check the constraints:
		
		if(walkingTime*(DataHandler.walking_speed/60) - Math.pow(10, -precision) > DataHandler.route_walking_distance_limit) {
			pw.println("Route "+routeID+" has a walking time "+walkingTime+" > "+DataHandler.route_walking_distance_limit);
			return false;
		}
		
		//If nothing happened:
		
			return true;
	}
}

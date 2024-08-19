package evaluators;

import java.io.PrintWriter;

import dataStructures.Arc;
import dataStructures.CustomerNode;
import dataStructures.DataHandler;
import dataStructures.Solution;
import parameters.GlobalParameters;

public class FOEvaluator {
	
	public FOEvaluator() {
		
	}
	
	public double evaluateOF(Solution sol, DataHandler data) {
		
		//Initialize the objective function:
		
		double fo = 0;
		
		//Iterate through the solution:
		
		int numberOfArcs = sol.getHeads().size(); 
		boolean parked = false;
		int parking_spot = -1;
		for(int i=0;i<numberOfArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int head = sol.getHeads().get(i)-1;
			
			int type = sol.getTypes().get(i);
			
				if(!parked) {
					
					if(type == 1) {
						
						if(head != 0) {
							if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
								fo+=1;
							}
						}
						
					}else {
						
						if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
							fo+=1;
						}
						
						parked = true;
						parking_spot = tail;
					}
					
				}else {
					
					if(head == parking_spot) {
						
						parked = false;
						parking_spot = -1;
						
					}else {
						
						if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
							fo+=1;
						}
						
					}
				}

			
			
		}
		
					
		//Return the fo:
		
		return(fo);
	}
	
	public double evaluateObjectiveFunctionRoute(int routeID, Solution sol, DataHandler data) {
		
		//Initialize the objective function:
		
			double fo = 0;
			
			//Iterate through the solution:
			
			int numberOfArcs = sol.getHeads().size(); 
			boolean parked = false;
			int parking_spot = -1;
			for(int i=0;i<numberOfArcs;i++) {
				
				int tail = sol.getTails().get(i)-1;
				int head = sol.getHeads().get(i)-1;
				
				int type = sol.getTypes().get(i);
				int route = sol.getRoutes().get(i);
				/**Arc arc = null;
				if(type == 1) {
					arc = data.arcs.get(tail+"-"+head);
				}else {
					arc = data.arcs.get(tail+"_"+head);
				}*/
				
				if(route == routeID) {
					if(!parked) {
						
						if(type == 1) {
							
							if(head != 0) {
								if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
									fo+=1;
								}
							}
							
						}else {
							
							if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
								fo+=1;
							}
							
							parked = true;
							parking_spot = tail;
						}
						
					}else {
						
						if(head == parking_spot) {
							
							parked = false;
							parking_spot = -1;
							
						}else {
							
							if(data.customer_nodes.get(head-1).type.equals("OnDemand")) {
								fo+=1;
							}
							
						}
					}

				}
				
			}
			
			
			//Return the fo:
			
			return(fo);
	
	}
	
	public void evaluateAttributes(CustomerNode node, Solution sol, DataHandler data,PrintWriter pw) {
		
		//Check the constraints:
		
		if(sol.getRoutes_visiting_customer().containsKey(node.id)) {
			pw.println((node.id+1)+" - "+sol.getRoutes_visiting_customer().get(node.id).toString()+" - "+node.type);
			
		}
		
	}
	
	
	public void evaluateAttributes(int routeID,Solution sol, DataHandler data,PrintWriter pw) {
		
		//Initialize the important values:
		
		try {
			Double duration=0.0,drivingTime=0.0,drivingDistance=0.0,walkingTime=0.0,walkingDistance=0.0,serviceTime = 0.0,parkingTime =0.0,waitingTime=0.0,movingTime=0.0;
		
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
				
				
			}
			parkingTime -= GlobalParameters.PARKING_TIME_MIN;
			
			//Check the constraints:
			
			//pw.println("Route "+routeID+":"+duration+" - "+drivingDistance+" - "+drivingTime+" - "+walkingDistance+" - "+walkingTime+" - "+serviceTime+" - "+load+" - "+parkingTime+" - "+waitingTime);
			pw.println(routeID+"\t"+duration+"\t"+waitingTime+"\t"+serviceTime+"\t"+parkingTime+"\t"+movingTime+"\t"+drivingDistance+"\t"+walkingDistance);

		}catch(Exception e) {
			pw.println("Route "+routeID+" is using an arc that does not comply with the problem constraints");
			e.printStackTrace();
			
		}
		
		
		
	}
}

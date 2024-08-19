package checkers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import dataStructures.CustomerNode;
import dataStructures.DataHandler;
import dataStructures.Solution;
import evaluators.ConstraintsEvaluator;
import evaluators.FOEvaluator;
import parameters.GlobalParameters;

/**
 * This class is in charge of performing the checking for a given solution file.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public class Checker {

	/**
	 * This method creates a new instance of the checker.
	 * @param solutionFileName
	 * @throws IOException
	 */
	public Checker(String solutionFileName) throws IOException {
		
		//Captures the current instance number:
		
			String solutionFileNameM = solutionFileName.replace(".txt", "");
	
		// Key values of the current instance:
			
			String[] parts = solutionFileNameM.split("_");
		
			int replicate = Integer.parseInt(parts[1]);
			double degree_of_dynamism = Double.parseDouble(parts[2]);
			double arrival_rate = Double.parseDouble(parts[3]);
			String distribution = parts[4];
			int series = Integer.parseInt(parts[5]);
			int number_workers = Integer.parseInt(parts[6]);
			
		// Stores the name of the instance:
		
			String dataFile = replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers+".txt";
	
		
		// Read the data:
		
			DataHandler data = this.readDataInfo(dataFile,replicate,degree_of_dynamism,arrival_rate,distribution,series,number_workers);

		// Reads the solution:
			
			Solution solution = this.readSolution(solutionFileName,dataFile,replicate,degree_of_dynamism,arrival_rate,distribution,series,number_workers);
		
		// Prints the report:
			
			printReport(solution,data,replicate,degree_of_dynamism,arrival_rate,distribution,series,number_workers);
			
	}	
	
	/**
	 * This method prints a report file in the results folder
	 * 
	 * @param solution
	 * @param data
	 * @param edf_id
	 * @param degree_of_dynamism
	 * @param arrival_rate
	 * @param number_workers
	 * @param replicate
	 */
	public void printReport(Solution solution,DataHandler data, int replicate,double degree_of_dynamism, double arrival_rate,String distribution, int series, int number_workers) {
		
		
		//Creates a path to print the report:
		
			String ruta = "";
			String ruta_details = "";
			
			ruta = GlobalParameters.RESULT_FOLDER+"Report_"+GlobalParameters.ALGORITHM_NAME+"_"+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers+".txt";
			ruta_details = GlobalParameters.RESULT_FOLDER+"DetailsReport_"+GlobalParameters.ALGORITHM_NAME+"_"+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers+".txt";
			String ruta_summary = GlobalParameters.RESULT_FOLDER+"Summary_"+GlobalParameters.ALGORITHM_NAME+"_"+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers+".txt";
			
		//Main logic:
		
			try {
				PrintWriter pw = new PrintWriter(new File(ruta));
				PrintWriter pw2 = new PrintWriter(new File(ruta_details));
				PrintWriter pw3 = new PrintWriter(new File(ruta_summary));
				
				//Headline:
				
				pw.println("Instance:"+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers);
				pw3.println("Parameter;Value");
				pw3.println("Instance;"+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers);
				pw3.println("Algorithm;"+GlobalParameters.ALGORITHM_NAME);
				
				//Check if the solution complies with the constraints:
				
				ConstraintsEvaluator evaluator_cons = new ConstraintsEvaluator();
				boolean cons1 = evaluator_cons.evaluateRouteWalkingDistance(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons2 = evaluator_cons.evaluateRouteDuration(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons3 = evaluator_cons.evaluateStartEndDepot(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons4 = evaluator_cons.evaluateRouteCapacity(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons5 = evaluator_cons.evaluateRouteTW(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons6 = evaluator_cons.evaluateNodeServed(solution, data, GlobalParameters.PRECISION, pw2);
				boolean cons7 = evaluator_cons.evaluateServeAllScheduled(solution, data, GlobalParameters.PRECISION, pw2);
				
				pw.println("----------------Constraints----------------");
				pw.println("Route walking distance limit: "+cons1);
				pw.println("Route duration constraints: "+cons2);
				pw.println("Route start-end constraints: "+cons3);
				pw.println("Route capacity: "+cons4);
				pw.println("Route time windows: "+cons5);
				pw.println("Each node is served by only one route: "+cons6);
				pw.println("Each scheduled request must be fulfilled: "+cons7);
				
				
				if(cons1 && cons2 && cons3 && cons4 && cons5 && cons6 && cons7) {
					pw.println("The solution is valid");
					pw3.println("Feasible;"+1);
				}else {
					System.out.println("Invalid "+GlobalParameters.ALGORITHM_NAME+" - "+replicate+"_"+degree_of_dynamism+"_"+arrival_rate+"_"+distribution+"_"+series+"_"+number_workers);
					pw.println("The solution is invalid");
					pw3.println("Feasible;"+0);
				}
				
			//Objective function for the solution:
				
				//The objective function:
				
				FOEvaluator fo_eva = new FOEvaluator();
				double objFO = fo_eva.evaluateOF(solution, data);
				pw.println("----------------ObjectiveFunction----------------");
				pw.println("ObjectiveFunction: "+objFO);
				pw3.println("ObjectiveFunction;"+objFO);
				
				//Objective function for each route:
				pw.println("----------------Routes----------------");
				pw.println("RouteID - ObjectiveFunction");
				
				for(Integer route : solution.getRoutesUnique()) {
					pw.println(route+" - "+fo_eva.evaluateObjectiveFunctionRoute(route, solution, data));
				}
				
				//Attributes for each route:
				pw.println("----------------Attributes----------------");
				pw.println("RouteID \t Duration \t WaitingTime \t ServiceTime \t ParkingTime \t MovingTime \t DrivingDistance \t WalkingDistance");
				for(Integer route : solution.getRoutesUnique()) {
					fo_eva.evaluateAttributes(route, solution, data,pw);
				}
				
				//Attributes for each customer:
				pw.println("----------------Customers----------------");
				pw.println("NodeID - VisitedBy - Outsourced");
				for(CustomerNode node : data.customer_nodes) {
					fo_eva.evaluateAttributes(node, solution, data,pw);
				}
				
				pw.close();
				pw2.close();
				pw3.close();
				
			}
			catch(Exception e) {
				System.out.println("Problem while printing the report");
				e.printStackTrace();
			}

	}
	
	public Solution readSolution(String solutionFileName,String dataFile,int replicate,double degree_of_dynamism, double arrival_rate,String distribution, int series, int number_workers) throws IOException {
		
		//Creates a solution:
		
			Solution sol = new Solution(dataFile);
			
		//Reads the solution file arcs:
			
			//0. Creates a buffered reader:
			
				BufferedReader buff = new BufferedReader(new FileReader(GlobalParameters.SOLUTIONS_FOLDER+solutionFileName));
		
			//1. Reads the header
				
				String line = buff.readLine();
			
			//2. Reads the routes:
				
				while(line != null) {
					String[] attrs = line.split(";");
					sol.getTails().add(Integer.parseInt(attrs[0]));
					sol.getHeads().add(Integer.parseInt(attrs[1]));
					sol.getTypes().add(Integer.parseInt(attrs[2]));
					if(!sol.getRoutes().contains(Integer.parseInt(attrs[3]))) {
						sol.getRoutesUnique().add(Integer.parseInt(attrs[3]));
					}
					sol.getRoutes().add(Integer.parseInt(attrs[3]));
					if(sol.getRoutes_visiting_customer().containsKey(Integer.parseInt(attrs[0])-1)) {
						if(!sol.getRoutes_visiting_customer().get(Integer.parseInt(attrs[0])-1).contains(Integer.parseInt(attrs[3]))) {
							sol.getRoutes_visiting_customer().get(Integer.parseInt(attrs[0])-1).add(Integer.parseInt(attrs[3]));
						}
					}else {
						sol.getRoutes_visiting_customer().put(Integer.parseInt(attrs[0])-1,new ArrayList<Integer>());
						sol.getRoutes_visiting_customer().get(Integer.parseInt(attrs[0])-1).add(Integer.parseInt(attrs[3]));
					}
					line = buff.readLine();
					
				}
				
				line = buff.readLine();
				
			
		
			//4. Closes the buffered reader
			
				buff.close();
			
		//Returns the solution:
			
			return(sol);
	}
	
	
	/**
	 * Creates the data handler
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public DataHandler readDataInfo(String dataFile,int replicate,double degree_of_dynamism, double arrival_rate,String distribution,int series,int number_workers ) throws IOException {
		
		//1.0 Creates a data handler
		
		DataHandler data = new DataHandler(dataFile,replicate,degree_of_dynamism,arrival_rate,distribution,series,number_workers);
		
		//1.4 Returns the data handler
		
		return(data);
	}
	
}

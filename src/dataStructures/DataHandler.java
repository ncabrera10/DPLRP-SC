package dataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import parameters.GlobalParameters;
import utilities.GeographicCalculator;

public class DataHandler {

	// Instance information:
	
	/**
	 * Instance data file
	 */
	
	private String instance_dataFile;
	
	/**
	 * ID of the seed used to generate the instance
	 */
	
	public int replicate;
	
	/**
	 * List containing all the scheduled tasks:
	 */
	public ArrayList<CustomerNode> scheduled_nodes;
	
	/**
	 * List containing all the on demand tasks:
	 */
	public ArrayList<CustomerNode> ondemand_nodes;
	
	/**
	 * List containing all the on tasks:
	 */
	public ArrayList<CustomerNode> customer_nodes;
	
	/**
	 * Array with all nodes
	 */
	public Node[] nodes;
	
	/**
	 * List of arcs
	 */
	public HashMap<String,Arc> arcs;
	
	/**
	 * A reference to the depot
	 */
	public StartDepot start_depot;
	
	/**
	 * Service times for each task
	 */
	private ArrayList<Double> service_times;
	
	/**
	 * Number of scheduled tasks
	 */
	
	public int number_scheduled = 0;
	
	/**
	 * Number of on-demand tasks
	 */
	
	public int number_ondemand = 0;
	
	/**
	 * Instance number of nodes
	 */
	
	public int number_nodes = 0;
	
	/**
	 * Number of walking arcs
	 */
	public int number_walking_arcs;
	
	/**
	 * Number of driving arcs
	 */
	public int number_driving_arcs;

	
	
	// Important information about the instance:
	
	/**
	 * Maximum duration for each route
	 */
	public static double route_duration_limit;
	
	/**
	 * Capacity of each route in terms of load
	 */
	public static double route_capacity;
	
	/**
	 * Maximum walking distance for each route
	 */
	public static double route_walking_distance_limit;

	/**
	 * Maximum duration for each walking subtour
	 */
	public static double subtour_duration_limit;
	
	/**
	 * Maximum walking distance between two points
	 */
	public static double max_walking_distance_bt2P;
	
	/**
	 * Walking speed 
	 */
	public static double walking_speed;
	
	/**
	 * Driving speed
	 */
	public static double driving_speed;
	
	/**
	 * Fixed cost
	 */
	public static double fixed_cost;
	
	/**
	 * Variable cost
	 */
	public static double variable_cost;
	
	/**
	 * Number of workers
	 */
	
	public static int number_of_workers;
	
	public  String distribution;
	
	public  int series;
	
	/**
	 * This method creates a DataHandler instance
	 * @param dataFile
	 * @param ty
	 * @param instanceType
	 * @param instanceNumber
	 * @param instanceSkills
	 * @param instanceLevels
	 * @param instanceVersion
	 * @throws IOException 
	 */
	public DataHandler(String dataFile,int replicate,double degree_of_dynamism, double arrival_rate,String distribution, int series, int number_workers ) throws IOException {
		
		// Initialize the main variables:
		
			this.instance_dataFile = dataFile;
			this.replicate = replicate;
			this.distribution = distribution;
			this.series = series;
			
		// Read the tasks information:
			
			readTasks();
			
		// Initialize main additional constraints:
			
			readMainConstraints();
			
		// Create the graph:
			
			createArcs();
	}
	
	
	
	/**
	 * This method reads the information of the tasks
	 * @throws IOException 
	 */
	public void readTasks() throws IOException {
		
		// Create the path to the instance file:
		
		String path_to_file = GlobalParameters.INSTANCE_FOLDER+"Instance_"+instance_dataFile;

	// Create a buffered reader:
		
		BufferedReader buff = new BufferedReader(new FileReader(path_to_file));

	// Read the nodes information:
		
		// Initialize the arrays to store the service times, the customer nodes, the scheduled nodes, and the on demand nodes:
		
		service_times = new ArrayList<Double>();
		customer_nodes = new ArrayList<CustomerNode>();
		scheduled_nodes = new ArrayList<CustomerNode>();
		ondemand_nodes = new ArrayList<CustomerNode>();
		
		// Read the txt file line by line:
		
			// Read the first line:
		
				String line = buff.readLine();
				line = buff.readLine(); //Skip the first line

			// Read the remaining lines:
				
				while(line != null && !line.isEmpty()) {
					
					// Recover the information of the current node:
					
						String[] parts = line.split(";");
						int id = Integer.parseInt(parts[0]);
						int original_id = Integer.parseInt(parts[3]);
						String type = parts[6];
						double coor_x = Double.parseDouble(parts[1]); //Longitude
						double coor_y = Double.parseDouble(parts[2]); //Latitude
						double service = Double.parseDouble(parts[4]); // Service time
						double early = Double.parseDouble(parts[7]); // Start of the time window
						double reward = 0;
						if(type.equals("OnDemand")) {
							reward = 1;
						}
						
					// Store the service time:
					
						service_times.add(service); 
					
					// If the depot:
						
					if (type.equals("Depot")) { // This means that we are reading the information of the depot:
						
						StartDepot node1 = new StartDepot(id-1,original_id,coor_x,coor_y,early,GlobalParameters.ROUTE_DURATION_LIMIT);
						start_depot = node1;
						number_nodes++;
						
					}else { //If a customer node:
						
						CustomerNode node = new CustomerNode(id-1,original_id,coor_x,coor_y,early,GlobalParameters.ROUTE_DURATION_LIMIT,service,type,reward);
						customer_nodes.add(node);
						number_nodes++;
						if(type.equals("Scheduled")) { //If scheduled
							number_scheduled++;
							scheduled_nodes.add(node);
						}else { // If on-demand
							number_ondemand++;
							ondemand_nodes.add(node);
							
						}
					}
					
					line = buff.readLine();
				}

		// Create an array for easy access to every node:
				
				nodes = new Node[number_nodes];
				
		// Store the start depot:
				
			nodes[0] = start_depot;
			
		// Store the customer nodes:
			
			for(CustomerNode node : customer_nodes) {
				nodes[node.id] = node;
			}
		
	// Close the buffered reader:
		
		buff.close();
	}
	
	
	
	
	/**
	 * This method reads the main constraints: 
	 */
	public void readMainConstraints() {
		
		driving_speed = GlobalParameters.DRIVING_SPEED; //km/h
		walking_speed = GlobalParameters.WALKING_SPEED; //km/h
		fixed_cost = GlobalParameters.FIXED_COST;
		variable_cost = GlobalParameters.VARIABLE_COST;
		max_walking_distance_bt2P = GlobalParameters.MAX_WD_B2P; //km 2.5
		subtour_duration_limit = GlobalParameters.SUBTOUR_TIME_LIMIT; //min
		route_walking_distance_limit = GlobalParameters.ROUTE_WALKING_DISTANCE_LIMIT; //km
		route_duration_limit = GlobalParameters.ROUTE_DURATION_LIMIT;
	}
	
	
	
	/**
	 * This method creates all the arcs.
	 * @throws IOException 
	 */
	public void createArcs() throws IOException {
			
		// Initializes the hashmap in which we will store all the arcs:
		
			arcs = new HashMap<String,Arc>();
			GeographicCalculator euc = new GeographicCalculator();
			
		// Driving arcs:
			
			// Arcs from the source depot to every customer node:
			
				for(CustomerNode node:customer_nodes) {
					
					double distance = euc.calc(start_depot.coor_x,start_depot.coor_y,node.coor_x, node.coor_y);
					double time = ((60 * distance) / GlobalParameters.DRIVING_SPEED) + GlobalParameters.PARKING_TIME_MIN;
					
					if(start_depot.tw_a + time <= node.tw_b) {
						
						arcs.put(start_depot.id+"-"+node.id, new Arc(start_depot,node,distance,distance*GlobalParameters.VARIABLE_COST,time,1));
						this.number_driving_arcs++;
					}
					
				}
				
				// Arcs from every customer to the source depot:
				
				for(CustomerNode node:customer_nodes) {
					
					double distance = euc.calc(node.coor_x, node.coor_y,start_depot.coor_x,start_depot.coor_y);
					double time = ((60 * distance) / GlobalParameters.DRIVING_SPEED);
					
					if(node.tw_a + time <= start_depot.tw_b) {
						
						arcs.put(node.id+"-"+start_depot.id, new Arc(node,start_depot,distance,distance*GlobalParameters.VARIABLE_COST,time,1));
						this.number_driving_arcs++;
					}
					
				}
				
			// Arcs from customers to other customers:
				
				for(CustomerNode node:customer_nodes) {
					
					for(CustomerNode node2:customer_nodes) {
						
						if(node.id != node2.id) {
							
							double distance =euc.calc(node.coor_x, node.coor_y,node2.coor_x,node2.coor_y);
							double time = ((60 * distance) / GlobalParameters.DRIVING_SPEED) + GlobalParameters.PARKING_TIME_MIN;	
							
							
							if(node.tw_a + node.service + time <= node2.tw_b) {
							
								arcs.put(node.id+"-"+node2.id, new Arc(node,node2,distance,distance*GlobalParameters.VARIABLE_COST,time,1));
								this.number_driving_arcs++;
							}
							
						}
						
					}
				}
				
		// Walking arcs :
				
			// Arcs from customers to other customers:
				
				for(CustomerNode node:customer_nodes) {
					
					for(CustomerNode node2:customer_nodes) {
						
						if(node.id != node2.id) {
							
							double distance =euc.calc(node.coor_x, node.coor_y,node2.coor_x,node2.coor_y);
							double time = ((60 * distance) / GlobalParameters.WALKING_SPEED);
							
							if(distance <= GlobalParameters.MAX_WD_B2P && node.tw_a + node.service + time <= node2.tw_b) {
							
								arcs.put(node.id+"_"+node2.id, new Arc(node,node2,distance,0,time,2));
								this.number_walking_arcs++;
							}
						}
						
					}
				}
					
	}	
	
	
	
}

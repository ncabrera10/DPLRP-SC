package dataStructures;

/**
 * This class represents an arc in the graph
 * @author nicolas.cabrera-malik
 *
 */
public class Arc {

	/**
	 * Tail of the arc
	 */
	private Node tail;
	
	/**
	 * Head of the arc
	 */
	private Node head;
	
	/**
	 * Distance of the arc
	 */
	private double distance;
	
	/**
	 * Cost of the arc
	 */
	private double cost;
	
	/**
	 * Time of the arc
	 */
	private double time;
	
	/**
	 * Current reduced cost of the arc
	 */
	
	private double reduced_cost;
	
	/**
	 * Sort criteria of the arc
	 */
	
	private double sort_criteria;
	
	/**
	 * Separator
	 */
	
	private String separator;
	
	/**
	 * Transportation mode of the arc: 1 Driving 2 Walking
	 */
	private int t_mode;
	
	/**
	 * Flow (arcs in the graph 1)
	 */
	
	private int flow;
	
	/**
	 * Creates an arc
	 * @param ta
	 * @param he
	 * @param dis
	 * @param co
	 * @param ti
	 * @param type
	 */
	public Arc(Node ta,Node he,double dis, double co, double ti,int type) {
		
		this.tail = ta;
		this.head = he;
		this.distance = dis;
		this.cost = co;
		this.reduced_cost = co;
		this.sort_criteria = dis;
		this.time = ti;
		this.t_mode = type;
		if(this.t_mode == 1) {
			this.separator = "-";
			tail.addOutGoingArc_drive(this);
		}else {
			this.separator = "_";
			tail.addOutGoingArc_walk(this);
		}
		tail.addOutGoingArc(this);
		flow = 1;
	}
	
	/**
	 * Creates an arc for the graph 1
	 * @param ta
	 * @param he
	 * @param dis
	 * @param co
	 * @param ti
	 * @param type
	 * @param fl
	 */
	public Arc(Node ta,Node he,double dis, double co, double ti,int type, int fl) {
		
		this.tail = ta;
		this.head = he;
		this.distance = dis;
		this.cost = co;
		this.reduced_cost = co;
		this.sort_criteria = dis;
		this.time = ti;
		this.t_mode = type;
		if(this.t_mode == 1) {
			this.separator = "-";
			tail.addOutGoingArc_drive(this);
		}else {
			this.separator = "_";
			tail.addOutGoingArc_walk(this);
		}
		tail.addOutGoingArc(this);
		flow = fl;
	}

	public String getKey() {
		return(tail.id+this.separator+head.id);
	}
	
	
	
	@Override
	public String toString() {
		return(tail.id+" - "+head.id+" - "+this.distance+" - "+this.getTime()+" - "+this.getCost()+" - "+this.getT_mode());
	}

	/**
	 * @return the tail
	 */
	public Node getTail() {
		return tail;
	}

	/**
	 * @param tail the tail to set
	 */
	public void setTail(Node tail) {
		this.tail = tail;
	}

	/**
	 * @return the head
	 */
	public Node getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(Node head) {
		this.head = head;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * @return the t_mode
	 */
	public int getT_mode() {
		return t_mode;
	}

	/**
	 * @param t_mode the t_mode to set
	 */
	public void setT_mode(int t_mode) {
		this.t_mode = t_mode;
	}

	/**
	 * @return the reduced_cost
	 */
	public double getReduced_cost() {
		return reduced_cost;
	}

	/**
	 * @param reduced_cost the reduced_cost to set
	 */
	public void setReduced_cost(double reduced_cost) {
		this.reduced_cost = reduced_cost;
	}

	/**
	 * @return the sort_criteria
	 */
	public double getSort_criteria() {
		return sort_criteria;
	}

	/**
	 * @param sort_criteria the sort_criteria to set
	 */
	public void setSort_criteria(double sort_criteria) {
		this.sort_criteria = sort_criteria;
	}

	/**
	 * @return the flow
	 */
	public int getFlow() {
		return flow;
	}

	/**
	 * @param flow the flow to set
	 */
	public void setFlow(int flow) {
		this.flow = flow;
	}
	
	
	
}


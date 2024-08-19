package evaluators;

import java.io.PrintWriter;

import constraints.Cons_MaxWalkDistance;
import constraints.Cons_NodeServed;
import constraints.Cons_RouteCapacity;
import constraints.Cons_RouteDuration;
import constraints.Cons_RouteTW;
import constraints.Cons_ServeAllScheduled;
import constraints.Cons_StartEndDepot;
import dataStructures.DataHandler;
import dataStructures.Solution;

public class ConstraintsEvaluator {

	public ConstraintsEvaluator() {
		
	}
	
	public boolean evaluateRouteWalkingDistance(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_MaxWalkDistance constraint = new Cons_MaxWalkDistance();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	public boolean evaluateRouteCapacity(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_RouteCapacity constraint = new Cons_RouteCapacity();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	public boolean evaluateRouteTW(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_RouteTW constraint = new Cons_RouteTW();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	public boolean evaluateRouteDuration(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_RouteDuration constraint = new Cons_RouteDuration();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	public boolean evaluateStartEndDepot(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_StartEndDepot constraint = new Cons_StartEndDepot();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	
	public boolean evaluateNodeServed(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_NodeServed constraint = new Cons_NodeServed();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
	
	public boolean evaluateServeAllScheduled(Solution solution, DataHandler data, int precision,PrintWriter pw) {
		Cons_ServeAllScheduled constraint = new Cons_ServeAllScheduled();
		return(constraint.checkConstraint(solution, data, true, precision,pw));
	}
}

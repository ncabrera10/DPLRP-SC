package dataStructures;

import java.util.ArrayList;

/**
 * This class represents an abstract node.
 * 
 * @author nicolas.cabrera-malik
 *
 */
public abstract class Node implements Cloneable {

	//Attributes:
	
	/**
	 * Id of the node - 1
	 */
	public int id;
	
	/**
	 * Id of the node in the complete graph
	 */
	public int original_id;
	
	/**
	 * X Coordinate
	 */
	public double coor_x;
	
	/**
	 * Y Coordinate
	 */
	public double coor_y;
	
	/**
	 * Early TW
	 */
	public double tw_a;
	 
	/**
	 * Late TW
	 */
	public double tw_b;
	
	/**
	 * Service time
	 */
	public double service;
	
	/**
	 * Reward of the task
	 */
	public double reward;
	
	/**
	 * Type of the node: Depot, Scheduled, OnDemand
	 */
	public String type;
	
	public int demand = 0;
	
	/**
	 * Outgoing driving arcs 
	 */
	ArrayList<Arc> forwardStar_drive;
	
	/**
	 * Outgoing walking arcs
	 */
	ArrayList<Arc> forwardStar_walk;
	
	/**
	 * All outgoing arcs
	 */
	ArrayList<Arc> forwardStar;
	
	// Methods:
	
	public String toString() {
		return (""+id);
	}
	
	
	/**
	 * @return the forwardStar
	 */
	public ArrayList<Arc> getForwardStar_drive() {
		return forwardStar_drive;
	}

	/**
	 * @param forwardStar the forwardStar to set
	 */
	public void setForwardStar_drive(ArrayList<Arc> forwardStar) {
		this.forwardStar_drive = forwardStar;
	}
	
	/**
	 * @return the forwardStar
	 */
	public ArrayList<Arc> getForwardStar_walk() {
		return forwardStar_drive;
	}

	/**
	 * @param forwardStar the forwardStar to set
	 */
	public void setForwardStar_walk(ArrayList<Arc> forwardStar) {
		this.forwardStar_walk = forwardStar;
	}
	
	/**
	 * Adds an outgoing arc (In order)
	 * @param a the arc
	 */
	public void addOutGoingArc_drive(Arc a){
		double cScore = a.getDistance();
		
		boolean cond = true;
		int l = 0; //Por izquierda
		int r = forwardStar_drive.size();
		int m = (int)((l+r)/2);
		double mVal = 0;
		if(forwardStar_drive.size() == 0){
			forwardStar_drive.add(a);
			return;
		}
		else if(forwardStar_drive.size() == 1){
			mVal = forwardStar_drive.get(m).getDistance();
			if(cScore <= mVal){
				forwardStar_drive.add(a);
			}else{
				forwardStar_drive.add(0, a);
			}
			return;
		}
		else{
			mVal = forwardStar_drive.get(m).getDistance();
		}
		while(cond){
			if(r-l>1){
				if(cScore < mVal){
					r = m;
					m = (int)((l+r)/2);
				}
				else if(cScore > mVal){
					l = m;
					m = (int)((l+r)/2);
				}else{
					forwardStar_drive.add(m,a);
					return;
				}
				mVal = forwardStar_drive.get(m).getDistance();
			}
			else{
				cond = false;
				if(l == m){
					if(cScore == mVal){
						forwardStar_drive.add(l+1,a);
					}else{
						forwardStar_drive.add(cScore<mVal?l:l+1, a);
					}
				}else if(r == m){
					if(cScore == mVal){
						forwardStar_drive.add(r+1,a);
					}else{
						forwardStar_drive.add(cScore<mVal?r:Math.min(r+1,forwardStar_drive.size()), a);
					}
				}
			}
		}
	}
	
	/**
	 * Adds an outgoing arc (In order)
	 * @param a the arc
	 */
	public void addOutGoingArc_walk(Arc a){
		double cScore = a.getDistance();
		
		boolean cond = true;
		int l = 0; //Por izquierda
		int r = forwardStar_walk.size();
		int m = (int)((l+r)/2);
		double mVal = 0;
		if(forwardStar_walk.size() == 0){
			forwardStar_walk.add(a);
			return;
		}
		else if(forwardStar_walk.size() == 1){
			mVal = forwardStar_walk.get(m).getDistance();
			if(cScore <= mVal){
				forwardStar_walk.add(a);
			}else{
				forwardStar_walk.add(0, a);
			}
			return;
		}
		else{
			mVal = forwardStar_walk.get(m).getDistance();
		}
		while(cond){
			if(r-l>1){
				if(cScore < mVal){
					r = m;
					m = (int)((l+r)/2);
				}
				else if(cScore > mVal){
					l = m;
					m = (int)((l+r)/2);
				}else{
					forwardStar_walk.add(m,a);
					return;
				}
				mVal = forwardStar_walk.get(m).getDistance();
			}
			else{
				cond = false;
				if(l == m){
					if(cScore == mVal){
						forwardStar_walk.add(l+1,a);
					}else{
						forwardStar_walk.add(cScore<mVal?l:l+1, a);
					}
				}else if(r == m){
					if(cScore == mVal){
						forwardStar_walk.add(r+1,a);
					}else{
						forwardStar_walk.add(cScore<mVal?r:Math.min(r+1,forwardStar_walk.size()), a);
					}
				}
			}
		}
	}
	
	/**
	 * Adds an outgoing arc (In order)
	 * @param a the arc
	 */
	public void addOutGoingArc(Arc a){
		double cScore = a.getDistance();
		
		boolean cond = true;
		int l = 0; //Por izquierda
		int r = forwardStar.size();
		int m = (int)((l+r)/2);
		double mVal = 0;
		if(forwardStar.size() == 0){
			forwardStar.add(a);
			return;
		}
		else if(forwardStar.size() == 1){
			mVal = forwardStar.get(m).getDistance();
			if(cScore <= mVal){
				forwardStar.add(a);
			}else{
				forwardStar.add(0, a);
			}
			return;
		}
		else{
			mVal = forwardStar.get(m).getDistance();
		}
		while(cond){
			if(r-l>1){
				if(cScore < mVal){
					r = m;
					m = (int)((l+r)/2);
				}
				else if(cScore > mVal){
					l = m;
					m = (int)((l+r)/2);
				}else{
					forwardStar.add(m,a);
					return;
				}
				mVal = forwardStar.get(m).getDistance();
			}
			else{
				cond = false;
				if(l == m){
					if(cScore == mVal){
						forwardStar.add(l+1,a);
					}else{
						forwardStar.add(cScore<mVal?l:l+1, a);
					}
				}else if(r == m){
					if(cScore == mVal){
						forwardStar.add(r+1,a);
					}else{
						forwardStar.add(cScore<mVal?r:Math.min(r+1,forwardStar.size()), a);
					}
				}
			}
		}
	}
	
	/**
	 * This method sorts the incoming arcs
	 * @param set
	 */
	protected void SortW(ArrayList<Arc> set) 
	{
		QSW(forwardStar_walk, 0, forwardStar_walk.size()-1);
	}
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 */
	public void QSW(ArrayList<Arc> e, int b, int t)
	{
		 int pivote;
	     if(b < t){
	        pivote=colocarW(e,b,t);
	        QSW(e,b,pivote-1);
	        QSW(e,pivote+1,t);
	     }  
	}
	
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 * @return
	 */
	public int colocarW(ArrayList<Arc> e, int b, int t)
	{
	    int i;
	    int pivote;
	    double valor_pivote;
	    Arc temp;
	    pivote = b;
	    valor_pivote = e.get(pivote).getSort_criteria();
	    for (i=b+1; i<=t; i++){
	        if (e.get(i).getSort_criteria() < valor_pivote){
	                pivote++;    
	                temp= e.get(i);
	                e.set(i, e.get(pivote));
	                e.set(pivote, temp);
	        }
	    }
	    temp=e.get(b);
	    e.set(b, e.get(pivote));
        e.set(pivote, temp);
	    return pivote;
	    
	}
	
	/**
	 * This method sorts the incoming arcs
	 * @param set
	 */
	protected void SortD(ArrayList<Arc> set) 
	{
		QSD(forwardStar_drive, 0, forwardStar_drive.size()-1);
	}
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 */
	public void QSD(ArrayList<Arc> e, int b, int t)
	{
		 int pivote;
	     if(b < t){
	        pivote=colocarD(e,b,t);
	        QSD(e,b,pivote-1);
	        QSD(e,pivote+1,t);
	     }  
	}
	
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 * @return
	 */
	public int colocarD(ArrayList<Arc> e, int b, int t)
	{
	    int i;
	    int pivote;
	    double valor_pivote;
	    Arc temp;
	    pivote = b;
	    valor_pivote = e.get(pivote).getSort_criteria();
	    for (i=b+1; i<=t; i++){
	        if (e.get(i).getSort_criteria() < valor_pivote){
	                pivote++;    
	                temp= e.get(i);
	                e.set(i, e.get(pivote));
	                e.set(pivote, temp);
	        }
	    }
	    temp=e.get(b);
	    e.set(b, e.get(pivote));
        e.set(pivote, temp);
	    return pivote;
	    
	}
	
	/**
	 * This method sorts the incoming arcs
	 * @param set
	 */
	protected void Sort(ArrayList<Arc> set) 
	{
		QS(forwardStar, 0, forwardStar.size()-1);
	}
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 */
	public void QS(ArrayList<Arc> e, int b, int t)
	{
		 int pivote;
	     if(b < t){
	        pivote=colocar(e,b,t);
	        QS(e,b,pivote-1);
	        QS(e,pivote+1,t);
	     }  
	}
	
	
	/**
	 * 
	 * @param e
	 * @param b
	 * @param t
	 * @return
	 */
	public int colocar(ArrayList<Arc> e, int b, int t)
	{
	    int i;
	    int pivote;
	    double valor_pivote;
	    Arc temp;
	    pivote = b;
	    valor_pivote = e.get(pivote).getSort_criteria();
	    for (i=b+1; i<=t; i++){
	        if (e.get(i).getSort_criteria() < valor_pivote){
	                pivote++;    
	                temp= e.get(i);
	                e.set(i, e.get(pivote));
	                e.set(pivote, temp);
	        }
	    }
	    temp=e.get(b);
	    e.set(b, e.get(pivote));
        e.set(pivote, temp);
	    return pivote;
	    
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}

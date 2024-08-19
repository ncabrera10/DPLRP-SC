package utilities;

/**
 * Implements a simple euclidean distance calculator
 * @author Jorge E. Mendoza (dev@jorge-mendoza.com)
 * @version %I%, %G%
 * @since Jan 8, 2015
 *
 */
public class GeographicCalculator implements DistanceCalculator{
	
	 private static final double r2d = 180.0D / 3.141592653589793D;
	 private static final double d2r = 3.141592653589793D / 180.0D;
	 private static final double d2km = 111189.57696D * r2d;
	 
	/**
	 * Computes the Euclidean distance between two points
	 * @param long1 the longitude of point 1
	 * @param lat1 the latitude of point 1
	 * @param long2 the longitude of point 2
	 * @param lat2 the latitude of point 2
	 * @return the distance between the two points in km
	 */
	public double calc(double ln1, double lt1, double ln2, double lt2){
		if(ln1 == ln2 && lt1 == lt2) {
			return 0.0;
		}else {
			 double x = lt1 * d2r;
			 double y = lt2 * d2r;
		     return (Math.acos( Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (ln1 - ln2))) * d2km)/1000.0;
		}
	}

	/**
	 * Computes a matrix of Euclidean distances from a coordinates matrix
	 * @param coordinates the coordinates long/lat
	 * @return the distance matrix
	 */
	public double[][] calc(double[][] coordinates){
		if(coordinates[0].length!=2)
			throw new IllegalArgumentException("argument coordinates must be a matrix with 2 columns and an open number of files");
		
		double[][] matrix=new double[coordinates.length][coordinates.length];
		for(int i=0; i< coordinates.length; i++){
			for(int j=i+1; j<coordinates.length;j++){
				matrix[i][j]=calc(coordinates[i][0],coordinates[i][1],coordinates[j][0],coordinates[j][1]);
				matrix[j][i]=matrix[i][j];
			}
		}
		return matrix;
	}

}

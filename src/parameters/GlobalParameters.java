package parameters;

public class GlobalParameters {



	public static final String INSTANCE_FOLDER = GlobalParametersReader.<String>get("INSTANCE_FOLDER", String.class);
	public static final String RESULT_FOLDER = GlobalParametersReader.<String>get("RESULT_FOLDER", String.class);
	public static final String SOLUTIONS_FOLDER = GlobalParametersReader.<String>get("SOLUTIONS_FOLDER", String.class);
	public static final String ALGORITHM_NAME = GlobalParametersReader.<String>get("ALGORITHM_NAME", String.class);

	public static final int PRECISION = GlobalParametersReader.<Integer>get("PRECISION", Integer.class);
	public static final double DECIMAL_PRECISION = Math.pow(10, -PRECISION);

	/**
	 * Driving speed in km/h
	 */

	public static int DRIVING_SPEED = GlobalParametersReader.<Integer>get("DRIVING_SPEED", Integer.class);
	
	/**
	 * Walking speed in km/h
	 */

	public static int WALKING_SPEED = GlobalParametersReader.<Integer>get("WALKING_SPEED", Integer.class);
	
	/**
	 * Fixed cost
	 */

	public static int FIXED_COST = GlobalParametersReader.<Integer>get("FIXED_COST", Integer.class);
	
	/**
	 * Variable cost
	 */

	public static int VARIABLE_COST = GlobalParametersReader.<Integer>get("VARIABLE_COST", Integer.class);
	
	/**
	 * Max walking distance between two points
	 */

	public static double MAX_WD_B2P = GlobalParametersReader.<Double>get("MAX_WD_B2P", Double.class);
	
	/**
	 * Time limit for each subtour
	 */

	public static int SUBTOUR_TIME_LIMIT = GlobalParametersReader.<Integer>get("SUBTOUR_TIME_LIMIT", Integer.class);
	
	/**
	 * Walking distance limit for each route
	 */

	public static double ROUTE_WALKING_DISTANCE_LIMIT = GlobalParametersReader.<Double>get("ROUTE_WALKING_DISTANCE_LIMIT", Double.class);
	
	/**
	 * Time limit for each route
	 */

	public static double ROUTE_DURATION_LIMIT = GlobalParametersReader.<Double>get("ROUTE_DURATION_LIMIT", Double.class);
	
	/**
	 * Parking time 
	 */

	public static double PARKING_TIME_MIN = GlobalParametersReader.<Double>get("PARKING_TIME_MIN", Double.class);
	
}

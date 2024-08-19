package dataStructures;

/**
 * This class represents a worker profile
 * @author nicolas.cabrera-malik
 *
 */
public class Profile {

	/**
	 * Profile id
	 */
	public int id;
	
	/**
	 * Skills/levels of this profile
	 */
	public String skills_key;
	
	/**
	 * This method creates a new profile
	 * @param i
	 * @param key
	 */
	public Profile(int i, String key) {
		
		id = i;
		skills_key = key;
		
	}
	
}

/**
 * 
 */
package br.unicamp.cst.core.entities;

/**
 * @author andre
 *
 */
public interface Memory {
	
	/**
	 * 
	 * @return the info in memory
	 */
	Object getI();
	
	/**
	 * 
	 * @param info the updated info to set in memory
	 * @return index of the memory inside the container or -1 if not a container
	 */
	int setI(Object info);
	
	
	/**
	 * 
	 * @return the evaluation of this memory
	 */
	Double getEvaluation();

	
	/**
	 * 
	 * @return the type of the memory
	 */
	String getName();

	/**
	 * 
	 * @param eval
	 */
	void setEvaluation(Double eval);

}

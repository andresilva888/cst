/*******************************************************************************
 * Copyright (c) 2012 K. Raizer, A. L. O. Paraense, R. R. Gudwin.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     K. Raizer, A. L. O. Paraense, R. R. Gudwin - initial API and implementation
 ******************************************************************************/
package br.unicamp.cst.centralExecutive.behaviornetwork;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.MemoryObjectTypesCore;
import br.unicamp.cst.core.entities.RawMemory;
import br.unicamp.cst.memory.WorkingStorage;
/**
 * This class is used as synchronized communication layer between raw action selection mechanism and all unconscious behaviors.
 * The motivation for its creation was making the process of avoiding deadlocks easier.
 * @author klaus
 *
 */
public class BgBComLayer {
	private ArrayList<MemoryObject> bgToBehaviors = new ArrayList<MemoryObject>();
	private ArrayList<MemoryObject> behaviorsToBg = new ArrayList<MemoryObject>();
	private boolean debugMode=false;
	public BgBComLayer(){
//		System.out.println("BgBComLayer empty constructor");
	}
	
	
	/**
	* Singleton instance
	*/
	private static BgBComLayer instance;

	/**
	* 
	* @return the singleton instance of Working Storage
	*/
	public synchronized static BgBComLayer getInstance()
	{
	  if(instance==null)
	  {
	     instance = new BgBComLayer();         
	  }

	  return instance;
	}

	/**
	* 
	* Avoids cloning
	*/
	public Object clone() throws CloneNotSupportedException{
		   throw new CloneNotSupportedException();
	}
	
	/**
	 * This method adds a new behavior state to behaviorsToBg list in case the isn't already a similar one.
	 * If it finds a memory object with the same type and information, it doesn't write it.
	 * If it finds a similar MO (same type and same name) but with different information, it simply updates the information
	 * NOTE: If the passed bs is not used, it should be removed from raw memory to save memory (would this be a problem?)
	 * @param bs memory object to be added to behaviorsToBg
	 */
	public synchronized void writeBehaviorState(MemoryObject bs){
		try {
			JSONObject bsInfo = new JSONObject(bs.getInfo());
			MemoryObject oldMO=new MemoryObject();
			
			boolean alreadyThere=false;
			boolean sameInfo=false;
			for(MemoryObject mo:this.behaviorsToBg){
				try {
					JSONObject moInfo = new JSONObject(mo.getInfo());
					
					if(moInfo.get("NAME").equals(bsInfo.get("NAME"))){
						alreadyThere=true;
						oldMO=mo;
						if(mo.getInfo().equals(bs.getInfo())){
							sameInfo=true;
						}
						break;
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(alreadyThere && sameInfo){// Remove MO from raw memory and discard it
				RawMemory.getInstance().destroyMemoryObject(bs);
			}else if(alreadyThere && !sameInfo){//Update info from old MO and discard de unused new one
				oldMO.setInfo(bs.getInfo());  //TODO I might need to change this in the future in case we implement a memory decay based on time
				RawMemory.getInstance().destroyMemoryObject(bs);
			}else{//Simply add it to the list
				this.behaviorsToBg.add(bs);
			}
		
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(debugMode==true){
			showBgBContent();
		}
	}
	/**
	 * This method adds a new behavior state to bgToBehaviors list in case the isn't already a similar one.
	 * If it finds a memory object with the same type and information, it doesn't write it.
	 * If it finds a similar MO (same type and same name) but with different information, it simply updates the information
	 * @param bgI memory object to be added to bgToBehaviors
	 */
	public synchronized void writeBGInstruction(MemoryObject bgI){
//		try {
//			JSONObject bsInfo = new JSONObject(bgI.getInfo());
			MemoryObject oldMO=new MemoryObject();
			
			boolean alreadyThere=false;
			boolean sameInfo=false;
			for(MemoryObject mo:this.bgToBehaviors){
//				try {
//					JSONObject moInfo = new JSONObject(mo.getInfo());
//					
					if((mo.getType().equals(bgI.getType()))&&(mo.getInfo().equals(bgI.getInfo()))){
						alreadyThere=true;
						oldMO=mo;
						if(mo.getInfo().equals(bgI.getInfo())){
							sameInfo=true;
						}
						break;
					}
					
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			
			if(alreadyThere && sameInfo){// Remove MO from raw memory and discard it
				RawMemory.getInstance().destroyMemoryObject(bgI);
			}else if(alreadyThere && !sameInfo){//Update info from old MO and discard de unused new one
				oldMO.setInfo(bgI.getInfo());  //TODO I might need to change this in the future in case we implement a memory decay based on time
				RawMemory.getInstance().destroyMemoryObject(bgI);
			}else{//Simply add it to the list
				this.bgToBehaviors.add(bgI);
			}
		
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
			if(debugMode==true){
				showBgBContent();
			}
	}
	/**
	 * 
	 * @return the list of behavior states stored in this object
	 */
	public synchronized List<MemoryObject> readBehaviorStates(){
		List<MemoryObject> behaviorStateList=new ArrayList<MemoryObject>();

		for(MemoryObject mo:this.behaviorsToBg){
			if(mo.getType()==MemoryObjectTypesCore.BEHAVIOR_STATE){
				behaviorStateList.add(mo);
			}
		}
		
		
		return behaviorStateList;
		
	}
	/**
	 * 
	 * @return the list of instructions from BG stored in this object
	 */
	public synchronized List<MemoryObject> readBGInstructions(){
		List<MemoryObject> bGInstructionsList = new ArrayList<MemoryObject>();
		
//		for(MemoryObject mo:this.bgToBehaviors){//TODO This might be unnecessary
//			if((mo.getType()==MemoryObjectTypesCore.BG_SHOULD_ACTIVATE)||(mo.getType()==MemoryObjectTypes.TEMP_THETA.ordinal())){
//				bGInstructionsList.add(mo);
//			}
//		}
		
		return bGInstructionsList;
		
	}

	public synchronized void showBgBContent(){
		System.out.println("------------------");
		System.out.println("-> bgToBehaviors: "+bgToBehaviors);
		System.out.println("-> behaviorsToBg: "+behaviorsToBg);
		System.out.println("------------------");
	}
	
}
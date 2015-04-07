package main.java.rest; 

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList; 

import org.json.JSONException;

import main.java.rest.RestMethods; 

public class RoomManagerRestMethods {

	/** [J.C]
	 * @return This method get all rooms in existence in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getAllRooms() throws JSONException, MalformedURLException, 
	IOException {
		RestMethods getRooms = new RestMethods(); 
		return getRooms.response("rooms", "displayName", "String"); 
	}
	
	/** [J.C]
	 * @return This method get all displayName rooms in existence in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getAllDisplayNameRooms() throws JSONException, 
	MalformedURLException, IOException {
		RestMethods getRooms = new RestMethods(); 
		return getRooms.response("rooms", "customDisplayName", "String"); 
	}

	/** [J.C]
	 * @return This method create association between a room and resource
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void createResource(String fileJSON, String authentication) throws 
	MalformedURLException, IOException {
		RestMethods createAssociatedResource = new RestMethods();
		createAssociatedResource.postMethod("resources",fileJSON, authentication);
	}

	/** [J.C]
	 * @return This method return a names of Associated resources to a Room in a 
	 * LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 * 
	 */
	public static LinkedList <String> getNamesAssociatedResources(String roomName) throws 
	JSONException, MalformedURLException, IOException {
		LinkedList <String> listResourcesRoomIds, listAllIdResources, listAllResourceNames;
		LinkedList <String> listNames = new LinkedList <String>(); 
		RestMethods getResourcesID = new RestMethods();
		RestMethods getRoomID = new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", 
				"displayName", roomName, "_id");
		String resourceAssociated = "rooms/" + roomID + "/resources";
		listResourcesRoomIds = getResourcesID.
				response(resourceAssociated, "resourceId","String");
		listAllIdResources = getAllIdResources();
		listAllResourceNames = getAllNameResources();
		for(int pos = 0; pos<listResourcesRoomIds.size(); pos ++) {
			for(int iterator = 0; iterator < listAllIdResources.size(); iterator ++ ) {
				if(listResourcesRoomIds.get(pos).equals(listAllIdResources.get(iterator))) {
					listNames.add(listAllResourceNames.get(iterator));
				}
			}
		}
		return listNames;
	}

	/** [J.C]
	 * @return This method return Name of rooms in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getRoomsByName(String Criteria) throws JSONException, 
	MalformedURLException, IOException{
		LinkedList <String> listAllRooms = getAllRooms();
		LinkedList <String> listRooms = new LinkedList <String>(); 
		for(int pos = 0; pos < listAllRooms.size(); pos ++) {
			if(listAllRooms.get(pos).contains(Criteria))
				listRooms.add(listAllRooms.get(pos));
		}
		return listRooms;
	}
	
	/** [J.C]
	 * @return This method return a names of rooms that have certain Associated resource 
	 * in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 * 
	 */
	public static LinkedList <String> getRoomNamesByResource(String ResourceName) 
			throws JSONException, MalformedURLException, IOException {
		LinkedList <String> listResourceNames, listRoomNames;
		LinkedList <String> listRoomswithResource = new LinkedList <String>(); 
		listRoomNames = getAllRooms();
		for(int pos = 0; pos < listRoomNames.size(); pos ++) {
			listResourceNames = getNamesAssociatedResources(listRoomNames.get(pos));
			for(int iter = 0; iter < listResourceNames.size(); iter ++) {
				if(listResourceNames.get(iter) == ResourceName) {
					listRoomswithResource.add(listRoomNames.get(pos));
					iter=listResourceNames.size();
				}
			}
		}
		return listRoomswithResource;
	}
	
	/** [J.C]
	 * @return This method return id of resources in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getAllIdResources() throws JSONException, 
	MalformedURLException, IOException {
		RestMethods getResources = new RestMethods(); 
		return getResources.response("resources", "_id", "String"); 
	}

	/** [J.C]
	 * @return This method return name of resources in a LinkedList <String> 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getAllNameResources() throws JSONException, 
	MalformedURLException, IOException {
		RestMethods getResources = new RestMethods(); 
		return getResources.response("resources", "customName", "String"); 
	}

	/** [J.C]
	 * @return This method delete a resource give a name
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void deleteResource(String resourceName) throws MalformedURLException, IOException {
		RestMethods getResourceID = new RestMethods(); 
		RestMethods deleteResource = new RestMethods(); 
		deleteResource.getOrDeleteMethods("DELETE", "resources/" + getResourceID
				.findAttributeValue("resources", "String", "name", resourceName, "_id")); 
	}

	/** [J.C]
	 * @return This method delete the association between a room and resource
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void deleteAssociatedResource(String roomName, String resourceName) 
			throws MalformedURLException, IOException{
		RestMethods getResourceID = new RestMethods(); 
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getResourceAssociatedID = new RestMethods(); 
		RestMethods deleteAssociatedResource = new RestMethods(); 
		String resourceID = getResourceID.findAttributeValue
				("resources", "String", "name", resourceName, "_id");
		String roomID = getRoomID.findAttributeValue("rooms", "String", 
				"displayName", roomName, "_id");
		String resourcesAssociated = "rooms/" + roomID + "/resources";
		String resourceAssociatedID = getResourceAssociatedID.findAttributeValue
				(resourcesAssociated, "String", "resourceId", resourceID, "_id");
		deleteAssociatedResource.getOrDeleteMethods
		("DELETE", resourcesAssociated + "/" + resourceAssociatedID); 
	}
	
	/** [J.C]
	 * @return This method create an OutOfOrderInfo in a room 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void createOutOfOrderInfo(String roomName, String fileJSON, String authentication) 
			throws MalformedURLException, IOException {
		RestMethods getRoomID = new RestMethods(); 
		RestMethods createAssociatedResource = new RestMethods();
		String roomID = getRoomID.findAttributeValue
				("rooms", "String", "displayName", roomName, "_id");
		String resourceAssociated = "rooms/" + roomID + "/out-of-orders";
		createAssociatedResource.postMethod(resourceAssociated, fileJSON, authentication);
	}
	
	/** [J.C]
	 * @return This method obtain info of an OutOfOrder selected
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static String getOutOfOrderInfo(String roomName, String title, String attribute) 
			throws JSONException, MalformedURLException, IOException{
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getoutOfOrderInfo = new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", 
				"displayName", roomName, "_id"); 
		String outOfOrders = "rooms/" + roomID + "/out-of-orders"; 
		if(attribute=="sendEmail") {
			return getoutOfOrderInfo.findAttributeValue(outOfOrders, "Boolean", 
					"title", title, attribute);
		}
		return getoutOfOrderInfo.findAttributeValue(outOfOrders, "String", 
				"title", title, attribute);
	}

	/** [J.C]
	 * @return This method delete An OutOfOrder
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void deleteOutOfOrder(String roomName, String title) throws MalformedURLException, 
	IOException{
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getoutOfOrderID = new RestMethods(); 
		RestMethods deleteOutOfOrder =  new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", 
				"displayName", roomName, "_id"); 
		String outOfOrders = "rooms/" + roomID + "/out-of-orders"; 
		String outOfOrderID = getoutOfOrderID.findAttributeValue
				(outOfOrders, "String", "title", title, "_id"); 
		deleteOutOfOrder.getOrDeleteMethods("DELETE", outOfOrders + "/" + outOfOrderID); 
	}

	/** [J.C]
	 * @return This method create A meeting 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void createMeeting(String roomName, String filePath, String authentication) 
			throws MalformedURLException, IOException{
		RestMethods getServicesID = new RestMethods();
		RestMethods getRoomID = new RestMethods();
		RestMethods createMeeting = new RestMethods();
		String serviceID = getServicesID.findAttributeValue("services", "String",
				"type", "exchange", "_id"); 
		String roomID = getRoomID.findAttributeValue("rooms","String", "displayName", 
				roomName, "_id");
		String meetings = "services/" + serviceID + "/rooms/" + roomID + "/meetings"; 
		createMeeting.postMethod(meetings, filePath, authentication); 
	}
	
	/** [J.C]
	 * @return This method delete A meeting created
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void deleteMeeting(String roomName, String meetingSubject, String authentication) 
			throws MalformedURLException, IOException {
		RestMethods getServicesID = new RestMethods();
		RestMethods getRoomID = new RestMethods();
		RestMethods getMeetingID = new RestMethods();
		RestMethods deleteMeeting = new RestMethods();
		String serviceID = getServicesID.findAttributeValue("services", "String",
				"type", "exchange", "_id"); 
		String roomID = getRoomID.findAttributeValue("rooms","String", "displayName", 
				roomName, "_id");
		String meetings = "services/" + serviceID + "/rooms/" + roomID + "/meetings";
		String meetingID = getMeetingID.findAttributeValue(meetings, "String", "title", 
				meetingSubject, "_id"); 
		System.out.println(meetings + "/" + meetingID);
		deleteMeeting.deleteAuthenticatedMethod(meetings + "/" + meetingID, authentication); 
	}

	/** [J.C]
	 * * 
	 * @param feature : Refers to the feature (i.e. rooms, resources, etc.)
	 * @param attribute : Refers to the attribute of feature 
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @param attributeValue : Refers to the value of attribute 
	 * (i.e. If attribute is displayName,attributeValue could be Room 1)
	 * @param attributeSearched : Refers to the attribute of feature that you search
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @return This method obtain a list of elements by condition selected
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static LinkedList <String> getListByNumeric(String feature, String attribute, 
			String attributeValue, String attributeSearched) throws JSONException, 
			MalformedURLException, IOException {
		RestMethods restConds = new RestMethods(); 
		LinkedList <String> list =  new LinkedList <String>(); 
		LinkedList <String> listAttributes = restConds.response(feature,"Int", attribute); 
		LinkedList <String> listAttSearched = restConds.response(feature,"String", 
				attributeSearched);
		for(int iterator = 0; iterator < listAttSearched.size(); iterator ++ ) {
			if(listAttributes.get(iterator).equals(attributeValue)) {
				list.add(listAttSearched.get(iterator)); 
			}
		}
		return list; 
	}

	/** [J.C]
	 * @return This method obtain a list of common elements between two lists
	 */
	public static LinkedList <String> mergeLists(LinkedList <String> list1, 
			LinkedList <String> list2) {
		LinkedList <String> list = new LinkedList <String>();
		for(int iter1 = 0;iter1 < list1.size();iter1 ++ ) {
			for(int iter2 = 0;iter2 < list2.size();iter2 ++ ) {
				if(list1.get(iter1).equals(list2.get(iter2))) {
					list.add(list1.get(iter1));
				}
			}
		}
		return list;
	}
	
	/**
	 * [YA] This method verifies if Out Of Order is activated 
	 * @param roomName
	 * @param title
	 * @return boolean
	 */
	public static boolean isOutOfOrderEnable(String roomName, String title) {
		try {
			getOutOfOrderInfo(roomName, title, "meetingId");
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * [YA] This method verifies if OutOf Order was created
	 * @param roomName
	 * @param title
	 * @return boolean
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static boolean isOutOfOrderCreated(String roomName, String title) throws JSONException, MalformedURLException, IOException {
		String outOfOrdertitle = getOutOfOrderInfo(roomName, title, "title");
		if(outOfOrdertitle == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * [RB]This method use the rootRestClass to verify the made changes
	 * @return true if the roomDisplay was modified 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JSONException 
	 */
	public static boolean verifyChangesMade(String ChangedDisplayName) throws JSONException, MalformedURLException, IOException {
		boolean flag = false;
		for (String displayName : getAllDisplayNameRooms()) {
			if (ChangedDisplayName.equals(displayName)) {
				flag = true;
			}
		}
		return flag;
	}
}

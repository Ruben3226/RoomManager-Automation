package main.java.utils;

/**
 * This class contains all messages for Room Manager
 * @author Yesica Acha
 *
 */
public final class MessageConstants {

	//The following messages are for Out Of Order
	public static final String OUT_OF_ORDER_SUCCESSFULLY_CREATED = "Out of order was created successfully";
	public static final String OUT_OF_ORDER_DEACTIVATED = "Out of order was deactivated";
	public static final String OUT_OF_ORDER_IN_THE_PAST = "Cannot establish out of order as a past event";
	public static final String TO_GRATER_THAN_FROM = "field must be greater than";
	public static final String OUT_OF_ORDER_SHOULD_HAVE_A_TITLE = "Out of order should have a title";

	//The following messages are for meetings
	public static final String MEETING_CREATED = "Meeting successfully created"; 
	public static final String MEETING_REMOVED = "Meeting successfully removed";
	public static final String MEETING_UPDATED = "Meeting successfully updated";
	public static final String MEETING_CREATE_ERROR = "Cannot create meeting, it has conflicts with other meeting";
	public static final String MEETING_UPDATE_ERROR = "Cannot update meeting, it has conflicts with other meeting"; 
	public static final String MEETING_ERROR = "Ooops, something bad happened";
	public static final String MEETING_SUBJECT_REQUIERED = "Subject is required";
	public static final String MEETING_ORGANIZER_REQUIRED = "Organizer is required";
	public static final String MEETING_ATTENDEES_REQUIRED = "Attendees are required";
	public static final String MEETING_TIME_STARTEND = "Start time must be smaller than end time";
	public static final String MEETING_ATTENDEES_INVALID = "Invalid attendees";
	public static final String MEETING_PAST_CREATED_INFORMATION = "This meeting request ocurrs in the past.";
	
	//The following messages are for RoomInfoPage
	public static final String ROOM_DISPLAY_NAME_EMPTY = "Display Name must not be empty";
	public static final String INVALID_ROOM_LOCATION = "No matches found";
	
	//The following messages are for ResourcesPage
	public static final String RESOURCE_NAME_DUPLICATED = "A resource with the same name already exists, please choose another name";
	public static final String RESOURCE_NAME_TEXTBOX_EMPTY = "Name must not be empty";
	public static final String RESOURCE_DISPLAY_NAME_TEXTBOX_EMPTY = "Display name must not be empty";
	
	
	
}

package com.debugger.downloader.constants;

public class Constants {


    public static final String TO_FRAGMENT = "TO_FRAGMENT";
    public static final String TO_ACTIVITY = "TO_ACTIVITY";





    ///////////////////////////////////////////////////////////////////



    public static class FragmentNavigation {

        public static final String FRAGMENT_COMMENT = "FRAGMENT_COMMENT";
        public static final String FRAGMENT_SEARCH_PEOPLE = "FRAGMENT_SEARCH_PEOPLE";
        public static final String FRAGMENT_MESSAGE = "FRAGMENT_MESSAGE";
        public static final String FRAGMENT_CREATE_CHECKIN = "FRAGMENT_CREATE_CHECKIN";
        public static final String FRAGMENT_REQUEST = "FRAGMENT_REQUEST";
        public static final String FRAGMENT_CHAT = "FRAGMENT_CHAT";
        public static final String FRAGMENT_TRACK_FRIEND = "FRAGMENT_TRACK_FRIEND";
        public static final String FRAGMENT_EDIT_CHECKIN = "FRAGMENT_EDIT_CHECKIN";


    }
    public static class ActivityNavigation {

        public static final String ACTIVITY_DRAWING = "ACTIVITY_DRAWING";


    }
 public static class APILINKS {

        public static final String FIREBASE_EMAIL = "https://us-central1-veltrotec-5a165.cloudfunctions.net/sendMail?";

        public static final String FIREBASE_DELETE_CHECK_IN = "https://us-central1-sighted-258213.cloudfunctions.net/deleteCheckIn?";
        public static final String FIREBASE_SUBSCRIBE_CHECK_IN = "https://us-central1-sighted-258213.cloudfunctions.net/subscribeCheckIns?";
        public static final String FIREBASE_SEND_CHECK_IN_UPDATES = "https://us-central1-sighted-258213.cloudfunctions.net/sendCheckInUpdates?";
        public static final String FIREBASE_ACCEPTED_REQUEST_UPDATE = "https://us-central1-sighted-258213.cloudfunctions.net/sendRequestAcceptNotification?";
        public static final String FIREBASE_SEND_REQUEST = "https://us-central1-sighted-258213.cloudfunctions.net/sendRequestNotification?";
        public static final String FIREBASE_UPDATE_PROFILE = "https://us-central1-sighted-258213.cloudfunctions.net/updateProfile?";
        public static final String FIREBASE_GET_CHECKIN = "https://us-central1-sighted-258213.cloudfunctions.net/getPosts?";
        public static final String FIREBASE_GET_MESSAGES = "https://us-central1-sighted-258213.cloudfunctions.net/getMessages?";
        public static final String FIREBASE_MESSAGE_NOTIFICATION = "https://us-central1-sighted-258213.cloudfunctions.net/messageNotifications?";
        public static final String FIREBASE_LOCATION_NOTIFICATION = "https://us-central1-sighted-258213.cloudfunctions.net/trackLocation?";


    }
    public static class NewsFeedParams
    {
        public static final String id = "id";
        public static final String checkInUUID = "checkInUUID";

    }


    public enum FragmentAnimation {
        SLIDE_UP, SLIDE_DOWN, SLIDE_RIGHT, SLIDE_LEFT, NONE
    }

}

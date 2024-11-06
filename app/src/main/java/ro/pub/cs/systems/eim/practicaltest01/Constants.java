package ro.pub.cs.systems.eim.practicaltest01;

public class Constants {

    public static final String PROCESSING_THREAD_TAG = "ProcessingThread";
    public static final String BROADCAST_RECEIVER_EXTRA = "MESSAGE";

    public static final String FIRST_NUMBER = "FIRST_NUMBER";
    public static final String SECOND_NUMBER = "SECOND_NUMBER";

    public static final String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.action1",
            "ro.pub.cs.systems.eim.practicaltest01.action2",
            "ro.pub.cs.systems.eim.practicaltest01.action3"
    };

    public static final int NUMBER_OF_CLICKS_THRESHOLD = 10;
    public static final int SERVICE_STARTED = 1;
    public static final int SERVICE_STOPPED = 0;

    public static final String BROADCAST_RECEIVER_TAG = "BroadcastReceiver";

}

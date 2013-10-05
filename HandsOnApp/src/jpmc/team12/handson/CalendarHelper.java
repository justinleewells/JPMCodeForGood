package jpmc.team12.handson;

import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.text.format.Time;
import android.util.Log;

public class CalendarHelper {

	public static void addToCalendar(Context context, Calendar beginTime,
			Calendar endTime, String title, String description, String place) {
		long startMillis = 0;
		long endMillis = 0;

		Intent intent = new Intent(Intent.ACTION_INSERT)
				.setType("vnd.android.cursor.item/event")
				.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
				.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
				.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
				.putExtra(Events.TITLE, title)
				.putExtra(Events.DESCRIPTION, description)
				.putExtra(Events.EVENT_LOCATION, place)
				.putExtra(Events.RRULE, "FREQ=DAILY;COUNT=1")
				.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
				.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
		context.startActivity(intent);
	}

	public static void addToCalendarAuto(Context context, int stTime,
			int enTime, String category, String description, String place,
			String tZone) {
		ContentResolver cr = context.getContentResolver();

		// Get calendar ID
		Uri uri = CalendarContract.Calendars.CONTENT_URI;
		String[] projection = new String[] { CalendarContract.Calendars._ID,
				CalendarContract.Calendars.ACCOUNT_NAME,
				CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
				CalendarContract.Calendars.NAME,
				CalendarContract.Calendars.CALENDAR_COLOR };

		Cursor calendarCursor = cr.query(uri, projection, null, null, null);
		// calendarCursor.moveToFirst();
		while (calendarCursor.moveToNext() != false) {
			Log.e("CATS", calendarCursor.getString(3));
		}
		int calId = calendarCursor.getInt(0);

		// Construct event details
		long startMillis = 0;
		long endMillis = 0;
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(2013, 10, 14, 7, 30);
		startMillis = beginTime.getTimeInMillis();
		Calendar endTime = Calendar.getInstance();
		endTime.set(2013, 10, 14, 8, 45);
		endMillis = endTime.getTimeInMillis();

		// Insert Event
		ContentValues values = new ContentValues();
		values.put(CalendarContract.Events.DTSTART, startMillis);
		values.put(CalendarContract.Events.DTEND, endMillis);
		values.put(CalendarContract.Events.TITLE, "Walk The Dog");
		values.put(CalendarContract.Events.DESCRIPTION,
				"My dog is bored, so we're going on a really long walk!");
		values.put(CalendarContract.Events.CALENDAR_ID, calId);
		values.put(CalendarContract.Events.EVENT_TIMEZONE,
				Time.getCurrentTimezone());
		uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

		// Retrieve ID for new event
		String eventID = uri.getLastPathSegment();
	}
}

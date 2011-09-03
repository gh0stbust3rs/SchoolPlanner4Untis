
package edu.htl3r.schoolplanner.gui.basti.GUIData;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;
import edu.htl3r.schoolplanner.DateTime;
import edu.htl3r.schoolplanner.DateTimeUtils;
import edu.htl3r.schoolplanner.backend.network.WebUntis;
import edu.htl3r.schoolplanner.backend.schoolObjects.SchoolHoliday;
import edu.htl3r.schoolplanner.backend.schoolObjects.ViewType;
import edu.htl3r.schoolplanner.backend.schoolObjects.lesson.Lesson;
import edu.htl3r.schoolplanner.backend.schoolObjects.timegrid.Timegrid;
import edu.htl3r.schoolplanner.backend.schoolObjects.timegrid.TimegridUnit;

public class RenderInfoWeekTable {

	private Map<String,List<Lesson>> weekdata ;
	
	private boolean dispsat, dispdate, dispweekdaynames, dispzerolesson;
	private Timegrid timegrid;
	private List<SchoolHoliday> holidays;
	private ViewType viewtype;
	
	
	public RenderInfoWeekTable(){
		
		//TODO Lese das aus den Prefernces aus!!!!
		dispsat = true;
		dispdate = true;
		dispweekdaynames = true;
		dispzerolesson = true;
	}
	
	
	public void setWeekData(Map<String,List<Lesson>> w){
		weekdata = w;
	}
	
	public void setHolidays(List<SchoolHoliday> hol){
		holidays = hol;
	}
	
	public void setTimeGrid(Timegrid t){
		timegrid = t;
	}
	
	public void setViewType(ViewType vt){
		viewtype = vt;
	}
	
	public GUIWeek analyse(){
		//TODO Alles da?
		Set<String> keySet = weekdata.keySet();
		TreeSet<DateTime> dates = new TreeSet<DateTime>();
		
		
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			dates.add(DateTimeUtils.iso8601StringToDateTime(string));
		}
		
		GUIWeek week = new GUIWeek();

		
		for (Iterator iterator = dates.iterator(); iterator.hasNext();) {
			DateTime dateTime = (DateTime) iterator.next();
			List<Lesson> lessons =  weekdata.get(DateTimeUtils.toISO8601Date(dateTime));
			GUIDay d = analyseDay(dateTime, lessons);
			week.setGUIDay(dateTime, d);
		}
		week.setViewType(viewtype);
		return week;
	}
	
	List<TimegridUnit> timegridForDateTimeDay;
	
	private GUIDay analyseDay(DateTime date, List<Lesson> lessons){
		GUIDay day = new GUIDay();
		day.setDate(date);
		
		if(timegridForDateTimeDay == null){ //TODO Das WochenGRID wird auf Montag gesynct
			timegridForDateTimeDay = timegrid.getTimegridForDay(WebUntis.MONDAY);
		}
		
		
		if(lessons.size() == 0){
			for (TimegridUnit timegridUnit : timegridForDateTimeDay) {
				GUILessonContainer lessoncon = new GUILessonContainer();
				lessoncon.setTime(timegridUnit.getStart(), timegridUnit.getEnd());
				lessoncon.setDate(date);
				day.addLessonContainer(timegridUnit.getStart(),lessoncon);
			}
			return day;
		}
		
		
		
		for (TimegridUnit timegridUnit : timegridForDateTimeDay) {
			GUILessonContainer lessoncon = new GUILessonContainer();
			lessoncon.setTime(timegridUnit.getStart(), timegridUnit.getEnd());
			lessoncon.setDate(date);
			
			
			for(Lesson lesson : lessons){
				
				if(lesson.getStartTime().getMinute() == timegridUnit.getStart().getMinute()
						&& lesson.getStartTime().getHour() == timegridUnit.getStart().getHour()
						&& lesson.getEndTime().getMinute() == timegridUnit.getEnd().getMinute()
						&& lesson.getEndTime().getHour() == timegridUnit.getEnd().getHour()){
					lessoncon.addLesson(lesson);
				}
			}
			day.addLessonContainer(timegridUnit.getStart(),lessoncon);
		}
		
		
		return day;
	}
	
	
}
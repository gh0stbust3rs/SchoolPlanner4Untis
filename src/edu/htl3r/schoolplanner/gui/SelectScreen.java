/* SchoolPlanner4Untis - Android app to manage your Untis timetable
    Copyright (C) 2011  Mathias Kub <mail@makubi.at>
			Sebastian Chlan <sebastian@schoolplanner.at>
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package edu.htl3r.schoolplanner.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import edu.htl3r.schoolplanner.R;
import edu.htl3r.schoolplanner.backend.DataFacade;
import edu.htl3r.schoolplanner.backend.schoolObjects.ViewType;
import edu.htl3r.schoolplanner.backend.schoolObjects.viewtypes.SchoolClass;
import edu.htl3r.schoolplanner.backend.schoolObjects.viewtypes.SchoolRoom;
import edu.htl3r.schoolplanner.backend.schoolObjects.viewtypes.SchoolSubject;
import edu.htl3r.schoolplanner.backend.schoolObjects.viewtypes.SchoolTeacher;
import edu.htl3r.schoolplanner.gui.basti.ViewBasti;
import edu.htl3r.schoolplanner.gui.selectScreen.ViewTypeOnClickListener;
import edu.htl3r.schoolplanner.gui.selectScreen.ViewTypeSpinnerOnItemSelectedListener;

public class SelectScreen extends SchoolPlannerActivity{
	
	private List<SchoolClass> classList;
	private List<SchoolTeacher> teacherList;
	private List<SchoolRoom> roomList;
	private List<SchoolSubject> subjectList;
	
	private Spinner classSpinner;
	private Spinner teacherSpinner;
	private Spinner roomSpinner;
	private Spinner subjectSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_screen);
		
		initSpinner();
		addImageOnClickListener();
	}
	
	private void initSpinner() {
		classSpinner = (Spinner) findViewById(R.id.selectScreen_spinnerClass);
		DataFacade<List<SchoolClass>> classData = (DataFacade<List<SchoolClass>>) getIntent().getExtras().getSerializable(BundleConstants.SCHOOL_CLASS_LIST);
		if(classData.isSuccessful()) {
			classList = classData.getData();
			initViewTypeSpinner(classSpinner, classList);
		}
		else {
			ImageView imageClass = (ImageView) findViewById(R.id.selectScreen_imageClass);
			setListImageNotAvailable(imageClass);
			classSpinner.setEnabled(false);
		}
		
		teacherSpinner = (Spinner) findViewById(R.id.selectScreen_spinnerTeacher);
		DataFacade<List<SchoolTeacher>> teacherData = (DataFacade<List<SchoolTeacher>>) getIntent().getExtras().getSerializable(BundleConstants.SCHOOL_TEACHER_LIST);
		if(teacherData.isSuccessful()) {
			teacherList = teacherData.getData();
			initViewTypeSpinner(teacherSpinner, teacherList);
		}
		else {
			ImageView imageTeacher = (ImageView) findViewById(R.id.selectScreen_imageTeacher);
			setListImageNotAvailable(imageTeacher);
			teacherSpinner.setEnabled(false);
		}
		
		roomSpinner = (Spinner) findViewById(R.id.selectScreen_spinnerRoom);
		DataFacade<List<SchoolRoom>> roomData = (DataFacade<List<SchoolRoom>>) getIntent().getExtras().getSerializable(BundleConstants.SCHOOL_ROOM_LIST);
		if(roomData.isSuccessful()) {
			roomList = roomData.getData();
			initViewTypeSpinner(roomSpinner, roomList);
		}
		else {
			ImageView imageRoom = (ImageView) findViewById(R.id.selectScreen_imageRoom);
			setListImageNotAvailable(imageRoom);
			roomSpinner.setEnabled(false);
		}
		
		subjectSpinner = (Spinner) findViewById(R.id.selectScreen_spinnerSubject);
		DataFacade<List<SchoolSubject>> subjectData = (DataFacade<List<SchoolSubject>>) getIntent().getExtras().getSerializable(BundleConstants.SCHOOL_SUBJECT_LIST);
		if(subjectData.isSuccessful()) {
			subjectList = subjectData.getData();
			initViewTypeSpinner(subjectSpinner, subjectList);
		}
		else {
			ImageView imageSubject = (ImageView) findViewById(R.id.selectScreen_imageSubject);
			setListImageNotAvailable(imageSubject);
			subjectSpinner.setEnabled(false);
		}
		
		Intent classIntent = new Intent(SelectScreen.this, ViewBasti.class);
		classSpinner.setOnItemSelectedListener(new ViewTypeSpinnerOnItemSelectedListener(this, classIntent, classList));
		
		Intent teacherIntent = new Intent(SelectScreen.this, ViewBasti.class);
		teacherSpinner.setOnItemSelectedListener(new ViewTypeSpinnerOnItemSelectedListener(this, teacherIntent, teacherList));
		
		Intent roomIntent = new Intent(SelectScreen.this, ViewBasti.class);
		roomSpinner.setOnItemSelectedListener(new ViewTypeSpinnerOnItemSelectedListener(this, roomIntent, roomList));
		
		Intent subjectIntent = new Intent(SelectScreen.this, ViewBasti.class);
		subjectSpinner.setOnItemSelectedListener(new ViewTypeSpinnerOnItemSelectedListener(this, subjectIntent, subjectList));
	}
	
	private void initViewTypeSpinner(Spinner spinner, List<? extends ViewType> list) {
		List<String> spinnerList = new ArrayList<String>();
		for(ViewType schoolRoom : list) {
			spinnerList.add(schoolRoom.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	private void setListImageNotAvailable(ImageView imageView) {		
		Drawable[] layers = new Drawable[] {toGrayscale(imageView.getDrawable()), getResources().getDrawable(R.drawable.ic_not) };
		imageView.setImageDrawable(new LayerDrawable(layers));
		imageView.setEnabled(false);
	}
	
	private Drawable toGrayscale(Drawable original) {
		return new BitmapDrawable(toGrayscale(((BitmapDrawable)original).getBitmap()));
	}
	
	private Bitmap toGrayscale(Bitmap bmpOriginal) {
	    Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}

	private void addImageOnClickListener() {
		ImageView imageClass = (ImageView) findViewById(R.id.selectScreen_imageClass);
		ImageView imageTeacher = (ImageView) findViewById(R.id.selectScreen_imageTeacher);
		ImageView imageRoom = (ImageView) findViewById(R.id.selectScreen_imageRoom);
		ImageView imageSubject = (ImageView) findViewById(R.id.selectScreen_imageSubject);
		
		Intent basti4 = new Intent(SelectScreen.this, ViewBasti.class);
		imageClass.setOnClickListener(new ViewTypeOnClickListener(this, basti4, classList, classSpinner));
		
		Intent basti = new Intent(SelectScreen.this, ViewBasti.class);
		imageSubject.setOnClickListener(new ViewTypeOnClickListener(this, basti, subjectList, subjectSpinner));
		
		Intent basti2 = new Intent(SelectScreen.this, ViewBasti.class);
		imageRoom.setOnClickListener(new ViewTypeOnClickListener(this, basti2, roomList, roomSpinner));
		
		Intent basti3 = new Intent(SelectScreen.this, ViewBasti.class);
		imageTeacher.setOnClickListener(new ViewTypeOnClickListener(this, basti3, teacherList, teacherSpinner));		
	}
	
}

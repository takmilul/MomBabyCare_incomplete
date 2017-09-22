package com.hackathon.appsoul.mombabycare.model;


/**
 * Created by A. A. M. Sharif on 18-Apr-16.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A record in the Event table
 */

public class Event implements Parcelable{
	public enum Type{
		DOCTOR,
		USER
	}

	private int id;
	private Type type;
	private Date date;
	private String text;
	private String photoFile;
	private String audioFile;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeValue(type);
		dest.writeValue(date);
		dest.writeString(text);
		dest.writeString(photoFile);
		dest.writeString(audioFile);
	}

	private Event(Parcel in){
		ClassLoader classLoader = Event.class.getClassLoader();
		this.id = in.readInt();
		this.type = (Type) in.readValue(classLoader);
		this.date = (Date) in.readValue(classLoader);
		this.text = in.readString();
		this.photoFile = in.readString();
		this.audioFile = in.readString();
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		@Override
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};

	public Event(){}

	public Event(String id, String type, String date, String text, String photoFile, String audioFile) {
		this.id = Integer.parseInt(id);
		this.type = (type.equalsIgnoreCase(Type.DOCTOR.name()) )? Type.DOCTOR : Type.USER;
		try {
			this.date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date);
		} catch (ParseException e) {
			Log.d("Event", e.getMessage(), e);
		}
		this.text = text;
		this.photoFile = photoFile;
		this.audioFile = audioFile;
	}

	public Integer getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}

	public String getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(String audioFile) {
		this.audioFile = audioFile;
	}
}

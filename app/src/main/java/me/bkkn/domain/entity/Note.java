package me.bkkn.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Note implements Parcelable {
    private int id;
    private String title;
    private String text;
    private int color;

    public Note(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    protected Note(Parcel in) {
        title = in.readString();
        text = in.readString();
    }

    public int getId() {
        return id;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(@Nullable Object other) {

        return  ((Note) other).id == id  &&
                ((Note) other).text.compareTo(text) == 0 &&
                ((Note) other).title.compareTo(title) == 0;
    }

    public int getColor() {
        return color;
    }
}

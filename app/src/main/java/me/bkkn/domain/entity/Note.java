package me.bkkn.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Note implements Parcelable {
    public static final String DEFAULT_COLOR = "FFFBEED1";
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
    private int id;
    private String title;
    private String text;
    private int color = (int) Long.parseLong(DEFAULT_COLOR, 16);

    public Note() {
    }

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

    public String getIdString() {
        return String.valueOf(id);
    }
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

    public void setContent(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return ((Note) other).id == id;
    }

    public int getColor() {
        return color;
    }
}

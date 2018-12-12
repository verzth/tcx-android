package id.kiosku.tcx.apis;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import id.kiosku.tcx.variables.AuthData;

public class AuthReceiver implements Parcelable {
    public Integer status;
    @SerializedName("status_number")
    public String statusNumber;
    @SerializedName("status_code")
    public String statusCode;
    @SerializedName("status_message")
    public String statusMessage;
    public String message;
    public AuthData data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.status);
        dest.writeValue(this.statusNumber);
        dest.writeString(this.statusCode);
        dest.writeString(this.statusMessage);
        dest.writeString(this.message);
        dest.writeString(this.message);
        dest.writeValue(this.data);
    }

    public AuthReceiver() {
    }

    protected AuthReceiver(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.statusNumber = in.readString();
        this.statusCode = in.readString();
        this.statusMessage = in.readString();
        this.message = in.readString();
        this.data = (AuthData) in.readValue(AuthData.class.getClassLoader());
    }

    public static final Creator<AuthReceiver> CREATOR = new Creator<AuthReceiver>() {
        @Override
        public AuthReceiver createFromParcel(Parcel source) {
            return new AuthReceiver(source);
        }

        @Override
        public AuthReceiver[] newArray(int size) {
            return new AuthReceiver[size];
        }
    };
}

package com.apps.game.market.entity.app;

import java.util.List;
import java.util.Vector;

import com.apps.game.market.enums.EnumAppStatus;

import android.os.Parcel;
import android.os.Parcelable;

public class EntityApp implements Parcelable {
	private long pid;
	private String icon;
	private String name;
	private long size;
	private String price;
	private long dcount;
	private long pcount;
	private EnumAppStatus status;
	private String packageName;
	private float rating;
	private String desc;
	private List<String> images;
	private String url;
	private boolean detail = false;

	public long getPid() {
		return pid;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getPrice() {
		return price;
	}

	public long getDcount() {
		return dcount;
	}

	public long getPcount() {
		return pcount;
	}

	public EnumAppStatus getStatus() {
		return status;
	}

	public String getPackageName() {
		return packageName;
	}

	public float getRating() {
		return rating;
	}

	public String getDesc() {
		return desc;
	}

	public List<String> getImages() {
		return images;
	}
	
	public String getUrl() {
		return url;
	}

	public boolean getDetail() {
		return detail;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setDcount(long dcount) {
		this.dcount = dcount;
	}

	public void setPcount(long pcount) {
		this.pcount = pcount;
	}

	public void setStatus(EnumAppStatus status) {
		this.status = status;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setDetail(boolean detail) {
		this.detail = detail;
	}

	public static Parcelable.Creator<EntityApp> getCreator() {
		return CREATOR;
	}

	public static final Parcelable.Creator<EntityApp> CREATOR = new Creator<EntityApp>() {
		@Override
		public EntityApp createFromParcel(Parcel parcel) {
			EntityApp entityApp = new EntityApp();
			entityApp.setPid(parcel.readLong());
			entityApp.setIcon(parcel.readString());
			entityApp.setName(parcel.readString());
			entityApp.setSize(parcel.readLong());
			entityApp.setDcount(parcel.readLong());
			entityApp.setPcount(parcel.readLong());
			entityApp.setStatus(EnumAppStatus.values()[parcel.readInt()]);
			entityApp.setPackageName(parcel.readString());
			entityApp.setRating(parcel.readFloat());
			entityApp.setDesc(parcel.readString());
			entityApp.setImages(new Vector<String>());
			parcel.readList(entityApp.getImages(),
					ClassLoader.getSystemClassLoader());
			boolean detail[] = new boolean[1];
			parcel.readBooleanArray(detail);
			entityApp.setDetail(detail[0]);

			return entityApp;
		}

		@Override
		public EntityApp[] newArray(int size) {
			return new EntityApp[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeLong(this.pid);
		parcel.writeString(this.icon);
		parcel.writeString(this.name);
		parcel.writeLong(this.size);
		parcel.writeString(this.price);
		parcel.writeLong(this.dcount);
		parcel.writeLong(this.pcount);
		parcel.writeInt(this.status.ordinal());
		parcel.writeString(this.packageName);
		parcel.writeFloat(this.rating);
		parcel.writeString(this.desc);
		parcel.writeList(this.images);
		parcel.writeBooleanArray(new boolean[] { this.detail });
	}
}

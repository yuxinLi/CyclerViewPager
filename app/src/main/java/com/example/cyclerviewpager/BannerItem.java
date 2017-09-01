package com.example.cyclerviewpager;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerItem implements Parcelable {

    public String clickUrl;
    public String extParams;
    public int fileId;
    public int playTurnId;
    public String resId;
    public int resPosition;
    /**资源类型：1,帖子;2.视频;3.个人秀-在线直播；4.资讯；5.资讯-组图;6.首页banner图(resId>0时跳转商品页，resId=0时使用clickUrl跳转，都没有时不跳转);*/
    public static int RESTYPE_ARTICLE_WEB = 4;
    public static int RESTYPE_ARTICLE_PHOTO = 5;
    public static int RESTYPE_HOMEPAGE_BANNER = 6;
    public int resType;

    public String resUrl;
    public int sortValue;
    public int status;
    public String title;
    public int deviceType;
    public String order;



    public BannerItem copy(){
        BannerItem bannerItem = new BannerItem();
        bannerItem.clickUrl = this.clickUrl;
        bannerItem.extParams = this.extParams;
        bannerItem.fileId = this.fileId;
        bannerItem.playTurnId = this.playTurnId;
        bannerItem.resId = this.resId;
        bannerItem.resPosition = this.resPosition;
        bannerItem.resType = this.resType;
        bannerItem.resUrl = this.resUrl;
        bannerItem.sortValue = this.sortValue;
        bannerItem.status = this.status;
        bannerItem.title = this.title;
        bannerItem.deviceType = this.deviceType;
        bannerItem.order = this.order;
        return bannerItem;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clickUrl);
        dest.writeString(this.extParams);
        dest.writeInt(this.fileId);
        dest.writeInt(this.playTurnId);
        dest.writeString(this.resId);
        dest.writeInt(this.resPosition);
        dest.writeInt(this.resType);
        dest.writeString(this.resUrl);
        dest.writeInt(this.sortValue);
        dest.writeInt(this.status);
        dest.writeString(this.title);
        dest.writeInt(this.deviceType);
        dest.writeString(this.order);
    }

    public BannerItem() {
    }

    protected BannerItem(Parcel in) {
        this.clickUrl = in.readString();
        this.extParams = in.readString();
        this.fileId = in.readInt();
        this.playTurnId = in.readInt();
        this.resId = in.readString();
        this.resPosition = in.readInt();
        this.resType = in.readInt();
        this.resUrl = in.readString();
        this.sortValue = in.readInt();
        this.status = in.readInt();
        this.title = in.readString();
        this.deviceType = in.readInt();
        this.order = in.readString();
    }

    public static final Creator<BannerItem> CREATOR = new Creator<BannerItem>() {
        @Override
        public BannerItem createFromParcel(Parcel source) {
            return new BannerItem(source);
        }

        @Override
        public BannerItem[] newArray(int size) {
            return new BannerItem[size];
        }
    };
}
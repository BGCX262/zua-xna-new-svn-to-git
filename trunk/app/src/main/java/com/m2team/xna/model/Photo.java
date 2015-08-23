package com.m2team.xna.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/30/2014.
 */
public class Photo implements Parcelable {
    String strAuthor;
    String href;//link in browser
    String title;
    String url;//link direct to image: ex: xna.com/xxx.jpg
    String avatarAuthorURL;//link direct to avatar
    String profileAuthorURL;//link display to profile page
    String colorHex;
    String cameraModel, dateAdded, iso, aperture, fstop, flash, focal, rgb,
            width, height, software, category, description, like, view, favorite, comment;
    Author author;
    HashMap<String, String> mapExif;
    boolean hasExif;

    public boolean isHasExif() {
        return hasExif;
    }

    public void setHasExif(boolean hasExif) {
        this.hasExif = hasExif;
    }

    public Photo(String title, String url, String author, String href) {
        this.title = title;
        this.url = url;
        this.strAuthor = author;
        this.href = href;
    }

    public Photo() {
    }

    public Photo(Parcel in) {
        String key, value;
        String[] data = new String[26];

        in.readStringArray(data);
        this.title = data[0];
        this.dateAdded = data[1];
        this.iso = data[2];
        this.aperture = data[3];
        this.fstop = data[4];

        this.like = data[5];
        this.view = data[6];
        this.favorite = data[7];
        author = new Author();
        this.author.setUsername(data[8]);
        this.author.setFullName(data[9]);
        this.author.setSinglePhotoCount(Integer.parseInt(data[10]));
        this.author.setChoosePhotoCount(Integer.parseInt(data[11]));
        this.author.setCommentCount(Integer.parseInt(data[12]));
        this.author.setDateRegister(data[13]);

        colorHex = data[14];
        cameraModel = data[15];
        flash = data[16];
        focal = data[17];
        rgb = data[18];
        width = data[19];
        height = data[20];
        software = data[21];
        category = data[22];
        description = data[23];
        comment = data[24];
        href = data[25];

        int size = in.readInt();
        mapExif = new HashMap<String, String>();
        for (int i = 0; i < size; i++) {
            key = in.readString();
            value = in.readString();
            mapExif.put(key, value);
        }

    }

    public Photo(String strAuthor, String href, String title, String url, String dateAdded, String iso, String aperture, String fstop, String view, String favorite, String like) {
        this.strAuthor = strAuthor;
        this.href = href;
        this.title = title;
        this.url = url;
        this.dateAdded = dateAdded;
        this.iso = iso;
        this.aperture = aperture;
        this.fstop = fstop;
        this.view = view;
        this.favorite = favorite;
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[]{
                title, dateAdded, iso, aperture, fstop,
                like, view, favorite,
                author.getUsername(), author.getFullName(), String.valueOf(author.getSinglePhotoCount()),
                String.valueOf(author.getChoosePhotoCount()), String.valueOf(author.getCommentCount()), author.getDateRegister(),
                colorHex, cameraModel, flash, focal, rgb, width, height, software, category, description, comment, href
        });
        out.writeInt(mapExif.size());
        for (Map.Entry<String, String> entry : mapExif.entrySet()) {
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public HashMap<String, String> getMapExif() {
        return mapExif;
    }

    public void setMapExif(HashMap<String, String> mapExif) {
        this.mapExif = mapExif;
    }

    public String getProfileAuthorURL() {
        return profileAuthorURL;
    }

    public void setProfileAuthorURL(String profileAuthorURL) {
        this.profileAuthorURL = profileAuthorURL;
    }

    public String getAvatarAuthorURL() {
        return avatarAuthorURL;
    }

    public void setAvatarAuthorURL(String avatarAuthorURL) {
        this.avatarAuthorURL = avatarAuthorURL;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }


    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getFstop() {
        return fstop;
    }

    public void setFstop(String fstop) {
        this.fstop = fstop;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public String getFocal() {
        return focal;
    }

    public void setFocal(String focal) {
        this.focal = focal;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getStrAuthor() {
        return this.strAuthor;
    }

    public String getHref() {
        return this.href;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setStrAuthor(String paramString) {
        this.strAuthor = paramString;
    }

    public void setHref(String paramString) {
        this.href = paramString;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }

    public void setUrl(String paramString) {
        this.url = paramString;
    }


}

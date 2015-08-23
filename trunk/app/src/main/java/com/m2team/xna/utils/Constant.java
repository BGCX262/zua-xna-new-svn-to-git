package com.m2team.xna.utils;

/**
 * Created by Admin on 9/30/2014.
 */
public class Constant {

    public static final String SHARE_PREF_FILE_NAME = "xna_pref";
    public static final String SHARE_PREF_KEY_LANGUAGE = "key_pref_lang";
    public static final String SHARE_PREF_KEY_THEME_COLOR = "key_pref_theme_color";
    //----------CONSTANT OF EXIF TABLE-----------
    public static final String BRAND_EXIF = "Make";
    public static final String EXPOSURE_EXIF = "ExposureTime";
    public static final String FLASH_EXIF = "Flash";
    public static final String FNUM_EXIF = "FNumber";
    public static final String FOCAL_EXIF = "FocalLength";
    public static final String HEIGHT_EXIF = "height";
    public static final String ISO_EXIF = "Iso";
    public static final String MODEL_EXIF = "Model";
    public static final String RGB_EXIF = "rgb";
    public static final String SOFTWARE_EXIF = "Software";
    public static final String AUTHOR_NAME_KEY = "authorName";

    public static final String FOLDER_APPS = "XomNhiepAnh";
    public static final String NUDE_FILTER_KEY = "nude";
    public static final String COLOR_ATTR_STRING_SPLIT = "color:";
    public static final int ITEM_PER_PAGE = 35;
    public static final String TOKEN_SPACE = " ";
    public static final String TOKEN_LINE = "-";
    public static final String TOKEN_DUAL_DOT = ":";
    public static final String TIME_ADDED_STRING_SPLIT = "ngày";
    public static final String WIDTH_EXIF = "width";
    public static final String KEY_INTENT_URL = "KEY_INTENT_URL";
    public static final String KEY_INTENT_AUTHOR = "KEY_INTENT_AUTHOR";
    public static final String KEY_INTENT_TITLE = "KEY_INTENT_TITLE";
    public static final String KEY_INTENT_HREF = "KEY_INTENT_HREF";
    public static final String KEY_INTENT_PHOTO_OBJECT = "KEY_INTENT_PHOTO_OBJECT";

    //-----------CONSTANT FOR KEY OF JSOUP TO RAW
    //key for main page
    public static final String CSS_CLASS_IMAGE_HTML = ".padcol-left a";
    public static final String CSS_PROPERTY_LINK_IMAGE_HTML = "rel";
    public static final String CSS_PROPERTY_TITLE_IMAGE_HTML = "title";
    public static final String CSS_PROPERTY_HREF_IMAGE_HTML = "href";
    //key for image page
    public static final String CSS_PROPERTY_EXIF_TABLE_IMG = "#tborder tr";
    public static final String CSS_PROPERTY_VIEW_COUNT_IMG = "span#viewCount";
    public static final String CSS_PROPERTY_COMMENT_COUNT_IMG = "span#commentCount";
    public static final String CSS_PROPERTY_FAVOR_COUNT_IMG = "span#faveCount";
    public static final String CSS_PROPERTY_TIME_TAKEN_IMG = "div.takenTime";
    public static final String CSS_PROPERTY_URL_USER_AVATAR_IMG = ".userInfo .avatar a[href]";
    public static final String CSS_PROPERTY_URL_USER_COLOR_IMG = ".userInfo .fl span";
    public static final String CSS_PROPERTY_CATEGORY_IMG = ".cate a";
    //key for author page
    public static final String CSS_PROPERTY_GET_NAME_AUTHOR = ".genTitle";
    public static final String CSS_PROPERTY_GET_INFO_AUTHOR = ".user_info .row";
    public static final String CSS_PROPERTY_GET_CHOOSE_PHOTO_INFO_AUTHOR = "Ảnh chọn";
    public static final String CSS_PROPERTY_GET_SINGLE_PHOTO_INFO_AUTHOR = "Ảnh đơn";
    public static final String CSS_PROPERTY_GET_COMMENT_PHOTO_INFO_AUTHOR = "Bình luận";
    public static final String CSS_PROPERTY_GET_JOIN_DATE_INFO_AUTHOR = "Tham gia";
    public static final String CSS_PROPERTY_GET_LATEST_ACTIVITY_INFO_AUTHOR = "Hoạt động cuối";
    public static final String CSS_PROPERTY_GET_YAHOO_INFO_AUTHOR = "";
    public static final String CSS_PROPERTY_GET_SKYPE_INFO_AUTHOR = "";

    //-----------CONSTANT FOR URL-----------------
    public static final String BASE_URL = "http://xomnhiepanh.com";
    public static final String SUFFIX_OTHER = "&page=";
    public static final String SUFFIX_STAFF_CHOICE = "&sort=&dir=&page=";
    public static final String ANIMAL = "/?mod=gallery&act=categories&catid=7&t=dong-vat-animal";
    public static final String ARCHITECTURE = "/?mod=gallery&act=categories&catid=21&t=kien-truc-architecture";
    public static final String Abstract = "/?mod=gallery&act=categories&catid=1&t=truu-tuong-abstract";
    public static final String Advertisement = "/?mod=gallery&act=categories&catid=15&t=quang-cao-advetisment";
    public static final String BW = "/?mod=gallery&act=categories&catid=8&t=den-trang-bw";
    public static final String Conceptual = "/?mod=gallery&act=categories&catid=19&t=y-niem-conceptual";
    public static final String Experimental = "/?mod=gallery&act=categories&catid=20&t=anh-do-hoa-experimental";
    public static final String Fashion = "/?mod=gallery&act=categories&catid=10&t=thoi-trang-fashion";
    public static final String Journalism = "/?mod=gallery&act=categories&catid=16&t=bao-chi-journalism";
    public static final String LANDSCAPE = "/?mod=gallery&act=categories&catid=6&t=phong-canh-landscape";
    public static final String MACRO = "/?mod=gallery&act=categories&catid=5&t=macro-macro";
    public static final String NATURE = "/?mod=gallery&act=categories&catid=3&t=thien-nhien-nature";
    public static final String Nude = "/?mod=gallery&act=categories&catid=14&t=khoa-than-nude";
    public static final String Other = "/?mod=gallery&act=categories&catid=13&t=khong-xac-dinh-other";
    public static final String PORTRAIT = "/?mod=gallery&act=categories&catid=2&t=chan-dung-portrait";
    public static final String Panorama = "/?mod=gallery&act=categories&catid=23&t=panorama";
    public static final String Product = "/?mod=gallery&act=categories&catid=18&t=san-pham-product";
    public static final String STAFF_CHOICE = "/?mod=gallery&act=staffchoice";
    public static final String STREET_LIFE = "/?mod=gallery&act=categories&catid=4&t=doi-thuong-streetlife";
    public static final String Sport = "/?mod=gallery&act=categories&catid=9&t=the-thao-sport";
    public static final String Stage = "/?mod=gallery&act=categories&catid=17&t=san-khau-stage";
    public static final String Still_Life = "/?mod=gallery&act=categories&catid=12&t=tinh-vat-still-life";
    public static final String TRAVEL = "/?mod=gallery&act=categories&catid=22&t=du-lich-travel";
    public static final String Wedding = "/?mod=gallery&act=categories&catid=11&t=anh-cuoi-wedding";

}

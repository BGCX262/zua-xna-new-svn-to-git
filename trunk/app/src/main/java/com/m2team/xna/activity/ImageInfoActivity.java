package com.m2team.xna.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.m2team.xna.R;
import com.m2team.xna.model.Author;
import com.m2team.xna.model.Photo;
import com.m2team.xna.utils.Applog;
import com.m2team.xna.utils.Common;
import com.m2team.xna.utils.Constant;
import com.m2team.xna.utils.Log;

import java.util.HashMap;

public class ImageInfoActivity extends ActionBarActivity {

    TextView txtCamera, info_iso, info_aperture, info_fnumber,
            info_flash, info_focal, info_rgb, info_width, info_height, info_software,
            info_title, info_category, info_description, info_date_added, info_like_list,
            info_view_count, info_favorites_count, info_comment_count,
            info_user_name, info_user_full_name, info_user_single_image_count,
            info_user_register_date, info_user_choose_image_count, info_user_comment_count,
            lbl_description, info_web_link;
    LinearLayout ll_parent_exif_info;
    ScrollView scroll_view_activity_image_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        initView();
        getSetData();
    }

    private void getSetData() {
        Intent intent = getIntent();
        if (intent != null) {
            String value;
            Photo photo = intent.getParcelableExtra(Constant.KEY_INTENT_PHOTO_OBJECT);
            HashMap<String, String> mapExif = photo.getMapExif();
            if (mapExif != null) {
                if (mapExif.containsKey(Constant.BRAND_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.BRAND_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        txtCamera.setText(value);
                    }
                }
                if (mapExif.containsKey(Constant.MODEL_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.MODEL_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        txtCamera.setText(value);
                    }
                }

                if (mapExif.containsKey(Constant.FNUM_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.FNUM_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_fnumber.setVisibility(View.VISIBLE);
                        info_fnumber.setText(value);
                    } else {
                        info_fnumber.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.EXPOSURE_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.EXPOSURE_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_aperture.setVisibility(View.VISIBLE);
                        info_aperture.setText(value);
                    } else {
                        info_aperture.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.ISO_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.ISO_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_iso.setVisibility(View.VISIBLE);
                        info_iso.setText(value);
                    } else {
                        info_iso.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.FLASH_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.FLASH_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_flash.setVisibility(View.VISIBLE);
                        info_flash.setText(value);
                    } else {
                        info_flash.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.FOCAL_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.FOCAL_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_focal.setVisibility(View.VISIBLE);
                        info_focal.setText(value);
                    } else {
                        info_focal.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.HEIGHT_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.HEIGHT_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_height.setVisibility(View.VISIBLE);
                        info_height.setText(value.trim() + " px");
                    } else {
                        info_height.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.WIDTH_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.WIDTH_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_width.setVisibility(View.VISIBLE);
                        info_width.setText(value.trim() + " px");
                    } else {
                        info_width.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.RGB_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.RGB_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_rgb.setVisibility(View.VISIBLE);
                        info_rgb.setText(value);
                    } else {
                        info_rgb.setVisibility(View.INVISIBLE);
                    }
                }
                if (mapExif.containsKey(Constant.SOFTWARE_EXIF.toLowerCase())) {
                    value = mapExif.get(Constant.SOFTWARE_EXIF.toLowerCase());
                    if (!TextUtils.isEmpty(value)) {
                        info_software.setVisibility(View.VISIBLE);
                        info_software.setText(value);
                    } else {
                        info_software.setVisibility(View.INVISIBLE);
                    }
                }
                //fill info of image
                if (!TextUtils.isEmpty(photo.getTitle())) {
                    info_title.setVisibility(View.VISIBLE);
                    info_title.setText(photo.getTitle());
                } else {
                    info_title.setVisibility(View.INVISIBLE);
                }
                //cateogry
                if (!TextUtils.isEmpty(photo.getCategory())) {
                    info_category.setVisibility(View.VISIBLE);
                    info_category.setText(photo.getCategory());
                } else {
                    info_category.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(photo.getDescription())) {
                    info_description.setVisibility(View.VISIBLE);
                    info_description.setText(photo.getDescription());
                } else {
                    info_description.setVisibility(View.GONE);
                    lbl_description.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(photo.getDateAdded())) {
                    info_date_added.setVisibility(View.VISIBLE);
                    info_date_added.setText(photo.getDateAdded());
                } else {
                    info_date_added.setVisibility(View.INVISIBLE);
                }
                /*if (!TextUtils.isEmpty(photo.getLike())) {
                    info_like_list.setVisibility(View.VISIBLE);
                    info_like_list.setText(photo.getDateAdded());
                } else {
                    info_like_list.setVisibility(View.INVISIBLE);
                }*/
                if (!TextUtils.isEmpty(photo.getView())) {
                    info_view_count.setVisibility(View.VISIBLE);
                    info_view_count.setText(photo.getView());
                } else {
                    info_view_count.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(photo.getFavorite())) {
                    info_favorites_count.setVisibility(View.VISIBLE);
                    info_favorites_count.setText(photo.getFavorite());
                } else {
                    info_favorites_count.setVisibility(View.INVISIBLE);
                }
                if (!TextUtils.isEmpty(photo.getComment())) {
                    info_comment_count.setVisibility(View.VISIBLE);
                    info_comment_count.setText(photo.getComment());
                } else {
                    info_comment_count.setVisibility(View.INVISIBLE);
                }

                //display or hidden exif fragment
                if (!mapExif.containsKey(Constant.FNUM_EXIF.toLowerCase()) && !mapExif.containsKey(Constant.EXPOSURE_EXIF.toLowerCase()) && !mapExif.containsKey(Constant.ISO_EXIF.toLowerCase())) {
                    ll_parent_exif_info.setVisibility(View.GONE);
                }

                //fill info of author
                Author author = photo.getAuthor();
                if (author != null) {
                    if (!TextUtils.isEmpty(author.getFullName())) {
                        info_user_full_name.setVisibility(View.VISIBLE);
                        info_user_full_name.setText(author.getFullName());
                    } else {
                        info_user_full_name.setVisibility(View.INVISIBLE);
                    }
                    if (!TextUtils.isEmpty(author.getUsername())) {
                        info_user_name.setVisibility(View.VISIBLE);
                        info_user_name.setText(author.getUsername());
                        try {
                            if (!TextUtils.isEmpty(photo.getColorHex()))
                                info_user_name.setTextColor(Color.parseColor(photo.getColorHex()));
                        } catch (IllegalArgumentException e) {
                            Applog.e("Error parse color: " + photo.getColorHex() + " of username: " + author.getUsername());
                        }
                    } else {
                        info_user_name.setVisibility(View.INVISIBLE);
                    }
                    if (!TextUtils.isEmpty(author.getDateRegister())) {
                        info_user_register_date.setVisibility(View.VISIBLE);
                        info_user_register_date.setText(author.getDateRegister());
                    } else {
                        info_user_register_date.setVisibility(View.INVISIBLE);
                    }
                    if (author.getChoosePhotoCount() > 0) {
                        info_user_choose_image_count.setVisibility(View.VISIBLE);
                        info_user_choose_image_count.setText(String.valueOf(author.getChoosePhotoCount()));
                    } else {
                        info_user_choose_image_count.setVisibility(View.INVISIBLE);
                    }
                    if (author.getCommentCount() > 0) {
                        info_user_comment_count.setVisibility(View.VISIBLE);
                        info_user_comment_count.setText(String.valueOf(author.getCommentCount()));
                    } else {
                        info_user_comment_count.setVisibility(View.INVISIBLE);
                    }
                    if (author.getSinglePhotoCount() > 0) {
                        info_user_single_image_count.setVisibility(View.VISIBLE);
                        info_user_single_image_count.setText(String.valueOf(author.getSinglePhotoCount()));
                    } else {
                        info_user_single_image_count.setVisibility(View.INVISIBLE);
                    }
                }
                //link to web
                if (!TextUtils.isEmpty(photo.getHref())) {
                    info_web_link.setText(Constant.BASE_URL + photo.getHref());
                }


            }

        }
    }

    private void initView() {
        scroll_view_activity_image_info = (ScrollView) findViewById(R.id.scroll_view_activity_image_info);
        ll_parent_exif_info = (LinearLayout) findViewById(R.id.ll_parent_exif_info);
        //exif info views
        txtCamera = (TextView) findViewById(R.id.info_camera_text);
        info_iso = (TextView) findViewById(R.id.info_iso);
        info_aperture = (TextView) findViewById(R.id.info_aperture);
        info_fnumber = (TextView) findViewById(R.id.info_fnumber);
        info_flash = (TextView) findViewById(R.id.info_flash);
        info_focal = (TextView) findViewById(R.id.info_focal);
        info_rgb = (TextView) findViewById(R.id.info_rgb);
        info_width = (TextView) findViewById(R.id.info_width);
        info_height = (TextView) findViewById(R.id.info_height);
        info_software = (TextView) findViewById(R.id.info_software);
        //image info views
        info_title = (TextView) findViewById(R.id.info_title);
        info_category = (TextView) findViewById(R.id.info_category);
        info_description = (TextView) findViewById(R.id.info_description);
        lbl_description = (TextView) findViewById(R.id.lbl_description);
        info_date_added = (TextView) findViewById(R.id.info_date_added);
        //info_like_list = (TextView) findViewById(R.id.info_like_list);
        info_view_count = (TextView) findViewById(R.id.info_view_count);
        info_favorites_count = (TextView) findViewById(R.id.info_favorites_count);
        info_comment_count = (TextView) findViewById(R.id.info_comment_count);
        //user info views
        info_user_name = (TextView) findViewById(R.id.info_user_name);
        info_user_full_name = (TextView) findViewById(R.id.info_user_full_name);
        info_user_single_image_count = (TextView) findViewById(R.id.info_user_single_image_count);
        info_user_register_date = (TextView) findViewById(R.id.info_user_register_date);
        info_user_choose_image_count = (TextView) findViewById(R.id.info_user_star_image_count);
        info_user_comment_count = (TextView) findViewById(R.id.info_user_comment_count);
        //web link
        info_web_link = (TextView) findViewById(R.id.info_web_link);
    }


}

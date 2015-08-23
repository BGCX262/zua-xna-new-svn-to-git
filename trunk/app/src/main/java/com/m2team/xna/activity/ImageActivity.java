package com.m2team.xna.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.capricorn.RayMenu;
import com.m2team.xna.R;
import com.m2team.xna.model.Author;
import com.m2team.xna.model.Photo;
import com.m2team.xna.utils.Common;
import com.m2team.xna.utils.Constant;
import com.m2team.xna.utils.Applog;
import com.m2team.xna.utils.TouchImageView;

import br.liveo.ui.RoundedImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ImageActivity
        extends ActionBarActivity {
    TextView apertureTextView, authorNameTextView, authorUsernameView, favoriteView, fstopTextView, isoTextView, timeAddedView, titleView, viewTextView;
    RoundedImageView avatarRoundAuthor;
    LinearLayout llImageExif;
    ProgressBar loadingProgress;
    TouchImageView photoFullImgView;
    ScrollView scroll_view_activity_image;
    private static final int[] ITEM_DRAWABLES = {R.drawable.ic_share_128, R.drawable.ic_download_128,
            R.drawable.ic_info};
    String title = "Image";
    String uriString = "";
    Photo photo;
    Author author;

    private void downloadImage(Uri paramUri) {
        Picasso.with(this).load(paramUri).into(this.target);
    }

    private void initActionButton() {
        RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);
            final int position = i;
            rayMenu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            String path = Common.getSharedPrefStringValue(ImageActivity.this, uriString);
                            if (TextUtils.isEmpty(path))
                                new LoadBitmapAsyncTask().execute(uriString);
                            else
                                createShareIntent(path);
                            break;
                        case 1:
                            if (!TextUtils.isEmpty(uriString)) {
                                downloadImage(Uri.parse(uriString));
                            }
                            break;
                        case 2:
                            Intent localIntent2 = new Intent(ImageActivity.this, ImageInfoActivity.class);
                            localIntent2.putExtra(Constant.KEY_INTENT_PHOTO_OBJECT, photo);
                            startActivity(localIntent2);
                            break;
                        default:

                            break;
                    }
                }
            });// Add a menu item
        }
    }

    private void initView() {
        photo = new Photo();
        author = new Author("", "", 0, 0, "", 0);
        scroll_view_activity_image = (ScrollView) findViewById(R.id.scroll_view_activity_image);
        this.photoFullImgView = ((TouchImageView) findViewById(R.id.photo_fullscreen));
        this.titleView = ((TextView) findViewById(R.id.photo_title));
        this.timeAddedView = ((TextView) findViewById(R.id.photo_added));
        this.viewTextView = ((TextView) findViewById(R.id.photo_view));
        this.favoriteView = ((TextView) findViewById(R.id.photo_favorite));
        this.isoTextView = ((TextView) findViewById(R.id.photo_iso_general));
        this.apertureTextView = ((TextView) findViewById(R.id.photo_aperture_general));
        this.fstopTextView = ((TextView) findViewById(R.id.photo_shutter_general));
        this.authorUsernameView = ((TextView) findViewById(R.id.photo_username_general));
        this.authorNameTextView = ((TextView) findViewById(R.id.photo_author_general));
        this.avatarRoundAuthor = ((RoundedImageView) findViewById(R.id.photo_avatar_general));
        loadingProgress = (ProgressBar) findViewById(R.id.progress_bar_load_photo);
        loadingProgress.setVisibility(View.VISIBLE);
        loadingProgress.setIndeterminate(true);
        llImageExif = (LinearLayout) findViewById(R.id.ll_image_exif);
        Common.setFont(this, scroll_view_activity_image, Common.ROBOTO_REGULAR);
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_image);
        initView();
        //createFakeData();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle localBundle = intent.getExtras();
            if (localBundle != null) {
                uriString = localBundle.getString(Constant.KEY_INTENT_URL);
                if (!TextUtils.isEmpty(uriString)) {
                    Applog.d("URI photo: " + uriString);
                    photo.setUrl(uriString);
                    Picasso.with(this).load(uriString).centerInside().resize(2000, 2000).placeholder(R.drawable.ic_launcher)
                            .error(R.drawable.ic_error).into(this.photoFullImgView, new Callback() {
                        @Override
                        public void onSuccess() {
                            loadingProgress.setVisibility(View.INVISIBLE);
                            initActionButton();
                        }

                        @Override
                        public void onError() {
                            loadingProgress.setVisibility(View.INVISIBLE);
                            Toast.makeText(ImageActivity.this, getString(R.string.error_loading_image) +  getString(R.string.error_connectivity), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                title = localBundle.getString(Constant.KEY_INTENT_TITLE);
                if (!TextUtils.isEmpty(title)) {
                    titleView.setText(title);
                    photo.setTitle(title);
                }
                String str1 = localBundle.getString(Constant.KEY_INTENT_AUTHOR);
                if (!TextUtils.isEmpty(str1)) {
                    photo.setStrAuthor(str1);
                    author.setUsername(str1);
                    this.authorUsernameView.setText(str1);
                }
                String str2 = localBundle.getString(Constant.KEY_INTENT_HREF);
                if (!TextUtils.isEmpty(str2)) {
                    photo.setHref(str2);
                    new ParseImageContentURL().execute(Constant.BASE_URL + str2);
                }
            }
        }
    }

    private void createFakeData() {
        uriString = "http://developer.android.com/images/home/l-hero_2x.png";
        Picasso.with(this).load(uriString).centerInside().resize(1000, 1000).placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_error).into(this.photoFullImgView);
        titleView.setText("Developer by Android");
        this.authorUsernameView.setText("googl");
        authorNameTextView.setText("Android");
        Picasso.with(ImageActivity.this).load("http://material-design.storage.googleapis.com/publish/v_2/material_ext_publish/0Bx4BSt6jniD7dnNjb0FwZGdQaGs/style_imagery_principles4.png")
                .error(R.drawable.ic_error).into(avatarRoundAuthor);
        timeAddedView.setText("01.01.2015");
        viewTextView.setText("333");
        favoriteView.setText("100");
        isoTextView.setText("400");
        apertureTextView.setText("200");
        fstopTextView.setText("F1/8");
        authorUsernameView.setTextColor(Color.BLACK);
        new ParseAuthorURL().execute("http://xomnhiepanh.com/profile/greattata/8054.xna");
        photo = new Photo();
        photo.setAuthor(new Author("tomcat", "Tom", 12, 2, "20.01.2015", 88));
    }

    class ParseAuthorURL
            extends AsyncTask<String, Void, Void> {

        ParseAuthorURL() {
        }

        protected Void doInBackground(String... paramVarArgs) {
            try {
                Document localDocument = Jsoup.connect(paramVarArgs[0]).get();
                if (localDocument != null) {
                    Elements localElements = localDocument.select(Constant.CSS_PROPERTY_GET_NAME_AUTHOR);
                    Elements userElements = localDocument.select(Constant.CSS_PROPERTY_GET_INFO_AUTHOR);
                    if ((userElements != null) && (userElements.size() > 0)) {
                        String temp;
                        for (int i = 0; i < userElements.size(); i++) {
                            temp = userElements.get(i).text();
                            if (temp.contains(Constant.CSS_PROPERTY_GET_SINGLE_PHOTO_INFO_AUTHOR)) {
                                String[] split = temp.split(Constant.TOKEN_DUAL_DOT);
                                if (split.length == 2) {
                                    author.setSinglePhotoCount(Integer.parseInt(split[1].trim()));
                                }
                            }
                            if (temp.contains(Constant.CSS_PROPERTY_GET_CHOOSE_PHOTO_INFO_AUTHOR)) {
                                String[] split = temp.split(Constant.TOKEN_DUAL_DOT);
                                if (split.length == 2) {
                                    author.setChoosePhotoCount(Integer.parseInt(split[1].trim()));
                                }
                            }
                            if (temp.contains(Constant.CSS_PROPERTY_GET_COMMENT_PHOTO_INFO_AUTHOR)) {
                                String[] split = temp.split(Constant.TOKEN_DUAL_DOT);
                                if (split.length == 2) {
                                    author.setCommentCount(Integer.parseInt(split[1].trim()));
                                }
                            }
                            if (temp.contains(Constant.CSS_PROPERTY_GET_JOIN_DATE_INFO_AUTHOR)) {
                                String[] split = temp.split(Constant.TOKEN_DUAL_DOT);
                                if (split.length == 2) {
                                    author.setDateRegister((split[1].trim()));
                                }
                            }
                            /*if  (temp.contains(Constant.CSS_PROPERTY_GET_LATEST_ACTIVITY_INFO_AUTHOR)) {
                                String[] split = temp.split(Constant.TOKEN_DUAL_DOT);
                                if (split.length == 2) {
                                   // author.setSinglePhotoCount(Integer.parseInt(split[1].trim()));
                                }
                            }*/
                        }
                    }
                    if ((localElements != null) && (localElements.size() > 0)) {
                        String str = localElements.get(0).text();
                        int index = str.indexOf("(");
                        if (index >= 0) {
                            String fullname = str.substring(index + 1, str.indexOf(")"));
                            author.setFullName(fullname);
                        }
                    }
                }
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Applog.d("Error parse number ");
            }
            return null;
        }

        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!TextUtils.isEmpty(author.getFullName())) {
                authorNameTextView.setText(author.getFullName());
            } else {
                authorNameTextView.setText(getString(R.string.no_name));
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            authorNameTextView.setText("");
        }
    }

    class ParseImageContentURL
            extends AsyncTask<String, Void, Void> {
        String avatarAuthorURL, colorHex, comment, favorite, profileAuthorURL, styleAuthor, time, view, category;
        Elements exifs;
        HashMap<String, String> mapExif;


        private String processAddTime(String paramString) {
            String str = "";
            if (!TextUtils.isEmpty(paramString)) {
                if (paramString.length() > 10)
                    return paramString.substring(paramString.length() - 10).trim();
            }
            return str;
        }

        private void processExif(Elements paramElements) {
            Element element;
            String strInfo, key, value;
            for (int i = 0; i < paramElements.size(); i++) {
                element = paramElements.get(i);
                if (element != null) {
                    strInfo = element.text();//Make Canon
                    if (!TextUtils.isEmpty(strInfo)) {
                        int j = strInfo.indexOf(Constant.TOKEN_SPACE);
                        if (j > 0) {
                            key = strInfo.substring(0, j);//Make
                            value = strInfo.substring(j);//Canon
                            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                                mapExif.put(key.toLowerCase(), value.trim());
                            }
                        }
                    }
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mapExif = new HashMap();
        }

        protected Void doInBackground(String... paramVarArgs) {
            try {
                Document localDocument = Jsoup.connect(paramVarArgs[0]).get();
                if (localDocument != null) {
                    view = localDocument.select(Constant.CSS_PROPERTY_VIEW_COUNT_IMG).html();
                    comment = localDocument.select(Constant.CSS_PROPERTY_COMMENT_COUNT_IMG).html();
                    favorite = localDocument.select(Constant.CSS_PROPERTY_FAVOR_COUNT_IMG).html();
                    time = localDocument.select(Constant.CSS_PROPERTY_TIME_TAKEN_IMG).html();
                    category = localDocument.select(Constant.CSS_PROPERTY_CATEGORY_IMG).html();
                    Applog.d("Time info: view: " + view + " comment: " + comment + " favor: " + favorite + " time: " + time);
                    exifs = localDocument.select(Constant.CSS_PROPERTY_EXIF_TABLE_IMG);
                    if (exifs != null && exifs.size() > 0) {
                        processExif(exifs);
                    }
                    Elements localElements = localDocument.select(Constant.CSS_PROPERTY_URL_USER_AVATAR_IMG);
                    avatarAuthorURL = localElements.select("img").attr("src");
                    profileAuthorURL = localElements.select("a").attr("href");
                    styleAuthor = localDocument.select(Constant.CSS_PROPERTY_URL_USER_COLOR_IMG).select("span").attr("style");
                    if (!TextUtils.isEmpty(styleAuthor)) {
                        int i = styleAuthor.indexOf(Constant.COLOR_ATTR_STRING_SPLIT);
                        if (i >= 0) {
                            colorHex = styleAuthor.substring(i + 6, i + 13);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            String temp;
            String timeAdded = processAddTime(time);
            timeAddedView.setText(timeAdded);
            viewTextView.setText(view);
            favoriteView.setText(favorite);
            if (mapExif.size() > 0) {
                if (mapExif.containsKey(Constant.ISO_EXIF.toLowerCase())) {
                    temp = mapExif.get(Constant.ISO_EXIF.toLowerCase());
                    if (temp != null) {
                        isoTextView.setText(temp);
                        photo.setIso(temp);
                    }
                }
                if (mapExif.containsKey(Constant.EXPOSURE_EXIF.toLowerCase())) {
                    temp = mapExif.get(Constant.EXPOSURE_EXIF.toLowerCase());
                    if (temp != null) {
                        apertureTextView.setText(temp);
                        photo.setAperture(temp);
                    }
                }
                if (mapExif.containsKey(Constant.FNUM_EXIF.toLowerCase())) {
                    temp = mapExif.get(Constant.FNUM_EXIF.toLowerCase());
                    if (temp != null) {
                        fstopTextView.setText(temp);
                        photo.setFstop(temp);
                    }
                }
            }
            if (TextUtils.isEmpty(photo.getIso()) && TextUtils.isEmpty(photo.getAperture()) && TextUtils.isEmpty(photo.getFstop())) {
                llImageExif.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(avatarAuthorURL)) {
                Picasso.with(ImageActivity.this).load(avatarAuthorURL).error(R.drawable.ic_error).into(avatarRoundAuthor);
            }
            if (!TextUtils.isEmpty(profileAuthorURL)) {
                new ParseAuthorURL().execute(Constant.BASE_URL + profileAuthorURL);
            }
            if (!TextUtils.isEmpty(colorHex)) {
                try {
                    authorUsernameView.setTextColor(Color.parseColor(colorHex));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Applog.d("Error parse color of user: " + colorHex);
                    authorUsernameView.setTextColor(Color.BLACK);
                }
            }
            photo.setCategory(category);
            photo.setDateAdded(timeAdded);
            photo.setView(view);
            photo.setFavorite(favorite);
            photo.setComment(comment);
            photo.setAvatarAuthorURL(avatarAuthorURL);
            photo.setProfileAuthorURL(profileAuthorURL);
            photo.setColorHex(colorHex);
            photo.setAuthor(author);
            photo.setMapExif(mapExif);
            Applog.d("Image info: cat: " + category + " time: " + timeAdded + " view: " + view + " fav: " + favorite
                    + " color: " + colorHex);
            Applog.d("author: " + author.getUsername() + " full: " + author.getFullName() + " single: " + author.getSinglePhotoCount()
                    + " choose: " + author.getChoosePhotoCount() + " cm: " + author.getCommentCount() + " reg: " + author.getDateRegister());
        }
    }

    private Target target = new Target() {
        public void onBitmapFailed(Drawable paramAnonymousDrawable) {
            Toast.makeText(ImageActivity.this, getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
        }

        public void onBitmapLoaded(final Bitmap paramAnonymousBitmap, Picasso.LoadedFrom paramAnonymousLoadedFrom) {
            new Thread(new Runnable() {
                public void run() {
                    File sdcard = Environment.getExternalStorageDirectory();
                    File dataDir = new File(sdcard, Constant.FOLDER_APPS);
                    if (!confirmDir(dataDir)) {
                        Toast.makeText(ImageActivity.this, "Cannot create folder in storage", Toast.LENGTH_SHORT).show();
                    } else {
                        File localFile = new File(sdcard.getPath() + "/" + Constant.FOLDER_APPS + "/" + title + ".jpg");
                        try {
                            localFile.createNewFile();
                            FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
                            paramAnonymousBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localFileOutputStream);
                            localFileOutputStream.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ImageActivity.this, getString(R.string.download_completed), Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        } catch (Exception localException) {
                            localException.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        public void onPrepareLoad(Drawable paramAnonymousDrawable) {
            Toast.makeText(ImageActivity.this, getString(R.string.download_starting), Toast.LENGTH_SHORT).show();
        }

        private boolean confirmDir(File dir) {
            return dir.isDirectory() || !dir.exists() && dir.mkdirs();
        }
    };

    class LoadBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap;
            try {
                url = params[0];
                bitmap = Picasso.with(ImageActivity.this).load(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, photo.getTitle() != null ? photo.getTitle() : "", null);
                if (!TextUtils.isEmpty(path)) {
                    Common.putSharedPrefStringValue(ImageActivity.this, url, path);
                    createShareIntent(path);
                } else {
                    Applog.e("error insert media to share image with url: " + url);
                    Toast.makeText(ImageActivity.this, getString(R.string.share_error), Toast.LENGTH_SHORT).show();
                }
            } else {
                Applog.e("error decode bitmap to share image with url: " + url);
                Toast.makeText(ImageActivity.this, getString(R.string.share_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createShareIntent(String path) {
        Uri screenshotUri = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, photo.getTitle() != null ? photo.getTitle() : "");
        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(intent);
    }
}



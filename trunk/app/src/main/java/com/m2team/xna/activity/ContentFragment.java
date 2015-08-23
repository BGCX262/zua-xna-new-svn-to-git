package com.m2team.xna.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.m2team.xna.R;
import com.m2team.xna.adapter.CustomAdapter;
import com.m2team.xna.model.Photo;
import com.m2team.xna.utils.Applog;
import com.m2team.xna.utils.Common;
import com.m2team.xna.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ContentFragment extends Fragment implements
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

    public static final String TAG = "ContentFragment";
    private static final String URL = "url";
    private static final String POSITION = "position";
    StaggeredGridView gridView;
    ProgressBar progressBar;
    ArrayList<Photo> photoList;
    ArrayList<String> urlList;
    CustomAdapter adapter;
    private String url;
    private int position;
    StringBuilder sb;
    int page;
    private boolean mHasRequestedMore;

    public static ContentFragment newInstance(String url, int position) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
        photoList = new ArrayList();
        urlList = new ArrayList();
        position = 0;
        sb = new StringBuilder();
        page = 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_content, container, false);
        gridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        View footer = inflater.inflate(R.layout.layout_main_header_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.loading);
        gridView.addFooterView(footer);
        adapter = new CustomAdapter(getActivity(), photoList);
        gridView.setAdapter(adapter);
        StringBuilder stringBuilder = new StringBuilder(Constant.BASE_URL);
        if (getArguments() != null) {
            stringBuilder.append(getArguments().getString(URL));
            new ParseURL().execute(stringBuilder.toString());
        }
        gridView.setOnScrollListener(this);
        gridView.setOnItemClickListener(this);
        return view;
    }

    Photo photo = null;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 0)
            position = 0;
        else if (position > photoList.size() - 1)
            position = photoList.size() - 1;
        photo = photoList.get(position);
        String url = photo.getUrl();
        url = url.replace("thumb_", "");
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra(Constant.KEY_INTENT_URL, url);
        intent.putExtra(Constant.KEY_INTENT_TITLE, photo.getTitle());
        intent.putExtra(Constant.KEY_INTENT_AUTHOR, photo.getStrAuthor());
        intent.putExtra(Constant.KEY_INTENT_HREF, photo.getHref());
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                if (photoList.size() > 0) {
                    if (photoList.size() <= Constant.ITEM_PER_PAGE)
                        page = 2;
                    else
                        page++;
                }
                if (position == 0) {
                    sb = new StringBuilder(Constant.BASE_URL).append(Constant.STAFF_CHOICE).append(Constant.SUFFIX_STAFF_CHOICE).append(page);
                } else {
                    sb = new StringBuilder(Constant.BASE_URL).append(url).append(Constant.SUFFIX_OTHER).append(page);

                }
                Log.d(TAG, "onScroll lastInScreen - so load more url: " + sb.toString());
                mHasRequestedMore = true;
                new ParseURL().execute(sb.toString());
            }
        }
    }

    class ParseURL extends AsyncTask<String, Void, ArrayList<Photo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Photo> doInBackground(String... strings) {
            mHasRequestedMore = true;
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Photo photo;
                String content = "";
                String title, href;
                Elements metaElement = doc.select(Constant.CSS_CLASS_IMAGE_HTML);
                for (Element metaElem : metaElement) {
                    content = metaElem.attr(Constant.CSS_PROPERTY_LINK_IMAGE_HTML);
                    content = content.substring(0, content.indexOf("@"));
                    // content = content.replace("thumb_","");
                    title = metaElem.attr(Constant.CSS_PROPERTY_TITLE_IMAGE_HTML);
                    href = metaElem.attr(Constant.CSS_PROPERTY_HREF_IMAGE_HTML);
                    if (!urlList.contains(content) && !isNude(href)) {
                        String[] split = title.split(Constant.TOKEN_LINE);
                        if (split.length == 2) {
                            title = split[0] != null ? split[0] : "";
                            if (filterPicture(title)) {
                                photo = new Photo(title, content, split[1] != null ? split[1].trim() : "", href);
                                photoList.add(photo);
                                urlList.add(content);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                Applog.e("Error when load URL connection: " + strings[0]);
                t.printStackTrace();
                return new ArrayList<Photo>();
            }
            return photoList;
        }

        private boolean isNude(String href) {
            return href.contains(Constant.NUDE_FILTER_KEY);
        }

        @Override
        protected void onPostExecute(final ArrayList<Photo> photoList) {
            super.onPostExecute(photoList);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
            if (photoList.size() == 0) {
                Toast.makeText(getActivity(), getString(R.string.error_connectivity), Toast.LENGTH_SHORT).show();
            } else {
                mHasRequestedMore = false;
            }
        }

        String[] filterString = new String[]{
                "18+", "19+", "nude", "20+", "she", "hot", "..."
        };

        private boolean filterPicture(String title) {
            for (int i = 0; i < filterString.length; i++) {
                if (title.toLowerCase().contains(filterString[i])) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_refresh).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_refresh:
                Fragment fragmentByTag = getFragmentManager().findFragmentByTag(TAG);
                if (fragmentByTag != null) {
                    Toast.makeText(getActivity(), getString(R.string.refresh_content), Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().detach(fragmentByTag).attach(fragmentByTag).commit();
                }
                break;

        }
        return true;
    }

}

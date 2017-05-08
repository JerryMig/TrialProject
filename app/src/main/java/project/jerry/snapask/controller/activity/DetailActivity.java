package project.jerry.snapask.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import project.jerry.snapask.R;
import project.jerry.snapask.util.ImageLoader;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initImage();
    }

    private void initImage() {
        String url = getIntent().getStringExtra(getString(R.string.photo_url));
        ImageView view = (ImageView) findViewById(R.id.image_view);
        ImageLoader.getInstance()
                .loadImageFromUrl(view, url, false, getResources().getColor(R.color.light_gray));
    }
}

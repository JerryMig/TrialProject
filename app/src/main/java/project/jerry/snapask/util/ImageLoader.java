package project.jerry.snapask.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class ImageLoader {

    private static ImageLoader sInstance;

    public static ImageLoader getInstance() {
        if (sInstance == null) {
            sInstance = new ImageLoader();
        }
        return sInstance;
    }

    private ImageLoader() {

    }

    private BitmapRequestBuilder<Uri, Bitmap> getGlideBitmapInstance(Context context, String url) {
        return Glide.with(context)
                .load(Uri.parse(url))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
    }

    public void loadImageFromUrl(final ImageView view, final String url
            , boolean transformToCircle, int defaultResImage) {
        if (view != null && !TextUtils.isEmpty(url)) {
            Context context = view.getContext();
            if (transformToCircle) {
                getGlideBitmapInstance(context, url)
                        .placeholder(defaultResImage)
                        .transform(new CropCircleTransformation(context))
                        .into(view);

            } else {
                getGlideBitmapInstance(context, url)
                        .placeholder(defaultResImage)
                        .into(view);
            }
        }
    }
}

package project.jerry.snapask.view.banner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.jerry.snapask.R;

/**
 * Created by Jerry on 2018/1/24.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerViewHolder> {

    private List<String> mBanners;

    public void setBannerList(List<String> banners) {
        mBanners = banners;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new BannerViewHolder(layoutInflater.inflate(R.layout.image_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        if (mBanners != null && !mBanners.isEmpty()) {
            ImageView imageView = (ImageView) holder.itemView;
            String url = mBanners.get(position % mBanners.size());
            Picasso.with(imageView.getContext()).load(url).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mBanners != null) {
            return mBanners.size() < 2 ? 1 : Integer.MAX_VALUE;
        }
        return 0;
    }

}

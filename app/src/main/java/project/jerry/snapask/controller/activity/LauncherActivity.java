package project.jerry.snapask.controller.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import project.jerry.snapask.R;
import project.jerry.snapask.controller.fragment.BaseFragment;
import project.jerry.snapask.controller.fragment.ClassViewFragment;
import project.jerry.snapask.controller.fragment.ShimmerRecyclerViewFragment;
import project.jerry.snapask.controller.fragment.TestViewFragment;
import project.jerry.snapask.controller.fragment.WebViewFragment;

public class LauncherActivity extends BaseActivity {

    private ImageView mToggle;
    private CoordinatorLayout mMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mMainContainer = (CoordinatorLayout) findViewById(R.id.main_container);
        setSupportActionBar(toolbar);
        showSnackBar();
        initToggle();
        getScreen();



        BaseFragment pagerFragment = new WebViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, pagerFragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.d("jerry_", "onKeyLongPress: ");
            // do your stuff here
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private void initToggle() {
        mToggle = (ImageView) findViewById(R.id.toggle_view);
        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTransition();

//                new ToolTip.Builder(getApplicationContext(), ToolTip.BELOW_ANCHOR).show(v);

            }
        });
    }

    private void showSnackBar() {
        View view = findViewById(R.id.frame_container);
        Snackbar.make(view, "Click Upper Right Button To Kick Off", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showFirstPage();
//            showShimmerPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFirstPage() {
        BaseFragment pagerFragment = new ClassViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, pagerFragment);
        transaction.commit();
    }

    private void showShimmerPage() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("shimmer_fragment");
        if (fragment == null) {
            fragment = ShimmerRecyclerViewFragment.newInstance();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame_container, fragment, "shimmer_fragment");
            ft.commit();
        }
    }

    public void activityTransition(View sharedView) {
        Intent intent = new Intent(LauncherActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.photo_url), (String) sharedView.getTag(R.id.image_loading));
        String transitionName = getString(R.string.transition_name);
        ActivityOptions transitionActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(LauncherActivity.this, sharedView, transitionName);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    public void toggleTransition() {
        Intent intent = new Intent(LauncherActivity.this, ToggleActivity.class);
//        FabTransform.addExtras(login, ContextCompat.getColor(DribbbleShot.this, R
//                .color.dribbble), R.drawable.ic_heart_empty_56dp);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                (LauncherActivity.this, mToggle, getString(R.string.transition_name_toggle));
        startActivity(intent, options.toBundle());
    }

    private static int mScreenHeight;
    private static int mScreenWidth;
    public void getScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return mScreenHeight;
    }

    public static int getScreenWidth() {
        return mScreenWidth;
    }



}

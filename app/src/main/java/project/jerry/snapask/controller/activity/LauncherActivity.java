package project.jerry.snapask.controller.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import project.jerry.snapask.R;
import project.jerry.snapask.controller.fragment.BaseFragment;
import project.jerry.snapask.controller.fragment.ClassViewFragment;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showSnackBar();
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

    public void activityTransition(View sharedView) {
        Intent intent = new Intent(LauncherActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.photo_url), (String) sharedView.getTag(R.id.image_loading));
        String transitionName = getString(R.string.transition_name);
        ActivityOptions transitionActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(LauncherActivity.this, sharedView, transitionName);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

}

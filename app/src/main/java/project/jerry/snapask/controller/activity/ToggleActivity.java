package project.jerry.snapask.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import project.jerry.snapask.R;

/**
 * Created by Migme_Jerry on 2017/5/25.
 */

public class ToggleActivity extends BaseActivity {

    private static final String TAG = "ToggleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d("ToggleActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);
    }

    public void dismiss(View view) {
        finishAfterTransition();
    }

}

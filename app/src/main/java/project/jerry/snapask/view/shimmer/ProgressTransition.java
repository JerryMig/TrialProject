package project.jerry.snapask.view.shimmer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.util.IntProperty;
import android.util.Log;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by Jerry on 2017/12/3.
 */

public class ProgressTransition extends Transition {

    private static final String PROPNAME_PROGRESS = "ProgressTransition:progress";

    /**
     * Property is like a helper that contain setter and getter in one place
     */
    private static final Property<ProgressBar, Integer> PROGRESS_PROPERTY =
            new IntProperty<ProgressBar>(PROPNAME_PROGRESS) {

                @Override
                public void setValue(ProgressBar progressBar, int value) {
                    Log.d("jerry_", "setValue is called.");
                    progressBar.setProgress(value);
                }

                @Override
                public Integer get(ProgressBar progressBar) {
                    Log.d("jerry_", "get is called.");
                    return progressBar.getProgress();
                }
            };

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        Log.d("jerry_", "captureStartValues is called.");
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof ProgressBar) {
            // save current progress in the values map
            ProgressBar progressBar = ((ProgressBar) transitionValues.view);
            transitionValues.values.put(PROPNAME_PROGRESS, progressBar.getProgress());
        }
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues != null && endValues != null && endValues.view instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) endValues.view;
            int start = (Integer) startValues.values.get(PROPNAME_PROGRESS);
            int end = (Integer) endValues.values.get(PROPNAME_PROGRESS);
            if (start != end) {
                // first of all we need to apply the start value, because right now
                // the view has end value
                progressBar.setProgress(start);
                // create animator with our progressBar, property and end value
                return ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, start, end, 20);
            }
        }
        return null;
    }
}

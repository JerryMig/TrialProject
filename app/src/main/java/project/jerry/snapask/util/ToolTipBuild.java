//package project.jerry.snapask.util;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.graphics.drawable.ColorDrawable;
//import android.support.annotation.NonNull;
//import android.text.TextUtils;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import project.jerry.snapask.R;
//
///**
// * Created by Jerry on 2017/11/17.
// */
//
//public class ToolTipBuild {
//
//    public static final int ONE_LINER = 0;
//    public static final int TWO_LINER = 1;
//    public static final int ONE_LINER_SP14_REGULAR = 2;
//
//    public static final int LEFT_ARROW = 1;
//    public static final int UP_ARROW = 2;
//    public static final int DOWN_ARROW = 3;
//
//    public interface TutorialViewListener {
//        public void onTutorialViewDismiss();
//    }
//
//    public static class Builder {
//
//        private Activity mActivity;
//
//        private TutorialViewListener mTutorialViewListener;
//        private Toast mToast;
//        private PopupWindow mPopupWindow;
//
//        private String mToastText = "No text set !";
//        private int mLinerNumber = ONE_LINER;
//        private int mDuration = Toast.LENGTH_SHORT;
//        private int mDefaultColour = R.color.black;
//        private boolean mIsShowing;
//
//        private RelativeLayout mMainContainer;
//        private RelativeLayout mRecView;
//        private ImageView mArrowView;
//        private ImageView mCloseTab;
//        private TextView mTextView;
//
//        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                switch (viewId) {
//                    case R.id.main_container:
//                    case R.id.close_tab:
//                        if (mPopupWindow != null) {
//                            mPopupWindow.dismiss();
//                        }
//                        sendOnTutorialViewDismiss();
//                        break;
//                }
//            }
//        };
//
//        private void sendOnTutorialViewDismiss() {
//            mIsShowing = false;
//            if (mTutorialViewListener != null) {
//                mTutorialViewListener.onTutorialViewDismiss();
//            }
//        }
//
//        public Builder(@NonNull Activity activity) {
//            mActivity = activity;
//            if (activity != null) {
//                inflateView();
//            }
//        }
//
//        public Builder setTutorialViewListener(TutorialViewListener listener) {
//            mTutorialViewListener = listener;
//            return this;
//        }
//
//        /** Example -
//         * ToolTipBuild.Builder builder = new ToolTipBuild.Builder(getActivity());
//         * builder.setUpCustomToast(Gravity.TOP, 0, 50)
//         *        .setNumLine(OverlayTutorialViewBuilder.ONE_LINER)
//         *        .setText("Shared to Moments!")
//         *        .setBgColour(R.color.color17)
//         *        .setShowCloseTab(View.GONE)
//         *        .show();
//         **/
////        public Builder setupCustomToast(int gravity, int x, int y) {
////            mToast = new Toast(mActivity);
////            mToast.setGravity(gravity, x, y);
////            mToast.setView(mMainContainer);
////            if (mTextView != null) {
////                mTextView.setPadding(ApplicationEx.getDimension(R.dimen.medium_margin), 0, 0, 0);
////            }
////            return this;
////        }
////
////        public Builder setupDefaultToast() {
////            setupCustomToast(Gravity.BOTTOM, 0, ApplicationEx.getDimension(R.dimen.xlarge_margin));
////            this.setBgResource(R.drawable.hint_alert_bg);
////            this.setShowCloseTab(View.GONE);
////            this.setHorizontalPadding(R.dimen.fragment_signuppassword_margin_36dp);
////            return this;
////        }
////
////        public Builder setupDefaultToastWithCenterText() {
////            mToast = new Toast(mActivity);
////            mToast.setGravity(Gravity.BOTTOM, 0, ApplicationEx.getDimension(R.dimen.xlarge_margin));
////            mToast.setView(mMainContainer);
////            this.setBgResource(R.drawable.hint_alert_bg);
////            this.setShowCloseTab(View.GONE);
////            this.setHorizontalPadding(R.dimen.fragment_validatecheckmail_8dp);
////            if (mTextView != null) {
////                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTextView.getLayoutParams();
////                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
////                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, ApplicationEx.getDimension(R.dimen.fragment_validateenterphone_10dp));
////                mTextView.setLayoutParams(layoutParams);
////            }
////            return this;
////        }
//
////        public Builder setupGreyToastWithCenterText() {
////            mToast = new Toast(mActivity);
////            mToast.setGravity(Gravity.BOTTOM, 0, ApplicationEx.getDimension(R.dimen.xlarge_margin));
////            mToast.setView(mMainContainer);
////            this.setBgResource(R.drawable.hint_alert_bg_grey);
////            this.setShowCloseTab(View.GONE);
////            this.setHorizontalPadding(R.dimen.fragment_validatecheckmail_8dp);
////            if (mTextView != null) {
////                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTextView.getLayoutParams();
////                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
////                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, ApplicationEx.getDimension(R.dimen.fragment_validateenterphone_10dp));
////                mTextView.setLayoutParams(layoutParams);
////            }
////            return this;
////        }
//
//        /** Example -
//         * ToolTipBuild.Builder builder = new ToolTipBuild.Builder(getActivity());
//         * builder.setupPopupWindow(rootview, Gravity.TOP, ApplicationEx.getDimension(R.dimen.dp_235) - 20)
//         *        .setNumLine(OverlayTutorialViewBuilder.TWO_LINER)
//         *        .setText("We have a new Achievement page dedicated just for you! Tap on your Name to view!")
//         *        .setShowArrow(View.VISIBLE, OverlayTutorialViewBuilder.DOWN_ARROW, R.dimen.dp_235)
//         *        .setShowCloseTab(View.VISIBLE);
//         **/
//        public Builder setupPopupWindow(final View rootView, final int gravity, final int offsetY) {
//            if (mMainContainer != null) {
//                mPopupWindow = new PopupWindow(mMainContainer);
//                mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//                mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                mPopupWindow.setOutsideTouchable(true);
//                mPopupWindow.setBackgroundDrawable(new ColorDrawable(ApplicationEx.getColorResource(R.color.transparent)));
//
////                setNumLine(OverlayTutorialViewBuilder.TWO_LINER);
//                setBgResource(R.drawable.tooltip_bg);
//                setHorizontalPadding(R.dimen.normal_margin);
//
//                mIsShowing = true;
//                if (rootView != null) {
//                    mMainContainer.post(new Runnable() {
//                        public void run() {
//                            try {
//                                if (mIsShowing) {
//                                    mPopupWindow.showAtLocation(rootView, gravity, 0, offsetY);
//                                } else {
//                                    mPopupWindow.dismiss();
//                                }
//                            } catch (IllegalStateException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//                mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        sendOnTutorialViewDismiss();
//                    }
//                });
//            }
//            return this;
//        }
//
//        @SuppressLint("InflateParams")
//        private void inflateView() {
//            LayoutInflater inflater = LayoutInflater.from(mActivity);
//            mMainContainer = (RelativeLayout) inflater.inflate(R.layout.overlay_toast_layout, null);
//            mRecView = (RelativeLayout) mMainContainer.findViewById(R.id.toast_rec_view);
//            mRecView.setBackgroundColor(ApplicationEx.getColorResource(mDefaultColour));
//            mArrowView = (ImageView) mMainContainer.findViewById(R.id.arrow_view);
//            mCloseTab = (ImageView) mMainContainer.findViewById(R.id.close_tab);
//            mCloseTab.setOnClickListener(mOnClickListener);
//            mTextView = (StyleTextView) mMainContainer.findViewById(R.id.toast_text);
//            mTextView.setText(mToastText);
//        }
//
////        public Builder setNumLine(int number) {
////            mLinerNumber = number;
////            return this;
////        }
//
//        public Builder setBgColour(int color) {
//            if (mRecView != null) {
//                int colour = ApplicationEx.getColorResource(color);
//                mRecView.setBackgroundColor(colour);
//            }
//            return this;
//        }
//
//        public Builder setBgResource(int drawable) {
//            if (mRecView != null) {
//                mRecView.setBackgroundResource(drawable);
//            }
//            return this;
//        }
//
//        public Builder setHorizontalPadding(int dimenId) {
//            if (mMainContainer != null) {
//                int dimen = ApplicationEx.getDimension(dimenId);
//                mMainContainer.setPadding(dimen, 0, dimen, 0);
//            }
//            return this;
//        }
//
//        public Builder setDuration(int duration) {
//            mDuration = duration;
//            return this;
//        }
//
//        public Builder setText(String text) {
//            mToastText = text;
//            if (mTextView != null) {
//                mTextView.setText(mToastText);
//                switch (mLinerNumber) {
//                    case ONE_LINER:
//                        mTextView.setMaxLines(1);
//                        mTextView.setTextSize(16);
//                        mTextView.setFontStyle(StyleTextView.FontStyle.BOLD);
//                        break;
//                    case TWO_LINER:
//                        mTextView.setMaxLines(2);
//                        mTextView.setTextSize(12);
//                        mTextView.setFontStyle(StyleTextView.FontStyle.REGULAR);
//                        break;
//                    case ONE_LINER_SP14_REGULAR:
//                        mTextView.setSingleLine();
//                        mTextView.setEllipsize(TextUtils.TruncateAt.END);
//                        mTextView.setTextSize(14);
//                        mTextView.setFontStyle(StyleTextView.FontStyle.REGULAR);
//                        break;
//                }
//            }
//            return this;
//        }
//
//        public Builder setShowCloseTab(int visibility) {
//            if (mCloseTab != null) {
//                mCloseTab.setVisibility(visibility);
//            }
//            return this;
//        }
//
//        public Builder setShowArrow(int visibility, int type, int marginLeft) {
//            if (mArrowView != null && mRecView != null) {
//                mArrowView.setVisibility(visibility);
//                if (visibility == View.VISIBLE) {
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRecView.getLayoutParams();
//                    RelativeLayout.LayoutParams arrowParam = (RelativeLayout.LayoutParams) mArrowView.getLayoutParams();
//                    switch (type) {
//                        case UP_ARROW:
//                            arrowParam.leftMargin = marginLeft;
//                            params.topMargin = ApplicationEx.getDimension(R.dimen.dp_4);
//                            break;
//                        case DOWN_ARROW:
//                            arrowParam.leftMargin = marginLeft;
//                            arrowParam.topMargin = ApplicationEx.getDimension(R.dimen.dp_57);
//                            mArrowView.setRotation(180);
//                            break;
//                        case LEFT_ARROW:
//                            arrowParam.leftMargin = marginLeft;
//                            arrowParam.topMargin = ApplicationEx.getDimension(R.dimen.dp_57);
//                            mArrowView.setRotation(90);
//                            break;
//                    }
//                }
//            }
//            return this;
//        }
//
//        public Builder setAnchorView(final View view, final int offsetX, final int offsetY) {
//            view.post(new Runnable() {
//                public void run() {
//                    try {
//                        mPopupWindow.showAsDropDown(view, offsetX, offsetY);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            return this;
//        }
//
//        public boolean isShowing() {
//            return mIsShowing;
//        }
//
//        public void show() {
//            if (mToast != null) {
//                mToast.setDuration(mDuration);
//                mToast.show();
//            }
//        }
//
//        public void forceDismiss() {
//            if (mPopupWindow != null) {
//                mTutorialViewListener = null;
//                mPopupWindow.dismiss();
//            }
//        }
//    }
//
//
//}

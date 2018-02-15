package project.jerry.snapask.controller.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import project.jerry.snapask.R;

/**
 * Created by Jerry on 2018/2/12.
 */

public class WebViewFragment extends BaseFragment {

    private final String TAG = WebViewFragment.class.getSimpleName();
    private WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWebView = (WebView) view.findViewById(R.id.in_app_browser);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Log.d(TAG, "enabled ");
            WebView.setWebContentsDebuggingEnabled(true);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "PageFinished: " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d(TAG, "old onReceivedError onReceivedError: " + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d(TAG, "onReceivedSslError: " + error.toString());
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.d(TAG, "onReceivedHttpError: " + errorResponse.toString());
                super.onReceivedHttpError(view, request, errorResponse);
            }

        });

        Log.d(TAG, "getUserAgentString: " + mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl("https://www.google.com.tw/?gws_rd=ssl");
    }
}

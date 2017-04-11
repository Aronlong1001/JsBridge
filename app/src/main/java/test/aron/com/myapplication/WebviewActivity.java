package test.aron.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.bl.sdk.page.web.BLSBaseWebPage;
import com.bl.sdk.page.web.WebViewJavascriptBridge;

/**
 * Created by Aron on 2017/2/20.
 */
public class WebviewActivity extends BLSBaseWebPage {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.putExtra("pageId", "home");
        setIntent(intent);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.basepage_webview);
        mWebView.setWebContentsDebuggingEnabled(true);//chrome debug pattern
    }

    @Override
    protected void setupNaviBar() {
        //TODO:
    }

    @Override
    protected void registerInterfaces(WebViewJavascriptBridge bridge) {
        super.registerInterfaces(bridge);
        bridge.registerHandler("handleTest", new WebViewJavascriptBridge.WVJBHandler() {
            @Override
            public void handle(String s, WebViewJavascriptBridge.WVJBResponseCallback wvjbResponseCallback) {
                Log.e("handle: ", "skfasdflkasdjlkfjasdklfjasdfjlskdjfl");
            }
        });
    }
}

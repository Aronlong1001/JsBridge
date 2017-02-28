package test.aron.com.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.uuzuche.lib_zxing.activity.CodeUtils;


public class MainActivity extends AppCompatActivity {

    private BridgeWebView bridgeWebView;
    private Button callJs;
    private CallBackFunction callBackFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bridgeWebView = (BridgeWebView) findViewById(R.id.jsBridgewebview);
        bridgeWebView.setWebContentsDebuggingEnabled(true);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebChromeClient(new WebChromeClient());
        bridgeWebView.setWebViewClient(new MyWebViewClient(bridgeWebView));
        bridgeWebView.loadUrl("file:///android_asset/demo.html");

        callJs = (Button) findViewById(R.id.callJs);
        callJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bridgeWebView.callHandler("fromAndroid", "data from android", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Log.e("from js", data);
                    }
                });
            }
        });

        //        第一个：订阅的方法名
        //        第二个: 回调Handler , 参数返回js请求的resqustData,function.onCallBack（）回调到js，调用function(responseData)
        bridgeWebView.registerHandler("handleScan", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                callBackFunction = function;
                Intent intent = new Intent(MainActivity.this, CustomCaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        bridgeWebView.registerHandler("test", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                callBackFunction = function;
                Intent intent = new Intent(MainActivity.this, CustomCaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView bridgeWebView) {
            super(bridgeWebView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || data.equals("")) {
            return;
        }
        if (resultCode == RESULT_OK) {
            String code = data.getExtras().getString(CodeUtils.RESULT_STRING);
            if (callBackFunction != null) {
                callBackFunction.onCallBack(code);
                callBackFunction = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}

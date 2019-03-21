package com.excel.earthhourapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.excel.configuration.ConfigurationReader;

import java.io.File;

public class MainActivity extends Activity {

    WebView wv_open_page;
    Context context = this;
    SharedPreferences spfs;
    ConfigurationReader cr;

    final static String TAG = "MainActivity";


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        init();

        String URL = "file:///android_asset/flipclock-standalone/earth-hour.html";

        Log.i( TAG, URL );

        wv_open_page.loadUrl( URL );

        setContentView( wv_open_page );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        wv_open_page.clearCache( true );

        context.deleteDatabase( "webview.db" );
        context.deleteDatabase( "webviewCache.db" );

        File dir = getCacheDir();

        if ( dir != null && dir.isDirectory() ){
            try{
                File[] children = dir.listFiles();
                if ( children.length > 0 ){
                    for ( int i = 0; i < children.length; i++ ){
                        File[] temp = children[ i ].listFiles();
                        for ( int x = 0; x < temp.length; x++ ){
                            temp[ x ].delete();
                        }
                    }
                }
            }
            catch ( Exception e ){
                Log.e( TAG, "failed cache clean" );
            }
        }
        Log.i( TAG, "onDestroy()" );
    }

    private void init(){
        wv_open_page = new WebView( this );
        wv_open_page.getSettings().setJavaScriptEnabled( true );
        wv_open_page.getSettings().setAppCacheEnabled( false );

        //cr = ConfigurationReader.getInstance();

        final Activity activity = this;

        wv_open_page.setWebViewClient( new WebViewClient() {

            ProgressDialog p;

            public void onReceivedError( WebView view, int errorCode, String description, String failingUrl ) {
                wv_open_page.loadUrl( "file:///android_asset/maintenance/maintenance.html" );
            }

            @Override
            public void onPageStarted( WebView view, String url, Bitmap favicon ) {
                super.onPageStarted( view, url, favicon );
            }

            @Override
            public void onPageFinished( WebView view, String url ) {
                super.onPageFinished( view, url );
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i( TAG, "onPause()" );

        finish();
    }
}

package com.android.myweibo;

import android.app.Activity;
import android.os.Bundle;
import com.sina.weibo.sdk.openapi.StatusesAPI;
/**
 * Created by Duke on 9/4/2016.
 */
public class ContentActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}

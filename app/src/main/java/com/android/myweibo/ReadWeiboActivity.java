package com.android.myweibo;

/**
 * Created by Duke on 9/11/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.android.myweibo.AccessTokenKeeper;
import com.android.myweibo.Constants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

import java.io.File;

public class ReadWeiboActivity extends Activity  {
    private static final String TAG = ReadWeiboActivity.class.getName();

    /** UI 元素：ListView */
    private ListView mWeiboTimeLineListView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TimeLineAdapter mAdpter;

    private StatusList mList;
    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用于获取微博信息流等操作的API */
    private StatusesAPI mStatusesAPI;

    /**
     * @see {@link Activity#onCreate}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readweibo_list);
        initImageLoader(getApplicationContext());

        // 初始化功能列表 ListView
        mWeiboTimeLineListView = (ListView)findViewById(R.id.api_func_list);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 对statusAPI实例化
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);
        if (mAccessToken != null && mAccessToken.isSessionValid()) {
            mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
        }
        else{
            Toast.makeText(ReadWeiboActivity.this,
                    "AccessToken is empty or invalid,please update the token.",
                    Toast.LENGTH_LONG).show();
            }
        }
    private void initImageLoader(Context context) {
        // TODO Auto-generated method stub
        ImageLoaderConfiguration config =  ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(config);
    }

/*    *//**
     * @see {@link AdapterView.OnItemClickListener#onItemClick}
     *//*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view instanceof TextView) {
            if (mAccessToken != null && mAccessToken.isSessionValid()) {
                switch (position) {
                    case 0:
                        mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
                        break;

                    case 1:
                        mStatusesAPI.mentions(0L, 0L, 10, 1, 0, 0, 0, false, mListener);
                        break;

                    case 2:
                        mStatusesAPI.update("发送一条纯文字微博", null, null, mListener);
                        break;

                    case 3:
                        Drawable drawable = getResources().getDrawable(R.drawable.ic_com_sina_weibo_sdk_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        mStatusesAPI.upload("发送一条带本地图片的微博", bitmap, null, null, mListener);

                        *//** 可以自行拼接参数，异步请求数据 *//*
                    *//*
                    WeiboParameters wbparams = new WeiboParameters();
                    wbparams.put("access_token", mAccessToken.getToken());
                    wbparams.put("status",       "通过API发送微博-upload");
                    wbparams.put("visible",      "0");
                    wbparams.put("list_id",      "");
                    wbparams.put("pic",          bitmap);
                    wbparams.put("lat",          "14.5");
                    wbparams.put("long",         "23.0");
                    wbparams.put("annotations",  "");

                    AsyncWeiboRunner.requestAsync(
                            "https://api.weibo.com/2/statuses/upload.json",
                            wbparams,
                            "POST",
                            mListener);*//*
                        break;

                    case 4:
                        String photoURL = "http://hiphotos.baidu.com/lvpics/pic/item/b25aae51bc7a3474377abe75.jpg";
                        // 请注意：该接口暂不支持发布多图，即参数pic_id不可用（只能通过BD合作接入，不对个人申请）
                        mStatusesAPI.uploadUrlText("发送一条带网络图片的微博", photoURL, null, null, null, mListener);
                        break;

                    default:
                        break;
                }
            } else {
                Toast.makeText(ReadWeiboActivity.this,
                        R.string.weibosdk_demo_access_token_is_empty,
                        Toast.LENGTH_LONG).show();
            }
        }
    }*/

    /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                   // 调用 StatusList#parse 解析字符串成微博列表对象

                    mList = StatusList.parse(response);
                    mSwipeRefreshLayout.setRefreshing(false);
                    //mSwipeRefreshLayout.setLoading(false);
                    Log.i("sdf", mList.toString());
                    if (mList != null && mList.total_number > 0) {
                        // mTimeLineDao.add(mList.statusList);
                        mAdpter = new TimeLineAdapter(ReadWeiboActivity.this, mList);
                        mWeiboTimeLineListView.setAdapter(mAdpter);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                   // mSwipeRefreshLayout.setLoading(false);
                } else if (response.startsWith("{\"created_at\"")) {
                    // 调用 Status#parse 解析字符串成微博对象
                    Status status = Status.parse(response);
                    Toast.makeText(ReadWeiboActivity.this,
                            "发送一送微博成功, id = " + status.id,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ReadWeiboActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            LogUtil.e(TAG, e.getMessage());
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(ReadWeiboActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };
}
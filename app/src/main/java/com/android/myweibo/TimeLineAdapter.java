package com.android.myweibo;

/**
 * Created by Duke on 9/19/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.FavoritesAPI;
import com.sina.weibo.sdk.openapi.models.FavoriteList;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

public class TimeLineAdapter extends BaseAdapter {

    private FavoritesAPI mFavouritesAPI;
    private Oauth2AccessToken mAccessToken;

    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 微博信息列表
     */
    private StatusList mStatusList = new StatusList();

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default_image)
            .showImageOnFail(R.drawable.ic_default_image)
            .bitmapConfig(Bitmap.Config.ARGB_8888).cacheInMemory(true)
            .cacheOnDisk(true).build();

    public TimeLineAdapter(Context context, StatusList list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mStatusList = list;
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        mFavouritesAPI = new FavoritesAPI(context, Constants.APP_KEY,
                mAccessToken);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mStatusList.statusList == null ? 0 : mStatusList.statusList
                .size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mStatusList == null ? null : mStatusList.statusList
                .get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.weibo_item_listview, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.rly_content = (RelativeLayout) convertView
                    .findViewById(R.id.rly_content);
            viewHolder.lly_comments = (LinearLayout) convertView
                    .findViewById(R.id.lly_comment);
            viewHolder.lly_repost = (LinearLayout) convertView
                    .findViewById(R.id.lly_repost);

            viewHolder.roundImageView = (RoundedImageView) convertView
                    .findViewById(R.id.iv_head);
            viewHolder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            viewHolder.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            viewHolder.tv_reContent = (TextView) convertView
                    .findViewById(R.id.tv_recontent);
            viewHolder.tv_like = (TextView) convertView
                    .findViewById(R.id.tv_like);
            viewHolder.tv_repost = (TextView) convertView
                    .findViewById(R.id.tv_repost);
            viewHolder.tv_comment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            viewHolder.iv_menu = (ImageView) convertView
                    .findViewById(R.id.iv_menu);
            viewHolder.view_line = (View) convertView
                    .findViewById(R.id.view_line);
            viewHolder.nineGridView = (NineGridImageView) convertView
                    .findViewById(R.id.grid_image);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Status mStatus = mStatusList.statusList.get(position);

        ImageLoader.getInstance().displayImage(mStatus.user.avatar_hd,
                viewHolder.roundImageView, options);
        viewHolder.tv_name.setText(mStatus.user.screen_name);

        String time = "";
        if (!TextUtils.isEmpty(mStatus.created_at)) {
            time = "";
        }
        String from = "";
        if (!TextUtils.isEmpty(mStatus.source)) {
            from = String.format("%s", Html.fromHtml(mStatus.source));
        }
        viewHolder.tv_time.setText(time + " " + from);
        //TODO April-->next time 1. to find the WeiboAutolinkUtil
        viewHolder.tv_content.setText("");
        if (mStatus.retweeted_status != null) {
            viewHolder.tv_reContent.setVisibility(View.VISIBLE);
            viewHolder.view_line.setVisibility(View.VISIBLE);
            String reUserName = mStatus.retweeted_status.user.screen_name;
            //TODO April-->next time 1. to find the WeiboAutolinkUtil
            viewHolder.tv_reContent.setText("");
            viewHolder.nineGridView.setVisibility(View.VISIBLE);
            viewHolder.nineGridView
                    .setAdapter(new NineGridImageViewAdapter<String>() {

                        @Override
                        protected void onDisplayImage(Context context,
                                                      ImageView imageView, String t) {
                            // TODO Auto-generated method stub
                            ImageLoader.getInstance().displayImage(t,
                                    imageView, options);
                        }

                        protected void onItemImageClick(Context context,
                                                        int index, java.util.List<String> list) {

                            Intent intent = new Intent(mContext,
                                    HomeActivity.class);//TODO April-->next time 1 . need to implement an activity to show photo details.
                            //TODO April-->next time 1
/*                            if (mStatus.retweeted_status != null) {
                                MyApplication.getInstance().setStatus(
                                        mStatus.retweeted_status);
                            } else {
                                if (mStatus.pic_urls != null) {
                                    MyApplication.getInstance().setStatus(
                                            mStatus);
                                }
                            }*/
                            intent.putExtra("index", index);
                            mContext.startActivity(intent);
                        };
                    });
            viewHolder.nineGridView
                    .setImagesData(mStatus.retweeted_status.pic_urls);
        } else {
            if (mStatus.pic_urls != null) {
                viewHolder.tv_reContent.setVisibility(View.GONE);
                viewHolder.view_line.setVisibility(View.GONE);
                viewHolder.nineGridView.setVisibility(View.VISIBLE);
                viewHolder.nineGridView
                        .setAdapter(new NineGridImageViewAdapter<String>() {

                            @Override
                            protected void onDisplayImage(Context context,
                                                          ImageView imageView, String t) {
                                // TODO Auto-generated method stub
                                ImageLoader.getInstance().displayImage(t,
                                        imageView, options);
                            }

                            protected void onItemImageClick(Context context,
                                                            int index, java.util.List<String> list) {
                                Intent intent = new Intent(mContext,
                                        HomeActivity.class);//TODO April-->next time 1 . need to implement an activity to show photo detials.
                                //TODO April-->next time 1.
/*                                if (mStatus.pic_urls != null) {
                                    MyApplication.getInstance().setStatus(
                                            mStatus);

                                }*/
                                intent.putExtra("index", index);
                                mContext.startActivity(intent);

                            };
                        });
                viewHolder.nineGridView.setImagesData(mStatus.pic_urls);

            } else {
                viewHolder.tv_reContent.setVisibility(View.GONE);
                viewHolder.view_line.setVisibility(View.GONE);
                viewHolder.nineGridView.setVisibility(View.GONE);
            }
        }
        viewHolder.tv_like.setText(mStatus.attitudes_count + "");
        viewHolder.tv_repost.setText(mStatus.reposts_count + "");
        viewHolder.tv_comment.setText(mStatus.comments_count + "");

        viewHolder.rly_content.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, HomeActivity.class);//TODO April-->next time 1. need to implement an activity to show comments
                //details.
                //TODO April-->next time 1. need to uncomment this sentence below.
                //MyApplication.getInstance().setStatus(mStatus);
                mContext.startActivity(intent);
            }
        });
        viewHolder.roundImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, HomeActivity.class);//TODO April-->next time 1. need to implements an activity to show user infomation
                //TODO April-->next time 1. need to understand the below two sentences.
/*                MyApplication.getInstance().setUser(mStatus.user);
                MyApplication.getInstance().setStatus(mStatus);*/
                intent.putExtra("From", "MainActivity");
                mContext.startActivity(intent);
            }
        });
        viewHolder.lly_comments.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, HomeActivity.class);//TODO　April-->next time 1, need to implement an activit to show publish info
/*                MyApplication.getInstance().setStatus(mStatus);
                intent.putExtra(Constants.KEY_TYPE, Constants.COMMENTS);*/
                mContext.startActivity(intent);
            }
        });

        viewHolder.lly_repost.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext, HomeActivity.class);//TODO　April-->next time 1, need to implement an activit to show publish info
/*                MyApplication.getInstance().setStatus(mStatus);
                intent.putExtra(Constants.KEY_TYPE, Constants.REPOST);*/
                mContext.startActivity(intent);
            }
        });
        viewHolder.iv_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {//TODO April--next time 1. need to uncomment this block.
/*                // TODO Auto-generated method stub
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        mContext, R.layout.item_replay_comments);

                arrayAdapter.add("收藏");
                arrayAdapter.add("取消收藏");

                ListView listView = new ListView(mContext);
                listView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                listView.setDividerHeight(0);
                listView.setAdapter(arrayAdapter);
                final MaterialDialog dialog = new MaterialDialog(mContext)
                        .setTitle(mStatus.user.screen_name).setContentView(
                                listView);
                dialog.setCanceledOnTouchOutside(true);
                listView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        switch (position) {
                            case 0:
                                long ids = Long.parseLong(mStatus.id);
                                mFavouritesAPI.create(ids, mListener);
                                break;
                            case 1:
                                long ids1 = Long.parseLong(mStatus.id);
                                mFavouritesAPI.destroy(ids1, mCancleListener);
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();*/

            }

        });
        return convertView;
    }

    class ViewHolder {
        private RelativeLayout rly_content;
        private LinearLayout lly_comments;
        private LinearLayout lly_repost;

        private RoundedImageView roundImageView;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_content;
        private TextView tv_reContent;
        private TextView tv_like;
        private TextView tv_repost;
        private TextView tv_comment;

        private View view_line;
        private ImageView iv_menu;

        private NineGridImageView nineGridView;

    }

    private RequestListener mListener = new RequestListener() {

        @Override
        public void onComplete(String response) {
            // TODO Auto-generated method stub
            if (!TextUtils.isEmpty(response)) {
                FavoriteList list = FavoriteList.parse(response);
                if (list != null) {
                    Toast.makeText(mContext, "收藏成功", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            // TODO Auto-generated method stub
            Toast.makeText(mContext, "你已经收藏过这条微博了", Toast.LENGTH_LONG).show();
        }

    };
    private RequestListener mCancleListener = new RequestListener() {

        @Override
        public void onComplete(String response) {
            // TODO Auto-generated method stub
            if (!TextUtils.isEmpty(response)) {
                FavoriteList list = FavoriteList.parse(response);
                if (list != null) {
                    Toast.makeText(mContext, "取消收藏成功", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            // TODO Auto-generated method stub
            Toast.makeText(mContext, "你还未收藏过这条微博", Toast.LENGTH_LONG).show();
        }

    };
}

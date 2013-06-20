package com.apps.game.market.activity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.entity.app.EntityComment;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.request.RequestComment;
import com.apps.game.market.request.RequestDetail;
import com.apps.game.market.request.RequestImage;
import com.apps.game.market.request.callback.RequestCallBackComment;
import com.apps.game.market.request.callback.RequestCallBackDetail;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderCommentApp;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetail extends ActivityBase implements RequestCallBackDetail {
	private EntityApp mEntityApp;
	private ImageView mAppIcon;
	private TextView mAppName;
	private TextView mAppPrice;	
	private TextView mAppSize;
	private RatingBar mAppRating;
	private TextView mAppScore;
	private TextView mAppDCount;
	private TextView mAppPCount;
	private LinearLayout mAppAction;
	private TextView mAppText;
	private ImageView mAppDownload;
	private TextView mAppDesc;
	private TextView mAppCollect;
	private TextView mAppShare;
	private ScrollView mAppIntroScrollView;
	private ListView mAppCommentListView;	
	private Gallery mAppImages;
	private FrameLayout mAppIntro;	
	private FrameLayout mAppComment;		
	private final List<ImageView> mImageViews = new Vector<ImageView>();	
	private boolean mIntro = true;
	private boolean mComment = false;
	private final DecimalFormat mFormat = new DecimalFormat("##0.0");	
	private final RequestDetail mRequestDetail = new RequestDetail(this);
	
	@Override
	protected void onAppCreate() {		
		setContentView(R.layout.app_detail);
		mEntityApp = mGlobalData.getSelectApp();
		layout();
	}
	
	@Override
	protected void onAppEntry() {		
		final String url = mEntityApp.getIcon();		
		final ImageCache imageCache = ImageCache.getInstance();
		final Bitmap bitmap = imageCache.get(url);
		if (bitmap == null) {
			RequestImage requestImage = new RequestImage(url, mAppIcon, false);
			requestImage.request();
		} else
			mAppIcon.setImageBitmap(bitmap);
						
		if (mEntityApp.getName() != null)
			mAppName.setText(mEntityApp.getName());
		
		if (mEntityApp.getPrice() != null)
			mAppPrice.setText(mEntityApp.getPrice());		
		
		final long size = mEntityApp.getSize();
		final double d = (double)size / 1024d / 1024d;
		mAppSize.setText(mFormat.format(d) + "M");
		
		mAppRating.setRating(mEntityApp.getRating());
		mAppScore.setText(String.valueOf(mEntityApp.getRating()));
		mAppDCount.setText(String.valueOf(mEntityApp.getDcount()));		
		mAppPCount.setText(String.valueOf(mEntityApp.getPcount()));
		
		final EnumAppStatus status = mEntityApp.getStatus();
		switch (status) {
			case NOINSTALL:
				mAppText.setText(getString(R.string.app_download));
				mAppAction.setVisibility(View.GONE);
				mAppDownload.setVisibility(View.VISIBLE);
				break;
				
			case INSTALL:
				mAppText.setText(getString(R.string.app_install));
				mAppAction.setVisibility(View.VISIBLE);
				mAppDownload.setVisibility(View.GONE);			
				break;
				
			case INSTALLED:
				mAppText.setText(getString(R.string.app_run));
				mAppAction.setVisibility(View.VISIBLE);
				mAppDownload.setVisibility(View.GONE);			
				break;
				
			case WAITING:
				mAppText.setText(getString(R.string.app_waiting));
				mAppAction.setVisibility(View.VISIBLE);
				mAppDownload.setVisibility(View.GONE);	
				break;
				
			case DOWNLOADING:
				mAppText.setText(getString(R.string.app_downloading));
				mAppAction.setVisibility(View.VISIBLE);
				mAppDownload.setVisibility(View.GONE);	
				break;
		}
		
		if (mEntityApp.getDetail())
			init();
		else
			mRequestDetail.request(mEntityApp);
		
		mGlobalData.addBrowseApp(mEntityApp);
	}

	@Override
	protected void onAppClose() {
		mImageViews.clear();
	}

	@Override
	protected void onAppResume() {
		
	}

	@Override
	protected void onAppClick(final View v) {
		final int id = v.getId();
		switch (id) {
			case R.id.detail_app_intro: {
				if (!mIntro) {
					mIntro = true;
					mAppCommentListView.setVisibility(View.GONE);					
					mAppIntro.setBackgroundResource(R.drawable.detail_app_intro_select);			
					mAppComment.setBackgroundResource(R.drawable.detail_app_comment);			
					mAppIntroScrollView.setVisibility(View.VISIBLE);
				}
			}
			break;
			
			case R.id.detail_app_comment: {
				if (mIntro) {
					mIntro = false;
					mAppIntroScrollView.setVisibility(View.GONE);					
					mAppIntro.setBackgroundResource(R.drawable.detail_app_intro);			
					mAppComment.setBackgroundResource(R.drawable.detail_app_comment_select);
					mAppCommentListView.setVisibility(View.VISIBLE);
					if (!mComment) {
						mAppCommentListView.setAdapter(new CommentListAdapter(this, mAppCommentListView, mEntityApp.getPid()));
						mComment = true;
					}
				}
			}
			break;
			
			case R.id.detail_app_collect: {
				final Context context = this;
				final AlertDialog.Builder builder = new Builder(this);
				final String name = mEntityApp.getName();
				final String collect = getString(R.string.detail_app_collect);
				final String confirm = getString(R.string.dialog_confirm);
				final String cancel = getString(R.string.dialog_cancel);
				builder.setMessage(collect);
				builder.setTitle(name);
				builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
						mGlobalData.addCollectApp(mEntityApp);					
						Toast.makeText(context, R.string.tip_collect_success, Toast.LENGTH_LONG).show();
					}
				});

				builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
								
				builder.create().show();
			}
			break;
		}
	}
				
	private void layout() {
		mAppIcon = (ImageView) findViewById(R.id.detail_app_icon);
		mAppName = (TextView) findViewById(R.id.detail_app_name);
		mAppPrice = (TextView) findViewById(R.id.detail_app_price);		
		mAppSize = (TextView) findViewById(R.id.detail_app_size);
		mAppRating = (RatingBar) findViewById(R.id.detail_app_rating);
		mAppScore = (TextView) findViewById(R.id.detail_app_score);
		mAppDCount = (TextView) findViewById(R.id.detail_app_dcount);
		mAppPCount = (TextView) findViewById(R.id.detail_app_pcount);		
		mAppAction = (LinearLayout) findViewById(R.id.detail_app_action);
		mAppText = (TextView) findViewById(R.id.detail_app_text);
		mAppDownload = (ImageView) findViewById(R.id.detail_app_download);		
		mAppIntro = (FrameLayout) findViewById(R.id.detail_app_intro);		
		mAppComment = (FrameLayout) findViewById(R.id.detail_app_comment);		
		mAppCollect = (TextView) findViewById(R.id.detail_app_collect);
		mAppShare = (TextView) findViewById(R.id.detail_app_share);
		mAppDesc = (TextView) findViewById(R.id.detail_app_desc);
		mAppIntroScrollView = (ScrollView) findViewById(R.id.detail_app_intro_scrollview);
		mAppCommentListView = (ListView) findViewById(R.id.detail_app_comment_listview);		
		mAppImages = (Gallery) findViewById(R.id.detail_app_images);
		
		mAppAction.setOnClickListener(this);
		mAppAction.setTag(mEntityApp);
		mAppDownload.setOnClickListener(this);
		mAppDownload.setTag(mEntityApp);
		mAppCollect.setOnClickListener(this);
		mAppShare.setOnClickListener(this);		
		mAppIntro.setOnClickListener(this);
		mAppComment.setOnClickListener(this);
		mAppName.setSelected(true);
	}
	
	private void init() {
		final String desc = mEntityApp.getDesc();
		if (desc != null)
			mAppDesc.setText(desc);
		
		final List<String> images = mEntityApp.getImages();
		if (images != null) {
			for (String image : images) {
				final ImageView imageView = new ImageView(this);				
				final Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);				
				imageView.setLayoutParams(layoutParams);				
				mImageViews.add(imageView);
				final RequestImage requestImage = new RequestImage(image, imageView, false);				
				requestImage.request();		
			}
			
			mAppImages.setAdapter(new ImageAdapter(mImageViews));
		}
	}

	@Override
	public void onCallBackDetail(boolean success) {
		if (success) 			
			init();
	}
	
	@Override
	public void onAppStatus(EntityApp entityApp) {
		if (entityApp.equals(mEntityApp)) {
			final EnumAppStatus status = entityApp.getStatus();
			switch (status) {
				case NOINSTALL:
					mAppText.setText(getString(R.string.app_download));
					mAppAction.setVisibility(View.GONE);
					mAppDownload.setVisibility(View.VISIBLE);
					break;
					
				case INSTALL:
					mAppText.setText(getString(R.string.app_install));
					mAppAction.setVisibility(View.VISIBLE);
					mAppDownload.setVisibility(View.GONE);			
					break;
					
				case INSTALLED:
					mAppText.setText(getString(R.string.app_run));
					mAppAction.setVisibility(View.VISIBLE);
					mAppDownload.setVisibility(View.GONE);			
					break;
					
				case WAITING:
					mAppText.setText(getString(R.string.app_waiting));
					mAppAction.setVisibility(View.VISIBLE);
					mAppDownload.setVisibility(View.GONE);	
					break;
					
				case DOWNLOADING:
					mAppText.setText(getString(R.string.app_downloading));
					mAppAction.setVisibility(View.VISIBLE);
					mAppDownload.setVisibility(View.GONE);	
					break;
			}
		}		
	}
}

class ImageAdapter extends BaseAdapter {
	private final List<ImageView> mImageViews;
	
	ImageAdapter(List<ImageView> imageViews) {
		mImageViews = imageViews;
	}

	@Override
	public int getCount() {		
		return mImageViews.size();
	}

	@Override
	public Object getItem(int position) {		
		return mImageViews.get(position);
	}

	@Override
	public long getItemId(int position) {
		final ImageView imageView = mImageViews.get(position);
		return imageView.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageView imageView = mImageViews.get(position);		
		return imageView;
	}	
}

class CommentListAdapter extends BaseAdapter implements OnScrollListener, RequestCallBackComment {
	private final ListView mListView;
	private final long mPageSize = 20;
	private boolean mRequest = true;
	private final LayoutInflater mInflater;
	private final List<EntityComment> mList = new Vector<EntityComment>();
	private final RequestComment mRequestComment = new RequestComment(this);
	private final Context mContext;
	private long mPid = 0;
	
	CommentListAdapter(Context context, ListView listView, long pid) {
		mContext = context;
		mListView = listView;
		mPid = pid;
		mListView.setOnScrollListener(this);
		mInflater = LayoutInflater.from(context);
		mRequestComment.request(pid, 0, mPageSize);
	}

	@Override
	public int getCount() {		
		final int size = mList.size();	
		return size;
	}

	@Override
	public Object getItem(int position) {		
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		final EntityComment entityComment =  mList.get(position);
		return entityComment.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		ViewHolderCommentApp holder = null;
		if (convertView == null) {
			holder = new ViewHolderCommentApp();
			convertView = mInflater.inflate(R.layout.app_comment, parent, false);
			holder.setNick((TextView) convertView.findViewById(R.id.app_comment_nick));		
			holder.setRating((RatingBar) convertView.findViewById(R.id.app_comment_rating));
			holder.setScore((TextView) convertView.findViewById(R.id.app_comment_score));
			holder.setContent((TextView) convertView.findViewById(R.id.app_comment_content));
			holder.setDate((TextView) convertView.findViewById(R.id.app_comment_date));
			convertView.setTag(holder);
		} else
			holder = (ViewHolderCommentApp) convertView.getTag();
		
		final EntityComment entityComment = mList.get(position);
		if (entityComment.getAuthor() != null)
			holder.getNick().setText(entityComment.getAuthor());
		else
			holder.getNick().setText("");				
		
		holder.getRating().setRating(entityComment.getRating());
		holder.getScore().setText(String.valueOf(entityComment.getRating()));
		
		if (entityComment.getComment() != null)
			holder.getContent().setText(entityComment.getComment());
		else
			holder.getContent().setText("");
		
		if (entityComment.getDate() != null)
			holder.getDate().setText(entityComment.getDate());
		else
			holder.getDate().setText("");
		
		return convertView;
	}

	@Override
	public void onCallBackComment(List<EntityComment> list, boolean finish) {
		if (list != null) {
			mList.addAll(list);
			notifyDataSetChanged();			
			mRequest = false;			
		} else {
			if (!finish)
				mRequest = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		final int count = mListView.getHeaderViewsCount();
		if (firstVisibleItem + visibleItemCount == totalItemCount && !mRequest) {			
			mRequestComment.request(mPid, totalItemCount - count, mPageSize);	
			mRequest = true;		
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}	
}


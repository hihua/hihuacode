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
import com.apps.game.market.request.RequestDownload;
import com.apps.game.market.request.RequestImage;
import com.apps.game.market.request.callback.RequestCallBackComment;
import com.apps.game.market.request.callback.RequestCallBackDetail;
import com.apps.game.market.util.ImageCache;
import com.apps.game.market.viewholder.ViewHolderCommentApp;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class ActivityDetail extends ActivityBase implements RequestCallBackDetail {
	private EntityApp mEntityApp = null;
	private ImageView mAppIcon;
	private TextView mAppName;
	private TextView mAppPrice;
	private TextView mAppType;
	private TextView mAppSize;
	private RatingBar mAppRating;
	private TextView mAppDCount;
	private TextView mAppPCount;
	private FrameLayout mAppAction;
	private TextView mAppStatus;
	private TextView mAppDesc;
	private ScrollView mAppIntroScrollView;
	private ListView mAppCommentListView;
	private TextView mAppExtTextView;
	private ImageView mAppExtImageView;
	private Gallery mAppImages;
	private FrameLayout mAppIntroFrameLayout;
	private TextView mAppIntroTextView;
	private FrameLayout mAppCommentFrameLayout;
	private TextView mAppCommentTextView;		
	private final List<ImageView> mImageViews = new Vector<ImageView>();
	private boolean mExt = false;
	private boolean mIntro = true;
	private boolean mComment = false;
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private final RequestDetail mRequestDetail = new RequestDetail(this);
	
	@Override
	protected void onAppCreate() {
		setContentView(R.layout.app_detail);		
		layout();
	}

	@Override
	protected void onAppClose() {
		mImageViews.clear();
	}

	@Override
	protected void onAppResume() {
		mEntityApp = mGlobalData.getSelectApp();
		String url = mEntityApp.getIcon();		
		ImageCache imageCache = ImageCache.getInstance();
		Drawable drawable = imageCache.get(url);
		if (drawable == null) {
			RequestImage requestImage = new RequestImage(url, mAppIcon, false);
			requestImage.request();
		} else
			mAppIcon.setImageDrawable(drawable);
						
		if (mEntityApp.getName() != null)
			mAppName.setText(mEntityApp.getName());
		
		if (mEntityApp.getPrice() != null)
			mAppPrice.setText(mEntityApp.getPrice());
		
		if (mEntityApp.getType() != null)
			mAppType.setText(mEntityApp.getType());
		
		long size = mEntityApp.getSize();
		double d = (double)size / 1024d / 1024d;
		mAppSize.setText(mFormat.format(d) + "M");
		
		mAppRating.setRating(mEntityApp.getRating());						
		mAppDCount.setText(String.valueOf(mEntityApp.getDcount()));		
		mAppPCount.setText(String.valueOf(mEntityApp.getPcount()));
		
		EnumAppStatus status = mEntityApp.getStatus();
		switch (status) {
			case NOINSTALL:
				mAppAction.setOnClickListener(this);
				mAppStatus.setText(R.string.app_download);				
				break;
				
			case INSTALL:
				mAppAction.setOnClickListener(this);
				mAppStatus.setText(R.string.app_run);
				break;
				
			case DOWNLOADING:
				mAppStatus.setText(R.string.app_downloading);				
				break;
		}
		
		if (mEntityApp.getDetail())
			init();
		else
			mRequestDetail.request(mEntityApp);		
	}

	@Override
	protected void onAppClick(final View v) {
		int id = v.getId();
		if (id == R.id.detail_app_intro_ext_textview || id == R.id.detail_app_intro_ext_imageview) {
			LayoutParams layoutParams = mAppDesc.getLayoutParams();
			if (mExt) {
				int height = getResources().getDimensionPixelSize(R.dimen.detail_app_desc_height);
				layoutParams.height = height;				
				mExt = false;
			} else {				
				layoutParams.height = LayoutParams.WRAP_CONTENT;				
				mExt = true;
			}
			
			mAppDesc.setLayoutParams(layoutParams);
			return;
		}
		
		if (id == R.id.detail_app_intro_textview && !mIntro) {
			mAppCommentListView.setVisibility(View.GONE);
			mIntro = true;
			mAppIntroFrameLayout.setBackgroundResource(R.drawable.detail_app_intro_select);
			mAppIntroTextView.setTextColor(getResources().getColor(R.color.detail_app_select));	
			mAppCommentFrameLayout.setBackgroundResource(R.drawable.detail_app_intro_no_select);
			mAppCommentTextView.setTextColor(getResources().getColor(R.color.detail_app_no_select));
			mAppIntroScrollView.setVisibility(View.VISIBLE);
			return;
		}
		
		if (id == R.id.detail_app_comment_textview && mIntro) {
			mAppIntroScrollView.setVisibility(View.GONE);
			mIntro = false;
			mAppIntroFrameLayout.setBackgroundResource(R.drawable.detail_app_intro_no_select);
			mAppIntroTextView.setTextColor(getResources().getColor(R.color.detail_app_no_select));	
			mAppCommentFrameLayout.setBackgroundResource(R.drawable.detail_app_intro_select);
			mAppCommentTextView.setTextColor(getResources().getColor(R.color.detail_app_select));
			mAppCommentListView.setVisibility(View.VISIBLE);
			if (!mComment) {
				mAppCommentListView.setAdapter(new CommentListAdapter(this, mAppCommentListView, mEntityApp.getPid()));
				mComment = true;
			}
			
			return;
		}
		
		if (id == R.id.detail_app_action) {
			EnumAppStatus status = mEntityApp.getStatus();
			if (status == EnumAppStatus.NOINSTALL) {
				View view = mInflater.inflate(R.layout.dialog_download, null);
				TextView textView = (TextView) view.findViewById(R.id.dialog_download_app_name);
				textView.setText(entityApp.getName());
				textView = (TextView) view.findViewById(R.id.dialog_download_app_size);
				long size = entityApp.getSize();
				double d = (double)size / 1024d / 1024d;
				textView.setText("文件大小: " + mFormat.format(d) + "M");
				AlertDialog.Builder builder = new Builder(mContext);			
				builder.setTitle("是否下载");
				builder.setView(view);
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
						RequestDownload requestApp = new RequestDownload(entityApp);
						requestApp.request();
						notifyDataSetChanged();
					}
				});

				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				builder.create().show();
				return;
			}
		}
	}
				
	private void layout() {
		mAppIcon = (ImageView) findViewById(R.id.detail_app_icon);
		mAppName = (TextView) findViewById(R.id.detail_app_name);
		mAppPrice = (TextView) findViewById(R.id.detail_app_price);
		mAppType = (TextView) findViewById(R.id.detail_app_type);
		mAppSize = (TextView) findViewById(R.id.detail_app_size);
		mAppRating = (RatingBar) findViewById(R.id.detail_app_rating);
		mAppDCount = (TextView) findViewById(R.id.detail_app_dcount);
		mAppPCount = (TextView) findViewById(R.id.detail_app_pcount);
		mAppAction = (FrameLayout) findViewById(R.id.detail_app_action);
		mAppStatus = (TextView) findViewById(R.id.detail_app_status);
		mAppDesc = (TextView) findViewById(R.id.detail_app_desc);
		mAppIntroScrollView = (ScrollView) findViewById(R.id.detail_app_intro_scrollview);
		mAppCommentListView = (ListView) findViewById(R.id.detail_app_comment_listview);
		mAppExtTextView = (TextView) findViewById(R.id.detail_app_intro_ext_textview);
		mAppExtImageView = (ImageView) findViewById(R.id.detail_app_intro_ext_imageview);
		mAppImages = (Gallery) findViewById(R.id.detail_app_images);
		mAppIntroFrameLayout = (FrameLayout) findViewById(R.id.detail_app_intro);
		mAppIntroTextView = (TextView) findViewById(R.id.detail_app_intro_textview);
		mAppCommentFrameLayout = (FrameLayout) findViewById(R.id.detail_app_comment);
		mAppCommentTextView = (TextView) findViewById(R.id.detail_app_comment_textview);		
		mAppExtTextView.setOnClickListener(this);
		mAppExtImageView.setOnClickListener(this);		
		mAppIntroTextView.setOnClickListener(this);
		mAppCommentTextView.setOnClickListener(this);		
	}
	
	private void init() {
		String desc = mEntityApp.getDesc();
		if (desc != null)
			mAppDesc.setText(desc);
		
		List<String> images = mEntityApp.getImages();
		if (images != null) {
			//int w = getResources().getDimensionPixelSize(R.dimen.detail_app_gallery_width);
			for (String image : images) {
				ImageView imageView = new ImageView(this);				
				Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);				
				imageView.setLayoutParams(layoutParams);				
				mImageViews.add(imageView);
				RequestImage requestImage = new RequestImage(image, imageView, false);				
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
	
	private void galleryToLeft(View parent, Gallery gallery) {
		int width = parent.getWidth();
		int w = getResources().getDimensionPixelSize(R.dimen.detail_app_gallery_width);
		int spacing = getResources().getDimensionPixelSize(R.dimen.detail_app_gallery_space);
		int offset = 0;

		if (width <= w)
			offset = width / 2 - w / 2 - spacing;
		else
			offset = width - w - 2 * spacing;
				
		MarginLayoutParams layoutParams = (MarginLayoutParams) gallery.getLayoutParams();
		layoutParams.setMargins(-offset, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
	}
}

class ImageAdapter extends BaseAdapter {
	private List<ImageView> mImageViews;
	
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
		ImageView imageView = mImageViews.get(position);
		return imageView.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = mImageViews.get(position);		
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
		int size = mList.size();	
		return size;
	}

	@Override
	public Object getItem(int position) {		
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		EntityComment entityComment =  mList.get(position);
		return entityComment.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		ViewHolderCommentApp holder = null;
		if (convertView == null) {
			holder = new ViewHolderCommentApp();
			convertView = mInflater.inflate(R.layout.app_comment, parent, false);
			holder.setNick((TextView) convertView.findViewById(R.id.app_comment_nick));	
			holder.setFloor((TextView) convertView.findViewById(R.id.app_comment_floor));
			holder.setRating((RatingBar) convertView.findViewById(R.id.app_comment_rating));
			holder.setContent((TextView) convertView.findViewById(R.id.app_comment_content));
			holder.setDate((TextView) convertView.findViewById(R.id.app_comment_date));
			convertView.setTag(holder);
		} else
			holder = (ViewHolderCommentApp) convertView.getTag();
		
		EntityComment entityComment = mList.get(position);
		if (entityComment.getAuthor() != null)
			holder.getNick().setText(entityComment.getAuthor());
		else
			holder.getNick().setText("");
						
		holder.getFloor().setText(String.valueOf(entityComment.getFloor()) + "F");
		holder.getRating().setRating(entityComment.getRating());
		
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
		int count = mListView.getHeaderViewsCount();
		if (firstVisibleItem + visibleItemCount == totalItemCount && !mRequest) {			
			mRequestComment.request(mPid, totalItemCount - count, mPageSize);	
			mRequest = true;		
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}	
}


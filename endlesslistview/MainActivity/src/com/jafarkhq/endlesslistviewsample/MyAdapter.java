package com.jafarkhq.endlesslistviewsample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zakoopi.utils.DynamicImageView;
import com.zakoopi.utils.POJO;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MyAdapter extends BaseAdapter {

	private List<POJO> mList;
	private LayoutInflater mInflater;
	Context ctx;
	ImageLoader imageLoader;

	public MyAdapter(Context context, List<POJO> list) {
		ctx = context;
		mList = list;
		imageLoader = ImageLoader.getInstance();
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.e("size", mList.size() + "");
	}

	

	public void addItems(List<POJO> newItems) {
		if (null == newItems || newItems.size() <= 0) {
			return;
		}

		if (null == mList) {
			mList = new ArrayList<POJO>();
		}

		mList.addAll(newItems);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null == mList) {
			return 0;
		}

		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		final DataObjectHolder holder = new DataObjectHolder();
		
		View result = convertView;

		//if (result == null) {

			// LayoutInflater inflater = LayoutInflater.from(ctx);
			result = mInflater.inflate(R.layout.home_feed_item_layout, parent,
					false);

			holder.user_name = (TextView) result.findViewById(R.id.user_name);
			holder.lookbook_view = (TextView) result
					.findViewById(R.id.user_view);
			holder.lookbook_like = (TextView) result
					.findViewById(R.id.txt_like_count);
			holder.user_image = (ImageView) result
					.findViewById(R.id.img_profile);
			holder.title = (TextView) result.findViewById(R.id.txt_title);
			holder.look_image = (DynamicImageView) result
					.findViewById(R.id.img_flash);
			holder.like_image = (ImageView) result.findViewById(R.id.img_like);
			holder.share_image = (ImageView) result
					.findViewById(R.id.img_share);
			holder.img1 = (ImageView) result.findViewById(R.id.post_img1);
			holder.img2 = (ImageView) result.findViewById(R.id.post_img2);
			holder.last_text = (RelativeLayout) result
					.findViewById(R.id.rel_post_img_count);
			holder.image_count = (TextView) result.findViewById(R.id.txt_count);

			holder.review = (TextView) result.findViewById(R.id.txt_review);
			holder.rel_hit = (RelativeLayout) result
					.findViewById(R.id.rel_view);
			holder.rel_store_rate = (RelativeLayout) result
					.findViewById(R.id.rel_rate);
			holder.rel_title = (RelativeLayout) result
					.findViewById(R.id.rel_title);
			holder.rel_img_count = (RelativeLayout) result
					.findViewById(R.id.rel_113);
			
			holder.bar = (ProgressBar) result.findViewById(R.id.progressBar1);
			
			result.setTag(holder);

//		} else {
//
//			holder = (DataObjectHolder) result.getTag();
//
//		}

		imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
		 
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(android.R.color.transparent)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		POJO pojo=mList.get(position);
		
		if (pojo.getMode().equals("Lookbooks")) {
			try {

				holder.review.setVisibility(View.GONE);
				holder.look_image.setVisibility(View.VISIBLE);
				holder.rel_store_rate.setVisibility(View.GONE);

				holder.user_name.setText(pojo.getUsername());

				ImageLoader.getInstance().displayImage(
						pojo.getUserimg(), holder.user_image, options);

				holder.lookbook_view.setText(pojo.getHits());
				holder.title.setText(pojo.getTitle());
				
				
//				ImageLoader.getInstance().displayImage(
//						pojo.getLookimg(), holder.look_image, options);
				
				ImageLoader.getInstance().displayImage(pojo.getLookimg(), holder.look_image, options, new ImageLoadingListener() {
				    @Override
				    public void onLoadingStarted(String imageUri, View view) {
				      
				    	holder.bar.setVisibility(View.VISIBLE);
				    }
				    @Override
				    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				    	
				    	holder.bar.setVisibility(View.GONE);
				    	
				    }
				    @Override
				    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				    	holder.bar.setVisibility(View.GONE);
				    }
				    @Override
				    public void onLoadingCancelled(String imageUri, View view) {
				    	holder.bar.setVisibility(View.GONE);
				    }
			
				});
				
				ImageLoader.getInstance().displayImage(
						pojo.getImg1(), holder.img1, options);
				ImageLoader.getInstance().displayImage(
						pojo.getImg2(), holder.img2, options);
				
//				Picasso.with(ctx).load(pojo.getLookimg())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.look_image);
//				
//				Picasso.with(ctx).load(pojo.getImg1())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.img1);
//				Picasso.with(ctx).load(pojo.getImg2())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.img2);

				//holder.image_count.setText("+" + (cards.size()));

				holder.lookbook_like.setText(pojo.getLikes());


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// For article
		else if (pojo.getMode().equals("Articles")) {
			try {

				holder.review.setVisibility(View.GONE);
				holder.look_image.setVisibility(View.VISIBLE);
				holder.rel_store_rate.setVisibility(View.GONE);

				holder.user_name.setText(pojo.getUsername());

				ImageLoader.getInstance().displayImage(
						pojo.getUserimg(), holder.user_image, options);

				holder.lookbook_view.setText(pojo.getHits());
				holder.title.setText(pojo.getTitle());
				
//				Picasso.with(ctx).load(pojo.getLookimg())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.look_image);
//				
//				Picasso.with(ctx).load(pojo.getImg1())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.img1);
//				Picasso.with(ctx).load(pojo.getImg2())
//				.placeholder(R.drawable.ic_launcher)
//				.into(holder.img2);
				
				ImageLoader.getInstance().displayImage(pojo.getLookimg(), holder.look_image, options, new ImageLoadingListener() {
				    @Override
				    public void onLoadingStarted(String imageUri, View view) {
				      
				    	holder.bar.setVisibility(View.VISIBLE);
				    	
				       
				    }
				    @Override
				    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				    	holder.bar.setVisibility(View.GONE);
				    	
				    	Toast.makeText(ctx, failReason.getCause()+"", 5000).show();
				    	holder.look_image.setImageResource(R.drawable.ic_launcher);
				    	
				    }
				    @Override
				    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				    	holder.bar.setVisibility(View.GONE);
				    	
				    }
				    @Override
				    public void onLoadingCancelled(String imageUri, View view) {
				    	holder.bar.setVisibility(View.GONE);
				    }
			
				});
				
				ImageLoader.getInstance().displayImage(
						pojo.getLookimg(), holder.look_image, options);
				ImageLoader.getInstance().displayImage(
						pojo.getImg1(), holder.img1, options);
				ImageLoader.getInstance().displayImage(
						pojo.getImg2(), holder.img2, options);
				
			
				holder.lookbook_like.setText(pojo.getLikes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// for Review
		else if (pojo.getMode().equals("StoreReviews")) {

			try {

			
				holder.user_name.setText(pojo.getUsername());

				ImageLoader.getInstance().displayImage(
						pojo.getUserimg(), holder.user_image, options);

				
				holder.lookbook_like.setText(pojo.getLikes());
				holder.lookbook_view.setText(pojo.getHits());

			//	holder.title.setText(pojo.getTitle());

				holder.review.setVisibility(View.VISIBLE);
				holder.look_image.setVisibility(View.GONE);
				holder.rel_img_count.setVisibility(View.GONE);
				holder.rel_store_rate.setVisibility(View.VISIBLE);
				holder.rel_title.setVisibility(View.GONE);

				holder.review.setText(Html.fromHtml(pojo.getTitle()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// for Zakoopi Teams
		else if ((pojo.getMode().equals("Teams"))) {
			try {

				holder.review.setVisibility(View.GONE);
				holder.look_image.setVisibility(View.VISIBLE);
				holder.rel_img_count.setVisibility(View.GONE);
				holder.rel_store_rate.setVisibility(View.GONE);
				holder.rel_title.setVisibility(View.VISIBLE);
				holder.user_image.setVisibility(View.GONE);
				
				holder.user_name.setText("Zakoopi Team");
				holder.lookbook_like.setText(pojo.getLikes());
				holder.lookbook_view.setText(pojo.getHits());
				holder.title.setText(pojo.getTitle());


				imageLoader.displayImage(pojo.getLookimg(), holder.look_image, options, new ImageLoadingListener() {
				    @Override
				    public void onLoadingStarted(String imageUri, View view) {
				      
				    	holder.bar.setVisibility(View.VISIBLE);
				    }
				    @Override
				    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				    	holder.bar.setVisibility(View.GONE);
				    }
				    @Override
				    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				    	holder.bar.setVisibility(View.GONE);
				    }
				    @Override
				    public void onLoadingCancelled(String imageUri, View view) {
				    	holder.bar.setVisibility(View.GONE);
				    }
			
				});
//				ImageLoader.getInstance().displayImage(
//						pojo.getLookimg(), holder.look_image, options);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public  boolean checkURL(CharSequence input) {
	    if (TextUtils.isEmpty(input)) {
	        return false;
	    }
	    Pattern URL_PATTERN = Patterns.WEB_URL;
	    boolean isURL = URL_PATTERN.matcher(input).matches();
	    if (!isURL) {
	        String urlString = input + "";
	        if (URLUtil.isNetworkUrl(urlString)) {
	            try {
	                new URL(urlString);
	                isURL = true;
	            } catch (Exception e) {
	            }
	        }
	    }
	    return isURL;
	}
	
	public class DataObjectHolder {
		// lookbook variables
		TextView user_name;
		TextView lookbook_view, lookbook_like;
		ImageView user_image;
		TextView title;
		DynamicImageView look_image;
		ImageView like_image, share_image;
		ImageView img1, img2;
		RelativeLayout last_text, rel_hit, rel_store_rate, rel_title,
				rel_img_count;
		TextView image_count;
		TextView review;
		ProgressBar bar;

	}

}

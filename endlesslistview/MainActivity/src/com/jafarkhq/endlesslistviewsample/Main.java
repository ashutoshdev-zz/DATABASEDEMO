package com.jafarkhq.endlesslistviewsample;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jafarkhq.views.EndlessListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zakoopi.homefeed.Recent;
import com.zakoopi.homefeed.Recent_ArticleData;
import com.zakoopi.homefeed.Recent_Article_Images;
import com.zakoopi.homefeed.Recent_Article_User;
import com.zakoopi.homefeed.Recent_Lookbook_Cards;
import com.zakoopi.homefeed.Recent_Lookbook_User;
import com.zakoopi.homefeed.Recent_Lookbookdata;
import com.zakoopi.homefeed.Recent_StoreReviewData;
import com.zakoopi.homefeed.Recent_StoreReview_Store;
import com.zakoopi.homefeed.Recent_StoreReview_Users;
import com.zakoopi.homefeed.Recent_Teamsdata;
import com.zakoopi.homefeed.recentfeed;
import com.zakoopi.utils.ConnectionDetector;
import com.zakoopi.utils.POJO;

public class Main extends Activity {

	private MyAdapter adapter;
	private EndlessListView endlessListView;
	private static final String FILENAME = "myFile.txt";
	private int mCount;
	private boolean mHaveMoreDataToLoad;

	private static final String RECENT_REST_URL = "http://192.168.1.17:8000/api/feedRecent.json?page=";
	static AsyncHttpClient client = new AsyncHttpClient();
	final static int DEFAULT_TIMEOUT = 40 * 1000;
	ConnectionDetector cd;
	String text = "";
	String line = "";
	int page = 1;
	Recent_Lookbookdata look;

	private boolean isInternetPresent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {

			recent_homeFeed(page);

		} 

		mHaveMoreDataToLoad = true;

		LayoutInflater inf=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header=inf.inflate(R.layout.header, null);
		Button b1=(Button) header.findViewById(R.id.button1);
		Button b2=(Button) header.findViewById(R.id.button2);
		endlessListView = (EndlessListView) findViewById(R.id.endlessListView);
		endlessListView.addHeaderView(header);
		endlessListView.setOnLoadMoreListener(loadMoreListener);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent in=new Intent(Main.this,MainActivity.class);
				startActivity(in);
				finish();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		// scroll speed decreases as friction increases. a value of 2 worked
		// well in an emulator; i need to test it on a real device
		endlessListView.setFriction(ViewConfiguration.getScrollFriction() * 3);
	}

	private void loadMoreData() {
		// new LoadMore().execute((Void) null);
		if (isInternetPresent) {
			page++;
			recent_loadmoreFeed(page);
			Log.e("kkkk", page + "");

		} 
	}

	private EndlessListView.OnLoadMoreListener loadMoreListener = new EndlessListView.OnLoadMoreListener() {

		@Override
		public boolean onLoadMore() {
			if (true == mHaveMoreDataToLoad) {

				loadMoreData();

			} else {

				Toast.makeText(Main.this, "No more data to load",
						Toast.LENGTH_SHORT).show();
			}
			endlessListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
			return mHaveMoreDataToLoad;
		}
	};

	public void recent_homeFeed(int page) {
		Log.e("url", RECENT_REST_URL + page);
		client.setBasicAuth("a.himanshu.verma@gmail.com", "dragonvrmxt2t");
		client.get(RECENT_REST_URL + page, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				try {

					BufferedReader br = new BufferedReader(
							new InputStreamReader(new ByteArrayInputStream(
									response)));
					while ((line = br.readLine()) != null) {

						text = text + line;
					}
					
					showData(text);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}
		});

	}

	/**
	 * Recent Load More Feed
	 * 
	 * @recent_loadmoreFeed page
	 */
	public void recent_loadmoreFeed(int page) {
		Log.e("url", RECENT_REST_URL + page);
		client.get(RECENT_REST_URL + page, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				// called when response HTTP status is "200 OK"

				try {

					BufferedReader br = new BufferedReader(
							new InputStreamReader(new ByteArrayInputStream(
									response)));
					String st = "";
					String st1 = "";
					while ((st = br.readLine()) != null) {

						st1 = st1 + st;

					}
					
					showmoreData(st1);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)

				endlessListView.loadMoreCompleat();

			}

			@Override
			public void onRetry(int retryNo) {
				// called when request is retried
			}
		});

	}

	
	/**
	 * Recent Feed Show Data
	 * 
	 * @showData data
	 */
	public void showData(String data) {

		ArrayList<POJO> pojolist = new ArrayList<POJO>();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(data));
		reader.setLenient(true);
		Recent ppp = gson.fromJson(reader, Recent.class);
		List<recentfeed> feeds = ppp.getFeedRecent();
		for (int i = 0; i < feeds.size(); i++) {
			recentfeed pop = feeds.get(i);

			if (pop.getModel().equals("Lookbooks")) {

				Log.e("img", "look");
				Recent_Lookbookdata look = pop.getLookbookdata();
				Recent_Lookbook_User user = look.getUser();
				ArrayList<Recent_Lookbook_Cards> cards = look.getCards();

				if (cards.size() >= 3) {

					Recent_Lookbook_Cards ccll = cards.get(0);
					Recent_Lookbook_Cards ccll1 = cards.get(1);
					Recent_Lookbook_Cards ccll2 = cards.get(2);
					String lookimg = ccll.getMedium_img();
					String img1 = ccll1.getTiny_img();
					String img2 = ccll2.getTiny_img();
					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLookbooklike_count();
					String hits = look.getView_count();
					String title = look.getTitle();
					POJO pp = new POJO("Lookbooks", username, userimg, lookimg,
							likes, hits, title, img1, img2);
					pojolist.add(pp);
				} else if (cards.size() == 2) {

					Recent_Lookbook_Cards ccll = cards.get(0);
					Recent_Lookbook_Cards ccll1 = cards.get(1);

					String lookimg = ccll.getMedium_img();
					String img1 = ccll1.getTiny_img();

					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLookbooklike_count();
					String hits = look.getView_count();
					String title = look.getTitle();
					POJO pp = new POJO("Lookbooks", username, userimg, lookimg,
							likes, hits, title, img1, "na");
					pojolist.add(pp);
				} else {

					Recent_Lookbook_Cards ccll = cards.get(0);

					String lookimg = ccll.getMedium_img();

					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLookbooklike_count();
					String hits = look.getView_count();
					String title = look.getTitle();
					POJO pp = new POJO("Lookbooks", username, userimg, lookimg,
							likes, hits, title, "na", "na");
					pojolist.add(pp);
				}

			} else if (pop.getModel().equals("Articles")) {

				Recent_ArticleData look = pop.getArticaldata();
				Recent_Article_User user = look.getUser();
				ArrayList<Recent_Article_Images> cards = look
						.getArticle_images();

				if (cards.size() >= 3) {

					Recent_Article_Images ccll = cards.get(0);
					Recent_Article_Images ccll1 = cards.get(1);
					Recent_Article_Images ccll2 = cards.get(2);
					String lookimg = ccll.getAndroid_api_img();
					String img1 = ccll1.getAndroid_api_img();
					String img2 = ccll2.getAndroid_api_img();
					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLikes_count();
					String hits = look.getHits();
					String title = look.getTitle();
					POJO pp = new POJO("Articles", username, userimg, lookimg,
							likes, hits, title, img1, img2);
					pojolist.add(pp);

				} else if (cards.size() == 2) {

					Recent_Article_Images ccll = cards.get(0);
					Recent_Article_Images ccll1 = cards.get(1);

					String lookimg = ccll.getAndroid_api_img();
					String img1 = ccll1.getAndroid_api_img();

					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLikes_count();
					String hits = look.getHits();
					String title = look.getTitle();
					POJO pp = new POJO("Articles", username, userimg, lookimg,
							likes, hits, title, img1, "na");
					pojolist.add(pp);
				} else {

					Recent_Article_Images ccll = cards.get(0);

					String lookimg = ccll.getAndroid_api_img();

					String username = user.getFirst_name();
					String userimg = user.getAndroid_api_img();

					String likes = look.getLikes_count();
					String hits = look.getHits();
					String title = look.getTitle();
					POJO pp = new POJO("Articles", username, userimg, lookimg,
							likes, hits, title, "na", "na");
					pojolist.add(pp);
				}

			} else if (pop.getModel().equals("StoreReviews")) {

				Recent_StoreReviewData store = pop.getStorereviewdata();
				Recent_StoreReview_Users user = store.getUser();
				Recent_StoreReview_Store likes = store.getStore();

				String username = user.getFirst_name();
				String userimg = user.getAndroid_api_img();
				String lookimg = "na";
				String likes1 = likes.getLikes_count();
				String hits = store.getHits();
				String title = store.getReview();
				POJO pp = new POJO("StoreReviews", username, userimg, lookimg,
						likes1, hits, title, "na", "na");
				pojolist.add(pp);

			} else if (pop.getModel().equals("Teams")) {

				Recent_Teamsdata team = pop.getTeamsdata();

				String username = "Zakoopi Team";
				String userimg = "na";
				String lookimg = team.getAndroid_api_img();
				String likes1 = team.getLikes_count();
				String hits = team.getHits();
				String title = team.getTitle();
				POJO pp = new POJO("Teams", username, userimg, lookimg, likes1,
						hits, title, "na", "na");
				pojolist.add(pp);

			}

		}

		adapter = new MyAdapter(Main.this, pojolist);
		endlessListView.setAdapter(adapter);

		endlessListView.loadMoreCompleat();
		endlessListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		endlessListView.setVerticalScrollBarEnabled(false);
	}

	/**
	 * Recent Feed Show More Data
	 * 
	 * @showmoreData data
	 */
	public void showmoreData(String data) {

		ArrayList<POJO> pojolist = new ArrayList<POJO>();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(data));
		reader.setLenient(true);
		Recent ppp = gson.fromJson(reader, Recent.class);
		List<recentfeed> feeds = ppp.getFeedRecent();
		if (feeds.size() > 0) {
			for (int i = 0; i < feeds.size(); i++) {
				recentfeed pop = feeds.get(i);

				if (pop.getModel().equals("Lookbooks")) {

					Log.e("img", "look");
					Recent_Lookbookdata look = pop.getLookbookdata();
					if (look != null) {
						Recent_Lookbook_User user = look.getUser();
						ArrayList<Recent_Lookbook_Cards> cards = look
								.getCards();

						if (cards.size() >= 3) {

							Recent_Lookbook_Cards ccll = cards.get(0);
							Recent_Lookbook_Cards ccll1 = cards.get(1);
							Recent_Lookbook_Cards ccll2 = cards.get(2);
							String lookimg = ccll.getLarge_img();
							String img1 = ccll1.getTiny_img();
							String img2 = ccll2.getTiny_img();
							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLookbooklike_count();
							String hits = look.getView_count();
							String title = look.getTitle();
							POJO pp = new POJO("Lookbooks", username, userimg,
									lookimg, likes, hits, title, img1, img2);
							pojolist.add(pp);

						} else if (cards.size() == 2) {

							Recent_Lookbook_Cards ccll = cards.get(0);
							Recent_Lookbook_Cards ccll1 = cards.get(1);

							String lookimg = ccll.getLarge_img();
							String img1 = ccll1.getTiny_img();

							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLookbooklike_count();
							String hits = look.getView_count();
							String title = look.getTitle();
							POJO pp = new POJO("Lookbooks", username, userimg,
									lookimg, likes, hits, title, img1, "na");
							pojolist.add(pp);
						} else {

							Recent_Lookbook_Cards ccll = cards.get(0);

							String lookimg = ccll.getLarge_img();

							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLookbooklike_count();
							String hits = look.getView_count();
							String title = look.getTitle();
							POJO pp = new POJO("Lookbooks", username, userimg,
									lookimg, likes, hits, title, "na", "na");
							pojolist.add(pp);
						}
					}
				} else if (pop.getModel().equals("Articles")) {

					Recent_ArticleData look = pop.getArticaldata();
					if (look != null) {
						Recent_Article_User user = look.getUser();
						ArrayList<Recent_Article_Images> cards = look
								.getArticle_images();

						if (cards.size() >= 3) {

							Recent_Article_Images ccll = cards.get(0);
							Recent_Article_Images ccll1 = cards.get(1);
							Recent_Article_Images ccll2 = cards.get(2);
							String lookimg = ccll.getMedium_img();
							String img1 = ccll1.getTiny_img();
							String img2 = ccll2.getTiny_img();
							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLikes_count();
							String hits = look.getHits();
							String title = look.getTitle();
							POJO pp = new POJO("Articles", username, userimg,
									lookimg, likes, hits, title, img1, img2);
							pojolist.add(pp);

						} else if (cards.size() == 2) {

							Recent_Article_Images ccll = cards.get(0);
							Recent_Article_Images ccll1 = cards.get(1);

							String lookimg = ccll.getMedium_img();
							String img1 = ccll1.getTiny_img();

							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLikes_count();
							String hits = look.getHits();
							String title = look.getTitle();
							POJO pp = new POJO("Articles", username, userimg,
									lookimg, likes, hits, title, img1, "na");
							pojolist.add(pp);
						} else {

							Recent_Article_Images ccll = cards.get(0);

							String lookimg = ccll.getMedium_img();

							String username = user.getFirst_name();
							String userimg = user.getAndroid_api_img();

							String likes = look.getLikes_count();
							String hits = look.getHits();
							String title = look.getTitle();
							POJO pp = new POJO("Articles", username, userimg,
									lookimg, likes, hits, title, "na", "na");
							pojolist.add(pp);
						}
					}
				} else if (pop.getModel().equals("StoreReviews")) {

					Recent_StoreReviewData store = pop.getStorereviewdata();

					if (store != null) {
						Recent_StoreReview_Users user = store.getUser();
						Recent_StoreReview_Store likes = store.getStore();

						String username = user.getFirst_name();
						String userimg = user.getAndroid_api_img();
						String lookimg = "na";
						String likes1 = likes.getLikes_count();
						String hits = store.getHits();
						String title = store.getReview();
						POJO pp = new POJO("StoreReviews", username, userimg,
								lookimg, likes1, hits, title, "na", "na");
						pojolist.add(pp);
					}
				} else if (pop.getModel().equals("Teams")) {

					Recent_Teamsdata team = pop.getTeamsdata();

					if (team != null) {
						String username = "Zakoopi Team";
						String userimg = "na";
						String lookimg = team.getAndroid_api_img();
						String likes1 = team.getLikes_count();
						String hits = team.getHits();
						String title = team.getTitle();
						POJO pp = new POJO("Teams", username, userimg, lookimg,
								likes1, hits, title, "na", "na");
						pojolist.add(pp);
					}
				}

			}

			adapter.addItems(pojolist);
			endlessListView.loadMoreCompleat();

			endlessListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
			endlessListView.setVerticalScrollBarEnabled(false);
		}

	}

	
}

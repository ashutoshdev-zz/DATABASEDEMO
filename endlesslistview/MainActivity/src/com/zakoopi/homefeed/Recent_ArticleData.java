package com.zakoopi.homefeed;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Recent_ArticleData {

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("hits")
	private String hits;

	@SerializedName("likes_count")
	private String likes_count;
	
	ArrayList<Recent_Article_Images>article_images;
	Recent_Article_User user;
	
	@SerializedName("description")
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getLikes_count() {
		return likes_count;
	}
	public void setLikes_count(String likes_count) {
		this.likes_count = likes_count;
	}
	public ArrayList<Recent_Article_Images> getArticle_images() {
		return article_images;
	}
	public void setArticle_images(ArrayList<Recent_Article_Images> article_images) {
		this.article_images = article_images;
	}
	public Recent_Article_User getUser() {
		return user;
	}
	public void setUser(Recent_Article_User user) {
		this.user = user;
	}
	
}

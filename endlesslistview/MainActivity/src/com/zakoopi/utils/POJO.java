package com.zakoopi.utils;

public class POJO {

	String username;
	String userimg;
	String lookimg;
	String img1;
	String img2;
	String likes;
	String hits;
	String title;
	String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public POJO(String mode,String username, String userimg, String lookimg, String likes,
			String hits, String title,String img1,String img2) {
		super();
		this.mode=mode;
		this.username = username;
		this.userimg = userimg;
		this.lookimg = lookimg;
		this.likes = likes;
		this.hits = hits;
		this.title = title;
		this.img1 = img1;
		this.img2 = img2;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	public String getLookimg() {
		return lookimg;
	}

	public void setLookimg(String lookimg) {
		this.lookimg = lookimg;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getHits() {
		return hits;
	}

	public void setHits(String hits) {
		this.hits = hits;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

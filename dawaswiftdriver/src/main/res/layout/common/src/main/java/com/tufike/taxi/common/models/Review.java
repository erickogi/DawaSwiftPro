package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

public class Review{

	@SerializedName("score")
	private int score;

	@SerializedName("review")
	private String review;
	public Review(int score,String review) {
		this.score = score;
		this.review = review;
	}

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	public void setReview(String review){
		this.review = review;
	}

	public String getReview(){
		return review;
	}
}
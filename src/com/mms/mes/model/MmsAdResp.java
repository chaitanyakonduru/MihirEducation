package com.mms.mes.model;

public class MmsAdResp {

	private String mAdImageUrl;
	private String mAdNavigationUrl;

	public MmsAdResp(String mAdImageUrl, String mAdNavigationUrl) {
		super();
		this.mAdImageUrl = mAdImageUrl;
		this.mAdNavigationUrl = mAdNavigationUrl;
	}

	public String getmAdImageUrl() {
		return mAdImageUrl;
	}

	public String getmAdNavigationUrl() {
		return mAdNavigationUrl;
	}

}

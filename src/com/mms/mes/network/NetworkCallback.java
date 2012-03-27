package com.mms.mes.network;

public interface NetworkCallback<T> {
	public void onSuccess(T t);
	public void onError(String errorMessage);
}

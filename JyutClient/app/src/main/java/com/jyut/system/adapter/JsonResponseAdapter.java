/**
 * 
 */
package com.jyut.system.adapter;

import java.net.ProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.Response;

import android.util.Log;

/**
 * JsonResponse的基类,实现了NoHttp里面的OnResponseListener,
 * 重点实现了父类的onFail方法,其余方法留给子类去实现
 * @author wztscau
 * @date Sep 30, 2016
 * @project 粤盟管理系统客户端
 */
public abstract class JsonResponseAdapter<T> implements com.yolanda.nohttp.rest.OnResponseListener<T> {

	private static String TAG = "";
	public JsonResponseAdapter() {
		TAG = getClass().getSimpleName();
	}

	@Override
	public void onSucceed(int i, Response<T> response) {
		Log.i(TAG, "request succeed: ");
	}

	@Override
	public void onStart(int what) {
		// TODO Auto-generated method stub
		Log.i(TAG, "request start");
	}
	
	@Override
	public void onFailed(int what, Response<T> response) {
		Log.e(TAG, "request Failed");
		Exception exception = response.getException();
		if (exception instanceof NetworkError) {
			// 网络不好
			Log.e(TAG, "onFail:networkerror");
			onNetworkError();
		} else if (exception instanceof TimeoutError) {
			// 请求超时
			Log.e(TAG, "onFail:timeouterror");
			onTimeoutError();
		} else if (exception instanceof UnKnownHostError) {
			// 找不到服务器
			Log.e(TAG, "onFail:unknownhosterror");
			onUnKnownHostError();
		} else if (exception instanceof URLError) {
			// URL是错的
			Log.e(TAG, "onFail:urlerroer");
			onURLError();
		} else if (exception instanceof NotFoundCacheError) {
			// 这个异常只会在仅仅查找缓存时没有找到缓存时返回
			Log.e(TAG, "onFail:nofountdcacheerror");
			onNotFoundCacheError();
		} else if (exception instanceof ProtocolException) {
			// 协议错误
			Log.e(TAG, "onFail:protocolexcpetion");
			onProtocolException();
		} else if (exception instanceof ParseError) {
			// JsonObject解析异常
			Log.e(TAG, "onFail:parseerror");
			onParseError();
		} else {
			Log.e(TAG, "onFail:unknownerror");
			onUnKnownerror();
		}
		if (exception != null)
			Log.e(TAG, exception.getMessage());
		onFailFinal();
	}
	
	/* (non-Javadoc)
	 * @see com.yolanda.nohttp.rest.OnResponseListener#onFinish(int)
	 */
	@Override
	public void onFinish(int what) {
		Log.i(TAG, "request finish");
	}

	public void onNetworkError(){}
	public void onTimeoutError(){}
	public void onUnKnownHostError(){}
	public void onURLError(){}
	public void onProtocolException(){}
	public void onParseError(){}
	public void onUnKnownerror(){}
	public void onNotFoundCacheError(){}
	public void onFailFinal(){}

}

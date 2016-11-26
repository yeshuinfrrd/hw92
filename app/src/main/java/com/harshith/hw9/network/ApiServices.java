package com.harshith.hw9.network;

import android.app.Activity;

import com.harshith.hw9.models.LegislatorsResponseModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by yash on 25-11-2016.
 */

public class ApiServices {

	WeakReference<ApiResponseCallbacks> apiResponseCallbacks;

	private final String API_GATEWAY_BASE_URL="http://104.198.0.197:8080/";

	private final String LEGISLATORS_API_END_POINT = "http://104.198.0.197:8080/";

	private final String BILLS_API_END_POINT = "http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50";

	private final String COMMITTEE_API_END_POINT = "http://104.198.0.197:8080/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";

	public ApiServices(Activity activity) {
		apiResponseCallbacks=new WeakReference<ApiResponseCallbacks>((ApiResponseCallbacks)activity);
	}

	public void fetchLegislators() {
		ApiCalls apiCalls=NetworkManager.getInstance().createService(ApiCalls.class,API_GATEWAY_BASE_URL);
		Call apiResponse=apiCalls.getLegislators("legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all");
		Callback<LegislatorsResponseModel> callback= new Callback<LegislatorsResponseModel>() {
			@Override
			public void onResponse(Call<LegislatorsResponseModel> call, Response<LegislatorsResponseModel> response) {
				if(response.isSuccessful()) {
					apiResponseCallbacks.get().onFetchLegislatorsSuccessful();
				} else {
					apiResponseCallbacks.get().onFetchLegislatorsFailure();
				}
			}

			@Override
			public void onFailure(Call<LegislatorsResponseModel> call, Throwable t) {
				apiResponseCallbacks.get().onFetchLegislatorsFailure();
			}
		};
		apiResponse.enqueue(callback);
	}

	public interface ApiResponseCallbacks {
		void onFetchLegislatorsSuccessful();
		void onFetchLegislatorsFailure();
		void onFetchBillsSuccessful();
		void onFetchBillsFailure();
		void onFetchCommitteesSuccessful();
		void onFetchCommitteesFailure();
	}

	public interface ApiCalls {

		@GET
		Call<LegislatorsResponseModel> getLegislators(@Url String url);
	}
}

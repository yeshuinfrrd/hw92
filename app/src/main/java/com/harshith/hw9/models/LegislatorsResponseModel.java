package com.harshith.hw9.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yashw on 25-11-2016.
 */

public class LegislatorsResponseModel {
	@SerializedName("results")
	@Expose
	public List<Legislator> result;

	@SerializedName("count")
	@Expose
	public Integer count;

	@SerializedName("page")
	@Expose
	public Page page;
}

package com.liferay.dtx.content.targeting.rule.category;

import com.liferay.content.targeting.api.model.BaseRuleCategory;

public class LuggageStatusRuleCategory extends BaseRuleCategory {

	public static final String KEY = "dtx";

	@Override
	public String getCategoryKey() {
		return KEY;
	}

	@Override
	public String getIcon() {
		return "icon-ok-sign";
	}

}

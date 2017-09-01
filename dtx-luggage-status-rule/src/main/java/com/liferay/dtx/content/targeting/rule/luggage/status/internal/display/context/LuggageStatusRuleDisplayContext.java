package com.liferay.dtx.content.targeting.rule.luggage.status.internal.display.context;

import com.liferay.content.targeting.display.context.BaseRuleDisplayContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;

public class LuggageStatusRuleDisplayContext extends BaseRuleDisplayContext {

	public LuggageStatusRuleDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public String[] getLuggageStatuses() {
		if (luggageStatuses != null) {
			return luggageStatuses;
		}

		luggageStatuses = new String[]{"Balcão", "Esteira", "Deposito", "Carrinho", "Porão", "Extraviada"};

		return luggageStatuses;
	}

	public String getLuggageStatus() {
		if (_luggageStatus != null) {
			return _luggageStatus;
		}

		_luggageStatus = GetterUtil.getString(context.get("luggageStatus"));

		return _luggageStatus;
	}

	private String[] luggageStatuses;
	private String _luggageStatus;

}
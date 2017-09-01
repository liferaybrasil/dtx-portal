package com.liferay.dtx.content.targeting.rule.luggage.status.internal.model;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseJSPRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.UserAttributesRuleCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Component(immediate = true, service = Rule.class)
public class LuggageStatusRule extends BaseJSPRule {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(
			HttpServletRequest request, RuleInstance ruleInstance,
			AnonymousUser anonymousUser)
		throws Exception {

		try {
			if (anonymousUser.getUser() != null) {
				System.out.println("Calling external web service: https://db-dtx.wedeploy.io/luggage");
				HttpResponse<JsonNode> response = Unirest.get("https://db-dtx.wedeploy.io/luggage?sort=%5B%7B%22timestamp%22:%22desc%22%7D%5D&limit=1")
					.header("accept", "application/json")
					.asJson();
				JsonNode json = response.getBody();
				System.out.println("Response from external call: " + json);
				JSONArray array = json.getArray();
				if (array.length() > 0) {
					JSONObject object = array.getJSONObject(0);
					String status = object.getString("status");
			
					if (ruleInstance.getTypeSettings().equalsIgnoreCase(status)) {
						System.out.println("Found rule: " + status);
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	@Override
	public String getIcon() {
		return "icon-desktop";
	}

	@Override
	public String getRuleCategoryKey() {
		return UserAttributesRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale.getLanguage());

		return LanguageUtil.get(resourceBundle, ruleInstance.getTypeSettings());
	}

	@Override
	public String processRule(
		PortletRequest request, PortletResponse response, String id,
		Map<String, String> values) {

		return values.get("luggageStatus");
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dtx.content.targeting.rule.luggage.status)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(
		RuleInstance ruleInstance, Map<String, Object> context,
		Map<String, String> values) {

		String luggageStatus = StringPool.BLANK;

		if (!values.isEmpty()) {
			luggageStatus = values.get("luggageStatus");
		}
		else if (ruleInstance != null) {
			luggageStatus = ruleInstance.getTypeSettings();
		}

		context.put("luggageStatus", luggageStatus);
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.dtx.content.targeting.rule.luggage.status)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private ResourceBundleLoader _resourceBundleLoader;

}
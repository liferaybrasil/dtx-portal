/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dtx.dashboard.web.internal.portlet.action.mysites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.dtx.dashboard.web.internal.constants.DashboardConstants;
import com.liferay.dtx.dashboard.web.internal.context.DashboardURLHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=dashboard_portlet",
		"dashboard.tab.key=" + DashboardConstants.VIEW_TAB,
		"dashboard.tab.order=1",
		"mvc.command.name=/", "mvc.command.name=View"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = (Template)renderRequest.getAttribute(
			WebKeys.TEMPLATE);

		template.put("steps", _getInitialStatus());

		return "View";
	}
	
	private List<Map<String, Object>> _getInitialStatus() {
		List<Map<String, Object>> steps = new ArrayList<>();

		steps.add(_getStep("balcão"));
		steps.add(_getStep("esteira"));
		steps.add(_getStep("porão"));
		steps.add(_getStep("extraviada"));

		return steps;
	}

	private Map<String, Object> _getStep(String label) {
		Map<String, Object> step1 = new HashMap<>();
		step1.put("label", label);
		step1.put("check", false);
		step1.put("timestamp", 0);
		step1.put("time", "");
		return step1;
	}

	@Reference
	private DashboardURLHelper _dashboardURLHelper;

}
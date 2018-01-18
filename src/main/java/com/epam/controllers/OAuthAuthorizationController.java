package com.epam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@SessionAttributes("authorizationRequest")
public class OAuthAuthorizationController 
{
	@Autowired
	private ClientDetailsService clientDetailsService;

	@RequestMapping("/oauth/confirm_access")
	public String getAccessConfirmation(Map<String, Object> model) {
		AuthorizationRequest request = (AuthorizationRequest) model.remove("authorizationRequest");
		ClientDetails client = clientDetailsService.loadClientByClientId(request.getClientId());
		model.put("client", client);
		
		// request.getScope() gives us the scopes that have been requested by the client		
		Map<String, String> scopes = new LinkedHashMap<String, String>();
		for (String scope : request.getScope()) {
			scopes.put(OAuth2Utils.SCOPE_PREFIX + scope, "false");
		}
		model.put("scopes", scopes);
		return "/oauthAuthorization";
	}
}

package com.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Log logger = LogFactory.getLog(OAuth2AuthenticationSuccessHandler.class);

/*	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.debug("Authentication Success.");
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
*/
/*	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		String targetUrl = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
				.replaceQuery(null).build().toUriString();
		targetUrl = (targetUrl!=null?targetUrl:getDefaultTargetUrl());
		//String token = tokenProvider.createToken(authentication);

		logger.debug("Redirecting to: "+targetUrl);
		return UriComponentsBuilder.fromUriString(targetUrl).build().toUriString();
		//.queryParam("token", authentication.getName())
	}*/
}
package com.auth.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.auth.model.AuthUser;

public class UserPrincipal implements OAuth2User, OidcUser, UserDetails {
	private static final Log logger = LogFactory.getLog(UserPrincipal.class);
	private static final long serialVersionUID = 6886336772817563966L;

	private Long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	private Map<String, Object> claims;
	private OidcUserInfo userInfo;
	private OidcIdToken idToken;
	
	public UserPrincipal() {
		logger.debug("Default Constructor Called!");
	}
	public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public UserPrincipal(Map<String, Object> claims, OidcUserInfo userInfo, OidcIdToken idToken) {
		this.claims = claims;
		this.userInfo = userInfo;
		this.idToken = idToken;
	}

	public static UserPrincipal create(AuthUser user) {
		logger.debug("Creating UserPrincipal from AuthUser.");
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
	}

	public static UserPrincipal create(AuthUser user, Map<String, Object> attributes) {
		logger.debug("Creating UserPrincipal from AuthUser and attributes..");
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return String.valueOf(id);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getClaims() {
		return claims;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public OidcIdToken getIdToken() {
		return idToken;
	}

}

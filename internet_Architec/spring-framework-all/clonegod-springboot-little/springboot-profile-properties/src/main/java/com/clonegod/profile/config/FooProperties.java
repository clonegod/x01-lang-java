package com.clonegod.profile.config;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("foo")
@Validated
public class FooProperties {

    private boolean enabled;
    
    @NotNull
    private InetAddress remoteAddress;

    @Valid
    private final Security security = new Security();

    public boolean isEnabled() {return enabled; }

	public void setEnabled(boolean enabled) { this.enabled = enabled; }

	public InetAddress getRemoteAddress() { return remoteAddress; }

	public void setRemoteAddress(InetAddress remoteAddress) {  this.remoteAddress = remoteAddress; }

	public Security getSecurity() { return security; }

	
	public static class Security {

        private String username;
        
        @NotBlank
        private String password;

        private List<String> roles = new ArrayList<>(Collections.singleton("USER"));

		public String getUsername() { return username; }

		public void setUsername(String username) { this.username = username; }

		public String getPassword() { return password; }

		public void setPassword(String password) { this.password = password; }

		public List<String> getRoles() { return roles; }

		public void setRoles(List<String> roles) { this.roles = roles; }

		@Override
		public String toString() {
			return "Security [username=" + username + ", password=" + password + ", roles=" + roles + "]";
		}

    }


	@Override
	public String toString() {
		return "FooProperties [enabled=" + enabled + ", remoteAddress=" + remoteAddress + ", \nsecurity=" + security
				+ "]";
	}
	
}
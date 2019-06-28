package com.src.oauth2.config.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by bijoypaul on 09/04/17.
 */
@ConfigurationProperties(prefix = "security.oauth2.resource")
public class OAuth2ResourceServerProperties extends ResourceServerProperties {

    /**
     * URI of the user endpoint.
     */
    private String userImageUri;

    public String getUserImageUri() {
        return userImageUri;
    }

    public void setUserImageUri(String userImageUri) {
        this.userImageUri = userImageUri;
    }
}

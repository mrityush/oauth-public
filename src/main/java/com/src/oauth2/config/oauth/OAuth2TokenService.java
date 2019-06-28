package com.src.oauth2.config.oauth;


import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.enums.LoginSignupType;
import com.src.oauth2.services.SocialService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by bijoypaul on 09/04/17.
 */
public class OAuth2TokenService implements ResourceServerTokenServices {

    protected final Log logger = LogFactory.getLog(getClass());

    private OAuth2RestOperations restTemplate;

    private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

    private ClientResources client;

    private SocialService socialService;



    public OAuth2TokenService(ClientResources client, SocialService socialService) {
        this.client = client;
        this.socialService = socialService;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {
        Map<String, Object> map = getMap(this.client.getResource().getUserInfoUri(), accessToken);
        map.keySet().forEach(s -> logger.debug(s + " --> " + map.get(s)));
        if (map.containsKey("error")) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("userinfo returned error: " + map.get("error"));
            }
            throw new InvalidTokenException(accessToken);
        }
        return extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private Map<String, Object> getMap(String path, String accessToken) {
        Map<String, Object> data = fetchData(path, accessToken);
        OAuth2UserProperties userKeys = this.client.getEntity();
        if (!data.containsKey(userKeys.getImageKey()) || data.get(userKeys.getImageKey()) == null) {
            data.put(userKeys.getImageKey(), fetchUserImage(data.get(userKeys.getIdKey()), accessToken));
        }
        return data;
    }

    private String fetchUserImage(Object id, String accessToken) {
        OAuth2UserProperties userKeys = this.client.getEntity();
        if (this.client.getLoginSignupType() == LoginSignupType.Facebook) {
            try {
                Map<String, Object> imageMap = fetchData(MessageFormat.format(this.client.getResource().getUserImageUri(), id), accessToken);
                if (imageMap.containsKey(userKeys.getImageKey())) {
                    imageMap = (Map<String, Object>) imageMap.get(userKeys.getImageKey());
                    if (imageMap.containsKey("data")) {
                        imageMap = (Map<String, Object>) imageMap.get("data");
                        return (String) imageMap.get("url");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private Map<String, Object> fetchData(String path, String accessToken) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Getting user info from: " + path);
        }
        try {
            OAuth2RestOperations restTemplate = this.restTemplate;
            if (restTemplate == null) {
                BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
                resource.setClientId(this.client.getClient().getClientId());
                restTemplate = new OAuth2RestTemplate(resource);
            }
            OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
                    .getAccessToken();
            if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
                        accessToken);
                token.setTokenType(this.tokenType);
                restTemplate.getOAuth2ClientContext().setAccessToken(token);
            }
            return restTemplate.getForEntity((path.contains("googleapis") ? path : path + "?fields=email,first_name"), Map.class).getBody();
        } catch (Exception ex) {
            this.logger.warn("Could not fetch user details: " + ex.getClass() + ", "
                    + ex.getMessage());
            return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
        }
    }


    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        OAuth2UserProperties userProperties = client.getEntity();
        List<GrantedAuthority> authorities = this.authoritiesExtractor.extractAuthorities(map);
        OAuth2Request request = new OAuth2Request(null, this.client.getClient().getClientId(), null, true, null,
                null, null, null, null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                map.get(userProperties.getNameKey()), "N/A", authorities);
        token.setDetails(map);
        UserAuthority userAuthority = userProperties.buildUser(map, client.getLoginSignupType());
        userAuthority.getUser().setReferralCode(socialService.getReferralCodeForUser(userAuthority.getUser()));

        socialService.save(userAuthority);
        String firstName = socialService.getUserFirstName(userAuthority, (String) map.get(userProperties.getFirstNameKey()));
        String imageKey = socialService.getUserImageKey(userAuthority, (String) map.get(userProperties.getImageKey()));
        HttpSession httpSession = session();
        httpSession.setAttribute("isLoggedIn", "true");
        httpSession.setAttribute("userRole", "ROLE_USER");
        httpSession.setAttribute("firstName", firstName );
        httpSession.setAttribute("imageKey", imageKey);
        return new OAuth2Authentication(request, token);
    }

    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }
}

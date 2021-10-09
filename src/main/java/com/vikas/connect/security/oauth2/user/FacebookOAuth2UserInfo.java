package com.vikas.connect.security.oauth2.user;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        super.setId(((Integer) attributes.get("id")).toString());
        return super.getId();
    }

    @Override
    public String getName() {
        super.setName((String) attributes.get("name"));
        return super.getName();
    }

    @Override
    public String getEmail() {
        super.setEmail((String) attributes.get("email"));
        return super.getEmail();
    }


    @Override
    public String getImageUrl() {
        if(attributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    super.setImageUrl((String) dataObj.get("url"));
                    return super.getImageUrl();
                }
            }
        }
        return null;
    }
}

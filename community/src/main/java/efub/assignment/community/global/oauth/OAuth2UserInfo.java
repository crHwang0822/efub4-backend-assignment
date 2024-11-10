package efub.assignment.community.global.oauth;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2UserInfo {

    private Long id;
    private String email;
    private String nickname;

    public static OAuth2UserInfo from(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        Long id = (Long) attributes.get("id");
        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        return new OAuth2UserInfo(id, email, nickname);
    }
}

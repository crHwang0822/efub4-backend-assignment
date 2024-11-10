package efub.assignment.community.global.oauth;

import efub.assignment.community.global.oauth.OAuth2UserInfo;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String,Object> oAuth2UserAttributes = oAuth2User.getAttributes();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.from(oAuth2UserAttributes);

        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
            .orElseGet(() -> createMember(oAuth2UserInfo));

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("id", member.getMemberId());
        attributes.put("email", member.getEmail());

        return new DefaultOAuth2User(
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "email"
        );

    }

    //사용자 생성 메소드
    private Member createMember(OAuth2UserInfo oAuth2UserInfo) {
        Member member = Member.builder()
            .email(oAuth2UserInfo.getEmail())
            .nickname(oAuth2UserInfo.getNickname())
            .kakaoId(oAuth2UserInfo.getId())
            .password(null)
            .build();
        return memberRepository.save(member);
    }
}

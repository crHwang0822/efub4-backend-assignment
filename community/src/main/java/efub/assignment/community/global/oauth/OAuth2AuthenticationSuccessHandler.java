package efub.assignment.community.global.oauth;

import efub.assignment.community.global.jwt.TokenProvider;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Log4j2
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    private static final String REDIRECT_URL = "/auth/login/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(()-> new EntityNotFoundException("해당 이메일을 가진 유저를 찾을 수 없습니다."));

        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);

        log.info("accessToken : " + accessToken);
        log.info("refreshToken : " + refreshToken);

        tokenProvider.saveRefreshToken(member.getMemberId(), refreshToken);

        //여기서 멤버의 상태(예를 들어, NOT_REGISTERED)에 따라 리다이렉트 시킬 페이지를 결정할 수 있음 (주로 회원가입 페이지)
        String redirectUrl = UriComponentsBuilder
            .fromUriString(REDIRECT_URL)
            .queryParam("accessToken", accessToken)
            .queryParam("refreshToken", refreshToken)
            .build().toUriString();

        response.sendRedirect(redirectUrl);
    }
}

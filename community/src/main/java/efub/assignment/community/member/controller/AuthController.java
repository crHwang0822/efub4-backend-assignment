package efub.assignment.community.member.controller;

import efub.assignment.community.member.service.AuthService;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/success")
    public ResponseEntity<?> success(String accessToken, String refreshToken) {
        HashMap<String,String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return ResponseEntity.ok(map);
    }
}

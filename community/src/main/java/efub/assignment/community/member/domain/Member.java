package efub.assignment.community.member.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, updatable = false, length = 20)
    private String university;

    @Column(name = "student_id", nullable = false, updatable = false, length = 10)
    private String studentId;

    @ColumnDefault("'REGISTERED'")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeStatus(){
        this.status = MemberStatus.UNREGISTERED;
    }

}

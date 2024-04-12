package efub.assignment.community.board.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable=false, updatable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @Column(unique = true, nullable=false, length = 20)
    private String name;

    @Column(nullable=false, length = 100)
    private String description;

    @Column(length = 1000)
    private String notice;
}

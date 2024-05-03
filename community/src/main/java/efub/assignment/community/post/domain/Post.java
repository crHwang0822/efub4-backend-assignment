package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id",nullable=false, updatable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_anonymous", nullable = false)
    private boolean anonymous;

    @Column(length = 1000)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void updateContent(String content){
        this.content = content;
    }
}

package efub.assignment.community.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import efub.assignment.community.post.domain.QPost;
import efub.assignment.community.post.dto.PostDetailsResponseDto;
import java.util.List;

public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;

    public CustomPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<PostDetailsResponseDto> search(Long boardId, String writer, String content) {

        QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();

        if(boardId != null) {
            builder.and(post.board.boardId.eq(boardId));
        }

        //작성자로 검색 시 익명 처리 된 글도 같이 검색되지 않도록 구현
        if(writer!=null&& !writer.isEmpty()){
            builder.and(post.member.nickname.eq(writer).and(
                post.anonymous.eq(false)
            ));
        }

        if(content!=null && !content.isEmpty()){
            builder.and(post.content.containsIgnoreCase(content)
                .or(post.content.containsIgnoreCase(content)));
        }

        return queryFactory
            .select(Projections.constructor(PostDetailsResponseDto.class
            , post.postId
            , post.board.boardId
            , post.member.nickname
            , post.anonymous
            , post.content
            , post.regDate
            , post.modDate))
            .from(post)
            .where(builder)
            .fetch();
    }
}

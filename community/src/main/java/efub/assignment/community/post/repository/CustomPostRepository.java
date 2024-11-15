package efub.assignment.community.post.repository;

import efub.assignment.community.post.dto.PostDetailsResponseDto;
import java.util.List;

public interface CustomPostRepository {
    List<PostDetailsResponseDto> search(Long boardId, String writer, String content);
}

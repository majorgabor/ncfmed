
package hu.elte.mynews.service;

import hu.elte.mynews.entity.Comment;
import hu.elte.mynews.exception.CommentException;
import hu.elte.mynews.exception.NewsException;
import hu.elte.mynews.exception.UserException;
import hu.elte.mynews.repository.CommentRepository;
import hu.elte.mynews.repository.NewsRepository;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsService newsService;
    
    
    public List<Comment> listCommentToNews(long id){
        Iterable<Comment> allComment = commentRepository.findAll();
        List<Comment> result = new ArrayList<>();
        for(Comment comment: allComment){
            if(comment.getNews().getId() == id){
                result.add(comment);
            }
        }
        return result;
    }
    
    public Comment newComment(long id, Comment comment) throws UserException, NewsException{
        comment.setDate(new Date());
        comment.setLikes(0);
        comment.setDislikes(0);
        comment.setUser(userService.getCurrentUser());
        if(newsRepository.findOne(id) != null){
            comment.setNews(newsRepository.findOne(id));
            commentRepository.save(comment);
            newsService.newComment(id, comment);
            userService.newComment(comment);
            
            return comment;
        } else {
            throw new NewsException();
        }
    }
    
    public Comment rate(long id, String rate) throws CommentException {
        Comment likedComment = commentRepository.findOne(id);
        if(likedComment != null && ( rate.equals("like") || rate.equals("dislike") )){
            if(rate.equals("like")) likedComment.setLikes(likedComment.getLikes()+1);
            if(rate.equals("dislike")) likedComment.setDislikes(likedComment.getDislikes()+1);
            commentRepository.save(likedComment);
            return likedComment;
        } else {
            throw new CommentException();
        }
    }
    
    public Comment deleteComment(long id) throws CommentException, NewsException, UserException{
        Comment deleteComment = commentRepository.findOne(id);
        if(deleteComment != null){
            long newsId = deleteComment.getNews().getId();
            long userId = deleteComment.getUser().getId();
            newsService.deleteComment(newsId, deleteComment);
            userService.deleteComment(userId, deleteComment);
            commentRepository.delete(deleteComment);
            return deleteComment;
        } else {
            throw new CommentException();
        }
    }

    void deleteNewsComment(List<Comment> comments) throws CommentException, NewsException, UserException {
        for(Comment comment : comments){
            if(comment != null){
                long userId = comment.getUser().getId();
                userService.deleteComment(userId, comment);
                commentRepository.delete(comment);
            } else {
                throw new CommentException();
            }
        }
    }
}

package com.example.blog.service;

import com.example.blog.dao.CommentRepository;
import com.example.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long BlogId) {
        Sort sort=Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentIsNull(BlogId,sort);
        return eachCommnent(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    /*
    * 循环每个顶级的评论节点
    * */
    private List<Comment> eachCommnent(List<Comment> comments){
        List<Comment> commentsView=new ArrayList<>();
        for(Comment comment:comments){
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    /*
    *
    */
    private void combineChildren(List<Comment> comments){
        for(Comment comment:comments){
            List<Comment> replys1=comment.getReplayComments();
            for(Comment reply1:replys1){
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComments(tempReplys);
            //清空临时存放区
            tempReplys=new ArrayList<>();
        }
    }
    //定义一个存放所有二级评论的集合
    private List<Comment> tempReplys=new ArrayList<>();
    /*
    * 递归迭代，找出每个节点的回复信息
    * */
    private void recursively(Comment comment){
        tempReplys.add(comment);//顶级节点存放到临时存放集合
        if(comment.getReplayComments().size()>0){
            List<Comment> replys=comment.getReplayComments();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplayComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}

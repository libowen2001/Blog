package com.example.blog.service;

import com.example.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long BlogId);
    Comment saveComment(Comment comment);
}

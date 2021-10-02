package com.example.blog.service;

import com.example.blog.po.Blog;
import com.example.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog saveBlog(Blog blog);
    void deleteBlog(Long id);
    Blog updateBlog(Long id,Blog blog);
}

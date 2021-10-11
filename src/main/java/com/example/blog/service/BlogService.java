package com.example.blog.service;

import com.example.blog.po.Blog;
import com.example.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog saveBlog(Blog blog);
    void deleteBlog(Long id);
    Blog updateBlog(Long id,Blog blog);
    Page<Blog> listblog(Pageable pageable);
    List<Blog> listrecommend(Integer size);
    Page<Blog> listquery(String query,Pageable pageable);
    Blog getAndconvert(Long id);//获取的blog要经过从markdown转换为html
    Page<Blog> listTag(Long id, Pageable pageable);
    Map<String,List<Blog>> archiveBlog();//按照年份查询博客，输出到归档页面
    Long count();
}

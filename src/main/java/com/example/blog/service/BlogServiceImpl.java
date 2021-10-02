package com.example.blog.service;

import com.example.blog.NotFoundException;
import com.example.blog.dao.BlogRepository;
import com.example.blog.po.Blog;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(cb.like(root.get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);

    }

    @Override
    public Blog saveBlog(Blog blog) {

        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
         blogRepository.deleteById(id);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1=blogRepository.findById(id).get();
        if(blog1==null){
            throw new NotFoundException("博客不存在！");
        }
        BeanUtils.copyProperties(blog,blog1);
        return blogRepository.save(blog1);
    }
}

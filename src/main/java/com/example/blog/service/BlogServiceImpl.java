package com.example.blog.service;

import com.example.blog.NotFoundException;
import com.example.blog.dao.BlogRepository;
import com.example.blog.po.Blog;
import com.example.blog.util.MarkdownUtils;
import com.example.blog.util.MyBeanUtils;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

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
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
         blogRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1=blogRepository.findById(id).get();
        if(blog1==null){
            throw new NotFoundException("博客不存在！");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Override
    public Page<Blog> listblog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listrecommend(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        List<Blog> l = blogRepository.findTop(pageable);
        System.out.println(l.size());
        return l;
    }

    @Override
    public Page<Blog> listquery(String query, Pageable pageable) {
        return blogRepository.listquery(query,pageable);
    }
    //获取的内容要转换为html格式
    @Override
    public Blog getAndconvert(Long id) {
        Blog blog=blogRepository.findById(id).get();
        if(blog==null){
            throw new NotFoundException("博客不存在");
        }
        /*Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);*/
        String content=blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog;
    }
    //博客和标签的关联查询
    @Override
    public Page<Blog> listTag(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join= root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);

    }
     //按年份把博客归档·
    @Override
    public Map<String,List<Blog>> archiveBlog() {
        List<String> years= blogRepository.findgroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String year : years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long count() {
        return blogRepository.count();
    }
    @Transactional
    @Override
    public Integer viewsadd(Long id) {
        return blogRepository.addViews(id);
    }
}

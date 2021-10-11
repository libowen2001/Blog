package com.example.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {


    public static String markdownToHtml(String markdown){
            Parser parser = Parser.builder().build();
            Node document= parser.parse(markdown);
           HtmlRenderer renderer= HtmlRenderer.builder().build();
           return renderer.render(document);

    }
    //处理表格
    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
        //转换table的html
        List<Extension> tableExtension= Arrays.asList(TablesExtension.create());
        Parser parser=Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document= parser.parse(markdown);
        HtmlRenderer renderer=HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }
    //处理标签
    static class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if(node instanceof Link){
               attributes.put("target","_blank");
            }
            if(node instanceof TableBlock){
                attributes.put("class","ui celled table");
            }
        }

    }
   /* public static void main(String[] args){
        String table="|hello | hi | 哈哈啊哈   |\n"+
                "|----------|-------|-------|\n"+
                "|中国  | 热本  |  美国   |\n";
        String a="[duhudsv  爱编程](http://www.libowen.cn)";
        System.out.println(markdownToHtmlExtensions(table));
    }*/
}

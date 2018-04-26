package clonegod.framework.dal.dao.resultmap;

import java.util.List;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.Blog;
import clonegod.framework.dal.dao.Post;
import lombok.Data;

@Data
public class BlogPostsResultMap {
	
	private Blog blog;

    private Author author;
    
    private List<Post> posts;

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
    
}

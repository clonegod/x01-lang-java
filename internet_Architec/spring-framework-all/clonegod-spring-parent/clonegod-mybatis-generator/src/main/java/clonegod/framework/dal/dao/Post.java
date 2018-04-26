package clonegod.framework.dal.dao;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private Integer id;

    private Integer blogId;

    private Integer authorId;

    private String section;

    private String subject;

    private String draft;

    private String body;

    private Date createdOn;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public Post withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public Post withBlogId(Integer blogId) {
        this.setBlogId(blogId);
        return this;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public Post withAuthorId(Integer authorId) {
        this.setAuthorId(authorId);
        return this;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getSection() {
        return section;
    }

    public Post withSection(String section) {
        this.setSection(section);
        return this;
    }

    public void setSection(String section) {
        this.section = section == null ? null : section.trim();
    }

    public String getSubject() {
        return subject;
    }

    public Post withSubject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getDraft() {
        return draft;
    }

    public Post withDraft(String draft) {
        this.setDraft(draft);
        return this;
    }

    public void setDraft(String draft) {
        this.draft = draft == null ? null : draft.trim();
    }

    public String getBody() {
        return body;
    }

    public Post withBody(String body) {
        this.setBody(body);
        return this;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Post withCreatedOn(Date createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", blogId=").append(blogId);
        sb.append(", authorId=").append(authorId);
        sb.append(", section=").append(section);
        sb.append(", subject=").append(subject);
        sb.append(", draft=").append(draft);
        sb.append(", body=").append(body);
        sb.append(", createdOn=").append(createdOn);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Post other = (Post) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBlogId() == null ? other.getBlogId() == null : this.getBlogId().equals(other.getBlogId()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getSection() == null ? other.getSection() == null : this.getSection().equals(other.getSection()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getDraft() == null ? other.getDraft() == null : this.getDraft().equals(other.getDraft()))
            && (this.getBody() == null ? other.getBody() == null : this.getBody().equals(other.getBody()))
            && (this.getCreatedOn() == null ? other.getCreatedOn() == null : this.getCreatedOn().equals(other.getCreatedOn()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBlogId() == null) ? 0 : getBlogId().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getSection() == null) ? 0 : getSection().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getDraft() == null) ? 0 : getDraft().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        result = prime * result + ((getCreatedOn() == null) ? 0 : getCreatedOn().hashCode());
        return result;
    }
}
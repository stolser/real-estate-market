package com.stolser.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Post entity: a concrete entity that represents one post.
 * */
@Entity
@NamedQueries({
	@NamedQuery(name="Post.findAll", query="select p from Post p"),
	@NamedQuery(name="Post.findByCategory",
			query="select p from Post p where p.category = :category"),
	@NamedQuery(name="Post.findByStatus",
		query="select p from Post p where p.status = :status"),
	@NamedQuery(name="Post.findByAuthor",
		query="select p from Post p where p.author = :author"),
	@NamedQuery(name="Post.findByLinkName",
		query="select p from Post p where p.linkName = :linkName")
})
@Table(name="POST")
public class Post implements Serializable {
	private static final long serialVersionUID = 350L;
	
	@Id
	@Column(name="POST_PK")
	@TableGenerator(name="postIdGenerator",
					table="SEQUENCE_STORAGE",
					pkColumnName="SEQUENCE_NAME",
					pkColumnValue="POST.POST_PK",
					valueColumnName="SEQUENCE_VALUE",
					initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="postIdGenerator")
	private int id;
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID")
	@NotNull
	private PostCategory category;
	@NotNull
	private PostStatusType status;
	@ManyToOne
	@JoinColumn(name="AUTHOR_ID")
	@NotNull
	private User author;
	@OneToMany(mappedBy="post", orphanRemoval=true)
	private Collection<PostComment> listOfComments;
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date dateOfCreation;
	/**
	 * <span style="color:red";>Value MUST be unique.</span> Will be used in a URL.
	 * */
	@Column(name="LINK_NAME")
	@NotNull
	private String linkName;
	@NotNull
	private String title;
	@NotNull 
	private String text;
	private String excerpt;
	@Column(name="META_TITLE")
	private String metaTitle;
	@Column(name="META_KEYWORDS")
	private String metaKeyWords;
	@Column(name="META_DESCRIPTION")
	private String metaDescription;
	@Version
	private int version;

	public Post() {}
	
	public Post(PostCategory category, PostStatusType status, User author, Date dateOfCreation,
			String linkName, String title, String text) {
		super();
		this.category = category;
		this.status = status;
		this.author = author;
		this.dateOfCreation = dateOfCreation;
		this.linkName = linkName;
		this.title = title;
		this.text = text;
	}

	public enum PostStatusType {
		ACTIVE, NOT_ACTIVE, DISCARDED;
	}

/*-------- getters and setters --------------------*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PostCategory getCategory() {
		return category;
	}

	public void setCategory(PostCategory category) {
		this.category = category;
	}

	public PostStatusType getStatus() {
		return status;
	}

	public void setStatus(PostStatusType status) {
		this.status = status;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		if (!(author instanceof PostAuthor)) {
			throw new IllegalArgumentException("Only PostAuthor instances can be " +
												"the author of posts.");
		}
		this.author = author;
	}

	public Collection<PostComment> getListOfComments() {
		return listOfComments;
	}

	public void setListOfComments(Collection<PostComment> listOfComments) {
		this.listOfComments = listOfComments;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaKeyWords() {
		return metaKeyWords;
	}

	public void setMetaKeyWords(String metaKeyWords) {
		this.metaKeyWords = metaKeyWords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
/*-------- END of getters and setters -------------*/

}














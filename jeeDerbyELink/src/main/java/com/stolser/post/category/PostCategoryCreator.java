package com.stolser.post.category;

import static com.stolser.MessageFromProperties.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stolser.jpa.PostCategory;
import com.stolser.jpa.PostCategory.PostCategoryStatusType;

@ManagedBean(name = "postCategoryCreator")
@ViewScoped
public class PostCategoryCreator implements Serializable {
	private static final long serialVersionUID = 1L;

	static private final Logger logger = LoggerFactory.getLogger(PostCategoryCreator.class);
	
	private PostCategory newPostCategory;
	private List<PostCategory> allPostCategories;
	private List<PostCategoryStatusType> postCategoryStatuses;
	@EJB
	private PostCategoryFacade categoryFacade;
	
	public PostCategoryCreator() {}
	 
	@PostConstruct
	private void init() {
		newPostCategory = new PostCategory();
		allPostCategories = categoryFacade.getPostCategoriesFindAll();
		postCategoryStatuses = Arrays.asList(PostCategoryStatusType.values());
	}
	
	public String createPostCategory() {
		try{
			newPostCategory = categoryFacade.addNewPostCategory(newPostCategory);
			
		} catch(Exception e) {
			String failureMessage = createMessageText("postCatNotBeenCreatedMessage", newPostCategory);
			addMessageToFacesContext(createErrorFacesMessage(failureMessage));
			logger.error(failureMessage, e);
			
			return null;
		}
		
		String successMessage = createMessageText("postCatBeenCreatedMessage", newPostCategory);
		addMessageToFacesContext(createInfoFacesMessage(successMessage));
		logger.info(successMessage);
		
//		return "createNewPostCategory?faces-redirect=true";
		return null;
	}
	
	public void fillSystemNameField() {
		String enteredTitle = newPostCategory.getTitle();
		String autoGeneratedSystemName = autoGenerateSystemName(enteredTitle);
		
		newPostCategory.setSystemName(autoGeneratedSystemName);
	}

	private String autoGenerateSystemName(String enteredTitle) {
		List<Character> newStringChars = new ArrayList<Character>();
		char nextChar;
		boolean isNextCharInUpperCase = false;
		
		enteredTitle = enteredTitle.trim().toLowerCase();
		for (int i = 0; i < enteredTitle.length(); i++) {
			nextChar = enteredTitle.charAt(i);
			if (Character.isSpaceChar(nextChar)) {
				isNextCharInUpperCase = true;
				continue;
			}
			
			if (charIsNotLegal(nextChar)) {
				continue;
			}
			
			if (isNextCharInUpperCase) {
				nextChar = Character.toUpperCase(nextChar);
				isNextCharInUpperCase = false;
			}
			
			newStringChars.add(nextChar);
		}
		
		char[] newStringCharsArray = new char[newStringChars.size()];
		for (int i = 0; i < newStringChars.size(); i++) {
			newStringCharsArray[i] = newStringChars.get(i);
		}
		
		String newString = new String(newStringCharsArray);
		
		return newString;
	}
	
	private boolean charIsNotLegal(char character) {
		boolean charIsNotLegal = false;
		if ((character < 'a') || (character > 'z')) {
			charIsNotLegal = true;
		}
		
		return charIsNotLegal;
	}

	public PostCategory getNewPostCategory() {
		return newPostCategory;
	}

	public void setNewPostCategory(PostCategory newPostCategory) {
		this.newPostCategory = newPostCategory;
	}

	public List<PostCategory> getAllPostCategories() {
		return allPostCategories;
	}

	public List<PostCategoryStatusType> getPostCategoryStatuses() {
		return postCategoryStatuses;
	}

	public PostCategoryFacade getCategoryFacade() {
		return categoryFacade;
	}

	public void setCategoryFacade(PostCategoryFacade categoryFacade) {
		this.categoryFacade = categoryFacade;
	}

}














package com.CheatSheet.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicBean {
	private int id;
    private int categoriesId; // Links to the Language ID
    private String title;
    private String description;
    private String codeContent;
    private String languageName;
    private int languageId;
}

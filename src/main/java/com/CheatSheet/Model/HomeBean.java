package com.CheatSheet.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeBean {
	private int id;
    private String name;
    private String imagePath;
    private String title;
    
    private String categories_id;
    private String description;
    private String code_content;
    private String userNotes;
}

package com.javacodegeeks.xml.bind.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element(name="movie")
public class Movie {
	
	@Element(required=false)
	public String score;
	
	@Element(required=false)
	public String popularity;
	
	@Element(required=false)
	public String name;
	
	@Element(required=false)
	public String id;
	
	@Element(required=false)
	public String biography;
	
	@Element(required=false)
	public String url;
	
	@Element(required=false)
	public String version;
	
	@Element(required=false)
	public String lastModifiedAt;
	
	@ElementList
	public List<Image> images;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie [biography=");
		builder.append(biography);
		builder.append(", id=");
		builder.append(id);
		builder.append(", images=");
		builder.append(images);
		builder.append(", lastModifiedAt=");
		builder.append(lastModifiedAt);
		builder.append(", name=");
		builder.append(name);
		builder.append(", popularity=");
		builder.append(popularity);
		builder.append(", score=");
		builder.append(score);
		builder.append(", url=");
		builder.append(url);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}

package com.javacodegeeks.xml.bind.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element(name="image")
public class Image {

	@Attribute
	public String type;
	
	@Attribute
	public String url;
	
	@Attribute
	public String size;
	
	@Attribute
	public int width;
	
	@Attribute
	public int height;
	
	@Attribute
	public String id;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Image [height=");
		builder.append(height);
		builder.append(", id=");
		builder.append(id);
		builder.append(", size=");
		builder.append(size);
		builder.append(", type=");
		builder.append(type);
		builder.append(", url=");
		builder.append(url);
		builder.append(", width=");
		builder.append(width);
		builder.append("]");
		return builder.toString();
	}

}

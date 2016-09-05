package com.javacodegeeks.xml.bind.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Query {

	@Attribute
	public String searchTerms;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Query [searchTerms=");
		builder.append(searchTerms);
		builder.append("]");
		return builder.toString();
	}
	
}

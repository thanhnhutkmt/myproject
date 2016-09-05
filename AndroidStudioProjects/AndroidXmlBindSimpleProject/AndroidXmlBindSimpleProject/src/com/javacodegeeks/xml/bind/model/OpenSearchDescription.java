package com.javacodegeeks.xml.bind.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class OpenSearchDescription {
	
	@Element(name="Query")
	public Query query;
	
	@Element
	public int totalResults;
	
	@ElementList
	public List<Movie> movies;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpenSearchDescription [movies=");
		builder.append(movies);
		builder.append(", query=");
		builder.append(query);
		builder.append(", totalResults=");
		builder.append(totalResults);
		builder.append("]");
		return builder.toString();
	}

}

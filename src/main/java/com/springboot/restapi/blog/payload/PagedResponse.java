package com.springboot.restapi.blog.payload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class PagedResponse<T> {

	private List<T> content;

	private int pageNumber;

	private int pageSize;

	private long totalElements;

	private int totalPages;

	private boolean last;

	public PagedResponse() {

	}

	public List<T> getContent() {
		return content == null ? null : new ArrayList<>(content);
	}

	public final void setContent(List<T> content) {
		if (content == null) {
			this.content = null;
		} else {
			this.content = Collections.unmodifiableList(content);
		}
	}

	public boolean isLast() {
		return last;
	}

}

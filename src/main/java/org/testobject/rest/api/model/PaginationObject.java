package org.testobject.rest.api.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class PaginationObject<T> {

	private List<T> entities;
	private MetaData metadata;

	public PaginationObject(@JsonProperty("entities") List<T> entities, @JsonProperty("metaData") MetaData metadata) {
		this.entities = entities;
		this.metadata = metadata;
	}

	public List<T> getEntities() {
		return entities;
	}

	public MetaData getMetadata() {
		return metadata;
	}

	public static final class MetaData {
		private final long offset;
		private final long limit;
		private final String searchTerm;
		private final String sortDirection;
		private final long count;

		@JsonCreator
		public MetaData(@JsonProperty("offset") long offset, @JsonProperty("limit") long limit,
				@JsonProperty("searchTerm") String searchTerm, @JsonProperty("sortDirection") String sortDirection,
				@JsonProperty("count") long count) {
			this.offset = offset;
			this.limit = limit;
			this.searchTerm = searchTerm;
			this.sortDirection = sortDirection;
			this.count = count;
		}

		public long getOffset() {
			return offset;
		}

		public long getLimit() {
			return limit;
		}

		public String getSearchTerm() {
			return searchTerm;
		}

		public String getSortDirection() {
			return sortDirection;
		}

		public long getCount() {
			return count;
		}
	}

}

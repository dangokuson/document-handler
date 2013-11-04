/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.common.dao.search.flex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.exoplatform.common.dao.model.Field;
import org.exoplatform.common.dao.model.Filter;
import org.exoplatform.common.dao.model.Sort;
import org.exoplatform.common.dao.search.SearchImmutable;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version FlexSearch.java Nov 4, 2013
 */
public class FlexSearch implements Serializable {

	protected int firstResult = -1; // -1 stands for unspecified

	protected int maxResults = -1; // -1 stands for unspecified

	protected int page = -1; // -1 stands for unspecified

	protected String searchClassName;

	protected List<Filter> filters = new ArrayList<Filter>();

	protected boolean disjunction;

	protected List<Sort> sorts = new ArrayList<Sort>();

	protected List<Field> fields = new ArrayList<Field>();
	
	protected boolean distinct;

	protected List<String> fetches = new ArrayList<String>();

	protected int resultMode = SearchImmutable.RESULT_AUTO;

	public void setSearchClassName(String searchClassName) throws ClassNotFoundException {
		this.searchClassName = searchClassName;
	}

	public String getSearchClassName() {
		return searchClassName;
	}

	public Filter[] getFilters() {
		return filters.toArray(new Filter[0]);
	}

	public void setFilters(Filter[] filters) {
		this.filters.clear();
		if (filters != null) {
			for (int i = 0; i < filters.length; i++) {
				Object o = filters[i];
				if (o != null && o instanceof Filter) {
					this.filters.add(filters[i]);
				}
			}
		}
	}

	public Sort[] getSorts() {
		return sorts.toArray(new Sort[0]);
	}

	public void setSorts(Sort[] sorts) {
		this.sorts.clear();
		if (sorts != null) {
			for (int i = 0; i < sorts.length; i++) {
				Object o = sorts[i];
				if (o != null && o instanceof Sort) {
					this.sorts.add(sorts[i]);
				}
			}
		}
	}

	public Field[] getFields() {
		return fields.toArray(new Field[0]);
	}

	public void setFields(Field[] fields) {
		this.fields.clear();
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				if (f != null && f.getProperty() != null && f.getProperty().length() > 0) {
					if (f.getKey() == null)
						f.setKey(f.getProperty());
					this.fields.add(f);
				}
			}
		}
	}

	public String[] getFetches() {
		return fetches.toArray(new String[0]);
	}

	public void setFetches(String[] fetches) {
		this.fetches.clear();
		if (fetches != null) {
			for (int i = 0; i < fetches.length; i++) {
				if (fetches[i] != null && !"".equals(fetches[i]))
					this.fetches.add(fetches[i]);
			}
		}
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isDisjunction() {
		return disjunction;
	}

	public void setDisjunction(boolean disjunction) {
		this.disjunction = disjunction;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public int getResultMode() {
		return resultMode;
	}

	public void setResultMode(int resultMode) {
		this.resultMode = resultMode;
	}
}

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
package org.exoplatform.document.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.exoplatform.document.constant.TBLEntity;
import org.exoplatform.document.constant.TBLPicture;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @version Picture.java Oct 31, 2013
 * 
 */
@Entity
@Table(name = TBLPicture.TBL_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = TBLEntity.ID) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = TBLPicture.TBL_NAME)
public class Picture extends StringIdentity {

	private static final long serialVersionUID = -1190891511410189743L;

	@Column(name = TBLPicture.URL, length = 1500)
	private String url;

	public Picture() {
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "picture [url=" + url + ", id=" + id + "]";
	}
}

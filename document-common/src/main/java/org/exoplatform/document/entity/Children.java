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

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @version Children.java Oct 31, 2013
 * 
 */
public class Children extends BaseEntityIdentity {

	private static final long serialVersionUID = 3338144206955937089L;

	private String childLink;

	public Children() {
		super();
	}

	/**
	 * @param childLink
	 */
	public Children(String childLink) {
		this.childLink = childLink;
	}

	/**
	 * @return the childLink
	 */
	public String getChildLink() {
		return childLink;
	}

	/**
	 * @param childLink
	 *            the childLink to set
	 */
	public void setChildLink(String childLink) {
		this.childLink = childLink;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((childLink == null) ? 0 : childLink.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Children)) {
			return false;
		}
		Children other = (Children) obj;
		if (childLink == null) {
			if (other.childLink != null) {
				return false;
			}
		} else if (!childLink.equals(other.childLink)) {
			return false;
		}
		return true;
	}
}

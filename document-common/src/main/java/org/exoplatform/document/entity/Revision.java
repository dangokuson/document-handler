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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.exoplatform.document.constant.TBLEntity;
import org.exoplatform.document.constant.TBLFile;
import org.exoplatform.document.constant.TBLRevision;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 * 
 * @version Revision.java Nov 1, 2013
 */
@Entity
@Table(name = TBLRevision.TBL_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = TBLEntity.ID) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = TBLRevision.TBL_NAME)
public class Revision extends Document {

	private static final long serialVersionUID = 6612421643350781170L;

	@Column(name = TBLRevision.PINNED, length = 10)
	private boolean pinned;

	@Column(name = TBLRevision.PUBLISHED, length = 10)
	private boolean published;

	@Column(name = TBLRevision.PUBLISH_AUTO, length = 10)
	private boolean publishAuto;

	@Column(name = TBLRevision.PUBLISHED_OUTSIDE_DOMAIN, length = 10)
	private boolean publishedOutsideDomain;

	@Column(name = TBLRevision.PUBLISHED_LINK, length = 1500)
	private String publishedLink;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = TBLFile.HEAD_REVISION_IDENTITY)
	private List<File> files;

	public Revision() {
		super();
	}

	/**
	 * @return the pinned
	 */
	public boolean isPinned() {
		return pinned;
	}

	/**
	 * @param pinned
	 *            the pinned to set
	 */
	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	/**
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}

	/**
	 * @return the publishAuto
	 */
	public boolean isPublishAuto() {
		return publishAuto;
	}

	/**
	 * @param publishAuto
	 *            the publishAuto to set
	 */
	public void setPublishAuto(boolean publishAuto) {
		this.publishAuto = publishAuto;
	}

	/**
	 * @return the publishedOutsideDomain
	 */
	public boolean isPublishedOutsideDomain() {
		return publishedOutsideDomain;
	}

	/**
	 * @param publishedOutsideDomain
	 *            the publishedOutsideDomain to set
	 */
	public void setPublishedOutsideDomain(boolean publishedOutsideDomain) {
		this.publishedOutsideDomain = publishedOutsideDomain;
	}

	/**
	 * @return the publishedLink
	 */
	public String getPublishedLink() {
		return publishedLink;
	}

	/**
	 * @param publishedLink
	 *            the publishedLink to set
	 */
	public void setPublishedLink(String publishedLink) {
		this.publishedLink = publishedLink;
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            the files to list
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + (pinned ? 1231 : 1237);
		result = prime * result + (publishAuto ? 1231 : 1237);
		result = prime * result + (published ? 1231 : 1237);
		result = prime * result
				+ ((publishedLink == null) ? 0 : publishedLink.hashCode());
		result = prime * result + (publishedOutsideDomain ? 1231 : 1237);
		return result;
	}
}

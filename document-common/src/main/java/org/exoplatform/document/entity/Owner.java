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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.exoplatform.document.constant.TBLDocument;
import org.exoplatform.document.constant.TBLEntity;
import org.exoplatform.document.constant.TBLFile;
import org.exoplatform.document.constant.TBLOwner;
import org.exoplatform.document.entity.plugin.IOwner;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @version Owner.java Oct 31, 2013
 * 
 */
@Entity
@Table(name = TBLOwner.TBL_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = TBLEntity.ID) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = TBLOwner.TBL_NAME)
public class Owner extends StringIdentity implements IOwner {

	private static final long serialVersionUID = 5870864171015175044L;

	@Column(name = TBLOwner.KIND, length = 50)
	private String kind;

	@Column(name = TBLOwner.DISPLAY_NAME, length = 100)
	private String displayName;

	@Column(name = TBLOwner.IS_AUTHENTICATED_USER, length = 1)
	private boolean isAuthenticatedUser;

	@Column(name = TBLOwner.PERMISSION_ID, length = 100)
	private String permissionId;

	/*
	 * One - To - One
	 * 
	 * One Owner has only one Picture and one Picture has only one Owner
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = TBLOwner.PICTURE)
	private Picture picture;

	/*
	 * One - To - One
	 * 
	 * One Owner has only one Owner's detail and one Owner's detail has only one
	 * Owner
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = TBLOwner.ACCOUNT_INFOR)
	private Account accountInfor;

	/*
	 * Many - To - Many
	 * 
	 * One Owner has many File and one File has many Owner
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = TBLFile.OWNERS)
	private List<File> filesOfOwner;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = TBLDocument.MAPPEDBY_LAST_MODIFYING_USER)
	private List<File> lastModifyingFiles;

	public Owner() {
		super();
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the isAuthenticatedUser
	 */
	public boolean isAuthenticatedUser() {
		return isAuthenticatedUser;
	}

	/**
	 * @param isAuthenticatedUser
	 *            the isAuthenticatedUser to set
	 */
	public void setAuthenticatedUser(boolean isAuthenticatedUser) {
		this.isAuthenticatedUser = isAuthenticatedUser;
	}

	/**
	 * @return the permissionId
	 */
	public String getPermissionId() {
		return permissionId;
	}

	/**
	 * @param permissionId
	 *            the permissionId to set
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * @return the picture
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @return the accountInfor
	 */
	public Account getAccountInfor() {
		return accountInfor;
	}

	/**
	 * @param accountInfor
	 *            the accountInfor to set
	 */
	public void setAccountInfor(Account accountInfor) {
		this.accountInfor = accountInfor;
	}

	/**
	 * @return the filesOfOwner
	 */
	public List<File> getFilesOfOwner() {
		return filesOfOwner;
	}

	/**
	 * @param filesOfOwner
	 *            the filesOfOwner to set
	 */
	public void setFilesOfOwner(List<File> filesOfOwner) {
		this.filesOfOwner = filesOfOwner;
	}

	/**
	 * @return the lastModifyingFiles
	 */
	public List<File> getLastModifyingFiles() {
		return lastModifyingFiles;
	}

	/**
	 * @param lastModifyingFiles
	 *            the lastModifyingFiles to set
	 */
	public void setLastModifyingFiles(List<File> lastModifyingFiles) {
		this.lastModifyingFiles = lastModifyingFiles;
	}
}
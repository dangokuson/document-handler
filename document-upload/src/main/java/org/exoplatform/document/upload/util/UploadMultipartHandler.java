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
package org.exoplatform.document.upload.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.exoplatform.document.upload.Document;
import org.exoplatform.document.util.FilePathUtils;
import org.exoplatform.document.util.FileUtils;
import org.exoplatform.document.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by The eXo Platform SAS
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 *          
 * @version UploadMultipartHandler.java Nov 7, 2013
 */
public class UploadMultipartHandler implements HttpRequestHandler {

  /** . */
	private static final Logger logger = LoggerFactory.getLogger(UploadMultipartHandler.class);
	
	protected static final int MAXIMUM_FILE_SIZE = 10 * 1024 * 1024; // maximum allowed size of a file
	
	/**
   * cache size, if file is bigger than the size, it will be saved in file system
   * or else if will be hold in memory. 
   */
	protected static final int MEMORY_CACHE_SIZE = 4 * 1024;
	
	protected List<Document> documents;
	
	/* (non-Javadoc)
	 * @see org.exoplatform.document.upload.util.UploadMultipartPlugin#parseUploadMultipart(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<Document> parseUploadMultipart(HttpServletRequest request) throws SizeLimitExceededException, FileUploadException, IOException {
		return parseHttpRequest(request);
	}

	/* (non-Javadoc)
	 * @see org.exoplatform.document.upload.util.UploadMultipartPlugin#parseRequestMultipart(javax.servlet.http.HttpServletRequest)
	 */
  @Override
	public List<Document> parseHttpRequest(HttpServletRequest request) throws SizeLimitExceededException, FileUploadException, IOException {
		if (logger.isDebugEnabled()) {
			logger.info("Parse file item form HTTP servlet request.");
		}
		
		// http://pic.dhe.ibm.com/infocenter/btt/v7r1/index.jsp?topic=%2Fcom.ibm.btt.dev_tools.doc_7.1%2Fdoc%2Ftasks%2Fxuied%2F2.3.1f_bindingdatatofileuploadwidgets.html
		Document document = null;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
		  documents = new ArrayList<Document>();
		  
		  // Create a factory for disk-based file items
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    
	    //config the memory cache, if above the cache, the file data will be 
      // saved on cache folder 
	    factory.setSizeThreshold(MEMORY_CACHE_SIZE);
	    
	    // set up cache folder, when uploading, the tmp file 
      // will be saved in cache folder with a internal managed name.
	    factory.setRepository(FileUtils.forceMkdir(FilePathUtils.REPOSITORY_PATH));
	    
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    // max size of a file 
	    upload.setSizeMax(MAXIMUM_FILE_SIZE);
	    
	    try {
	      List<FileItem> items = upload.parseRequest(request);
	      Iterator<FileItem> iterator = items.iterator();
	      
	      logger.info("To create specified sub-folder under " + FilePathUtils.ROOT_PATH + " top-level folder");
	      FileUtils.forceMkdir(FilePathUtils.RESOURCE_PATH);
	      while (iterator.hasNext()) {
	        FileItem fileItem = iterator.next();
	        if (!fileItem.isFormField()) {
	          document = Document.getInstance();
	          document.setFilename(fileItem.getName());
	          document.setContentType(fileItem.getContentType());
	          document.setSize(fileItem.getSize());
	          
	          // Write file items to disk-based
	          String absolutePath = writeFiles(fileItem, document.getFilename());
	          if (StringUtils.isNotEmpty(absolutePath)) {
	            // Sets specified local path
	            document.setUrl(absolutePath);
	            document.setReadOnly(false);
	            document.setArchive(false);
	            document.setDirectory(false);
	            document.setHidden(false);
	            document.setSystem(false);
	            document.setOther(false);
	            document.setRegularFile(false);
	            
	            Date time = Calendar.getInstance().getTime();
	            document.setCreationTime(time);
	            document.setLastAccessTime(time);
	            document.setLastModifiedTime(time);
	            
	            documents.add(document);
	            logger.info("File(s) " + document.getFilename() + " was/were uploaded successfully");
	          } else {
	            logger.info("File(s) " + document.getFilename() + " was/were not uploaded");
	          }
	        }
	      }
	      return documents;
      } catch (SizeLimitExceededException slee) {
        throw new SizeLimitExceededException("The request was rejected because its size exceeds the configured maximum");
      } catch (FileUploadException fue) {
        throw new FileUploadException("Upload file stream was been cancelled", fue.getCause());
      } finally {
        logger.info("Cleans a directory without deleting it");
        FileUtils.cleanDirectory(factory.getRepository());
      }
		}
		
		documents = Collections.<Document>emptyList();
		return documents;
	}
	
	private String writeFiles(FileItem fileItem, String fileName) throws IOException {
	  String absolutePath = null;
	  
	  int lastIndexOf = fileName.lastIndexOf("\\");
	  if (lastIndexOf >= 0) {
	    lastIndexOf += 1;
	  }
	  File file = new File(FilePathUtils.RESOURCE_PATH + File.separator + fileName.substring(lastIndexOf));
    
    byte[] buffer = new byte[1024];
    int length = 0;
    InputStream inputStream = fileItem.getInputStream();        
    FileOutputStream outputStream = new FileOutputStream(file);
    while ((length = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, length);
    }
    
    // colse stream 
    outputStream.close();
    inputStream.close();
    
    absolutePath = file.getAbsolutePath();
    return absolutePath;
	}
}

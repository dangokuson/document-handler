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
package org.exoplatform.common.dao.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 * 
 * 
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:exo@exoplatform.com">eXoPlatform</a>
 * 
 * @version RepositorySessionFactory.java Dec 5, 2013
 */
public final class RepositorySessionFactory {

	private static final Logger logger = LoggerFactory.getLogger(RepositorySessionFactory.class);
	
	/** Session Factory {@link SessionFactory} */
	private final SessionFactory _sessionFactory;

	/** Configuration {@link Configuration} */
	private final Configuration _configuration;

	/**
	 * Create a new instance of the SessionFactory from standard hibernate.cfg.xml config file
	 * 
	 * @param configuration
	 * @param serviceRegistryBuilder
	 */
	public RepositorySessionFactory(Configuration configuration, ServiceRegistryBuilder serviceRegistryBuilder) {
		logger.info("Trying to initialize the SessionFactory creation instance from hibernate.cfg.xml config file");
		
		try {
			this._configuration = configuration;
			this._configuration.configure();
			this._sessionFactory = _configuration.buildSessionFactory(serviceRegistryBuilder.applySettings(_configuration.getProperties()).buildServiceRegistry());
		} catch (HibernateException he) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("Initial SessionFactory creation failed", he);
			// Throw new HibernateException
			throw new HibernateException(he);
		}
	}

	/**
	 * Get the singleton hibernate Session Factory
	 * 
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return _sessionFactory;
	}

	public boolean isClosed() {
		return _sessionFactory.isClosed();
	}

	/**
	 * Open a new session
	 * 
	 * @return Session
	 */
	public Session openSession() {
		return _sessionFactory.openSession();
	}

	/**
	 * Closes the current session factory and releases all resources 
	 * (caches, connection pools, etc).
	 */
	private void close() {
		if (_sessionFactory != null && !isClosed())
			_sessionFactory.close();
	}
}

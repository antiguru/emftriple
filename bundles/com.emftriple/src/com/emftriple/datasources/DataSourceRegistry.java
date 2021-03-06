/*******************************************************************************
 * Copyright (c) 2011 Guillaume Hillairet.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Guillaume Hillairet - initial API and implementation
 *******************************************************************************/
package com.emftriple.datasources;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

import com.emftriple.datasources.IDataSource.Registry;

public class DataSourceRegistry implements Registry {

	@SuppressWarnings("rawtypes")
	private static final Map<URI, IDataSource> map = new HashMap<URI, IDataSource>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <G, T, N, U, L> IDataSource<G, T, N, U, L> getDataSource(URI uri) {
		return map.get(uri);
	}

	@Override
	public <G, T, N, U, L> void register(URI uri, IDataSource<G, T, N, U, L> dataSource) {
		map.put(uri, dataSource);
	}

}

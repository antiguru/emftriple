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
package com.emftriple.sail;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.openrdf.model.Graph;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

import com.emftriple.datasources.IDataSource;
import com.emftriple.datasources.IResultSet;
import com.emftriple.datasources.IResultSet.Solution;
import com.emftriple.resource.ETripleResourceImpl;
import com.emftriple.transform.Metamodel;
import com.emftriple.transform.SparqlQueries;
import com.emftriple.util.ETripleOptions;

public class SailResourceImpl 
extends ETripleResourceImpl<Graph, Statement, Value, URI, Literal>{

	public SailResourceImpl(org.eclipse.emf.common.util.URI uri) {
		super(uri);
	}

	@Override
	protected Set<String> loadingContentFromResultSet(IResultSet<Value, URI, Literal> resultSet) {
		final Set<String> uris = new HashSet<String>();

		for (;resultSet.hasNext();) {
			Solution<Value, URI, Literal> s = resultSet.next();
			for (String var: s.getSolutionNames()) {
				if (s.isResource(var)) {
					URI res = s.getResource(var);
					if (!uris.contains(res.stringValue())) {
						uris.add(res.stringValue());
					}
				}
			}
		}

		return uris;
	}

	@Override
	public IDataSource<Graph, Statement, Value, URI, Literal> getDataSource(Map<?, ?> options) {
		SailDataSource dataSource = null;
		if (options.containsKey(ETripleOptions.OPTION_DATASOURCE_OBJECT)) {
			Object obj = options.get(ETripleOptions.OPTION_DATASOURCE_OBJECT);
			if (obj instanceof Sail) {
				Sail sail = (Sail) obj;
				try {
					if (!((Sail) obj).isWritable()) {
						sail.shutDown();
					}
				} catch (SailException e) {
					e.printStackTrace();
				}
				dataSource = new SailDataSource(sail);
			}
		}
		return dataSource;
	}

	@Override
	public IDataSource<Graph, Statement, Value, URI, Literal> getDataSource() {
		return getDataSource(getResourceSet().getLoadOptions());
	}

	@Override
	public EObject load(@SuppressWarnings("rawtypes") IDataSource dataSource, String uri, String graphURI) {
		EObject object;
		
		if (getPrimaryCache().hasKey(uri)) {
			object = getPrimaryCache().getObjectByKey(uri);
			
			if (((InternalEObject)object).eIsProxy()) {
				object = new SailEObjectBuilder(this, dataSource).loadEObject(object, uri, graphURI);
			}
			
		} else {
			@SuppressWarnings("unchecked")
			EClass eClass = Metamodel.INSTANCE.getEClassByRdfType(
					SparqlQueries.selectAllTypes(dataSource, uri, graphURI));
			
			if (eClass == null) {
				object = null;
			} else {
				object = EcoreUtil.create(eClass);
				getPrimaryCache().cache(uri, object);
				object = new SailEObjectBuilder(this, dataSource).loadEObject(object, uri, graphURI);
			}
		}

		return object;
	}

	@Override
	protected Collection<Statement> getTriples(EObject object) {
		final org.eclipse.emf.common.util.URI key = this.getID(object);
		final Collection<Statement> triples = new SailRdfBuilder().createTriples(object, key.toString(), 
				new GraphImpl());

		return triples;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(Collection<Statement> triples, @SuppressWarnings("rawtypes") IDataSource dataSource, String graphURI) {
		dataSource.connect();
		dataSource.add(triples, graphURI);
		dataSource.disconnect();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}

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
package com.emftriple.sail.util;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;

import com.emftriple.datasources.IResultSet;

public class SailResultSet implements IResultSet<Value, URI, Literal> {

	private final CloseableIteration<? extends BindingSet, QueryEvaluationException> result;

	public SailResultSet(CloseableIteration<? extends BindingSet, QueryEvaluationException> result) {
		this.result = result;
	}

	@Override
	public boolean hasNext() {
		if (result == null) {
			return false;
		}
		try {
			return result.hasNext();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Solution<Value, URI, Literal> next() {
		try {
			return new SailSolution(result.next());
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove() {
		try {
			result.remove();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
	}

	public static class SailSolution implements Solution<Value, URI, Literal> {

		private final BindingSet solution;

		public SailSolution(BindingSet solution) {
			this.solution = solution;
		}

		@Override
		public Value get(String varName) {
			return solution.getValue(varName);
		}

		@Override
		public boolean isResource(String varName) {
			return solution.getValue(varName) instanceof URI;
		}

		@Override
		public URI getResource(String varName) {
			final Value value = solution.getValue(varName);
			
			return (URI) value;
		}

		@Override
		public boolean isLiteral(String varName) {
			return solution.getValue(varName) instanceof Literal;
		}

		@Override
		public Literal getLiteral(String varName) {
			final Value value = solution.getValue(varName);
			
			return (Literal) value;
		}

		@Override
		public Iterable<String> getSolutionNames() {
			return solution.getBindingNames();
		}

	}

}

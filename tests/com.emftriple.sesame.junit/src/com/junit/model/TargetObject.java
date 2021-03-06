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
package com.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.junit.model.TargetObject#getSingleAttribute <em>Single Attribute</em>}</li>
 *   <li>{@link com.junit.model.TargetObject#getArrayAttribute <em>Array Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.junit.model.ModelPackage#getTargetObject()
 * @model
 * @generated
 */
public interface TargetObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Single Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Single Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Single Attribute</em>' attribute.
	 * @see #setSingleAttribute(String)
	 * @see com.junit.model.ModelPackage#getTargetObject_SingleAttribute()
	 * @model
	 * @generated
	 */
	String getSingleAttribute();

	/**
	 * Sets the value of the '{@link com.junit.model.TargetObject#getSingleAttribute <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Single Attribute</em>' attribute.
	 * @see #getSingleAttribute()
	 * @generated
	 */
	void setSingleAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Array Attribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Attribute</em>' attribute list.
	 * @see com.junit.model.ModelPackage#getTargetObject_ArrayAttribute()
	 * @model
	 * @generated
	 */
	EList<String> getArrayAttribute();

} // TargetObject

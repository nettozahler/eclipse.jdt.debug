/*******************************************************************************
 * Copyright (c) 2002, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.debug.tests.eval;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.jdt.debug.core.IJavaPrimitiveValue;

public class TypeHierarchy_68_2 extends Tests {
	/**
	 * Constructor for TypeHierarchy.
	 * @param name
	 */
	public TypeHierarchy_68_2(String name) {
		super(name);
	}

	public void init() throws Exception {
		initializeFrame("EvalTypeHierarchyTests", 68, 2, 2);
	}

	protected void end() throws Exception {
		destroyFrame();
	}

	public void testEvalNestedTypeTest_m1() throws Throwable {
		try {
		init();
		IValue value = eval("m1()");
		String typeName = value.getReferenceTypeName();
		assertEquals("m1 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("m1 : wrong result : ", 111, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_m2() throws Throwable {
		try {
		init();
		IValue value = eval("m2()");
		String typeName = value.getReferenceTypeName();
		assertEquals("m2 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("m2 : wrong result : ", 222, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_s2() throws Throwable {
		try {
		init();
		IValue value = eval("s2()");
		String typeName = value.getReferenceTypeName();
		assertEquals("s2 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("s2 : wrong result : ", 99, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_m3() throws Throwable {
		try {
		init();
		IValue value = eval("m3()");
		String typeName = value.getReferenceTypeName();
		assertEquals("m3 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("m3 : wrong result : ", 333, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_m4() throws Throwable {
		try {
		init();
		IValue value = eval("m4()");
		String typeName = value.getReferenceTypeName();
		assertEquals("m4 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("m4 : wrong result : ", 444, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_s4() throws Throwable {
		try {
		init();
		IValue value = eval("s4()");
		String typeName = value.getReferenceTypeName();
		assertEquals("s4 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("s4 : wrong result : ", 88, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_super_m1() throws Throwable {
		try {
		init();
		IValue value = eval("super.m1()");
		String typeName = value.getReferenceTypeName();
		assertEquals("super.m1 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("super.m1 : wrong result : ", 1, intValue);
		} finally {
		end();
		}
	}

	public void testEvalNestedTypeTest_super_m2() throws Throwable {
		try {
		init();
		IValue value = eval("super.m2()");
		String typeName = value.getReferenceTypeName();
		assertEquals("super.m2 : wrong type : ", "int", typeName);
		int intValue = ((IJavaPrimitiveValue)value).getIntValue();
		assertEquals("super.m2 : wrong result : ", 2, intValue);
		} finally {
		end();
		}
	}

}

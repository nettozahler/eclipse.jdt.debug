/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.debug.tests.performance;

import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.tests.AbstractDebugPerformanceTest;
import org.eclipse.test.performance.Dimension;

/**
 * Tests performance of stepping.
 */
public class PerfSteppingTests extends AbstractDebugPerformanceTest {
	
	public PerfSteppingTests(String name) {
		super(name);
	}

	public void testRapidStepping() throws Exception {
		tagAsSummary("Rapid Stepping", Dimension.CPU_TIME);
		String typeName = "PerfLoop";
		IJavaLineBreakpoint bp = createLineBreakpoint(20, typeName);
		
		IJavaThread thread= null;
		try {
			thread= launchToLineBreakpoint(typeName, bp);

			IJavaStackFrame frame = (IJavaStackFrame)thread.getTopStackFrame();
			for (int n= 0; n < 10; n++) {
				startMeasuring();
				for (int i = 0; i < 100; i++) {
					stepOver(frame);
				}
				stopMeasuring();
			}
			commitMeasurements();
			assertPerformance();
		} finally {
			terminateAndRemove(thread);
			removeAllBreakpoints();
		}		
	}
}

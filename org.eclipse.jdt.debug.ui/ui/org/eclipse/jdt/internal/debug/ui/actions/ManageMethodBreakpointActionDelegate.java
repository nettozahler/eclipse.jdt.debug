package org.eclipse.jdt.internal.debug.ui.actions;

/**********************************************************************
Copyright (c) 2002 IBM Corp.  All rights reserved.
This file is made available under the terms of the Common Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/cpl-v10.html
**********************************************************************/
 
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaMethodBreakpoint;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.internal.debug.ui.BreakpointUtils;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Adds a method breakpoint on a single selected element of type IMethod 
 */
public class ManageMethodBreakpointActionDelegate extends AbstractManageBreakpointActionDelegate {
		
	protected IJavaBreakpoint getBreakpoint(IMember method) {
		IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints= breakpointManager.getBreakpoints(JDIDebugModel.getPluginIdentifier());
		for (int i= 0; i < breakpoints.length; i++) {
			IBreakpoint breakpoint= breakpoints[i];
			if (breakpoint instanceof IJavaMethodBreakpoint) {
				IMember container = null;
				try {
					container= BreakpointUtils.getMember((IJavaMethodBreakpoint) breakpoint);
				} catch (CoreException e) {
					JDIDebugUIPlugin.log(e);
					return null;
				}
				if (method.equals(container)) {
					return (IJavaBreakpoint)breakpoint;
				}
			}
		}
		return null;
	}
	
	protected IMember getMember(ISelection s) {
		if (s instanceof IStructuredSelection) {
			IStructuredSelection ss= (IStructuredSelection) s;
			if (ss.size() == 1) {					
				Object o=  ss.getFirstElement();
				if (o instanceof IMethod) {
					return (IMethod) o;
				}
			}
		}
		return null;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		updateForRun();
		if (getBreakpoint() == null) {
			// add breakpoint
			try {
				IMethod method = (IMethod)getMember();
				if (method == null || !enableForMember(method)) {
					IStatus status = new Status(IStatus.ERROR, JDIDebugUIPlugin.getUniqueIdentifier(), Status.ERROR, ActionMessages.getString("ManageMethodBreakpointActionDelegate.Method_breakpoints_can_only_be_added_to_concrete,_binary_methods._1"), null); //$NON-NLS-1$
					JDIDebugUIPlugin.errorDialog(ActionMessages.getString("ManageMethodBreakpointActionDelegate.Add_Method_Breakpoint_Failed_2"), status); //$NON-NLS-1$
					return;
				} 
				int start = -1;
				int end = -1;
				ISourceRange range = method.getNameRange();
				if (range != null) {
					start = range.getOffset();
					end = start + range.getLength();
				}
				Map attributes = new HashMap(10);
				BreakpointUtils.addJavaBreakpointAttributes(attributes, method);
				String methodName = method.getElementName();
				if (((IMethod)method).isConstructor()) {
					methodName = "<init>"; //$NON-NLS-1$
				}
				setBreakpoint(JDIDebugModel.createMethodBreakpoint(BreakpointUtils.getBreakpointResource(method), 
					method.getDeclaringType().getFullyQualifiedName(), methodName, method.getSignature(), true, false, false, -1, start, end, 0, true, attributes));
			} catch (CoreException x) {
				JDIDebugUIPlugin.log(x);
				MessageDialog.openError(JDIDebugUIPlugin.getActiveWorkbenchShell(), ActionMessages.getString("ManageMethodBreakpointAction.Problems_creating_breakpoint_7"), x.getMessage()); //$NON-NLS-1$
			}
		} else {
			// remove breakpoint
			try {
				IBreakpointManager breakpointManager= DebugPlugin.getDefault().getBreakpointManager();
				breakpointManager.removeBreakpoint(getBreakpoint(), true);
			} catch (CoreException x) {
				JDIDebugUIPlugin.log(x);
				MessageDialog.openError(JDIDebugUIPlugin.getActiveWorkbenchShell(), ActionMessages.getString("ManageMethodBreakpointAction.Problems_removing_breakpoint_8"), x.getMessage()); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * @see AbstractManageBreakpointActionDelegate#enableForMember(IMember)
	 */
	protected boolean enableForMember(IMember member) {
		try {
			return member instanceof IMethod && member.isBinary() && !Flags.isAbstract(member.getFlags());
		} catch (JavaModelException e) {
			JDIDebugUIPlugin.log(e);
		}
		return false;
	}
	
	/**
	 * Only enabled for binary methods
	 * @see IPartListener#partActivated(IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		super.partActivated(part);
		setEnabledState(getTextEditor());
	}
	
	protected void setEnabledState(ITextEditor editor) {
		if (getAction() != null && getPage() != null) {
			IWorkbenchPart part = getPage().getActivePart();
			if (part == null) {
				getAction().setEnabled(false);
			} else {
				if (part == getPage().getActiveEditor()) {
					IClassFile classFile= (IClassFile)getPage().getActiveEditor().getEditorInput().getAdapter(IClassFile.class);
					getAction().setEnabled(classFile != null);
				} else {
					ISelectionProvider sp= part.getSite().getSelectionProvider();
					getAction().setEnabled(sp != null && enableForMember(getMember(sp.getSelection())));
				}
			}
		}	
	}
}
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.launching;

 
import java.util.Map;

import org.eclipse.jdt.internal.launching.LaunchingMessages;

/**
 * Holder for various arguments passed to a VM runner.
 * Mandatory parameters are passed in the constructor; optional arguments, via setters.
 * <p>
 * Clients may instantiate this class; it is not intended to be subclassed.
 * </p>
 */
public class VMRunnerConfiguration {
	private String fClassToLaunch;
	private String[] fVMArgs;
	private String[] fProgramArgs;
	private String[] fEnvironment;
	private String[] fClassPath;
	private String[] fPrependBootClassPath;
	private String[] fMainBootClassPath;
	private String[] fAppendBootClassPath;
	private String[] fBootClassPath;
	private String fWorkingDirectory;
	private Map fVMSpecificAttributesMap;
	
	private static final String[] fgEmpty= new String[0];
	
	/**
	 * Creates a new configuration for launching a VM to run the given main class
	 * using the given class path.
	 *
	 * @param classToLaunch The fully qualified name of the class to launch. May not be null.
	 * @param classPath 	The classpath. May not be null.
	 */
	public VMRunnerConfiguration(String classToLaunch, String[] classPath) {
		if (classToLaunch == null) {
			throw new IllegalArgumentException(LaunchingMessages.getString("vmRunnerConfig.assert.classNotNull")); //$NON-NLS-1$
		}
		if (classPath == null) {
			throw new IllegalArgumentException(LaunchingMessages.getString("vmRunnerConfig.assert.classPathNotNull")); //$NON-NLS-1$
		}
		fClassToLaunch= classToLaunch;
		fClassPath= classPath;
	}

	/**
	 * Sets the <code>Map</code> that contains String name/value pairs that represent
	 * VM-specific attributes.
	 * 
	 * @param map the <code>Map</code> of VM-specific attributes.
	 * @since 2.0
	 */
	public void setVMSpecificAttributesMap(Map map) {
		fVMSpecificAttributesMap = map;
	}

	/**
	 * Sets the custom VM arguments. These arguments will be appended to the list of 
	 * VM arguments that a VM runner uses when launching a VM. Typically, these VM arguments
	 * are set by the user.
	 * These arguments will not be interpreted by a VM runner, the client is responsible for
	 * passing arguments compatible with a particular VM runner.
	 *
	 * @param args the list of VM arguments
	 */
	public void setVMArguments(String[] args) {
		if (args == null) {
			throw new IllegalArgumentException(LaunchingMessages.getString("vmRunnerConfig.assert.vmArgsNotNull")); //$NON-NLS-1$
		}
		fVMArgs= args;
	}
	
	/**
	 * Sets the custom program arguments. These arguments will be appended to the list of 
	 * program arguments that a VM runner uses when launching a VM (in general: none). 
	 * Typically, these VM arguments are set by the user.
	 * These arguments will not be interpreted by a VM runner, the client is responsible for
	 * passing arguments compatible with a particular VM runner.
	 *
	 * @param args the list of arguments	
	 */
	public void setProgramArguments(String[] args) {
		if (args == null) {
			throw new IllegalArgumentException(LaunchingMessages.getString("vmRunnerConfig.assert.programArgsNotNull")); //$NON-NLS-1$
		}
		fProgramArgs= args;
	}
	
	/**
	 * Sets the environment for the Java program. The Java VM will be
	 * launched in the given environment.
	 * 
	 * @param environment the environment for the Java program specified as an array
	 *  of strings, each element specifying an environment variable setting in the
	 *  format <i>name</i>=<i>value</i>
	 * @since 3.0
	 */
	public void setEnvironment(String[] environment) {
		fEnvironment= environment;
	}
	
	/**
	 * Sets the classpath entries to prepend to the boot classpath - <code>null</code>
	 * or empty if none.
	 * 
	 * @param prependBootClassPath the classpath entries to prepend to the boot classpath - <code>null</code>
	 * or empty if none
	 * @since 3.0
	 */
	public void setPrependBootClassPath(String[] prependBootClassPath) {
		fPrependBootClassPath= prependBootClassPath;
		if (prependBootClassPath != null && prependBootClassPath.length == 0) {
			fPrependBootClassPath = null;
		}
	}
	
	/**
	 * Sets the main boot classpath entries. A value of 
	 * <code>null</code> indicates the default boot classpath should be used
	 * (i.e. not specified on the command line), and empty array indicates
	 * an empty boot classpath attribute.
	 * 
	 * @param bootClassPath the main boot classpath entries, possibly <code>null</code>
	 * @since 3.0
	 */
	public void setMainBootClassPath(String[] bootClassPath) {
		fMainBootClassPath= bootClassPath;
	}
	
	/**
	 * Sets the classpath entries to append to the boot classpath - <code>null</code>
	 * or empty if none.
	 * 
	 * @param appendBootClassPath the entries to append to the boot classpath - 
	 *  <code>null</code> or empty indicates none.
	 * @since 3.0
	 */
	public void setAppendBootClassPath(String[] appendBootClassPath) {
		fAppendBootClassPath= appendBootClassPath;
		if (appendBootClassPath != null && appendBootClassPath.length == 0) {
			fAppendBootClassPath = null;
		}
	}
	
	/**
	 * Sets the boot classpath. Note that the boot classpath will be passed to the 
	 * VM "as is". This means it has to be complete. Interpretation of the boot class path
	 * is up to the VM runner this object is passed to.
	 * <p>
	 * In release 3.0, support has been added for appending and prepending the
	 * boot classpath. Generally an <code>IVMRunner</code> should use the prepend,
	 * main, and append boot classpaths provided. However, in the case that an
	 * <code>IVMRunner</code> does not support these options, a complete boothpath
	 * should also be specified.
	 * </p>
	 * @param bootClassPath The boot classpath. An emptry array indicates an empty
	 *  bootpath and <code>null</code> indicates a default bootpath.
	 */
	public void setBootClassPath(String[] bootClassPath) {
		fBootClassPath= bootClassPath;
	}
	
	/**
	 * Returns the <code>Map</code> that contains String name/value pairs that represent
	 * VM-specific attributes.
	 * 
	 * @return The <code>Map</code> of VM-specific attributes or <code>null</code>.
	 * @since 2.0
	 */
	public Map getVMSpecificAttributesMap() {
		return fVMSpecificAttributesMap;
	}
	
	/**
	 * Returns the name of the class to launch.
	 *
	 * @return The fully qualified name of the class to launch. Will not be <code>null</code>.
	 */
	public String getClassToLaunch() {
		return fClassToLaunch;
	}
	
	/**
	 * Returns the classpath.
	 *
	 * @return the classpath
	 */
	public String[] getClassPath() {
		return fClassPath;
	}
	
	/**
	 * Return the classpath entries to prepend to the boot classpath,
	 * or <code>null</code> if none.
	 * 
	 * @return the classpath entries to prepend to the boot classpath, or
	 *  <code>null</code> if none
	 * @since 3.0
	 */
	public String[] getPrependBootClassPath() {
		return fPrependBootClassPath;
	}
	
	/**
	 * Return the main part of the boot classpath -
	 * <code>null</code> represents the default boot classpath.
	 * 
	 * @return the main part of the boot classpath
	 * @since 3.0
	 */
	public String[] getMainBootClassPath() {
		return fMainBootClassPath;
	}
	
	/**
	 * Return the classpath entries to append to the boot classpath,
	 * or <code>null</code> null if none
	 * 
	 * @return the classpath entries to append to the boot classpath,
	 *  or <code>null</code> if none
	 * @since 3.0
	 */
	public String[] getAppendBootClassPath() {
		return fAppendBootClassPath;
	}

	/**
	 * Returns the boot classpath. An empty array indicates an empty
	 * bootpath and <code>null</code> indicates a default bootpah.
	 * <p>
	 * In 3.0, support has been added for prepending and appending to the boot classpath.
	 * The methods <code>#getPrependBootClassPath()</code>, <code>#getMainBootClassPath()</code>,
	 * and <code>#getAppendBootClassPath()</code> should be used instead of this method
	 * if an <code>IVMRunner</code> supports the options, as they may return more accurate
	 * information. In the case that the other options are not specified, and a single
	 * boot classpath is provided, an <code>IVMRunner</code> should honor the
	 * boot classpath specified by this method.
	 * </p>
	 * @return The boot classpath. An emptry array indicates an empty
	 *  bootpath and <code>null</code> indicates a default bootpah.
	 * @see #setBootClassPath
	 */
	public String[] getBootClassPath() {
		return fBootClassPath;
	}

	/**
	 * Returns the arguments to the VM itself.
	 *
	 * @return The VM arguments. Default is an empty array. Will not be <code>null</code>.
	 * @see #setVMArguments
	 */
	public String[] getVMArguments() {
		if (fVMArgs == null) {
			return fgEmpty;
		}
		return fVMArgs;
	}
	
	/**
	 * Returns the arguments to the Java program.
	 *
	 * @return The Java program arguments. Default is an empty array. Will not be <code>null</code>.
	 * @see #setProgramArguments
	 */
	public String[] getProgramArguments() {
		if (fProgramArgs == null) {
			return fgEmpty;
		}
		return fProgramArgs;
	}
	
	/**
	 * Returns the environment for the Java program or <code>null</code>
	 * 
	 * @return The Java program environment. Default is <code>null</code>
	 * @since 3.0
	 */
	public String[] getEnvironment() {
		return fEnvironment;
	}
	
	/**
	 * Sets the working directory for a launched VM.
	 * 
	 * @param path the absolute path to the working directory
	 *  to be used by a launched VM, or <code>null</code> if
	 *  the default working directory is to be inherited from the
	 *  current process
	 * @since 2.0
	 */
	public void setWorkingDirectory(String path) {
		fWorkingDirectory = path;
	}
	
	/**
	 * Returns the working directory of a launched VM.
	 * 
	 * @return the absolute path to the working directory
	 *  of a launched VM, or <code>null</code> if the working
	 *  directory is inherited from the current process
	 * @since 2.0
	 */
	public String getWorkingDirectory() {
		return fWorkingDirectory;
	}	
}

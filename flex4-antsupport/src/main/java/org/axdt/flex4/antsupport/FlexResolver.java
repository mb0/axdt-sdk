/*******************************************************************************
 * Copyright (c) 2010 Martin Schnabel <mb0@mb0.org>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.axdt.flex4.antsupport;

import java.io.IOException;
import java.net.URL;

import org.eclipse.ant.core.IAntPropertyValueProvider;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableInitializer;
import org.osgi.framework.Bundle;

public class FlexResolver implements IAntPropertyValueProvider, IValueVariableInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.ant.core.IAntPropertyValueProvider#getAntPropertyValue(java.lang.String)
	 */
	public String getAntPropertyValue(String name) {
		if (name.equals("flex.frameworks")) {
			return getValue(new Path("flexsdk/frameworks"));
		}
		return getValue(new Path("flexsdk/"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.variables.IValueVariableInitializer#initialize(org.eclipse.core.variables.IValueVariable)
	 */
	public void initialize(IValueVariable variable) {
		String name = variable.getName();
		if (name.equals("flex.frameworks")) {
			variable.setValue(getValue(new Path("flexsdk/frameworks")));
		}
		variable.setValue(getValue(new Path("flexsdk/")));
	}
	
	public String getValue(Path path) {
		return getBundleURL(path).getPath().replaceFirst("/$", "");
	}
	public URL getBundleURL(IPath path) {
		Bundle bundle = Platform.getBundle("org.axdt.sdk.flex4");
		URL[] urls = FileLocator.findEntries(bundle, path);
		if (urls.length < 1)
			return null;
		try {
			return FileLocator.resolve(urls[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

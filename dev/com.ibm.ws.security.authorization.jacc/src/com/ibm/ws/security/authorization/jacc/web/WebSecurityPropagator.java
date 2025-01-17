/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.security.authorization.jacc.web;

import javax.security.jacc.PolicyConfigurationFactory;

/**
 ** this class is for propagating the security constraints for Web servlet.
 ** since Servlet-3.x feature might not exist, all of servlet related code is located
 ** to the separate feature which only activated when servlet feature exists.
 **/

public interface WebSecurityPropagator {

    public void propagateWebConstraints(PolicyConfigurationFactory pcf,
                                        String contextId,
                                        Object webAppConfig);
}

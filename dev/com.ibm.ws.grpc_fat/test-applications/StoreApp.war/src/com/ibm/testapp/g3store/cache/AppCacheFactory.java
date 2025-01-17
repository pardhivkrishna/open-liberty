/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
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
package com.ibm.testapp.g3store.cache;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author anupag
 *
 */
public class AppCacheFactory {

    private static AppCache appCacheHelper;
    private static Logger log = Logger.getLogger(AppCacheFactory.class.getName());

    public static synchronized AppCache getInstance() {
        // find if it is already created for this object
        if (appCacheHelper != null) {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("getInstance: existing cache: " + appCacheHelper);
            }
        } else {
            // DistributedMap is default
            appCacheHelper = new DistributedMapAppCacheImpl();

        }
        return appCacheHelper;
    }

}

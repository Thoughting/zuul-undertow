/*
 * Copyright 2013 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.Debug
import com.netflix.zuul.context.RequestContext
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Mikey Cohen
 * Date: 3/12/12
 * Time: 1:51 PM
 */
class DebugRequest extends ZuulFilter {

    @Override
    String filterType() {
        return 'pre'
    }

    @Override
    int filterOrder() {
        return 10000
    }

    @Override
    boolean shouldFilter() {
        return true;
    }

    @Override
    Object run() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        return null;
    }
}

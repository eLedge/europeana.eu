/*
 * Copyright (c) 2014 eLedge.net and the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.eledge.android.eu.europeana.search.model.facets.convertors;

import android.content.Context;

import net.eledge.android.eu.europeana.search.model.enums.Country;
import net.eledge.android.eu.europeana.search.model.facets.abstracts.FacetConverter;
import net.eledge.android.toolkit.gui.GuiUtils;

public class Countries implements FacetConverter {

    @Override
    public String createFacetLabel(Context context, String facet) {
        Country country = Country.safeValueOf(facet);
        if (country != null) {
            return GuiUtils.getString(context, country.resourceId);
        }
        return facet;
    }

    @Override
    public String getFacetIcon(String facet) {
        return null;
    }

}

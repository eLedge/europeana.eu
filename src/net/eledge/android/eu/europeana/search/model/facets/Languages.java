package net.eledge.android.eu.europeana.search.model.facets;

import net.eledge.android.eu.europeana.search.model.facets.abstracts.FacetConverter;
import net.eledge.android.eu.europeana.search.model.facets.enums.Language;
import net.eledge.android.toolkit.gui.GuiUtils;
import android.content.Context;

public class Languages implements FacetConverter {

	@Override
	public String createFacetLabel(Context context, String facet) {
		Language language = Language.safeValueOf(facet);
		if (language != null) {
			return GuiUtils.getString(context, language.resourceId);
		}
		return facet;
	}

}

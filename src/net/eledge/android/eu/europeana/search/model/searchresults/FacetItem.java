package net.eledge.android.eu.europeana.search.model.searchresults;

import net.eledge.android.eu.europeana.search.model.enums.FacetItemType;
import net.eledge.android.eu.europeana.search.model.enums.FacetType;

public class FacetItem {
	
	public FacetItemType itemType;
	
	public FacetType facetType;

	public String label;

	public String facet;
	
	public String description;
	
	public boolean last = false;
	
}

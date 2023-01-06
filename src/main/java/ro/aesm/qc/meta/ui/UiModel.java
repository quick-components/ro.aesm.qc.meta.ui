package ro.aesm.qc.meta.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.aesm.qc.base.AbstractMetaModel;
import ro.aesm.qc.meta.ui.model.MUi_Item;
import ro.aesm.qc.meta.ui.model.MUi_Region;

public class UiModel extends AbstractMetaModel {
	public static final String TAG = "ui";

	protected List<MUi_Item> items = new ArrayList<MUi_Item>();
	private Map<String, MUi_Item> itemsMap = new HashMap<String, MUi_Item>();

	protected List<MUi_Region> regions = new ArrayList<MUi_Region>();
	private Map<String, MUi_Region> regionsMap = new HashMap<String, MUi_Region>();

	public UiModel(String name) {
		super(name);
	}

	public void addItem(MUi_Item item) {
		this.items.add(item);
		this.itemsMap.put(item.getName(), item);
		if (item instanceof MUi_Region) {
			MUi_Region region = (MUi_Region) item;
			this.regions.add(region);
			this.regionsMap.put(region.getName(), region);
		}
	}

	public MUi_Item getItem(String name) {
		return itemsMap.get(name);
	}

	public List<MUi_Item> getItems() {
		return items;
	}

	public Map<String, MUi_Item> getItemsMap() {
		return itemsMap;
	}

	public MUi_Region getRegion(String name) {
		return regionsMap.get(name);
	}

	public List<MUi_Region> getRegions() {
		return this.regions;
	}

	public Map<String, MUi_Region> getRegionsMap() {
		return this.regionsMap;
	}
}

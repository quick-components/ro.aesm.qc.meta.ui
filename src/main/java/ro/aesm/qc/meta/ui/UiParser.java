package ro.aesm.qc.meta.ui;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.w3c.dom.Node;

import ro.aesm.qc.api.exception.QcResourceException;
import ro.aesm.qc.api.meta.component.IMetaComponentParser;
import ro.aesm.qc.base.AbstractMetaParser;
import ro.aesm.qc.meta.ui.model.MUi_Button;
import ro.aesm.qc.meta.ui.model.MUi_Checkbox;
import ro.aesm.qc.meta.ui.model.MUi_DateField;
import ro.aesm.qc.meta.ui.model.MUi_FormItem;
import ro.aesm.qc.meta.ui.model.MUi_HiddenField;
import ro.aesm.qc.meta.ui.model.MUi_Html;
import ro.aesm.qc.meta.ui.model.MUi_Item;
import ro.aesm.qc.meta.ui.model.MUi_LovField;
import ro.aesm.qc.meta.ui.model.MUi_NumberField;
import ro.aesm.qc.meta.ui.model.MUi_PopupLayoutConfig;
import ro.aesm.qc.meta.ui.model.MUi_Region;
import ro.aesm.qc.meta.ui.model.MUi_TabsLayoutConfig;
import ro.aesm.qc.meta.ui.model.MUi_TextField;
import ro.aesm.qc.meta.ui.model.MUi_Textarea;
import ro.aesm.qc.meta.ui.model.feature.GridLayoutChildItem;
import ro.aesm.qc.meta.ui.model.feature.LabeledItem;

@Component(service = IMetaComponentParser.class)
public class UiParser extends AbstractMetaParser {

	protected String labelPosition; // before top
	protected String labelAlign; // start center end
	protected String showLabel;// yes no empty (for alignment purpose)

	public UiParser() {
		super();
		this.getNamespaceMap().put(ConstantsUi.XMLNS_ALIAS, ConstantsUi.XMLNS);
		this.getNamespaceMap().put("", "*");
	}

	@Override
	public String getTag() {
		return ConstantsUi.TAG;
	}

	@Override
	public UiModel parse(Node node) throws QcResourceException {
		String name = this.getAttr(node, "name").trim();
		UiModel uiModel = new UiModel(name);

		// parse

		Node detailsNode = this.getNode(node, "items");
		if (detailsNode != null) {
			for (Node childNode : this.getNodes(detailsNode, "*")) {
				MUi_Item _mm = this.parseItem(childNode);
				if (_mm != null) {
					uiModel.addItem(_mm);
				}
			}
		}

		// post process meta models

		// Map<String, MUi_Item> itemsMap = mm.getItemsMap();
		MUi_Region mainRegion = uiModel.getRegion(ConstantsUi.MAIN_REGION_NAME);

		GridLayoutChildItem defaultGridLayoutChildItem = new GridLayoutChildItem();
		defaultGridLayoutChildItem.applyDefaults(null);
		LabeledItem defaultLabeledItem = new LabeledItem();
		defaultLabeledItem.applyDefaults(null);

		mainRegion.getGridLayoutChildItem().applyDefaults(defaultGridLayoutChildItem);
		mainRegion.getDefaultsProvider().getGridLayoutChildItem().applyDefaults(defaultGridLayoutChildItem);
		mainRegion.getDefaultsProvider().getLabeledItem().applyDefaults(defaultLabeledItem);

		this.postProcessRegion(mainRegion, null, uiModel);

//		for (MUi_Region region : mm.getRegions()) {
//			
//		}
		return uiModel;
	}

	protected void postProcessRegion(MUi_Region region, MUi_Region parent, UiModel uiModel)
			throws QcResourceException {
		Map<String, MUi_Item> itemsMap = uiModel.getItemsMap();
		String childrenNames = region.getChildrenNamesValue();
		if (childrenNames == null) {
			return;
		}
		String[] tmp = childrenNames.split(",");
		List<MUi_Item> children = new ArrayList<>();
		for (String cn : tmp) {
			String childName = cn.strip();
			if (childName.startsWith("@")) {
				uiModel.addItem(UiModelTemplates.createItem(childName));
				childName = childName.substring(1);
			}
			MUi_Item child = itemsMap.get(childName );
			if (child == null) {
				throw new QcResourceException(String.format(
						"Region %s declares as child item `%s` which is not defined", region.getName(), childName));
			}
			children.add(itemsMap.get(childName));

			child.getGridLayoutChildItem().applyDefaults(region.getDefaultsProvider().getGridLayoutChildItem());

			if (child instanceof MUi_FormItem) {
				((MUi_FormItem) child).getLabeledItem().applyDefaults(region.getDefaultsProvider().getLabeledItem());
			} else if (child instanceof MUi_Region) {
				((MUi_Region) child).getDefaultsProvider().getLabeledItem()
						.applyDefaults(region.getDefaultsProvider().getLabeledItem());
				((MUi_Region) child).getDefaultsProvider().getGridLayoutChildItem()
						.applyDefaults(region.getDefaultsProvider().getGridLayoutChildItem());

				this.postProcessRegion((MUi_Region) child, region, uiModel);
			}
		}
		region.setChildren(children);
	}

	protected MUi_Item parseItem(Node node) {
		String tag = node.getLocalName();
		if (tag.equals(ConstantsUi.REGION_TAG)) {
			return this.parseRegion(node);
		} else if (tag.equals(ConstantsUi.BUTTON_TAG)) {
			return this.parseButton(node);
		} else if (tag.equals(ConstantsUi.TEXT_FIELD_TAG)) {
			return this.parseTextField(node);
		} else if (tag.equals(ConstantsUi.TEXT_AREA_TAG)) {
			return this.parseTextArea(node);
		} else if (tag.equals(ConstantsUi.DATE_FIELD_TAG)) {
			return this.parseDateField(node);
		} else if (tag.equals(ConstantsUi.NUMBER_FIELD_TAG)) {
			return this.parseNumberField(node);
		} else if (tag.equals(ConstantsUi.HIDDEN_FIELD_TAG)) {
			return this.parseHiddenField(node);
		} else if (tag.equals(ConstantsUi.LOV_FIELD_TAG)) {
			return this.parseLovField(node);
		} else if (tag.equals(ConstantsUi.CHECKBOX_TAG)) {
			return this.parseCheckbox(node);
		} else if (tag.equals(ConstantsUi.HTML_TAG)) {
			return this.parseHtml(node);
		}
		return null;
	}

	protected void parseUiItem(Node node, MUi_Item item) {
		// item.setDsItem(this.getAttr(node, "ds-item"));
		item.setName(this.getAttr(node, ConstantsUi.NAME_ATTR));
		item.setCssClass(this.getAttr(node, ConstantsUi.CSS_CLASS_ATTR));
		this.parseGridLayoutChildItem(node, item.getGridLayoutChildItem());
	}

	protected void parseFormItem(Node node, MUi_FormItem item) {
		this.parseUiItem(node, item);
		this.parseLabeledItem(node, item.getLabeledItem());
		item.setLabel(this.getAttr(node, ConstantsUi.LABEL_ATTR));
		item.setRequired(this.getAttrAsBoolean(node, "required", false));
	}

	protected void parseLabeledItem(Node node, LabeledItem li) {
		li.setShowLabel(this.getAttr(node, ConstantsUi.SHOW_LABEL_ATTR)); // , "yes"
		li.setLabelPosition(this.getAttr(node, ConstantsUi.LABEL_POSITION_ATTR)); // , MUi_Item.LABEL_POSITION_BEFORE
		li.setLabelAlign(this.getAttr(node, ConstantsUi.LABEL_ALIGN_ATTR)); // , MUi_Item.LABEL_ALIGN_START
		li.setLabelWidth(this.getAttrAsInt(node, ConstantsUi.LABEL_WIDTH_ATTR, -1)); // 120
	}

	protected void parseGridLayoutChildItem(Node node, GridLayoutChildItem glc) {
		glc.setColspan(this.getAttrAsInt(node, ConstantsUi.COLSPAN_ATTR, -1));
		glc.setRowspan(this.getAttrAsInt(node, ConstantsUi.ROWSPAN_ATTR, -1));
		glc.setNewRow(this.getAttrAsBoolean(node, ConstantsUi.NEWROW_ATTR));
	}

	protected MUi_Region parseRegion(Node node) {
		MUi_Region item = new MUi_Region();
		this.parseUiItem(node, item);
		item.setLayout(this.getAttr(node, "layout"));
		item.setChildrenNamesValue(this.getAttr(node, "children"));

		Node childNode = this.getNode(node, ConstantsUi.DEFAULTS_TAG);
		if (childNode != null) {
			this.parseRegionChildrenDefaults(childNode, item);
		}

//		childNode = this.getNode(node, ConstantsUi.REGION_POPUP_CONFIG_TAG);
//		if (childNode != null) {
//			MUi_PopupLayoutConfig cfg = new MUi_PopupLayoutConfig();
//			item.setPopupConfig(cfg);
//		}
//		childNode = this.getNode(node, ConstantsUi.REGION_TABS_CONFIG_TAG);
//		if (childNode != null) {
//			MUi_TabsLayoutConfig cfg = new MUi_TabsLayoutConfig();
//			item.setTabsConfig(cfg);
//		}
		return item;
	}

	protected void parseRegionChildrenDefaults(Node node, MUi_Region region) {
		this.parseLabeledItem(node, region.getDefaultsProvider().getLabeledItem());
		this.parseGridLayoutChildItem(node, region.getDefaultsProvider().getGridLayoutChildItem());
	}

	protected MUi_TextField parseTextField(Node node) {
		MUi_TextField item = new MUi_TextField();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_Textarea parseTextArea(Node node) {
		MUi_Textarea item = new MUi_Textarea();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_DateField parseDateField(Node node) {
		MUi_DateField item = new MUi_DateField();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_NumberField parseNumberField(Node node) {
		MUi_NumberField item = new MUi_NumberField();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_HiddenField parseHiddenField(Node node) {
		MUi_HiddenField item = new MUi_HiddenField();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_LovField parseLovField(Node node) {
		MUi_LovField item = new MUi_LovField();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_Checkbox parseCheckbox(Node node) {
		MUi_Checkbox item = new MUi_Checkbox();
		this.parseFormItem(node, item);
		return item;
	}

	protected MUi_Button parseButton(Node node) {
		MUi_Button item = new MUi_Button();
		this.parseUiItem(node, item);
		item.setText(this.getAttr(node, "text"));
		item.setIcon(this.getAttr(node, "icon"));
		return item;
	}

	protected MUi_Html parseHtml(Node node) {
		MUi_Html item = new MUi_Html();
		this.parseUiItem(node, item);
		item.setContent(this.getValue(node));
		return item;
	}

}

package ro.aesm.qc.meta.ui;

public class ConstantsUi {
	public static final String XMLNS_ALIAS = "ui";
	public static final String XMLNS = "https://qc.aesm.ro/meta/ui";

	public static final String TAG = "ui";

	// item tags
	// ==================================================
	public static final String TEXT_FIELD_TAG = "text_field";
	public static final String DATE_FIELD_TAG = "date_field";
	public static final String NUMBER_FIELD_TAG = "number_field";
	public static final String HIDDEN_FIELD_TAG = "hidden_field";
	public static final String LOV_FIELD_TAG = "lov_field";
	public static final String TEXT_AREA_TAG = "textarea";
	public static final String CHECKBOX_TAG = "checkbox";
	public static final String BUTTON_TAG = "button";
	public static final String HTML_TAG = "html";
	public static final String REGION_TAG = "region";

	public static final String MAIN_REGION_NAME = "main";

//	public static final String REGION_TABS_CONFIG_TAG = "tabs-config";
//	public static final String REGION_POPUP_CONFIG_TAG = "popup-config";

	public static final String DEFAULTS_TAG = "defaults";

	// properties
	// ==================================================
	public static final String NAME_ATTR = "name";
	public static final String CSS_CLASS_ATTR = "cssClass";
	
	public static final String LABEL_ATTR = "label";

	public static final String LAYOUT_HORIZONTAL = "horizontal";
	public static final String LAYOUT_VERTICAL = "vertical";
	public static final String LAYOUT_GRID = "grid";
	public static final String LAYOUT_TABS = "tabs";

	// grid layout child
	// ==================================================
	public static final String COLSPAN_ATTR = "colspan";
	public static final String ROWSPAN_ATTR = "rowspan";
	public static final String NEWROW_ATTR = "newRow";

	// default values
	public static final int COLSPAN_VALUE = 12;
	public static final int ROWSPAN_VALUE = 1;
	public static final boolean NEWROW_VALUE = false;

	// labeled item
	// ==================================================
	public static final String LABEL_POSITION_ATTR = "labelPosition";
	public static final String LABEL_ALIGN_ATTR = "labelAlign";
	public static final String LABEL_WIDTH_ATTR = "labelWidth";
	public static final String SHOW_LABEL_ATTR = "showLabel";

	// default values
	public static final String LABEL_POSITION_VALUE = "front";
	public static final String LABEL_ALIGN_VALUE = "end";
	public static final int LABEL_WIDTH_VALUE = 120;
	public static final String SHOW_LABEL_VALUE = "yes";

	// values
	public static final String LABEL_POSITION__FRONT = "front";
	public static final String LABEL_POSITION__TOP = "top";

	public static final String LABEL_ALIGN__BEGIN = "begin";
	public static final String LABEL_ALIGN__CENTER = "center";
	public static final String LABEL_ALIGN__END = "end";

	public static final String SHOW_LABEL__YES = "yes";
	public static final String SHOW_LABEL__NO = "no";
	public static final String SHOW_LABEL__EMPTY = "empty";// (for alignment purpose)

}

package com.seeyon.apps.a8x.oa.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class XmlRowManyUtils {

	private XmlRowManyUtils() {

	}

	public static Map<String, Object> toMap(String docXml) {
		try {
			Document doc = DocumentHelper.parseText(docXml);
			return toMap(doc);
		} catch (DocumentException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private static Map<String, Object> toMap(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null) {
			return map;
		}
		Element root = doc.getRootElement();
		map.put("formname", root.attribute("formName").getText());

	
		Element rowElement = root;
		String display = null;
		for (Iterator iterator = rowElement.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), toMap(e));
			} else {
				map.put(e.getName(), e.getText());
				display = e.attributeValue("display");
				if (display != null) {
					map.put(e.attributeValue("display"), e.getText());
				}
			}
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Object> toMap(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();
				if (iter.elements().size() > 0) {
					Map<String, Object> m = toMap(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else {
						map.put(iter.getName(), m);
					}
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else {
						map.put(iter.getName(), iter.getText());
					}
				}
			}
		} else {
			map.put(e.getName(), e.getText());
		}
		return map;
	}
}

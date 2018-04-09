package com.seeyon.apps.a8x.common.util;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

final public class UtilXml {

	private UtilXml() {

	}

	public static List<Element> childElementList(Element element, String childElementName) {
		if (element == null)
			return null;
		List<Element> elements = new LinkedList<Element>();
		Node node = element.getFirstChild();
		if (node != null) {
			do {
				if (node.getNodeType() == Node.ELEMENT_NODE && (childElementName == null || childElementName.equals(node.getNodeName()))) {
					Element childElement = (Element) node;
					elements.add(childElement);
				}
			} while ((node = node.getNextSibling()) != null);
		}
		return elements;
	}

	public static Element firstChildElement(Element element, String childElementName) {
		if (element == null)
			return null;
		Node node = element.getFirstChild();
		if (node != null) {
			do {
				if (node.getNodeType() == Node.ELEMENT_NODE && (childElementName == null || childElementName.equals(node.getNodeName()))) {
					Element childElement = (Element) node;
					return childElement;
				}
			} while ((node = node.getNextSibling()) != null);
		}
		return null;
	}
}

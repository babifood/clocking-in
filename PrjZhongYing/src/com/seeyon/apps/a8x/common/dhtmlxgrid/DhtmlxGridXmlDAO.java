package com.seeyon.apps.a8x.common.dhtmlxgrid;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.seeyon.apps.a8x.common.util.UtilXml;
import com.seeyon.ctp.common.AppContext;

public class DhtmlxGridXmlDAO {
	private String filePathName;
	private DhtmlxGridModel model;

	public DhtmlxGridXmlDAO(String theFileName) {
		filePathName = theFileName;
	}

	public DhtmlxGridModel getModel() {
		return model;
	}

	public void setModel(DhtmlxGridModel model) {
		this.model = model;
	}

	private Element getRoot() {
		Element root = null;
		try {

			HttpServletRequest request = (HttpServletRequest) AppContext.getThreadContext("THREAD_CONTEXT_REQUEST_KEY");
			String path = request.getSession().getServletContext().getRealPath("WEB-INF/a8xgrid");
			InputStream tmpInputStream = new FileInputStream(path + "/" + filePathName + ".xml");
			root = loadDocument(tmpInputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}

	public Element loadDocument(InputStream tmpInputStream) {
		Document doc = null;
		try {
			InputSource xmlInp = new InputSource(tmpInputStream);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
			doc = parser.parse(xmlInp);
			Element root = doc.getDocumentElement();
			root.normalize();
			return root;
		} catch (SAXParseException err) {
			System.err.println("DhtmlxGridHeaderXmlDAO ** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + err.getMessage());
		} catch (SAXException e) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + e);
		} catch (java.net.MalformedURLException mfx) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + mfx);
		} catch (java.io.IOException e) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + e);
		} catch (Exception pce) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + pce);
		}
		return null;
	}

	public Element loadDocument(URL url) {
		Document doc = null;
		try {
			InputSource xmlInp = new InputSource(url.openStream());
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
			doc = parser.parse(xmlInp);
			Element root = doc.getDocumentElement();
			root.normalize();
			return root;
		} catch (SAXParseException err) {
			System.err.println("DhtmlxGridHeaderXmlDAO ** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + err.getMessage());
		} catch (SAXException e) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + e);
		} catch (java.net.MalformedURLException mfx) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + mfx);
		} catch (java.io.IOException e) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + e);
		} catch (Exception pce) {
			System.err.println("DhtmlxGridHeaderXmlDAO error: " + pce);
		}
		return null;
	}

	public void doParse() {
		Element tmpRoot = getRoot();
		if (tmpRoot == null) {
			return;
		}
		load(tmpRoot);
	}

	@SuppressWarnings("rawtypes")
	private void load(Element element) {

		List<Element> flagElementList = UtilXml.childElementList(element, "column");
		model = new DhtmlxGridModel((flagElementList.size() * 4) / 3 + 1);

		Element flagElement = null;
		DhtmlxGridField dhtmlxGridHeader = null;

		if (flagElementList != null) {
			Iterator flagElementIt = flagElementList.iterator();
			while (flagElementIt.hasNext()) {
				dhtmlxGridHeader = new DhtmlxGridField();
				flagElement = (Element) flagElementIt.next();

				dhtmlxGridHeader.setDescription(flagElement.getTextContent().trim());
				dhtmlxGridHeader.setWidth(flagElement.getAttribute("width").trim());
				dhtmlxGridHeader.setType(flagElement.getAttribute("type").trim());
				dhtmlxGridHeader.setAlign(flagElement.getAttribute("align").trim());
				dhtmlxGridHeader.setSort(flagElement.getAttribute("sort").trim());
				dhtmlxGridHeader.setColor(flagElement.getAttribute("color").trim());
				dhtmlxGridHeader.setFormat(flagElement.getAttribute("format").trim());
				dhtmlxGridHeader.setHidden(flagElement.getAttribute("hidden").trim());
				dhtmlxGridHeader.setAttribute(flagElement.getAttribute("attribute").trim());
				dhtmlxGridHeader.setColumnID(flagElement.getAttribute("columnid").trim());
				if (dhtmlxGridHeader.getColumnID().equals("")) {
					dhtmlxGridHeader.setColumnID(dhtmlxGridHeader.getAttribute());
				}
				dhtmlxGridHeader.setAuth(flagElement.getAttribute("auth").trim());
				dhtmlxGridHeader.setFooter(flagElement.getAttribute("footer").trim());
				dhtmlxGridHeader.setStat(flagElement.getAttribute("stat").trim());
				dhtmlxGridHeader.setExcelStyle(flagElement.getAttribute("excelStyle").trim());
				model.add(dhtmlxGridHeader);
			}
		}

		List tmpList = UtilXml.childElementList(element, "userdata");
		Element tmpElement = null;
		DhtmlxUserData dhtmlxUserData = null;
		if (tmpList != null) {
			Iterator tmpElementIt = tmpList.iterator();
			while (tmpElementIt.hasNext()) {
				dhtmlxUserData = new DhtmlxUserData();
				tmpElement = (Element) tmpElementIt.next();
				dhtmlxUserData.setName(tmpElement.getAttribute("name").trim());
				dhtmlxUserData.setAttribute(tmpElement.getAttribute("attribute").trim());
				dhtmlxUserData.setColumnID(tmpElement.getAttribute("columnid").trim());
				dhtmlxUserData.setAuth(tmpElement.getAttribute("auth").trim());
				model.add(dhtmlxUserData);
			}
		}

		List tmpGlobalList = UtilXml.childElementList(element, "globaluserdata");
		if (tmpGlobalList != null) {
			Iterator tmpElementIt = tmpGlobalList.iterator();
			while (tmpElementIt.hasNext()) {
				dhtmlxUserData = new DhtmlxUserData();
				tmpElement = (Element) tmpElementIt.next();
				dhtmlxUserData.setDescription(tmpElement.getTextContent().trim());
				dhtmlxUserData.setFormat(tmpElement.getAttribute("format").trim());
				dhtmlxUserData.setName(tmpElement.getAttribute("name").trim());

				dhtmlxUserData.setAttribute(tmpElement.getAttribute("attribute").trim());
				dhtmlxUserData.setAuth(tmpElement.getAttribute("auth").trim());
				model.addGlobal(dhtmlxUserData);
			}
		}
	}
}

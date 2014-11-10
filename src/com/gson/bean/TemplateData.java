/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.bean;

import java.util.HashMap;
import java.util.List;

/**
 *	模板消息数据对象
 *
 * @author L.cm
 * @date 2014-11-10 下午3:32:30
 * @description 模板消息数据对象
 */
public class TemplateData {

	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private List<TemplateItem> data;
	
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public List<TemplateItem> getData() {
		return data;
	}

	public void setData(List<TemplateItem> data) {
		this.data = data;
	}

	class TemplateItem extends HashMap<String, Item> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TemplateItem(String key, Item item) {
			this.put(key, item);
		}
	}

	class Item {
		private String value;
		private String color;

		public void setColor(String color) {
			this.color = color;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getColor() {
			return color;
		}
	}
}

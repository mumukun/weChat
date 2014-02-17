/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.bean;

import java.io.BufferedInputStream;

/**
 * 下载文件对象
 * @author ____′↘夏悸
 *
 */
public class Attachment {

	private String filename;
	private String contentLength;
	private String contentType;
	private BufferedInputStream fileStream;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getContentLength() {
		return contentLength;
	}
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public BufferedInputStream getFileStream() {
		return fileStream;
	}
	public void setFileStream(BufferedInputStream fileStream) {
		this.fileStream = fileStream;
	}
}

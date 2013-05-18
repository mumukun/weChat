package com.gson;

import com.gson.bean.InMessage;
import com.gson.bean.OutMessage;
import com.gson.inf.MessageProcessingHandler;

/**
 * 自定义实现消息处理
 * @author GodSon
 *
 */
public class MessageProcessingHandlerImpl implements MessageProcessingHandler {

	@Override
	public OutMessage textTypeMsg(InMessage msg) {
		OutMessage oms = new OutMessage();
		oms.setContent(msg.getContent());
		return oms;
	}

	@Override
	public OutMessage locationTypeMsg(InMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutMessage imageTypeMsg(InMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutMessage linkTypeMsg(InMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutMessage eventTypeMsg(InMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

}

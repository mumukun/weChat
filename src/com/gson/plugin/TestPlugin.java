package com.gson.plugin;


public class TestPlugin implements Plugin {

	@Override
	public void run(Object... obj) {
		System.out.println("plugin runing");
	}
}

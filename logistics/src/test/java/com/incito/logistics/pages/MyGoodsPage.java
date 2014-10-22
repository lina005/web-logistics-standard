
package com.incito.logistics.pages;

import org.openqa.selenium.By;

/**
 * @author xy-incito-wy
 * @desprition  我的货源页面元素声明
 * */
public class MyGoodsPage {
	
	/**货源编号输入框*/
	public static final By MGP_INPUT_GOODSNO = By.name("goodsno");
	/**发货地输入框*/
	public static final By MGP_INPUT_ORIGINALCITY = By.name("localcity");
	/**收货地输入框*/
	public static final By MGP_INPUT_TARGETCITY= By.name("targetcity");
	/**搜索按钮*/
	public static final By MGP_BUTTON_SEARCH = By.xpath("//button[@onclick='doSearch()']");	
	/**高级搜索按钮*/
	public static final By MGP_BUTTON_ADSEARCH = By.id("advancedBtn");
	//**********以下元素 是点击了高级搜索按钮出现的 我是分隔符*********************
	/**货物名*/
	public static final By MGP_INPUT_GOODSNAME= By.name("goodsnames");
	/**单位-体积还是重量*/
	public static final By MGP_SELECT_GOODSUNIT = By.id("wvSwitch");
	/**开始吨位*/
	public static final By MGP_INPUT_STARTWEIGHT = By.name("startweight");
	/**结束吨位*/
	public static final By MGP_INPUT_ENDWEIGHT = By.name("endweight");
	/**开始体积*/
	public static final By MGP_INPUT_STARTVOLUME = By.name("startvolume");
	/**结束体积*/
	public static final By MGP_INPUT_ENDVOLUME = By.name("endvolume");
	/**发布开始时间*/
	public static final By MGP_INPUT_STARTDATE = By.name("startgoodstime");
	/**发布结束时间*/
	public static final By MGP_INPUT_ENDDATE = By.name("endgoodstime");
	//**********以上元素 是点击了高级搜索按钮出现的 我是分隔符*********************
	
	/**查看全部 新货源 已失效的货源 已交易的货源  四个radiobox*/
	public static final By MGP_RADIOBOX_GOODSTYPE = By.xpath("//label[@class='radio-inline']/input");
	/**判断每页条数的关键元素*/
	public static final By MGP_ITEM_GOODS = By.xpath("//*[@class='mygoods-inbox']");
	

}

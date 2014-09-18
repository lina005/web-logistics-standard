package com.incito.logistics.testcase.home.logout;

/**
 *@author  xy-incito-wy
 *@Description 测试用例：未登录情况下点击公共货源的定位按钮 弹出登陆页面
 *
 * */
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.incito.logistics.base.BaseParpare;
import com.incito.logistics.pages.HomePage;
import com.incito.logistics.pages.pageshelper.HomePageHelper;
import com.incito.logistics.pages.pageshelper.LoginPageHelper;

public class HomePage_NotLogin_011_GoodsLoc_Test extends BaseParpare {
	@Test
	public void clickLocButton(ITestContext context) {
		int timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		int indexGoods = Integer.valueOf(context.getCurrentXmlTest().getParameter("indexGoods"));

		HomePageHelper.waitHomePageToLoad(timeOut, seleniumUtil);
		HomePageHelper.enterPage(seleniumUtil, HomePage.HP_ARERA_CARDSANDGOODS, HomePage.HP_BUTTON_LOC, indexGoods);
		LoginPageHelper.waitLoginPageToLoad(timeOut, seleniumUtil);
		LoginPageHelper.checkLoginPageText(seleniumUtil);

	}

}
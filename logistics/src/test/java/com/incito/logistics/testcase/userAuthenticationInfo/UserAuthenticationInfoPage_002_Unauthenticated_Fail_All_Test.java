package com.incito.logistics.testcase.userAuthenticationInfo;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.incito.logistics.pages.AddUserInfoPage;
import com.incito.logistics.pages.pageshelper.AddUserInfoPagerHelper;
import com.incito.logistics.plugins.father.UserAuthenticationInfoFather;

/**
 * @author xy-incito-wk
 * @Description 填写信息全为空直接提交
 *
 * */
public class UserAuthenticationInfoPage_002_Unauthenticated_Fail_All_Test extends UserAuthenticationInfoFather {
	@Test
	public void addUserInfoPageFailAllTest(ITestContext context) {
		UserAuthenticationInfoFather.userAuthenticationInfoParpare(context, seleniumUtil);
		AddUserInfoPagerHelper.enterPage(seleniumUtil, AddUserInfoPage.AUIP_BUTTON_SUBMIT);
		AddUserInfoPagerHelper.checkAddUserInfoPrompt(timeOut, seleniumUtil);
	}
}
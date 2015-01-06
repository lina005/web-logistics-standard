package com.incito.logistics.testcase.userAuthenticationInfo;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.incito.logistics.pages.UserAuthenticationInfoPage;
import com.incito.logistics.pages.pageshelper.UserAuthenticationInfoPagerHelper;
import com.incito.logistics.plugins.father.UserAttestedInfoFather;

/**
 * @author xy-incito-wk
 * @Description 填写信息姓名，联系方式，身份证号点击提交
 *
 * */
public class UserAuthenticationInfoPage_018_Unauthenticated_Fail_NameTelIdcar_Test extends UserAttestedInfoFather {
	@Test(dataProvider = "data")
	public void addUserInfoPageFailNameTelIdcar(ITestContext context, Map<String, String> data) {
		UserAttestedInfoFather.userUnatestedInfoParpare(context, seleniumUtil);

		By[] bys={UserAuthenticationInfoPage.AUIP_INPUT_TEL, UserAuthenticationInfoPage.AUIP_INPUT_PHOTO,
				UserAuthenticationInfoPage.AUIP_INPUT_CHIT, UserAuthenticationInfoPage.AUIP_BUTTON_CHIT };
		UserAuthenticationInfoPagerHelper.typeTelChit(seleniumUtil, bys, data.get("ADD_TEL"), 
				data.get("ADD_PHOTO"), data.get("ADD_CHIT"));
		seleniumUtil.type(seleniumUtil.findElementBy(UserAuthenticationInfoPage.AUIP_INPUT_NAME), data.get("ADD_NAME"));
		seleniumUtil.type(seleniumUtil.findElementBy(UserAuthenticationInfoPage.AUIP_INPUT_IDCARD), data.get("ADD_IDCARD"));
		UserAuthenticationInfoPagerHelper.enterPage(seleniumUtil, UserAuthenticationInfoPage.AUIP_BUTTON_SUBMIT);
		UserAuthenticationInfoPagerHelper.checkUserAuthenticationInfoPrompt(timeOut, seleniumUtil);
	}
}
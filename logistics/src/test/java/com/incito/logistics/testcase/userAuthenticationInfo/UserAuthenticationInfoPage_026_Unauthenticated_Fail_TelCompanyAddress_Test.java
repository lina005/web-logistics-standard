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
 * @Description 填写联系方式，公司名称，公司地址点击提交
 *
 * */
public class UserAuthenticationInfoPage_026_Unauthenticated_Fail_TelCompanyAddress_Test extends UserAttestedInfoFather {
	@Test(dataProvider = "data")
	public void addUserInfoPageFailTelCompanyAddress(ITestContext context, Map<String, String> data) {
		UserAttestedInfoFather.userUnatestedInfoParpare(context, seleniumUtil);

		By[] bys={UserAuthenticationInfoPage.AUIP_INPUT_TEL, UserAuthenticationInfoPage.AUIP_INPUT_PHOTO,
				UserAuthenticationInfoPage.AUIP_INPUT_CHIT, UserAuthenticationInfoPage.AUIP_BUTTON_CHIT };
		UserAuthenticationInfoPagerHelper.typeTelChit(seleniumUtil, bys, data.get("ADD_TEL"), 
				data.get("ADD_PHOTO"), data.get("ADD_CHIT"));
		seleniumUtil.type(seleniumUtil.findElementBy(UserAuthenticationInfoPage.AUIP_INPUT_COMPANY), data.get("ADD_COMPANY"));
		seleniumUtil.address(UserAuthenticationInfoPage.AUIP_SELECT_PROVINCE, data.get("ADD_PROVINCE"), UserAuthenticationInfoPage.AUIP_SELECT_CITY, data.get("ADD_CITY"), UserAuthenticationInfoPage.AUIP_SELECT_REGION, data.get("ADD_REGION"), UserAuthenticationInfoPage.AUIP_INPUT_ADDRESS, data.get("ADD_ADDRESS"));
		UserAuthenticationInfoPagerHelper.enterPage(seleniumUtil, UserAuthenticationInfoPage.AUIP_BUTTON_SUBMIT);
		UserAuthenticationInfoPagerHelper.checkUserAuthenticationInfoPrompt(timeOut, seleniumUtil);
	}
}

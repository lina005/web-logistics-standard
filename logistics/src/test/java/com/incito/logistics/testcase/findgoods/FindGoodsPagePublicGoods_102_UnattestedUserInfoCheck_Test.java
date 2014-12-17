package com.incito.logistics.testcase.findgoods;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.incito.logistics.pages.FindGoodsPage;
import com.incito.logistics.pages.pageshelper.FindGoodsPageHelper;
import com.incito.logistics.plugins.father.FindGoodsFather;

/**
 * 
 * @author xy-incito-wk
 * @Description 测试用例：已登录的情况下，未认证的用户登录，隐藏信息的检验
 */
public class FindGoodsPagePublicGoods_102_UnattestedUserInfoCheck_Test extends FindGoodsFather {
	@Test
	public void FindGoodsPagePublicGoodsInfoCheck(ITestContext context) {
		FindGoodsFather.UnattestedFindGoodsParpare(context, seleniumUtil);
		
		FindGoodsPageHelper.unattestedUserChecked(seleniumUtil, FindGoodsPage.FGP_DISPLAY_SEARCH, FindGoodsPage.FGP_HIDE_ATTESTED);
	}
}
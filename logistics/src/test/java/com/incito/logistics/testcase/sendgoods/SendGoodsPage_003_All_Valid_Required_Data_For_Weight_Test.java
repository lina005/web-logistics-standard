package com.incito.logistics.testcase.sendgoods;
import java.util.Map;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.incito.logistics.pages.SendGoodsPage;
import com.incito.logistics.pages.pageshelper.SendGoodsPageHelper;
import com.incito.logistics.pages.pageshelper.SendGoodsSuccessPageHelper;
import com.incito.logistics.plugins.father.SendGoodsFather;

/**
 * @author xy-incito-wy
 * @Description 测试用例：发布货源，所有的输入合法有效，然后点击发布按钮确认是否发布成功(重量):只填 必填项
 * */
public class SendGoodsPage_003_All_Valid_Required_Data_For_Weight_Test extends SendGoodsFather {

	@Test(dataProvider = "data")
	public void sendAllValidRequiredDataForWeightSuccessTest(ITestContext context, Map<String, String> data) {
		SendGoodsFather.sendGoodsParpare(context, seleniumUtil);
		SendGoodsPageHelper.typeGoodsInfo(seleniumUtil, SendGoodsPage.SGP_BUTTON_DEVICEGOODS, SendGoodsPage.SGP_BUTTON_GOODSDATE7, 
				data.get("SGP_INPUT_GOODSORIGINALCITY"), data.get("SGP_INPUT_GOODSRECEIPTCITY"), data.get("SGP_INPUT_GOODSNAME"), data.get("SGP_INPUT_GOODSDETAILS"),
				data.get("SGP_INPUT_WEIGHT"), data.get("SGP_INPUT_COUNT"), data.get("SGP_INPUT_CARLENGTH"), data.get("SGP_INPUT_CARTYPE"), 
				data.get("SGP_INPUT_INFOFARE"),data.get("SGP_INPUT_UNITPRICE"),data.get("SGP_SELECT_UNITNAME"),data.get("SGP_INPUT_ALLPRICE"), data.get("SGP_INPUT_DECLAREVALUE"), data.get("SGP_INPUT_INSTRUCTION"));
		SendGoodsPageHelper.enterPage(seleniumUtil, SendGoodsPage.SGP_BUTTON_SEND);
		SendGoodsSuccessPageHelper.waitSendGoodsSuccessPageToLoad(timeOut, seleniumUtil);
		SendGoodsSuccessPageHelper.checkSendGoodsPageText(seleniumUtil);
	
	}
	

}

package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import net.sf.json.JSONObject;
/**
 *
 * **/
public class AddSkuToCart {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	String orderNumber = "" ;
	//String S0489317067
	//@Test
	public void get_sku_detail() {
		String url = "http://localhost:8080/maxbox/web/machine_api/get_sku_detail.action" ;
		json.put("storeId", "12");
		json.put("skuNumber", "582");
		try {
			JSONObject get_sku_detail_result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("getSkuDetail result is:"+get_sku_detail_result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 机器端将商品添加到购物车
	 * **/
	@Test
	public void add_sku_to_cart() {
		String url = "http://test.mjitech.com/web/machine_api/add_sku_to_cart.action" ;
		//String url = "http://test.mjitech.com/web/machine_api/add_sku_to_cart.action" ;
		json.put("storeId","12");//门店号
		json.put("skuId", "582");//商品SKU
		json.put("count", "1") ;
		try {
			JSONObject add_sku_to_cart_result = service.httppostCartReturnJson(url,service.postParameter(json));
			System.out.println("add_sku_to_cart result is:"+add_sku_to_cart_result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//http://localhost:8080/maxbox/web/machine_api/submit_cart.action
	//{"is_succ":true,"storeId":12,"order":{"newTotalPrice":0,"leftPrice":0.2,"skus":[],"orderNumber":"S0857802930","originalPrice":20,"totalPrice":20,"payTime":"","cancelUserId":0,"source":2,"buyerId":0,"type":0,"wxpayUrl":"","takenUserId":0,"sellerId":0,"refundOrderNumber":"","statusName":"已下单","takeGoodsNumber":"","from":0,"id":14247,"sellTime":1528336962205,"originalSellOrderId":0,"isParent":2,"parentId":0,"initialOrderId":0,"wxprepayId":"","expireTime":1528337862205,"warehouseId":12,"cancelTime":"","payStatusName":"未付款","to":0,"payStatus":1,"refundUserId":0,"realPrice":0,"status":1}}
	@Test(dependsOnMethods = "add_sku_to_cart")
	public void submitCart() {
		String url = "http://test.mjitech.com/web/machine_api/submit_cart.action" ;
		json.put("storeId","12");
		try {
			JSONObject submitResult = service.httppostCartReturnJson(url, service.postParameter(json));
			JSONObject order = submitResult.getJSONObject("order");
			orderNumber  = order.getString("orderNumber");
			System.out.println("orderNumber:"+orderNumber);
			System.out.println("submitResult is "+submitResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取支付URL
	 * */
	@Test(dependsOnMethods = "submitCart")
	public void getPayUrl() {
		String url = "http://test.mjitech.com/web/machine_api/get_pay_url.action" ;
		json.put("storeId","12") ;
		json.put("orderNumber",orderNumber);
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("获取二维码连接："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付回调接JSONObject口。
	 * */
	@Test(dependsOnMethods = "getPayUrl")
	public void wxpay_callback_test() {
		String url="http://test.mjitech.com/web/weixinpay_callback_test.action" ;
		String body = "{\"return_code\":\"SUCCESSTEST\",\"openid\": \"oj4sH0qtPm0x0-ggPk0AQZGQR9xs\",\"out_trade_no\":\""+orderNumber+"\"}";
		try {
			JSONObject  result  = service.httppostPayCall(url, body);			
			System.out.println("支付结果："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	
	
	
    
	
	
	
	
	
}

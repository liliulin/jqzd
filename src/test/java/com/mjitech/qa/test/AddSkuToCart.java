package com.mjitech.qa.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.Test;

import com.mjitech.qa.service.BaseService;

import junit.framework.Assert;
import net.sf.json.JSONObject;
/**
 *订单支付相关-订单支付完成，可售库存-1；实际库存不变，货到数量不变。
 *@author gangwang@mjitech.com
 *@date 2018-06-20
 * **/
public class AddSkuToCart {
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");
	String orderNumber = "" ;
	String takingNumber = "" ;
	String  takeGoodsNumber = "" ;//取货码
	//String S0489317067
	//@Test
	public void get_sku_detail() {
	//	String url = "http://localhost:8080/maxbox/web/machine_api/get_sku_detail.action" ;
		String url = "http://localhost:8080/maxbox/web/machine_api/get_sku_detail.action" ;
		json.put("storeId", "15");
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
		json.put("storeId","15");//门店号
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
		json.put("storeId","15");
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
		json.put("storeId","15") ;
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

	//接口地址: http://test.mjitech.com/web/machine_api/get_order_detail.action
	/**
	 * 获取订单详情接口
	 * */
	@Test(dependsOnMethods = "wxpay_callback_test")
	public void get_order_detail() {
		String url = "http://test.mjitech.com/web/machine_api/get_order_detail.action" ;
		json.put("storeId","15") ;
		json.put("orderNumber",orderNumber);
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			String is_succ = result.getString("is_succ");
			Assert.assertEquals("true", is_succ);
			JSONObject order = result.getJSONObject("order");
			if(is_succ.equals("true")) {
				if(order!=null) {
					takeGoodsNumber = order.getString("takeGoodsNumber");
				}		
			}		
			System.out.println("获取订单详情："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	/**
	 * 扫码取货
	 * 
	 * */
	@Test(dependsOnMethods="get_order_detail")
	public void get_order_detail_by_takingnumber() {
		String url = "http://test.mjitech.com/web/machine_api/get_order_detail_by_takingnumber.action" ;
	//	String body = "{\"storeId\":\"15\",\"takingNumber\":\""+takeGoodsNumber+"\"}";
		json.put("storeId", "15");
		json.put("takingNumber", takeGoodsNumber) ;
		try {
		      JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
		      System.out.println("扫码取货结果："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新订单接口
	 * */
	//http://test.mjitech.com/web/machine_api/update_order_status.action
	@Test(dependsOnMethods="get_order_detail_by_takingnumber")
	public void update_order_status() {
		String url = "http://test.mjitech.com/web/machine_api/update_order_status.action";
		json.put("storeId", "15");
		json.put("orderNumber", orderNumber) ;
		//1新2已支付3未取4正在取5已取6取货失败7退款申请中8已退款9退款失败10部分退款，退款申请状态查阅RefundOrder状态21机器故障但未退款91已取消
		json.put("status", "4");
	  
		try {
		  JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
		  System.out.println("更新订单："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//http://test.mjitech.com/web/machine_api/set_outbatchsku_status.action
	/**
	 * 更新每个订单的状态
	 * */
	@Test(dependsOnMethods="update_order_status")
	public void set_outbatchsku_status() {
		String url  = "http://test.mjitech.com/web/machine_api/set_outbatchsku_status.action" ;
		json.put("storeId","15") ;
		json.put("outBatchSkuId","");
		json.put("status", "2");
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("批量更新接口："+result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//@Test(dependsOnMethods="")
	public void upload_error() {
		String url = "http://test.mjitech.com/web/machine_api/upload_error.action" ;
		json.put("shouldRefund","true");
		json.put("outBatchId", "");
		json.put("storeId","15") ;
		json.put("errorType","13");
		json.put("errorInfo", "");		
	}
	public void end() {
		
	}
	
	
	
	
	
	
    
	
	
	
	
	
}

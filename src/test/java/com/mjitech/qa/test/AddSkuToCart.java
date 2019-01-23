package com.mjitech.qa.test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.mjitech.qa.service.BaseService;
import com.mjitech.qa.util.DBConnection;
import com.alibaba.fastjson.JSONArray;
import junit.framework.Assert;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONArray;


/**
 * 订单支付流程（已取货）订单支付完成，可售库存-1；实际库存不变，货道中商品数量-1；
 * 
 * @author gangwang@mjitech.com
 * @date 2018-06-20
 **/
public class AddSkuToCart {
	private static Logger logger = Logger.getLogger(AddSkuToCart.class);
	BaseService service = new BaseService();
	JSONObject json = JSONObject.fromObject("{}");

	String orderNumber = ""; // 订单号
	String takingNumber = "";
	String takeGoodsNumber = "";// 取货码
	int outBatchSkuId = 0;// 批次商品id
	int outBatchId=0;//出货批次
	String sku="";//
	// 可售
	int quantity = 0;
	// 货道中商品数量
	int mt_new_sku_pass_real_quantity = 0;
	// 实际库存值
	int real_quantity = 0;

	@BeforeTest
	public void beforerMethod() {
		String shwoQuantity = "select quantity from mt_inventory where warehouse_id=17 and sku_id=679 ORDER BY id desc";
		String show_mt_new_sku_pass_real_quantity = "select real_quantity from mt_inventory where warehouse_id=17 and sku_id=679 ORDER BY id desc";
		String show_real_quantity = "select real_quantity from mt_new_sku_pass where warehouse_id=17 and sku_id=679 ORDER BY id desc";
		DBConnection db = new DBConnection("test");
		ResultSet rs = null;
		try {
			Statement stmt = db.conn.createStatement();
			String sql = "";
			for (int i = 1; i <= 3; i++) {
				if (i == 1) {
					sql = shwoQuantity;
					rs = (ResultSet) stmt.executeQuery(sql);
					while (rs.next()) {
						quantity = rs.getInt("quantity");
						System.out.println("quantity=" + quantity);
					}
				} else if (i == 2) {
					sql = show_mt_new_sku_pass_real_quantity;
					rs = (ResultSet) stmt.executeQuery(sql);
					while (rs.next()) {
						mt_new_sku_pass_real_quantity = rs.getInt("real_quantity");
						System.out.println("mt_new_sku_pass_real_quantity" + mt_new_sku_pass_real_quantity);
					}
				} else {
					sql = show_real_quantity;
					rs = (ResultSet) stmt.executeQuery(sql);
					while (rs.next()) {
						real_quantity = rs.getInt("real_quantity");
					}
					System.out.println("real_quantity=" + real_quantity);
					db.close();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.close();
		}

		db.close();
	}

//	/**
//	 * 获得当前购物车接口
//	 * 
//	 * @author gangwang
//	 * 
//	 */
//	@Test
//	public void get_cart() {
//		// String url = "http://test.mjitech.com/web/machine_api/get_cart.action" ;
//		String url = "http://test.mjitech.com/web/machine_api/get_cart.action";
//		json.put("storeId", 17);
//		try {
//			JSONObject getCarResult = service.httppostCartReturnJson(url, service.postParameter(json));
//			String is_succ = getCarResult.getString("is_succ");
//			Assert.assertEquals(is_succ, "true");
//			logger.info("getCarResult is" + getCarResult);
//			System.out.println("getCarResult is:" + getCarResult);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//	//String S0489317067
//	//@Test
//	public void get_sku_detail() {
//	//	String url = "http://localhost:8080/maxbox/web/machine_api/get_sku_detail.action" ;
//		String url = "http://localhost:8080/maxbox/web/machine_api/get_sku_detail.action" ;
//		json.put("storeId", "17");
//		json.put("skuNumber", "582");
//		try {
//			JSONObject get_sku_detail_result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("getSkuDetail result is:"+get_sku_detail_result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 02)机器端将商品添加到购物车
//	 **/
//	@Test
//	public void add_sku_to_cart() {
//		String url = "http://test.mjitech.com/web/machine_api/add_sku_to_cart.action";
//		// String url = "http://test.mjitech.com/web/machine_api/add_sku_to_cart.action"
//		// ;
//		json.put("storeId", "17");// 门店号
//		json.put("skuId", "679");// 商品SKU
//		json.put("count", "1");
//		try {
//			JSONObject add_sku_to_cart_result = service.httppostCartReturnJson(url, service.postParameter(json));
//			// {"currentCount":1,"is_succ":true}
//			String is_succ = add_sku_to_cart_result.getString("is_succ");
//			Assert.assertEquals(is_succ, "true");
//			System.out.println("add_sku_to_cart result is:" + add_sku_to_cart_result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	// http://localhost:8080/maxbox/web/machine_api/submit_cart.action
//	// {"is_succ":true,"storeId":12,"order":{"newTotalPrice":0,"leftPrice":0.2,"skus":[],"orderNumber":"S0857802930","originalPrice":20,"totalPrice":20,"payTime":"","cancelUserId":0,"source":2,"buyerId":0,"type":0,"wxpayUrl":"","takenUserId":0,"sellerId":0,"refundOrderNumber":"","statusName":"已下单","takeGoodsNumber":"","from":0,"id":14247,"sellTime":1528336962205,"originalSellOrderId":0,"isParent":2,"parentId":0,"initialOrderId":0,"wxprepayId":"","expireTime":1528337862205,"warehouseId":12,"cancelTime":"","payStatusName":"未付款","to":0,"payStatus":1,"refundUserId":0,"realPrice":0,"status":1}}
//	@Test(dependsOnMethods = "add_sku_to_cart")
//	public void submitCart() {
//		String url = "http://test.mjitech.com/web/machine_api/submit_cart.action";
//		json.put("storeId", "17");
//		try {
//			JSONObject submitResult = service.httppostCartReturnJson(url, service.postParameter(json));
//			JSONObject order = submitResult.getJSONObject("order");
//			orderNumber = order.getString("orderNumber");
//			System.out.println("orderNumber:" + orderNumber);
//			System.out.println("submitResult is " + submitResult);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取支付URL
//	 */
//	@Test(dependsOnMethods = "submitCart")
//	public void getPayUrl() {
//		String url = "http://test.mjitech.com/web/machine_api/get_pay_url.action";
//		json.put("storeId", "17");
//		json.put("orderNumber", orderNumber);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("获取二维码连接：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 支付回调接JSONObject口。
//	 */
//	@Test(dependsOnMethods = "getPayUrl")
//	public void wxpay_callback_test() {
//		String url = "http://test.mjitech.com/web/weixinpay_callback_test.action";
//		String body = "{\"return_code\":\"SUCCESSTEST\",\"openid\": \"oj4sH0qtPm0x0-ggPk0AQZGQR9xs\",\"out_trade_no\":\""
//				+ orderNumber + "\"}";
//		try {
//			JSONObject result = service.httppostPayCall(url, body);
//			System.out.println("支付结果：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	// 接口地址: http://test.mjitech.com/web/machine_api/get_order_detail.action
//	/**
//	 * 获取订单详情接口
//	 */
//	@Test(dependsOnMethods = "wxpay_callback_test")
//	public void get_order_detail() {
//		String url = "http://test.mjitech.com/web/machine_api/get_order_detail.action";
//		json.put("storeId", "17");
//		json.put("orderNumber", orderNumber);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			String is_succ = result.getString("is_succ");
//			Assert.assertEquals("true", is_succ);
//			JSONObject order = result.getJSONObject("order");
//			if (is_succ.equals("true")) {
//				if (order != null) {
//					takeGoodsNumber = order.getString("takeGoodsNumber");
//				}
//			}
//			System.out.println("获取订单详情：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	
//	/**
//	 * 扫码取货
//	 * 
//	 */
//	@Test(dependsOnMethods = "get_order_detail")
//	public void get_order_detail_by_takingnumber() {
//		String url = "http://test.mjitech.com/web/machine_api/get_order_detail_by_takingnumber.action";
//		// String body =
//		// "{\"storeId\":\"15\",\"takingNumber\":\""+takeGoodsNumber+"\"}";
//		json.put("storeId", "17");
//		json.put("takingNumber", takeGoodsNumber);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			String is_succ= result.getString("is_succ");
//			Assert.assertEquals("true", is_succ);
//			JSONObject order = result.getJSONObject("order");
//			String  batches =order.getString("batches"); //获取字符串数组
//			List<BatchsBean> batcheList = JSONArray.parseArray(batches, BatchsBean.class);
//			if (is_succ.equals("true")) {
//				if (batches != null) {
//					outBatchSkuId = batcheList.get(0).getSkus().get(0).getOutBatchSkuId();//通过数组下标获取元素
//					outBatchId=batcheList.get(0).getOutBatchId();//出货批次
//				}
//			}
//			System.out.println("扫码取货结果：" + result);
////			System.out.println("获取批次商品id11：" + order);
////			System.out.println("获取批次商品id1122：" + batches);
//		    System.out.println("获取批次商品id1122：" + sku);
//			System.out.println("批次商品id" +outBatchSkuId);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 更新outbatchsku（批次商品id）状态		 
// * * 
//	 */
//
//	@Test(dependsOnMethods = "get_order_detail_by_takingnumber")
//	public void set_outbatchsku_status() {
//		String url = "http://test.mjitech.com/web/machine_api/set_outbatchsku_status.action";
//		json.put("storeId", "17");
//		json.put("outBatchId", outBatchId);
//		json.put("outBatchSkuId", outBatchSkuId);
//		json.put("orderNumber", orderNumber);
//		// 1未出 2正在出- 3已出- 4已出货- 5出货失set_outbatchsku_status败退款中 6出货失败退款成功- 7出货失败退款失败 8出货失败换货-
//		json.put("status", "2");
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("更新outbatchsku状态：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	
//	
//	/**
//	 * 取货失败
//	 * 
//	*/
//
//	@Test(dependsOnMethods = "set_outbatchsku_status")
//	public void fail_get_sku() {
//		String url = "http://test.mjitech.com/web/machine_api/fail_get_sku.action";
//		json.put("storeId", "17");
//		json.put("outBatchSkuId", outBatchSkuId);
//
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("取货失败:" + result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//
//	/**
//	 * 获取换货信息
//	 * 
//	 */
//	@Test(dependsOnMethods = "fail_get_sku")
//	public void get_exchanged_sku() {
//		String url = "http://test.mjitech.com/web/machine_api/get_exchanged_sku.action";
//		json.put("storeId", "17");
//		json.put("outBatchSkuId", outBatchSkuId);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("换货信息:" + result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	
//	
//	/**
//	 * 选择换货
//	 * 
//	 */
//	@Test(dependsOnMethods = "get_exchanged_sku")
//		public void exchange_sku() {
//			String url = "http://test.mjitech.com/web/machine_api/exchange_sku.action";
//			json.put("storeId", "17");
//			json.put("outBatchSkuId", outBatchSkuId);
//			json.put("skuId","1408");
//			try {
//				JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//				System.out.println("换货:" + result);
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	
//	
//	/**
//	 * 换货完成更新订单状态
//	 * 
//	 */
//	/**
//	 * 更新订单接口 备注：该接口设置的就是4，和5 1, "初始订单" 3, "未取货" 4, "正在取货" 5, "已取货"6, "取货失败"8,
//	 * "已退款(全部退款)"9, "退款失败"10, "部分退款" 91, "已取消"
//	 */
//
//	@Test(dependsOnMethods = "exchange_sku")
//	public void update_order_status() {
//		String url = "http://test.mjitech.com/web/machine_api/update_order_status.action";
//		json.put("storeId", "17");
//		json.put("orderNumber", orderNumber);
//		// 1新2已支付3未取4正在取5已取6取货失败7退款申请中8已退款9退款失败10部分退款，退款申请状态查阅RefundOrder状态21机器故障但未退款91已取消
//		json.put("status", "5");
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("更新订单：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	
	
	
	
	
	
	
	
	
	
	//退款
	
	/**
	 * 扫码取货
	 * 
	 */
////	@Test(dependsOnMethods = "get_order_detail")
//	@Test
//	public void get_order_detail_by_takingnumber() {
//		String url = "http://test.mjitech.com/web/machine_api/get_order_detail_by_takingnumber.action";
//		// String body =
//		// "{\"storeId\":\"15\",\"takingNumber\":\""+takeGoodsNumber+"\"}";
//		json.put("storeId", "17");
////		json.put("takingNumber", takeGoodsNumber);
//		json.put("takingNumber", "3398493949");//取货码
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			String is_succ= result.getString("is_succ");
//			Assert.assertEquals("true", is_succ);
//			JSONObject order = result.getJSONObject("order");
//			String  batches =order.getString("batches"); //获取字符串数组
//			List<BatchsBean> batcheList = JSONArray.parseArray(batches, BatchsBean.class);
//			if (is_succ.equals("true")) {
//				if (batches != null) {
//					outBatchSkuId = batcheList.get(0).getSkus().get(0).getOutBatchSkuId();//通过数组下标获取元素
//					outBatchId=batcheList.get(0).getOutBatchId();//出货批次
//				}
//			}
//			System.out.println("扫码取货结果：" + result);
////			System.out.println("获取批次商品id11：" + order);
////			System.out.println("获取批次商品id1122：" + batches);
//		    System.out.println("获取批次商品id1122：" + sku);
//			System.out.println("批次商品id" +outBatchSkuId);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 更新outbatchsku（批次商品id）状态		 
// * * 
//	 */
//
//	@Test(dependsOnMethods = "get_order_detail_by_takingnumber")
//	public void set_outbatchsku_status() {
//		String url = "http://test.mjitech.com/web/machine_api/set_outbatchsku_status.action";
//		json.put("storeId", "17");
//		json.put("outBatchId", outBatchId);
//		json.put("outBatchSkuId", outBatchSkuId);
//		json.put("orderNumber", orderNumber);
//		// 1未出 2正在出- 3已出- 4已出货- 5出货失set_outbatchsku_status败退款中 6出货失败退款成功- 7出货失败退款失败 8出货失败换货-
//		json.put("status", "2");
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("更新outbatchsku状态：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	
//	/**
//	 * 取货失败
//	 * 
//	*/
//
//	@Test(dependsOnMethods = "set_outbatchsku_status")
//	public void fail_get_sku() {
//		String url = "http://test.mjitech.com/web/machine_api/fail_get_sku.action";
//		json.put("storeId", "17");
//		json.put("outBatchSkuId", outBatchSkuId);
//
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("取货失败:" + result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
// 
//
//
//	/**
//	 * 获取换货信息
//	 * 
//	 */
//	@Test(dependsOnMethods = "fail_get_sku")
//	public void get_exchanged_sku() {
//		String url = "http://test.mjitech.com/web/machine_api/get_exchanged_sku.action";
//		json.put("storeId", "17");
//		json.put("outBatchSkuId", outBatchSkuId);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("换货信息:" + result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	
//	/**
//	 * 选择退款
//	* 
//	 */
//	@Test(dependsOnMethods = "get_exchanged_sku")
//	public void refund_failed_get_sku() {
//		String url = "http://test.mjitech.com/web/machine_api/refund_failed_get_sku.action";
//		json.put("storeId", "17");
//		json.put("outBatchSkuId", outBatchSkuId);
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("选择退款:" + result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	 
//
//	/**
//	 * 更新订单状态
//	* 
//	 */
//	@Test(dependsOnMethods = "refund_failed_get_sku")
//	public void update_order_status() {
//		String url = "http://test.mjitech.com/web/machine_api/update_order_status.action";
//		json.put("storeId", "17");
////		json.put("orderNumber", orderNumber);
//		json.put("orderNumber", "S2981029890");//订单号
//		// 1新2已支付3未取4正在取5已取6取货失败7退款申请中8已退款9退款失败10部分退款，退款申请状态查阅RefundOrder状态21机器故障但未退款91已取消
//		json.put("status", "8");
//		try {
//			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
//			System.out.println("更新订单：" + result);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	
	

	//机器故障
	
	/**
	 * 扫码取货
	 * 
	 */
//	@Test(dependsOnMethods = "get_order_detail")
	@Test
	public void get_order_detail_by_takingnumber() {
		String url = "http://test.mjitech.com/web/machine_api/get_order_detail_by_takingnumber.action";
		// String body =
		// "{\"storeId\":\"15\",\"takingNumber\":\""+takeGoodsNumber+"\"}";
		json.put("storeId", "17");
		json.put("takingNumber", "2912231521");//取货码
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			String is_succ= result.getString("is_succ");
			Assert.assertEquals("true", is_succ);
			JSONObject order = result.getJSONObject("order");
			String  batches =order.getString("batches"); //获取字符串数组
			List<BatchsBean> batcheList = JSONArray.parseArray(batches, BatchsBean.class);
			if (is_succ.equals("true")) {
				if (batches != null) {
					outBatchSkuId = batcheList.get(0).getSkus().get(0).getOutBatchSkuId();//通过数组下标获取元素
					outBatchId=batcheList.get(0).getOutBatchId();//出货批次
				}
			}
			System.out.println("扫码取货结果：" + result);
//			System.out.println("获取批次商品id11：" + order);
//			System.out.println("获取批次商品id1122：" + batches);
		    System.out.println("获取批次商品id1122：" + sku);
			System.out.println("批次商品id" +outBatchSkuId);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新outbatchsku（批次商品id）状态		 
 * * 
	 */

	@Test(dependsOnMethods = "get_order_detail_by_takingnumber")
	public void set_outbatchsku_status() {
		String url = "http://test.mjitech.com/web/machine_api/set_outbatchsku_status.action";
		json.put("storeId", "17");
		json.put("outBatchId", outBatchId);
		json.put("outBatchSkuId", outBatchSkuId);
		json.put("orderNumber", orderNumber);
		// 1未出 2正在出- 3已出- 4已出货- 5出货失set_outbatchsku_status败退款中 6出货失败退款成功- 7出货失败退款失败 8出货失败换货-
		json.put("status", "2");
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("更新outbatchsku状态：" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * 机器故障
	 * 
	*/

	@Test(dependsOnMethods = "set_outbatchsku_status")
	public void upload_error() {
		String url = "http://test.mjitech.com/web/machine_api/upload_error.action";
		json.put("storeId", "17");
		json.put("outBatchId", "22750");
		json.put("shouldRefund", "true");
		json.put("errorType", "0");
		json.put("errorInfo", "test");
		

		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("机器故障:" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 



	/**
	 * 更新订单状态
	* 
	 */
	@Test(dependsOnMethods = "upload_error")
	public void update_order_status() {
		String url = "http://test.mjitech.com/web/machine_api/update_order_status.action";
		json.put("storeId", "17");
		json.put("orderNumber", "S8199207684");//订单号
		// 1新2已支付3未取4正在取5已取6取货失败7退款申请中8已退款9退款失败10部分退款，退款申请状态查阅RefundOrder状态21机器故障但未退款91已取消
		json.put("status", "8");
		try {
			JSONObject result = service.httppostCartReturnJson(url, service.postParameter(json));
			System.out.println("更新订单：" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	
	
	




	
}

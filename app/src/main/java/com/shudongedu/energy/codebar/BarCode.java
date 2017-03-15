/*
 * 文件名: BarCode.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: 二维码扫描实体类
 * 创建人: jp
 * 创建时间:12 Nov, 2014
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.codebar;

import android.net.Uri;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 二维码扫描实体类<BR>
 *
 * @author jp
 * @version [Paitao Client V20130911, 12 Nov, 2014]
 */
public class BarCode {
  /**
   * 优惠券字段,新域名表示法
   */
  public static final String NODE_COUPON_NEW = "p";

  /**
   * 充值字段,新域名表示法
   */
  public static final String NODE_CHARGE_NEW = "c";

  /**
   * 优惠券字段
   */
  public static final String NODE_COUPON = "coupon";

  /**
   * 充值字段
   */
  public static final String NODE_CHARGE = "charge";

  /**
   * 95特权
   */
  public static final String NODE_MEMBERCARD = "membercard";

  /**
   * 码格式有误
   */
  public static final byte NO_MATCHER = 0x00;

  /**
   * 充值
   */
  public static final byte CHARGE = 0x01;

  /**
   * 优惠券
   */
  public static final byte COUPON = 0x02;

  /**
   * 95特权
   */
  public static final byte MEMBERCARD = 0x03;

  /**
   * HTTP
   */
  public static final byte HTTP = 0x04;

  /**
   * 是否需要登录
   */
  private boolean requireLogin;

  /**
   * 种类
   */
  private int type;

  /**
   * 扫描url
   */
  private String url;

  /**
   * 扫描Code
   */
  private String code;

  /**
   * 构造函数
   */
  public BarCode() {
    this.type = NO_MATCHER;
    this.requireLogin = true;
  }

  /**
   * 解析扫描码<BR>
   *
   * @return barcode 解析返回
   */
  public static BarCode parse(String res) {
    BarCode barCode = new BarCode();
    barCode.setCode(res);
    if (isMatchCode(res)) {
      barCode = parseValidateCode(res);
    } else if (isMatchHttp(res)) {
      barCode = parseHttp(res);
    }
    return barCode;
  }

  /**
   * 解析格式正确的码<BR>
   */
  private static BarCode parseValidateCode(String res) {
    BarCode barCode = new BarCode();
    barCode.setUrl(res);
    Uri uri = Uri.parse(res);
    List<String> segments = uri.getPathSegments();
    int index = segments.size();
    if (index >= 2) {
      //倒数第二位表示类型
      String type = segments.get(index - 2);
      //url最后一位表示code
      String code = segments.get(index - 1);
      if (NODE_COUPON.equals(type)) {
        barCode.setType(COUPON);
      } else if (NODE_CHARGE.equals(type)) {
        barCode.setType(CHARGE);
      } else if (NODE_MEMBERCARD.equals(type)) {
        barCode.setType(MEMBERCARD);
      } else if (NODE_COUPON_NEW.equals(type)) {
        barCode.setType(COUPON);
      } else if (NODE_CHARGE_NEW.equals(type)) {
        barCode.setType(CHARGE);
      } else {
        barCode.setType(NO_MATCHER);
      }
      barCode.setCode(code);
    }
    return barCode;
  }

  /**
   * 解析Http请求码<BR>
   */
  private static BarCode parseHttp(String res) {
    BarCode barCode = new BarCode();
    barCode.setType(HTTP);
    barCode.setUrl(res);
    barCode.setCode(res);
    barCode.setRequireLogin(false);
    return barCode;
  }

  /**
   * 检测扫码URL是否符合要求<BR>
   * http://qrcode.xiaomei.com/coupon/#####或com.paitao.xmlife://qrcode/coupon/#####
   * 由于老的太长，启用新的域名http://xmkg.me/c/##### http://xmkg.me/p/#####
   * c代表 charge p代表coupon
   * 为了兼容性，需要保留对老的兼容。
   */
  private static boolean isMatchCode(String url) {
    String[] reg = {
        "com.paitao.xmlife://qrcode/[^\\s]+", "http(s)?://qrcode.xiaomei.com/[^\\s]+",
        "http(s)?://xmkg.me/[^\\s]+"
    };
    for (int i = 0; i < reg.length; i++) {
      Pattern pattern = Pattern.compile(reg[i]);
      Matcher matcher = pattern.matcher(url);
      if (matcher.matches()) {
        return true;
      }
    }
    return false;
  }

  /**
   * 是否HTTP请求<BR>
   */
  private static boolean isMatchHttp(String url) {
    String reg = "http(s)?://[^\\s]+";
    Pattern pattern = Pattern.compile(reg);
    Matcher matcher = pattern.matcher(url);
    return matcher.matches();
  }

  public boolean isRequireLogin() {
    return requireLogin;
  }

  public void setRequireLogin(boolean requireLogin) {
    this.requireLogin = requireLogin;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}

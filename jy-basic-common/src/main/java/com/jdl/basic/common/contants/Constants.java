package com.jdl.basic.common.contants;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String SEPARATOR_COMMA = ",";
    public static final String EMPTY_FILL = "";
    public static final String SEPARATOR_HYPHEN = "-";

    public static final Integer YN_YES = 1; // 使用
    public static final Integer YN_NO = 0; // 已删除

    /**
     * 快运中心站点类型
     */
    public static final int B2B_SITE_TYPE = 6420;

    /**
     * 常量值：0
     * */
    public static final int  CONSTANT_NUMBER_ZERO = 0;

    /**
     * 常量值：1
     * */
    public static final int  CONSTANT_NUMBER_ONE = 1;
    /**
     * 常量值：2
     * */
    public static final int  CONSTANT_NUMBER_TWO = 2;

    /**
     * 常量值：10
     * */
    public static final int  CONSTANT_NUMBER_TEN = 10;

    /**
     * 特殊符号：*
     */
    public static final String  CONSTANT_SPECIAL_MARK_ASTERISK = "*";

    /**
     * 操作标识-1
     */
    public static final Integer FLAG_OPRATE_ON = 1;

    /**
     * 数字：0
     */
    public static final Integer NUMBER_ZERO = 0;

    /**
     * SendD取消状态
     */
    public static final int OPERATE_TYPE_CANCEL_L = 0;
    public static final int OPERATE_TYPE_CANCEL_Y = 1;

  /**
     * 线路类型-默认值0
     */
    public static Integer LINE_TYPE_DEFAULT = 0;

    /**
     * 换行符
     */
    public static String LINE_NEXT_CHAR = "\n";
    /**
     * 默认时区-GMT+8
     */
    public final static String TIME_ZONE8 = "GMT+8";
    /**
     * 默认国际化-zh_CN
     */
    public final static String LOCALE_ZH_CN = "zh_CN";
    /**
     * 传摆线路类型列表
     */
    public static List<Integer> CUAN_BAI_LINE_TYPES = new ArrayList<Integer>();
    static{
    	//11-市内传站
    	CUAN_BAI_LINE_TYPES.add(11);
    	//5-摆渡
    	CUAN_BAI_LINE_TYPES.add(5);
    	//9-市内传站返回
    	CUAN_BAI_LINE_TYPES.add(9);
    	//10-长途传站
    	CUAN_BAI_LINE_TYPES.add(10);
    	//34-长途传站返回
    	CUAN_BAI_LINE_TYPES.add(34);
    }

    /**
     * 车牌长度9位 ex:010A00001
     */
    public static final int VEHICLE_NUMBER_LENGTH_9 = 9;

    /**
     * 车牌长度10位 ex:0371A00001
     */
    public static final int VEHICLE_NUMBER_LENGTH_10 = 10;

    /**
     * 查询已扫包裹最大数限制单次IN50
     */
    public static final int QUERY_LOAD_SCAN_MAX = 50;
    //DeliveryPackageD信任包裹称重
    public static Integer isTrust = 1;

    /**
     * 卸车任务流水线模式:只验货不组板
     */
    public static final Integer ASSEMBLY_LINE_TYPE = 0;

    /**
     * 系统名
     */
    public static final String SYSTEM_NAME = "QLFJZXJT";

    /**
     * 导出并发限制数量
     */
    public static Integer CONCURRENCY_EXPORT_LIMIT = 50;

    /**
     * 导出并发key 缓存有效时间 单位:天
     */
    public static Integer EXPORT_REDIS_KEY_TIME_OUT = 1;

    /**
     * B网抽检图片前缀标识：B
     */
    public static final String SPOT_CHECK_B = "B";

    /**
     * 开启状态 1
     */
    public static final String SWITCH_OPEN = "1";

    /**
     * 关闭状态 0
     */
    public static final String SWITCH_OFF = "0";

    /**
     * 默认泡重比：8000
     * */
    public static final Integer DEFAULT_VOLUME_RATE = 8000;

    /**
     * 快运使用的泡重比:6000
     */
    public static final Integer EXPRESS_VOLUME_RATE= 6000;

    /**
     * B网特快重货重泡比
     * */
    public static final int VOLUME_RATIO_TKZH = 6000;
    /**
     * B网非特快重货重泡比
     * */
    public static final int VOLUME_RATIO_NOT_TKZH = 4800;

    /**
     * 驻厂操作全程跟踪
     */
    public static final String TRACE_PACK_RECEIVE = "订单/包裹已接货";
    public static final String TRACE_DELIVERY_COLLECT = "配送员%s揽收完成";

    /***
     * 跨越成功校验缓存前缀
     */
    public static final String KYEXPRESSLOADSUCCESS="kyloadcarsuccess:";
    /***跨越成功发送妥投消息前缀**/
    public static final String KYSENDMSGSUCCESSPREFIX = "kysendmsgsuccessprefix:";

    /**
     * 库表切换警告信息
     */
    public static final String UNLOAD_TRANSFER_WARN_MESSAGE = "抱歉，数据库正在升级中，请稍后再试";

    /**
     * 产品类型-冷链小票
     */
    public static final String PRODUCT_TYPE_COLD_CHAIN_XP = "ll-m-0020";

    /**
     * 产品类型-医药大票
     */
    public static final String PRODUCT_TYPE_MEDICINE_DP = "ll-m-0018";

    public static final String TOTAL_URL_INTERCEPTOR = "*";
    /**
     * 用户有效标识
     */
    public static final Integer FLAG_USER_Is_Resign = 1;
    /**
     * 医药零担产品类型
     */
    public static final String PRODUCT_TYPE_MEDICAL_PART_BILL = "LL-YYLD-M";
    /**
     * 医药冷链产品
     */
    public static final String PRODUCT_TYPE_MEDICAL_COLD_BILL = "ll-m-0002";

    /**
     * 运单预售未付尾款
     */
    public static final String PRODUCT_ABILITY_OF_PRE_SELL_NO_PAY  = "VI007-01";

    /**
     * 运单预售已付尾款
     */
    public static final String PRODUCT_ABILITY_OF_PRE_SELL_PAY_DONE  = "VI007-02";

    /**
     * 产品类型-医冷零担
     */
    public static final String PRODUCT_TYPE_MEDICINE_COLD = "md-m-0002";

    /**
     * 验货菜单编码：0101019
     */
    public static final String MENU_CODE_INSPECTION  = "0101019";

    /**
     * UMP appName
     */
    public static final String UMP_APP_NAME = "jy-basic-services";

    public static final String USER_LOCK_PREFIX  = "jy_user_lock_%s";

    public static final int  LOCK_EXPIRE  = 10;

    public static final int DEFAULT_PAGE_NO =1;
    public static final int DEFAULT_PAGE_SIZE_QUERY_USER =256;

    public static final String COLUMN_PAGE_SIZE="pageSize";
    public static final String COLUMN_PAGE_NO="pageNo";
    public static final String COLUMN_SITE_CODE="siteCode";

    /**
     * 用户打卡数据筛选天数
     */
    public static final Integer USER_SIGN_CHECK_DAYS= -7;

    /**
     * String类型标识-1-true
     */
    public static final String STRING_FLG_TRUE = "1";

    /**
     * Integer类型标识-true
     */
    public static final Integer INTEGER_FLG_TRUE = 1;
    public static final String LEFT_PARENTHESIS = "(";

    public static final String RIGHT_PARENTHESIS = ")";

    /**
     * 中文顿号
     */
    public static final String CHINESE_COMMA = "、";

    /**
     * 场地人员数量限制
     */
    public static final int SITE_USER_COUNT_LIMIT =2000;
    
    public static final String QUERY_BY_WORKGRID_KEY_CACHE_KEY="WorkGridJsfServiceImpl.queryByWorkGridKeyWithCache_%s";

}

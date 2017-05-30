package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Api {
    public static final String url = "http://dawnki.cn:8100/api/v1/";//统一域名
    public static final String http = "http://";//图片统一地址协议
    //登录模块
    public static final String LOGIN = url + "login";//登录
    public static final String REGISTER = url + "register";//注册
    public static final String LOGOUT = url + "logout";//登出
    public static final String UPDATETOKEN = url + "updatetoken";//更换令牌
    public static final String SETPASSWORD = url + "setPwd";//修改密码

    //管理员模块
    public static final String ADMINS_GETINFO = url + "admins/getInfo";//获取管理员信息
    public static final String ADMINS_GETUSERLIST = url + "admins/getUserList";//获取用户列表
    public static final String ADMINS_GETUSERDETAIL = url + "admins/getUserDetail";//获取用户详细信息
    public static final String ADMINS_MANAGEAGENT_GETLIST = url + "admins/manageAgent/getList";//获取代理商列表
    public static final String ADMINS_AGENT_GETSTORE = url + "admins/manageAgent/getStoreByAgent";//获取代理商的商店
    public static final String ADMINS_SETSTATUS = url + "admins/manageAgent/setStatus";//设置审核
    public static final String ADMINS_GETCLASS = url + "admins/class/get";//获取茶叶分类
    public static final String ADMINS_CREATECLASS = url + "admins/class/create";//创建分类
    public static final String ADMINS_DELETECLASS = url + "admins/class/delete";//删除分类
    public static final String ADMINS_MODIFYCLASS = url + "admins/class/modify";//修改分类
    public static final String ADMINS_GETTEABYCLASS = url + "admins/class/getGoodsByClass";//分类下获取茶叶
    public static final String ADMINS_CREATEGOODS = url + "admins/goods/create";//新建茶叶
    public static final String ADMINS_DELETEGOODS = url + "admins/goods/delete";//删除茶叶
    public static final String ADMINS_MODIFYGOODS = url + "admins/goods/modify";//编辑茶叶
    public static final String ADMINS_SETTEACLASS = url + "admins/class/setClass";//修改茶叶分类
    public static final String ADMINS_ONSELL = url + "admins/manageAgent/onSell";//商铺上架
    public static final String ADMINS_GETORDERLIST = url + "admins/getOrder";//获取订单列表
    public static final String ADMINS_GETORDERDETAIL = url + "admins/getOrderDetail";//获取订单详情
    //用户模块
    public static final String USER_GETINFO = url + "getInfo";//获取账号信息
    public static final String USER_MODIFYINFO = url + "modifyInfo";//修改账号信息
    public static final String USER_GETADDRESS = url + "address/get";//获取地址
    public static final String USER_DELETEADDRESS = url + "address/delete";//删除地址
    public static final String USER_CREATEADDRESS = url + "address/create";//新建地址
    public static final String USER_MODIFYADDRESS = url + "address/modify";//修改地址
    public static final String USER_UPGRADE = url + "upgrade";//申请认证代理商
    public static final String USER_GETUPGRADE = url + "getUpgrade";//查看申请
    public static final String USER_GETSTORELIST = url + "shop/getStoreList";//查看商铺列表
    public static final String USER_GETGOODLISTBYSRORE = url + "shop/getGoodsListByStore";//获取商铺下的商品
    public static final String USER_GETCOUPONBYGOODS = url + "shop/getCouponByGoods";//获取商品下的优惠
    public static final String USER_CREATEORDER = url + "shop/createOrder";//创建订单
    public static final String USER_GETORDERLIST = url + "shop/getOrder";//获取订单列表
    public static final String USER_GETORDERDETAIL = url + "shop/getOrderDetail";//获取订单详情

    //代理商模块
    public static final String AGENT_GETSTORE = url + "agents/store/get";//获取商铺
    public static final String AGENT_GETGOODLIST = url + "agents/store/getGoodsList";//获取代理商品
    public static final String AGENT_CREATESTORE = url + "agents/store/create";//创建商铺
    public static final String AGENT_MOFITYSTORE = url + "agents/store/modify";//修改商铺信息
    public static final String AGENT_COUPONCREATE = url + "agents/coupon/create";//创建优惠券
    public static final String AGENT_COUPONGET = url + "agents/coupon/get";//获取优惠券
    public static final String AGENT_COUPONDELETE = url + "agents/coupon/delete";//删除优惠券


}

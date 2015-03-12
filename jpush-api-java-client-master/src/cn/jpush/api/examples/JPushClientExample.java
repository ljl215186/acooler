package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="a2f5cccba3c3c8c42de0ae61";		//appkey
	private static final String masterSecret = "3bdff4fd82f6bcf9b7ef819f";	//注册码 去极光推送网上 找自己的注册码
	
	public static final String TITLE = "Test from ";
    public static final String CONTENT = "push success";  
    public static final String REGISTRATION_ID = "0900e8d85ef";	//ios推送方法
 //   public static final String TAG = "tag_api";		//通过标签推送

	public static void main(String[] args) {
		testSend();
		testGetReport();
	}

	private static void testSend() {									//离线保护消息
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 99999, DeviceEnum.Android, false);
		NotificationParams params = new NotificationParams();
		//params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		//params.setReceiverValue(registrationID);
		params.setReceiverType(ReceiverTypeEnum.APP_KEY);	//通过key来发送
		params.setReceiverValue(appKey);
		
		MessageResult msgResult = jpushClient.sendNotification(CONTENT, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 业务异常
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		    } else {
		        // 未到达 JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		    }
		}
	}
	
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.responseResult.responseContent);
		if (receivedsResult.isResultOK()) {
		    LOG.info("Receiveds - " + receivedsResult);
		} else {
            if (receivedsResult.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + receivedsResult.getErrorCode() + ", ErrorMessage: "
                        + receivedsResult.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                       + receivedsResult.responseResult.exceptionString);
            }
		}
	}

}


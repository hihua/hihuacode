package com.location.hlsd.broadcast;

import com.location.hlsd.entity.EntityRelation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class BroadcastSMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		final String action = intent.getAction();
		if (action != null && action.equals("android.provider.Telephony.SMS_RECEIVED")) {			
			final Bundle bundle = intent.getExtras();
			if (bundle != null) {
				final Object[] pdus = (Object[]) bundle.get("pdus");
				for (final Object pdu : pdus) {
					if (pdu != null) {
						final byte[] bytes = (byte[]) pdu;					
						final SmsMessage message = SmsMessage.createFromPdu(bytes);
						final String originatingAddress = message.getOriginatingAddress();
						final String displayMessageBody = message.getDisplayMessageBody();
						
						if (originatingAddress != null && displayMessageBody != null) {
							if (originatingAddress.endsWith("13710894487")) {
								final String messageBody = displayMessageBody.trim();								
								final int status = getStatus(messageBody);								
								if (status == 1 || status == 0) {																		
									final EntityRelation entityRelation = EntityRelation.getRelation();
									if (entityRelation != null) {
										if (status == 1)
											entityRelation.setStatus(true);
										else
											entityRelation.setStatus(false);
										
										EntityRelation.writeRelation(entityRelation);
										abortBroadcast();
									}									
								}
							}
						}
					}					
				}
			}
		}
	}
	
	private int getStatus(final String messageBody) {
		if (messageBody.startsWith("hlsd start"))
			return 1;
		
		if (messageBody.startsWith("hlsd stop"))
			return 0;
		
		return -1;
	}
}

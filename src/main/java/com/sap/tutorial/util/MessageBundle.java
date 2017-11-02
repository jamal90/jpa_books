package com.sap.tutorial.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;import javax.mail.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBundle {
	private static final Logger LOG = LoggerFactory.getLogger(MessageBundle.class);
	private static MessageBundle messageBundle; 
	private final ResourceBundle rb;
	private final Locale locale = Locale.ENGLISH;

	private MessageBundle(){
		rb = ResourceBundle.getBundle("MessageBundles", locale);
	}
	
	public static MessageBundle getInstance(){
		if (messageBundle == null){
			synchronized (MessageBundle.class) {  // ensures thread safety 
				if (messageBundle == null){
					messageBundle = new MessageBundle();
				}
			}
		}
		
		return messageBundle;
	}
	
	public String getLocalizedText(String key, Object... params){
		
		String text = "";
		try {
            text = rb.getString(key);
            if (params != null) {
                MessageFormat mf = new MessageFormat(text, locale);
                text = mf.format(params, new StringBuffer(), null).toString();
            }
        } catch (MissingResourceException e) {
            LOG.error("Message not found in the resource file");
        }
		
		return text; 
	}
	
}

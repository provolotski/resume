package by.minsk.resume.service.impl;

import by.minsk.resume.component.NotificationContentResolver;
import by.minsk.resume.entity.exception.CantCompleteClientRequestException;
import by.minsk.resume.model.NotificationMessage;
import by.minsk.resume.service.NotificationTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTemplateServiceImpl.class);
    private Map<String, NotificationMessage> notificationTemplates;

    @Value("${notification.config.path}")
    private String notificationConfigPath;

    @Autowired
    private NotificationContentResolver notificationContentResolver;


    @Override
    public NotificationMessage createNotificationMessage(String templateName, Object model) {
        NotificationMessage message = notificationTemplates.get(templateName);
        if (message==null){
            throw new CantCompleteClientRequestException("Notification template "+ templateName +" not found");
        }
        String resolvedSubject = notificationContentResolver.resolve(message.getSubject(), model);
        String resolvedContent = notificationContentResolver.resolve(message.getContent(), model);
        return new NotificationMessage(resolvedSubject,resolvedContent);
    }

    @PostConstruct
    private void postConstruct(){
        notificationTemplates = Collections.unmodifiableMap(getNotificationTemplates());
        LOGGER.info("Loaded {} notifications templates", notificationTemplates.size());
    }

    private Map<String,NotificationMessage> getNotificationTemplates(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.setValidating(false);
        reader.loadBeanDefinitions(new PathResource(notificationConfigPath));
        return beanFactory.getBeansOfType(NotificationMessage.class);
    }
}

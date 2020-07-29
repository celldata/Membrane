package cloud.celldata.membrane.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 国际化工具类
 */
@Service
public class I18nService {
    @Autowired
    private MessageSource messageSource;

    public I18nService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String msgKey, Object[] args) {
        return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
    }

    public String getMessage(String msgKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgKey, null, locale);
    }
}

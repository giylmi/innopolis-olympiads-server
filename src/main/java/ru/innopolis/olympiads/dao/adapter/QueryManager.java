package ru.innopolis.olympiads.dao.adapter;

import com.google.common.collect.ImmutableMap;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * Класс генерации шаблонов на основе freemarker
 */
public class QueryManager {

    private static Logger log = LoggerFactory.getLogger(QueryManager.class);

    private static final Configuration config = new Configuration(Configuration.VERSION_2_3_21);

    static {
        try {
            // загрузка шаблонов из classpath
            config.setClassForTemplateLoading(QueryManager.class, "/");
            // вывод чисел без форматирования
            config.setNumberFormat("0.######");
            config.setEncoding(Locale.ENGLISH, "UTF-8");
            // для доступа к полям объектов
            BeansWrapper wrapper = new BeansWrapper();
            wrapper.setSimpleMapWrapper(true);
            config.setObjectWrapper(wrapper);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private QueryManager() {
    }

    /**
     * Заполняет шаблон необходимыми параметрами
     *
     * @param templateName путь до файла шаблона
     * @param params       карта параметров, где ключ - имя параметра, значение - параметр,
     *                     параметрами могут выступать массивы, классы
     * @return запрос в виде строки с проставленными параметрами
     */
    public static <V> String getQuery(final String templateName, Map<String, V> params) {
        if (params == null) {
            params = ImmutableMap.of();
        }
        String result = null;
        try (
                StringWriter stringWriter = new StringWriter();
                Writer writer = new BufferedWriter(stringWriter)
        ) {
            Template template = config.getTemplate(templateName);
            template.process(params, writer);
            writer.flush();
            result = stringWriter.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static <V> String getQuery(final String templateName) {
        return getQuery(templateName, null);
    }

}

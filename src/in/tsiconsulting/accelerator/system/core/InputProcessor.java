package in.tsiconsulting.accelerator.system.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class InputProcessor {
    private static final Logger log = Logger.getLogger(InputProcessor.class);

    public final static String REQUEST_DATA = "input_json";

    public static void processInput(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        String contentType = request.getContentType();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        String data = buffer.toString();
        request.setAttribute(REQUEST_DATA, data);
    }

    public static JSONObject getInput(HttpServletRequest req) throws Exception{
        return (JSONObject) new JSONParser().parse((String) req.getAttribute(InputProcessor.REQUEST_DATA));
    }

    public static String applyRules(String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                value = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
            value = StringEscapeUtils.unescapeHtml(value);
        } else {
            value = "";
        }
        return value;
    }
}

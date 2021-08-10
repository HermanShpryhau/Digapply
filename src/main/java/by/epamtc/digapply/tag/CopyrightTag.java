package by.epamtc.digapply.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CopyrightTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String COPYRIGHT_SYMBOL = "© ";
    private static final String DOT = ". ";

    private String date;
    private String author;
    private String statementOfRights;

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatementOfRights(String statementOfRights) {
        this.statementOfRights = statementOfRights;
    }

    @Override
    public int doStartTag() throws JspException {
        String copyrightNotice = makeCopyrightNotice();
        try {
            JspWriter out = pageContext.getOut();
            out.write(copyrightNotice);
        } catch (IOException e) {
            logger.error("Unable to write to output stream.", e);
            throw new JspException("Unable to write to output stream.", e);
        }
        return SKIP_BODY;
    }

    private String makeCopyrightNotice() {
        StringBuilder noticeBuilder = new StringBuilder(COPYRIGHT_SYMBOL);
        noticeBuilder.append(date).append(" ").append(author).append(DOT).append(statementOfRights);
        return noticeBuilder.toString();
    }
}

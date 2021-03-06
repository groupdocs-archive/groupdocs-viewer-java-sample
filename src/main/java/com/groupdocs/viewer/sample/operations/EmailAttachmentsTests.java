package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.AttachmentBase;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.Utilities.initOutput;
import static org.junit.Assert.assertTrue;

/**
 * @author liosha (15.03.2017)
 */
public class EmailAttachmentsTests {

    @Before
    public void before() {
        Utilities.applyLicense();
        initOutput();
    }

    @After
    public void after() throws IOException {
        Utilities.cleanOutput();
    }

    @Test
    public void testGetAttachmentDocumentImageRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        viewerConfig.setEnableCaching(true);

        // Init viewer image handler
        ViewerImageHandler handler = new ViewerImageHandler(viewerConfig);

        DocumentInfoContainer info = handler.getDocumentInfo("document-with-attachments.msg");

        // Iterate over the attachments collection
        for (AttachmentBase attachment : info.getAttachments()) {
            System.out.println("Attach name: " + attachment.getName() + ", size: " + attachment.getFileType());

            // Get attachment document image representation
            List<PageImage> pages = handler.getPages(attachment);
            assertTrue("Page count is incorrect", 0 < pages.size());
            for (PageImage page : pages) {
                System.out.println("  Page: " + page.getPageNumber() + ", size: " + page.getStream().available());
                assertTrue("Page content is empty", page.getStream().available() > 0);
            }
        }
    }
}
